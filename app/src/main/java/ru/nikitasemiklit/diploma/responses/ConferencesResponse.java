package ru.nikitasemiklit.diploma.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.nikitasemiklit.diploma.model.Conference;

public class ConferencesResponse extends Response {

    @SerializedName("ConferencesList")
    private List<Conference> conferences;

    public List<Conference> getConferences() {
        return conferences;
    }
}
