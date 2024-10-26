package finance.uc_project.model.courriers;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "livret_audit")
public class LivretAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_audit_livret", updatable = false, nullable = false)
    private Long idAuditLivret;

    @Column(name = "id_courrier", nullable = false)
    private Long idCourrier;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "date_insertion", nullable = false)
    private LocalDateTime dateInsertion;

    @Column(name = "contenue")
    private byte[] contenue;

    @Column(name = "type_contenue")
    private String typeContenue;

    @Column(name = "type_document")
    private String typeDocument;

    @Column(name = "audit_timestamp", nullable = false)
    private LocalDateTime auditTimestamp;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "type", nullable = false)
    private String type;



    // Getters and Setters
    public Long getIdAuditLivret() {
        return idAuditLivret;
    }

    public void setIdAuditLivret(Long idAuditLivret) {
        this.idAuditLivret = idAuditLivret;
    }
    
    public Long getIdCourrier() {
        return idCourrier;
    }

    public void setIdCourrier(Long idCourrier) {
        this.idCourrier = idCourrier;
    }

    public LocalDateTime getDateInsertion() {
        return dateInsertion;
    }

    public void setDateInsertion(LocalDateTime dateInsertion) {
        this.dateInsertion = dateInsertion;
    }

    public byte[] getContenue() {
        return contenue;
    }

    public void setContenue(byte[] contenue) {
        this.contenue = contenue;
    }

    public String getTypeContenue() {
        return typeContenue;
    }

    public void setTypeContenue(String typeContenue) {
        this.typeContenue = typeContenue;
    }

    public String getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
    }

    public LocalDateTime getAuditTimestamp() {
        return auditTimestamp;
    }

    public void setAuditTimestamp(LocalDateTime auditTimestamp) {
        this.auditTimestamp = auditTimestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

}
