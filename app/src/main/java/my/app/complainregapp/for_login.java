package my.app.complainregapp;

import my.app.complainregapp.model.GetComplaintAdmin;
import my.app.complainregapp.model.GetComplain;
import my.app.complainregapp.model.PostAction;
import my.app.complainregapp.model.User;
import my.app.complainregapp.model.UserComplaint;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface for_login {

    @FormUrlEncoded
    @POST("/signup")
    Call<User> createAccount(@Header("access-token") String token,
                             @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/userExists")
    Call<User> ReInstall(@FieldMap Map<String, Object> params);


        @FormUrlEncoded
    @POST("/registerComplaint/")
    Call<UserComplaint> userNotify(@Header("access-token") String token1, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("/admin/actionUpdate")
    Call<PostAction> postAction(@Header("complaint-id") String complID, @FieldMap Map<String, Object> params);





    @GET("/admin/complaints")
    Call<GetComplaintAdmin> getLoc(@Header("branch") String token3, @Header("designation") String desig);

    @GET("/complaints")
    Call<GetComplain> getNoti(@Header("access-token") String token4);

    @GET("/complaintHistory")
    Call<GetComplainHistory> getHistory(@Header("complaint-id") String id);

    @GET("/admin/remark")
    Call<GetRemark> getRemark(@Header("complaint-id") String id);



}
