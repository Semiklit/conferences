package ru.nikitasemiklit.diploma.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.nikitasemiklit.diploma.responses.LoginResponse;

public class Client {
    private static final String ENDPOINT = "https://conference.semiklit.keenetic.pro/";
    private final ApiInterface apiInterface;

    public Client() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    public Call<LoginResponse> loginAsVk(int userId, String token) {
        return apiInterface.loginViaVk(ApiInterface.ACTION_AUTH, "vk", userId, token);
    }

}
