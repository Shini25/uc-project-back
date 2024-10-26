package finance.uc_project.service.meeting;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finance.uc_project.dto.ParticipantOrganizerDTO;
import finance.uc_project.model.meeting.MeetingOrganizer;
import finance.uc_project.repository.meeting.InfoMeetingBaseRepository;
import finance.uc_project.repository.meeting.MeetingOrganizerRepository;

@Service
public class MeetingOrganizerService {

    @Autowired
    private MeetingOrganizerRepository meetingOrganizerRepository;

    @Autowired
    private InfoMeetingBaseRepository infoMeetingBaseRepository;

    public List<MeetingOrganizer> getAllMeetingOrganizers() {
        return meetingOrganizerRepository.findAll();
    }

    public MeetingOrganizer getMeetingOrganizerById(Long id) {
        return meetingOrganizerRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<ParticipantOrganizerDTO> getMeetingOrganizerByMeeting(Long meetingId) {
        return infoMeetingBaseRepository.findById(meetingId)
                .map(meeting -> meeting.getMeetingOrganizers().stream()
                        .map(meetingOrganizer -> new ParticipantOrganizerDTO(meetingOrganizer.getEmail(), meetingOrganizer.getName()) )
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    public MeetingOrganizer createMeetingOrganizer(MeetingOrganizer meetingOrganizer) {
        return meetingOrganizerRepository.save(meetingOrganizer);
    }

    public MeetingOrganizer updateMeetingOrganizer(MeetingOrganizer meetingOrganizer) {
        return meetingOrganizerRepository.save(meetingOrganizer);
    }

    public void deleteMeetingOrganizer(Long id) {
        meetingOrganizerRepository.deleteById(id);
    }

}
