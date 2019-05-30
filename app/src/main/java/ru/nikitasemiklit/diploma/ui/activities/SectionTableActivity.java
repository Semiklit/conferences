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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.nikitasemiklit.diploma.R;
import ru.nikitasemiklit.diploma.managers.DataManager;
import ru.nikitasemiklit.diploma.model.Section;
import ru.nikitasemiklit.diploma.ui.fragments.ConferenceListFragment;

public class SectionTableActivity extends AppCompatActivity {

    public static final String EXTRA_SECTION_ID = "ru.nikitasemiklit.susu_conference.section_id";
    UUID mConferenceID;
    RecyclerView mRecyclerView;
    SectionAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_table);
        setTitle("Секции");
        mConferenceID = (UUID) getIntent().getSerializableExtra(ConferenceListFragment.EXTRA_CONFERENCE_ID);
        adapter = new SectionAdapter();
        mRecyclerView = findViewById(R.id.rv_section_list);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Section> mSections = new ArrayList<>();
        for (UUID sectionId : DataManager.getConference(mConferenceID).getSectionsIds()) {
            mSections.add(DataManager.getSection(sectionId));
        }
        adapter.setSections(mSections);
    }

    private class SectionAdapter extends RecyclerView.Adapter<SectionHolder> {

        private List<Section> mSections;

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
            notifyDataSetChanged();
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
