package finance.uc_project.model.courriers;

import finance.uc_project.enums.courrier.ActiviteType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "activite")
public class Activite extends Courrier {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ActiviteType type;

    // Getters and Setters
    public ActiviteType getType() {
        return type;
    }

    public void setType(ActiviteType type) {
        this.type = type;
    }
}
