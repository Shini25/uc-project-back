package finance.uc_project.repository.chefs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import finance.uc_project.model.chefs.organizationalChartAudit;

@Repository
public interface organizationalChartAuditRepository extends JpaRepository<organizationalChartAudit, Long> {
    List<organizationalChartAudit> findAllByIdOrganizationalChart(Long idOrganizationalChart);
    
}
