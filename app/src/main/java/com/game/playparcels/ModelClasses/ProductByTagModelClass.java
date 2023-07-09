package com.game.playparcels.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductByTagModelClass implements Serializable {


    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("error_code")
    @Expose
    public String errorCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("posts")
    @Expose
    public List<Post> posts = null;

    public static class Category implements Serializable {

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

    }


    public class Post implements Serializable {

        @SerializedName("ID")
        @Expose
        public Integer iD;
        @SerializedName("category_id")
        @Expose
        public String categoryId;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("slug")
        @Expose
        public String slug;
        @SerializedName("product_type")
        @Expose
        public String productType;
        @SerializedName("featured")
        @Expose
        public Boolean featured;
        @SerializedName("full_description")
        @Expose
        public String fullDescription;
        @SerializedName("video_url")
        @Expose
        public String videoUrl;
        @SerializedName("date")
        @Expose
        public String date;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("sale_price")
        @Expose
        public String salePrice;
        @SerializedName("regular_price")
        @Expose
        public String regularPrice;
        @SerializedName("categories")
        @Expose
        public List<Category> categories = null;
        @SerializedName("image_id")
        @Expose
        public String imageId;
        @SerializedName("video_thumbnail_url")
        @Expose
        public String videoThumbnailUrl;
        @SerializedName("tag_category_data")
        @Expose
        public List<String> tagCategoryData = null;

    }
}
