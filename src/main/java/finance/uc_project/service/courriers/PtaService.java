package finance.uc_project.service.courriers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import finance.uc_project.enums.courrier.TypeDocument;
import finance.uc_project.model.User_account;
import finance.uc_project.model.courriers.Pta;
import finance.uc_project.model.courriers.PtaAudit;
import finance.uc_project.repository.UserRepository;
import finance.uc_project.repository.courriers.PtaAuditRepository;
import finance.uc_project.repository.courriers.PtaRepository;

@Service
public class PtaService {

    @Autowired
    private PtaRepository ptaRepository;

    @Autowired
    private PtaAuditRepository ptaAuditRepository;

    @Autowired
    private UserRepository userRepository;

    public Pta createPtaPersonalise(String titre, MultipartFile contenue, String typeDeContenue, String sousType, String ptaType, String userId) {
        Pta pta = new Pta();
        pta.setTitre(titre);
        try {
            byte[] contenueBytes = contenue.getBytes();
            pta.setContenue(contenueBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pta.setType(ptaType);
        pta.setSousType(sousType);
        pta.setDateInsertion(LocalDateTime.now());
        pta.setTypeContenue(typeDeContenue);
        pta.setTypeDocument(TypeDocument.PTA);
        User_account user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId));
        pta.setUserId(user);
        return ptaRepository.save(pta);
    }

    public List<Pta> getAllPta() {
        return ptaRepository.findAll();
    }

    public Optional<Pta> getPtaById(Long id) {
        return ptaRepository.findById(id);
    }

    public Pta createPta(Pta pta) {
        return ptaRepository.save(pta);
    }
    
    public Pta updatePta(Long id, MultipartFile contenue,String fileType, String modifyby) {
        Pta existingPta = ptaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid pta id: " + id));
        existingPta.setDateModification(LocalDateTime.now());
        savePtaAudit(existingPta, modifyby);
        existingPta.setDateInsertion(LocalDateTime.now());
        try {
            byte[] contenueBytes = contenue.getBytes();
            existingPta.setContenue(contenueBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        existingPta.setTypeContenue(fileType);
        return ptaRepository.save(existingPta);
    }

    public Pta validerPta(Long id) {
        Pta existingPta = ptaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid pta id: " + id));
        existingPta.setValide(true);
        return ptaRepository.save(existingPta);
    }

    public void deletePta(Long id) {
        ptaRepository.deleteById(id);
    }

    private void savePtaAudit(Pta pta, String modifyby) {
        PtaAudit ptaAudit = new PtaAudit();
        ptaAudit.setIdCourrier(pta.getIdCourrier());
        ptaAudit.setTitre(pta.getTitre());
        ptaAudit.setDateInsertion(pta.getDateInsertion());
        ptaAudit.setContenue(pta.getContenue());
        ptaAudit.setTypeContenue(pta.getTypeContenue());
        ptaAudit.setTypeDocument(pta.getTypeDocument().name());
        ptaAudit.setUserId(pta.getUser_account().getNumero());
        ptaAudit.setType(pta.getType());
        ptaAudit.setValide(pta.isValide());
        ptaAudit.setSousType(pta.getSousType());
        ptaAudit.setAuditTimestamp(LocalDateTime.now());
        ptaAudit.setModifiedBy(modifyby);
        ptaAuditRepository.save(ptaAudit);
    }
}
