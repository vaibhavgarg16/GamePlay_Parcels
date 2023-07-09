package com.game.playparcels.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LoginModelClass implements Serializable {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("login_user_details")
    @Expose
    public List<LoginUserDetail> loginUserDetails = null;

    public class LoginUserDetail {

        @SerializedName("userid")
        @Expose
        public Integer userid;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("Address")
        @Expose
        public String address;
        @SerializedName("role")
        @Expose
        public String role;
        @SerializedName("password")
        @Expose
        public String password;
    }
}
