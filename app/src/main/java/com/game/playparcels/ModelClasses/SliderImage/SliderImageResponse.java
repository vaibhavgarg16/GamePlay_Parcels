package com.game.playparcels.ModelClasses.SliderImage;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SliderImageResponse{

	@SerializedName("is_update")
	private String isUpdate;

	@SerializedName("image_url")
	private List<String> imageUrl;

	@SerializedName("error_code")
	private String errorCode;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public String getIsUpdate(){
		return isUpdate;
	}

	public List<String> getImageUrl(){
		return imageUrl;
	}

	public String getErrorCode(){
		return errorCode;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}