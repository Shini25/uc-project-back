package finance.uc_project.service.meeting;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import finance.uc_project.dto.ParticipantOrganizerDTO;
import finance.uc_project.model.meeting.InfoMeetingBase;
import finance.uc_project.model.meeting.Logistics;
import finance.uc_project.model.meeting.MeetingObservation;
import finance.uc_project.model.meeting.MeetingOrganizer;
import finance.uc_project.model.meeting.MeetingParticipant;
import finance.uc_project.repository.meeting.InfoMeetingBaseRepository;
import finance.uc_project.repository.meeting.LogisticsRepository;
import finance.uc_project.repository.meeting.MeetingObservationRepository;
import finance.uc_project.repository.meeting.MeetingOrganizerRepository;
import finance.uc_project.repository.meeting.MeetingParticipantRepository;


@Service
public class MeetingService {

    @Autowired
    private InfoMeetingBaseRepository infoMeetingBaseRepository;

    @Autowired
    private LogisticsRepository logisticsRepository;

    @Autowired
    private MeetingParticipantRepository meetingParticipantRepository;

    @Autowired
    private MeetingOrganizerRepository meetingOrganizerRepository;

    @Autowired
    private MeetingObservationRepository meetingObservationRepository;

    @Transactional
    public InfoMeetingBase meetingScheduling(LocalDateTime meetingDate, String location, String objet, String meetingType, List<String> logistics, List<ParticipantOrganizerDTO> organizersMail, List<ParticipantOrganizerDTO> participantsMail , List<String> meetingObservation, String addBy) {
        // Create and save InfoMeetingBase
        InfoMeetingBase meeting = new InfoMeetingBase();
        meeting.setDateCreation(LocalDateTime.now());
        meeting.setMeetingType(meetingType);
        meeting.setMeetingDate(meetingDate);
        meeting.setLocation(location);
        meeting.setObjet(objet);
        meeting.setAddby(addBy);
        meeting.setReminder(false);
        InfoMeetingBase savedMeeting = infoMeetingBaseRepository.save(meeting);

        // Add observation
        for (String meetingObservation1 : meetingObservation) {
            MeetingObservation meetingObservation2 = new MeetingObservation();
            meetingObservation2.setInfoMeetingBase(savedMeeting);
            meetingObservation2.setObservation(meetingObservation1);
            meetingObservationRepository.save(meetingObservation2);
        }

        // Add logistique
        for (String description : logistics) {
            Logistics logistics1 = new Logistics();
            logistics1.setDescription(description);
            logistics1.setInfoMeetingBase(savedMeeting);
            logisticsRepository.save(logistics1);
        }

        // Add responsables
        for (ParticipantOrganizerDTO entry : organizersMail) {
            String email = entry.getEmail();
            String name = entry.getName();
            MeetingOrganizer meetingOrganizer = new MeetingOrganizer();
            meetingOrganizer.setInfoMeetingBase(savedMeeting);
            meetingOrganizer.setEmail(email);
            meetingOrganizer.setName(name);
            meetingOrganizerRepository.save(meetingOrganizer);
        }

        // Add participants
        for (ParticipantOrganizerDTO entry : participantsMail) {
            String email = entry.getEmail();
            String name = entry.getName();
            MeetingParticipant meetingParticipant = new MeetingParticipant();
            meetingParticipant.setInfoMeetingBase(savedMeeting);
            meetingParticipant.setEmail(email);
            meetingParticipant.setName(name);
            meetingParticipantRepository.save(meetingParticipant);
        }

        return savedMeeting;
    }

    public List<InfoMeetingBase> getAllMeetings() {
        return infoMeetingBaseRepository.findAll();
    }

    public InfoMeetingBase getMeetingById(Long id) {
        return infoMeetingBaseRepository.findById(id).orElse(null);
    }

    public InfoMeetingBase updateMeeting(InfoMeetingBase meeting) {
        return infoMeetingBaseRepository.save(meeting);
    }

    public void deleteMeeting(Long id) {
        infoMeetingBaseRepository.deleteById(id);
    }

    public InfoMeetingBase addAttendanceSheet(Long id, MultipartFile attendanceSheet, String fileType, String modifyby) {
        InfoMeetingBase meeting = infoMeetingBaseRepository.findById(id).orElse(null);
        if (meeting != null) {
            try {
                byte[] attendanceSheetBytes = attendanceSheet.getBytes();
                meeting.setAttendanceSheet(attendanceSheetBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            meeting.setFileType(fileType);
            meeting.setModifyby(modifyby);
            meeting.setModificationDate(LocalDateTime.now());
            return infoMeetingBaseRepository.save(meeting);
        }
        return null;
    }

    // update reminder
    public InfoMeetingBase updateReminder(Long id, String addby) {
        InfoMeetingBase meeting = infoMeetingBaseRepository.findById(id).orElse(null);
        if (meeting != null) {
            meeting.setReminder(true);
            meeting.setReminderaddby(addby);
            meeting.setReminderDate(LocalDateTime.now());
            return infoMeetingBaseRepository.save(meeting);
        }
        return null;
    }
}
