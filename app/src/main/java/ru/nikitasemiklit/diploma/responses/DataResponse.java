package ru.nikitasemiklit.diploma.responses;


import java.util.List;

import ru.nikitasemiklit.diploma.model.Conference;
import ru.nikitasemiklit.diploma.model.Report;
import ru.nikitasemiklit.diploma.model.Section;
import ru.nikitasemiklit.diploma.model.User;

public class DataResponse extends Response {
    private List<Conference> conferenceList;
    private List<Section> sections;
    private List<Report> reports;
    private List<User> users;

    public List<Conference> getConferenceList() {
        return conferenceList;
    }

    public List<Section> getSections() {
        return sections;
    }

    public List<Report> getReports() {
        return reports;
    }

    public List<User> getUsers() {
        return users;
    }
}
