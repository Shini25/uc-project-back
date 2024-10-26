package finance.uc_project.repository.chefs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import finance.uc_project.model.chefs.attribution;
import finance.uc_project.model.chefs.infoBase;

@Repository
public interface attributionRepository extends JpaRepository<attribution, Long> {
    void deleteByChef(infoBase chef);

    // New method to find attributions by chef's contact
    List<attribution> findByChefContact(String contact);
}