package com.game.playparcels.ModelClasses.HomeGameList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CategoriesItem implements Serializable {

	@SerializedName("name")
	private String name;

	@SerializedName("posts")
	private ArrayList<PostsItem> posts;

	public String getName(){
		return name;
	}

	public ArrayList<PostsItem> getPosts(){
		return posts;
	}
}