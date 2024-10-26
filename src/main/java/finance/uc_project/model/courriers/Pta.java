package finance.uc_project.model.courriers;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "pta")
public class Pta extends Courrier {

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "valide")
    private boolean valide;

    @Column(name = "sousType", nullable = true)
    private String sousType;

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public String getSousType() {
        return sousType;
    }

    public void setSousType(String sousType) {
        this.sousType = sousType;
    }
}
