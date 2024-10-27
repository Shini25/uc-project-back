package finance.uc_project.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import finance.uc_project.model.User_account;
import finance.uc_project.repository.UserRepository;
import finance.uc_project.service.CustomUserDetailsService;
import finance.uc_project.service.EmailService;
import finance.uc_project.service.UserService;
import finance.uc_project.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

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
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                                       HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getNumero(), authenticationRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getNumero());
            final String jwt = jwtUtil.generateToken(userDetails);

            logger.info("JWT token generated successfully for user: {}", authenticationRequest.getNumero());

            // Configure le cookie pour stocker le JWT
            Cookie jwtCookie = new Cookie("jwtToken", jwt);
            jwtCookie.setHttpOnly(true); // Empêche l'accès par JavaScript
            jwtCookie.setSecure(false); // Activer uniquement en production (HTTPS)
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(60 * 60); // Durée de vie de 1 heure
            response.addCookie(jwtCookie);

            // Mettre à jour le statut de l'utilisateur
            userService.updateStatus(authenticationRequest.getNumero(), "ONLINE");

            return ResponseEntity.ok("Authentification réussie");
        } catch (Exception ex) {
            logger.error("Échec de l'authentification pour l'utilisateur: {}", authenticationRequest.getNumero(), ex);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants incorrects");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("jwtToken", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // Ajuster pour l'environnement de dev si nécessaire
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Expire immédiatement
        response.addCookie(jwtCookie);

        logger.info("Déconnexion réussie, le cookie JWT a été supprimé.");
        return ResponseEntity.ok("Déconnexion réussie");
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<Map<String, String>> requestPasswordReset(@RequestBody String email) {
        Optional<User_account> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User_account user = userOptional.get();

            // Générer un code de vérification à 6 chiffres
            String verificationCode = String.format("%06d", new Random().nextInt(999999));

            // Enregistrer le code de vérification et son horodatage
            user.setVerificationCode(verificationCode);
            user.setVerificationCodeTimestamp(LocalDateTime.now());
            userRepository.save(user);

            // Envoyer le code par e-mail
            emailService.sendVerificationCode(user.getEmail(), verificationCode);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Code de vérification envoyé à votre adresse e-mail.");
            logger.info("Code de vérification envoyé à l'utilisateur avec l'email : {}", email);
            return ResponseEntity.ok(response);
        }

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Utilisateur non trouvé.");
        logger.warn("Demande de réinitialisation de mot de passe échouée pour l'email : {}", email);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordRequest request) {
        Optional<User_account> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            User_account user = userOptional.get();

            // Vérification de l'expiration du code de vérification
            if (user.getVerificationCode() != null &&
                user.getVerificationCodeTimestamp() != null &&
                user.getVerificationCodeTimestamp().isAfter(LocalDateTime.now().minusMinutes(10)) &&
                user.getVerificationCode().equals(request.getVerificationCode())) {

                // Réinitialiser le mot de passe
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                user.setVerificationCode(null);
                user.setVerificationCodeTimestamp(null);
                userRepository.save(user);

                Map<String, String> response = new HashMap<>();
                response.put("message", "Mot de passe réinitialisé avec succès.");
                logger.info("Mot de passe réinitialisé avec succès pour l'utilisateur : {}", request.getEmail());
                return ResponseEntity.ok(response);
            }

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Code de vérification invalide ou expiré.");
            logger.warn("Échec de la réinitialisation du mot de passe : code invalide ou expiré pour l'email : {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Utilisateur non trouvé.");
        logger.warn("Tentative de réinitialisation de mot de passe pour un utilisateur non trouvé : {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
