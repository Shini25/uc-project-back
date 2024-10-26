package finance.uc_project.controller.courriers;

import java.util.List;
import java.util.Optional;

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

import finance.uc_project.model.courriers.Livret;
import finance.uc_project.service.courriers.LivretService;

@RestController
@RequestMapping("/api/livrets")
public class LivretController {

    @Autowired
    private LivretService livretService;

    @GetMapping
    public List<Livret> getAllLivrets() {
        return livretService.getAllLivrets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livret> getLivretById(@PathVariable Long id) {
        Optional<Livret> livret = livretService.getLivretById(id);
        return livret.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/insertion/personalise")
        public ResponseEntity<Livret> createLivretPersonalise(
                @RequestPart("titre") String titre,
                @RequestPart("contenue") MultipartFile contenue,
                @RequestPart("type") String type,
                @RequestPart("typeDeContenue") String typeDeContenue,
                @RequestPart("userId") String userId) {
            try {

                Livret createLivret = livretService.createLivretPersonalise(titre, contenue, typeDeContenue, type, userId);
                return ResponseEntity.ok(createLivret);
            } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }   

    @PutMapping("/{id}")
    public ResponseEntity<Livret> updateLivret(@PathVariable Long id, @RequestPart("contenue") MultipartFile contenue, @RequestPart("fileType") String fileType, @RequestPart("modifyby") String modifyby) {
        try {
            Livret updatedLivret = livretService.updateLivret(id, contenue, fileType,  modifyby);
            return ResponseEntity.ok(updatedLivret);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
