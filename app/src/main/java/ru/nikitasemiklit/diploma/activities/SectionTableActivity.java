package ru.nikitasemiklit.diploma.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import ru.nikitasemiklit.diploma.model.Section;
import ru.nikitasemiklit.diploma.model.SectionLab;

import static ru.nikitasemiklit.diploma.activities.LoginActivity.API_KEY;

/**
 * Created by nikitasemiklit1 on 31.05.17.
 */

public class SectionTableActivity extends AppCompatActivity {

    UUID mConferenceID;
    RecyclerView mRecyclerView;

    OkHttpClient sClient;

    public static final String EXTRA_SECTION_ID = "ru.nikitasemiklit.susu_conference.section_id";

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_section_table);
        setTitle("Секции");

        mConferenceID = (UUID) getIntent().getSerializableExtra(ConferenceListActivity.EXTRA_CONFERENCE_ID);

        final SectionLab sectionLab = SectionLab.get(getApplicationContext());
        final SectionAdapter adapter = new SectionAdapter(sectionLab.getSections(mConferenceID));

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
                            adapter.setConferences(sectionList);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(SectionTableActivity.this, "Данные обновлены", Toast.LENGTH_SHORT).show();
                        }
                    });

                    sectionLab.addSections(sectionList, mConferenceID);

                } catch (IOException e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SectionTableActivity.this, "Отсутсвует соединение с инетрентом", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        };

        Thread thread = new Thread(task);
        thread.start();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_section_list);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }


    private class SectionAdapter extends RecyclerView.Adapter<SectionHolder>{

        private List<Section> mSections;

        public SectionAdapter(List<Section> sections){
            mSections = sections;
        }

        @Override
        public SectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View view  = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new SectionHolder(view);
        }

        @Override
        public void onBindViewHolder(SectionHolder holder, final int position) {
            final Section section = mSections.get(position);
            holder.mTitleTextView.setText(section.getTitle());
            holder.mTitleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), TimeTableActivity.class);
                    i.putExtra(EXTRA_SECTION_ID, section.getSectionId());
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mSections.size();
        }

        public void setConferences(List<Section> sections){
            mSections = sections;
        }
    }

    private class SectionHolder extends RecyclerView.ViewHolder{

        public TextView mTitleTextView;

        public SectionHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
    }
}
