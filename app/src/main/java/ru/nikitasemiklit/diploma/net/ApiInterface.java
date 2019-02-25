package ru.nikitasemiklit.diploma.net;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.nikitasemiklit.diploma.model.Report;

public interface ApiInterface {

    @GET("report")
    Call<List<Report>> getReports();
}
