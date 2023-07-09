package com.game.playparcels.Activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.login.LoginManager;
import com.game.playparcels.Expandable.ExpandableListAdapter;
import com.game.playparcels.Expandable.MenuModel;
import com.game.playparcels.ModelClasses.MyProfileModelClass;
import com.game.playparcels.ModelClasses.SearchGamesModelClass;
import com.game.playparcels.R;
import com.game.playparcels.SevicesApi.ApiClient;
import com.game.playparcels.SevicesApi.ApiInterface;
import com.game.playparcels.SingleCategory.FullCategoryFragment;
import com.game.playparcels.SingleCategory.GameSingleFragment;
import com.game.playparcels.Youtube.Config;
import com.game.playparcels.ui.NewsTab.NewsFragment;
import com.game.playparcels.ui.SearchFragment;
import com.game.playparcels.ui.GamesTab.GamesFragment;
import com.game.playparcels.ui.AccountTab.AccountFragment;
import com.game.playparcels.ui.QueueTab.QueueFragment;
import com.game.playparcels.ui.SettingsTab.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.preference.PowerPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressCustom;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
//import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,/*AppCompatActivity implements*/ NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = "MainActivity";
    BottomNavigationView navView;
    DrawerLayout drawer_layout;
    ImageView menuicon;
    ImageButton searr;
    View searchshadow;
    View homeshadow;
    RelativeLayout searchLayout, headerLayout;
    TextView cancel_action;
    NavigationView navigationView;
    Boolean loggedin = false;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    RelativeLayout blurbar, blurbar1;
    ProgressBar onee;
    RelativeLayout YoutubeRelative, searchFrame;
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    YouTubePlayer youTubePlayer;
    CircleImageView closeyoutube;
    String id, se;
    //    BubbleNavigationConstraintView bubbleNavigation;
    FrameLayout frameLayout;
    EditText searchInput;
    RecyclerView searchrecycler;
    GameSearchMainRecyclerAdapter gameSearchRecyclerAdapter;
    Call<SearchGamesModelClass> call;
    private SearchGamesModelClass.Post posts;
    LinearLayout loadinglinear;
    Handler handler = new Handler();
    ACProgressCustom bottomDialog;
    Button btnReportBugNav, btnLogoutNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_drawer);
        PowerPreference.init(getApplicationContext());
        setNavigationViewListener();

        navView = findViewById(R.id.nav_view);
        drawer_layout = findViewById(R.id.drawer_layout);
        menuicon = findViewById(R.id.menuicon);
        searr = findViewById(R.id.searr);
        searchshadow = findViewById(R.id.searchshadow);
        homeshadow = findViewById(R.id.homeshadow);
        blurbar = findViewById(R.id.blurbar);
        blurbar1 = findViewById(R.id.blurbar1);
        blurbar.setVisibility(View.INVISIBLE);
        blurbar1.setVisibility(View.INVISIBLE);
        YoutubeRelative = findViewById(R.id.YoutubeRelative);
        loggedin = PowerPreference.getDefaultFile().getBoolean("loggedin", false);
        onee = findViewById(R.id.pbaarr);
        closeyoutube = findViewById(R.id.closeyoutube);
        youTubeView = findViewById(R.id.youtube_view);
        frameLayout = findViewById(R.id.container);
        headerLayout = findViewById(R.id.headerLayout);
        searchLayout = findViewById(R.id.searchLayout);
        cancel_action = findViewById(R.id.cancel_action);
        searchFrame = findViewById(R.id.searchFrame);
        searchInput = findViewById(R.id.searchInput);
        searchrecycler = findViewById(R.id.searchrecycler);
        loadinglinear = findViewById(R.id.loadinglinear);

        btnReportBugNav = findViewById(R.id.btnReportBugNav);
        btnLogoutNav = findViewById(R.id.btnLogoutNav);

        bottomDialog = new ACProgressCustom.Builder(this)
                .useImages(R.drawable.black_fingerprint)
                .build();

        Toasty.success(MainActivity.this, "Login Success!", Toast.LENGTH_LONG, true).show();

        btnReportBugNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toasty.info(MainActivity.this, "Report Clicked.", Toast.LENGTH_LONG, true).show();
                Intent mailer = new Intent(Intent.ACTION_SEND);
                mailer.setData(Uri.parse("mailto:")); // only email apps should handle this
                mailer.setType("text/plain");
                mailer.putExtra(Intent.EXTRA_EMAIL, new String[]{"name@email.com"});
