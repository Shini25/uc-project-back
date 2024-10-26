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

import finance.uc_project.model.chefs.photo;
import finance.uc_project.service.chefs.photoService;

@RestController
@RequestMapping("/api/chefs")
public class UcPhotoController {

    @Autowired
    private photoService ucPhotoService;

    // CRUD pour UcPhoto
    @GetMapping("/{chefId}/photos/base64")
    public ResponseEntity<List<String>> getPhotosByChef(@PathVariable Long chefId) {
        return ResponseEntity.ok(ucPhotoService.getPhotosByChef(chefId));
    }

    @PostMapping("/{chefId}/photos")
    public ResponseEntity<photo> addPhotoToChef(@PathVariable Long chefId, @RequestBody photo photo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ucPhotoService.addPhotoToChef(chefId, photo));
    }

    @GetMapping("/photos")
    public ResponseEntity<List<photo>> getAllPhotos() {
        return ResponseEntity.ok(ucPhotoService.getAllPhotos());
    }

    @GetMapping("/photos/{id}")
    public ResponseEntity<photo> getPhotoById(@PathVariable Long id) {
        Optional<photo> photo = ucPhotoService.getPhotoById(id);
        return photo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/photos/{id}")
    public ResponseEntity<photo> updatePhoto(@PathVariable Long id, @RequestBody photo photo) {
        photo.setId(id);
        return ResponseEntity.ok(ucPhotoService.updatePhoto(photo));
    }

    @DeleteMapping("/photos/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        ucPhotoService.deletePhoto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/photos/{id}/base64")
    public ResponseEntity<String> getPhotoBase64ById(@PathVariable Long id) {
        String photoBase64 = ucPhotoService.getPhotoBase64ById(id);
        return photoBase64 != null ? ResponseEntity.ok(photoBase64) : ResponseEntity.notFound().build();
    }

    @GetMapping("/photos/base64")
    public ResponseEntity<List<String>> getAllPhotosBase64() {
        List<String> photosBase64 = ucPhotoService.getAllPhotosBase64();
        return ResponseEntity.ok(photosBase64);
    }

}