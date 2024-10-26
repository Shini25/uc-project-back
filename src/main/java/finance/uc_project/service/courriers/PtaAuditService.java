package finance.uc_project.service.courriers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finance.uc_project.model.courriers.PtaAudit;
import finance.uc_project.repository.courriers.PtaAuditRepository;

@Service
public class PtaAuditService {

    @Autowired
    private PtaAuditRepository ptaAuditRepository;

    public PtaAudit savePtaAudit(PtaAudit ptaAudit) {
        return ptaAuditRepository.save(ptaAudit);
    }

    public List<PtaAudit> getAllPtaAudit() {
        return ptaAuditRepository.findAll();    
    }

    public PtaAudit getPtaAuditById(Long id) {
        return ptaAuditRepository.findById(id).orElse(null);
    }

    public void deletePtaAudit(Long id) {
        ptaAuditRepository.deleteById(id);
    }

    public List<PtaAudit> getAllPtaAuditByIdCourrier(Long idCourrier) {
        return ptaAuditRepository.findAllByIdCourrier(idCourrier);
    }
}