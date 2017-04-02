package ru.nikitasemiklit.diploma.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import ru.nikitasemiklit.diploma.R;

public class ConferenceListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_conference_list);

    }


    private class ConferenceAdapter extends RecyclerView.Adapter<ConferenceHolder>{

        @Override
        public ConferenceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(ConferenceHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    private class ConferenceHolder extends RecyclerView.ViewHolder{

        public ConferenceHolder(View itemView) {
            super(itemView);
        }
    }
}
