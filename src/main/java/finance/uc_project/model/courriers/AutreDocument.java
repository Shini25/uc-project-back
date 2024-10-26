package finance.uc_project.model.courriers;

import finance.uc_project.enums.courrier.AutreDocumentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "autre_document")
public class AutreDocument extends Courrier {
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AutreDocumentType type;

    // Getters and Setters
    public AutreDocumentType getType() {
        return type;
    }

    public void setType(AutreDocumentType type) {
        this.type = type;
    }
}
