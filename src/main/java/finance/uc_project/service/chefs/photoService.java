package finance.uc_project.service.chefs;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finance.uc_project.model.chefs.photo;
import finance.uc_project.repository.chefs.infoBaseRepository;
import finance.uc_project.repository.chefs.photoRepository;

@Service
public class photoService {

    @Autowired
    private photoRepository ucPhotoRepository;

    @Autowired
    private infoBaseRepository ucRepository;

    public List<photo> getAllPhotos() {
        return ucPhotoRepository.findAll();
    }

    public Optional<photo> getPhotoById(Long id) {
        return ucPhotoRepository.findById(id);
    }

    public photo createPhoto(photo photo) {
        return ucPhotoRepository.save(photo);
    }

    public photo updatePhoto(photo photo) {
        return ucPhotoRepository.save(photo);
    }

    public void deletePhoto(Long id) {
        ucPhotoRepository.deleteById(id);
    }

    public List<String> getPhotosByChef(Long chefId) {
        return ucRepository.findById(chefId)
                .map(chef -> chef.getPhotos().stream()
                        .map(photo -> Base64.getEncoder().encodeToString(photo.getPhoto()))
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    public photo addPhotoToChef(Long chefId, photo photo) {
        return ucRepository.findById(chefId).map(chef -> {
            photo.setChef(chef);
            return ucPhotoRepository.save(photo);
        }).orElse(null);
    }

    public String getPhotoBase64ById(Long id) {
        return ucPhotoRepository.findById(id)
                .map(photo -> Base64.getEncoder().encodeToString(photo.getPhoto()))
                .orElse(null);
    }

    public List<String> getAllPhotosBase64() {
        return ucPhotoRepository.findAll().stream()
                .map(photo -> Base64.getEncoder().encodeToString(photo.getPhoto()))
                .collect(Collectors.toList());
    }
}