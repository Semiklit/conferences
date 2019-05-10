package ru.nikitasemiklit.diploma.net;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.nikitasemiklit.diploma.responses.ReportsResponse;

public interface ApiInterface {

    @GET("report")
    Call<ReportsResponse> getReports();
}
