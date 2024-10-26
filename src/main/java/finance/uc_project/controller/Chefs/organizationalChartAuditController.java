package finance.uc_project.controller.Chefs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finance.uc_project.model.chefs.organizationalChartAudit;
import finance.uc_project.service.chefs.organizationalChartAuditService;

@RestController
@RequestMapping("/api/organizational-charts/audit")
public class organizationalChartAuditController {

    @Autowired
    private organizationalChartAuditService organizationalChartAuditService;

    @GetMapping("/{idOrganizationalChart}")
    public List<organizationalChartAudit> getAllOrganizationalChartAuditByIdOrganizationalChart(@PathVariable Long idOrganizationalChart) {
        return organizationalChartAuditService.getAllOrganizationalChartAuditByIdOrganizationalChart(idOrganizationalChart);
    }
}
