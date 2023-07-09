package com.game.playparcels.ui.AccountTab;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.webkit.CookieManager;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.baoyz.widget.PullRefreshLayout;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.login.LoginManager;
import com.game.playparcels.Activity.MainActivity;
import com.game.playparcels.Activity.SplashActivity;
import com.game.playparcels.ModelClasses.CancelReactivateGetModelClass;
import com.game.playparcels.ModelClasses.MyProfileModelClass;
import com.game.playparcels.ModelClasses.UpdateAddressModelClass;
import com.game.playparcels.ModelClasses.UpdatePasswordModelClass;
import com.game.playparcels.R;
import com.game.playparcels.SevicesApi.ApiClient;
import com.game.playparcels.SevicesApi.ApiInterface;
import com.game.playparcels.ui.GamesTab.CustomDrawable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.preference.PowerPreference;
import com.squareup.picasso.Picasso;
import com.tistory.zladnrms.roundablelayout.RoundableLayout;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

  // private NotificationsViewModel notificationsViewModel;

    TextView signin/*,loginnn*/,cancelsubscription,logout;
    RoundableLayout changeaddresstxt;
    LinearLayout allcards,addresscardclick,addresstwo,rentdetails;
    CardView addresscard,subscriptioncard,updatepasscard;
    Button addressbutton,changeaddressbtn,confirmchanges,rentdetailsback,confirmc,passback;
    RelativeLayout addressone,layoutgone;
    Boolean isUp;
    ProgressBar crossxwhite;
    ImageView ProfileImage;
    String imageStr;
    RelativeLayout nologinlayout;
    LinearLayout loggedinlayout,updatepasscardclick;

    TextView displaynameEdittext,displaynamecard,towncity,textviewaddress;
    Boolean loggedin=false;
    String displayname,towncityS,streeaaddress;

    String TAG="NotificationsFragmentTAG";
    TextView packagename,renewsString,renewstwostring,packagenameone,addresstext,passstrength,subscribetextview;


    List<MyProfileModelClass.ProductRent> productRents;
    RecyclerView productRents_product_recycler;
    ProductRentsRecycler productRentsRecycler;
    TextView packagetwolinear,rentt;
    TextView townc,staddress,Postcode,doorno,cpassword,npassword;
    PullRefreshLayout pullRefreshLayout;
    CoordinatorLayout coo;
    TSnackbar snackbar;
    LinearLayout progressLinear,Progresslinear;

    RoundableLayout subscribeWebview;
    WebView mWebview;

    public static AccountFragment newInstance() {

        return new AccountFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       /* notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);*/
       return inflater.inflate(R.layout.fragment_notifications, container, false);


    }

    @Override
    public void onViewCreated(View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        loggedin=PowerPreference.getDefaultFile().getBoolean("loggedin",false);
        displayname=  PowerPreference.getDefaultFile().getString("username");

        streeaaddress=PowerPreference.getDefaultFile().getString("streetaddress");
        towncityS=PowerPreference.getDefaultFile().getString("towncity");

        signin=root.findViewById(R.id.signnin);

        //loginnn=root.findViewById(R.id.loginnn);
        allcards=root.findViewById(R.id.allcards);
        addresscard = root.findViewById(R.id.addresscard);
        addresscardclick = root.findViewById(R.id.addresscardclick);
        addressbutton = root.findViewById(R.id.addressbutton);
        passback = root.findViewById(R.id.passback);
        changeaddressbtn = root.findViewById(R.id.changeaddressbtn);
        addressone = root.findViewById(R.id.addressone);
        addresstwo = root.findViewById(R.id.addresstwo);
        changeaddresstxt = root.findViewById(R.id.changeaddress);
        confirmchanges= root.findViewById(R.id.confirmchanges);
        confirmc=root.findViewById(R.id.confirmc);

        subscriptioncard= root.findViewById(R.id.subscriptioncard);
        rentdetails=root.findViewById(R.id.rentdetails);
        rentdetailsback=root.findViewById(R.id.rentdetailsback);
        cancelsubscription = root.findViewById(R.id.cancelsubscription);
        layoutgone = root.findViewById(R.id.layoutgone);
        crossxwhite=root.findViewById(R.id.cross);

        displaynameEdittext=root.findViewById(R.id.displayname);
        displaynamecard=root.findViewById(R.id.displaynamecard);
        towncity=root.findViewById(R.id.towncity);
        textviewaddress=root.findViewById(R.id.textviewaddress);
        logout=root.findViewById(R.id.logout);
        loggedinlayout=root.findViewById(R.id.loggedinlayout);
        nologinlayout=root.findViewById(R.id.nologin);
        packagename=root.findViewById(R.id.packagename);
        renewsString=root.findViewById(R.id.renewsString);
        renewstwostring=root.findViewById(R.id.renewstwostring);
        packagenameone = root.findViewById(R.id.packagenameone);
        addresstext=root.findViewById(R.id.addresstext);
        subscribetextview=root.findViewById(R.id.subscribetextview);
        passstrength=root.findViewById(R.id.passstrength);


        productRents_product_recycler=root.findViewById(R.id.productRents_product_recycler);

        packagetwolinear=root.findViewById(R.id.Rentals);
        rentt=root.findViewById(R.id.rentt);

        townc=root.findViewById(R.id.townc);
        staddress = root.findViewById(R.id.staddress);
        Postcode = root.findViewById(R.id.Postcode);
        doorno = root.findViewById(R.id.doorno);

        updatepasscardclick = root.findViewById(R.id.updatepasscardclick);
        updatepasscard = root.findViewById(R.id.updatepasscard);

        cpassword=root.findViewById(R.id.cpassword);
        npassword=root.findViewById(R.id.npassword);

        coo=root.findViewById(R.id.coo);
        progressLinear =root.findViewById(R.id.progressLinear);
        Progresslinear=root.findViewById(R.id.Progresslinear);

        subscribeWebview = root.findViewById(R.id.subscribeWebview);

        isUp = false;

        ProfileImage=root.findViewById(R.id.profile);
        imageStr = PowerPreference.getDefaultFile().getString("socialImageUrl", "");
        if(imageStr != null && !imageStr.equals("")) {
            Picasso.with(getActivity())
                    .load(imageStr)
                    .placeholder(R.drawable.accountperson)
                    .into(ProfileImage);
        }else{
            ProfileImage.setImageResource(R.drawable.accountperson);
        }


        Log.d(TAG, "onCreateView: "+loggedin+" id "+PowerPreference.getDefaultFile().getInt("userid"));

        if (loggedin){
            nologinlayout.setVisibility(View.GONE);
            loggedinlayout.setVisibility(View.VISIBLE);
        }else {
            nologinlayout.setVisibility(View.VISIBLE);
            loggedinlayout.setVisibility(View.GONE);
        }


        pullRefreshLayout =  root. findViewById(R.id.swipeRefreshLayout);
        pullRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.white));
        pullRefreshLayout.setRefreshDrawable(new CustomDrawable(getContext(),pullRefreshLayout));
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (loggedin) {
                    Integer userid =PowerPreference.getDefaultFile().getInt("userid");
                    getuserprofile(userid,true);
                }else {
                    pullRefreshLayout.setRefreshing(false);
                }
            }
        });


        initonclick();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SplashActivity.class)); //LoginActivity
                Objects.requireNonNull(getActivity()).finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PowerPreference.getDefaultFile().clear();
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                /*nologinlayout.setVisibility(View.VISIBLE);
                loggedinlayout.setVisibility(View.GONE);*/
                startActivity(new Intent(getContext(), SplashActivity.class));//LoginActivity
                getActivity().finish();
            }
        });

        if (!loggedin) {
            //   startActivity(new Intent(getContext(), LoginActivity.class));
        } else {

            Gson gson = new Gson();
            String json = PowerPreference.getDefaultFile().getString("profile", "");

            Integer userid =PowerPreference.getDefaultFile().getInt("userid");
            if (!json.equals("")){

                MyProfileModelClass myProfileModelClass  = gson.fromJson(json,MyProfileModelClass.class);
                assert myProfileModelClass != null;
                if (myProfileModelClass.errorCode.equals("0")){
                    if (getActivity()!=null){
                        ((MainActivity)getActivity()).hideloader();
                        if (myProfileModelClass.errorCode.equals("0")) {
                            PowerPreference.getDefaultFile().putBoolean("loggedin", true);

                            Log.d(TAG, "onResponsedddddg: "+myProfileModelClass.subscription.get(0).subscriptionId);
                            //Below code for get only (TIER 1) from the string.
                            String packagenameString = myProfileModelClass.subscription.get(0).subscriptionname;
//                                Log.d("Package ", "TIER " + String.valueOf(packagenameString.charAt(packagenameString.length() - 1)));

                            displaynameEdittext.setText(myProfileModelClass.userDetail.get(0).username);
                            displaynamecard.setText(myProfileModelClass.userDetail.get(0).username);
                            towncity.setText(myProfileModelClass.userExtraDetail.get(0).townCity);
                            try {
                                if (!TextUtils.isEmpty(packagenameString)){
                                    packagename.setText("TIER " + String.valueOf(packagenameString.charAt(packagenameString.length() - 1)));
                                }else{

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            packagenameone.setText(myProfileModelClass.subscription.get(0).subscriptionname);
                            renewsString.setText("Renews " + myProfileModelClass.subscription.get(0).remainingDaysOfSubscription + " days from now");
                            renewstwostring.setText("Renews " + myProfileModelClass.subscription.get(0).remainingDaysOfSubscription + " days from now");


                            if (myProfileModelClass.userExtraDetail.get(0).streetAddress.equals(""))
                                textviewaddress.setText(streeaaddress);
                            else
                                textviewaddress.setText(myProfileModelClass.userExtraDetail.get(0).doorNumber+myProfileModelClass.userExtraDetail.get(0).streetAddress);


                            addresstext.setText(myProfileModelClass.userExtraDetail.get(0).streetAddress);


                            Log.d(TAG, "onResponse:passStrength " + myProfileModelClass.passStrength.get(0));
                            passstrength.setText(myProfileModelClass.passStrength.get(0));

                   /* for (int i=0; i<myProfileModelClass.productRent .size(); i++) {
                        Log.d(TAG, "onResponse:myProfileModelClass.productRent  "+myProfileModelClass.productRent.get(i).imageUrl);
                    }*/
                            if (Integer.parseInt(myProfileModelClass.subscription.get(0).remainingDaysOfSubscription) >= 0) {
                                cancelsubscription.setVisibility(View.VISIBLE);
                                subscribetextview.setText("Unsubscribe");
                            } else {
                                cancelsubscription.setVisibility(View.GONE);
                                packagename.setText("Subscribe");
                                subscribetextview.setText("Learn more");
                                renewstwostring.setText("No active subscription");
                                renewsString.setText("No active subscription");
                            }

                            rentt.setText("Rented " + myProfileModelClass.productRent.size() + " Games");

                            if (myProfileModelClass.productRent.size() > 0) {
                                packagetwolinear.setText("you have rented " + myProfileModelClass.productRent.size() + " Games this month");

                            } else {
                                packagetwolinear.setText(" ");
                            }

                            RecyclerView.LayoutManager ee = new GridLayoutManager(getContext(), 2);
                            productRents_product_recycler.setLayoutManager(ee);
                            Log.d(TAG, "onResponse:myProfileModelClass.productRent size  " + myProfileModelClass.productRent.size());
                            productRentsRecycler = new ProductRentsRecycler(getContext(), myProfileModelClass.productRent);
                            productRents_product_recycler.setAdapter(productRentsRecycler);


                     /*   if (myProfileModelClass.subscription.get(0).subscriptionId!=0)
                        getsub(myProfileModelClass.subscription.get(0).subscriptionId);*/

                            // startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else {
                            Toast.makeText(getContext(), "" + myProfileModelClass.message, Toast.LENGTH_SHORT).show();
                        }


                    }
                } else {
                    getuserprofile(userid,true);
                }

            }else {
                getuserprofile(userid,true);
            }

        }

        subscribeWebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(getActivity(), "Loading Packages...", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(), SubscribeWebView.class));
            }
        });
    }

    private void initonclick() {

        // for address card clicks
        changeaddressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                YoYo.with(Techniques.FadeOut)
                        .duration(200)
                        .repeat(0)
                        .playOn(addressone);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        addressone.setVisibility(View.GONE);

                        addresstwo.setVisibility(View.VISIBLE);

                        confirmchanges.setVisibility(View.VISIBLE);
                        addressbutton.setVisibility(View.VISIBLE);

                        YoYo.with(Techniques.FadeInUp)
                                .duration(200)
                                .repeat(0)
                                .playOn(confirmchanges);
                        YoYo.with(Techniques.FadeInUp)
                                .duration(200)
                                .repeat(0)
                                .playOn(addressbutton);

                        YoYo.with(Techniques.FadeInUp)
                                .duration(200)
                                .repeat(0)
                                .playOn(addresscardclick);
                        YoYo.with(Techniques.FadeInUp)
                                .duration(200)
                                .repeat(0)
                                .playOn(addresstwo);
                    }
                }, 200);




                //addressone.setVisibility(View.GONE);
                //addresstwo.setVisibility(View.VISIBLE);
                //confirmchanges.setVisibility(View.VISIBLE);
                //addressbutton.setVisibility(View.VISIBLE);
            }
        });


        updatepasscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allcards.setVisibility(View.GONE);
                updatepasscardclick.setVisibility(View.VISIBLE);
                confirmc.setVisibility(View.VISIBLE);

                YoYo.with(Techniques.FadeInUp)
                        .duration(700)
                        .repeat(0)
                        .playOn(updatepasscardclick);

                YoYo.with(Techniques.FadeInUp)
                        .duration(700)
                        .repeat(0)
                        .playOn(confirmc);

            }
        });

        changeaddresstxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YoYo.with(Techniques.FadeOut)
                        .duration(200)
                        .repeat(0)
                        .playOn(allcards);
                YoYo.with(Techniques.FadeOut)
                        .duration(200)
                        .repeat(0)
                        .playOn(addressone);

                addresscardclick.setVisibility(View.VISIBLE);
                addresstwo.setVisibility(View.VISIBLE);
                confirmchanges.setVisibility(View.VISIBLE);
                addressbutton.setVisibility(View.VISIBLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        allcards.setVisibility(View.GONE);
                        addressone.setVisibility(View.GONE);

                        YoYo.with(Techniques.FadeInUp)
                                .duration(200)
                                .repeat(0)
                                .playOn(addresstwo);
                        YoYo.with(Techniques.FadeInUp)
                                .duration(200)
                                .repeat(0)
                                .playOn(confirmchanges);
                        YoYo.with(Techniques.FadeInUp)
                                .duration(200)
                                .repeat(0)
                                .playOn(addressbutton);

                        YoYo.with(Techniques.FadeInUp)
                                .duration(200)
                                .repeat(0)
                                .playOn(addresscardclick);
                    }
                }, 400);
            }
        });

        passback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.FadeOut)
                        .duration(400)
                        .repeat(0)
                        .playOn(updatepasscardclick);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        updatepasscardclick.setVisibility(View.GONE);
                        confirmc.setVisibility(View.GONE);
                        allcards.setVisibility(View.VISIBLE);

                        YoYo.with(Techniques.FadeInUp)
                                .duration(700)
                                .repeat(0)
                                .playOn(updatepasscardclick);
                        YoYo.with(Techniques.FadeInUp)
                                .duration(700)
                                .repeat(0)
                                .playOn(allcards);
                    }
                }, 400);
            }
        });

        addressbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.FadeOut)
                        .duration(200)
                        .repeat(0)
                        .playOn(addresscardclick);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        addresscardclick.setVisibility(View.GONE);

                        addressone.setVisibility(View.VISIBLE);
                        addressbutton.setVisibility(View.GONE);
                        confirmchanges.setVisibility(View.GONE);

                        allcards.setVisibility(View.VISIBLE);

                        YoYo.with(Techniques.FadeInUp)
                                .duration(200)
                                .repeat(0)
                                .playOn(addressone);


                        YoYo.with(Techniques.FadeInUp)
                                .duration(200)
                                .repeat(0)
                                .playOn(addresscardclick);
                        YoYo.with(Techniques.FadeInUp)
                                .duration(200)
                                .repeat(0)
                                .playOn(allcards);
                    }
                }, 200);





               // addressone.setVisibility(View.VISIBLE);
               // addressbutton.setVisibility(View.GONE);
              //  confirmchanges.setVisibility(View.GONE);
               // allcards.setVisibility(View.VISIBLE);
                //addresscardclick.setVisibility(View.GONE);
            }
        });

       confirmc.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              String pone =  cpassword.getText().toString();
               String ptwo =  npassword.getText().toString();

               if (pone.equals("")||ptwo.equals("")){
 //                  Toast.makeText(getActivity(), "Please Enter All Details", Toast.LENGTH_SHORT).show();
                   snackbar = TSnackbar.make(coo, "Please Enter All Details", TSnackbar.LENGTH_LONG);
                   snackbar.setActionTextColor(Color.WHITE);
                   View snackbarView = snackbar.getView();
                   snackbarView.setBackgroundResource(R.drawable.roundborderred);
                   //snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));

                   TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                   textView.setTextColor(Color.BLACK);
                   snackbar.show();
                   return;
               }else {
                   progressLinear.setVisibility(View.VISIBLE);
                   confirmc.setVisibility(View.GONE);

                   updatePassword(pone,ptwo);
               }

           }
       });

        confirmchanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String town = townc.getText().toString();
                String street = staddress.getText().toString();
                String post = Postcode.getText().toString();
                String door = doorno.getText().toString();
                if (town.equals("")||street.equals("")||post.equals("")||door.equals("")){
                    Toast.makeText(getActivity(), "Please Enter All Details", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    updateAddress(town,street,post,door);
                }



              //  addressone.setVisibility(View.VISIBLE);
              //  addresstwo.setVisibility(View.GONE);
              //  addressbutton.setVisibility(View.GONE);
              //  confirmchanges.setVisibility(View.GONE);
              //  allcards.setVisibility(View.VISIBLE);
              //  addresscardclick.setVisibility(View.GONE);
            }
        });

        addresscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YoYo.with(Techniques.FadeOut)
                        .duration(200)
                        .repeat(0)
                        .playOn(allcards);

                addresscardclick.setVisibility(View.VISIBLE);
                addressone.setVisibility(View.VISIBLE);
                addresstwo.setVisibility(View.GONE);
                addressbutton.setVisibility(View.VISIBLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        allcards.setVisibility(View.GONE);


                        YoYo.with(Techniques.FadeInUp)
                                .duration(200)
                                .repeat(0)
                                .playOn(addresscardclick);
                        YoYo.with(Techniques.FadeInUp)
                                .duration(200)
                                .repeat(0)
                                .playOn(addressbutton);
                    }
                }, 200);

                //allcards.setVisibility(View.GONE);
                //addresscardclick.setVisibility(View.VISIBLE);
                //addressone.setVisibility(View.VISIBLE);
                //addresstwo.setVisibility(View.GONE);
                //addressbutton.setVisibility(View.VISIBLE);

            }
        });

        //Learn more button click
        subscribetextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Learn More clicked.", Toast.LENGTH_SHORT).show();
            }
        });

        //for subscription card
        subscriptioncard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YoYo.with(Techniques.FadeOut)
                        .duration(100)
                        .repeat(0)
                        .playOn(allcards);

                rentdetails.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        allcards.setVisibility(View.GONE);

                        YoYo.with(Techniques.FadeInUp)
                                .duration(100)
                                .repeat(0)
                                .playOn(rentdetails);
                    }
                }, 200);

               /* YoYo.with(Techniques.FadeInUpUp)
                        .duration(700)
                        .repeat(0)
                        .playOn(allcards);*/
               // allcards.setVisibility(View.GONE);
               // rentdetails.setVisibility(View.VISIBLE);

            }
        });

        rentdetailsback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YoYo.with(Techniques.FadeOut)
                        .duration(200)
                        .repeat(0)
                        .playOn(rentdetails);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        rentdetails.setVisibility(View.GONE);

                        allcards.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.FadeInUp)
                                .duration(200)
                                .repeat(0)
                                .playOn(allcards);
                    }
                }, 200);

               /* allcards.setVisibility(View.VISIBLE);
                rentdetails.setVisibility(View.GONE);*/
            }
        });


        cancelsubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int subscriptionId = PowerPreference.getDefaultFile().getInt("subscriptionId");
                if (subscriptionId!=0){
              // onSlideViewButtonClick(v);
                    Progresslinear.setVisibility(View.VISIBLE);
                    cancelsubscription.setVisibility(View.GONE);
                cancelorreactivate();
                }else{
                    Toast.makeText(getContext(), "System Error", Toast.LENGTH_SHORT).show();
                }

               /* Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                        crossxwhite.performClick();
                        rentdetailsback.performClick();
                    }
                }, 2000);*/


            }
        });

        crossxwhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSlideViewButtonClick(v);
            }
        });
    }

    private void updatePassword(String pone, String ptwo) {

        String base64streettxt= null,base64towntxt= null;
        try {
            //streettxt
            byte[] data6 = pone.getBytes("UTF-8");
            base64streettxt = Base64.encodeToString(data6, Base64.DEFAULT);
            Log.i("Base 64 ", base64streettxt);

            //towntxt
            byte[] data7 = ptwo.getBytes("UTF-8");
            base64towntxt = Base64.encodeToString(data7, Base64.DEFAULT);
            Log.i("Base 64 ", base64towntxt);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //=======================================================================================
        final int userid = PowerPreference.getDefaultFile().getInt("userid");
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<UpdatePasswordModelClass> call = apiInterface.updatePassword(userid,base64streettxt,
                base64towntxt);
        call.enqueue(new Callback<UpdatePasswordModelClass>() {
            @Override
            public void onResponse(Call<UpdatePasswordModelClass> call, Response<UpdatePasswordModelClass> response) {


                UpdatePasswordModelClass reOrderModelClass= response.body();

                assert reOrderModelClass != null;

                if (reOrderModelClass.status.equals("false")) {
                  //  Toast.makeText(getActivity(), ""+reOrderModelClass.message, Toast.LENGTH_SHORT).show();
                    snackbar = TSnackbar.make(coo, reOrderModelClass.message, TSnackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundResource(R.drawable.roundborderred);
                    //snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));

                    TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                    textView.setTextColor(Color.BLACK);
                    snackbar.show();
                }else {
                    progressLinear.setVisibility(View.GONE);

                    snackbar = TSnackbar.make(coo, reOrderModelClass.message, TSnackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundResource(R.drawable.roundbordegren);
                    //snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));

                    TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                    textView.setTextColor(Color.BLACK);
                    snackbar.show();


                    getuserprofile(userid,false);


                    YoYo.with(Techniques.FadeOut)
                            .duration(200)
                            .repeat(0)
                            .playOn(updatepasscardclick);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            updatepasscardclick.setVisibility(View.GONE);
                            confirmc.setVisibility(View.GONE);
                            allcards.setVisibility(View.VISIBLE);

                            YoYo.with(Techniques.FadeInUp)
                                    .duration(200)
                                    .repeat(0)
                                    .playOn(updatepasscardclick);
                            YoYo.with(Techniques.FadeInUp)
                                    .duration(200)
                                    .repeat(0)
                                    .playOn(allcards);
                        }
                    }, 200);
                }


                //---------------
                /*YoYo.with(Techniques.FadeOut)
                        .duration(400)
                        .repeat(0)
                        .playOn(addresscardclick);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        addresscardclick.setVisibility(View.GONE);

                        allcards.setVisibility(View.VISIBLE);
                        addressone.setVisibility(View.VISIBLE);

                        addressone.setVisibility(View.VISIBLE);
                        addresstwo.setVisibility(View.GONE);
                        addressbutton.setVisibility(View.GONE);

                        YoYo.with(Techniques.FadeInUp)
                                .duration(700)
                                .repeat(0)
                                .playOn(allcards);
                        YoYo.with(Techniques.FadeInUp)
                                .duration(700)
                                .repeat(0)
                                .playOn(addressone);
                    }
                }, 400);*/
            }

            @Override
            public void onFailure(Call<UpdatePasswordModelClass> call, Throwable t) {
                // PowerPreference.getDefaultFile().putBoolean("loggedin",false);
                //viewDialog.hideDialog();
                Toast.makeText(getActivity(), ""+t, Toast.LENGTH_SHORT).show();
                progressLinear.setVisibility(View.GONE);
            }
        });



    }

    private void updateAddress(String town, String street, String post, String door) {
        String base64postcodetxt= null ,base64doortxt= null,base64streettxt= null,base64towntxt= null;
        try {
            //postcodetxt
            byte[]  data4= post.getBytes("UTF-8");
            base64postcodetxt = Base64.encodeToString(data4, Base64.DEFAULT);
            Log.i("Base 64 ", base64postcodetxt);

            //doortxt
            byte[] data5 = door.getBytes("UTF-8");
            base64doortxt = Base64.encodeToString(data5, Base64.DEFAULT);
            Log.i("Base 64 ", base64doortxt);

            //streettxt
            byte[] data6 = street.getBytes("UTF-8");

            base64streettxt = Base64.encodeToString(data6, Base64.DEFAULT);

            Log.i("Base 64 ", base64streettxt);




            //towntxt
            byte[] data7 = town.getBytes("UTF-8");

            base64towntxt = Base64.encodeToString(data7, Base64.DEFAULT);

            Log.i("Base 64 ", base64towntxt);



        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        }

        //=======================================================================================
            final int userid = PowerPreference.getDefaultFile().getInt("userid");
            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<UpdateAddressModelClass> call = apiInterface.updateAddress(userid,base64postcodetxt,
                    base64doortxt,base64streettxt,base64towntxt);
            call.enqueue(new Callback<UpdateAddressModelClass>() {
                @Override
                public void onResponse(Call<UpdateAddressModelClass> call, Response<UpdateAddressModelClass> response) {

                    UpdateAddressModelClass reOrderModelClass= response.body();

                    assert reOrderModelClass != null;
                    if (reOrderModelClass.status) {
                        Toast.makeText(getActivity(), ""+reOrderModelClass.message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), ""+reOrderModelClass.message, Toast.LENGTH_SHORT).show();
                    }
                    getuserprofile(userid,false);
                    //---------------
                    YoYo.with(Techniques.FadeOut)
                            .duration(200)
                            .repeat(0)
                            .playOn(addresscardclick);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            addresscardclick.setVisibility(View.GONE);

                            allcards.setVisibility(View.VISIBLE);
                            addressone.setVisibility(View.VISIBLE);

                            addressone.setVisibility(View.VISIBLE);
                            addresstwo.setVisibility(View.GONE);
                            addressbutton.setVisibility(View.GONE);

                            YoYo.with(Techniques.FadeInUp)
                                    .duration(200)
                                    .repeat(0)
                                    .playOn(allcards);
                            YoYo.with(Techniques.FadeInUp)
                                    .duration(200)
                                    .repeat(0)
                                    .playOn(addressone);
                        }
                    }, 200);


                }

                @Override
                public void onFailure(Call<UpdateAddressModelClass> call, Throwable t) {
                    // PowerPreference.getDefaultFile().putBoolean("loggedin",false);
                    //viewDialog.hideDialog();
                    Toast.makeText(getActivity(), ""+t, Toast.LENGTH_SHORT).show();
                }
            });


    }


    // slide the view from below itself to the current position
    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void onSlideViewButtonClick(View view) {
        if (isUp) {
            slideDown(layoutgone);
            // myButton.setText("Slide up");
        } else {
            slideUp(layoutgone);
            // myButton.setText("Slide down");
        }
        isUp = !isUp;
    }

    private void getuserprofile(Integer userid,boolean b) {
        Log.d(TAG, "loginapi userid: "+userid );

        if (getActivity()!=null) {
          //  Bitmap blurredBitmap = GaussianBlur.with(getContext()).render(R.drawable.demo1);
            if (b)
            ((MainActivity)getActivity()).showloader();
        }
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MyProfileModelClass> call = apiInterface.myprofile(userid);
        call.enqueue(new Callback<MyProfileModelClass>() {
            @Override
            public void onResponse(Call<MyProfileModelClass> call, Response<MyProfileModelClass> response) {
                if (getActivity()!=null) {
                    pullRefreshLayout.setRefreshing(false);
                    ((MainActivity)getActivity()).hideloader();
                    MyProfileModelClass myProfileModelClass = response.body();
                    Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if (myProfileModelClass.errorCode.equals("0")) {
                        PowerPreference.getDefaultFile().putBoolean("loggedin", true);
                        PowerPreference.getDefaultFile().putString("profile",new Gson().toJson(response.body()));

                        Log.d(TAG, "onResponsedddddg: "+myProfileModelClass.subscription.get(0).subscriptionId);

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

                        //productrent image_url
                        PowerPreference.getDefaultFile().putBoolean("rentstatus", myProfileModelClass.productCheck.get(0).status);

                        String p = myProfileModelClass.subscription.get(0).subscriptionname;
//                        Log.d("Package 2 ", "TIER " + String.valueOf(p.charAt(p.length() - 1)));

                        displaynameEdittext.setText(myProfileModelClass.userDetail.get(0).username);
                        displaynamecard.setText(myProfileModelClass.userDetail.get(0).username);
                        towncity.setText(myProfileModelClass.userExtraDetail.get(0).townCity);
                        try {
                            packagename.setText("TIER " + String.valueOf(p.charAt(p.length() - 1)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        packagenameone.setText(myProfileModelClass.subscription.get(0).subscriptionname);
                        renewsString.setText("Renews " + myProfileModelClass.subscription.get(0).remainingDaysOfSubscription + " days from now");
                        renewstwostring.setText("Renews " + myProfileModelClass.subscription.get(0).remainingDaysOfSubscription + " days from now");

                        if (myProfileModelClass.userExtraDetail.get(0).streetAddress.equals(""))
                            textviewaddress.setText(streeaaddress);
                        else
                            textviewaddress.setText(myProfileModelClass.userExtraDetail.get(0).doorNumber+myProfileModelClass.userExtraDetail.get(0).streetAddress);

                        addresstext.setText(myProfileModelClass.userExtraDetail.get(0).streetAddress);


                        Log.d(TAG, "onResponse:passStrength " + myProfileModelClass.passStrength.get(0));
                        passstrength.setText(myProfileModelClass.passStrength.get(0));

                   /* for (int i=0; i<myProfileModelClass.productRent .size(); i++) {
                        Log.d(TAG, "onResponse:myProfileModelClass.productRent  "+myProfileModelClass.productRent.get(i).imageUrl);
                    }*/
                        if (Integer.parseInt(myProfileModelClass.subscription.get(0).remainingDaysOfSubscription) >= 0) {
                            cancelsubscription.setVisibility(View.VISIBLE);
                            subscribetextview.setText("Unsubscribe");
                        } else {
                            cancelsubscription.setVisibility(View.GONE);
                            packagename.setText("Subscribe");
                            subscribetextview.setText("Learn more");
                            renewstwostring.setText("No active subscription");
                            renewsString.setText("No active subscription");
                        }

                        if ( myProfileModelClass.productRent.size()>1){
                            rentt.setText("Rented " + myProfileModelClass.productRent.size() + " Games");
                        }else {
                            rentt.setText("Rented " + myProfileModelClass.productRent.size() + " Game");
                        }


                        if (myProfileModelClass.productRent.size() > 0) {
                            packagetwolinear.setText("you have rented " + myProfileModelClass.productRent.size() + " Games this month");

                        } else {
                            packagetwolinear.setText(" ");
                        }

                        RecyclerView.LayoutManager ee = new GridLayoutManager(getContext(), 2);
                        productRents_product_recycler.setLayoutManager(ee);
                        Log.d(TAG, "onResponse:myProfileModelClass.productRent size  " + myProfileModelClass.productRent.size());
                        productRentsRecycler = new ProductRentsRecycler(getContext(), myProfileModelClass.productRent);
                        productRents_product_recycler.setAdapter(productRentsRecycler);


                     /*   if (myProfileModelClass.subscription.get(0).subscriptionId!=0)
                        getsub(myProfileModelClass.subscription.get(0).subscriptionId);*/

                        // startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    } else {
                        Toast.makeText(getContext(), "" + myProfileModelClass.message, Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<MyProfileModelClass> call, Throwable t) {
                PowerPreference.getDefaultFile().putBoolean("loggedin",false);
                Toast.makeText(getContext(), ""+t, Toast.LENGTH_SHORT).show();
                if (getActivity()!=null) {
                    ((MainActivity)getActivity()).hideloader();
                    pullRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    public  void cancelorreactivate(){
        final int userid = PowerPreference.getDefaultFile().getInt("userid");
        final int subscriptionId = PowerPreference.getDefaultFile().getInt("subscriptionId");

        Log.d(TAG, "cancelorreactivate: userid "+userid+" subscriptionId "+subscriptionId);


        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<CancelReactivateGetModelClass> call = apiInterface.getcancelreactivatesubscription(userid, String.valueOf(subscriptionId));
        call.enqueue(new Callback<CancelReactivateGetModelClass>() {
            @Override
            public void onResponse(Call<CancelReactivateGetModelClass> call, Response<CancelReactivateGetModelClass> response) {

                Progresslinear.setVisibility(View.GONE);
                cancelsubscription.setVisibility(View.VISIBLE);

                CancelReactivateGetModelClass cancelReactivateGetModelClass= response.body();
               // cancelsubscription.setText(cancelReactivateGetModelClass.message);

               // crossxwhite.performClick();
                rentdetailsback.performClick();
                getuserprofile(userid,false);

                assert cancelReactivateGetModelClass != null;
                if (cancelReactivateGetModelClass.status.equals("false")){
                    snackbar = TSnackbar.make(coo, cancelReactivateGetModelClass.message, TSnackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundResource(R.drawable.roundborderred);
                    //snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));

                    TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                    textView.setTextColor(Color.BLACK);
                    snackbar.show();
                }else {
                    snackbar = TSnackbar.make(coo, cancelReactivateGetModelClass.message, TSnackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundResource(R.drawable.roundbordergreen);
                    //snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));

                    TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                    textView.setTextColor(Color.BLACK);
                    snackbar.show();
                }
              //  Toast.makeText(getContext(), ""+cancelReactivateGetModelClass.message, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<CancelReactivateGetModelClass> call, Throwable t) {

                Progresslinear.setVisibility(View.GONE);
                cancelsubscription.setVisibility(View.VISIBLE);
                // PowerPreference.getDefaultFile().putBoolean("loggedin",false);
                //viewDialog.hideDialog();
             //   Toast.makeText(getActivity(), ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
