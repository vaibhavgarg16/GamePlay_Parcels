package com.game.playparcels.SevicesApi;

import androidx.annotation.NonNull;

import com.game.playparcels.ModelClasses.AdToQueuesModelClass;
import com.game.playparcels.ModelClasses.CancelReactivateGetModelClass;
import com.game.playparcels.ModelClasses.HomeGameList.HomeGameList1;
import com.game.playparcels.ModelClasses.HomeGameListClass;
import com.game.playparcels.ModelClasses.LoginModelClass;
import com.game.playparcels.ModelClasses.MyProfileModelClass;
import com.game.playparcels.ModelClasses.NewsModelClass;
import com.game.playparcels.ModelClasses.ProductByTagModelClass;
import com.game.playparcels.ModelClasses.QueueListModelClass;
import com.game.playparcels.ModelClasses.QueueReturnModelClass;
import com.game.playparcels.ModelClasses.ReOrderModelClass;
import com.game.playparcels.ModelClasses.RelatedGamesModelClass;
import com.game.playparcels.ModelClasses.ReturnPhotoModelClass;
import com.game.playparcels.ModelClasses.SearchGamesModelClass;
import com.game.playparcels.ModelClasses.SignupModelClass;
import com.game.playparcels.ModelClasses.SliderImage.SliderImageResponse;
import com.game.playparcels.ModelClasses.SliderImagesModelClass;
import com.game.playparcels.ModelClasses.UpdateAddressModelClass;
import com.game.playparcels.ModelClasses.UpdatePasswordModelClass;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

//   https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=signup&username=pinky112&email=pinky112@gmail.com&password=123456&repeat_password=123456&surname=agarwal&forename=pinky&postcode=302033&door_number=A-12&street_address=sctor%2016%20pratap%20nagar&town_city=Jaipur&main_console=Play%20station%204
  @NonNull
  @POST("mobile?task=signup")
  Call<SignupModelClass> signup(@Query("username") String username, @Query("email") String email, @Query("password") String password, @Query("repeat_password") String repeat_password, @Query("surname") String surname, @Query("forename") String forename, @Query("postcode") String postcode, @Query("door_number") String door_number, @Query("street_address") String street_address, @Query("town_city") String town_city, @Query("main_console") String main_console);

  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=login&type=email&email=pinky@gmail.com&password=123456
  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=login&email=YWJjQGdtYWlsLmNvbQ==&type=c29jaWFs&profile_pic=https://s3-eu-west-2.amazonaws.com/imagesgameplayparcels/wp-content/uploads/2020/08/20060516/user-54.png&first_name=YWJj&last_name=YWJjZGZz
//  https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=login&email=kirsty-louise1990@outlook.com&type=email&password=123456
  @NonNull
  @POST("mobile?task=login")
  Call<LoginModelClass> login(@Query("email") String email,@Query("type") String type,@Query("password") String password,@Query("device_token") String device_token,@Query("profile_pic") String profile_pic,@Query("first_name") String first_name,@Query("last_name") String last_name);


  @NonNull
  @POST("mobile?task=login")
  Call<LoginModelClass> loginSocial(@Query("email") String email, @Query("type") String type,@Query("profile_pic") String profile_pic,@Query("first_name") String first_name,@Query("last_name") String last_name);


  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=home_page_Game_list
/*  @NonNull
  @POST("mobile?task=home_page_Game_list")
  C

  all<HomeGameListClass> homepagegame();*/

