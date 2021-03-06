package ru.nikitasemiklit.diploma.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nikitasemiklit.diploma.App;
import ru.nikitasemiklit.diploma.R;
import ru.nikitasemiklit.diploma.managers.DataManager;
import ru.nikitasemiklit.diploma.model.Conference;
import ru.nikitasemiklit.diploma.responses.DataResponse;
import ru.nikitasemiklit.diploma.ui.activities.ConferenceDetailActivity;
import ru.nikitasemiklit.diploma.ui.activities.ConferenceListActivity;
import ru.nikitasemiklit.diploma.ui.createConference.CreateConferenceActivity;

public class ConferenceListFragment extends Fragment {

    public static final String EXTRA_CONFERENCE_ID = "ru.nikitasemiklit.android.susu_conference.conference_id";
    private Context context;
    private ConferenceAdapter adapter;

    Callback<DataResponse> callback = new Callback<DataResponse>() {
        @Override
        public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
            if (response.body() != null) {
                DataManager.setDataResponse(response.body());
                adapter.setConferences(response.body().getConferenceList());
            }
        }

        @Override
        public void onFailure(Call<DataResponse> call, Throwable t) {
            t.printStackTrace();
        }
    };

    private int mode = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conference_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ConferenceListActivity.EXTRA_MODE)) {
            mode = getArguments().getInt(ConferenceListActivity.EXTRA_MODE);
        }

        adapter = new ConferenceAdapter();
        switch (mode) {
            case ConferenceListActivity.MODE_MY:
                App.getClient().getOwnedConferences(App.getToken()).enqueue(callback);
                break;
            case ConferenceListActivity.MODE_FAVORITE:
                App.getClient().getFavoriteConferences(App.getToken()).enqueue(callback);
                break;
            case ConferenceListActivity.MODE_AVAILABLE:
//                setTitle("Доступные мне");
            default:
                App.getClient().getData(App.getToken()).enqueue(callback);
        }


        RecyclerView mRecyclerView = view.findViewById(R.id.rv_conference_list);
        mRecyclerView.setAdapter(adapter);

        FloatingActionMenu fabMenu = view.findViewById(R.id.fab_menu);
        fabMenu.setClosedOnTouchOutside(true);
        FloatingActionButton fabTemplate = view.findViewById(R.id.fab_new_conference);
        Drawable addFabIcon = getResources().getDrawable(R.drawable.ic_meeting);
        fabTemplate.setImageDrawable(addFabIcon);
        fabTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CreateConferenceActivity.class);
                startActivity(i);
            }
        });

    }


    private class ConferenceAdapter extends RecyclerView.Adapter<ConferenceHolder> {

        private List<Conference> mConferences = new ArrayList<>();

        @Override
        @NonNull
        public ConferenceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.item_conference, parent, false);
            return new ConferenceHolder(view);
        }

        @Override
        public void onBindViewHolder(ConferenceHolder holder, final int position) {
            final Conference conference = mConferences.get(position);
            holder.setConference(conference);
        }

        @Override
        public int getItemCount() {
            return mConferences.size();
        }

        void setConferences(List<Conference> conferences) {
            mConferences.clear();
            mConferences.addAll(conferences);
            notifyDataSetChanged();
        }
    }

    private class ConferenceHolder extends RecyclerView.ViewHolder {

        TextView mTitleTextView;
        TextView mDescTextView;
        Conference conference;

        ConferenceHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.title);
            mDescTextView = itemView.findViewById(R.id.value);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, ConferenceDetailActivity.class);
                    i.putExtra(EXTRA_CONFERENCE_ID, conference.getConferenceId());
                    startActivity(i);
                }
            });
        }

        void setConference(Conference conference) {
            this.conference = conference;
            mTitleTextView.setText(conference.getTitle());
            mDescTextView.setText(conference.getDesc());
        }
    }
}
