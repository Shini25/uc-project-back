package finance.uc_project.repository.meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import finance.uc_project.model.meeting.Logistics;

@Repository
public interface LogisticsRepository extends JpaRepository<Logistics, Long> {
}
