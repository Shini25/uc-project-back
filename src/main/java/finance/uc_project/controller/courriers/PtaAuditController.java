package finance.uc_project.controller.courriers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finance.uc_project.model.courriers.PtaAudit;
import finance.uc_project.service.courriers.PtaAuditService;

@RestController
@RequestMapping("/api/pta-audit")
public class PtaAuditController {

    @Autowired
    private PtaAuditService ptaAuditService;

    @GetMapping("/{idCourrier}")
    public List<PtaAudit> getAllPtaAuditByIdCourrier(@PathVariable Long idCourrier) {
        return ptaAuditService.getAllPtaAuditByIdCourrier(idCourrier);
    }
}
