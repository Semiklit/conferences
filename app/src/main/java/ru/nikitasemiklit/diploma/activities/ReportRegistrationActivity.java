package ru.nikitasemiklit.diploma.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ru.nikitasemiklit.diploma.R;

/**
 * Created by nikitasemiklit1 on 02.04.17.
 */

public class ReportRegistrationActivity extends AppCompatActivity {

    EditText mEditTextDateArrive;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_registration);

        mEditTextDateArrive = (EditText) findViewById(R.id.et_report_registration_date_arrive);

    }
}
