package finance.uc_project.repository.chefs;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import finance.uc_project.enums.TypeChef;
import finance.uc_project.model.chefs.infoBase;

@Repository
public interface infoBaseRepository extends JpaRepository<infoBase, Long> {

    Optional<infoBase> findByContact(String contact);

    List<infoBase> findByTypeChef(TypeChef typeDeChef);

    void deleteByContact(String contact);

    Optional<infoBase> getByContact(String contact);

}