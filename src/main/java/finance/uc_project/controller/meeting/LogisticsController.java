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

import finance.uc_project.model.meeting.Logistics;
import finance.uc_project.service.meeting.LogisticsService;


@RestController
@RequestMapping("/api/logistics")
public class LogisticsController {

    @Autowired
    private LogisticsService logisticsService;

    @GetMapping
    public ResponseEntity<List<Logistics>> getAllLogistics() {
        List<Logistics> logistics = logisticsService.getAllLogistics();
        return ResponseEntity.ok(logistics);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Logistics> getLogistiqueById(@PathVariable Long id) {
        Optional<Logistics> logistics = logisticsService.getLogisticsById(id);
        return logistics.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{meetingId}/meetings")
    public ResponseEntity<List<String>> getLogistiqueByReunion(@PathVariable Long meetingId) {
        List<String> logistics = logisticsService.getLogisticsByMeeting(meetingId);
        return ResponseEntity.ok(logistics);
    }

    @PostMapping
    public ResponseEntity<Logistics> createLogistics(@RequestBody Logistics logistics) {
        Logistics createdLogistics = logisticsService.createLogistics(logistics);
        return new ResponseEntity<>(createdLogistics, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Logistics> updateLogistics(@PathVariable Long id, @RequestBody Logistics logistics) {
        if (!logisticsService.getLogisticsById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        logistics.setId(id);
        Logistics updatedLogistics = logisticsService.updateLogistics(logistics);
        return ResponseEntity.ok(updatedLogistics);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogistics(@PathVariable Long id) {
        if (!logisticsService.getLogisticsById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        logisticsService.deleteLogistics(id);
        return ResponseEntity.noContent().build();
    }
}