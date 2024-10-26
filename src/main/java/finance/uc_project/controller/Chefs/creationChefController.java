package finance.uc_project.controller.Chefs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import finance.uc_project.model.chefs.infoBase;
import finance.uc_project.service.chefs.creationChefService;

@RestController
@RequestMapping("/api/chefs")
public class creationChefController {

    @Autowired
    private creationChefService chefCreationService;

    @PostMapping("/create")
    public ResponseEntity<infoBase> createChefs(
            @RequestPart("chef") infoBase chef,
            @RequestPart("photo") MultipartFile photo,
            @RequestPart("attributions") List<String> attributions,
            @RequestPart("motsDuChef") List<String> motsDuChef) {
        try {
            infoBase createdChef = chefCreationService.createChef(chef, photo, attributions, motsDuChef);
            return ResponseEntity.ok(createdChef);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PutMapping("/update/{ancienContact}")
    public ResponseEntity<infoBase> updateChef(
            @PathVariable String ancienContact,
            @RequestPart("chef") infoBase chef,
            @RequestPart("photo") MultipartFile photo,
            @RequestPart("attributions") List<String> attributions,
            @RequestPart("motsDuChef") List<String> motsDuChef) {
        System.out.println(ancienContact);
        System.out.println(chef);
        System.out.println(attributions);
        System.out.println(motsDuChef);
        try {
            infoBase updatedChef = chefCreationService.updateChef(ancienContact, chef, photo, attributions, motsDuChef);
            return ResponseEntity.ok(updatedChef);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }
    
}
