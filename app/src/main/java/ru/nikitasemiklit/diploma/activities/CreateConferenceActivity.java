package ru.nikitasemiklit.diploma.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import ru.nikitasemiklit.diploma.App;
import ru.nikitasemiklit.diploma.R;
import ru.nikitasemiklit.diploma.model.Conference;
import ru.nikitasemiklit.diploma.model.Section;
import ru.nikitasemiklit.diploma.requests.CreateConfereceRequest;
import ru.nikitasemiklit.diploma.responses.Response;

public class CreateConferenceActivity extends AppCompatActivity {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd.MM.yy", Locale.getDefault());

    RecyclerView sectionsRecycler;
    EditText etTitle;
    EditText etDesc;
    TextView tvStart;
    TextView tvEnd;
    TextView tvRegistrationEnd;

    Date conferenceStart;
    Date conferenceEnd;
    Date conferenceRegistrationEnd;

    List<Section> sectionList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_conference);
        sectionsRecycler = findViewById(R.id.recycler_sections);
        etTitle = findViewById(R.id.conference_title);
        etDesc = findViewById(R.id.conference_desc);
        tvStart = findViewById(R.id.date_conference_start);
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                if (conferenceStart != null) {
                    calendar.setTime(conferenceStart);
                } else {
                    calendar.setTime(new Date());
                }
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, monthOfYear);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                setStartDate(c.getTime());
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                Calendar now = Calendar.getInstance();
                now.setTime(new Date());
                dpd.setMinDate(now);
                dpd.show(CreateConferenceActivity.this.getFragmentManager(), "Datepickerdialog");
            }
        });
        tvEnd = findViewById(R.id.date_conference_end);
        tvEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                if (conferenceEnd != null) {
                    calendar.setTime(conferenceEnd);
                } else {
                    calendar.setTime(new Date());
                }
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, monthOfYear);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                setEndDate(c.getTime());
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                Calendar now = Calendar.getInstance();
                now.setTime(new Date());
                dpd.setMinDate(now);
                dpd.show(CreateConferenceActivity.this.getFragmentManager(), "Datepickerdialog");
            }
        });
        tvRegistrationEnd = findViewById(R.id.date_end_registration);
        tvRegistrationEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                if (conferenceRegistrationEnd != null) {
                    calendar.setTime(conferenceRegistrationEnd);
                } else {
                    calendar.setTime(new Date());
                }
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, monthOfYear);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                setRegistrationEndDate(c.getTime());
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                Calendar now = Calendar.getInstance();
                now.setTime(new Date());
                dpd.setMinDate(now);
                dpd.show(CreateConferenceActivity.this.getFragmentManager(), "Datepickerdialog");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_conference_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.create_conference_item).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Запрос на добавление конференции
                if (validateFields()) {
                    Conference conference = new Conference(etTitle.getText().toString(),
                            etDesc.getText().toString(),
                            conferenceStart, conferenceEnd, conferenceRegistrationEnd);
                    CreateConfereceRequest createConfereceRequest = new CreateConfereceRequest(conference, sectionList);
                    App.getClient().createConferce(createConfereceRequest).enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            if (response.body().getStatus() == Response.STATUS_OK) {
                                Toast.makeText(CreateConferenceActivity.this, "Конференция создана", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            Toast.makeText(CreateConferenceActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            Toast.makeText(CreateConferenceActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return false;
            }
        });
        return true;
    }

    private boolean validateFields() {
        return !TextUtils.isEmpty(etTitle.getText()) &&
                !TextUtils.isEmpty(etTitle.getText()) &&
                conferenceStart != null &&
                conferenceEnd != null &&
                conferenceStart.before(conferenceEnd) &&
                conferenceRegistrationEnd != null;
    }

    private void setStartDate(Date date) {
        conferenceStart = date;
        if (date != null) {
            tvStart.setText("C " + dateFormat.format(date));
        }
    }

    private void setEndDate(Date date) {
        conferenceEnd = date;
        if (date != null) {
            tvEnd.setText("По " + dateFormat.format(date));
        }
    }

    private void setRegistrationEndDate(Date date) {
        conferenceRegistrationEnd = date;
        if (date != null) {
            tvRegistrationEnd.setText(dateFormat.format(date));
        }
    }
}
