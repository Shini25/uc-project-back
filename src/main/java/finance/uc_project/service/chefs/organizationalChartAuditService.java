package finance.uc_project.service.chefs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finance.uc_project.model.chefs.organizationalChartAudit;
import finance.uc_project.repository.chefs.organizationalChartAuditRepository;

@Service
public class organizationalChartAuditService {
    
    @Autowired
    private organizationalChartAuditRepository organizationalChartAuditRepository;

    public organizationalChartAudit saveOrganizationalChartAudit(organizationalChartAudit organizationalChartAudit) {
        return organizationalChartAuditRepository.save(organizationalChartAudit);
    }
    
    public List<organizationalChartAudit> getAllOrganizationalChartAudit() {
        return organizationalChartAuditRepository.findAll();
    }

    public organizationalChartAudit getOrganizationalChartAuditById(Long id) {
        return organizationalChartAuditRepository.findById(id).orElse(null);
    }

    public void deleteOrganizationalChartAudit(Long id) {
        organizationalChartAuditRepository.deleteById(id);
    }   

    public List<organizationalChartAudit> getAllOrganizationalChartAuditByIdOrganizationalChart(Long idOrganizationalChart) {
        return organizationalChartAuditRepository.findAllByIdOrganizationalChart(idOrganizationalChart);
    }
}   
