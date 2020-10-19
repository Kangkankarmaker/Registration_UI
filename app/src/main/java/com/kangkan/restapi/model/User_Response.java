package com.kangkan.restapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User_Response {

    @SerializedName("User_name")
    @Expose
    private String user_name;
    @SerializedName("User_email")
    @Expose
    private String user_email;
    @SerializedName("User_phone")
    @Expose
    private String user_phone;
    @SerializedName("User_pass")
    @Expose
    private String user_pass;


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }
}
