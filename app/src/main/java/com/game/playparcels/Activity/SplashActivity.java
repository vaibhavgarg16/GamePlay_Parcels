package com.game.playparcels.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.androidadvance.topsnackbar.TSnackbar;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.game.playparcels.Login.ForgotPasswordActivity;
import com.game.playparcels.ModelClasses.LoginModelClass;
import com.game.playparcels.ModelClasses.SignupModelClass;
import com.game.playparcels.ProgressBarAnimation;
import com.game.playparcels.R;
import com.game.playparcels.SevicesApi.ApiClient;
import com.game.playparcels.SevicesApi.ApiInterface;
import com.game.playparcels.ui.AccountTab.SubscribeWebView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.preference.PowerPreference;
import com.rw.keyboardlistener.KeyboardUtils;
import com.ybs.passwordstrengthmeter.PasswordStrength;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;
    private static final int SPLASH_ANIMATION_TIME_OUT = 400;

    CallbackManager mCallbackManager;
    String TAG = "SplashActivityTAG";
    ImageView splashicon;
    CardView logincard, registercard;
    Boolean loggedin = false;

    ImageButton backpress;
    //ImageView imageviewback;
    TextView gotoregister;
    LinearLayout loginbtn;
    EditText emailEditText, passwordEditText;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextView forgotpassword;
    ImageView logingoogle, logingooglereg;
    LoginButton loginButton, loginButton2;
    TextView signintxt;
    ProgressBar signintxtprogress, signintxtprogressreg;
    WebView mWebview;

    //-----------------------------------Googele Sign In -----------------------------------//
    private FirebaseAuth mAuth;
    Integer RC_SIGN_IN = 32131;
    Integer REG_SIGN_IN = 32132;
    GoogleSignInClient mGoogleSignInClient;


    TSnackbar snackbar;
    CoordinatorLayout coo;

    TextView skiptext;
    TextView newbuttonz;
    LinearLayout idtworeg, idtworeg1;


    //----------------------------------reg-------------------------------

    Boolean visible = false, visiblelog = false;
    CheckBox tncCheck;
    TextView gotologin;
    Button Continue;
    EditText passwordreg, passwordregconfirm, emailreg, usernamereg, forenamereg;
    String emailPatternreg = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String TAGreg = "RegisterActivity";
    int Strength = 0;

    TSnackbar snackbarv;
    CoordinatorLayout cooreg;

    String Username, Email, Password, Forenamereg;

    //=============================reg2-----------------------
    LinearLayout Register;
    String passwordreg2, emailreg2;
    EditText postcode, door, street, town;
    TextView signintxtreg;
    ProgressBar progressBar2;
    String st = "";

    ProgressBar skipProgressId;
    String deviceToken;

    @SuppressLint({"ResourceAsColor", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        PowerPreference.init(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();

        //twitterHelper = new TwitterHelper(this, this); // Twitter Initialization
        // imageviewback=findViewById(R.id.imageviewback);
        skipProgressId = findViewById(R.id.skipProgressId);
        backpress = findViewById(R.id.backpress);
        gotoregister = findViewById(R.id.gotoregister);
        loginbtn = findViewById(R.id.loginbtn);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        logingoogle = findViewById(R.id.logingoogle);
        logingooglereg = findViewById(R.id.logingooglereg);
        forgotpassword = findViewById(R.id.forgotpassword);
        coo = findViewById(R.id.coo);

        splashicon = findViewById(R.id.splashicon);
        splashicon.setVisibility(View.VISIBLE);
        logincard = findViewById(R.id.logincard);
        registercard = findViewById(R.id.registercard);
        registercard.setVisibility(View.GONE);
        logincard.setVisibility(View.GONE);
        skiptext = findViewById(R.id.skiptext);

        idtworeg = findViewById(R.id.idtworeg);
        idtworeg1 = findViewById(R.id.idtworeg1);

        //----------------------------login----------------------------------------

        signintxt = findViewById(R.id.signintxt);
        signintxtprogress = findViewById(R.id.signintxtprogress);
        signintxtprogressreg = findViewById(R.id.signintxtprogressreg);

        //------------------------------reg-----------------------------
        tncCheck = findViewById(R.id.tncCheck);
        gotologin = findViewById(R.id.gotologin);
        Continue = findViewById(R.id.Continue);


        emailreg = findViewById(R.id.emailreg);
        usernamereg = findViewById(R.id.username);
        forenamereg = findViewById(R.id.forenamereg);
        passwordreg = findViewById(R.id.passwordreg);
        passwordregconfirm = findViewById(R.id.passwordregconfirm);
        cooreg = findViewById(R.id.cooreg);
        progressBar2 = findViewById(R.id.progressBarreg2);

        //==========================================================

        //----------reg2--------------------------------
        Register = findViewById(R.id.Register);
        postcode = findViewById(R.id.postode);
        door = findViewById(R.id.door);
        street = findViewById(R.id.street);
        town = findViewById(R.id.town);
        signintxtreg = findViewById(R.id.signintxtreg);
        newbuttonz = findViewById(R.id.newbuttonz);

        mCallbackManager = CallbackManager.Factory.create();



        loginButton = (LoginButton) findViewById(R.id.loginFaceBook);
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                    }
                });
            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        deviceToken = token;
                    }
                });



        loginButton2 = (LoginButton) findViewById(R.id.loginFaceBookreg);
        loginButton2.setReadPermissions("email", "public_profile");
        loginButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                loginButton2.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken2(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                    }
                });
            }
        });

        loggedin = PowerPreference.getDefaultFile().getBoolean("loggedin", false);
        if (PowerPreference.getDefaultFile().getBoolean("keepsigned", false)) {
            SPLASH_TIME_OUT = 1500;
        }


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        //Then we will get the GoogleSignInClient object from GoogleSignIn class
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        Intent intent = getIntent();
        if (intent != null) {
            st = intent.getStringExtra("splash");
            if (st == null) {
                st = "";
                initIalizeSplash();
            } else {
                if (st.equals("login")) {
                    // Toast.makeText(this, "Toast st - "+st, Toast.LENGTH_SHORT).show();
                    splashicon.setVisibility(View.GONE);
                    YoYo.with(Techniques.FadeOut)
                            .duration(300)
                            .repeat(0)
                            .playOn(registercard);


                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            registercard.setVisibility(View.GONE);
                            logincard.setVisibility(View.VISIBLE);

                            YoYo.with(Techniques.FadeIn)
                                    .duration(900)
                                    .repeat(0)
                                    .playOn(logincard);
                        }
                    }, 300);   //5 seconds

                }
            }


        } else {
            initIalizeSplash();
        }


        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YoYo.with(Techniques.FadeOut)
                        .duration(300)
                        .repeat(0)
                        .playOn(logincard);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        logincard.setVisibility(View.GONE);
                        registercard.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.FadeIn)
                                .duration(400)
                                .repeat(0)
                                .playOn(registercard);
                    }
                }, 300);   //5 seconds

               /* startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();*/
            }
        });
       /* backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                onBackPressed();
            }
        });*/

        /*fblogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/




//----------------------------------KeyBoard------------------------------------
        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible) {
                if (isVisible && getCurrentFocus().getId() == passwordreg.getId()) {
                    progressBar2.setVisibility(View.VISIBLE);
                } else {
                    progressBar2.setVisibility(View.GONE);
                }

                Log.d("keyboard", "keyboard visible: " + isVisible);
            }
        });

        passwordreg.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    progressBar2.setVisibility(View.GONE);
                } else {
                    progressBar2.setVisibility(View.VISIBLE);
                }
            }
        });

        logingoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinGoogle();
            }
        });

        logingooglereg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                signinGooglereg();
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(LoginActivity.this, "In Development", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                //  startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
            }
        });

        skiptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skiptext.setVisibility(View.GONE);
                skipProgressId.setVisibility(View.VISIBLE);

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

        newbuttonz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(LoginActivity.this, "In Development", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), TermsNConditions.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                //  startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
            }
        });


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                String Email = emailEditText.getText().toString().trim();
                String Pass = passwordEditText.getText().toString().trim();


                if (Email.equals("")) {
                    emailEditText.setError("Email can't be empty");
                    Animation shake = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.shake);
                    emailEditText.startAnimation(shake);
                    emailEditText.requestFocus();
                    return;

                }

                if (Pass.equals("")) {
                    passwordEditText.setError("Password can't be empty");
                    Animation shake = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.shake);
                    passwordEditText.startAnimation(shake);
                    passwordEditText.requestFocus();
                    return;
                }

                loginapi(Email, Pass);
            }
        });


        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;


                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if (!visiblelog) {
                            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                            passwordEditText.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                            visiblelog = true;
                            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visibility_off_24, 0);
                        } else {
                            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            passwordEditText.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password
                            visiblelog = false;
                            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_24, 0);
                        }

                        return true;
                    }
                }
                return false;
            }
        });


        //-------------------------------------------------------------------------------------------------reg
        passwordreg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                updatePasswordStrengthView(s.toString());

            }
        });


        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username = usernamereg.getText().toString().trim();
                Email = emailreg.getText().toString().trim();
                Password = passwordreg.getText().toString().trim();
                Forenamereg = forenamereg.getText().toString().trim();

                Log.d(TAG, "Continue: " + Email + " " + Password);

                if (Forenamereg.equals("")) {
                    forenamereg.setError("Username can't be Empty");
                    Animation shake = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.shake);
                    forenamereg.startAnimation(shake);
                    forenamereg.requestFocus();
                    return;
                }
                if (Email.equals("")) {
                    emailreg.setError("Email can't be Empty");
                    Animation shake = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.shake);
                    emailreg.startAnimation(shake);
                    emailreg.requestFocus();
                    return;

                }
                if ( /*!(Email.matches(emailPattern))*/!(Email.contains("@") && Email.contains("."))) {
                    emailreg.setError("Please Enter correct email");
                    Animation shake = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.shake);
                    emailreg.startAnimation(shake);
                    emailreg.requestFocus();
                    return;
                }

                if (Password.equals("")) {
                    passwordreg.setError("Password Cant be Empty");
                    Animation shake = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.shake);
                    passwordreg.startAnimation(shake);
                    passwordreg.requestFocus();
                    return;
                }

                if (!passwordreg.getText().toString().equals(passwordregconfirm.getText().toString())) {
                    passwordregconfirm.setError("Password does not match");
                    Animation shake = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.shake);
                    passwordregconfirm.startAnimation(shake);
                    passwordregconfirm.requestFocus();
                    return;
                }

                if (!tncCheck.isChecked()) {
                    ShowToastreg("Please Accept Term And Condition");
                    return;
                }


                idtworeg.setVisibility(View.GONE);
                idtworeg1.setVisibility(View.VISIBLE);



                /*  Intent intent =new Intent(getApplicationContext(), RegisterTwoActivity.class);

                intent.putExtra("email",Email);
                intent.putExtra("password",Password);
                startActivity(intent);*/
                //finish();
            }
        });
        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YoYo.with(Techniques.FadeOut)
                        .duration(300)
                        .repeat(0)
                        .playOn(registercard);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        registercard.setVisibility(View.GONE);

                        logincard.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.FadeIn)
                                .duration(400)
                                .repeat(0)
                                .playOn(logincard);
                    }
                }, 300);   //5 seconds



             /*   startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();*/
            }
        });

        passwordreg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;


                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (passwordreg.getRight() - passwordreg.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if (!visible) {
                            passwordreg.setInputType(InputType.TYPE_CLASS_TEXT);
                            passwordreg.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                            visible = true;
                            passwordreg.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visibility_off_24, 0);

                        } else {
                            passwordreg.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            passwordreg.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password
                            visible = false;
                            passwordreg.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_24, 0);
                        }

                        return true;
                    }
                }
                return false;
            }
        });


        //------------------reg2------------------------------------------------------------
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String postcodetxt, doortxt, streettxt, towntxt, spinnertxt;


                postcodetxt = postcode.getText().toString().trim();
                doortxt = door.getText().toString().trim();
                streettxt = street.getText().toString().trim();
                towntxt = town.getText().toString().trim();
                spinnertxt = "PS4";//spinnertxtview.getText().toString().trim();


                if (postcodetxt.equals("") || doortxt.equals("") || streettxt.equals("") || towntxt.equals("")) {
                    //  Toast.makeText(RegisterTwoActivity.this, "All Fields Are Reuired", Toast.LENGTH_SHORT).show();
                    ShowToast("All Fields Are Reuired");
                    return;
                }
                if (spinnertxt.equalsIgnoreCase("Main Console")) {
                    //  Toast.makeText(RegisterTwoActivity.this, "Choose Main Console", Toast.LENGTH_SHORT).show();
                    ShowToast("Choose Main Console");
                    return;
                }
                registerapi(Email, Password, Forenamereg, Username, postcodetxt, doortxt, streettxt, towntxt, spinnertxt);

            }
        });


    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            PowerPreference.getDefaultFile().removeAsync("socialImageUrl");
                            PowerPreference.getDefaultFile().putString("socialImageUrl", String.valueOf(user.getPhotoUrl()));
                            loginSocialapi(user.getEmail(), user.getDisplayName());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SplashActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void handleFacebookAccessToken2(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            forenamereg.setText(user.getDisplayName());
                            emailreg.setText(user.getEmail());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SplashActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initIalizeSplash() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                splashicon.animate().scaleX(0.0f).scaleY(0.0f).setDuration(SPLASH_ANIMATION_TIME_OUT).start();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (st.equals("login")) {

                            YoYo.with(Techniques.FadeOut)
                                    .duration(300)
                                    .repeat(0)
                                    .playOn(registercard);


                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    registercard.setVisibility(View.GONE);
                                    logincard.setVisibility(View.VISIBLE);

                                    YoYo.with(Techniques.FadeIn)
                                            .duration(400)
                                            .repeat(0)
                                            .playOn(logincard);
                                }
                            }, 300);   //5 seconds

                        } else if (PowerPreference.getDefaultFile().getBoolean("fingerprint", false)) {
                            startActivity(new Intent(getApplicationContext(), FingerprintActivity.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                        } else if (PowerPreference.getDefaultFile().getBoolean("keepsigned", false)) {
                            Intent i = new Intent(getApplicationContext(), FingerprintActivity.class);
                            i.putExtra("keepsigned", true);
                            startActivity(i);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        } else {
                            if (loggedin) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                            } else {

                                logincard.setVisibility(View.VISIBLE);
                                YoYo.with(Techniques.FadeIn)
                                        .duration(400)
                                        .repeat(0)
                                        .playOn(logincard);
                            }
                        }

                    }
                }, SPLASH_ANIMATION_TIME_OUT);

            }
        }, SPLASH_TIME_OUT);
    }

    public void loginSocialapi(final String email, final String name) {
        signintxtprogress.setVisibility(View.VISIBLE);
        signintxt.setVisibility(View.GONE);
//        Log.d(TAG, "loginapi email password: " + email + " " + password);
        byte[] data, data1, data2;
        String base64email = null, base64type = null, base64name = null;
        String type = "social";

        try {
            data = email.getBytes("UTF-8");
            base64email = Base64.encodeToString(data, Base64.DEFAULT);
            Log.i("Base 64 ", base64email);

            data2 = type.getBytes("UTF-8");
            base64type = Base64.encodeToString(data2, Base64.DEFAULT);
            Log.i("Base 64 ", base64type);

            data1 = name.getBytes("UTF-8");
            base64name = Base64.encodeToString(data1, Base64.DEFAULT);
            Log.i("Base 64 ", base64name);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "loginapi:emailEn " + email + " " + base64email + " type" + type + " " + base64type + "first_name" + name + " " + base64name/*+ " decode "+decodeValue+" "+decodeValuep*/);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<LoginModelClass> call = apiInterface.loginSocial(base64email, base64type, "", base64name, "");

        final String finalBase64email = base64email;
        call.enqueue(new Callback<LoginModelClass>() {
            @Override
            public void onResponse(Call<LoginModelClass> call, Response<LoginModelClass> response) {
                LoginModelClass loginModelClass = response.body();
                Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                Log.d(TAG, "onResponse H: 1- " + new Gson().toJson(response.body()));
                Log.d(TAG, "onResponse H: 2- " + new Gson().toJson(response.code()));
                assert loginModelClass != null;
                try {
                    if (loginModelClass.status.equalsIgnoreCase("true")) {
                        PowerPreference.getDefaultFile().putBoolean("loggedin", true);
                        Log.d(TAG, "onResponse:--- " + loginModelClass.loginUserDetails.get(0).userid + " " + loginModelClass.loginUserDetails.get(0).role);
                        PowerPreference.getDefaultFile().putInt("userid", loginModelClass.loginUserDetails.get(0).userid);
                        PowerPreference.getDefaultFile().putString("role", loginModelClass.loginUserDetails.get(0).role);
                        PowerPreference.getDefaultFile().putString("baseEmail", finalBase64email);
                        firstTimePreferance();
                    } else {
                        ShowToast("Login Error");
                        if (loginModelClass.status.equalsIgnoreCase("false")) {
                            emailEditText.setError("Email or username does not match.");
                            passwordEditText.setError("Password Incorrect.");
                            Animation shake = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.shake);
                            emailEditText.startAnimation(shake);
                            passwordEditText.startAnimation(shake);
                            emailEditText.clearFocus();
                            passwordEditText.clearFocus();
                        }
                    }
                } catch (NullPointerException kj) {
                    loginbtn.performClick();
                } finally {
                    signintxtprogress.setVisibility(View.GONE);
                    signintxt.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<LoginModelClass> call, Throwable t) {
                PowerPreference.getDefaultFile().putBoolean("loggedin", false);

                signintxtprogress.setVisibility(View.GONE);
                signintxt.setVisibility(View.VISIBLE);
                ShowToast(t.getMessage());
                //Toast.makeText(LoginActivity.this, ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loginapi(final String email, final String password) {
        signintxtprogress.setVisibility(View.VISIBLE);
        signintxt.setVisibility(View.GONE);
        Log.d(TAG, "loginapi email password: " + email + " " + password);
        byte[] data, data1, data2;
        String base64email = null, base64password = null, base64type = null;
        String type = "email";

        try {
            data = email.getBytes("UTF-8");
            base64email = Base64.encodeToString(data, Base64.DEFAULT);
            Log.i("Base 64 ", base64email);
            data1 = password.getBytes("UTF-8");
            base64password = Base64.encodeToString(data1, Base64.DEFAULT);
            Log.i("Base 64 ", base64password);
            data2 = type.getBytes("UTF-8");
            base64type = Base64.encodeToString(data2, Base64.DEFAULT);
            Log.i("Base 64 ", base64type);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "loginapi:emailEn " + email + " " + base64email + " passwordEn " + password + " " + base64password + " type" + type + " " + base64type/*+ " decode "+decodeValue+" "+decodeValuep*/);
        ApiInterface apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<LoginModelClass> call = apiInterface.login(base64email, base64type, base64password, deviceToken, "", "", "");

        final String finalBase64email = base64email;
        call.enqueue(new Callback<LoginModelClass>() {
            @Override
            public void onResponse(Call<LoginModelClass> call, Response<LoginModelClass> response) {
                LoginModelClass loginModelClass = response.body();
                assert loginModelClass != null;
                try {
                    if (loginModelClass.status.equalsIgnoreCase("true")) {
                        PowerPreference.getDefaultFile().putBoolean("loggedin", true);
                        Log.d(TAG, "onResponse:--- " + loginModelClass.loginUserDetails.get(0).userid + " " + loginModelClass.loginUserDetails.get(0).role);
                        PowerPreference.getDefaultFile().putInt("userid", loginModelClass.loginUserDetails.get(0).userid);
                        PowerPreference.getDefaultFile().putString("role", loginModelClass.loginUserDetails.get(0).role);
                        PowerPreference.getDefaultFile().putString("email", loginModelClass.loginUserDetails.get(0).email);
                        PowerPreference.getDefaultFile().putString("password", loginModelClass.loginUserDetails.get(0).password);
                        PowerPreference.getDefaultFile().putString("streetaddress", loginModelClass.loginUserDetails.get(0).address);
                        PowerPreference.getDefaultFile().putString("baseEmail", finalBase64email);
                        firstTimePreferance();

                    } else {
                        ShowToast("Login Error");
                        if (loginModelClass.status.equalsIgnoreCase("false")) {
                            emailEditText.setError("Email or username does not match.");
                            Animation shake = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.shake);
                            emailEditText.startAnimation(shake);
                            passwordEditText.startAnimation(shake);
                            passwordEditText.setError("Password Incorrect.");
                            emailEditText.clearFocus();
                            passwordEditText.clearFocus();
                        }
                    }
                } catch (NullPointerException kj) {
                    loginbtn.performClick();
                } finally {
                    signintxtprogress.setVisibility(View.GONE);
                    signintxt.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<LoginModelClass> call, Throwable t) {
                PowerPreference.getDefaultFile().putBoolean("loggedin", false);
                //   progressBar.setVisibility(View.GONE);
                //viewDialog.hideDialog();
                signintxtprogress.setVisibility(View.GONE);
                signintxt.setVisibility(View.VISIBLE);
                ShowToast(t.getMessage());
                //Toast.makeText(LoginActivity.this, ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void firstTimePreferance() {
        //Code for popup window below in multi line comment section.
        View customViewPopup = LayoutInflater.from(SplashActivity.this).inflate(R.layout.first_popup, null);
        PopupWindow popupWindow = new PopupWindow(customViewPopup, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(false);
        popupWindow.setAnimationStyle(R.style.AnimationPopup);
        popupWindow.showAtLocation(customViewPopup, Gravity.CENTER, 0, 0);

        PowerPreference.getDefaultFile().putBoolean("keepsigned", true);
        PowerPreference.getDefaultFile().putBoolean("fingerprint", true);
        PowerPreference.getDefaultFile().putBoolean("notification", true);


        TextView messageButton = customViewPopup.findViewById(R.id.messageButton);
        Switch switch1 = customViewPopup.findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //   Toast.makeText(SplashActivity.this, "Is checked 1" + isChecked, Toast.LENGTH_SHORT).show();
                if (isChecked) {
                    PowerPreference.getDefaultFile().putBoolean("keepsigned", true);
                } else {
                    PowerPreference.getDefaultFile().putBoolean("keepsigned", false);
                }
            }
        });
        Switch switch2 = customViewPopup.findViewById(R.id.switch2);
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    PowerPreference.getDefaultFile().putBoolean("fingerprint", true);
                } else {
                    PowerPreference.getDefaultFile().putBoolean("fingerprint", false);
                }
            }
        });
        Switch switch3 = customViewPopup.findViewById(R.id.switch3);
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    PowerPreference.getDefaultFile().putBoolean("notification", true);
                } else {
                    PowerPreference.getDefaultFile().putBoolean("notification", false);
                }
            }
        });
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //Uncomment below code for alertdialog with full funciton.
        /*View customView = LayoutInflater.from(SplashActivity.this).inflate(R.layout.first_popup, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SplashActivity.this);
        dialogBuilder.setView(customView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
//        alertDialog.getWindow().setLayout(300, 400);
        alertDialog.setCancelable(false);
        TextView messageButton = customView.findViewById(R.id.messageButton);
        Switch switch1 = customView.findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(SplashActivity.this, "Is checked 1" + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        Switch switch2 = customView.findViewById(R.id.switch2);
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(SplashActivity.this, "Is checked 2" + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        Switch switch3 = customView.findViewById(R.id.switch3);
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(SplashActivity.this, "Is checked 3" + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        });*/
    }

    private void signinGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signinGooglereg() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REG_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        } else if (requestCode == REG_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogleReg(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(getApplicationContext(), "Google Login Successfull", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            PowerPreference.getDefaultFile().removeAsync("socialImageUrl");
                            PowerPreference.getDefaultFile().putString("socialImageUrl", String.valueOf(user.getPhotoUrl()));
                            loginSocialapi(user.getEmail(), user.getDisplayName());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Google Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void firebaseAuthWithGoogleReg(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            forenamereg.setText(user.getDisplayName());
                            emailreg.setText(user.getEmail());
//                            loginSocialapi(user.getEmail(),user.getDisplayName());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
      /*  Intent back = new Intent(getApplicationContext(), MainActivity.class);
        back.putExtra("frag","profile");
        startActivity(back);
        finish();*/

        if (idtworeg1.getVisibility() == View.VISIBLE) {
            backtoregone();
            return;
        }


        super.onBackPressed();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
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


    //-----------------------------reg
    private void updatePasswordStrengthView(String password) {

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarreg);
        TextView strengthView = (TextView) findViewById(R.id.password_strength);
        if (TextView.VISIBLE != strengthView.getVisibility())
            return;

        if (password.isEmpty()) {
            strengthView.setText("");

            progressBar.setProgress(0);
            progressBar2.setProgress(0);

            progressBar.setProgressTintList(ColorStateList.valueOf(Color.WHITE));
            progressBar2.setProgressTintList(ColorStateList.valueOf(Color.WHITE));
            strengthView.setTextColor(ColorStateList.valueOf(Color.WHITE));
            Strength = 1;
            ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, progressBar2.getProgress(), 0);
            anim.setDuration(1000);
            progressBar.startAnimation(anim);

            ProgressBarAnimation anim2 = new ProgressBarAnimation(progressBar2, progressBar2.getProgress(), 0);
            anim2.setDuration(1000);
            progressBar2.startAnimation(anim2);
            return;
        }

        PasswordStrength str = PasswordStrength.calculateStrength(password);
        strengthView.setText(str.getText(this));
        strengthView.setTextColor(str.getColor());

        //  progressBar.getProgressDrawable().setColorFilter(str.getColor(), android.graphics.PorterDuff.Mode.SRC_IN);

        progressBar.setProgressTintList(ColorStateList.valueOf(Color.WHITE));
        strengthView.setTextColor(ColorStateList.valueOf(Color.WHITE));

        progressBar2.setProgressTintList(ColorStateList.valueOf(Color.WHITE));

        if (str.getText(this).equals("Weak")) {
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
            progressBar2.setProgressTintList(ColorStateList.valueOf(Color.RED));
            strengthView.setTextColor(ColorStateList.valueOf(Color.RED));
            Strength = 1;
            ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, progressBar2.getProgress(), 25);
            anim.setDuration(1000);
            progressBar.startAnimation(anim);

            ProgressBarAnimation anim2 = new ProgressBarAnimation(progressBar2, progressBar2.getProgress(), 25);
            anim2.setDuration(1000);
            progressBar2.startAnimation(anim2);

        } else if (str.getText(this).equals("Medium")) {
            strengthView.setTextColor(ColorStateList.valueOf(Color.YELLOW));
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
            progressBar2.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
            ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, progressBar.getProgress(), 50);
            anim.setDuration(1000);
            progressBar.startAnimation(anim);


            progressBar2.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
            ProgressBarAnimation anim2 = new ProgressBarAnimation(progressBar2, progressBar2.getProgress(), 50);
            anim2.setDuration(1000);
            progressBar2.startAnimation(anim2);


            Strength = 2;
        } else if (str.getText(this).equals("Strong")) {
            ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, progressBar.getProgress(), 75);
            anim.setDuration(1000);
            progressBar.startAnimation(anim);
            strengthView.setTextColor(ColorStateList.valueOf(Color.GREEN));
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));

            progressBar2.setProgressTintList(ColorStateList.valueOf(Color.GREEN));


            ProgressBarAnimation anim2 = new ProgressBarAnimation(progressBar2, progressBar2.getProgress(), 75);
            anim2.setDuration(1000);
            progressBar2.startAnimation(anim2);
            strengthView.setTextColor(ColorStateList.valueOf(Color.GREEN));
            progressBar2.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
            Strength = 3;
        } else {
            ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, progressBar.getProgress(), 100);
            anim.setDuration(1000);
            progressBar.startAnimation(anim);
            strengthView.setTextColor(ColorStateList.valueOf(Color.GREEN));
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));

            progressBar2.setProgressTintList(ColorStateList.valueOf(Color.GREEN));


            ProgressBarAnimation anim2 = new ProgressBarAnimation(progressBar2, progressBar2.getProgress(), 100);
            anim2.setDuration(1000);
            progressBar2.startAnimation(anim2);
            strengthView.setTextColor(ColorStateList.valueOf(Color.GREEN));
            progressBar2.setProgressTintList(ColorStateList.valueOf(Color.GREEN));


            Strength = 4;
        }
    }

    public void ShowToastreg(String s) {
        snackbar = TSnackbar.make(cooreg, s, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundResource(R.drawable.roundborderyellow);

        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.BLACK);
        snackbar.show();
    }


    //-----------------------------------------reg2---------------------------------------------------

    private void registerapi(String Email, String Password, String Forenamereg, String username, String postcodetxt, String doortxt, String streettxt, String towntxt, String spinnertxt) {

        signintxtprogressreg.setVisibility(View.VISIBLE);
        signintxtreg.setVisibility(View.GONE);

        // int index = email.indexOf('@');
        // String username = email.substring(0, index);
        Log.d(TAG, "loginapi email password: " + emailEditText + " " + passwordEditText);


        String base64email = null, base64password = null, base64username = null, base64tforenametxt = null, base64surnametxt = null, base64postcodetxt = null, base64doortxt = null, base64streettxt = null, base64towntxt = null, base64spinnertxt = null;
        try {

            //username
            byte[] data0 = username.getBytes("UTF-8");

            base64username = Base64.encodeToString(data0, Base64.DEFAULT);

            Log.i("Base 64 ", base64username);

            //email
            byte[] data = Email.getBytes("UTF-8");

            base64email = Base64.encodeToString(data, Base64.DEFAULT);

            Log.i("Base 64 ", base64email);

            //password
            byte[] data1 = Password.getBytes("UTF-8");

            base64password = Base64.encodeToString(data1, Base64.DEFAULT);

            Log.i("Base 64 ", base64password);


            //forenametxt
            byte[] data2 = Forenamereg.getBytes("UTF-8");

            base64tforenametxt = Base64.encodeToString(data2, Base64.DEFAULT);

            Log.i("Base 64 ", base64tforenametxt);


           /* //surnametxt
            byte[] data3 = surnametxt.getBytes("UTF-8");

            base64surnametxt = Base64.encodeToString(data3, Base64.DEFAULT);

            Log.i("Base 64 ", base64surnametxt);*/


            //postcodetxt
            byte[] data4 = postcodetxt.getBytes("UTF-8");

            base64postcodetxt = Base64.encodeToString(data4, Base64.DEFAULT);

            Log.i("Base 64 ", base64postcodetxt);


            //doortxt
            byte[] data5 = doortxt.getBytes("UTF-8");

            base64doortxt = Base64.encodeToString(data5, Base64.DEFAULT);

            Log.i("Base 64 ", base64doortxt);


            //streettxt
            byte[] data6 = streettxt.getBytes("UTF-8");

            base64streettxt = Base64.encodeToString(data6, Base64.DEFAULT);

            Log.i("Base 64 ", base64streettxt);


            //towntxt
            byte[] data7 = towntxt.getBytes("UTF-8");

            base64towntxt = Base64.encodeToString(data7, Base64.DEFAULT);

            Log.i("Base 64 ", base64towntxt);


            //spinnertxt
            byte[] data8 = spinnertxt.getBytes("UTF-8");

            base64spinnertxt = Base64.encodeToString(data8, Base64.DEFAULT);

            Log.i("Base 64 ", base64spinnertxt);


        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        }

        // viewDialog.showDialog();
        ApiInterface apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<SignupModelClass> call = apiInterface.signup(base64username, base64email, base64password, base64password, base64surnametxt, base64tforenametxt, base64postcodetxt,
                base64doortxt, base64streettxt, base64towntxt, base64spinnertxt);
        call.enqueue(new Callback<SignupModelClass>() {
            @Override
            public void onResponse(Call<SignupModelClass> call, Response<SignupModelClass> response) {

                signintxtprogressreg.setVisibility(View.GONE);
                signintxtreg.setVisibility(View.VISIBLE);
                //viewDialog.hideDialog();
                final SignupModelClass signupModelClass = response.body();
                Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));

                if (signupModelClass != null && signupModelClass.status.equalsIgnoreCase("true")) {
                    PowerPreference.getDefaultFile().putBoolean("loggedin", true);
                    PowerPreference.getDefaultFile().putInt("userid", signupModelClass.userdetail.iD);
                    PowerPreference.getDefaultFile().putString("displayname", signupModelClass.userdetail.displayName);
                    PowerPreference.getDefaultFile().putString("email", signupModelClass.userdetail.email);
                    PowerPreference.getDefaultFile().putString("password", signupModelClass.userdetail.passoword);
                    PowerPreference.getDefaultFile().putString("username", signupModelClass.userdetail.userLogin);
                    PowerPreference.getDefaultFile().putString("doornumber", signupModelClass.userExtraDetail.doorNumber);
                    PowerPreference.getDefaultFile().putString("forename", signupModelClass.userExtraDetail.forename);
                    PowerPreference.getDefaultFile().putString("mainconsole", signupModelClass.userExtraDetail.mainConsole);
                    PowerPreference.getDefaultFile().putString("postcode", signupModelClass.userExtraDetail.postcode);
                    PowerPreference.getDefaultFile().putString("streetaddress", signupModelClass.userExtraDetail.streetAddress);
                    PowerPreference.getDefaultFile().putString("towncity", signupModelClass.userExtraDetail.townCity);
                    PowerPreference.getDefaultFile().putString("surname", signupModelClass.userExtraDetail.surname);
                    PowerPreference.getDefaultFile().putString("subscription", "no subscription");

                    //code for popup dialog.

                    final Dialog dialog = new Dialog(SplashActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.subscription_check_dialog);
                    Button buttonyes = (Button) dialog.findViewById(R.id.subscribeBtnYesId);
                    Button buttonno = (Button) dialog.findViewById(R.id.subscribeBtnNoId);

                    // if button is clicked, close the custom dialog
                    buttonyes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view1) {
                            dialog.dismiss();
                            startActivity(new Intent(SplashActivity.this, SubscribeWebView.class));
                        }
                    });

                    buttonno.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view1) {
                            dialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("frag", "profile");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    });
                    dialog.show();

                }
            }

            @Override
            public void onFailure(Call<SignupModelClass> call, Throwable t) {
                PowerPreference.getDefaultFile().putBoolean("loggedin", false);
                signintxtprogressreg.setVisibility(View.GONE);
                signintxtreg.setVisibility(View.VISIBLE);
                //viewDialog.hideDialog();
                //  Toast.makeText(getApplicationContext(), ""+t, Toast.LENGTH_SHORT).show();
                ShowToast(t.getMessage());


            }
        });

    }

    public void backtoregone() {
        idtworeg.setVisibility(View.VISIBLE);
        idtworeg1.setVisibility(View.GONE);
    }

    public void FullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }

    }

}