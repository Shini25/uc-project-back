package finance.uc_project.controller.meeting;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import finance.uc_project.service.EmailService;
import finance.uc_project.service.meeting.MeetingService;

@RestController
@RequestMapping("/api/send-mail")
public class SendMailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private MeetingService meetingService;

    // Regular expression for basic email validation
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

    @PostMapping("/meeting")
    public ResponseEntity<String> sendReminderEmail(
            @RequestParam String email,
            @RequestParam String objet,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam String lieu,
            @RequestParam String addby,
            @RequestParam Long meetingId) {
    
        // Validate email format
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Adresse e-mail invalide");
        }

        String sujet = "Invitation à la réunion : " + objet;
        String message = generateMessage(date, lieu, objet);

        try {
            // Send the email
            emailService.sendSimpleEmail(email, sujet, message);
            // Update meeting reminder status
            meetingService.updateReminder(meetingId, addby);
            return ResponseEntity.ok("Email envoyé avec succès");
        } catch (Exception e) {
            // Catch general exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur inattendue s'est produite lors de l'envoi de l'e-mail");
        }
    }

    // Generate the email message body
    private String generateMessage(LocalDateTime date, String lieu, String objet) {
        return String.format("""
            Madame/Monsieur,
            Vous êtes concerné(e) par la réunion qui se tiendra le %s au %s à %s.
            Votre présence est essentielle pour discuter de :  **%s**.
            
            Cordialement,\n
            Unité de coordination de DSP
            """,
            date.toLocalDate(), date.toLocalTime(), lieu, objet);
    }
}
