package finance.uc_project.controller.courriers;

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

import finance.uc_project.model.courriers.Pta;
import finance.uc_project.service.courriers.PtaService;

@RestController
@RequestMapping("/api/ptas")
public class PtaController {

    @Autowired
    private PtaService ptaService;


    @PostMapping("/insertion/personalise")
        public ResponseEntity<Pta> createPtaPersonalise(
                @RequestPart("titre") String titre,
                @RequestPart("contenue") MultipartFile contenue,
                @RequestPart("type") String type,
                @RequestPart("typeDeContenue") String typeDeContenue,
                @RequestPart("userId") String userId,
                @RequestPart("sousType") String sousType )  {
            try {

                Pta createPta = ptaService.createPtaPersonalise(titre, contenue, typeDeContenue, type, sousType, userId);
                return ResponseEntity.ok(createPta);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(400).body(null);
            }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Pta>> getAllPta() {
        List<Pta> ptas = ptaService.getAllPta();
        return ResponseEntity.ok(ptas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pta> updatePta(@PathVariable Long id, @RequestPart("contenue") MultipartFile contenue, @RequestPart("modifyby") String modifyby, @RequestPart("fileType") String fileType) {
        try {
            Pta updatedPta = ptaService.updatePta(id, contenue, fileType, modifyby);
            return ResponseEntity.ok(updatedPta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PutMapping("/valider/{id}")
    public ResponseEntity<Pta> validerPta(@PathVariable Long id) {
        Pta validerPta = ptaService.validerPta(id);
        return ResponseEntity.ok(validerPta);
    }

}
