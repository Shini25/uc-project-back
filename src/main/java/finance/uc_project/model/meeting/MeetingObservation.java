package finance.uc_project.model.meeting;

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
@Table(name = "meeting_observation")
public class MeetingObservation {

    @Id
    @SequenceGenerator(name = "meeting_observation_sequence", sequenceName = "meeting_observation_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meeting_observation_sequence")
    private Long id;

    @Lob
    @Column(name = "observation")
    private String observation;

    @Column(name = "numero_observation")
    private Integer numeroObservation;

    @ManyToOne
    @JoinColumn(name = "info_meeting_base_id")
    private InfoMeetingBase infoMeetingBase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public InfoMeetingBase getInfoMeetingBase() {
        return infoMeetingBase;
    }

    public void setInfoMeetingBase(InfoMeetingBase infoMeetingBase) {
        this.infoMeetingBase = infoMeetingBase;
    }

    public Integer getNumeroObservation() {
        return numeroObservation;
    }

    public void setNumeroObservation(Integer numeroObservation) {
        this.numeroObservation = numeroObservation;
    }
}
