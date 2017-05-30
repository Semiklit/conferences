package ru.nikitasemiklit.diploma.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import ru.nikitasemiklit.diploma.R;
import ru.nikitasemiklit.diploma.model.Conference;
import ru.nikitasemiklit.diploma.model.ConferenceLab;
import ru.nikitasemiklit.diploma.model.Section;
import ru.nikitasemiklit.diploma.model.SectionLab;

/**
 * Created by nikitasemiklit1 on 02.04.17.
 */

public class ConferenceDetailActivity extends AppCompatActivity {

    static OkHttpClient sClient = new OkHttpClient.Builder().build();

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
                Intent i = new Intent(getApplicationContext(), SectionTableActivity.class);
                i.putExtra(ConferenceListActivity.EXTRA_CONFERENCE_ID, conferenceId);
                startActivity(i);
            }
        });

        Runnable task = new Runnable(){

            @Override
            public void run() {

                String response = "";

                Request request = new Request.Builder()
                        .url("")
                        .get()
                        .build();

                try {
                    response = sClient.newCall(request)
                            .execute()
                            .body().string();
                    final List<Section> sectionList = new ArrayList<>();
                    JsonArray array = new JsonParser().parse(response).getAsJsonArray();
                    for (JsonElement element : array){
                        Section section = new Gson().fromJson(element, Section.class);
                        sectionList.add(section);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ConferenceDetailActivity.this, "Данные обновлены", Toast.LENGTH_SHORT).show();
                        }
                    });

                    final SectionLab sectionLab = SectionLab.get(getApplicationContext());

                    sectionLab.addSections(sectionList, conferenceId);

                } catch (IOException e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ConferenceDetailActivity.this, "Отсутсвует соединение с инетрентом", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        };

        Thread thread = new Thread(task);
        thread.start();

    }
}
