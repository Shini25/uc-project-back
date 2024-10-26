package finance.uc_project.model.courriers;

import finance.uc_project.enums.courrier.AccesReserveType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "acces_reserve")
public class AccesReserve extends Courrier {
    
    @Enumerated(EnumType.STRING)
    @Column(name = "acces_reserve_type", nullable = false)
    private AccesReserveType accesReserveType;

    // Getters and Setters
    public AccesReserveType getAccesReserveType() {
        return accesReserveType;
    }

    public void setType(AccesReserveType accesReserveType) {
        this.accesReserveType = accesReserveType;
    }
}
