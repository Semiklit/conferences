package ru.nikitasemiklit.diploma.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.UUID;

import ru.nikitasemiklit.diploma.R;
import ru.nikitasemiklit.diploma.model.Conference;
import ru.nikitasemiklit.diploma.model.ConferenceLab;

/**
 * Created by nikitasemiklit1 on 02.04.17.
 */

public class ConferenceDetailActivity extends AppCompatActivity {

    TextView mTitleView;
    TextView mDescView;
    Button mTimeTableButton;
    FloatingActionButton mNewReportButton;


    @Override
    protected void onCreate (Bundle savedInstancestate){
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_conference_detail);
        setTitle("Информация о конференции");
        mTitleView = (TextView) findViewById(R.id.tv_conference_detail_title);
        mDescView = (TextView) findViewById(R.id.tv_conference_detail_desc);

        final UUID conferenceId = (UUID) getIntent().getSerializableExtra(ConferenceListActivity.EXTRA_CONFERENCE_ID);
        Conference conference = ConferenceLab.get(getApplicationContext()).getConference(conferenceId);

        mTitleView.setText(conference.getTitle());
        mDescView.setText(conference.getDesc());

        mNewReportButton = (FloatingActionButton) findViewById(R.id.bt_conference_detail_new_report);
        mNewReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ReportRegistrationActivity.class);
                i.putExtra(ConferenceListActivity.EXTRA_CONFERENCE_ID, conferenceId);
                startActivity(i);
            }
        });

        mTimeTableButton = (Button) findViewById(R.id.bt_conference_detail_timetable);
        mTimeTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TimeTableActivity.class);
                i.putExtra(ConferenceListActivity.EXTRA_CONFERENCE_ID, conferenceId);
                startActivity(i);
            }
        });

    }
}
