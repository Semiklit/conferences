package ru.nikitasemiklit.diploma.ui.createConference;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import ru.nikitasemiklit.diploma.requests.CreateConferenceRequest;
import ru.nikitasemiklit.diploma.responses.Response;
import ru.nikitasemiklit.diploma.widgets.SectionView;

public class CreateConferenceActivity extends AppCompatActivity {

    public static final int MODE_CREATE = 0;

//    int colors [] = {R.color.colorPrimary, R.color.text_color_secondary};
    public static final int MODE_EDIT = 1;
    public static final int MODE_VIEW = 2;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd.MM.yy", Locale.getDefault());
    int mode;

    LinearLayout sectionsLayout;
    LinearLayout addSection;
    EditText etTitle;
    EditText etDesc;
    EditText city;
    SwitchCompat swIsPublic;
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
        setTitle("Создание конференции");
        sectionsLayout = findViewById(R.id.sections_container);
        city = findViewById(R.id.conference_city);
        swIsPublic = findViewById(R.id.conference_public_switch);
        swIsPublic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setText("Открытая");
                } else {
                    buttonView.setText("Закрытая");
                }
            }
        });
        swIsPublic.setChecked(false);
        addSection = findViewById(R.id.add_section_layout);
        addSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogLayout = View.inflate(v.getContext(), R.layout.layout_dialog_add_section, null);
                final EditText title = dialogLayout.findViewById(R.id.title);
                final EditText desc = dialogLayout.findViewById(R.id.desc);
                final AlertDialog dialog = new AlertDialog.Builder(v.getContext(), R.style.PB_AlertDialog_Base)
                        .setTitle("Добавление секции")
                        .setView(dialogLayout)
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                addSection(new Section(title.getText().toString(), desc.getText().toString()));
                            }
                        })
                        .create();
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                }
                dialog.show();
                final Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                okButton.setEnabled(false);
                dialog.setCanceledOnTouchOutside(false);
                title.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(title.getText().length() >= 1);
                    }
                });

                desc.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(desc.getText().length() >= 1);
                    }
                });
            }
        });
        etTitle = findViewById(R.id.conference_title);
        etDesc = findViewById(R.id.conference_desc);
        tvStart = findViewById(R.id.date_conference_start);
        findViewById(R.id.date_conference_start_layout).setOnClickListener(new View.OnClickListener() {
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
        findViewById(R.id.date_conference_end_layout).setOnClickListener(new View.OnClickListener() {
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
        findViewById(R.id.date_end_registration_layout).setOnClickListener(new View.OnClickListener() {
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
                            conferenceStart, conferenceEnd, conferenceRegistrationEnd, swIsPublic.isChecked(), city.getText().toString(), false);
                    CreateConferenceRequest createConferenceRequest = new CreateConferenceRequest(conference, sectionList);
                    App.getClient().createConference(App.getToken(), createConferenceRequest).enqueue(new Callback<Response>() {
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
                } else {
                    Toast.makeText(CreateConferenceActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
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
                conferenceRegistrationEnd != null &&
                !TextUtils.isEmpty(city.getText()) &&
                !sectionList.isEmpty();
    }

    private void setStartDate(Date date) {
        conferenceStart = date;
        if (date != null) {
            tvStart.setText(dateFormat.format(date));
        }
    }

    private void setEndDate(Date date) {
        conferenceEnd = date;
        if (date != null) {
            tvEnd.setText(dateFormat.format(date));
        }
    }

    private void setRegistrationEndDate(Date date) {
        conferenceRegistrationEnd = date;
        if (date != null) {
            tvRegistrationEnd.setText(dateFormat.format(date));
        }
    }

    private void addSection(final Section section) {
        final SectionView sectionView = new SectionView(this, null);
        sectionView.setSection(section);
        sectionsLayout.addView(sectionView);
        sectionList.add(section);
        if (mode != MODE_VIEW) {
            sectionView.setOnDeleteClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sectionList.remove(section);
                    sectionsLayout.removeView(sectionView);
                }
            });
        }
    }
}
