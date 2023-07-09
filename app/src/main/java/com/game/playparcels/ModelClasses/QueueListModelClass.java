package com.game.playparcels.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class QueueListModelClass implements Serializable {

    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("post_count")
    @Expose
    public Integer postCount;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("categories")
    @Expose
    public List<Category> categories = null;

    public class Category implements Serializable{

        @SerializedName("Status")
        @Expose
        public String status;
        @SerializedName("post_count")
        @Expose
        public Integer postCount;
        @SerializedName("orders")
        @Expose
        public List<Order> orders = null;

    }


    public class Order implements Serializable{

        @SerializedName("order_id")
        @Expose
        public Integer orderId;
        @SerializedName("category_id")
        @Expose
        public Integer categoryId;
        @SerializedName("product_id")
        @Expose
        public Integer productId;
        @SerializedName("product_name")
        @Expose
        public String productName;
        @SerializedName("order_status")
        @Expose
        public String orderStatus;


        @SerializedName("order_date")
        @Expose
        public String orderdate;




        @SerializedName("img_url")
        @Expose
        public String imgUrl;
        @SerializedName("Availibility")
        @Expose
        public String availibility;
        @SerializedName("address")
        @Expose
        public Address address;

    }



    public class Address implements Serializable{

        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("last_name")
        @Expose
        public String lastName;
        @SerializedName("company")
        @Expose
        public String company;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("phone")
        @Expose
        public String phone;
        @SerializedName("address_1")
        @Expose
        public String address1;
        @SerializedName("address_2")
        @Expose
        public String address2;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("state")
        @Expose
        public String state;
        @SerializedName("postcode")
        @Expose
        public String postcode;
        @SerializedName("country")
        @Expose
        public String country;

    }

}
