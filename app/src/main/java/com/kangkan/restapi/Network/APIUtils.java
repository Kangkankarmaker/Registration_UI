package com.kangkan.restapi.Network;


public class APIUtils {

    public APIUtils() {
    };

    public static final String API_URL = "https://gobeyonditsolutions.com/";
    public static User_Service Add_User(){
        return Retrofit_Client.getClient(API_URL).create(User_Service.class);
    }
}
