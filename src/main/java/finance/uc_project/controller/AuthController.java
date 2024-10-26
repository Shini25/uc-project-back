package finance.uc_project.controller;

import java.time.LocalDateTime;
import java.util.HashMap; // Corrected import for Optional
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finance.uc_project.dto.ResetPasswordRequest;
import finance.uc_project.model.AuthenticationRequest;
import finance.uc_project.model.AuthenticationResponse;
import finance.uc_project.model.User_account;
import finance.uc_project.repository.UserRepository;
import finance.uc_project.service.CustomUserDetailsService;
import finance.uc_project.service.EmailService;
import finance.uc_project.service.UserService;
import finance.uc_project.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getNumero(), authenticationRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getNumero());
        final String jwt = jwtUtil.generateToken(userDetails);
        System.out.println("JWT token generated: " + jwt);

        // Update user status to ONLINE
        userService.updateStatus(authenticationRequest.getNumero(), "ONLINE");

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<Map<String, String>> requestPasswordReset(@RequestBody String email) {
        Optional<User_account> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User_account user = userOptional.get();

            // Generate a 6-digit verification code
            String verificationCode = String.format("%06d", new Random().nextInt(999999));

            // Save the verification code and timestamp in the database
            user.setVerificationCode(verificationCode);
            user.setVerificationCodeTimestamp(LocalDateTime.now());
            userRepository.save(user);

            // Send the code by email
            emailService.sendVerificationCode(user.getEmail(), verificationCode);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Code de vérification envoyé à votre adresse email.");
            return ResponseEntity.ok(response);
        }

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Utilisateur non trouvé.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordRequest request) {
        Optional<User_account> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            User_account user = userOptional.get();

            // Check if the verification code is not null and not expired before comparing
            if (user.getVerificationCode() != null && 
                user.getVerificationCodeTimestamp() != null &&
                user.getVerificationCodeTimestamp().isAfter(LocalDateTime.now().minusMinutes(10)) &&
                user.getVerificationCode().equals(request.getVerificationCode())) {
                
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                user.setVerificationCode(null);
                user.setVerificationCodeTimestamp(null);
                userRepository.save(user);

                Map<String, String> response = new HashMap<>();
                response.put("message", "Mot de passe réinitialisé avec succès.");
                return ResponseEntity.ok(response);
            }

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Code de vérification invalide ou expiré.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Utilisateur non trouvé.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
