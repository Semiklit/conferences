package ru.nikitasemiklit.diploma.activities;

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
import ru.nikitasemiklit.diploma.model.Report;
import ru.nikitasemiklit.diploma.model.ReportLab;


/**
 * Created by Никита on 27.05.2017.
 */

public class TimeTableActivity extends AppCompatActivity {

    UUID mSectionID;
    RecyclerView mRecyclerView;

    OkHttpClient sClient;

    public static final String EXTRA_REPORT_ID = "ru.nikitasemiklit.susu_conference.report_id";

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_section_table);
        setTitle("Секции");

        mSectionID = (UUID) getIntent().getSerializableExtra(ConferenceListActivity.EXTRA_CONFERENCE_ID);

        final ReportLab reportLab = ReportLab.get(getApplicationContext());
        final ReportAdapter adapter = new ReportAdapter(reportLab.getReports(mSectionID));

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
                    final List<Report> reportList = new ArrayList<>();
                    JsonArray array = new JsonParser().parse(response).getAsJsonArray();
                    for (JsonElement element : array){
                        Report section = new Gson().fromJson(element, Report.class);
                        reportList.add(section);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setConferences(reportList);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(TimeTableActivity.this, "Данные обновлены", Toast.LENGTH_SHORT).show();
                        }
                    });

                    reportLab.addReports(reportList, mSectionID);

                } catch (IOException e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TimeTableActivity.this, "Отсутсвует соединение с инетрентом", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        };

        Thread thread = new Thread(task);
        thread.start();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_report_list);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }


    private class ReportAdapter extends RecyclerView.Adapter<ReportHolder>{

        private List<Report> mReports;

        public ReportAdapter(List<Report> reports){
            mReports = reports;
        }

        @Override
        public ReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View view  = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ReportHolder(view);
        }

        @Override
        public void onBindViewHolder(ReportHolder holder, final int position) {
            final Report report = mReports.get(position);
            holder.mTitleTextView.setText(report.getTitle());
            holder.mTitleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                }
            });
        }

        @Override
        public int getItemCount() {
            return mReports.size();
        }

        public void setConferences(List<Report> reports){
            mReports = reports;
        }
    }

    private class ReportHolder extends RecyclerView.ViewHolder{

        public TextView mTitleTextView;

        public ReportHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
    }
}
