package ru.nikitasemiklit.diploma.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.nikitasemiklit.diploma.R;

/**
 * Created by nikitasemiklit1 on 02.04.17.
 */

public class LoginActivity extends AppCompatActivity {

    public static final String URL = "http://192.168.0.233:8080/conference_backend_war_exploded/login";
    public static final String TOKEN = "ru.nikitasemiklit.anroid.susu_conference.session_token";

    static OkHttpClient sClient = new OkHttpClient.Builder().build();

    Button mLoginButton;
    Button mVKLoginButton;
    Button mSingupButton;
    SignInButton mGoogleLoginButton;
    EditText mLoginEditText;
    EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanseState) {
        super.onCreate(savedInstanseState);

        setContentView(R.layout.activity_login);
        setTitle("Авторизация");

        mLoginEditText = findViewById(R.id.et_email);
        mPasswordEditText = findViewById(R.id.et_password);

        mLoginButton = findViewById(R.id.bt_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                final String json = "{" +
                        "\"email\" : \"" + mLoginEditText.getText() + "\"," +
                        "\"password\" : \"" + mPasswordEditText.getText() + "\", " +
                        "\"duration\" : " + 200 +
                        "}";

                Runnable mAuthenticator = new Runnable() {
                    @Override
                    public void run() {
                        Request request = new Request.Builder()
                                .url(URL)
                                .post(RequestBody.create(JSON, json))
                                .build();

                        Response response;

                        try {
                            response = sClient.newCall(request).execute();
                            if (response.code() == 200) {

                                final JsonObject element = new JsonParser().parse(response.body().string()).getAsJsonObject();
                                final String token = element.getAsJsonPrimitive("session_token").getAsString();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(getApplicationContext(), ConferenceListActivity.class);
                                        i.putExtra(TOKEN, token);
                                        startActivity(i);
                                    }
                                });
                            }
                        } catch (final IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "Ошибка авторизации", Toast.LENGTH_SHORT).show();
                                    Log.e("Server answer: ", e.getMessage());
                                }
                            });
                        }
                    }
                };

                Thread thread = new Thread(mAuthenticator);
                thread.start();
            }
        });

        mVKLoginButton = findViewById(R.id.bt_login_vk);
        mVKLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VK.initialize(LoginActivity.this.getApplicationContext());
                VK.login(LoginActivity.this);
            }
        });

        mGoogleLoginButton = findViewById(R.id.bt_google_sign_in);
        mGoogleLoginButton.setOnClickListener(new View.OnClickListener() {
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


        mSingupButton = (Button) findViewById(R.id.bt_singup_activity);
        mSingupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SingupActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VK.onActivityResult(requestCode, resultCode, data, new VKAuthCallback() {
            @Override
            public void onLogin(@NotNull VKAccessToken vkAccessToken) {
                final String token = vkAccessToken.getAccessToken();
                Runnable mAuthenticator = new Runnable() {
                    @Override
                    public void run() {
                        Request request = new Request.Builder()
                                .url(URL + "?token=" + token)
                                .get()
                                .build();

                        Response response;

                        try {
                            response = sClient.newCall(request).execute();
                            if (response.code() == 200) {
                            }
                        } catch (final IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "Ошибка авторизации", Toast.LENGTH_SHORT).show();
                                    Log.e("Server answer: ", e.getMessage());
                                }
                            });
                        }
                    }
                };

                Thread thread = new Thread(mAuthenticator);
                thread.start();
            }

            @Override
            public void onLoginFailed(int i) {

            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
