package ru.nikitasemiklit.diploma.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ru.nikitasemiklit.diploma.R;
import ru.nikitasemiklit.diploma.ui.fragments.ConferenceListFragment;

public class ConferenceListActivity extends AppCompatActivity {

    public static final String EXTRA_MODE = "extra.mode";

    public static final int MODE_MY = 1;
    public static final int MODE_FAVORITE = 2;
    public static final int MODE_AVAILABLE = 3;

    private int mode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mode = getIntent().getIntExtra(EXTRA_MODE, MODE_MY);
        switch (mode) {
            case MODE_MY:
                setTitle("Мои конференции");
                break;
            case MODE_FAVORITE:
                setTitle("Избранные");
                break;
            case MODE_AVAILABLE:
                setTitle("Доступные мне");
        }
        setContentView(R.layout.activity_container);
        Fragment fragment = new ConferenceListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_MODE, mode);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.container_fragment, fragment, "main_fragment")
                .commit();

    }
}