//                mailer.putExtra(Intent.EXTRA_SUBJECT, subject);
//                mailer.putExtra(Intent.EXTRA_TEXT, bodyText);
                startActivity(Intent.createChooser(mailer, "Send email..."));
            }
        });

        btnLogoutNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toasty.info(MainActivity.this, "Logout Clicked.", Toast.LENGTH_LONG, true).show();
                PowerPreference.getDefaultFile().clear();
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSearch();
            }
        });

        searr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.FadeOutUp)
                        .duration(400)
                        .repeat(0)
                        .playOn(headerLayout);


//                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        homeshadow.setVisibility(View.GONE);
                        headerLayout.setVisibility(View.GONE);
                        searchLayout.setVisibility(View.VISIBLE);
                        searchshadow.setVisibility(View.VISIBLE);


                        YoYo.with(Techniques.FadeInDown)
                                .duration(400)
                                .repeat(0)
                                .playOn(searchLayout);

                    }
                }, 400);

                YoYo.with(Techniques.FadeOutDown)
                        .duration(400)
                        .repeat(0)
                        .playOn(frameLayout);

//                Handler handler1 = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        frameLayout.setVisibility(View.GONE);
                        searchFrame.setVisibility(View.VISIBLE);


                        YoYo.with(Techniques.FadeInUp)
                                .duration(400)
                                .repeat(0)
                                .playOn(searchFrame);

                        searchInput.requestFocus();

                        //New Thread
                        if (searchInput.requestFocus()) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.showSoftInput(searchInput, InputMethodManager.SHOW_IMPLICIT);
                                }
                            }).start();
                        }
                    }
                }, 400);


            }
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                String sss = s.toString();
                if (sss.equals("")) {
                    if (loggedin) {
                        Integer userid = PowerPreference.getDefaultFile().getInt("userid");
                        String useridd = userid.toString();
                        recentSearchApi(useridd);
                    } else {
                        recentSearchApi("");
                    }
                }
                se = s.toString();

                if (se.length() > 2) {
                    Handler handler4 = new Handler();
                    handler4.postDelayed(new Runnable() {
                        public void run() {
                            YoYo.with(Techniques.FadeIn)
                                    .duration(100)
                                    .repeat(0)
                                    .playOn(loadinglinear);

                            Handler handler4 = new Handler();
                            handler4.postDelayed(new Runnable() {
                                public void run() {

                                    loadinglinear.setVisibility(View.VISIBLE);
                                }
                            }, 100);
                        }
                    }, 210);
                } else {
                    Handler handler4 = new Handler();
                    handler4.postDelayed(new Runnable() {
                        public void run() {
                            YoYo.with(Techniques.FadeOut)
                                    .duration(100)
                                    .repeat(0)
                                    .playOn(loadinglinear);

                            Handler handler4 = new Handler();
                            handler4.postDelayed(new Runnable() {
                                public void run() {

                                    loadinglinear.setVisibility(View.GONE);
                                }
                            }, 400);
                        }
                    }, 310);
                }


                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    public void run() {

                        if (!se.equals("")) {
                            if (call != null && call.isExecuted()) {
                                call.cancel();
                                searchpageapi(se);
                            } else {
                                searchpageapi(se);
                            }
                            YoYo.with(Techniques.FadeOutDown)
                                    .duration(400)
                                    .repeat(0)
                                    .playOn(searchrecycler);

                            Handler handler1 = new Handler();
                            handler1.postDelayed(new Runnable() {
                                public void run() {
                                    searchrecycler.setVisibility(View.GONE);
                                }
                            }, 500);
                        }

                    }
                }, 500);
            }
        });


        blurbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        blurbar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        menuicon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                drawer_layout.openDrawer(Gravity.LEFT);
            }
        });

        YoutubeRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseVideo();
            }
        });

        closeyoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseVideo();
            }
        });

        expandableListView = findViewById(R.id.expandableListView);

        expandableListView.getLayoutTransition();//.enableTransitionType(LayoutTransition.CHANGING);
        expandableListView.getLayoutTransition().setDuration(200);

        prepareMenuData();
        populateExpandableList();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String result = extras.getString("frag");
            Log.d(TAG, "onCreate: " + result);
            assert result != null;
            switch (result) {
                case "queue":
                    // Load corresponding fragment
//                    bubbleNavigation.setCurrentActiveItem(0);
                    InitUIQueue();
                    break;

                case "profile":
//                    Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show();
//                    bubbleNavigation.setCurrentActiveItem(2);
                    // Load corresponding fragment
                    ProfileUI();
                    break;

                case "home":
//                    bubbleNavigation.setCurrentActiveItem(1);
                    // Load corresponding fragment
                    InitUI();
                    break;

                default:
                    InitUI();
            }
        } else
            InitUI();



        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("ResourceType")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                Log.d(TAG, "on" +
                        "NavigationItemSelected: " + menuItem.getItemId());


                switch (menuItem.getItemId()) {

                    case R.id.navigation_home:
                        stopVideo();
                        hideloader();
                        hideloader1();
                        QueueFragment fragment = QueueFragment.newInstance();
                        FragmentTransaction ft = getFragmentManager().beginTransaction()/*.addToBackStack("")*/;
                        ft.replace(R.id.container, fragment);
                        ft.commit();
                        return true;

                    case R.id.navigation_dashboard:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideloader();
                                        hideloader1();
                                        stopVideo();
                                    }
                                });
                            }
                        }).start();
                        // InitUI();
