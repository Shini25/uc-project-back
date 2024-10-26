package finance.uc_project.service.courriers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import finance.uc_project.enums.courrier.AccesReserveType;
import finance.uc_project.enums.courrier.TypeDocument;
import finance.uc_project.model.User_account;
import finance.uc_project.model.courriers.AccesReserve;
import finance.uc_project.repository.UserRepository;
import finance.uc_project.repository.courriers.AccesReserveRepository;

@Service
public class AccesReserveService {
    @Autowired
    private AccesReserveRepository accesReserveRepository;

    @Autowired
    private UserRepository userRepository;

    public AccesReserve createAccesReserve(String titre, MultipartFile contenue, String typeDeContenue, String accesReserveType, String userId) {
        AccesReserve accesReserve = new AccesReserve();
        accesReserve.setTitre(titre);
        try {
            byte[] contenueBytes = contenue.getBytes();
            accesReserve.setContenue(contenueBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        accesReserve.setDateInsertion(LocalDateTime.now());
        accesReserve.setType(AccesReserveType.valueOf(accesReserveType));
        accesReserve.setTypeContenue(typeDeContenue);
        accesReserve.setTypeDocument(TypeDocument.ACCES_RESERVE);
        User_account user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId));
        accesReserve.setUserId(user);
        return accesReserveRepository.save(accesReserve);
    }

    public List<AccesReserve> getAllAccesReserve() {
        return accesReserveRepository.findAll();
    }

}
