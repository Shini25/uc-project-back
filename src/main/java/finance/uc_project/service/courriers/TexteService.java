package finance.uc_project.service.courriers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import finance.uc_project.enums.courrier.TextesType;
import finance.uc_project.enums.courrier.TypeDocument;
import finance.uc_project.model.User_account;
import finance.uc_project.model.courriers.Texte;
import finance.uc_project.repository.UserRepository;
import finance.uc_project.repository.courriers.TexteRepository;

@Service
public class TexteService {

    @Autowired
    private TexteRepository texteRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Texte> getAllTextes() {
        return texteRepository.findAll();
    }

    public Optional<Texte> getTexteById(Long id) {
        return texteRepository.findById(id);
    }

    public Texte  createTextePersonalise(String titre, MultipartFile contenue, String typeDeContenue, String texteType, String userId) {
        Texte texte = new Texte();
        texte.setTitre(titre);
        try {
            byte[] contenueBytes = contenue.getBytes();
            texte.setContenue(contenueBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        texte.setType(TextesType.valueOf(texteType));
        texte.setDateInsertion(LocalDateTime.now());
        texte.setTypeContenue(typeDeContenue);
        texte.setTypeDocument(TypeDocument.TEXTE);
        User_account user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId));
        texte.setUserId(user);
        return texteRepository.save(texte);
    }
}
