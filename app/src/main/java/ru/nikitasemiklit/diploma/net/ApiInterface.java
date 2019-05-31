package ru.nikitasemiklit.diploma.net;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.nikitasemiklit.diploma.requests.CreateConferenceRequest;
import ru.nikitasemiklit.diploma.responses.DataResponse;
import ru.nikitasemiklit.diploma.responses.LoginResponse;
import ru.nikitasemiklit.diploma.responses.Response;
import ru.nikitasemiklit.diploma.responses.UserResponse;

public interface ApiInterface {

    String PARAMETER_ACTION = "action";
    String PARAMETER_TOKEN = "token";

    String ACTION_GET_CONFERENCE_LIST = "get.conference.list";
    String ACTION_GET_CONFERENCE = "get.conference";
    String ACTION_GET_CONFERENCE_OWEND = "get.conference.list.owned";
    String ACTION_GET_CONFERENCE_FAVORITES = "get.conference.list.fav";
    String ACTION_GET_REPORTS_FOR_SUBMIT = "get.reports.not_submitted";
    String ACTION_GET_USER = "get.user";
    String ACTION_GET_MESSAGES = "get.messages";
    String ACTION_GET_NOTIFICATIONS = "get.notifications";
    String ACTION_CREATE_CONFERENCE = "create.conference";
    String ACTION_EDIT_CONFERENCE = "edit.conference";
    String ACTION_CREATE_REPORT = "create.report";
    String ACTION_AUTH = "auth";

    String PARAMETER_ID = "id";
    String PARAMETER_CONFERENCE_ID = "conference.id";
    String PARAMETER_USER_ID = "user.id";
    String PARAMETER_AUTH_METHOD = "auth.method";
    String PARAMETER_EXT_SERVICE_ID = "ext_service.id";
    String PARAMETER_EXT_SERVICE_TOKEN = "ext_service.name";

    String HEADER_AUTH = "Authorization";

    String PATH_LOGIN = "conference_backend_war_exploded/login";
    String PATH_CREATE = "conference_backend_war_exploded/create";
    String PATH_DATA = "conference_backend_war_exploded/data";

    @GET(PATH_LOGIN)
    Call<LoginResponse> loginViaVk(@Query(PARAMETER_ACTION) String action,
                                   @Query(PARAMETER_AUTH_METHOD) String authMethod,
                                   @Query(PARAMETER_EXT_SERVICE_ID) int extId,
                                   @Query(PARAMETER_EXT_SERVICE_TOKEN) String token);

    @GET(PATH_DATA)
    Call<DataResponse> getConferences(@Header(HEADER_AUTH) String token,
                                      @Query(PARAMETER_ACTION) String action);

    @GET(PATH_DATA)
    Call<UserResponse> getCurrentUser(@Header(HEADER_AUTH) String token,
                                      @Query(PARAMETER_ACTION) String action);

    @GET(PATH_DATA)
    Call<DataResponse> getConferences(@Header(HEADER_AUTH) String token,
                                      @Query(PARAMETER_ACTION) String action,
                                      @Query(PARAMETER_CONFERENCE_ID) UUID conference_id);

    @POST(PATH_CREATE)
    Call<Response> createConference(@Header(HEADER_AUTH) String token,
                                    @Query(PARAMETER_ACTION) String action,
                                    @Body CreateConferenceRequest createConferenceRequest);


}
