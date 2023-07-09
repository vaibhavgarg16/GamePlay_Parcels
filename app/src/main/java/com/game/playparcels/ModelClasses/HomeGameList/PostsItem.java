package com.game.playparcels.ModelClasses.HomeGameList;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PostsItem implements Serializable {

	@SerializedName("comment_count")
	private int commentCount;

	@SerializedName("author_name")
	private String authorName;

	@SerializedName("post_title")
	private String postTitle;

	@SerializedName("post_author")
	private String postAuthor;

	@SerializedName("menu_order")
	private int menuOrder;

	@SerializedName("pinged")
	private String pinged;

	@SerializedName("description")
	private String description;

	@SerializedName("post_excerpt")
	private String postExcerpt;

	@SerializedName("post_mime_type")
	private String postMimeType;

	@SerializedName("video_url")
	private String videoUrl;

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("post_name")
	private String postName;

	@SerializedName("post_password")
	private String postPassword;

	@SerializedName("post_image_url")
	private String postImageUrl;

	@SerializedName("tag_category_data")
	private List<String> tagCategoryData;

	@SerializedName("to_ping")
	private String toPing;

	@SerializedName("post_modified")
	private String postModified;

	@SerializedName("post_type")
	private String postType;

	@SerializedName("ID")
	private int iD;

	@SerializedName("post_status")
	private String postStatus;

	@SerializedName("post_content_filtered")
	private String postContentFiltered;

	@SerializedName("post_date_gmt")
	private String postDateGmt;

	@SerializedName("video_thumbnail_url")
	private String videoThumbnailUrl;

	@SerializedName("Availability")
	private String availability;

	@SerializedName("post_modified_gmt")
	private String postModifiedGmt;

	@SerializedName("comment_status")
	private String commentStatus;

	@SerializedName("post_parent")
	private int postParent;

	@SerializedName("filter")
	private String filter;

	@SerializedName("post_content")
	private String postContent;

	@SerializedName("ping_status")
	private String pingStatus;

	@SerializedName("post_date")
	private String postDate;

	@SerializedName("guid")
	private String guid;

	public int getCommentCount(){
		return commentCount;
	}

	public String getAuthorName(){
		return authorName;
	}

	public String getPostTitle(){
		return postTitle;
	}

	public String getPostAuthor(){
		return postAuthor;
	}

	public int getMenuOrder(){
		return menuOrder;
	}

	public String getPinged(){
		return pinged;
	}

	public String getDescription(){
		return description;
	}

	public String getPostExcerpt(){
		return postExcerpt;
	}

	public String getPostMimeType(){
		return postMimeType;
	}

	public String getVideoUrl(){
		return videoUrl;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public String getPostName(){
		return postName;
	}

	public String getPostPassword(){
		return postPassword;
	}

	public String getPostImageUrl(){
		return postImageUrl;
	}

	public List<String> getTagCategoryData(){
		return tagCategoryData;
	}

	public String getToPing(){
		return toPing;
	}

	public String getPostModified(){
		return postModified;
	}

	public String getPostType(){
		return postType;
	}

	public int getID(){
		return iD;
	}

	public String getPostStatus(){
		return postStatus;
	}

	public String getPostContentFiltered(){
		return postContentFiltered;
	}

	public String getPostDateGmt(){
		return postDateGmt;
	}

	public String getVideoThumbnailUrl(){
		return videoThumbnailUrl;
	}

	public String getAvailability(){
		return availability;
	}

	public String getPostModifiedGmt(){
		return postModifiedGmt;
	}

	public String getCommentStatus(){
		return commentStatus;
	}

	public int getPostParent(){
		return postParent;
	}

	public String getFilter(){
		return filter;
	}

	public String getPostContent(){
		return postContent;
	}

	public String getPingStatus(){
		return pingStatus;
	}

	public String getPostDate(){
		return postDate;
	}

	public String getGuid(){
		return guid;
	}
}