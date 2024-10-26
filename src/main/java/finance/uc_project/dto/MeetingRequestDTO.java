package finance.uc_project.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MeetingRequestDTO {
    private LocalDateTime dateMeeting;
    private String location;
    private String objet;
    private String meetingType;
    private List<String> logistics;
    private List<ParticipantOrganizerDTO> organizersMail;
    private List<ParticipantOrganizerDTO> participantsMail;
    private List<String> observations;
    private String addBy;

    // Getters et Setters

    public LocalDateTime getDateMeeting() {
        return dateMeeting;
    }

    public void setDateMeeting(LocalDateTime dateMeeting) {
        this.dateMeeting = dateMeeting;
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

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }

    public List<String> getLogistics() {
        return logistics;
    }

    public void setLogistics(List<String> logistics) {
        this.logistics = logistics;
    }

    public List<ParticipantOrganizerDTO> getOrganizersMail() {
        return organizersMail;
    }

    public void setOrganizersMail(List<ParticipantOrganizerDTO> organizersMail) {
        this.organizersMail = organizersMail;
    }

    public List<ParticipantOrganizerDTO> getParticipantsMail() {
        return participantsMail;
    }

    public void setParticipantsMail(List<ParticipantOrganizerDTO> participantsMail) {
        this.participantsMail = participantsMail;
    }

    public List<String> getObservations() {
        return observations;
    }

    public void setObservations(List<String> observations) {
        this.observations = observations;
    }

    public String getAddBy() {
        return addBy;
    }

    public void setAddBy(String addBy) {
        this.addBy = addBy;
    }
}
