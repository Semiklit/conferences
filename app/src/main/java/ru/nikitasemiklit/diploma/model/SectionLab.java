package ru.nikitasemiklit.diploma.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by nikitasemiklit1 on 23.05.17.
 */

public class SectionLab {

    public List<Section> getSections() {
        return mSections;
    }

    private List<Section> mSections;

    private static SectionLab sSectionLab;

    public static SectionLab get(Context context){
        if (sSectionLab == null){
            sSectionLab = new SectionLab(context);
        }
        return sSectionLab;
    }

    private SectionLab(Context context){
        mSections = new ArrayList<>();
        List <Conference> mConferences = ConferenceLab.get(context).getConferences();
        Iterator<Conference> iterator = mConferences.iterator();
        for (int i = 0; i < 500; i++){
            Section section = new Section();
            section.setConferenceId(UUID.randomUUID());
            if (iterator.hasNext()) {
                section.setConferenceId(iterator.next().getConferenceId());
            } else {
                iterator = mConferences.iterator();
                section.setConferenceId(iterator.next().getConferenceId());
            }
            section.setTitle("Section #" + i);
            mSections.add(section);
        }
    };

    public Section getSection(UUID id){
        for (Section section : mSections){
            if (section.getSectionId().equals(id)){
                return section;
            }
        }
        return null;
    }
}
