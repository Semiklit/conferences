package ru.nikitasemiklit.diploma.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.nikitasemiklit.diploma.R;
import ru.nikitasemiklit.diploma.managers.DataManager;
import ru.nikitasemiklit.diploma.model.Report;


/**
 * Created by Никита on 27.05.2017.
 */

public class TimeTableActivity extends AppCompatActivity {

    public static final String EXTRA_REPORT_ID = "ru.nikitasemiklit.susu_conference.report_id";
    UUID sectionId;
    RecyclerView mRecyclerView;
    ReportAdapter reportAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        setTitle("Секции");
        sectionId = (UUID) getIntent().getSerializableExtra(SectionTableActivity.EXTRA_SECTION_ID);
        reportAdapter = new ReportAdapter();
        mRecyclerView = findViewById(R.id.rv_report_list);
        mRecyclerView.setAdapter(reportAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Report> reports = new ArrayList<>();
        for (UUID reportId : DataManager.getSection(sectionId).getReportsIds()) {
            reports.add(DataManager.getReport(reportId));
        }
        reportAdapter.setReports(reports);
    }

    private class ReportAdapter extends RecyclerView.Adapter<ReportHolder> {

        private List<Report> mReports;

        @Override
        @NonNull
        public ReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
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

        void setReports(List<Report> reports) {
            mReports = reports;
            notifyDataSetChanged();
        }
    }

    private class ReportHolder extends RecyclerView.ViewHolder {

        TextView mTitleTextView;

        ReportHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
    }
}
