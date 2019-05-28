package ru.nikitasemiklit.diploma.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import ru.nikitasemiklit.diploma.model.Section;
import ru.nikitasemiklit.diploma.model.SectionLab;
import ru.nikitasemiklit.diploma.responses.SectionsResponse;
import ru.nikitasemiklit.diploma.ui.fragments.ConferenceListFragment;

public class SectionTableActivity extends AppCompatActivity {

    UUID mConferenceID;
    RecyclerView mRecyclerView;

    public static final String EXTRA_SECTION_ID = "ru.nikitasemiklit.susu_conference.section_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_section_table);
        setTitle("Секции");

        mConferenceID = (UUID) getIntent().getSerializableExtra(ConferenceListFragment.EXTRA_CONFERENCE_ID);

        final SectionLab sectionLab = SectionLab.get(getApplicationContext());
        final SectionAdapter adapter = new SectionAdapter(sectionLab.getSections(mConferenceID));

        App.getClient().getSections(App.getToken(), mConferenceID).enqueue(new Callback<SectionsResponse>() {
            @Override
            public void onResponse(Call<SectionsResponse> call, Response<SectionsResponse> response) {
                adapter.setSections(response.body().getSections());
                adapter.notifyDataSetChanged();
                sectionLab.addSections(response.body().getSections(), mConferenceID);
                Toast.makeText(SectionTableActivity.this, "Данные обновлены", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SectionsResponse> call, Throwable t) {

            }
        });

        mRecyclerView = findViewById(R.id.rv_section_list);
        mRecyclerView.setAdapter(adapter);
    }


    private class SectionAdapter extends RecyclerView.Adapter<SectionHolder> {

        private List<Section> mSections;

        SectionAdapter(List<Section> sections) {
            mSections = sections;
        }

        @Override
        @NonNull
        public SectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new SectionHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SectionHolder holder, final int position) {
            final Section section = mSections.get(position);
            holder.mTitleTextView.setText(section.getTitle());
            holder.mTitleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(SectionTableActivity.this, TimeTableActivity.class);
                    i.putExtra(EXTRA_SECTION_ID, section.getSectionId());
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mSections.size();
        }

        void setSections(List<Section> sections) {
            mSections = sections;
        }
    }

    private class SectionHolder extends RecyclerView.ViewHolder {

        TextView mTitleTextView;

        SectionHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
    }
}
