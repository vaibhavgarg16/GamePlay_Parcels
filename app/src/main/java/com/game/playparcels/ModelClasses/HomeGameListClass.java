package com.game.playparcels.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomeGameListClass implements Serializable {
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("error_code")
    @Expose
    public String errorCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("categories")
    @Expose
    public List<Category> categories = null;


    public class Category implements Serializable {

        @SerializedName("post_count")
        @Expose
        public Integer postCount;
        @SerializedName("term_id")
        @Expose
        public Integer termId;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("slug")
        @Expose
        public String slug;
        @SerializedName("term_group")
        @Expose
        public Integer termGroup;
        @SerializedName("term_taxonomy_id")
        @Expose
        public Integer termTaxonomyId;
        @SerializedName("taxonomy")
        @Expose
        public String taxonomy;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("parent")
        @Expose
        public Integer parent;
        @SerializedName("count")
        @Expose
        public Integer count;
        @SerializedName("filter")
        @Expose
        public String filter;
        @SerializedName("posts")
        @Expose
        public ArrayList<Post> posts = null;

    }


    public class Post implements Serializable {

        @SerializedName("ID")
        @Expose
        public Integer iD;

        @SerializedName("category_id")
        @Expose
        public String categoryid;
        @SerializedName("post_author")
        @Expose
        public String postAuthor;
        @SerializedName("post_date")
        @Expose
        public String postDate;
        @SerializedName("post_date_gmt")
        @Expose
        public String postDateGmt;
        @SerializedName("post_content")
        @Expose
        public String postContent;
        @SerializedName("post_title")
        @Expose
        public String postTitle;
        @SerializedName("post_excerpt")
        @Expose
        public String postExcerpt;
        @SerializedName("post_status")
        @Expose
        public String postStatus;
        @SerializedName("comment_status")
        @Expose
        public String commentStatus;
        @SerializedName("ping_status")
        @Expose
        public String pingStatus;
        @SerializedName("post_password")
        @Expose
        public String postPassword;
        @SerializedName("post_name")
        @Expose
        public String postName;
        @SerializedName("to_ping")
        @Expose
        public String toPing;
        @SerializedName("pinged")
        @Expose
        public String pinged;
        @SerializedName("post_modified")
        @Expose
        public String postModified;
        @SerializedName("post_modified_gmt")
        @Expose
        public String postModifiedGmt;
        @SerializedName("post_content_filtered")
        @Expose
        public String postContentFiltered;
        @SerializedName("post_parent")
        @Expose
        public Integer postParent;
        @SerializedName("guid")
        @Expose
        public String guid;
        @SerializedName("menu_order")
        @Expose
        public Integer menuOrder;
        @SerializedName("post_type")
        @Expose
        public String postType;
        @SerializedName("post_mime_type")
        @Expose
        public String postMimeType;
        @SerializedName("comment_count")
        @Expose
        public Integer commentCount;
        @SerializedName("filter")
        @Expose
        public String filter;

        @SerializedName("author_name")
        @Expose
        public String authorName;


        @SerializedName("video_url")
        @Expose
        public String videoUrl;

        @SerializedName("description")
        @Expose
        public String description;

        @SerializedName("post_image_url")
        @Expose
        public String postImageUrl;


        @SerializedName("video_thumbnail_url")
        @Expose
        public String postThumbnail;


        @SerializedName("tag_category_data")
        @Expose
        public List<String> tagCategoryData = null;

        @SerializedName("Availability")
        @Expose
        public String Availability;

    }

}
