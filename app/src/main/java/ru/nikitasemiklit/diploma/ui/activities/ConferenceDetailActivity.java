package ru.nikitasemiklit.diploma.ui.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;

import java.util.UUID;

import ru.nikitasemiklit.diploma.R;
import ru.nikitasemiklit.diploma.model.Conference;
import ru.nikitasemiklit.diploma.model.ConferenceLab;
import ru.nikitasemiklit.diploma.ui.fragments.ConferenceListFragment;

public class ConferenceDetailActivity extends AppCompatActivity {

    TextView mTitleView;
    TextView mDescView;
    Button mTimeTableButton;
    FloatingActionButton mNewReportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_detail);
        setTitle("Информация о конференции");
        mTitleView = findViewById(R.id.tv_conference_detail_title);
        mDescView = findViewById(R.id.tv_conference_detail_desc);


        final UUID conferenceId = (UUID) getIntent().getSerializableExtra(ConferenceListFragment.EXTRA_CONFERENCE_ID);
        Conference conference = ConferenceLab.get(this).getConference(conferenceId);

        mTitleView.setText(conference.getTitle());
        mDescView.setText(conference.getDesc());

        FloatingActionMenu fabMenu = findViewById(R.id.fab_menu);
        fabMenu.setClosedOnTouchOutside(true);
        com.github.clans.fab.FloatingActionButton fabTemplate = findViewById(R.id.fab_new_conference);
        Drawable addFabIcon = getResources().getDrawable(R.drawable.ic_newspaper);
        fabTemplate.setImageDrawable(addFabIcon);
        fabTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConferenceDetailActivity.this, ReportRegistrationActivity.class);
                i.putExtra(ConferenceListFragment.EXTRA_CONFERENCE_ID, conferenceId);
                startActivity(i);
            }
        });

        mTimeTableButton = findViewById(R.id.bt_conference_detail_timetable);
        mTimeTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConferenceDetailActivity.this, SectionTableActivity.class);
                i.putExtra(ConferenceListFragment.EXTRA_CONFERENCE_ID, conferenceId);
                startActivity(i);
            }
        });
    }
}
