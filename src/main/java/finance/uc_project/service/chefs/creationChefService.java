package finance.uc_project.service.chefs;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import finance.uc_project.model.chefs.attribution;
import finance.uc_project.model.chefs.infoBase;
import finance.uc_project.model.chefs.motDuChef;
import finance.uc_project.model.chefs.photo;
import finance.uc_project.repository.chefs.attributionRepository;
import finance.uc_project.repository.chefs.infoBaseRepository;
import finance.uc_project.repository.chefs.motDuChefRepository;
import finance.uc_project.repository.chefs.photoRepository;

@Service
public class creationChefService {

    @Autowired
    private infoBaseRepository chefRepository;

    @Autowired
    private photoRepository chefPhotoRepository;

    @Autowired
    private attributionRepository chefAttributionRepository;

    @Autowired
    private motDuChefRepository chefMotDuChefRepository;

    @Transactional
    public infoBase createChef(infoBase chef, MultipartFile photo, List<String> attributions, List<String> motsDuChef) {
        // Set the createdAt field
        chef.setCreatedAt(LocalDateTime.now());

        // Save the chef entity
        infoBase savedChef = chefRepository.save(chef);

        // Save the photo
        photo chefPhoto = new photo();
        chefPhoto.setChef(savedChef);
        try {
            chefPhoto.setPhoto(photo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        chefPhotoRepository.save(chefPhoto);

        // Save the attributions
        int numeroAttribution = 1;
        for (String attribution : attributions) {
            attribution chefAttribution = new attribution();
            chefAttribution.setChef(savedChef);
            chefAttribution.setAttribution(attribution);
            chefAttribution.setNumeroAttribution(numeroAttribution++);
            chefAttributionRepository.save(chefAttribution);
        }

        // Save the mots du chef
        int numeroMotDuChef = 1;
        for (String motDuChef : motsDuChef) {
            motDuChef chefMotDuChef = new motDuChef();
            chefMotDuChef.setChef(savedChef);
            chefMotDuChef.setParagraphe(motDuChef);
            chefMotDuChef.setNumeroParagraphe(numeroMotDuChef++);
            chefMotDuChefRepository.save(chefMotDuChef);
        }

        return savedChef;
    }


    @Transactional
    public infoBase updateChef(String ancienContact, infoBase chef, MultipartFile photo, List<String> attributions, List<String> motsDuChef) {
        chef.setUpdatedAt(LocalDateTime.now());

        infoBase chefToUpdate = chefRepository.findByContact(ancienContact).orElseThrow(() -> new IllegalArgumentException("Chef not found"));

        chefToUpdate.setNom(chef.getNom());
        chefToUpdate.setPrenoms(chef.getPrenoms());
        chefToUpdate.setEmail(chef.getEmail());
        chefToUpdate.setContact(chef.getContact());
        chefToUpdate.setTypeChef(chef.getTypeChef());
        chefToUpdate.setSousType(chef.getSousType());
        chefToUpdate.setUpdatedAt(LocalDateTime.now());

        chefRepository.save(chefToUpdate);

        photo chefPhoto = chefPhotoRepository.findByChef(chefToUpdate);
        if (chefPhoto != null) {
            try {
                chefPhoto.setPhoto(photo.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            chefPhotoRepository.save(chefPhoto);
        }

        chefAttributionRepository.deleteByChef(chefToUpdate);
        int numeroAttribution = 1;
        for (String attribution : attributions) {
            attribution chefAttribution = new attribution();
            chefAttribution.setChef(chefToUpdate);
            chefAttribution.setAttribution(attribution);
            chefAttribution.setNumeroAttribution(numeroAttribution++);
            chefAttributionRepository.save(chefAttribution);
        }

        chefMotDuChefRepository.deleteByChef(chefToUpdate);
        int numeroMotDuChef = 1;
        for (String motDuChef : motsDuChef) {
            motDuChef chefMotDuChef = new motDuChef();
            chefMotDuChef.setChef(chefToUpdate);
            chefMotDuChef.setParagraphe(motDuChef);
            chefMotDuChef.setNumeroParagraphe(numeroMotDuChef++);
            chefMotDuChefRepository.save(chefMotDuChef);
        }

        return chefToUpdate;
    }
    
}

