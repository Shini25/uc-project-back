package finance.uc_project.controller.meeting;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finance.uc_project.dto.ParticipantOrganizerDTO;
import finance.uc_project.model.meeting.MeetingParticipant;
import finance.uc_project.service.meeting.MeetingParticipantService;

@RestController
@RequestMapping("/api/meetingParticipants")
public class MeetingParticipantController {

    @Autowired
    private MeetingParticipantService meetingParticipantService;

    @GetMapping
    public ResponseEntity<List<MeetingParticipant>> getAllParticipants() {
        List<MeetingParticipant> participants = meetingParticipantService.getAllMeetingParticipants();
        return ResponseEntity.ok(participants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingParticipant> getParticipantById(@PathVariable Long id) {
        Optional<MeetingParticipant> participant = meetingParticipantService.getMeetingParticipantById(id);
        return participant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{participantId}/meetings")
    public ResponseEntity<List<ParticipantOrganizerDTO>> getParticipantByMeeting(@PathVariable Long participantId) {
        List<ParticipantOrganizerDTO> meetings = meetingParticipantService.getMeetingParticipantsByMeeting(participantId);
        return ResponseEntity.ok(meetings);
    }

    @PostMapping
    public ResponseEntity<MeetingParticipant> createParticipant(@RequestBody MeetingParticipant meetingParticipant) {
        MeetingParticipant createdParticipant = meetingParticipantService.createMeetingParticipant(meetingParticipant);
        return new ResponseEntity<>(createdParticipant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetingParticipant> updateParticipant(@PathVariable Long id, @RequestBody MeetingParticipant meetingParticipant) {
        if (!meetingParticipantService.getMeetingParticipantById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        meetingParticipant.setId(id);
        MeetingParticipant updatedParticipant = meetingParticipantService.updateMeetingParticipant(meetingParticipant);
        return ResponseEntity.ok(updatedParticipant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
        if (!meetingParticipantService.getMeetingParticipantById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        meetingParticipantService.deleteMeetingParticipant(id);
        return ResponseEntity.noContent().build();
    }
}