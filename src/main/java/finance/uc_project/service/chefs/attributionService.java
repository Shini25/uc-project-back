package finance.uc_project.service.chefs;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finance.uc_project.model.chefs.attribution;
import finance.uc_project.repository.chefs.attributionRepository;
import finance.uc_project.repository.chefs.infoBaseRepository;

@Service
public class attributionService {

    @Autowired
    private attributionRepository ucAttributionRepository;

    @Autowired
    private infoBaseRepository ucRepository;

    public List<attribution> getAllAttributions() {
        return ucAttributionRepository.findAll();
    }

    public Optional<attribution> getAttributionById(Long id) {
        return ucAttributionRepository.findById(id);
    }

    public attribution createAttribution(attribution attribution) {
        return ucAttributionRepository.save(attribution);
    }

    public attribution updateAttribution(attribution attribution) {
        return ucAttributionRepository.save(attribution);
    }

    public void deleteAttribution(Long id) {
        ucAttributionRepository.deleteById(id);
    }

    @Transactional
    public List<String> getAttributionsByChef(Long chefId) {
        return ucRepository.findById(chefId)
                .map(chef -> chef.getAttributions().stream()
                        .map(attribution::getAttribution)
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    public attribution addAttributionToChef(Long chefId, attribution attribution) {
        return ucRepository.findById(chefId).map(chef -> {
            attribution.setChef(chef);
            return ucAttributionRepository.save(attribution);
        }).orElse(null);
    }

    // New method to get attributions by chef's contact
    public List<attribution> getAttributionsByChefContact(String contact) {
        return ucAttributionRepository.findByChefContact(contact);
    }
}