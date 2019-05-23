package ru.nikitasemiklit.diploma;

import android.app.Application;

import ru.nikitasemiklit.diploma.net.Client;

public class App extends Application {
    private static Client client;

    public static Client getClient() {
        return client;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        client = new Client();
    }
}
