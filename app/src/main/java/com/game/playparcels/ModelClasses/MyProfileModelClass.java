package com.game.playparcels.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MyProfileModelClass implements Serializable {
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("error_code")
    @Expose
    public String errorCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("user_detail")
    @Expose
    public List<UserDetail> userDetail = null;
    @SerializedName("user_extra_detail")
    @Expose
    public List<UserExtraDetail> userExtraDetail = null;
    @SerializedName("subscription")
    @Expose
    public List<Subscription> subscription = null;

    @SerializedName("pass_strength")
    @Expose
    public List<String> passStrength = null;

    @SerializedName("product_check")
    @Expose
    public List<ProductCheck> productCheck = null;
    @SerializedName("product_rent")
    @Expose
    public List<ProductRent> productRent = null;


    public class ProductCheck implements Serializable{

        @SerializedName("status")
        @Expose
        public Boolean status;
        @SerializedName("error_code")
        @Expose
        public String errorCode;
        @SerializedName("message")
        @Expose
        public String message;

    }

    public class UserExtraDetail implements Serializable {

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

    public class ProductRent implements Serializable {

        @SerializedName("product_id")
        @Expose
        public Integer productId;
        @SerializedName("product_name")
        @Expose
        public String productName;
        @SerializedName("product_price")
        @Expose
        public String productPrice;
        @SerializedName("product_type")
        @Expose
        public String productType;
        @SerializedName("product_short_description")
        @Expose
        public String productShortDescription;
        @SerializedName("product_long_description")
        @Expose
        public String productLongDescription;
        @SerializedName("product_url")
        @Expose
        public String productUrl;
        @SerializedName("image_url")
        @Expose
        public String imageUrl;
        @SerializedName("product_status")
        @Expose
        public String productStatus;

    }

    public class Subscription implements Serializable{

        @SerializedName("subscription_id")
        @Expose
        public Integer subscriptionId;


        @SerializedName("subscription_name")
        @Expose
        public String subscriptionname;



        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("currency")
        @Expose
        public String currency;
        @SerializedName("date_created")
        @Expose
        public String dateCreated;
        @SerializedName("date_modified")
        @Expose
        public String dateModified;
        @SerializedName("total")
        @Expose
        public String total;
        @SerializedName("payment_method")
        @Expose
        public String paymentMethod;
        @SerializedName("payment_method_title")
        @Expose
        public String paymentMethodTitle;
        @SerializedName("billing_period")
        @Expose
        public String billingPeriod;
        @SerializedName("trial_period")
        @Expose
        public String trialPeriod;
        @SerializedName("schedule_next_payment")
        @Expose
        public String scheduleNextPayment;
        @SerializedName("remaining_days_of_subscription")
        @Expose
        public String remainingDaysOfSubscription;

    }

    public class UserDetail implements Serializable {

        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("user_email")
        @Expose
        public String userEmail;
        @SerializedName("user_register")
        @Expose
        public String userRegister;

    }
}
