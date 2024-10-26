package finance.uc_project.controller.courriers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import finance.uc_project.model.courriers.Activite;
import finance.uc_project.service.courriers.ActiviteService;
@RestController
@RequestMapping("/api/activites")
public class ActiviteController {

    @Autowired
    private ActiviteService activiteService;


    @PostMapping("/insertion/personalise")
        public ResponseEntity<Activite> createActivitePersonalise(
                @RequestPart("titre") String titre,
                @RequestPart("contenue") MultipartFile contenue,
                @RequestPart("type") String type,
                @RequestPart("typeDeContenue") String typeDeContenue,
                @RequestPart("userId") String userId ) {
            try {
                Activite createActivite = activiteService.createActivitePersonalise(titre, contenue, typeDeContenue, type, userId);
                return ResponseEntity.ok(createActivite);
            } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<Activite>> getAllActivites() {
        List<Activite> activites = activiteService.getAllActivites();
        return ResponseEntity.ok(activites);
    }

    @GetMapping("/day/{date}")
    public List<Activite> getActivitesByDay(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return activiteService.getActivitesByDay(date);
    }

    @GetMapping("/month/{year}/{month}")
    public List<Activite> getActivitesByMonth(@PathVariable int year, @PathVariable int month) {
        return activiteService.getActivitesByMonth(year, month);
    }

    @GetMapping("/quarter/{year}/{quarter}")
    public List<Activite> getActivitesByQuarter(@PathVariable int year, @PathVariable int quarter) {
        return activiteService.getActivitesByQuarter(year, quarter);
    }

    @GetMapping("/semester/{year}/{semester}")
    public List<Activite> getActivitesBySemester(@PathVariable int year, @PathVariable int semester) {
        return activiteService.getActivitesBySemester(year, semester);
    }

    @GetMapping("/week/{date}")
    public List<Activite> getActivitesByWeek(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return activiteService.getActivitesByWeek(date);
    }
}