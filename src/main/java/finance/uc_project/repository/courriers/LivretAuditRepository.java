package finance.uc_project.repository.courriers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import finance.uc_project.model.courriers.LivretAudit;

@Repository
public interface LivretAuditRepository extends JpaRepository<LivretAudit, Long> {
    List<LivretAudit> findAllByIdCourrier(Long idCourrier);
}
