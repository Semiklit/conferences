package ru.nikitasemiklit.diploma.requests;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.nikitasemiklit.diploma.model.Conference;
import ru.nikitasemiklit.diploma.model.Section;

public class CreateConfereceRequest {

    @SerializedName("conference")
    Conference conference;

    @SerializedName("sections_list")
    List<Section> sectionList;

    public CreateConfereceRequest(Conference conference, List<Section> sectionList) {
        this.conference = conference;
        this.sectionList = sectionList;
    }
}
