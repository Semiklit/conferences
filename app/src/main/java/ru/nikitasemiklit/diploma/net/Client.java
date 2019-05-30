package ru.nikitasemiklit.diploma.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.nikitasemiklit.diploma.requests.CreateConferenceRequest;
import ru.nikitasemiklit.diploma.responses.DataResponse;
import ru.nikitasemiklit.diploma.responses.LoginResponse;
import ru.nikitasemiklit.diploma.responses.Response;

public class Client {
    private static final String ENDPOINT = "https://conference.semiklit.keenetic.pro/";
    private final ApiInterface apiInterface;

    public Client() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
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

    public Call<DataResponse> getData(UUID token) {
        return apiInterface.getConferences(token != null ? token.toString() : "none", ApiInterface.ACTION_GET_CONFERENCE_LIST, 10);
    }

    public Call<DataResponse> getData(UUID token, UUID conference_id) {
        return apiInterface.getConferences(token != null ? token.toString() : "none", ApiInterface.ACTION_GET_CONFERENCE_LIST, conference_id);
    }

    public Call<Response> createConference(UUID token, CreateConferenceRequest request) {
        return apiInterface.createConference(token != null ? token.toString() : "none", ApiInterface.ACTION_CREATE_CONFERENCE, request);
    }

}
