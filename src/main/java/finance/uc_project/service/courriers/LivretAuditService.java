 package finance.uc_project.service.courriers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finance.uc_project.model.courriers.LivretAudit;
import finance.uc_project.repository.courriers.LivretAuditRepository;


@Service
public class LivretAuditService {

    @Autowired
    private LivretAuditRepository livretAuditRepository;

    public LivretAudit saveLivretAudit(LivretAudit livretAudit) {
        return livretAuditRepository.save(livretAudit);
    }

    public List<LivretAudit> getAllLivretAudit() {
        return livretAuditRepository.findAll();
    }

    public LivretAudit getLivretAuditById(Long id) {
        return livretAuditRepository.findById(id).orElse(null);
    }

    public void deleteLivretAudit(Long id) {
        livretAuditRepository.deleteById(id);
    }

    public List<LivretAudit> getAllLivretAuditByIdCourrier(Long idCourrier) {
        return livretAuditRepository.findAllByIdCourrier(idCourrier);
    }
}
