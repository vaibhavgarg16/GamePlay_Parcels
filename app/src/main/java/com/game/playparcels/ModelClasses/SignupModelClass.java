package com.game.playparcels.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SignupModelClass implements Serializable {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("userdetail")
    @Expose
    public Userdetail userdetail;
    @SerializedName("user_extra_detail")
    @Expose
    public UserExtraDetail userExtraDetail;

    public class UserExtraDetail {

        @SerializedName("surname")
        @Expose
        public String surname;
        @SerializedName("forename")
        @Expose
        public String forename;
        @SerializedName("postcode")
        @Expose
        public String postcode;
        @SerializedName("door_number")
        @Expose
        public String doorNumber;
        @SerializedName("street_address")
        @Expose
        public String streetAddress;
        @SerializedName("town_city")
        @Expose
        public String townCity;
        @SerializedName("main_console")
        @Expose
        public String mainConsole;

    }


    public class Userdetail {

        @SerializedName("ID")
        @Expose
        public Integer iD;
        @SerializedName("user_login")
        @Expose
        public String userLogin;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("display_name")
        @Expose
        public String displayName;
        @SerializedName("user_registered")
        @Expose
        public String userRegistered;
        @SerializedName("passoword")
        @Expose
        public String passoword;
    }
}
