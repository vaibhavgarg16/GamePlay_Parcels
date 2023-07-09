package com.game.playparcels.ModelClasses.HomeGameList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HomeGameList1 implements Serializable {

	@SerializedName("error_code")
	private String errorCode;

	@SerializedName("categories")
	private ArrayList<CategoriesItem> categories;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public String getErrorCode(){
		return errorCode;
	}

	public ArrayList<CategoriesItem> getCategories(){
		return categories;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}