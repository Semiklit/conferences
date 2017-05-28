package ru.nikitasemiklit.diploma.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by nikitasemiklit1 on 02.04.17.
 */

public class ConferenceLab {

    public List<Conference> getConferences() {
        return mConferences;
    }

    private List<Conference> mConferences;

    private static ConferenceLab sConferenceLab;

    public static ConferenceLab get(Context context){
        if (sConferenceLab == null){
            sConferenceLab = new ConferenceLab(context);
        }
        return sConferenceLab;
    }

    private ConferenceLab(Context context){
        mConferences = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            Conference conference = new Conference();
            conference.setConferenceId(UUID.randomUUID());
            conference.setTitle("Conference #" + i);
            conference.setDesc("Some information about this conference");
            mConferences.add(conference);
        }
    };

    public Conference getConference(UUID id){
        for (Conference conference : mConferences){
            if (conference.getConferenceId().equals(id)){
                return conference;
            }
        }
        return null;
    }
}
