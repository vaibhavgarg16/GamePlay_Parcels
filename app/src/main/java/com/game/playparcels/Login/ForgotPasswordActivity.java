package com.game.playparcels.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.game.playparcels.Activity.SplashActivity;
import com.game.playparcels.ModelClasses.CancelReactivateGetModelClass;
import com.game.playparcels.R;
import com.game.playparcels.SevicesApi.ApiClient;
import com.game.playparcels.SevicesApi.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email;
    LinearLayout loginbtn;
    TextView gotoregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email = findViewById(R.id.email);
        loginbtn = findViewById(R.id.settingbtn);
        gotoregister = findViewById(R.id.returnbtn);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                if (mail.equals("")) {
                    email.setError("Cant be Empty");
                    Animation shake = AnimationUtils.loadAnimation(ForgotPasswordActivity.this, R.anim.shake);
                    email.startAnimation(shake);
                    email.requestFocus();
                    return;
                }

                cancelorreactivate(mail);
            }
        });

        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                intent.putExtra("splash", "login");

                overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);

                startActivity(intent);

                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });


    }


    public void cancelorreactivate(String mail) {

        //  progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<CancelReactivateGetModelClass> call = apiInterface.getForgotPassword(mail);
        call.enqueue(new Callback<CancelReactivateGetModelClass>() {
            @Override
            public void onResponse(Call<CancelReactivateGetModelClass> call, Response<CancelReactivateGetModelClass> response) {

                CancelReactivateGetModelClass cancelReactivateGetModelClass = response.body();

                assert cancelReactivateGetModelClass != null;
                Toast.makeText(getApplicationContext(), "" + cancelReactivateGetModelClass.message, Toast.LENGTH_SHORT).show();

                ///      progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<CancelReactivateGetModelClass> call, Throwable t) {
                // PowerPreference.getDefaultFile().putBoolean("loggedin",false);
                //viewDialog.hideDialog();
                Toast.makeText(getApplicationContext(), "" + t, Toast.LENGTH_SHORT).show();
                //   progressBar.setVisibility(View.GONE);
            }
        });


    }
}
