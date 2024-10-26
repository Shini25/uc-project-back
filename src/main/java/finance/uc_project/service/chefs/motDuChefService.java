package finance.uc_project.service.chefs;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finance.uc_project.model.chefs.motDuChef;
import finance.uc_project.repository.chefs.infoBaseRepository;
import finance.uc_project.repository.chefs.motDuChefRepository;

@Service
public class motDuChefService {

    @Autowired
    private motDuChefRepository ucMotDuChefRepository;

    @Autowired
    private infoBaseRepository ucRepository;

    public List<motDuChef> getAllMotDuChefs() {
        return ucMotDuChefRepository.findAll();
    }

    public Optional<motDuChef> getMotDuChefById(Long id) {
        return ucMotDuChefRepository.findById(id);
    }

    public motDuChef createMotDuChef(motDuChef motDuChef) {
        return ucMotDuChefRepository.save(motDuChef);
    }

    public motDuChef updateMotDuChef(motDuChef motDuChef) {
        return ucMotDuChefRepository.save(motDuChef);
    }

    public void deleteMotDuChef(Long id) {
        ucMotDuChefRepository.deleteById(id);
    }

    @Transactional
    public List<String> getMotDuChefsByChef(Long chefId) {
        return ucRepository.findById(chefId)
                .map(chef -> chef.getMotDuChefs().stream()
                        .map(motDuChef::getParagraphe)
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    public motDuChef addMotDuChefToChef(Long chefId, motDuChef motDuChef) {
        return ucRepository.findById(chefId).map(chef -> {
            motDuChef.setChef(chef);
            return ucMotDuChefRepository.save(motDuChef);
        }).orElse(null);
    }
}