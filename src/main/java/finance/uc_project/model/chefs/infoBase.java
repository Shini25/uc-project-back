package finance.uc_project.model.chefs;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import finance.uc_project.enums.TypeChef;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "chefs")
public class infoBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenoms", nullable = false)
    private String prenoms;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "contact", unique = true, nullable = false)
    private String contact;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_chef", nullable = false)
    private TypeChef typeChef;

    @Column(name = "sous_type", nullable = true)
    private  String sousType;

    @OneToMany(mappedBy = "chef")
    @JsonIgnore
    private List<photo> photos;

    @OneToMany(mappedBy = "chef")
    @JsonIgnore
    private List<attribution> attributions;

    @OneToMany(mappedBy = "chef")
    @JsonIgnore
    private List<motDuChef> motDuChefs;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public TypeChef getTypeChef () {
        return typeChef;
    }

    public void setTypeChef (TypeChef typeChef) {
        this.typeChef = typeChef;
    }

    public String getSousType () {
        return sousType;
    }

    public void setSousType (String sousType ) {
        this.sousType = sousType;
    }

    public List<photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<photo> photos) {
        this.photos = photos;
    }

    public List<attribution> getAttributions() {
        return attributions;
    }

    public void setAttributions(List<attribution> attributions) {
        this.attributions = attributions;
    }

    public List<motDuChef> getMotDuChefs() {
        return motDuChefs;
    }

    public void setMotDuChefs(List<motDuChef> motDuChefs) {
        this.motDuChefs = motDuChefs;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
