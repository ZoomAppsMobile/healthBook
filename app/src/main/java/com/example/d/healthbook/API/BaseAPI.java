package com.example.d.healthbook.API;


import com.example.d.healthbook.CalendarBekarysa.womancycle.Result;
import com.example.d.healthbook.Models.Chat.ChatResponseData;
import com.example.d.healthbook.Models.Chat.CreateChatResponseData;
import com.example.d.healthbook.Models.Chat.DialogsResponseData;
import com.example.d.healthbook.Models.CityList;
import com.example.d.healthbook.Models.DefaultUpdateResponseModel;
import com.example.d.healthbook.Models.DiaryModel.CompleteDiaryData;
import com.example.d.healthbook.Models.DiaryModel.ParameterTableModel.ParameterTableResponse;
import com.example.d.healthbook.Models.DiaryModel.ParameterType;
import com.example.d.healthbook.Models.DiaryModel.ParameterTypeList;
import com.example.d.healthbook.Models.DiaryModel.ParametersResponse;
import com.example.d.healthbook.Models.LoginResponseModel;
import com.example.d.healthbook.Models.RegisterResponse;
import com.example.d.healthbook.Models.ResponseAllSubscriptionsToDoctor;
import com.example.d.healthbook.Models.ResponseCategoryProtocol;
import com.example.d.healthbook.Models.ResponseChannel;
import com.example.d.healthbook.Models.ResponseClinicInfo;
import com.example.d.healthbook.Models.ResponseClinicInfo2;
import com.example.d.healthbook.Models.ResponseClinicList;
import com.example.d.healthbook.Models.ResponseCloseOrOpenVisit;
import com.example.d.healthbook.Models.ResponseDiseaseCategory;
import com.example.d.healthbook.Models.ResponseDoctorInfo;
import com.example.d.healthbook.Models.ResponseDoctorList;
import com.example.d.healthbook.Models.ResponseDrugsCategory;
import com.example.d.healthbook.Models.ResponseEditUserProfile;
import com.example.d.healthbook.Models.ResponseFeedList;
import com.example.d.healthbook.Models.ResponseGetUserData;
import com.example.d.healthbook.Models.ResponseLibraryProtocol;
import com.example.d.healthbook.Models.ResponseMyFamilyMemberCreate;
import com.example.d.healthbook.Models.ResponseMyFamilyMembers;
import com.example.d.healthbook.Models.ResponseNoteType1;
import com.example.d.healthbook.Models.ResponseProgressUser;
import com.example.d.healthbook.Models.ResponseSubscribe;
import com.example.d.healthbook.Models.ResponseSubscribeToDoctor;
import com.example.d.healthbook.Models.ResponseVisit;
import com.example.d.healthbook.Models.SubsriptionCheckResponseData;
import com.example.d.healthbook.Models.VisitResponse;
import com.example.d.healthbook.new_code.choose_service.model.FolderDatum;
import com.example.d.healthbook.new_code.choose_service.model.FolderResponse;
import com.example.d.healthbook.new_code.choose_service.model.FolderResponse2;
import com.example.d.healthbook.new_code.choose_service.model.MonitorRequest;
import com.example.d.healthbook.new_code.choose_service.model.MonitorResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseAPI {









    @Headers("Content-Type: application/json")
    @DELETE("calendar/action/{actionId}")
    public Call<Void> deleteWomanTask(@Path("actionId") String id , @Header("auth-token") String auth_token );



    @Headers("Content-Type: application/json")
    @POST("calendar/action")
    public Call<ResponseNoteType1> saveNoteType1(@Header("auth-token") String auth_token, @Body JsonObject dataToSend);


    @Headers("Content-Type: application/json")
    @PUT("calendar/action/{actionId}")
    public Call<ResponseNoteType1> updateDrug(@Header("auth-token") String auth_token, @Body JsonObject dataToSend,@Path("actionId") String id );




    @Headers("Content-Type: application/json")
    @POST("accounts/login")
    public Call<LoginResponseModel> loginUser(@Body JsonObject dataToSend);

    @Headers("Content-Type: application/json")
    @POST("invite/invite")
    public Call<RegisterResponse> registerUser(@Body JsonObject dataToSend);

    @POST("booking/expert/search")
    public Call<ResponseDoctorList> seeDoctorList(@Query("city_id") String city_id);

    @POST("content/subscriber/subscribe")
    public Call<ResponseSubscribe> subscribe(@Header("auth-token") String auth_token, @Body JsonObject dataToSend);

    @Headers("Content-Type: application/json")
    @POST("content/subscriber/unsubscribe")
    public Call<String> unSubscribe(@Header("auth-token") String auth_token,
                                    @Query("channel_id") String channel_id,
                                    @Query("type") String type,
                                    @Body JsonObject dataToSend);

    @GET("content/library/protocolCategory")
    public Call<ResponseLibraryProtocol> seeLibraryProtocol();

    @GET("booking/visit/next")
    public Call<List<ResponseVisit>> seeVisit(@Header("auth-token") String auth_token);

    @GET("content/channel")
    public Call<ResponseChannel> seeChannel();

    @GET("content/channel")
    public Call<ResponseChannel> addChannel(@Query("page") Integer page);

    @GET("content/library/drugCategory")
    public Call<ResponseLibraryProtocol> seeLibraryDrugs();

    @GET("content/library/diseaseCategory")
    public Call<ResponseLibraryProtocol> seeLibraryDisease();

    @GET("content/library/protocol")
    public Call<ResponseCategoryProtocol> seeProtocolCategory(@Query("category_id") String category_id);

    @GET("content/library/drug")
    public Call<ResponseDrugsCategory> seeDrugsCategory(@Query("category_id") String category_id);

    @GET("content/library/disease")
    public Call<ResponseDiseaseCategory> seeDiseaseCategory(@Query("category_id") String category_id);

    @GET("content/post")
    public Call<ResponseDoctorList> seeNewsList();

    @GET("content/post")
    public Call<ResponseDoctorList> addNewsList(@Query("page") Integer page);

    @GET("content/feed")
    public Call<ResponseFeedList> seeFeedList(@Header("auth-token") String auth_token, @Query("page") Integer page, @Query("lang") String lang,
                                              @Query("type") String type, @Query("scope") String scope);

    @GET("content/feed")
    public Call<ResponseFeedList> seeFeedList2(@Header("auth-token") String auth_token);


    @GET("booking/expert/search")
    public Call<ResponseDoctorList> seeDoctorListFilter(@Query("city_id") String city_id, @Query("speciality") String speciality,
                                                        @Query("full_name") String full_name);


    @GET("booking/expert/search")
    public Call<ResponseDoctorList> seeDoctorListFilter2(@Query("company_point_id") String city_id, @Query("page") String speciality
    );

    @GET("booking/expert/search")
    public Call<ResponseDoctorList> seeDoctorListFilter(@Query("city_id") String city_id, @Query("speciality") String speciality,
                                                        @Query("full_name") String full_name,@Query("page") Integer page);

    @POST("booking/expert/search")
    public Call<ResponseDoctorList> addDoctorList(@Query("page") Integer page, @Query("city_id") Integer city_id);

    @GET("booking/expert/{expert_id}")
    public Call<ResponseDoctorInfo> seedoctorInfo(@Path("expert_id") String user_id);


    @GET("booking/company/{company_id}")
    public Call<ResponseClinicInfo> seeclinicInfo(@Path("company_id") String user_id);

    @GET("booking/companyPoint/{company_id}")
    public Call<ResponseClinicInfo2> seeclinicInfo2(@Path("company_id") String user_id);

    @GET("accounts/user/{userId}")
    public Call<ResponseGetUserData> seedUserInfo(@Header("auth-token") String auth_token, @Path("userId") Integer userId);

    //TODO : CHANGE TO https://healthbook.kz/api/booking/companyPoint/clinic/search?city_id=1&page=1
    @GET("booking/companyPoint")
    public Call<ResponseClinicList> seeClinicList();


    @GET("booking/companyPoint/clinic/search")
    public Call<ResponseClinicList> seeClinicListByName(@Query("name") String name);


    @GET("booking/companyPoint/clinic/search")
    public Call<ResponseClinicList> seeClinicListByNameandCity(@Query("name") String name,@Query("city_id ") String city_id );


    @GET("booking/visit/enroll")
    public Call<List<ResponseCloseOrOpenVisit>> visitCheck(@Header("auth-token") String auth_token, @Query("expert_id") String expert_id, @Query("finish_date") String finish_date,
                                                           @Query("start_date") String start_date);


    @Headers("Content-Type: application/json")
    @POST("booking/visit")
    public Call<VisitResponse> visitUser(@Header("auth-token") String auth_token, @Body JsonObject dataToSend);

    @POST("achievements/task/get-progress")
    public Call<ResponseProgressUser> seeUserProgress(@Header("auth-token") String auth_token);

    @Headers("Content-Type: application/json")
    @POST("booking/subscribing/expert/subscribe")
    public Call<ResponseSubscribeToDoctor> subscribeToDoctor(@Header("auth-token") String auth_token, @Body JsonObject dataToSend);

    @Headers("Content-Type: application/json")
    @POST("booking/subscribing/expert/unsubscribe")
    public Call<ResponseSubscribeToDoctor> unSubscribeToDoctor(@Header("auth-token") String auth_token, @Body JsonObject dataToSend);

    @GET("booking/subscribing/experts")
    public Call<List<ResponseAllSubscriptionsToDoctor>> allUserSubscpriptionsToDoc(@Header("auth-token") String auth_token);

    @Headers("Content-Type: application/json")
    @PUT("accounts/user/{user_id}")
    public Call<ResponseEditUserProfile> editUserProfile(@Path("user_id") Integer user_id, @Header("auth-token") String auth_token, @Body JsonObject dataToSend);


















    @GET("healthbook/diary")
    public Call<ResponseMyFamilyMembers> seeFamilyMembers(@Header("auth-token") String auth_token);

    @GET("healthbook/parameter/monitorParameter/{id}")
    public Call<ParametersResponse> getFolderParameter(@Path("id") String id , @Header("auth-token") String auth_token );

    @GET("healthbook/dictionary/parameterType")
    public Call<List<ParameterTypeList>> getParameterTypes( @Header("auth-token") String auth_token );

    @GET("healthbook/parameter/{id}/table?page=1&size=4")
    public Call<ParameterTableResponse> getFolderParameterTable(@Path("id") String id , @Header("auth-token") String auth_token );

    @GET("calendar/action")
    public Call<List<ResponseNoteType1>> seeNoteType1(@Header("auth-token") String auth_token);

//    @GET("calendar/action")
//    public Call<List<ResponseNoteType1>> seeNoteType1(@Header("auth-token") String auth_token,@Query("type") String type);

    @Headers("Content-Type: application/json")
    @PUT("healthbook/diary")
    public Call<DefaultUpdateResponseModel> editFamilyMember(@Header("auth-token") String auth_token,
                                                             @Body CompleteDiaryData dataToSend);


    @Headers("Content-Type: application/json")
    @POST("healthbook/diary")
    public Call<ResponseMyFamilyMemberCreate> createFamilyMember(@Header("auth-token") String auth_token, @Body JsonObject dataToSend);


    @Headers("Content-Type: application/json")
    @POST("healthbook/diary")
    public Call<FolderResponse> addFolder(@Header("auth-token") String auth_token, @Body JsonObject dataToSend);


    @Headers("Content-Type: application/json")
    @POST("healthbook/parameter")
    public Call<List<FolderResponse2>> addFolderData(@Header("auth-token") String auth_token, @Body List<FolderDatum> folderDatum);




    @Headers("Content-Type: application/json")
    @POST("healthbook/parameter/monitorParameter")
    public Call<MonitorResponse> addMonitorType(@Header("auth-token") String auth_token, @Body MonitorRequest monitorRequest);



    //CHAT
    @GET("chat/get_chats")
    public Call<DialogsResponseData> getDialogsList(@Header("auth-token") String auth_token,
                                                    @Header("USER-ID") Integer user_id,
                                                    @Query("user_id") Integer _user_id ,
                                                    @Query("role") String role);

    @GET("chat/add_chat")
    public Call<CreateChatResponseData> addChat(@Header("auth-token") String auth_token,
                                                @Header("USER-ID") Integer user_id,
                                                @Query("chat_type") String chat_type ,
                                                @Query("chat_name") String chat_name,
                                                @Query("participants") String participants);

    @GET("chat/get_chat_by_id")
    public Call<ChatResponseData> getChatById(@Header("auth-token") String auth_token,
                                              @Header("USER-ID") Integer user_id,
                                              @Query("chat_id") String chat_id);

    @GET("accounts/user")
    public Call<List<ResponseGetUserData>> getDialogParticipantsList(@Header("auth-token") String auth_token,
                                                                     @Query("idList") String uidList );

    @GET("chat/edit_chat")
    public Call<CreateChatResponseData> editChat(@Header("auth-token") String auth_token,
                                                 @Header("USER-ID") Integer user_id,
                                                 @Query("chat_id") String chat_id,
                                                 @Query("chat_name") String chat_name,
                                                 @Query("participants") String participants );

    //Channel
    @GET("content/subscriber/isSubscribed")
    public Call<SubsriptionCheckResponseData> checkChannelSubscription(@Header("auth-token") String auth_token,
                                                                       @Header("USER-ID") Integer user_id,
                                                                       @Query("id") String channel_id ,
                                                                       @Query("type") String type );

    @GET("content/post")
    public Call<ResponseDoctorList> getChannelNews(@Header("auth-token") String auth_token,
                                                   @Header("USER-ID") Integer user_id,
                                                   @Query("channel_id") String channel_id);

    @POST("accounts/phone")
    public Call<String> setPhone(@Header("Content-Type")String contentType,
                                 @Header("auth-token") String auth_token,
                                 @Header("USER-ID") Integer user_id,
                                 @Body JsonObject phoneJson);


    //Files
    @GET("healthbook/diary/{diary_id}")
    public Call<CompleteDiaryData> getDiaryData(@Header("auth-token") String auth_token,
                                                @Header("USER-ID") Integer user_id,
                                                @Path("diary_id") String diary_id);

    @GET("booking/expert/search-params")
    Call<CityList> getListCity();

    @GET("/accounts/login/vk")
    Call<LoginResponseModel> setTokenVk(@Query("code") String code);

    @GET("/accounts/login/fb")
    Call<LoginResponseModel> setTokenFb(@Query("code") String code , @Query("redirectUri") String redirectUri);



}


