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
@Table(name = "uc_attribution")
public class attribution {
    @Id
    @SequenceGenerator(name = "uc_attribution_sequence", sequenceName = "uc_attribution_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uc_attribution_sequence")
    private Long id;

    @Lob
    @Column(name = "attribution")
    private String attribution;

    @Column(name = "numero_attribution")
    private Integer numeroAttribution;

    @ManyToOne
    @JoinColumn(name = "uc_id")
    @JsonIgnore
    private infoBase chef;


    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public infoBase getChef() {
        return chef;
    }

    public void setChef(infoBase chef) {
        this.chef = chef;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public Integer getNumeroAttribution() {
        return numeroAttribution;
    }

    public void setNumeroAttribution(Integer numeroAttribution) {
        this.numeroAttribution = numeroAttribution;
    }
}