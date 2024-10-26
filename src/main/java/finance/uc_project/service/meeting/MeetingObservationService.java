package finance.uc_project.service.meeting;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finance.uc_project.model.meeting.MeetingObservation;
import finance.uc_project.repository.meeting.InfoMeetingBaseRepository;
import finance.uc_project.repository.meeting.MeetingObservationRepository;

@Service
public class MeetingObservationService {

    @Autowired
    private MeetingObservationRepository meetingObservationRepository;

    @Autowired
    private InfoMeetingBaseRepository infoMeetingBaseRepository;

    public MeetingObservation saveObservation(MeetingObservation meetingObservation) {
        return meetingObservationRepository.save(meetingObservation);
    }

    public MeetingObservation getObservationById(Long id) {
        return meetingObservationRepository.findById(id).orElse(null);
    }

    public void deleteObservation(Long id) {
        meetingObservationRepository.deleteById(id);
    }

    @Transactional
    public List<String> getObservationsByMeeting(Long meetingId) {
        return infoMeetingBaseRepository.findById(meetingId)
                .map(meeting -> meeting.getMeetingObservations().stream()
                        .map(observation -> observation.getObservation())
                        .collect(Collectors.toList()))
                .orElse(null);
    }
}
