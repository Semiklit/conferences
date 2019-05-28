package ru.nikitasemiklit.diploma.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nikitasemiklit.diploma.App;
import ru.nikitasemiklit.diploma.R;
import ru.nikitasemiklit.diploma.responses.LoginResponse;
import ru.nikitasemiklit.diploma.ui.fragments.CalendarFragment;
import ru.nikitasemiklit.diploma.ui.fragments.ConferenceListFragment;
import ru.nikitasemiklit.diploma.ui.fragments.LoginFragment;
import ru.nikitasemiklit.diploma.ui.fragments.UserInfoFragment;

public class TabBarActivity extends AppCompatActivity {

    BottomNavigationView navigationMenuView;
    ConferenceListFragment conferencesList;
    CalendarFragment calendar;
    UserInfoFragment userInfo;
    LoginFragment loginFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_bar);
        conferencesList = new ConferenceListFragment();
        calendar = new CalendarFragment();
        userInfo = new UserInfoFragment();
        loginFragment = new LoginFragment();
        navigationMenuView = findViewById(R.id.navigation);
        navigationMenuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_conference_list:
                        setFragment(conferencesList);
                        return true;
                    case R.id.action_calendar:
                        setFragment(calendar);
                        return true;
                    case R.id.action_person_info:
                        setFragment(App.hasToken() ? userInfo : loginFragment);
                        return true;
                }
                return false;
            }
        });
        navigationMenuView.setSelectedItemId(R.id.action_conference_list);
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.container_fragment, fragment, "main_fragment")
                .commit();
    }

    public void onUserAuthenticated() {
        navigationMenuView.setSelectedItemId(R.id.action_person_info);
    }


    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (!VK.onActivityResult(requestCode, resultCode, data, new VKAuthCallback() {
            @Override
            public void onLogin(@NotNull final VKAccessToken vkAccessToken) {
                App.getClient().loginAsVk(vkAccessToken.getUserId(), vkAccessToken.getAccessToken()).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.body().getStatus() == ru.nikitasemiklit.diploma.responses.Response.STATUS_OK) {
                            UUID token = response.body().getToken();
                            App.setToken(token);
                            onUserAuthenticated();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onLoginFailed(int i) {

            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
