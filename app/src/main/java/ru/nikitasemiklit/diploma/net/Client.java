package ru.nikitasemiklit.diploma.net;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class Client {
    static final String ENDPOINT = "http://35.158.74.167:81";

    Client() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .client(client)
//                .addConverterFactory()
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
    }

}
