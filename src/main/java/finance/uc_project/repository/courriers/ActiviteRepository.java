package finance.uc_project.repository.courriers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import finance.uc_project.model.courriers.Activite;

@Repository
public interface ActiviteRepository extends JpaRepository<Activite, Long> {

    List<Activite> findByDateInsertionBetween(LocalDate startDate, LocalDate endDate);

    default List<Activite> findByDay(LocalDate date) {
        return findByDateInsertionBetween(date.atStartOfDay().toLocalDate(), date.plusDays(1).atStartOfDay().toLocalDate());
    }

    default List<Activite> findByMonth(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);
        return findByDateInsertionBetween(startDate, endDate);
    }

    default List<Activite> findByQuarter(int year, int quarter) {
        int month = (quarter - 1) * 3 + 1;
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(3);
        return findByDateInsertionBetween(startDate, endDate);
    }

    default List<Activite> findBySemester(int year, int semester) {
        int month = (semester - 1) * 6 + 1;
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(6);
        return findByDateInsertionBetween(startDate, endDate);
    }

    default List<Activite> findByWeek(LocalDate date) {
        LocalDate startDate = date.with(java.time.DayOfWeek.MONDAY);
        LocalDate endDate = startDate.plusWeeks(1);
        return findByDateInsertionBetween(startDate, endDate);
    }
}
