package ru.nikitasemiklit.diploma.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.nikitasemiklit.diploma.R;
import ru.nikitasemiklit.diploma.model.Section;
import ru.nikitasemiklit.diploma.model.SectionLab;
import ru.nikitasemiklit.diploma.ui.fragments.ConferenceListFragment;

/**
 * Created by nikitasemiklit1 on 02.04.17.
 */

public class ReportRegistrationActivity extends AppCompatActivity {

    EditText mEditTextReportName;
    Spinner mSectionSpinner;


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_registration);
        setTitle("Регистрация доклада");

        final UUID conferenceId = (UUID) getIntent().getSerializableExtra(ConferenceListFragment.EXTRA_CONFERENCE_ID);

        final SectionLab mSectionLab = SectionLab.get(getApplicationContext());
        final List<Section> mSections = mSectionLab.getSections(conferenceId);

        mEditTextReportName = (EditText) findViewById(R.id.et_report_registration_name);

        mSectionSpinner = (Spinner) findViewById(R.id.sp_section_choose);

        List<String> sectionStrings = new ArrayList<>();

        for (Section section : mSections){
            sectionStrings.add(section.getTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, sectionStrings);

        mSectionSpinner.setAdapter(adapter);

        mSectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String)  adapterView.getItemAtPosition(i);
                for (Section section : mSections){
                    if (item == section.getTitle()){
                        return;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

}
