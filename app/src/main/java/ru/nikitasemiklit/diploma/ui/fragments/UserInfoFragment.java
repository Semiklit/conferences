package ru.nikitasemiklit.diploma.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nikitasemiklit.diploma.App;
import ru.nikitasemiklit.diploma.R;
import ru.nikitasemiklit.diploma.managers.DataManager;
import ru.nikitasemiklit.diploma.model.User;
import ru.nikitasemiklit.diploma.responses.UserResponse;
import ru.nikitasemiklit.diploma.ui.activities.ConferenceListActivity;

public class UserInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.my_conferences).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ConferenceListActivity.class);
                i.putExtra(ConferenceListActivity.EXTRA_MODE, ConferenceListActivity.MODE_MY);
                startActivity(i);
            }
        });
        view.findViewById(R.id.my_favorites).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ConferenceListActivity.class);
                i.putExtra(ConferenceListActivity.EXTRA_MODE, ConferenceListActivity.MODE_FAVORITE);
                startActivity(i);
            }
        });
        ;
        view.findViewById(R.id.my_available).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ConferenceListActivity.class);
                i.putExtra(ConferenceListActivity.EXTRA_MODE, ConferenceListActivity.MODE_AVAILABLE);
                startActivity(i);
            }
        });
        ;
        final TextView userName = view.findViewById(R.id.user_name);
        if (DataManager.getCurrentUser() != null) {
            userName.setText(DataManager.getCurrentUser().getName());
        } else {
            App.getClient().getCurrentUser(App.getToken()).enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.body() != null) {
                        DataManager.setCurrentUser(response.body().getUser());
                        User user = DataManager.getCurrentUser();
                        if (user != null) {
                            userName.setText(DataManager.getCurrentUser().getName());
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {

                }
            });
        }
        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.clearToken();
            }
        });
    }
}
