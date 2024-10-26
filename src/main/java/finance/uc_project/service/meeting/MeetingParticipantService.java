package finance.uc_project.service.meeting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finance.uc_project.dto.ParticipantOrganizerDTO;
import finance.uc_project.model.meeting.MeetingParticipant;
import finance.uc_project.repository.meeting.InfoMeetingBaseRepository;
import finance.uc_project.repository.meeting.MeetingParticipantRepository;

@Service
public class MeetingParticipantService {

    @Autowired
    private MeetingParticipantRepository meetingParticipantRepository;

    @Autowired
    private InfoMeetingBaseRepository infoMeetingBaseRepository;

    public List<MeetingParticipant> getAllMeetingParticipants() {
        return meetingParticipantRepository.findAll();
    }

    public Optional<MeetingParticipant> getMeetingParticipantById(Long id) {
        return meetingParticipantRepository.findById(id);
    }

    @Transactional
    public List<ParticipantOrganizerDTO> getMeetingParticipantsByMeeting(Long meetingId) {
        return infoMeetingBaseRepository.findById(meetingId)
                .map(meeting -> meeting.getMeetingParticipants().stream()
                        .map(meetingParticipant -> new ParticipantOrganizerDTO(meetingParticipant.getEmail(), meetingParticipant.getName()))
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    public MeetingParticipant createMeetingParticipant(MeetingParticipant meetingParticipant) {
        return meetingParticipantRepository.save(meetingParticipant);
    }

    public MeetingParticipant updateMeetingParticipant(MeetingParticipant meetingParticipant) {
        return meetingParticipantRepository.save(meetingParticipant);
    }

    public void deleteMeetingParticipant(Long id) {
        meetingParticipantRepository.deleteById(id);
    }
}