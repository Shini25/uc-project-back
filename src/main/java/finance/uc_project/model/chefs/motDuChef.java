package finance.uc_project.model.chefs;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "uc_mot_du_chef")
public class motDuChef {
    @Id
    @SequenceGenerator(name = "uc_mot_du_chef_sequence", sequenceName = "uc_mot_du_chef_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uc_mot_du_chef_sequence")
    private Long id;

    @Lob
    @Column(name = "paragraphe")
    private String paragraphe;

    @Column(name = "numero_paragraphe")
    private Integer numeroParagraphe;

    @ManyToOne
    @JoinColumn(name = "uc_id")
    @JsonIgnore
    private infoBase chef;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParagraphe() {
        return paragraphe;
    }

    public void setParagraphe(String paragraphe) {
        this.paragraphe = paragraphe;
    }

    public Integer getNumeroParagraphe() {
        return numeroParagraphe;
    }

    public void setNumeroParagraphe(Integer numeroParagraphe) {
        this.numeroParagraphe = numeroParagraphe;
    }

    public infoBase getChef() {
        return chef;
    }

    public void setChef(infoBase chef) {
        this.chef = chef;
    }
}