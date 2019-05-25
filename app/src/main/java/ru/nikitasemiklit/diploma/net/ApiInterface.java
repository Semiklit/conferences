package ru.nikitasemiklit.diploma.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import ru.nikitasemiklit.diploma.responses.ConferencesResponse;
import ru.nikitasemiklit.diploma.responses.LoginResponse;
import ru.nikitasemiklit.diploma.responses.ReportsResponse;
import ru.nikitasemiklit.diploma.responses.SectionsResponse;

public interface ApiInterface {

    String PARAMETER_ACTION = "action";
    String PARAMETER_TOKEN = "token";
    String ACTION_GET_CONFERENCE_LIST = "get.conference.list";
    String ACTION_GET_CONFERENCE_INFO = "get.conference.info";
    String ACTION_GET_REPORTS_LIST = "get.reports.list";
    String ACTION_GET_REPORT_INFO = "get.report.info";
    String ACTION_GET_SECTION_LIST = "get.section.list";
    String ACTION_GET_SECTION_INFO = "get.section.info";
    String ACTION_GET_USER_INFO = "get.user.info";
    String ACTION_AUTH = "auth";
    String PARAMETER_ID = "id";
    String PARAMETER_CONFERENCE_ID = "conference.id";
    String PARAMETER_SECTION_ID = "section.id";
    String PARAMETER_AUTH_METHOD = "auth.method";
    String PARAMETER_AUTH_EMAIL = "auth.email";
    String PARAMETER_AUTH_S_NAME = "auth.s_name";
    String PARAMETER_AUTH_L_NAME = "auth.l_name";
    String PARAMETER_AUTH_F_NAME = "auth.f_name";
    String PARAMETER_EXT_SERVICE_ID = "ext_service.id";
    String PARAMETER_EXT_SERVICE_NAME = "ext_service.name";
    String PARAMETER_EXT_SERVICE_TOKEN = "ext_service.name";

    String PATH_LOGIN = "conference_backend_war_exploded/login";
    String PATH_DATA = "conference_backend_war_exploded/data";

    @GET(PATH_LOGIN)
    Call<LoginResponse> loginViaVk(@Query(PARAMETER_ACTION) String action,
                                   @Query(PARAMETER_AUTH_METHOD) String authMethod,
                                   @Query(PARAMETER_EXT_SERVICE_ID) int extId,
                                   @Query(PARAMETER_EXT_SERVICE_TOKEN) String token);

    @GET(PATH_DATA)
    Call<ReportsResponse> getReports(@Header("Authorization") String token,
                                             @Query(PARAMETER_ACTION) String action,
                                             @Query(PARAMETER_SECTION_ID) String sectionId);

    @GET(PATH_DATA)
    Call<SectionsResponse> getSections(@Header("Authorization") String token,
                                       @Query(PARAMETER_ACTION) String action,
                                       @Query(PARAMETER_CONFERENCE_ID) String conferenceId);


    @GET(PATH_DATA)
    Call<ConferencesResponse> getConferences(@Header("Authorization") String token,
                                             @Query(PARAMETER_ACTION) String action);


}
