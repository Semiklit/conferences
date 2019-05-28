package ru.nikitasemiklit.diploma.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nikitasemiklit.diploma.App;
import ru.nikitasemiklit.diploma.R;
import ru.nikitasemiklit.diploma.model.Report;
import ru.nikitasemiklit.diploma.model.ReportLab;
import ru.nikitasemiklit.diploma.responses.ReportsResponse;


/**
 * Created by Никита on 27.05.2017.
 */

public class TimeTableActivity extends AppCompatActivity {

    UUID mReportId;
    RecyclerView mRecyclerView;

    public static final String EXTRA_REPORT_ID = "ru.nikitasemiklit.susu_conference.report_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_time_table);
        setTitle("Секции");

        mReportId = (UUID) getIntent().getSerializableExtra(SectionTableActivity.EXTRA_SECTION_ID);

        final ReportLab reportLab = ReportLab.get(getApplicationContext());
        final ReportAdapter adapter = new ReportAdapter(reportLab.getReports(mReportId));

        App.getClient().getReports(App.getToken(), mReportId).enqueue(new Callback<ReportsResponse>() {
            @Override
            public void onResponse(Call<ReportsResponse> call, Response<ReportsResponse> response) {
                adapter.setConferences(response.body().getReports());
                adapter.notifyDataSetChanged();
                reportLab.addReports(response.body().getReports(), mReportId);
                Toast.makeText(TimeTableActivity.this, "Данные обновлены", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ReportsResponse> call, Throwable t) {

            }
        });

        mRecyclerView = findViewById(R.id.rv_report_list);
        mRecyclerView.setAdapter(adapter);
    }


    private class ReportAdapter extends RecyclerView.Adapter<ReportHolder> {

        private List<Report> mReports;

        public ReportAdapter(List<Report> reports) {
            mReports = reports;
        }

        @Override
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

        public void setConferences(List<Report> reports) {
            mReports = reports;
        }
    }

    private class ReportHolder extends RecyclerView.ViewHolder {

        public TextView mTitleTextView;

        public ReportHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
    }
}
