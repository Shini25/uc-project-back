package finance.uc_project.model.courriers;

import java.time.LocalDateTime;

import finance.uc_project.enums.courrier.TypeDocument;
import finance.uc_project.model.User_account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Courrier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_courrier", updatable = false, nullable = false)
    private Long idCourrier;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "date_insertion", nullable = false, updatable = false)
    private LocalDateTime dateInsertion;

    @Column(name = "contenue")
    private byte[] contenue;

    @Column(name = "type_contenue")
    private String typeContenue;

    @Column(name = "type_document")
    private TypeDocument typeDocument;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User_account user_account; 

    @PrePersist
    protected void onCreate() {
        dateInsertion = LocalDateTime.now();
    }

    @Column(name = "date_modification")
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }

    // Getters and Setters
    private LocalDateTime dateModification;


    // Getters and Setters
    public Long getIdCourrier() {
        return idCourrier;
    }

    public void setIdCourrier(Long idCourrier) {
        this.idCourrier = idCourrier;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
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

    public User_account getUser_account() {
        return user_account;
    }

    public void setUserId(User_account user_account) {
        this.user_account = user_account;
    }

    public TypeDocument getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(TypeDocument typeDocument) {
        this.typeDocument = typeDocument;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }
}

