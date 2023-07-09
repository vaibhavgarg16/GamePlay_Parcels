package com.game.playparcels.SingleCategory;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.game.playparcels.Activity.MainActivity;
import com.game.playparcels.ModelClasses.AdToQueuesModelClass;
import com.game.playparcels.ModelClasses.HomeGameList.PostsItem;
import com.game.playparcels.ModelClasses.HomeGameListClass;
import com.game.playparcels.ModelClasses.ProductByTagModelClass;
import com.game.playparcels.ModelClasses.RelatedGamesModelClass;
import com.game.playparcels.ModelClasses.SearchGamesModelClass;
import com.game.playparcels.R;
import com.game.playparcels.SevicesApi.AddSearchModelClass;
import com.game.playparcels.SevicesApi.ApiClient;
import com.game.playparcels.SevicesApi.ApiInterface;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.gson.Gson;
import com.preference.PowerPreference;
import com.squareup.picasso.Picasso;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameSingleFragment extends Fragment implements View.OnClickListener {
    LinearLayout relatedlinear;
    TextView watcht;

    PostsItem posts;
    SearchGamesModelClass.Post postsearch;
    RelatedGamesModelClass.Post relatedpost;
    ProductByTagModelClass.Post tagpost;
    String TAG = "GameSingleFragmentTAG";
    TextView about;
    ImageView mainimage;

    String watchurl = "";
    List tagcatdata;
    List tagcatdatatwo;
    Integer PostId;

    RecyclerView recyclerlist;
    ListCatRecycler listCatRecycler;
    ListCatRecycler1 listCatRecycler1;
    //ViewDialog viewDialog;

    RecyclerView game_category_recycler;
    GameRelatedRecycler gameRelatedRecycler;
    TextView releaseDate;
    TextView GameTitle;

    int idd;


    RelatedGamesModelClass.Post postss;
    //  ProgressBar progressBar;

    TextView addtoqueue;
    ProgressBar addtoqueuebar;
    TSnackbar snackbar;
    String stringMsg = "Invalid Subscription";
    CoordinatorLayout coo;
    ImageView /*one,*/ gamebackgroundimage;
    String Thumbnail, rdate;
    TextView populartxt1;
    RelatedGamesModelClass relatedGamesModelClass;
    String type, title;
    String categoryId;
    View view2;
    LinearLayout blurrylayout;
    Boolean loggedin;
    private View root;

    public static GameSingleFragment newInstance() {

        return new GameSingleFragment();
    }


    public GameSingleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_game_single, container, false);
    }

    @Override
    public void onViewCreated(final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);


        ((MainActivity) getActivity()).stopVideo();

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(430, 630);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(300, 630);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(130, 630);

        relatedlinear = root.findViewById(R.id.relatedlinear);
        // toastone=root.findViewById(R.id.toastone);
        watcht = root.findViewById(R.id.watcht);
        about = root.findViewById(R.id.about);
        mainimage = root.findViewById(R.id.mainimage);
        game_category_recycler = root.findViewById(R.id.game_related_recycler);
        recyclerlist = root.findViewById(R.id.recyclerlist);
        blurrylayout = root.findViewById(R.id.blurrylayout);
        //viewDialog = new ViewDialog(getActivity());
        // progressBar=root.findViewById(R.id.progressBar);

        addtoqueue = root.findViewById(R.id.addtoqueue);
        addtoqueuebar = root.findViewById(R.id.addtoqueuebar);
        coo = root.findViewById(R.id.coo);

        gamebackgroundimage = root.findViewById(R.id.gamebackgroundimage);

        releaseDate = root.findViewById(R.id.releaseDate);
        GameTitle = root.findViewById(R.id.gametitle);
        populartxt1 = root.findViewById(R.id.populartxt1);


        loggedin = PowerPreference.getDefaultFile().getBoolean("loggedin", false);

        Bundle bundle = getArguments();
        Log.d(TAG, "onCreateView: " + bundle);
        if (bundle != null) {
            type = bundle.getString("adapter");
            Log.d(TAG, "onCreateView:ppp " + type);
            if (type.equals("child")) {

                idd = bundle.getInt("idd");

                posts = (PostsItem) bundle.getSerializable("data");
                Log.d(TAG, "onCreateView:bundle " + posts.getPostImageUrl());
                about.setText(posts.getDescription());
                rdate = posts.getPostDate();
                title = posts.getPostTitle();
                categoryId = String.valueOf(posts.getCategoryId());
                Thumbnail = posts.getVideoThumbnailUrl();
                Log.d(TAG, "onCreateView: thumbnail" + Thumbnail);

                /**
                 * For showing Image to the background, have to change some string in URL of images
                 */
                final String childImg = Thumbnail.replaceFirst("imagesgameplayparcels", "gameplayparcelsuk");
                Log.d(TAG, "onCreateView: " + childImg);

                if (idd == 640) {
                    Picasso.with(getContext()).load(posts.getPostImageUrl()).placeholder(R.drawable.xboxseries).into(mainimage);
                } else if (idd == 26 || idd == 636 || idd == 90 || idd == 25 || idd == 440 || idd == 641) {
                    Picasso.with(getContext()).load(posts.getPostImageUrl()).placeholder(R.drawable.xboxone).into(mainimage);
                } else if (idd == 29 || idd == 441 || idd == 28 || idd == 432 || idd == 622 || idd == 623 || idd == 624 || idd == 607 || idd == 608 || idd == 609 || idd == 605 || idd == 606 || idd == 626 || idd == 625) {
                    mainimage.setLayoutParams(layoutParams1);
                    Picasso.with(getContext()).load(posts.getPostImageUrl()).placeholder(R.drawable.switchnin).into(mainimage);
                } else if (idd == 642 || idd == 639 || idd == 645 || idd == 644 || idd == 643 || idd == 647 || idd == 646 || idd == 650 || idd == 648 || idd == 649 || idd == 652 || idd == 651 || idd == 663) {
                    Picasso.with(getContext()).load(posts.getPostImageUrl()).placeholder(R.drawable.psfive).into(mainimage);
                } else {
                    Picasso.with(getContext()).load(posts.getPostImageUrl()).placeholder(R.drawable.psfour).into(mainimage);
                }

                watchurl = posts.getVideoUrl();

                tagcatdata = posts.getTagCategoryData();

                Log.d(TAG, "posts.iD: " + posts.getID());
                PostId = posts.getID();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getContext()).load(childImg).placeholder(R.drawable.backgroundlogin).into(gamebackgroundimage);
                    }
                });

            } else if (type.equals("related")) {

                idd = bundle.getInt("idd");
                Log.d(TAG, "onCreateView:related idd " + idd);

                postss = (RelatedGamesModelClass.Post) bundle.getSerializable("data");
                Log.d(TAG, "onCreateView:bundle " + postss.imageId);
                about.setText(postss.fullDescription);
                rdate = postss.date;
                title = postss.title;
                categoryId = postss.categoryId;
                watchurl = postss.videoUrl;
                Thumbnail = postss.videoThumbnailUrl;

                /**
                 * For showing Image to the background, have to change some string in URL of images
                 */
                final String childImg = Thumbnail.replaceFirst("imagesgameplayparcels", "gameplayparcelsuk");
                Log.d(TAG, "onCreateView: " + childImg);

                tagcatdata = postss.tagCategoryData;

                Log.d(TAG, "postss.iD: " + postss.iD);
                PostId = postss.iD;
                Picasso.with(getContext()).load(postss.imageId).placeholder(R.drawable.gamelog).into(mainimage);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getContext()).load(childImg).placeholder(R.drawable.backgroundlogin).into(gamebackgroundimage);
                    }
                });

            } else if (type.equals("full")) {

                posts = (PostsItem) bundle.getSerializable("data");
                Log.d(TAG, "onCreateView:bundle " + posts.getPostImageUrl());
                about.setText(posts.getDescription());
                rdate = posts.getPostDate();
                title = posts.getPostTitle();
                //  about.setColor(Color.WHITE);
                // Glide.with(getContext()).load(posts.postImageUrl).into(mainimage);
                watchurl = posts.getVideoUrl();

                tagcatdata = posts.getTagCategoryData();

                Log.d(TAG, "posts.iD: " + posts.getID());
                PostId = posts.getID();
                Thumbnail = posts.getVideoThumbnailUrl();

                /**
                 * For showing Image to the background, have to change some string in URL of images
                 */
                final String childImg = Thumbnail.replaceFirst("imagesgameplayparcels", "gameplayparcelsuk");
                Log.d(TAG, "onCreateView: " + childImg);

                categoryId = String.valueOf(posts.getCategoryId());

                if (idd == 640) {
                    Picasso.with(getContext()).load(posts.getPostImageUrl()).placeholder(R.drawable.xboxseries).into(mainimage);
                } else if (idd == 26 || idd == 636 || idd == 90 || idd == 25 || idd == 440 || idd == 641) {
                    Picasso.with(getContext()).load(posts.getPostImageUrl()).placeholder(R.drawable.xboxone).into(mainimage);
                } else if (idd == 29 || idd == 441 || idd == 28 || idd == 432 || idd == 622 || idd == 623 || idd == 624 || idd == 607 || idd == 608 || idd == 609 || idd == 605 || idd == 606 || idd == 626 || idd == 625) {
                    mainimage.setLayoutParams(layoutParams2);
                    Picasso.with(getContext()).load(posts.getPostImageUrl()).placeholder(R.drawable.switchnin).into(mainimage);
                } else if (idd == 642 || idd == 639 || idd == 645 || idd == 644 || idd == 643 || idd == 647 || idd == 646 || idd == 650 || idd == 648 || idd == 649 || idd == 652 || idd == 651 || idd == 663) {
                    Picasso.with(getContext()).load(posts.getPostImageUrl()).placeholder(R.drawable.psfive).into(mainimage);
                } else {
                    Picasso.with(getContext()).load(posts.getPostImageUrl()).placeholder(R.drawable.psfour).into(mainimage);
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getContext()).load(childImg).placeholder(R.drawable.backgroundlogin).into(gamebackgroundimage);
                    }
                });

            } else if (type.equals("childrelated")) {
                postsearch = (SearchGamesModelClass.Post) bundle.getSerializable("data");
                Log.d(TAG, "onCreateView:bundle " + postsearch.postImageUrl);
                about.setText(postsearch.description);
                rdate = postsearch.postDate;
                title = postsearch.postTitle;

                watchurl = postsearch.videoUrl;

                tagcatdata = postsearch.tagCategoryData;

                Log.d(TAG, "postsearch.iD: " + postsearch.iD);
                PostId = postsearch.iD;
                if (loggedin) {
                    Integer userid = PowerPreference.getDefaultFile().getInt("userid");
                    recentAddApi(userid, PostId);
                } else {

                }


                Thumbnail = postsearch.postThumbnail;
                Log.d(TAG, "onCreateView: thumbnail" + Thumbnail);

                /**
                 * For showing Image to the background, have to change some string in URL of images
                 */
                final String childImg = Thumbnail.replaceFirst("imagesgameplayparcels", "gameplayparcelsuk");
                Log.d(TAG, "onCreateView: " + childImg);

                categoryId = postsearch.categoryid;

                /*try {
                    String kept = categoryId.substring(0, categoryId.indexOf(","));
                    idd = Integer.parseInt(kept);
                } catch (StringIndexOutOfBoundsException b) {
                    idd = Integer.valueOf(categoryId);
                }

                if (idd == 29 || idd == 441 || idd == 28 || idd == 432 || idd == 622 || idd == 623 || idd == 624 || idd == 607 || idd == 608 || idd == 609 || idd == 605 || idd == 606 || idd == 626 || idd == 625) {
                    mainimage.setLayoutParams(layoutParams);
                    Picasso.with(getContext()).load(postsearch.postImageUrl).placeholder(R.drawable.switchnin).into(mainimage);
                    view2.setVisibility(View.VISIBLE);

                } else if (idd == 26) {
                    Picasso.with(getContext()).load(postsearch.postImageUrl).placeholder(R.drawable.xboxone).into(mainimage);
                } else if (idd == 27) {
                    Picasso.with(getContext()).load(postsearch.postImageUrl).placeholder(R.drawable.psfour).into(mainimage);
                } else {*/
                Picasso.with(getContext()).load(postsearch.postImageUrl).placeholder(R.drawable.gamelog).into(mainimage);
//                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getContext()).load(childImg).placeholder(R.drawable.backgroundlogin).into(gamebackgroundimage);
                    }
                });

            } else if (type.equals("fullrelated")) {
                relatedpost = (RelatedGamesModelClass.Post) bundle.getSerializable("data");
                Log.d(TAG, "onCreateView:bundle " + relatedpost.imageId);
                about.setText(relatedpost.fullDescription);
                rdate = relatedpost.date;
                title = relatedpost.title;
                // about.setColor(Color.WHITE);
                //  Glide.with(getContext()).load(relatedpost.imageId).into(mainimage);
                watchurl = relatedpost.videoUrl;
                tagcatdata = relatedpost.tagCategoryData;

                Log.d(TAG, "postsearch.iD: " + relatedpost.iD);
                PostId = relatedpost.iD;
                Thumbnail = relatedpost.videoThumbnailUrl;

                /**
                 * For showing Image to the background, have to change some string in URL of images
                 */
                final String childImg = Thumbnail.replaceFirst("imagesgameplayparcels", "gameplayparcelsuk");
                Log.d(TAG, "onCreateView: " + childImg);

                categoryId = String.valueOf(relatedpost.categories.get(0).termId);

                if (idd == 640) {
                    Picasso.with(getContext()).load(relatedpost.imageId).placeholder(R.drawable.xboxseries).into(mainimage);
                } else if (idd == 26 || idd == 636 || idd == 90 || idd == 25 || idd == 440 || idd == 641) {
                    Picasso.with(getContext()).load(relatedpost.imageId).placeholder(R.drawable.xboxone).into(mainimage);
                } else if (idd == 29 || idd == 441 || idd == 28 || idd == 432 || idd == 622 || idd == 623 || idd == 624 || idd == 607 || idd == 608 || idd == 609 || idd == 605 || idd == 606 || idd == 626 || idd == 625) {
                    mainimage.setLayoutParams(layoutParams1);
                    Picasso.with(getContext()).load(relatedpost.imageId).placeholder(R.drawable.switchnin).into(mainimage);
                } else if (idd == 642 || idd == 639 || idd == 645 || idd == 644 || idd == 643 || idd == 647 || idd == 646 || idd == 650 || idd == 648 || idd == 649 || idd == 652 || idd == 651 || idd == 663) {
                    Picasso.with(getContext()).load(relatedpost.imageId).placeholder(R.drawable.psfive).into(mainimage);
                } else {
                    Picasso.with(getContext()).load(relatedpost.imageId).placeholder(R.drawable.psfour).into(mainimage);
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getContext()).load(childImg).placeholder(R.drawable.backgroundlogin).into(gamebackgroundimage);
                    }
                });

            } else if (type.equals("tagcat")) {
                tagpost = (ProductByTagModelClass.Post) bundle.getSerializable("data");
                Log.d(TAG, "onCreateView:bundle " + tagpost.imageId);
                about.setText(tagpost.fullDescription);
                rdate = tagpost.date;
                title = tagpost.title;
                // about.setColor(Color.WHITE);
                //Glide.with(getContext()).load(tagpost.imageId).into(mainimage);
                watchurl = tagpost.videoUrl;

                tagcatdata = tagpost.tagCategoryData;

                Log.d(TAG, "postsearch.iD: " + tagpost.iD);
                PostId = tagpost.iD;
                Thumbnail = tagpost.videoThumbnailUrl;
                /**
                 * For showing Image to the background, have to change some string in URL of images
                 */
                final String childImg = Thumbnail.replaceFirst("imagesgameplayparcels", "gameplayparcelsuk");
                Log.d(TAG, "onCreateView: " + childImg);
                Log.d(TAG, "onCreateView: " + childImg);

                categoryId = tagpost.categoryId;
                Log.d(TAG, "onCreateView: categoryId " + categoryId);

                /*try {
                    String kept = categoryId.substring(0, categoryId.indexOf(","));
                    idd = Integer.parseInt(kept);
                } catch (StringIndexOutOfBoundsException b) {
                    idd = Integer.valueOf(categoryId);
                }


                if (idd == 29 || idd == 441 || idd == 28 || idd == 432 || idd == 622 || idd == 623 || idd == 624 || idd == 607 || idd == 608 || idd == 609 || idd == 605 || idd == 606 || idd == 626 || idd == 625) {
                    mainimage.setLayoutParams(layoutParams);
                    view2.setVisibility(View.VISIBLE);
                    Picasso.with(getContext()).load(tagpost.imageId).placeholder(R.drawable.switchnin).into(mainimage);

                } else if (idd == 26) {
                    Picasso.with(getContext()).load(tagpost.imageId).placeholder(R.drawable.xboxone).into(mainimage);
                } else if (idd == 27) {
                    Picasso.with(getContext()).load(tagpost.imageId).placeholder(R.drawable.psfour).into(mainimage);
                } else {*/
                Picasso.with(getContext()).load(tagpost.imageId).placeholder(R.drawable.gamelog).into(mainimage);
//                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getContext()).load(childImg).placeholder(R.drawable.backgroundlogin).into(gamebackgroundimage);
                    }
                });
            }
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Picasso.with(getContext()).load(Thumbnail).placeholder(R.drawable.backgroundlogin).into(gamebackgroundimage);

            }
        });

        releaseDate.setText("Released On: " + rdate);

        GameTitle.setText(title);


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (tagcatdata.size() != 0) {

                    Log.d(TAG, "onCreateViewagcatdata.size(): " + tagcatdata.size());

                    if (tagcatdata.size() > 3) {
                        tagcatdatatwo = tagcatdata.subList(0, 2);
                    }

                    final RecyclerView.LayoutManager ee = new GridLayoutManager(getContext(), 1);
                    ((LinearLayoutManager) ee).setOrientation(RecyclerView.HORIZONTAL);
                    recyclerlist.setLayoutManager(ee);
                    listCatRecycler1 = new ListCatRecycler1(getContext(), tagcatdata, GameSingleFragment.this, categoryId);
                    recyclerlist.setAdapter(listCatRecycler1);
                    recyclerlist.setHasFixedSize(true);
                    recyclerlist.setItemViewCacheSize(5);
                    listCatRecycler1.notifyDataSetChanged();
                }
            }
        });

        Log.d(TAG, "onCreateView:tagcatdata " + tagcatdata);
        Log.d(TAG, "onCreateView:tagcatdatatwo " + tagcatdatatwo);


        Log.d(TAG, "onCreateView:PostId " + PostId);
        if (PostId != null) {
            relatedpostapi(PostId);
        }
        try {
            Log.d(TAG, "onCreateView: watchurl - " + watchurl);

            String[] vid = watchurl.split("=");
            String last = vid[1];
            // Thumbnail = "http://img.youtube.com/vi/"+last+"/0.jpg";
            Log.d(TAG, "onCreateView: Thumbnail " + Thumbnail);


            String urll = watchurl;
            String[] separated = urll.split("/");
            String a = separated[0];
            String c = separated[1];
            String d = separated[2];
            String e = separated[3];
            Log.d(TAG, "onClick: watchurl " + e);
            // Toast.makeText(Learn_Video_Activity.this, "YouTube " + e, Toast.LENGTH_SHORT).show();


            // watchYoutubeVideo(getContext(), last1);

            //play video
            if (last != null && !last.equals("")) {
                ((MainActivity) getActivity()).playVideo(last);
            }

        } catch (NullPointerException | ArrayIndexOutOfBoundsException n) {
        }


        watcht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).visibleYt();
                YoYo.with(Techniques.FadeIn)
                        .duration(300)
                        .repeat(0)
                        .playOn(watcht);
            }
        });


        relatedlinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameSingleFragment fragment1 = GameSingleFragment.newInstance();
                FragmentTransaction ft1 = getFragmentManager().beginTransaction().addToBackStack("tag");
                ft1.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                ft1.replace(R.id.container, fragment1);
                ft1.commit();

            }
        });

        populartxt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (relatedGamesModelClass == null) {
                    return;
                }

                if(relatedGamesModelClass.posts != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", relatedGamesModelClass); /*.get(getPosition()*/
                    bundle.putString("adapter", "relatedtext");
                    FullCategoryFragment fragment1 = FullCategoryFragment.newInstance();
                    fragment1.setArguments(bundle);

                    FragmentTransaction ft1 = (getActivity()).getFragmentManager().beginTransaction().addToBackStack( "tag" );
                    ft1.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                    ft1.replace(R.id.container, fragment1);
                    ft1.commit();
                }else {
                    Toasty.warning(getActivity(), "There is no related games", Toast.LENGTH_LONG, true).show();
                }
            }
        });


        addtoqueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean loggedin = PowerPreference.getDefaultFile().getBoolean("loggedin", false);
                if (loggedin) {
                    Integer userid = PowerPreference.getDefaultFile().getInt("userid");
                    Integer productid = PostId;
                    addtoQueueapi(userid, productid);
                } else {
                    Toasty.error(getActivity(), "You must be logged in.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void relatedpostapi(Integer postId) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<RelatedGamesModelClass> call = apiInterface.relatedgames(postId);
        call.enqueue(new Callback<RelatedGamesModelClass>() {
            @Override
            public void onResponse(Call<RelatedGamesModelClass> call, Response<RelatedGamesModelClass> response) {
                if (getActivity() != null) {
                    relatedGamesModelClass = response.body();
                    Log.d(TAG, "onResponse H:related " + new Gson().toJson(response.body()));
                    assert relatedGamesModelClass != null;
                    if (relatedGamesModelClass.status) {
                        if (relatedGamesModelClass.posts != null) {
                            RecyclerView.LayoutManager ee = new GridLayoutManager(getContext(), 1);
                            ((LinearLayoutManager) ee).setOrientation(RecyclerView.HORIZONTAL);
                            game_category_recycler.setLayoutManager(ee);
                            gameRelatedRecycler = new GameRelatedRecycler(getContext(), relatedGamesModelClass.posts, GameSingleFragment.this, idd);
                            game_category_recycler.setAdapter(gameRelatedRecycler);
                            game_category_recycler.setHasFixedSize(true);
                            game_category_recycler.setItemViewCacheSize(5);
                            gameRelatedRecycler.notifyDataSetChanged();
                        }
                    } else {
                    }
                    if (getActivity() != null) {
                        ((MainActivity) getActivity()).hideloader();
                    }
                }
            }


            @Override
            public void onFailure(Call<RelatedGamesModelClass> call, Throwable t) {
                // PowerPreference.getDefaultFile().putBoolean("loggedin",false);
                Log.d(TAG, "onFailure H:" + t);
                // progressBar.setVisibility(View.GONE);
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).hideloader();
                }
            }
        });
    }


    public void watchYoutubeVideo(Context context, String id) {
        if (id != null && !id.equals(""))
            ((MainActivity) getActivity()).playVideo(id);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }


    public void addtoQueueapi(int userid, int productid) {

        addtoqueue.setVisibility(View.INVISIBLE);
//        one.setVisibility(View.GONE);
        addtoqueuebar.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<AdToQueuesModelClass> call = apiInterface.addToQueue(userid, productid);
        call.enqueue(new Callback<AdToQueuesModelClass>() {
            @Override
            public void onResponse(Call<AdToQueuesModelClass> call, Response<AdToQueuesModelClass> response) {
                AdToQueuesModelClass adToQueuesModelClass = response.body();
                if (getActivity() != null) {
                    Log.d(TAG, "onResponse:adToQueuesModelClass " + adToQueuesModelClass.message + " " + adToQueuesModelClass.status);

                    assert adToQueuesModelClass != null;
                    Toasty.warning(getActivity(), adToQueuesModelClass.message, Toast.LENGTH_LONG).show();

                    addtoqueue.setVisibility(View.VISIBLE);

//                        one.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeIn)
                            .duration(200)
                            .repeat(0)
                            .playOn(addtoqueue);
                    addtoqueuebar.setVisibility(View.GONE);
                }
            }


            @Override
            public void onFailure(Call<AdToQueuesModelClass> call, Throwable t) {
                Log.d(TAG, "onFailure H:" + t);
                addtoqueue.setVisibility(View.VISIBLE);
//                one.setVisibility(View.VISIBLE);
                addtoqueuebar.setVisibility(View.GONE);
                //  Toast.makeText(getContext(), ""+t, Toast.LENGTH_SHORT).show();
                ShowToast(t.getMessage());
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        String urll = watchurl;

        try {
            String[] separated = urll.split("/");
            String a = separated[0];
            String c = separated[1];
            String d = separated[2];
            String e = separated[3];
            Log.d(TAG, "onClick: watchurl " + e);
            // Toast.makeText(Learn_Video_Activity.this, "YouTube " + e, Toast.LENGTH_SHORT).show();

            String[] vid1 = urll.split("=");
            String lastt = vid1[1];
            Log.d(TAG, "onClick: watchurl last " + lastt);
        } catch (NullPointerException | ArrayIndexOutOfBoundsException b) {
        }


    }


    public void ShowToast(String s) {
        snackbar = TSnackbar.make(coo, s, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundResource(R.drawable.roundborderyellow);

        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.BLACK);
        snackbar.show();
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    private void recentAddApi(Integer userid, Integer postid) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<AddSearchModelClass> call = apiInterface.addRecentSearch(userid, postid);
        call.enqueue(new Callback<AddSearchModelClass>() {
            @Override
            public void onResponse(Call<AddSearchModelClass> call, Response<AddSearchModelClass> response) {
            }

            @Override
            public void onFailure(Call<AddSearchModelClass> call, Throwable t) {
            }
        });
    }
}
