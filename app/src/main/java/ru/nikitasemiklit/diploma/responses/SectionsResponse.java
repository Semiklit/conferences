package ru.nikitasemiklit.diploma.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.nikitasemiklit.diploma.model.Section;

public class SectionsResponse extends Response {

    @SerializedName("SectionsList")
    private List<Section> sections;

    public List<Section> getSections() {
        return sections;
    }
}
