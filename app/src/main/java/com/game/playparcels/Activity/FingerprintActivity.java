package com.game.playparcels.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.SystemClock;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.login.LoginManager;
import com.game.playparcels.ModelClasses.LoginModelClass;
import com.game.playparcels.R;
import com.game.playparcels.SevicesApi.ApiClient;
import com.game.playparcels.SevicesApi.ApiInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.preference.PowerPreference;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FingerprintActivity extends AppCompatActivity {

    private KeyStore keyStore;
    // Variable used for storing the key in the Android Keystore container
    private static final String KEY_NAME = "GamePlayParcel";
    private Cipher cipher;
    private TextView textView;
    ImageView fingerprinticon;
    String deviceToken;

    String displayname;
    EditText passwordreg;
    TextView username, fingerprintText;
    Boolean visible = false;

    LinearLayout loginbtn, lockLayout, passwordtype;
    CardView RoundedCornerLayout, layoutlock, layoutfingrprint;

    ImageView iconlock, fingerprinticon1;
    TextView notYou, touchsensertxt, signintxt;
    LinearLayout layout2, layout1, layout3, layout4;
    ProgressBar pbar1, signintxtprogress;
    private long lastClickTime = 0;
    private static final String TAG = "FingerprintActivity";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);
        PowerPreference.init(getApplicationContext());

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

        fingerprintText = findViewById(R.id.fingerprintText_x);
        textView = findViewById(R.id.errorText);
        fingerprinticon = findViewById(R.id.fingerprinticon);
        username = findViewById(R.id.username);
        passwordreg = findViewById(R.id.passwordreg);
        loginbtn = findViewById(R.id.settingbtn);
        iconlock = findViewById(R.id.iconlock);
        lockLayout = findViewById(R.id.lockLayout);
        passwordtype = findViewById(R.id.passwordtype);
        notYou = findViewById(R.id.notYou);
        touchsensertxt = findViewById(R.id.touchsensertxt);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        layoutfingrprint = findViewById(R.id.layoutfingrprint);
        fingerprinticon1 = findViewById(R.id.fingerprinticon1);
        pbar1 = findViewById(R.id.pbar1);
        signintxt = findViewById(R.id.signintxt);
        signintxtprogress = findViewById(R.id.signintxtprogress);
        layout4 = findViewById(R.id.layout4);


        displayname = PowerPreference.getDefaultFile().getString("forename");
        username.setText("Hi, " + displayname);

        Intent intent = getIntent();
        Boolean st = intent.getBooleanExtra("keepsigned", false);
        String type = intent.getStringExtra("type");
        Log.d("TAG", "onCreate: type- " + type);
        if (st) {

            YoYo.with(Techniques.FadeOut)
                    .duration(300)
                    .repeat(0)
                    .playOn(lockLayout);


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    lockLayout.setVisibility(View.GONE);
                    passwordtype.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeIn)
                            .duration(400)
                            .repeat(0)
                            .playOn(passwordtype);
                }
            }, 300);   //5 seconds
        }
        if (type != null && type.equalsIgnoreCase("layout3")) {
            layout3.setVisibility(View.VISIBLE);
            imitializwFingerprint();
        }
        if (!PowerPreference.getDefaultFile().getBoolean("loggedin", false)) {
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.VISIBLE);
            fingerprintText.setText("Touch Sensor");
            imitializwFingerprint();
        }

        fingerprinticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YoYo.with(Techniques.FadeOut)
                        .duration(500)
                        .repeat(0)
                        .playOn(layout1);
                YoYo.with(Techniques.FadeOut)
                        .duration(500)
                        .repeat(0)
                        .playOn(layout2);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        layout1.setVisibility(View.GONE);
                        layout2.setVisibility(View.GONE);
                        layout3.setVisibility(View.VISIBLE);

                        YoYo.with(Techniques.FadeIn)
                                .duration(700)
                                .repeat(0)
                                .playOn(layout3);

                    }
                }, 500);

                /*  LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        2
                );
                layout2.setPadding(0,0,0,0);
                layout2.setLayoutParams(param);



                PropertyValuesHolder pvhTop = PropertyValuesHolder.ofInt("top", 0, 1);
                PropertyValuesHolder pvhRight = PropertyValuesHolder.ofInt("right", 1, 1);
                PropertyValuesHolder pvhBottom = PropertyValuesHolder.ofInt("bottom", 0, 1);


                final Animator collapseExpandAnim = ObjectAnimator.ofPropertyValuesHolder(layout2,  pvhTop,
                        pvhRight, pvhBottom);
                collapseExpandAnim.setupStartValues();

                layout2.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        layout2.getViewTreeObserver().removeOnPreDrawListener(this);
                        collapseExpandAnim.setupEndValues();
                        collapseExpandAnim.start();
                        return false;
                    }
                });
*/

                imitializwFingerprint();

            }
        });

        notYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PowerPreference.getDefaultFile().clear();
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent inten = new Intent(getApplicationContext(), SplashActivity.class);
                inten.putExtra("splash", "login");
                inten.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(inten);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();

            }
        });


        iconlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //     layout4.setVisibility(View.VISIBLE);

                YoYo.with(Techniques.FadeOut)
                        .duration(500)
                        .repeat(0)
                        .playOn(lockLayout);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        layout1.setVisibility(View.GONE);
                        layout2.setVisibility(View.GONE);
                        layout3.setVisibility(View.GONE);

                        Handler handler5 = new Handler();
                        handler5.postDelayed(new Runnable() {
                            public void run() {

                                YoYo.with(Techniques.FadeOut)
                                        .duration(100)
                                        .repeat(0)
                                        .playOn(lockLayout);

                                Handler handlert = new Handler();
                                handlert.postDelayed(new Runnable() {
                                    public void run() {
                                        lockLayout.setVisibility(View.GONE);
                                        passwordtype.setVisibility(View.VISIBLE);


                                        YoYo.with(Techniques.FadeIn)
                                                .duration(250)
                                                .repeat(0)
                                                .playOn(passwordtype);


                                    }
                                }, 250);


                            }
                        }, 500);


                    }
                }, 500);


            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Hide Keyboard when login or sign in button clicked
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                if (SystemClock.elapsedRealtime() - lastClickTime < 2000) {
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();


                if (passwordreg.getText().toString().equals("")) {
                    passwordreg.setError("Please enter password");
                    Animation shake = AnimationUtils.loadAnimation(FingerprintActivity.this, R.anim.shake);
                    passwordreg.startAnimation(shake);
                    passwordreg.requestFocus();
                    return;
                }

                Log.d(TAG, "onClick: login Data Biometric" + displayname);
                Log.d(TAG, "onClick: login Data Biometric" + PowerPreference.getDefaultFile().getString("baseEmail"));
                Log.d(TAG, "onClick: login Data Biometric" + passwordreg.getText().toString());
//                loginapi(displayname, passwordreg.getText().toString());

                byte[] data = Base64.decode(PowerPreference.getDefaultFile().getString("baseEmail"), Base64.DEFAULT);
                String baseEmail = null;
                try {
                    baseEmail = new String(data, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Log.d(TAG, "onClick: login Data Biometric" + e.getMessage());
                }
                Log.d(TAG, "onClick: login Data Biometric" + baseEmail);

//                loginapi(displayname, passwordreg.getText().toString());
                loginapi(baseEmail, passwordreg.getText().toString());

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


    }

    private void imitializwFingerprint() {
       /* touchsensertxt.setVisibility(View.VISIBLE);

        fingerprinticon.setBackgroundColor(Color.GREEN);
        layoutfingrprint.setBackgroundColor(Color.GREEN);*/

        // Initializing both Android Keyguard Manager and Fingerprint Manager
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);


        // Check whether the device has a Fingerprint sensor.
        if (!fingerprintManager.isHardwareDetected()) {

            textView.setText("Your Device does not have a Fingerprint Sensor");
        } else {
            // Checks whether fingerprint permission is set on manifest
            if (ActivityCompat.checkSelfPermission(FingerprintActivity.this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                textView.setText("Fingerprint authentication permission not enabled");
            } else {
                // Check whether at least one fingerprint is registered
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    textView.setText("Register at least one fingerprint in Settings");
                } else {
                    // Checks whether lock screen security is enabled or not
                    if (!keyguardManager.isKeyguardSecure()) {
                        textView.setText("Lock screen security not enabled in Settings");
                    } else {
                        generateKey();

                        if (cipherInit()) {
                            FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                            FingerprintHandler helper = new FingerprintHandler(FingerprintActivity.this);

                            helper.startAuth(fingerprintManager, cryptoObject);
                        }
                    }
                }
            }

        }

    }


    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }


        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }


        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | IOException | java.security.cert.CertificateException e) {
            throw new RuntimeException(e);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }


        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException | java.security.cert.CertificateException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

        private Context context;


        // Constructor
        public FingerprintHandler(Context mContext) {
            context = mContext;

        }


        public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
        }


        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            this.update("Fingerprint Authentication error\n" + errString, false);
        }


        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            this.update("Fingerprint Authentication help\n" + helpString, false);
        }


        @Override
        public void onAuthenticationFailed() {
            this.update("Fingerprint Authentication failed.", false);
        }


        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            this.update("Fingerprint Authentication succeeded.", true);
        }


        public void update(String e, Boolean success) {
            TextView textView = (TextView) ((Activity) context).findViewById(R.id.errorText);
            textView.setText(e);

            if (success) {

                textView.setTextColor(ContextCompat.getColor(context, R.color.white));
                fingerprinticon1.setVisibility(View.INVISIBLE);
                pbar1.setVisibility(View.VISIBLE);
                Log.d("fjpoisjpoisjp", "update: ");
                if (PowerPreference.getDefaultFile().getBoolean("fingerprint", false)) {




                   /* pbar.setVisibility(View.VISIBLE);
                    fingerprinticon.setBackgroundResource(R.drawable.whitebackground);
                    layoutfingrprint.setBackgroundColor(Color.WHITE);
                    fingerprinticon.setBackgroundColor(Color.WHITE);
                    fingerprinticon.setVisibility(View.INVISIBLE);*/
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Intent openThree = new Intent(context, MainActivity.class);
                            Log.d("fp", "update: " + true);
                            context.startActivity(openThree);
                            ((Activity) context).finish();
                        }
                    }, 400);   //1.5 seconds


                } else {
               /* Intent openThree = new Intent(context, MainActivity.class);
                openThree.putExtra("startbio",true);
                context.startActivity(openThree);*/
                    Log.d("fjpoisjpoisjp", "update: " + false);
                    PowerPreference.getDefaultFile().putBoolean("fingerprintS", true);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {

                            ((Activity) context).finish();
                        }
                    }, 1000);   //1 second
                }


            }
        }
    }

    public void loginapi(String email, String password) {
        //viewDialog.showDialog();
        //  progressBar.setVisibility(View.VISIBLE);


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


        YoYo.with(Techniques.FadeOut)
                .duration(100)
                .repeat(0)
                .playOn(signintxt);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                signintxtprogress.setVisibility(View.VISIBLE);
                signintxt.setVisibility(View.GONE);
                YoYo.with(Techniques.FadeIn)
                        .duration(500)
                        .repeat(0)
                        .playOn(signintxtprogress);
            }
        }, 100);   //5 seconds


        ApiInterface apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<LoginModelClass> call = apiInterface.login(base64email,base64type, base64password, deviceToken,"", "", "");
        call.enqueue(new Callback<LoginModelClass>() {
            @Override
            public void onResponse(Call<LoginModelClass> call, Response<LoginModelClass> response) {
                if(response.body() != null) {
                    LoginModelClass loginModelClass = response.body();
                    try {
                        assert loginModelClass != null;

                        if (loginModelClass.status.equalsIgnoreCase("true")) {
                            PowerPreference.getDefaultFile().putBoolean("loggedin", true);
                            PowerPreference.getDefaultFile().putInt("userid", loginModelClass.loginUserDetails.get(0).userid);
                            PowerPreference.getDefaultFile().putString("role", loginModelClass.loginUserDetails.get(0).role);

                            YoYo.with(Techniques.FadeOut)
                                    .duration(500)
                                    .repeat(0)
                                    .playOn(signintxtprogress);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    signintxtprogress.setVisibility(View.GONE);
                                    signintxt.setVisibility(View.VISIBLE);
                                    YoYo.with(Techniques.FadeIn)
                                            .duration(500)
                                            .repeat(0)
                                            .playOn(signintxt);
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                            }, 50);   //5 seconds

                        } else {
//                        Toast.makeText(FingerprintActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                            passwordreg.setError("Incorrect Password");
                            Animation shake = AnimationUtils.loadAnimation(FingerprintActivity.this, R.anim.shake);
                            passwordreg.startAnimation(shake);
                            passwordreg.requestFocus();
                            YoYo.with(Techniques.FadeOut)
                                    .duration(200)
                                    .repeat(0)
                                    .playOn(signintxtprogress);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    signintxtprogress.setVisibility(View.GONE);
                                    signintxt.setVisibility(View.VISIBLE);
                                    YoYo.with(Techniques.FadeIn)
                                            .duration(200)
                                            .repeat(0)
                                            .playOn(signintxt);
                                }
                            }, 200);   //5 seconds
                        }
                    } catch (NullPointerException d) {
                        loginapi(displayname, passwordreg.getText().toString());
                    }

                }
                //viewDialog.hideDialog();
                //  progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<LoginModelClass> call, Throwable t) {
                PowerPreference.getDefaultFile().putBoolean("loggedin", false);
                //   progressBar.setVisibility(View.GONE);
                //viewDialog.hideDialog();


                //Toast.makeText(LoginActivity.this, ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*public void homepageapi() {
        // progressBar.setVisibility(View.VISIBLE);

        //viewDialog.showDialog();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<HomeGameListClass> call = apiInterface.homepagegame();
        call.enqueue(new Callback<HomeGameListClass>() {
            @Override
            public void onResponse(Call<HomeGameListClass> call, Response<HomeGameListClass> response) {


                HomeGameListClass homeGameListClass = response.body();


                assert homeGameListClass != null;


                if (homeGameListClass.status) {
                    PowerPreference.getDefaultFile().putString("home", new Gson().toJson(response.body()));

                }


            }


            @Override
            public void onFailure(Call<HomeGameListClass> call, Throwable t) {
                // PowerPreference.getDefaultFile().putBoolean("loggedin",false);
            }
        });
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}