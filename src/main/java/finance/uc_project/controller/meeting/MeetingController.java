package finance.uc_project.controller.meeting;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import finance.uc_project.dto.MeetingRequestDTO;
import finance.uc_project.dto.ParticipantOrganizerDTO;
import finance.uc_project.model.meeting.InfoMeetingBase;
import finance.uc_project.service.meeting.MeetingService;


@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

@PostMapping("/schedule")
public ResponseEntity<InfoMeetingBase> meetingScheduling(
        @RequestBody MeetingRequestDTO meetingRequest
) {
    try {
        // Extraire les données du DTO
        LocalDateTime dateMeeting = meetingRequest.getDateMeeting();
        String location = meetingRequest.getLocation();
        String objet = meetingRequest.getObjet();
        String meetingType = meetingRequest.getMeetingType();
        List<String> logistics = meetingRequest.getLogistics();
        List<ParticipantOrganizerDTO> organizersMail = meetingRequest.getOrganizersMail();
        List<ParticipantOrganizerDTO> participantsMail = meetingRequest.getParticipantsMail();
        List<String> observations = meetingRequest.getObservations();
        String addBy = meetingRequest.getAddBy();

        // Appel du service pour planifier la réunion
        InfoMeetingBase meeting = meetingService.meetingScheduling(dateMeeting, location, objet, meetingType, logistics, organizersMail, participantsMail, observations, addBy);
        return new ResponseEntity<>(meeting, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}

    
    @GetMapping("/all")
    public ResponseEntity<List<InfoMeetingBase>> getAllMeetings() {
        List<InfoMeetingBase> meetings = meetingService.getAllMeetings();
        return new ResponseEntity<>(meetings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InfoMeetingBase> getMeetingById(@PathVariable Long id) {
        InfoMeetingBase meeting = meetingService.getMeetingById(id);
        return new ResponseEntity<>(meeting, HttpStatus.OK);
    }

    @PutMapping("/{id}/addAttendanceSheet")
    public ResponseEntity<InfoMeetingBase> addAttendanceSheet(@PathVariable Long id, @RequestPart("content") MultipartFile attendanceSheet, @RequestPart("fileType") String fileType, @RequestPart("modifyby") String modifyby) {
        try {
            InfoMeetingBase updatedMeeting = meetingService.addAttendanceSheet(id, attendanceSheet, fileType, modifyby);
            return ResponseEntity.ok(updatedMeeting);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
    }
    }
}
