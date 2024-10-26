package finance.uc_project.model.meeting;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "info_meeting_base")
public class InfoMeetingBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "objet", nullable = false)
    private String objet;

    @Column(name = "meetingDate", nullable = false)
    private LocalDateTime meetingDate;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "meetingType", nullable = false)
    private String meetingType;

    @Column(name = "attendanceSheet", nullable = true)
    private byte[] attendanceSheet;

    @Column(name = "fileType", nullable = true)
    private String fileType;

    @Column(name = "ModificationDate", nullable = true)
    private LocalDateTime ModificationDate;

    @Column(name = "modifyby", nullable = true)
    private String modifyby;

    @Column(name = "addby", nullable = true)
    private String addby;

    @Column(name = "reminder", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean reminder;

    @Column(name = "reminderDate", nullable = true)
    private LocalDateTime reminderDate;

    @Column(name = "reminderaddby", nullable = true)
    private String reminderaddby;

    @OneToMany(mappedBy = "infoMeetingBase", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Logistics> logistics;

    @OneToMany(mappedBy = "infoMeetingBase", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MeetingOrganizer> meetingOrganizers;

    @OneToMany(mappedBy = "infoMeetingBase", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MeetingParticipant> meetingParticipants;

    @OneToMany(mappedBy = "infoMeetingBase", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MeetingObservation> meetingObservations;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(LocalDateTime meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public List<Logistics> getLogistics() {
        return logistics;
    }

    public void setLogistics(List<Logistics> logistics) {
        this.logistics = logistics;
    }

    public List<MeetingOrganizer> getMeetingOrganizers() {
        return meetingOrganizers;
    }

    public void setMeetingOrganizers(List<MeetingOrganizer> meetingOrganizers) {
        this.meetingOrganizers = meetingOrganizers;
    }

    public List<MeetingParticipant> getMeetingParticipants() {
        return meetingParticipants;
    }

    public void setMeetingParticipants(List<MeetingParticipant> meetingParticipants) {
        this.meetingParticipants = meetingParticipants;
    }

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }

    public List<MeetingObservation> getMeetingObservations() {
        return meetingObservations;
    }

    public void setMeetingObservations(List<MeetingObservation> meetingObservations) {
        this.meetingObservations = meetingObservations;
    }

    public byte[] getAttendanceSheet() {
        return attendanceSheet;
    }

    public void setAttendanceSheet(byte[] attendanceSheet) {
        this.attendanceSheet = attendanceSheet;
    }

    public LocalDateTime getModificationDate() {
        return ModificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.ModificationDate = modificationDate;
    }

    public String getModifyby() {
        return modifyby;
    }

    public void setModifyby(String modifyby) {
        this.modifyby = modifyby;
    }

    public String getAddby() {
        return addby;
    }

    public void setAddby(String addby) {
        this.addby = addby;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Boolean getReminder() {
        return reminder;
    }

    public void setReminder(Boolean reminder) {
        this.reminder = reminder;
    }

    public LocalDateTime getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(LocalDateTime reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getReminderaddby() {
        return reminderaddby;
    }

    public void setReminderaddby(String reminderaddby) {
        this.reminderaddby = reminderaddby;
    }
    


}
