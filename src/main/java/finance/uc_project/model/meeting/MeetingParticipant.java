package finance.uc_project.model.meeting;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "meeting_participant")
public class MeetingParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "info_meeting_base_id", nullable = false)
    private InfoMeetingBase infoMeetingBase;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = true)
    private String name;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InfoMeetingBase getInfoMeetingBase() {
        return infoMeetingBase;
    }

    public void setInfoMeetingBase(InfoMeetingBase infoMeetingBase) {
        this.infoMeetingBase = infoMeetingBase;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
