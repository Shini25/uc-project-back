package finance.uc_project.controller.Chefs;

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

import finance.uc_project.model.chefs.attribution;
import finance.uc_project.service.chefs.attributionService;

@RestController
@RequestMapping("/api/chefs")
public class attributionController {

    @Autowired
    private attributionService chefAttributionService;

    @GetMapping("/{chefId}/attributions")
    public ResponseEntity<List<String>> getAttributionsByChef(@PathVariable Long chefId) {
        return ResponseEntity.ok(chefAttributionService.getAttributionsByChef(chefId));
    }

    @PostMapping("/{chefId}/attributions")
    public ResponseEntity<attribution> addAttributionToChef(@PathVariable Long chefId, @RequestBody attribution attribution) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chefAttributionService.addAttributionToChef(chefId, attribution));
    }

    @GetMapping("/attributions")
    public ResponseEntity<List<attribution>> getAllAttributions() {
        return ResponseEntity.ok(chefAttributionService.getAllAttributions());
    }

    @GetMapping("/attributions/{id}")
    public ResponseEntity<attribution> getAttributionById(@PathVariable Long id) {
        Optional<attribution> attribution = chefAttributionService.getAttributionById(id);
        return attribution.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/attributions/{id}")
    public ResponseEntity<attribution> updateAttribution(@PathVariable Long id, @RequestBody attribution attribution) {
        attribution.setId(id);
        return ResponseEntity.ok(chefAttributionService.updateAttribution(attribution));
    }

    @DeleteMapping("/attributions/{id}")
    public ResponseEntity<Void> deleteAttribution(@PathVariable Long id) {
        chefAttributionService.deleteAttribution(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/attributions/contact/{contact}")
    public ResponseEntity<List<attribution>> getAttributionsByChefContact(@PathVariable String contact) {
        List<attribution> attributions = chefAttributionService.getAttributionsByChefContact(contact);
        return ResponseEntity.ok(attributions);
    }
}


