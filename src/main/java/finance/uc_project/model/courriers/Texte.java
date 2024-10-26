package finance.uc_project.model.courriers;

import finance.uc_project.enums.courrier.TextesType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "texte")
public class Texte extends Courrier {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TextesType type;

    // Getters and Setters
    public TextesType getType() {
        return type;
    }

    public void setType(TextesType type) {
        this.type = type;
    }
}