//                        hideloader();
//                        hideloader1();
//                        stopVideo();
//                        DashboardFragment fragment1 = DashboardFragment.newInstance();
                        GamesFragment fragment1 = new GamesFragment();
                        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                        ft1.replace(R.id.container, fragment1);
                        ft1.commit();
                        return true;


                    case R.id.navigation_notifications:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideloader();
                                        hideloader1();
                                    }
                                });
                            }
                        }).start();
//                        hideloader();
//                        hideloader1();
                        //  stopVideo();
                        if (!loggedin) {
                            startActivity(new Intent(getApplicationContext(), SplashActivity.class)); //LoginActivity
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();
                            return false;

                        } else {
                            AccountFragment fragment2 = AccountFragment.newInstance();
                            FragmentTransaction ft2 = getFragmentManager().beginTransaction()/*.addToBackStack("")*/;
                            ft2.replace(R.id.container, fragment2);
                            ft2.commit();
                            return true;
                        }

                    case R.id.navigation_setting:
                        hideloader();
                        hideloader1();
                        stopVideo();
                        SettingFragment fragment3 = SettingFragment.newInstance();
                        FragmentTransaction ft3 = getFragmentManager().beginTransaction()/*.addToBackStack(null)*/;
                        ft3.replace(R.id.container, fragment3);
                        ft3.commit();
                        return true;

                    case R.id.navigation_news:
                        hideloader();
                        hideloader1();
                        stopVideo();
                        NewsFragment fragment4 = NewsFragment.newInstance();
                        FragmentTransaction ft4 = getFragmentManager().beginTransaction()/*.addToBackStack(null)*/;
                        ft4.replace(R.id.container, fragment4);
                        ft4.commit();
                        return true;
                }
                return false;
            }
        });

        if (!loggedin) {

            btnLogoutNav.setText("LOGIN");
        }

        if (loggedin) {
            Integer userid = PowerPreference.getDefaultFile().getInt("userid");
            String useridd = userid.toString();
            recentSearchApi(useridd);
            getuserprofile(userid);
        } else {
            recentSearchApi("");
        }

    }

    private void cancelSearch() {

        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

//        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                searchrecycler.setVisibility(View.VISIBLE);
                searchFrame.setVisibility(View.VISIBLE);
                searchInput.setText("");

                if (loggedin) {
                    Integer userid = PowerPreference.getDefaultFile().getInt("userid");
                    String useridd = userid.toString();
                    recentSearchApi(useridd);
                } else {
                    recentSearchApi("");
                }

                YoYo.with(Techniques.FadeOutUp)
                        .duration(400)
                        .repeat(0)
                        .playOn(searchLayout);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        searchLayout.setVisibility(View.GONE);
                        searchshadow.setVisibility(View.GONE);
                        headerLayout.setVisibility(View.VISIBLE);
                        homeshadow.setVisibility(View.VISIBLE);


                        YoYo.with(Techniques.FadeInDown)
                                .duration(400)
                                .repeat(0)
                                .playOn(headerLayout);

                    }
                }, 400);

                YoYo.with(Techniques.FadeOutDown)
                        .duration(400)
                        .repeat(0)
                        .playOn(searchFrame);

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    public void run() {
                        searchFrame.setVisibility(View.GONE);
                        frameLayout.setVisibility(View.VISIBLE);

                        YoYo.with(Techniques.FadeInUp)
                                .duration(400)
                                .repeat(0)
                                .playOn(frameLayout);

                    }
                }, 400);

            }
        }, 8);

    }

    private void getuserprofile(Integer userid) {
        Log.d(TAG, "loginapi userid: " + userid);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MyProfileModelClass> call = apiInterface.myprofile(userid);
        call.enqueue(new Callback<MyProfileModelClass>() {
            @Override
            public void onResponse(Call<MyProfileModelClass> call, Response<MyProfileModelClass> response) {

                MyProfileModelClass myProfileModelClass = response.body();
                Log.d(TAG, "onResponse:-- " + new Gson().toJson(response.body()));

                if (myProfileModelClass != null && myProfileModelClass.errorCode.equals("0")) {
                    //user_detail
                    PowerPreference.getDefaultFile().putString("username", myProfileModelClass.userDetail.get(0).username);
                    PowerPreference.getDefaultFile().putString("userEmail", myProfileModelClass.userDetail.get(0).userEmail);
                    PowerPreference.getDefaultFile().putString("userRegister", myProfileModelClass.userDetail.get(0).userRegister);

                    //subscription
                    PowerPreference.getDefaultFile().putInt("subscriptionId", myProfileModelClass.subscription.get(0).subscriptionId);

                    PowerPreference.getDefaultFile().putString("subscriptionname", myProfileModelClass.subscription.get(0).subscriptionname);
                    PowerPreference.getDefaultFile().putString("status", myProfileModelClass.subscription.get(0).status);
                    PowerPreference.getDefaultFile().putString("currency", myProfileModelClass.subscription.get(0).currency);
                    PowerPreference.getDefaultFile().putString("dateCreated", myProfileModelClass.subscription.get(0).dateCreated);
                    PowerPreference.getDefaultFile().putString("dateModified", myProfileModelClass.subscription.get(0).dateModified);
                    PowerPreference.getDefaultFile().putString("total", myProfileModelClass.subscription.get(0).total);
                    PowerPreference.getDefaultFile().putString("paymentMethod", myProfileModelClass.subscription.get(0).paymentMethod);
                    PowerPreference.getDefaultFile().putString("paymentMethodTitle", myProfileModelClass.subscription.get(0).paymentMethodTitle);
                    PowerPreference.getDefaultFile().putString("billingPeriod", myProfileModelClass.subscription.get(0).billingPeriod);
                    PowerPreference.getDefaultFile().putString("trialPeriod", myProfileModelClass.subscription.get(0).trialPeriod);
                    PowerPreference.getDefaultFile().putString("scheduleNextPayment", myProfileModelClass.subscription.get(0).scheduleNextPayment);
                    PowerPreference.getDefaultFile().putString("remainingDaysOfSubscription", myProfileModelClass.subscription.get(0).remainingDaysOfSubscription);

                    //user extra detail
                    PowerPreference.getDefaultFile().putString("doorNumber", myProfileModelClass.userExtraDetail.get(0).doorNumber);
                    PowerPreference.getDefaultFile().putString("forename", myProfileModelClass.userExtraDetail.get(0).forename);
                    PowerPreference.getDefaultFile().putString("mainConsole", myProfileModelClass.userExtraDetail.get(0).mainConsole);
                    PowerPreference.getDefaultFile().putString("postcode", myProfileModelClass.userExtraDetail.get(0).postcode);
                    PowerPreference.getDefaultFile().putString("streetAddress", myProfileModelClass.userExtraDetail.get(0).streetAddress);
                    PowerPreference.getDefaultFile().putString("surname", myProfileModelClass.userExtraDetail.get(0).surname);
                    PowerPreference.getDefaultFile().putString("townCity", myProfileModelClass.userExtraDetail.get(0).townCity);
                    // startActivity(new Intent(getApplicationContext(),MainActivity.class));
                } else {
                    // Toast.makeText(MainActivity.this, ""+myProfileModelClass.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyProfileModelClass> call, Throwable t) {
                Log.d(TAG, "onResponse:Throwable " + "" + t);
            }
        });
    }

    //For open NotificationFragment
    private void ProfileUI() {
        hideloader();
        hideloader1();
        navView.setSelectedItemId(R.id.navigation_notifications);
//        bubbleNavigation.setCurrentActiveItem(2);
        AccountFragment fragment2 = AccountFragment.newInstance();
        FragmentTransaction ft2 = getFragmentManager().beginTransaction();//.addToBackStack("");
        ft2.replace(R.id.container, fragment2);
        ft2.commit();
    }


    private void InitUIQueue() {
//        bubbleNavigation.setCurrentActiveItem(0);
        hideloader();
        navView.setSelectedItemId(R.id.navigation_home);
        QueueFragment fragment = QueueFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction().addToBackStack("");
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    //For open Dashboard Fragment.
    private void InitUI() {
//        Toast.makeText(this, "Dash Clicked.", Toast.LENGTH_SHORT).show();
        hideloader();
        hideloader1();
        navView.setSelectedItemId(R.id.navigation_dashboard);
//        bubbleNavigation.setCurrentActiveItem(1);
//        DashboardFragment fragment1 = DashboardFragment.newInstance();
        GamesFragment fragment1 = new GamesFragment();
        FragmentTransaction ft1 = getFragmentManager().beginTransaction();//.addToBackStack("");
        ft1.replace(R.id.container, fragment1);
        ft1.commit();
    }


    private void setNavigationViewListener() {
        navigationView = findViewById(R.id.nav_viewone);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.d(TAG, "onNavigationItemSelecteddrawer: " + menuItem.getItemId());


        switch (menuItem.getItemId()) {

            case R.id.XBOX_ONE:
                drawer_layout.closeDrawer(Gravity.LEFT);
                InitUI();
//                Toast.makeText(this, "XBOX_ONE", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.PLAYSTATION:
                drawer_layout.closeDrawer(Gravity.LEFT);
                InitUI();
//                Toast.makeText(this, "PLAYSTATION", Toast.LENGTH_SHORT).show();
                return true;


            case R.id.NINTENDO_SWITCH:
                drawer_layout.closeDrawer(Gravity.LEFT);
                InitUI();
//                Toast.makeText(this, "NINTENDO_SWITCH", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.xbox_series_x:
                drawer_layout.closeDrawer(Gravity.LEFT);
                InitUI();
//                Toast.makeText(this, "xbox_series_x", Toast.LENGTH_SHORT).show();
                return true;


            case R.id.playstation_5:
                drawer_layout.closeDrawer(Gravity.LEFT);
                InitUI();
//                Toast.makeText(this, "playstation_5", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        stopVideo();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager f = getFragmentManager();
            if (f.getBackStackEntryCount() > 0) {
                Fragment ff = getFragmentManager().findFragmentById(R.id.container);
                Log.d(TAG, "onBackPressed: ffff" + ff);
                if (ff instanceof QueueFragment) {
                    Log.i(TAG, "onBackPressed: home");
                    f.popBackStack();
//                    bubbleNavigation.setCurrentActiveItem(1);
                } else if (ff instanceof GamesFragment) {
//                    bubbleNavigation.setCurrentActiveItem(1);
                } else if (ff instanceof AccountFragment) {
//                    bubbleNavigation.setCurrentActiveItem(1);
                } else if (ff instanceof SettingFragment) {
                    f.popBackStack();
//                    bubbleNavigation.setCurrentActiveItem(1);
                } else if (ff instanceof SearchFragment) {
                    f.popBackStack();
//                    bubbleNavigation.setCurrentActiveItem(1);
                } else if (ff instanceof GameSingleFragment) {
                    f.popBackStack();
//                    bubbleNavigation.setCurrentActiveItem(1);
                } else if (ff instanceof FullCategoryFragment) {
                    f.popBackStack();
//                    bubbleNavigation.setCurrentActiveItem(1);
                } else {
//                    bubbleNavigation.setCurrentActiveItem(1);
                }


            } else if (getFragmentManager().getBackStackEntryCount() == 1) {
                getFragmentManager().popBackStack();
                f.popBackStack();
                Log.i(TAG, "onBackPressed: homefffff");
            } else {
                stopVideo();
                finishAffinity();
                super.onBackPressed();
            }

        }
    }

    private void prepareMenuData() {

        MenuModel menuModel = new MenuModel(R.id.icon_menu, "Playstation 5", true, true, "0"); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        //menuModel = new MenuModel("data",false,)
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel(R.id.icon_menu, "CATEGORY", true, false, "639,642");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Latest Releases", false, false, "639");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Backwards Compatible", false, false, "642");
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }


        menuModel = new MenuModel((R.id.icon_menu), "Xbox Series X|S", true, true, "0"); //Menu of Java Tutorials
        headerList.add(menuModel);

        //menuModel = new MenuModel("data",false,)
        childModelsList = new ArrayList<>();
        childModel = new MenuModel(R.id.icon_menu, "CATEGORY", true, false, "640,641");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Latest Releases", false, false, "641");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Backwards Compatible", false, false, "640");
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            Log.d("API123", "here");
            childList.put(menuModel, childModelsList);
        }


        childModelsList = new ArrayList<>();
        menuModel = new MenuModel(R.id.icon_menu, "Playstation 4", true, true, "0");
        headerList.add(menuModel);
        //menuModel = new MenuModel("data",false,)
        childModelsList = new ArrayList<>();
        childModel = new MenuModel(R.id.icon_menu, "CATEGORY", true, false, "27,427,89,22,99,621");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Latest Releases", false, false, "27");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Pro Enhanced", false, false, "621");
        childModelsList.add(childModel);


        childModel = new MenuModel(R.id.icon_menu, "Exclusive Games", false, false, "22");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Bonus Games", false, false, "99");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Trial Games", false, false, "89");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "GENRE", true, false, "616,619,615,618,617,620");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Multiplayer", false, false, "615");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Action/Advanture", false, false, "616");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Shooter", false, false, "617");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "RPG", false, false, "618");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Fighting", false, false, "619");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Sports", false, false, "620");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "AGE", true, false, "603,604,600,601,602");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Pegi 18", false, false, "600");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "pegi 16", false, false, "601");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Pegi 12", false, false, "602");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Pegi 7", false, false, "603");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Pegi 3", false, false, "604");
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }


        childModelsList = new ArrayList<>();
        menuModel = new MenuModel(R.id.icon_menu, "Xbox One", true, true, "0"); //Menu of Python Tutorials
        headerList.add(menuModel);
        //menuModel = new MenuModel("data",false,)
        childModelsList = new ArrayList<>();
        childModel = new MenuModel(R.id.icon_menu, "CATEGORY", true, false, "26,90,25,440,634");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Latest Releases", false, false, "26");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "X Enhanced", false, false, "634");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Exclusive Games", false, false, "25");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Bonus Games", false, false, "440");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Trial Games", false, false, "90");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "GENRE", true, false, "633,629,630,632,631,628");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Action/Adventure", false, false, "632");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Multiplayer", false, false, "628");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Shooter", false, false, "629");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "RPG", false, false, "630");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Fighting", false, false, "631");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Sports", false, false, "633");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "AGE", true, false, "613,614,610,611,612");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Pegi 18", false, false, "610");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "pegi 16", false, false, "611");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Pegi 12", false, false, "612");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Pegi 7", false, false, "613");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Pegi 3", false, false, "614");
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }


        childModelsList = new ArrayList<>();
        menuModel = new MenuModel(R.id.icon_menu, "Nintendo Switch", true, true, "0"); //Menu of Python Tutorials
        headerList.add(menuModel);
        //menuModel = new MenuModel("data",false,)
        childModelsList = new ArrayList<>();
        childModel = new MenuModel(R.id.icon_menu, "CATEGORY", true, false, "29,432,28,441");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Latest Releases", false, false, "29");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Exclusive Games", false, false, "28");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Bonus Games", false, false, "441");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Trial Games", false, false, "432");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "GENRE", true, false, "622,623,625,626,627,624");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Action/Adventure", false, false, "622");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Multiplayer", false, false, "623");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Shooter", false, false, "625");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "RPG", false, false, "626");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Fighting", false, false, "624");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Sports", false, false, "627");

        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "AGE", true, false, "609,608,607,606,605");
        childModelsList.add(childModel);

        childModel = new MenuModel(R.id.icon_menu, "Pegi 18", false, false, "609");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "pegi 16", false, false, "608");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Pegi 12", false, false, "607");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Pegi 7", false, false, "606");
        childModelsList.add(childModel);
        childModel = new MenuModel(R.id.icon_menu, "Pegi 3", false, false, "605");
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                Log.d(TAG, "onGroupClickold: " + parent + " zzz " + v + "  zz  " + groupPosition + " xx " + id);
                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {

                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Log.d(TAG, "onGroupClick: " + parent + " zzz " + v + "  zz  " + groupPosition + " xx " + id);
                // TransitionManager.beginDelayedTransition(expandableListView);
                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);

                    showloader1();

                    Log.d(TAG, "onChildClick: groupPosition " + groupPosition + " childPosition " + childPosition + " id " + id + " modelurl " + model.url);
                    navView.setSelectedItemId(R.id.navigation_dashboard);
                    if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                        drawer_layout.closeDrawer(GravityCompat.START);
                    }

                    Bundle args = new Bundle();
                    args.putString("index", model.url);

                    if (model.isGroup) {
//                        DashboardFragment fragment1 = DashboardFragment.newInstance();
                        GamesFragment fragment1 = new GamesFragment();
                        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                        fragment1.setArguments(args);
                        ft1.replace(R.id.container, fragment1);
                        ft1.commit();
                    } else {
                        FullCategoryFragment fragment1 = FullCategoryFragment.newInstance();
                        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                        fragment1.setArguments(args);
                        ft1.replace(R.id.container, fragment1);
                        ft1.commit();
                    }
                }

                return false;
            }
        });
    }

    public void showloader() {

        new Handler().postDelayed(new Runnable() {
            public void run() {

                Log.e(TAG, "showloader:timestemp " + System.currentTimeMillis());
                blurbar.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn)
                        .duration(10)
                        .repeat(0)
                        .playOn(blurbar);

            }
        }, 3);


    }

    public void hideloader() {
        YoYo.with(Techniques.FadeOut)
                .duration(20)
                .repeat(0)
                .playOn(blurbar);

        blurbar.setVisibility(View.INVISIBLE);
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//
//                YoYo.with(Techniques.FadeOut)
//                        .duration(20)
//                        .repeat(0)
//                        .playOn(blurbar);
//
//                blurbar.setVisibility(View.INVISIBLE);
//                Log.e(TAG, "timestemp hide loader- " + System.currentTimeMillis());
//
//
//            }
//        }, 100);
        // Log.e(TAG, "timestemp hide loader- " + System.currentTimeMillis());

    }

    public void showloader1() {

        blurbar1.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(1)
                .repeat(0)
                .playOn(blurbar1);
    }

    public void hideloader1() {

        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            public void run() {
                YoYo.with(Techniques.FadeOut)
                        .duration(100)
                        .repeat(0)
                        .playOn(blurbar1);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        blurbar1.setVisibility(View.INVISIBLE);
                    }
                }, 110);
            }
        }, 210);

    }

    public void playVideo(String idd) {
        id = idd;
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer = player;
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
            player.cueVideo(id);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }


    public void visibleYt() {
        YoutubeRelative.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(750)
                .repeat(0)
                .playOn(YoutubeRelative);

        if (youTubePlayer != null) {
            youTubePlayer.play();
        }/*else{
            YoutubeRelative.setVisibility(View.GONE);
        }*/
    }

    public void pauseVideo() {
        YoutubeRelative.setVisibility(View.GONE);
        if (youTubePlayer != null) {
            youTubePlayer.pause();
        }
    }

    public void stopVideo() {

        YoutubeRelative.setVisibility(View.GONE);
        if (youTubePlayer != null)
            youTubePlayer.release();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            //  getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }


    private void recentSearchApi(String userid) {

        ApiInterface apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        call = apiInterface.getRecentSearch(userid);
        call.enqueue(new Callback<SearchGamesModelClass>() {
            @Override
            public void onResponse(Call<SearchGamesModelClass> call, Response<SearchGamesModelClass> response) {

                if (getApplicationContext() != null) {

                    SearchGamesModelClass searchGamesModelClass = response.body();
                    Log.d(TAG, "onResponse H: " + new Gson().toJson(response.body()));

                    assert searchGamesModelClass != null;
                    if (searchGamesModelClass.errorCode.equals("0")) {
                        try {

                            if (searchGamesModelClass.status) {
                                RecyclerView.LayoutManager ee = new GridLayoutManager(getApplicationContext(), 1);
                                searchrecycler.setLayoutManager(ee);
                                gameSearchRecyclerAdapter = new GameSearchMainRecyclerAdapter(getApplication(), searchGamesModelClass.categories, MainActivity.this, false);
                                searchrecycler.setAdapter(gameSearchRecyclerAdapter);
                            }
                        } catch (NullPointerException o) {
                        }
                    } else {

                        searchrecycler.setAdapter(null);

                    }

                    searchrecycler.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeInUp)
                            .duration(500)
                            .repeat(0)
                            .playOn(searchrecycler);

                    Handler handler4 = new Handler();
                    handler4.postDelayed(new Runnable() {
                        public void run() {
                            Handler handler4 = new Handler();
                            handler4.postDelayed(new Runnable() {
                                public void run() {
                                    YoYo.with(Techniques.FadeOut)
                                            .duration(100)
                                            .repeat(0)
                                            .playOn(loadinglinear);

                                    Handler handler4 = new Handler();
                                    handler4.postDelayed(new Runnable() {
                                        public void run() {

                                            loadinglinear.setVisibility(View.GONE);
                                        }
                                    }, 100);
                                }
                            }, 310);
                        }
                    }, 110);

                }
            }

            @Override
            public void onFailure(Call<SearchGamesModelClass> call, Throwable t) {
                // PowerPreference.getDefaultFile().putBoolean("loggedin",false);
                YoYo.with(Techniques.FadeInUp)
                        .duration(500)
                        .repeat(0)
                        .playOn(searchrecycler);
                searchrecycler.setVisibility(View.VISIBLE);

            }
        });
    }

    public void searchpageapi(String s) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        call = apiInterface.searchgames(s);
        call.enqueue(new Callback<SearchGamesModelClass>() {
            @Override
            public void onResponse(Call<SearchGamesModelClass> call, Response<SearchGamesModelClass> response) {

                if (getApplicationContext() != null) {

                    SearchGamesModelClass homeGameListClass = response.body();
                    Log.d(TAG, "onResponse H: " + new Gson().toJson(response.body()));

                    try {
                        assert homeGameListClass != null;
                        if (homeGameListClass.status) {
                            RecyclerView.LayoutManager ee = new GridLayoutManager(getApplicationContext(), 1);
                            searchrecycler.setLayoutManager(ee);
                            Log.d(TAG, "onResponse: homeGameListClass " + homeGameListClass.categories + " size " + homeGameListClass.categories.size());
                            gameSearchRecyclerAdapter = new GameSearchMainRecyclerAdapter(getApplication(), homeGameListClass.categories, MainActivity.this, true);
                            searchrecycler.setAdapter(gameSearchRecyclerAdapter);
                        }
                    } catch (NullPointerException o) {
                    }

                    searchrecycler.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeInUp)
                            .duration(300)
                            .repeat(0)
                            .playOn(searchrecycler);


                    Handler handler4 = new Handler();
                    handler4.postDelayed(new Runnable() {
                        public void run() {
                            YoYo.with(Techniques.FadeOut)
                                    .duration(100)
                                    .repeat(0)
                                    .playOn(loadinglinear);

                            Handler handler4 = new Handler();
                            handler4.postDelayed(new Runnable() {
                                public void run() {

                                    loadinglinear.setVisibility(View.GONE);
                                }
                            }, 100);
                        }
                    }, 310);

                }
            }


            @Override
            public void onFailure(Call<SearchGamesModelClass> call, Throwable t) {
                Handler handler4 = new Handler();
                handler4.postDelayed(new Runnable() {
                    public void run() {
                        YoYo.with(Techniques.FadeOut)
                                .duration(100)
                                .repeat(0)
                                .playOn(loadinglinear);

                        Handler handler4 = new Handler();
                        handler4.postDelayed(new Runnable() {
                            public void run() {

                                loadinglinear.setVisibility(View.GONE);
                            }
                        }, 100);
                    }
                }, 310);

                searchrecycler.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInUp)
                        .duration(300)
                        .repeat(0)
                        .playOn(searchrecycler);

            }
        });
    }

    @Override
    public void onClick(final View v) {

        switch (v.getId()) {
            case R.id.holderchildid:

                Handler handler3 = new Handler();
                handler3.postDelayed(new Runnable() {
                    public void run() {

                        cancelSearch();

                    }
                }, 0);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        posts = (SearchGamesModelClass.Post) v.getTag();
                        Log.d(TAG, "onClick:posts " + v.getTag());

                        Bundle bundle = new Bundle();

                        Log.d("poipoi", "onClick: " + posts);
                        bundle.putSerializable("data", posts);
                        bundle.putString("adapter", "childrelated");
                        bundle.putInt("idd", posts.iD);
                        GameSingleFragment fragment1 = GameSingleFragment.newInstance();
                        //put bundle in fragment
                        fragment1.setArguments(bundle);
                        //simple context wont work
                        FragmentTransaction ft1 = getFragmentManager().beginTransaction().addToBackStack("tag");
                        ft1.replace(R.id.container, fragment1);
                        ft1.commit();
                        searchInput.setText("");

                    }
                }, 30);


                break;


        }
    }

    class BackgroundThreadMainActivity implements Runnable {

        @Override
        public void run() {

        }
    }
}
