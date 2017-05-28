package ru.nikitasemiklit.diploma.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nikitasemiklit1 on 02.04.17.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanseState){
        super.onCreate(savedInstanseState);
        Intent i = new Intent(getApplicationContext(), ConferenceListActivity.class);
        startActivity(i);
        //setContentView(R.layout.activity_login);
    }
}
