package ru.nikitasemiklit.diploma.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import ru.nikitasemiklit.diploma.R;
import ru.nikitasemiklit.diploma.model.Conference;
import ru.nikitasemiklit.diploma.model.ConferenceLab;

public class ConferenceListActivity extends Activity {

    public static final String EXTRA_CONFERENCE_ID = "ru.nikitasemiklit.android.susu_conference.conference_id";

    static OkHttpClient sClient = new OkHttpClient.Builder().build();

    private RecyclerView mRecyclerView;

    private String mToken;

    List<Conference> mConferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_list);
        setTitle("Список конференций");

        mToken = "123";

        final ConferenceLab conferenceLab = ConferenceLab.get(getApplicationContext());
        final ConferenceAdapter adapter = new ConferenceAdapter(conferenceLab.getConferences());

        Runnable task = new Runnable() {

            @Override
            public void run() {

                String response = "";

                Request request = new Request.Builder()
                        .url("https://gist.githubusercontent.com/Semiklit/d68cc57981232b41097c155b7afcb941/raw/6cbcc5655600b381414e892d2c9db942c8001245/Conferences.json")
                        .get()
                        .build();

                try {
                    response = sClient.newCall(request)
                            .execute()
                            .body().string();
                    final List<Conference> conferenceList = new ArrayList<>();
                    JsonArray array = new JsonParser().parse(response).getAsJsonArray();
                    for (JsonElement element : array) {
                        Conference conference = new Gson().fromJson(element, Conference.class);
                        conferenceList.add(conference);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setConferences(conferenceList);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(ConferenceListActivity.this, "Данные обновлены", Toast.LENGTH_SHORT).show();
                        }
                    });

                    conferenceLab.addConferences(conferenceList);

                } catch (IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ConferenceListActivity.this, "Отсутсвует соединение с инетрентом", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        };

        Thread thread = new Thread(task);
        thread.start();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_conference_list);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }


    private class ConferenceAdapter extends RecyclerView.Adapter<ConferenceHolder> {

        private List<Conference> mConferences;

        public ConferenceAdapter(List<Conference> conferences) {
            mConferences = conferences;
        }

        @Override
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

        public void setConferences(List<Conference> conferences) {
            mConferences = conferences;
        }
    }

    private class ConferenceHolder extends RecyclerView.ViewHolder {

        public TextView mTitleTextView;

        public ConferenceHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
    }
}
