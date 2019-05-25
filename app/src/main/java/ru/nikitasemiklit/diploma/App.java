package ru.nikitasemiklit.diploma;

import android.app.Application;

import java.util.UUID;

import ru.nikitasemiklit.diploma.net.Client;

public class App extends Application {
    private static Client client;
    private static UUID token;

    public static Client getClient() {
        return client;
    }

    public static UUID getToken() {
        return token;
    }

    public static void setToken(UUID token) {
        App.token = token;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        client = new Client();
        //достать токены из хранилища
    }
}
