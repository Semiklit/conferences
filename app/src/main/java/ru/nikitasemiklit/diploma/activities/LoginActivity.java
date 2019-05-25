package ru.nikitasemiklit.diploma.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
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

public class LoginActivity extends AppCompatActivity {
    EditText mLoginEditText;
    EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanseState) {
        super.onCreate(savedInstanseState);

        setContentView(R.layout.activity_login);
        setTitle("Авторизация");

        mLoginEditText = findViewById(R.id.et_email);
        mPasswordEditText = findViewById(R.id.et_password);

        findViewById(R.id.bt_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        findViewById(R.id.bt_login_vk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VK.initialize(LoginActivity.this.getApplicationContext());
                VK.login(LoginActivity.this);
            }
        });

        findViewById(R.id.bt_google_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestServerAuthCode("AIzaSyAxh7RXHVBIMXIu4_RJwB1RxFguUzM2qBg")
                        .requestEmail()
                        .requestScopes(new Scope("https://www.googleapis.com/auth/youtube.readonly"))
                        .build();
                GoogleApiClient mApiClient = new GoogleApiClient.Builder(LoginActivity.this)
                        .enableAutoManage(LoginActivity.this, new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                            }
                        })
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mApiClient);
                startActivityForResult(signInIntent, 12);
            }
        });


        findViewById(R.id.bt_singup_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SingupActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (!VK.onActivityResult(requestCode, resultCode, data, new VKAuthCallback() {
            @Override
            public void onLogin(@NotNull final VKAccessToken vkAccessToken) {
                App.getClient().loginAsVk(vkAccessToken.getUserId(), vkAccessToken.getAccessToken()).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.body().getStatus() == ru.nikitasemiklit.diploma.responses.Response.STATUS_OK) {
                            UUID token = response.body().getToken();
                            App.setToken(token);
                            goToMainActivity();
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

    private void goToMainActivity() {
        Intent i = new Intent(this, ConferenceListActivity.class);
        startActivity(i);
    }
}
