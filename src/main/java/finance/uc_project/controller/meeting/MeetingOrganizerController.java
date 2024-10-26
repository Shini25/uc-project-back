package finance.uc_project.controller.meeting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finance.uc_project.dto.ParticipantOrganizerDTO;
import finance.uc_project.model.meeting.MeetingOrganizer;
import finance.uc_project.service.meeting.MeetingOrganizerService;

@RestController
@RequestMapping("/api/meetingOrganizers")
public class MeetingOrganizerController {

    @Autowired
    private MeetingOrganizerService meetingOrganizerService;

    @GetMapping
    public ResponseEntity<List<MeetingOrganizer>> getAllResponsables() {
        List<MeetingOrganizer> organizers = meetingOrganizerService.getAllMeetingOrganizers();
        return ResponseEntity.ok(organizers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingOrganizer> getResponsableById(@PathVariable Long id) {
        MeetingOrganizer organizer = meetingOrganizerService.getMeetingOrganizerById(id);
        return organizer != null ? ResponseEntity.ok(organizer) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{meetingId}/meetings")
    public ResponseEntity<List<ParticipantOrganizerDTO>> getResponsableByMeeting(@PathVariable Long meetingId) {
        List<ParticipantOrganizerDTO> organizers = meetingOrganizerService.getMeetingOrganizerByMeeting(meetingId);
        return ResponseEntity.ok(organizers);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MeetingOrganizer> updateOrganizer(@PathVariable Long id, @RequestBody MeetingOrganizer organizer) {
        if (meetingOrganizerService.getMeetingOrganizerById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        organizer.setId(id);
        MeetingOrganizer updatedOrganizer = meetingOrganizerService.updateMeetingOrganizer(organizer);
        return ResponseEntity.ok(updatedOrganizer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizer(@PathVariable Long id) {
        if (meetingOrganizerService.getMeetingOrganizerById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        meetingOrganizerService.deleteMeetingOrganizer(id);
        return ResponseEntity.noContent().build();
    }
}