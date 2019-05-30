package ru.nikitasemiklit.diploma.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ru.nikitasemiklit.diploma.model.Conference;
import ru.nikitasemiklit.diploma.model.Report;
import ru.nikitasemiklit.diploma.model.Section;
import ru.nikitasemiklit.diploma.model.User;
import ru.nikitasemiklit.diploma.responses.DataResponse;

public class DataManager {

    private static Map<UUID, Conference> conferenceMap = new HashMap<>();
    private static Map<UUID, Section> sectionMap = new HashMap<>();
    private static Map<UUID, Report> reportMap = new HashMap<>();
    private static Map<UUID, User> userMap = new HashMap<>();

    public static Conference getConference(UUID id) {
        return conferenceMap.get(id);
    }

    public static Section getSection(UUID id) {
        return sectionMap.get(id);
    }

    public static Report getReport(UUID id) {
        return reportMap.get(id);
    }

    public static User getUser(UUID id) {
        return userMap.get(id);
    }

    public static void setDataResponse(DataResponse dataResponse) {
        for (Conference conference : dataResponse.getConferenceList()) {
            conferenceMap.put(conference.getConferenceId(), conference);
        }
        for (Section section : dataResponse.getSections()) {
            sectionMap.put(section.getSectionId(), section);
        }
        for (Report report : dataResponse.getReports()) {
            reportMap.put(report.getReportId(), report);
        }
        for (User user : dataResponse.getUsers()) {
            userMap.put(user.getUserId(), user);
        }
    }

}
