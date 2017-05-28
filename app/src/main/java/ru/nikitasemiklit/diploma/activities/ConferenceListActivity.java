package ru.nikitasemiklit.diploma.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.nikitasemiklit.diploma.R;
import ru.nikitasemiklit.diploma.model.Conference;
import ru.nikitasemiklit.diploma.model.ConferenceLab;

public class ConferenceListActivity extends AppCompatActivity {

    public static final String EXTRA_CONFERENCE_ID = "ru.nikitasemiklit.android.susu_conference.conference_id";

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_list);

        ConferenceLab conferenceLab = ConferenceLab.get(getApplicationContext());
        List<Conference> conferences = conferenceLab.getConferences();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_conference_list);
        mRecyclerView.setAdapter(new ConferenceAdapter(conferences));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }


    private class ConferenceAdapter extends RecyclerView.Adapter<ConferenceHolder>{

        private List<Conference> mConferences;

        public ConferenceAdapter(List<Conference> conferences){
            mConferences = conferences;
        }

        @Override
        public ConferenceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View view  = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ConferenceHolder(view);
        }

        @Override
        public void onBindViewHolder(ConferenceHolder holder, final int position) {
            final Conference conference = mConferences.get(position);
            holder.mTitleTextView.setText(conference.getTitle());
            holder.mTitleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), ConferenceDetailActivity.class);
                    i.putExtra(EXTRA_CONFERENCE_ID, conference.getConferenceId());
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mConferences.size();
        }
    }

    private class ConferenceHolder extends RecyclerView.ViewHolder{

        public TextView mTitleTextView;

        public ConferenceHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
    }
}
