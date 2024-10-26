package finance.uc_project.repository.chefs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import finance.uc_project.model.chefs.infoBase;
import finance.uc_project.model.chefs.photo;
@Repository
public interface photoRepository extends JpaRepository<photo, Long> {
    photo findByChef(infoBase chef);
}
