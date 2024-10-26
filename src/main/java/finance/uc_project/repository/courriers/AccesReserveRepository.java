package finance.uc_project.repository.courriers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import finance.uc_project.model.courriers.AccesReserve;

@Repository
public interface AccesReserveRepository extends JpaRepository<AccesReserve, Long> {
}
