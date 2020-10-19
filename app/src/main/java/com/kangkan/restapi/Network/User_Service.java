package com.kangkan.restapi.Network;

import com.kangkan.restapi.model.User_Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface User_Service {

    @FormUrlEncoded
    @POST("appApi/register_userApi/insert_registeredUser.php")
    Call<User_Response> addUser(
            @Field("user_name") String user_name,
            @Field("user_email") String user_email,
            @Field("user_phone") String user_phone,
            @Field("user_pass") String user_pass
    );

}
