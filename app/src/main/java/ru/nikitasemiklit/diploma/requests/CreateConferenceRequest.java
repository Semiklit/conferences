package ru.nikitasemiklit.diploma.requests;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.nikitasemiklit.diploma.model.Conference;
import ru.nikitasemiklit.diploma.model.Section;

public class CreateConferenceRequest {

    @SerializedName("conference")
    private Conference conference;

    @SerializedName("sections_list")
    private List<Section> sectionList;

    public CreateConferenceRequest(Conference conference, List<Section> sectionList) {
        this.conference = conference;
        this.sectionList = sectionList;
    }
}
