package finance.uc_project.controller.courriers;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finance.uc_project.model.courriers.LivretAudit;
import finance.uc_project.service.courriers.LivretAuditService;

@RestController
@RequestMapping("/api/livret-audit")
public class LivretAuditController {

    @Autowired
    private LivretAuditService livretAuditService;

    @GetMapping("/{idCourrier}")
    public List<LivretAudit> getAllLivretAuditByIdCourrier(@PathVariable Long idCourrier) {
        return livretAuditService.getAllLivretAuditByIdCourrier(idCourrier);
    }
    
}
