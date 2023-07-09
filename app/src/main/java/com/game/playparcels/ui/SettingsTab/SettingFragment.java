package com.game.playparcels.ui.SettingsTab;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.game.playparcels.Activity.FingerprintActivity;

import com.game.playparcels.Activity.SplashActivity;
import com.game.playparcels.Activity.TermsNConditions;
import com.game.playparcels.R;
import com.preference.PowerPreference;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

import static com.facebook.FacebookSdk.getApplicationContext;


public class SettingFragment extends Fragment {

    Boolean loggedin = false;
    TextView pushtext;
    TextView socialtext;
    TextView biotext;
    TextView version;
    TextView termsbtn;
    TextView guestwarning;
    TextView textpp;
    TextView settingbtn;
    RelativeLayout settinga;
    RelativeLayout settingc;
    WebView pWebViewId;


    //   private SettingViewModel mViewModel;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }


    String TAG = "fjpoisjpoisjp";
    SwitchCompat fingerprintSw;
    SwitchCompat pushNotification;
    SwitchCompat socialswitch;
    SwitchCompat darkmodesw;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        loggedin = PowerPreference.getDefaultFile().getBoolean("loggedin", false);


        return inflater.inflate(R.layout.setting_fragment, container, false);


    }


    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        fingerprintSw = v.findViewById(R.id.fingerprintSw);
        pushNotification = v.findViewById(R.id.pushNotification);
        pushtext = v.findViewById(R.id.pushtext);
        socialtext = v.findViewById(R.id.socialtext);
        termsbtn = v.findViewById(R.id.termsbtn);
        biotext = v.findViewById(R.id.biotext);
        socialswitch = v.findViewById(R.id.socialswitch);
        guestwarning = v.findViewById(R.id.guestwarning);
        settingbtn = v.findViewById(R.id.settingbtn);
        textpp = v.findViewById(R.id.textpp);
        settinga = v.findViewById(R.id.settinga);
        darkmodesw = v.findViewById(R.id.darkmodesw);
        pWebViewId = v.findViewById(R.id.pWebViewId);

        final ViewStub viewStub = v.findViewById(R.id.settingcstub);

        // Start of code to manage all ids and implement processes in relation to them inside the Viewstub
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                View inflatedView = viewStub.inflate();
                settingc = inflatedView.findViewById(R.id.settingc);
                YoYo.with(Techniques.FadeInDown)
                        .duration(1000)
                        .repeat(0)
                        .playOn(settingc);
                version = inflatedView.findViewById(R.id.version);

                version.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        // Code Below constructs Alertbox.
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        // Sets Title of AlertBox
                        alertDialog.setTitle("Changelog");
                        // Sets message of Alertbox in html (this has been done to use html increasing methods of organisation and performance.
                        alertDialog.setMessage(Html.fromHtml("<h1>Version 1.0.0</h1><h5>Coming Soon</h5><h6>News tab</h6><p>Here at Gameplayparcels we are working hard to introduce our news content into the mobile app complementing your game browsing experience!.<br><h5>Improvements</h5><p>• Improved app launching time.</p><p>• Improved general performance across all mobile devices.</p><p>• Improved performance when viewing search results on older devices</p><p>• Improved" +
                                " loading speed of subscription page</p><br><h5>Bug Fixes</h5><p>• Addressed app crash on viewing any related games.</p>" +
                                "<p>• Addressed logging in / registrating through social media.</p><p>• Addressed rare searching issue which caused keyboard to cut off games.</p>" +
                                "<p>• Addressed searching issue that limited search results.</p><p>• Addressed loader issue which caused it to not show.</p>" +
                                "<p>• Addressed searching issue that caused loader to flicker </p><p>• Addressed browsing issue that caused random games to crash</p>"));

                        // Sets button text
                        alertDialog.setButton("Dismiss..", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // here you can add functions
                            }
                        });

                        alertDialog.show();  // shows alertbox
                    }

                });

            }
        }, 400);
        // End of code to manage all ids and implement processes in relation to them inside the Viewstub


        // Start of code to send user to T&C layout.
        termsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(LoginActivity.this, "In Development", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), TermsNConditions.class));
            }
        });
        // End of code to send user to T&C layout..

        // Start of code to send user to Webview containing Privacy policy.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textpp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toasty.info(getActivity(), "Loading Page", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getActivity(), PrivacyWebView.class));
                    }
                });
            }
        }, 25);
        // End of code to send user to Webview containing Privacy policy.

        // Start of code to send user to register/login on splash layout.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                settingbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), SplashActivity.class)); //LoginActivity
                        Objects.requireNonNull(getActivity()).finish();
                    }
                });
            }
        }, 250);
        // end of code to send user to register/login on splash layout.

        //Start of code to check if user is logged in
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loggedin) {
                    fingerprintSw.setEnabled(true);
                    darkmodesw.setEnabled(true);
                    socialswitch.setEnabled(true);
                    pushNotification.setEnabled(true);
                    settinga.setAlpha(1f);
                    guestwarning.setVisibility(View.GONE);
                }
            }
        }, 25);
        //End of code to check if user is logged in

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!loggedin) {
                    guestwarning.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeInUp)
                            .duration(1000)
                            .repeat(0)
                            .playOn(guestwarning);
                    settingbtn.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeInDown)
                            .duration(2500)
                            .repeat(0)
                            .playOn(settingbtn);
                }
            }
        }, 50);

        if (PowerPreference.getDefaultFile().getBoolean("fingerprint", false) ||
                PowerPreference.getDefaultFile().getBoolean("fingerprintS", false)) {

            Log.d(TAG, "onCreateView51: " + true);
            fingerprintSw.setChecked(true);
            PowerPreference.getDefaultFile().putBoolean("fingerprint", true);
        } else {
            Log.d(TAG, "onCreateView55: " + false);
            fingerprintSw.setChecked(false);
            PowerPreference.getDefaultFile().putBoolean("fingerprint", false);
        }

        if (PowerPreference.getDefaultFile().getBoolean("notification", false)) {
            pushNotification.setChecked(true);
        } else {
            pushNotification.setChecked(false);
        }

        pushNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    PowerPreference.getDefaultFile().putBoolean("que", true);
                } else {
                    PowerPreference.getDefaultFile().getBoolean("notification", false);
                }
            }
        });


        fingerprintSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                if (isChecked) {
                    Log.d(TAG, "onCheckedChanged: " + isChecked);
                    if (PowerPreference.getDefaultFile().getBoolean("fingerprint", false) ||
                            PowerPreference.getDefaultFile().getBoolean("fingerprintS", false)) {
                        PowerPreference.getDefaultFile().getBoolean("fingerprint", true);

                    } else {
                        Intent inn = new Intent(getContext(), FingerprintActivity.class);
                        inn.putExtra("type", "layout3");
                        startActivity(inn);
                    }


                } else {
                    Log.d(TAG, "onCheckedChanged: " + isChecked);
                    PowerPreference.getDefaultFile().putBoolean("fingerprint", isChecked);
                }
            }
        });

    }

        @Override
        public void onResume () {

//        Toast.makeText(getContext(), "onResume "+(PowerPreference.getDefaultFile().getBoolean("fingerprint",false))
//                +" "+PowerPreference.getDefaultFile().getBoolean("fingerprintS",false), Toast.LENGTH_SHORT).show();

            if (PowerPreference.getDefaultFile().getBoolean("fingerprint", false) ||
                    PowerPreference.getDefaultFile().getBoolean("fingerprintS", false)) {
                Log.d(TAG, "onResume: " + true);
                fingerprintSw.setChecked(true);
                PowerPreference.getDefaultFile().putBoolean("fingerprint", true);
            } else {
                Log.d(TAG, "onResume: " + false);
                fingerprintSw.setChecked(false);
                PowerPreference.getDefaultFile().putBoolean("fingerprint", false);
            }
            super.onResume();
        }


        @Override
        public void onStart () {
//        Toast.makeText(getContext(), "onstart "+(PowerPreference.getDefaultFile().getBoolean("fingerprint",false))
//                +" "+PowerPreference.getDefaultFile().getBoolean("fingerprintS",false), Toast.LENGTH_SHORT).show();

            if (PowerPreference.getDefaultFile().getBoolean("fingerprint", false) ||
                    PowerPreference.getDefaultFile().getBoolean("fingerprintS", false)) {

                Log.d(TAG, "onStart: " + true);
                fingerprintSw.setChecked(true);
                PowerPreference.getDefaultFile().putBoolean("fingerprint", true);
            } else {

                Log.d(TAG, "onStart: " + false);
                fingerprintSw.setChecked(false);
                PowerPreference.getDefaultFile().putBoolean("fingerprint", false);
            }
            super.onStart();
        }

    }
