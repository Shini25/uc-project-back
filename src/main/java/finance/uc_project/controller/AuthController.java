package finance.uc_project.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finance.uc_project.dto.ResetPasswordRequest;
import finance.uc_project.model.AuthenticationRequest;
import finance.uc_project.model.User_account;
import finance.uc_project.repository.UserRepository;
import finance.uc_project.service.CustomUserDetailsService;
import finance.uc_project.service.EmailService;
import finance.uc_project.service.UserService;
import finance.uc_project.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

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
    public ResponseEntity<Map<String, String>> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                                                     HttpServletResponse response) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getNumero(), authenticationRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getNumero());
        final String jwt = jwtUtil.generateToken(userDetails);
        System.out.println("JWT token generated: " + jwt);

        Cookie jwtCookie = new Cookie("jwtToken", jwt);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false); // Set to true in production with HTTPS
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60); // 1 hour in seconds
        response.addCookie(jwtCookie);

        userService.updateStatus(authenticationRequest.getNumero(), "ONLINE");

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Authentification réussie");
        responseBody.put("jwt", jwt);

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/check-session")
    public ResponseEntity<?> checkSession() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return ResponseEntity.ok("User is authenticated");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("jwtToken", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        response.addCookie(jwtCookie);

        return ResponseEntity.ok("Déconnexion réussie");
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<Map<String, String>> requestPasswordReset(@RequestBody String email) {
        Optional<User_account> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User_account user = userOptional.get();

            String verificationCode = String.format("%06d", new Random().nextInt(999999));

            user.setVerificationCode(verificationCode);
            user.setVerificationCodeTimestamp(LocalDateTime.now());
            userRepository.save(user);

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
