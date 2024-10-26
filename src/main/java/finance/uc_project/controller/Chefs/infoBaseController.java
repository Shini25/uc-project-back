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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import finance.uc_project.model.chefs.infoBase;
import finance.uc_project.service.chefs.infoBaseService;

@RestController
@RequestMapping("/api/chefs")
public class infoBaseController {

    @Autowired
    private infoBaseService chefService;

    @GetMapping("/list")
    public ResponseEntity<List<infoBase>> getAllChefsOrdered(@RequestParam(defaultValue = "desc") String order) {
        List<infoBase> chefs = chefService.getAllChefsOrdered(order);
        return ResponseEntity.ok(chefs);
    }

    @GetMapping("/{contact}")
    public ResponseEntity<infoBase> getChefById(@PathVariable String contact) {
        Optional<infoBase> chef = chefService.getChefByContact(contact);
        return chef.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<infoBase> createChef(@RequestBody infoBase chef) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chefService.createChef(chef));
    }

    @PutMapping("/{contact}")
    public ResponseEntity<infoBase> updateChef(@PathVariable String contact, @RequestBody infoBase chef) {
        chef.setContact(contact);
        return ResponseEntity.ok(chefService.updateChef(chef));
    }

    @DeleteMapping("/{contact}")
    public ResponseEntity<Void> deleteChef(@PathVariable String contact) {
        chefService.deleteByContact(contact);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{typeDeChef}")
    public ResponseEntity<List<infoBase>> getChefsByTypeDeChef(@PathVariable String typeDeChef) {
        return ResponseEntity.ok(chefService.getChefsByTypeDeChef(typeDeChef));
    }
}