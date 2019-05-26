package ru.nikitasemiklit.diploma.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nikitasemiklit.diploma.App;
import ru.nikitasemiklit.diploma.R;
import ru.nikitasemiklit.diploma.model.Conference;
import ru.nikitasemiklit.diploma.model.ConferenceLab;
import ru.nikitasemiklit.diploma.responses.ConferencesResponse;

public class ConferenceListActivity extends Activity {

    public static final String EXTRA_CONFERENCE_ID = "ru.nikitasemiklit.android.susu_conference.conference_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_list);
        setTitle("Список конференций");

        final ConferenceLab conferenceLab = ConferenceLab.get(getApplicationContext());
        final ConferenceAdapter adapter = new ConferenceAdapter(conferenceLab.getConferences());

        App.getClient().getConferences(App.getToken()).enqueue(new Callback<ConferencesResponse>() {
            @Override
            public void onResponse(Call<ConferencesResponse> call, Response<ConferencesResponse> response) {
                adapter.setConferences(response.body().getConferences());
                Toast.makeText(ConferenceListActivity.this, "Данные обновлены", Toast.LENGTH_SHORT).show();
                conferenceLab.addConferences(response.body().getConferences());
            }

            @Override
            public void onFailure(Call<ConferencesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        RecyclerView mRecyclerView = findViewById(R.id.rv_conference_list);
        mRecyclerView.setAdapter(adapter);

        FloatingActionMenu fabMenu = findViewById(R.id.fab_menu);
        fabMenu.setClosedOnTouchOutside(true);
        FloatingActionButton fabTemplate = findViewById(R.id.fab_new_conference);
        fabTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConferenceListActivity.this, CreateConferenceActivity.class);
                startActivity(i);
            }
        });

    }


    private class ConferenceAdapter extends RecyclerView.Adapter<ConferenceHolder> {

        private List<Conference> mConferences;

        ConferenceAdapter(List<Conference> conferences) {
            mConferences = conferences;
        }

        @Override
        @NonNull
        public ConferenceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
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

        void setConferences(List<Conference> conferences) {
            mConferences = conferences;
            notifyDataSetChanged();
        }
    }

    private class ConferenceHolder extends RecyclerView.ViewHolder {

        TextView mTitleTextView;

        ConferenceHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
    }
}
