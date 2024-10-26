package finance.uc_project.service.meeting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finance.uc_project.model.meeting.Logistics;
import finance.uc_project.repository.meeting.InfoMeetingBaseRepository;
import finance.uc_project.repository.meeting.LogisticsRepository;

@Service
public class LogisticsService {

    @Autowired
    private LogisticsRepository logisticsRepository;

    @Autowired
    private InfoMeetingBaseRepository infoMeetingBaseRepository;


    public List<Logistics> getAllLogistics() {
        return logisticsRepository.findAll();
    }

    public Optional<Logistics> getLogisticsById(Long id) {
        return logisticsRepository.findById(id);
    }
    @Transactional
    public List<String> getLogisticsByMeeting(Long meetingId) {
        return infoMeetingBaseRepository.findById(meetingId)
                .map(meeting -> meeting.getLogistics().stream()
                        .map(logistics -> logistics.getDescription())
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    public Logistics createLogistics(Logistics logistics) {
        return logisticsRepository.save(logistics);
    }

    public Logistics updateLogistics(Logistics logistics) {
        return logisticsRepository.save(logistics);
    }

    public void deleteLogistics(Long id) {
        logisticsRepository.deleteById(id);
    }


}