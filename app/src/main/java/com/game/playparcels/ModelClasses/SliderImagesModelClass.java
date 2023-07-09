package com.game.playparcels.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SliderImagesModelClass implements Serializable {

    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("error_code")
    @Expose
    public String errorCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("list_view_image_1")
    @Expose
    public String listViewImage1;
    @SerializedName("list_view_image_2")
    @Expose
    public String listViewImage2;
    @SerializedName("list_view_image_3")
    @Expose
    public String listViewImage3;
}