//  https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=home_page_Game_list_1

  @POST("mobile?task=home_page_Game_list_1")
  Call<HomeGameList1> homepagegame1();

  @NonNull
  @POST("?mobile?task=news_api")
 Call<NewsModelClass> newspage1();
  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=related_games&post_id=73
  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=related_games&post_id=8144
  @NonNull
  @POST("mobile?task=related_games")
  Call<RelatedGamesModelClass> relatedgames(@Query("post_id") Integer post_id);

  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=my_profile&user_id=54
  @NonNull
  @POST("mobile?task=my_profile")
  Call<MyProfileModelClass> myprofile(@Query("user_id") Integer user_id);

    //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=home_page_Game_list
  /* @NonNull
    @POST("mobile?task=home_page_Game_list")
    Call<HomeGameListClass> HomeGameList();*/

  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=search_game&search_game=The%20Witcher
  @NonNull
  @POST("mobile?task=search_game")
  Call<SearchGamesModelClass> searchgames(@Query("search_game") String search_game);

  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=add_game_to_queue&user_id=54&product_id=8218
  @NonNull
  @POST("mobile?task=add_game_to_queue")
  Call<AdToQueuesModelClass> addToQueue(@Query("user_id") int user_id, @Query("product_id") int product_id);

  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=show_queue_list&user_id=54
  @NonNull
  @POST("mobile?task=show_queue_list")
  Call<QueueListModelClass> getQueue(@Query("user_id") int user_id);


  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=queue_page_return&order_id=8724&user_id=54
  @NonNull
  @POST("mobile?task=queue_page_return")
  Call<QueueReturnModelClass> returnProduct(@Query("order_id") int order_id, @Query("user_id") int user_id);

  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=change_priority&user_id=54&new_array=1,0,2
  @NonNull
  @POST("mobile?task=change_priority")
  Call<ReOrderModelClass> reOrderProduct(@Query("user_id") int user_id, @Query("new_array") String new_array);

  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=return_with_photo order_id:8833
  //user_id:54  proof_image

  @NonNull
  @Multipart
  @POST("mobile?task=return_with_photo")
  Call<ReturnPhotoModelClass> returnWithPhoto(@Query("order_id") int order_id, @Query("user_id") int user_id, @Part MultipartBody.Part proof_image);

  // https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=update_address&user_id=93&postcode=MjMyMzIz&door_number=MTI=&street_address=aGRmZnMgZmRnZGc=&town_city=amFpcHVyCg==
  @NonNull
  @POST("mobile?task=update_address")
  Call<UpdateAddressModelClass> updateAddress(@Query("user_id") int user_id, @Query("postcode") String postcode, @Query("door_number") String door_number, @Query("street_address") String street_address, @Query("town_city") String town_city);

  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=update_password&user_id=93&existing_password=aW5kaWE=&new_password=Y25lbA==
  @NonNull
  @POST("mobile?task=update_password")
  Call<UpdatePasswordModelClass> updatePassword(@Query("user_id") int user_id, @Query("existing_password") String existing_password, @Query("new_password") String new_password);

  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=get_subscription_plan&user_id=93&subscription_id=8854
 /* @NonNull
  @POST("mobile?task=get_subscription_plan")
  Call<CancelReactivateGetModelClass> getplancancelreactivate(@Query("user_id") int user_id, @Query("subscription_id") String subscription_id);*/

  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=cancel_reactivate_subscription&user_id=93&subscription_id=8854
  @NonNull
  @POST("mobile?task=cancel_reactivate_subscription")
  Call<CancelReactivateGetModelClass> getcancelreactivatesubscription(@Query("user_id") int user_id, @Query("subscription_id") String subscription_id);

  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=get_menu_product&category_id=99
  @NonNull
  @POST("mobile?task=get_menu_product")
  Call<HomeGameList1> getHomeGameListClass(@Query("category_id") String category_id);

  // https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=forgotPassword&username_or_email:agarwalpinky666@gmail.com
  @NonNull
  @POST("mobile?task=forgotPassword")
  Call<CancelReactivateGetModelClass> getForgotPassword(@Query("username_or_email") String category_id);

  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=list_view_slider_images
  @NonNull
  @POST("mobile?task=list_view_slider_images")
  Call<SliderImagesModelClass> getSliderImages();

  // https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=get_product_by_tag&category_id=99&tag=pegi
  @NonNull
  @POST("mobile?task=get_product_by_tag")
  Call<ProductByTagModelClass> getProductByTag(@Query("category_id") String category_id, @Query("tag") String tag);

  // https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=recent_view&user_id=94
  @NonNull
  @POST("mobile?task=recent_view")
  Call<SearchGamesModelClass> getRecentSearch(@Query("user_id") String userid);

  //https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=add_games_to_recent_view&user_id=94&product_id=8990
  @NonNull
  @POST("mobile?task=add_games_to_recent_view")
  Call<AddSearchModelClass> addRecentSearch(@Query("user_id") Integer userid,@Query("product_id") Integer product_id);

//  https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/mobile?task=home_page_slider_image
  @GET("mobile?task=home_page_slider_image")
  Call<SliderImageResponse> getSliderImages1();

}