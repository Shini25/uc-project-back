package finance.uc_project.model.courriers;

import finance.uc_project.enums.courrier.LivretType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "livret")
public class Livret extends Courrier {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private LivretType type;


    // Getters and Setters
    public LivretType getType() {
        return type;
    }

    public void setType(LivretType type) {
        this.type = type;
    }
}
