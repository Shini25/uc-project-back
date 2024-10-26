package finance.uc_project.repository.courriers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import finance.uc_project.model.courriers.PtaAudit;

@Repository
public interface PtaAuditRepository extends JpaRepository<PtaAudit, Long> {

    List<PtaAudit> findAllByIdCourrier(Long idCourrier);
    
}
