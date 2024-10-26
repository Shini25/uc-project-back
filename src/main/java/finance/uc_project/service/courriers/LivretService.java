package finance.uc_project.service.courriers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import finance.uc_project.enums.courrier.LivretType;
import finance.uc_project.enums.courrier.TypeDocument;
import finance.uc_project.model.User_account;
import finance.uc_project.model.courriers.Livret;
import finance.uc_project.model.courriers.LivretAudit;
import finance.uc_project.repository.UserRepository;
import finance.uc_project.repository.courriers.LivretAuditRepository;
import finance.uc_project.repository.courriers.LivretRepository;

@Service
public class LivretService {

    @Autowired
    private LivretRepository livretRepository;

    @Autowired
    private LivretAuditRepository livretAuditRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<Livret> getAllLivrets() {
        return livretRepository.findAll();
    }

    public Optional<Livret> getLivretById(Long id) {
        return livretRepository.findById(id);
    }

    public Livret createLivret(Livret livret) {
        return livretRepository.save(livret);
    }

    public Livret updateLivret(Long id, MultipartFile contenue, String fileType, String modifyby) {
        Livret existingLivret = livretRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid livret id: " + id));
        existingLivret.setDateModification(LocalDateTime.now());
        saveLivretAudit(existingLivret, modifyby);
        existingLivret.setDateInsertion(LocalDateTime.now());
        existingLivret.setTypeContenue(fileType);
        try {
        byte[] contenueBytes = contenue.getBytes();
            existingLivret.setContenue(contenueBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return livretRepository.save(existingLivret);
    }

    public void deleteLivret(Long id) {
        livretRepository.deleteById(id);
    }

    public Livret createLivretPersonalise(String titre, MultipartFile contenue, String typeDeContenue, String livretType, String userId ) {
        Livret livret = new Livret();
        livret.setTitre(titre);

        try {
            byte[] contenueBytes = contenue.getBytes();
            livret.setContenue(contenueBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        livret.setType(LivretType.valueOf(livretType));
        livret.setDateInsertion(LocalDateTime.now());
        livret.setTypeContenue(typeDeContenue);
        livret.setTypeDocument(TypeDocument.LIVRET);
        User_account user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId));
        livret.setUserId(user);
        return livretRepository.save(livret);
    }

    private void saveLivretAudit(Livret livret, String modifyby) {
        LivretAudit livretAudit = new LivretAudit();
        livretAudit.setIdCourrier(livret.getIdCourrier());
        livretAudit.setTitre(livret.getTitre());
        livretAudit.setDateInsertion(livret.getDateInsertion());
        livretAudit.setContenue(livret.getContenue());
        livretAudit.setTypeContenue(livret.getTypeContenue());
        livretAudit.setTypeDocument(livret.getTypeDocument().name());
        livretAudit.setUserId(livret.getUser_account().getNumero());
        livretAudit.setType(livret.getType().toString());
        livretAudit.setAuditTimestamp(LocalDateTime.now());
        livretAudit.setModifiedBy(modifyby);

        livretAuditRepository.save(livretAudit);
    }
}
