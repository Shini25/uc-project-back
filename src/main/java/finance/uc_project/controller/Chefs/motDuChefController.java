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

import finance.uc_project.model.chefs.motDuChef;
import finance.uc_project.service.chefs.motDuChefService;

@RestController
@RequestMapping("/api/chefs")
public class motDuChefController {

    @Autowired
    private motDuChefService ucMotDuChefService;

    // CRUD pour UcMotDuChef
    @GetMapping("/{chefId}/motduchefs")
    public ResponseEntity<List<String>> getMotDuChefsByChef(@PathVariable Long chefId) {
        return ResponseEntity.ok(ucMotDuChefService.getMotDuChefsByChef(chefId));
    }


    @PostMapping("/{chefId}/motduchefs")
    public ResponseEntity<motDuChef> addMotDuChefToChef(@PathVariable Long chefId, @RequestBody motDuChef motDuChef) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ucMotDuChefService.addMotDuChefToChef(chefId, motDuChef));
    }

    @GetMapping("/motduchefs")
    public ResponseEntity<List<motDuChef>> getAllMotDuChefs() {
        return ResponseEntity.ok(ucMotDuChefService.getAllMotDuChefs());
    }

    @GetMapping("/motduchefs/{id}")
    public ResponseEntity<motDuChef> getMotDuChefById(@PathVariable Long id) {
        Optional<motDuChef> motDuChef = ucMotDuChefService.getMotDuChefById(id);
        return motDuChef.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/motduchefs/{id}")
    public ResponseEntity<motDuChef> updateMotDuChef(@PathVariable Long id, @RequestBody motDuChef motDuChef) {
        motDuChef.setId(id);
        return ResponseEntity.ok(ucMotDuChefService.updateMotDuChef(motDuChef));
    }

    @DeleteMapping("/motduchefs/{id}")
    public ResponseEntity<Void> deleteMotDuChef(@PathVariable Long id) {
        ucMotDuChefService.deleteMotDuChef(id);
        return ResponseEntity.noContent().build();
    }
}