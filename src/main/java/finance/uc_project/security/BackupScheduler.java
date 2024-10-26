package finance.uc_project.security;

import java.io.IOException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BackupScheduler {

    @Scheduled(cron = "0 20 23 * * *") // Exécute tous les jours à 23h20
    public void performBackup() {
        String[] command = {"/bin/bash", "/home/leopard/Documents/F_M_C_L/STAGE/backup/backup.sh"};
        try {
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Backup successful");
            } else {
                System.out.println("Backup failed");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
