package finance.uc_project.repository.meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import finance.uc_project.model.meeting.InfoMeetingBase;

@Repository
public interface InfoMeetingBaseRepository extends JpaRepository<InfoMeetingBase, Long> {
}
