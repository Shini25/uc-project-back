package finance.uc_project.service.courriers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import finance.uc_project.enums.courrier.ActiviteType;
import finance.uc_project.enums.courrier.TypeDocument;
import finance.uc_project.model.User_account;
import finance.uc_project.model.courriers.Activite;
import finance.uc_project.repository.UserRepository;
import finance.uc_project.repository.courriers.ActiviteRepository;

@Service
public class ActiviteService {

    @Autowired
    private ActiviteRepository activiteRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Activite> getAllActivites() {
        return activiteRepository.findAll();
    }

    public Activite createActivitePersonalise(String titre, MultipartFile contenue, String typeDeContenue, String activiteType, String userId) {
        Activite activite = new Activite();
        activite.setTitre(titre);
        try {
            byte[] contenueBytes = contenue.getBytes();
            activite.setContenue(contenueBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        activite.setType(ActiviteType.valueOf(activiteType));
        activite.setDateInsertion(LocalDateTime.now());
        activite.setTypeContenue(typeDeContenue);
        activite.setTypeDocument(TypeDocument.ACTIVITE);
        User_account user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId));
        activite.setUserId(user);
        return activiteRepository.save(activite);
    }


    public List<Activite> getActivitesByDay(LocalDate date) {
        return activiteRepository.findByDay(date);
    }

    public List<Activite> getActivitesByMonth(int year, int month) {
        return activiteRepository.findByMonth(year, month);
    }

    public List<Activite> getActivitesByQuarter(int year, int quarter) {
        return activiteRepository.findByQuarter(year, quarter);
    }

    public List<Activite> getActivitesBySemester(int year, int semester) {
        return activiteRepository.findBySemester(year, semester);
    }

    public List<Activite> getActivitesByWeek(LocalDate date) {
        return activiteRepository.findByWeek(date);
    }
}
