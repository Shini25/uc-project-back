package finance.uc_project.service.courriers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import finance.uc_project.enums.courrier.AutreDocumentType;
import finance.uc_project.enums.courrier.TypeDocument;
import finance.uc_project.model.User_account;
import finance.uc_project.model.courriers.AutreDocument;
import finance.uc_project.repository.UserRepository;
import finance.uc_project.repository.courriers.AutreDocumentRepository;

@Service
public class AutreDocumentService {

    @Autowired
    private AutreDocumentRepository autreDocumentRepository;

    @Autowired
    private UserRepository userRepository;

    public List<AutreDocument> getAllAutreDocuments() {
        return autreDocumentRepository.findAll();
    }

    public Optional<AutreDocument> getAutreDocumentById(Long id) {
        return autreDocumentRepository.findById(id);
    }

    public AutreDocument createAutreDocumentPersonalise(String titre, MultipartFile contenue, String typeDeContenue, String autreDocumentType, String userId) {
        AutreDocument autreDocument = new AutreDocument();
        autreDocument.setTitre(titre);
        try {
            byte[] contenueBytes = contenue.getBytes();
            autreDocument.setContenue(contenueBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        autreDocument.setType(AutreDocumentType.valueOf(autreDocumentType));
        autreDocument.setDateInsertion(LocalDateTime.now());
        autreDocument.setTypeContenue(typeDeContenue);
        autreDocument.setTypeDocument(TypeDocument.AUTRE_DOCUMENT);
        User_account user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId));
        autreDocument.setUserId(user);
        return autreDocumentRepository.save(autreDocument);
    }
}
