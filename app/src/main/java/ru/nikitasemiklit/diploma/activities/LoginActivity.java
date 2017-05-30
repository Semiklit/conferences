package ru.nikitasemiklit.diploma.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

    public static final String API_KEY = "0c2b48b207ecc820101d610af97c90df2e92ee1dd07b718e3975b8190e5bf93a";
    public static final String URL = "http://35.158.74.167:81/api/v2/user/session";
    public static final String TOKEN = "ru.nikitasemiklit.anroid.susu_conference.session_token";

    static OkHttpClient sClient = new OkHttpClient.Builder().build();

    Button mLoginButton;
    Button mSingupButton;
    EditText mLoginEditText;
    EditText mPasswordEditText;

    @Override
    protected void onCreate (Bundle savedInstanseState){
        super.onCreate(savedInstanseState);

        setContentView(R.layout.activity_login);
        setTitle("Авторизация");

        mLoginEditText = (EditText) findViewById(R.id.et_email);
        mPasswordEditText = (EditText) findViewById(R.id.et_password);

        mLoginButton = (Button) findViewById(R.id.bt_login);
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
                                .addHeader("X-Dreamfactory-API-Key", API_KEY)
                                .addHeader("Content-Type" ,"application/json")
                                .post(RequestBody.create(JSON, json))
                                .build();

                        Response response;

                        try{
                            response = sClient.newCall(request).execute();
                            if (response.code() == 200){

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
                        } catch (final IOException e){
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

        mSingupButton = (Button) findViewById(R.id.bt_singup_activity);
        mSingupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SingupActivity.class);
                startActivity(i);
            }
        });

    }
}
