package ru.nikitasemiklit.diploma;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

import ru.nikitasemiklit.diploma.net.Client;

public class App extends Application {
    private static Client client;
    private static UUID token;
    private static SharedPreferences settings;

    public static Client getClient() {
        return client;
    }

    public static UUID getToken() {
        return token;
    }

    public static void setToken(UUID token) {
        App.token = token;
        settings.edit().putString("token", token.toString()).apply();
    }

    public static void clearToken(){
        token = null;
        settings.edit().remove("token").apply();
    }

    public static boolean hasToken() {
        return token != null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        client = new Client();
        settings = getSharedPreferences("token", Context.MODE_PRIVATE);
        if (settings.contains("token")) {
            token = UUID.fromString(settings.getString("token", ""));
        }
    }
}
