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
import ru.nikitasemiklit.diploma.managers.DataManager;
import ru.nikitasemiklit.diploma.model.Section;
import ru.nikitasemiklit.diploma.ui.fragments.ConferenceListFragment;

public class ReportRegistrationActivity extends AppCompatActivity {

    final List<Section> mSections = new ArrayList<>();
    EditText mEditTextReportName;
    Spinner mSectionSpinner;
    UUID conferenceId;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_registration);
        setTitle("Регистрация доклада");
        conferenceId = (UUID) getIntent().getSerializableExtra(ConferenceListFragment.EXTRA_CONFERENCE_ID);
        mEditTextReportName = findViewById(R.id.et_report_registration_name);
        mSectionSpinner = findViewById(R.id.sp_section_choose);
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item);
        mSectionSpinner.setAdapter(adapter);
        mSectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) adapterView.getItemAtPosition(i);
                for (Section section : mSections) {
                    if (item.equals(section.getTitle())) {
                        return;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSections.clear();
        for (UUID sectionId : DataManager.getConference(conferenceId).getSectionsIds()) {
            mSections.add(DataManager.getSection(sectionId));
        }
        List<String> sectionStrings = new ArrayList<>();

        for (Section section : mSections) {
            sectionStrings.add(section.getTitle());
        }
        adapter.clear();
        adapter.addAll(sectionStrings);
    }
}
