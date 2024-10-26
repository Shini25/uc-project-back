package finance.uc_project.controller.meeting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finance.uc_project.service.meeting.MeetingObservationService;

@RestController
@RequestMapping("/api/meetingObservations")
public class MeetingObservationController {

    @Autowired
    private MeetingObservationService meetingObservationService;

    @GetMapping("/{meetingId}/meetings")
    public ResponseEntity<List<String>> getObservationByMeeting(@PathVariable Long meetingId) {
        List<String> observations = meetingObservationService.getObservationsByMeeting(meetingId);
        return ResponseEntity.ok(observations);
    }

    
}
