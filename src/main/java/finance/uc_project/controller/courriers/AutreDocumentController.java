package finance.uc_project.controller.courriers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import finance.uc_project.model.courriers.AutreDocument;
import finance.uc_project.service.courriers.AutreDocumentService;


@RestController
@RequestMapping("/api/autre-documents")
public class AutreDocumentController {

    @Autowired
    private AutreDocumentService autreDocumentService;

    @GetMapping("/all")
    public ResponseEntity<List<AutreDocument>> getAllAutreDocuments() {
        List<AutreDocument> autreDocuments = autreDocumentService.getAllAutreDocuments();
        return ResponseEntity.ok(autreDocuments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutreDocument> getAutreDocumentById(@PathVariable Long id) {
        Optional<AutreDocument> autreDocument = autreDocumentService.getAutreDocumentById(id);
        return autreDocument.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/insertion/personalise")
        public ResponseEntity<AutreDocument> createLivretPersonalise(
                @RequestPart("titre") String titre,
                @RequestPart("contenue") MultipartFile contenue,
                @RequestPart("type") String type,
                @RequestPart("typeDeContenue") String typeDeContenue,
                @RequestPart("userId") String userId ) {
            try {

                AutreDocument createAutreDocument = autreDocumentService.createAutreDocumentPersonalise(titre, contenue, typeDeContenue, type, userId);
                return ResponseEntity.ok(createAutreDocument);
            } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
