package ru.nikitasemiklit.diploma.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.nikitasemiklit.diploma.R;

/**
 * Created by nikitasemiklit1 on 23.05.17.
 */

public class SingupActivity extends AppCompatActivity{

    EditText mLoginEditText;
    EditText mNameEditText;
    EditText mSurnameEditText;
    EditText mPasswordEditText;
    EditText mPasswordConfirmEditText;
    Button mSingupButton;

    static OkHttpClient sClient = new OkHttpClient.Builder().build();


    public static final String URL = "http://35.158.74.167:81/api/v2/user/register";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_singup);
        setTitle("Регистрация");

        mLoginEditText = (EditText) findViewById(R.id.et_singup_email);
        mNameEditText = (EditText) findViewById(R.id.et_first_name);
        mSurnameEditText = (EditText) findViewById(R.id.et_last_name);
        mPasswordEditText = (EditText) findViewById(R.id.et_singup_password);
        mPasswordConfirmEditText = (EditText) findViewById(R.id.et_singup_password_confirm);
        mSingupButton = (Button) findViewById(R.id.bt_singup);

        mSingupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = mLoginEditText.getText().toString();
                String name = mNameEditText.getText().toString();
                String surname = mSurnameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                String password_confirm = mPasswordConfirmEditText.getText().toString();

                Log.d("Пароли", password + " " + password_confirm);

                if (!login.isEmpty() &&
                        !name.isEmpty() &&
                        !surname.isEmpty() &&
                        !password.isEmpty()) {

                    if (password.contentEquals(password_confirm)) {

                        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                        final String json = "{" +
                                "\"email\" : \"" + mLoginEditText.getText() + "\"," +
                                "\"first_name\" : \"" + mNameEditText.getText() + "\"," +
                                "\"last_name\" : \"" + mSurnameEditText.getText() + "\"," +
                                "\"password\" : \"" + mPasswordEditText.getText() + "\"" +
                                "}";

                        Runnable mAuthenticator = new Runnable() {
                            @Override
                            public void run() {
                                Request request = new Request.Builder()
                                        .url(URL)
                                        .addHeader("Content-Type", "application/json")
                                        .post(RequestBody.create(JSON, json))
                                        .build();

                                Response response;

                                try {
                                    response = sClient.newCall(request).execute();
                                    if (response.code() == 200) {

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(SingupActivity.this, "Регистрация прошла успешно. Вам на почту выслано письмо, перейдите по ссылке для завершения регистрации", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(SingupActivity.this, "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } catch (final IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(SingupActivity.this, "Проверьте соединение с интернетом", Toast.LENGTH_SHORT).show();
                                            Log.e("Server answer: ", e.getMessage());
                                        }
                                    });
                                }
                            }
                        };

                        Thread thread = new Thread(mAuthenticator);
                        thread.start();
                    } else {
                        Toast.makeText(SingupActivity.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SingupActivity.this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



}
