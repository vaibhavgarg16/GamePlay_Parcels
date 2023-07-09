package com.game.playparcels.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.androidadvance.topsnackbar.TSnackbar;
import com.game.playparcels.Activity.MainActivity;
import com.game.playparcels.Activity.TermsNConditions;
import com.game.playparcels.LoginHelper.TwitterHelper;
import com.game.playparcels.ModelClasses.LoginModelClass;
import com.game.playparcels.R;
import com.game.playparcels.SevicesApi.ApiClient;
import com.game.playparcels.SevicesApi.ApiInterface;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.preference.PowerPreference;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity /*implements   TwitterHelper.OnTwitterSignInListener*/ {

    ImageButton backpress;
    //ImageView imageviewback;
    TextView gotoregister, newbuttonz;
    Button loginbtn;
    EditText email,password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+",TAG="LoginActivityTAG";
    TextView forgotpassword;
    Button fblogin,logingoogle;

    RelativeLayout progressBar;

    //-----------------------------------Twitter Sign In -----------------------------------//
    private TwitterHelper twitterHelper;
    private Button tSignInButton;
    private boolean isFbLogin = false;
    private FirebaseAuth mAuth;
    Integer RC_SIGN_IN = 32131;
    GoogleSignInClient mGoogleSignInClient;
    String deviceToken;
    TSnackbar snackbar;
    CoordinatorLayout coo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        setContentView(R.layout.activity_login);
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
        progressBar=findViewById(R.id.progressBar);

        //twitterHelper = new TwitterHelper(this, this); // Twitter Initialization
        // imageviewback=findViewById(R.id.imageviewback);
        backpress = findViewById(R.id.backpress);
        gotoregister = findViewById(R.id.gotoregister);
        loginbtn = findViewById(R.id.settingbtn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        fblogin = findViewById(R.id.fblogin);
        logingoogle = findViewById(R.id.logingoogle);
        forgotpassword=findViewById(R.id.forgotpassword);
        coo = findViewById(R.id.coo);
        newbuttonz = findViewById(R.id.newbuttonz);


// ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //Then we will get the GoogleSignInClient object from GoogleSignIn class
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);







        //----------------------------------Twitter Sign in button ------------------------------//
        tSignInButton = (Button) findViewById(R.id.main_twitter_sign_in_button);
        tSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");

                mAuth.startActivityForSignInWithProvider(/* activity= */ LoginActivity.this, provider.build())
                        .addOnSuccessListener(
                                new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Log.d(TAG, "onSuccess:authResult "+authResult.getUser().getEmail());
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure:authResult "+e);
                                        // Handle failure.
                                    }
                                });
                // progressBar.setVisibility(View.VISIBLE);
               /* twitterHelper.connect();
                isFbLogin = false;*/
            }
        });

        Task<AuthResult> pendingResultTask = mAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    // User is signed in.
                                    // IdP data available in
                                    // authResult.getAdditionalUserInfo().getProfile().
                                    // The OAuth access token can also be retrieved:
                                    // authResult.getCredential().getAccessToken().
                                    // The OAuth secret can be retrieved by calling:
                                    // authResult.getCredential().getSecret().
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure.
                                }
                            });
        } else {
            // There's no pending result so you need to start the sign-in flow.
            // See below.
        }

        //----------------------------------Twitter Sign in button End ------------------------------//

        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                onBackPressed();
            }
        });

        fblogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        logingoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinGoogle();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Pass = password.getText().toString().trim();



                if (Email.equals("")) {
                    email.setError("Email Cant be Empty");
                    return;

                }
                /*if( *//*!(Email.matches(emailPattern))*//*!(Email.contains("@")&& Email.contains("."))) {
                    email.setError("Please Enter correct email");
                    return;
                }*/

                if (Pass.equals("")) {
                    email.setError("Password Cant be Empty");
                    return;
                }

                loginapi(Email, Pass);
            }
        });


        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(LoginActivity.this, "In Development", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
                                                                                                                                                                                                                                                                                                             startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
            }
        });

        newbuttonz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(LoginActivity.this, "In Development", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), TermsNConditions.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

    }

    private void signinGoogle() {

            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);


    }

    public void loginapi(String email, String password) {
        //viewDialog.showDialog();
        progressBar.setVisibility(View.VISIBLE);
        Log.d(TAG, "loginapi email password: "+email +" "+password);


        byte[] data,data1,data2;
        String base64email=null,base64password=null,base64type=null;
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



      /*   byte[] emailE = Base64.encode(email.getBytes(), Base64.DEFAULT);
        String emailEn = emailE.toString();


        byte[] passwordE= Base64.encode(password.getBytes(), Base64.DEFAULT);
        String passwordEn = passwordE.toString();


        byte[] decodeValue = Base64.decode(emailE, Base64.DEFAULT);
        byte[] decodeValuep = Base64.decode(passwordE, Base64.DEFAULT);
*/

        Log.d(TAG, "loginapi:emailEn "+email+" "+base64email+" passwordEn "+password+" "+base64password +" type"+type+" "+ base64type/*+ " decode "+decodeValue+" "+decodeValuep*/);



        ApiInterface apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<LoginModelClass> call = apiInterface.login(base64email,base64type,base64password,deviceToken,"","","");
        call.enqueue(new Callback<LoginModelClass>() {
            @Override
            public void onResponse(Call<LoginModelClass> call, Response<LoginModelClass> response) {
                LoginModelClass loginModelClass = response.body();
                Log.d(TAG, "onResponse: "+new Gson().toJson(response.body()));
                Log.d(TAG, "onResponse H: 1- "+new Gson().toJson(response.body()));
                Log.d(TAG, "onResponse H: 2- "+new Gson().toJson(response.code()));
                assert loginModelClass != null;
                if (loginModelClass.status.equalsIgnoreCase("true")){
                    PowerPreference.getDefaultFile().putBoolean("loggedin",true);
                    Log.d(TAG, "onResponse:--- "+loginModelClass.loginUserDetails.get(0).userid+ " "+loginModelClass.loginUserDetails.get(0).role);
                    PowerPreference.getDefaultFile().putInt("userid",loginModelClass.loginUserDetails.get(0).userid);
                    PowerPreference.getDefaultFile().putString("role",loginModelClass.loginUserDetails.get(0).role);

                    Intent intt = new Intent(getApplicationContext(),MainActivity.class);
                    intt.putExtra("frag","profile");
                    startActivity(intt);
                }else {
                    ShowToast("Login Error");
                }

                //viewDialog.hideDialog();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<LoginModelClass> call, Throwable t) {
                PowerPreference.getDefaultFile().putBoolean("loggedin",false);
                progressBar.setVisibility(View.GONE);
                //viewDialog.hideDialog();

             ShowToast(t.getMessage());
                //Toast.makeText(LoginActivity.this, ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }


   /* @Override
    public void OnTwitterSignInComplete(TwitterHelper.UserDetails userDetails, String error) {
       // progressBar.setVisibility(View.GONE);
        if (userDetails != null) {
            //userName.setText(userDetails.getUserName());

            PowerPreference.getDefaultFile().putString("username",userDetails.getUserName());

            Log.d(TAG, "OnTwitterSignInComplete: "+userDetails.getUserName() );
            if (userDetails.getUserEmail() != null) {
                email.setText(userDetails.getUserEmail());
            }
        }
       // twitterHelper.postTweet("url", null, false, null, null, null, false, false, null);
    }

    @Override
    public void OnTweetPostComplete(Result<Tweet> result, String error) {

        Log.d(TAG, "OnTwitterSignInComplete: "+" Error "+error );
    }
*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // googleSignInHelper.onActivityResult(requestCode, resultCode, data);
      //  fbConnectHelper.onActivityResult(requestCode, resultCode, data);
      //  twitterHelper.onActivityResult(requestCode, resultCode, data);
       // linkedInSignInHelper.onActivityResult(requestCode, resultCode, data);
        if (isFbLogin) {
        //    progressBar.setVisibility(View.VISIBLE);
            isFbLogin = false;
        }
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
        }
    }




    @Override
    public void onBackPressed() {
        Intent back = new Intent(getApplicationContext(), MainActivity.class);
        back.putExtra("frag","profile");
        startActivity(back);
        finish();
        super.onBackPressed();
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
                            Toast.makeText(LoginActivity.this, "Google Login Successfull", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }

                        // ...
                    }
                });
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
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void ShowToast(String s){
        snackbar = TSnackbar.make(coo, s, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundResource(R.drawable.roundborderyellow);

        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.BLACK);
        snackbar.show();
    }

}
