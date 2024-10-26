package finance.uc_project.controller.courriers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import finance.uc_project.model.courriers.AccesReserve;
import finance.uc_project.service.courriers.AccesReserveService;

@RestController
@RequestMapping("/api/acces-reserves")
public class AccesReserveController {

    @Autowired
    private AccesReserveService accesReserveService;

    @PostMapping("/insertion/personalise")
    public ResponseEntity<AccesReserve> createAccesReservePersonalise(
        @RequestPart("titre") String titre,
        @RequestPart("contenue") MultipartFile contenue,
        @RequestPart("type") String type,
        @RequestPart("typeDeContenue") String typeDeContenue,
        @RequestPart("userId") String userId ) {
            try {
                AccesReserve createAccesReserve = accesReserveService.createAccesReserve(titre, contenue, typeDeContenue, type, userId);
                return ResponseEntity.ok(createAccesReserve);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccesReserve>> getAllAccesReserve() {
        List<AccesReserve> accesReserves = accesReserveService.getAllAccesReserve();
        return ResponseEntity.ok(accesReserves);
    }

}
