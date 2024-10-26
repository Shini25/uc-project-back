package finance.uc_project.controller.Chefs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import finance.uc_project.model.chefs.organizationalChart;
import finance.uc_project.service.chefs.organizationalChartService;
@RestController
@RequestMapping("/api/organizational-charts")
public class organizationalChartController {

    @Autowired
    private organizationalChartService organizationalChartService;

    @GetMapping
    public List<organizationalChart> getAllOrganizationalCharts() {
        return organizationalChartService.getAllOrganizationalCharts();
    }


    @PostMapping("/add")
    public ResponseEntity<organizationalChart> createOrganizationalChartPersonalise(
            @RequestPart("type") String type,
            @RequestPart("contenue") MultipartFile contenue,
            @RequestPart("addby") String addby,
            @RequestPart("filetype") String filetype) {
        try {

            organizationalChart createOrganizationalChart = organizationalChartService.saveOrganizationalChart(type, contenue, addby, filetype);
            return ResponseEntity.ok(createOrganizationalChart);
        } catch (IllegalArgumentException e) {
        return ResponseEntity.status(400).body(null);
    }
} 


public ResponseEntity<organizationalChart> updateOrganizationalChart(@PathVariable Long id, @RequestPart("contenue") MultipartFile contenue, @RequestPart("updateby") String updateby) {
    try {
        organizationalChart updatedOrganizationalChart = organizationalChartService.updateOrganizationalChart(id, contenue, updateby);
        return ResponseEntity.ok(updatedOrganizationalChart);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(400).body(null);
        }
        }



@PutMapping("/{id}")
public ResponseEntity<organizationalChart> updateOrganizationalChart(@PathVariable Long id, @RequestPart("contenue") MultipartFile contenue, @RequestPart("modifyby") String modifyby, @RequestPart("fileType") String fileType) {
    try {
        organizationalChart updatedOrganizationalChart = organizationalChartService.updateOrganizationalChart(id, contenue, fileType, modifyby);
        return ResponseEntity.ok(updatedOrganizationalChart);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(400).body(null);
    }
}

}
