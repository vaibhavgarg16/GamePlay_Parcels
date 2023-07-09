package com.game.playparcels.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.game.playparcels.Activity.MainActivity;
import com.game.playparcels.R;
import com.ybs.passwordstrengthmeter.PasswordStrength;

public class RegisterActivity extends AppCompatActivity {

    TextView gotologin;
    Button Continue;
    EditText password,email,passwordone;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String TAG = "RegisterActivity";
    int Strength = 0;

    TSnackbar snackbar;
    CoordinatorLayout coo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        gotologin=findViewById(R.id.gotologin);
        Continue=findViewById(R.id.Continue);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        passwordone = findViewById(R.id.passwordone);
        coo = findViewById(R.id.coo);

        password.addTextChangedListener(new TextWatcher() {
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
            public void onClick(View v)
            {
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                String Passwordone = passwordone.getText().toString().trim();

                Log.d(TAG, "Continue: "+Email+ " "+Password);

               /* if (Email.equals("")||!(Email.matches(emailPattern))){
                    email.setError("Email Cant be Empty");
                    return;

                }*/

                if (Email.equals("")) {
                    email.setError("Email cannot be Empty");
                    Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);
                    email.startAnimation(shake);
                    email.requestFocus();
                    return;

                }
                if( /*!(Email.matches(emailPattern))*/!(Email.contains("@")&& Email.contains("."))) {
                    email.setError("Please Enter correct email");
                    Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);
                    email.startAnimation(shake);
                    email.requestFocus();
                    return;
                }

                if (Password.equals("")){
                    password.setError("Password Cannot be Empty");
                    Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);
                    password.startAnimation(shake);
                    password.requestFocus();
                    return;
                }
                if (Passwordone.equals("")){
                    passwordone.setError("Password Cannot be Empty");
                    Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);
                    passwordone.startAnimation(shake);
                    passwordone.requestFocus();
                    return;
                }else if (Strength<2){
                    passwordone.setError("Password  Weak");
                    Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);
                    passwordone.startAnimation(shake);
                    passwordone.requestFocus();
                    return;
                }

                if (!Password.equals(Passwordone)) {
                  //  Toast.makeText(RegisterActivity.this, "Password Not Matched", Toast.LENGTH_SHORT).show();
                    ShowToast("Password Not Matched");
                    return;
                }

                Intent intent =new Intent(getApplicationContext(),RegisterTwoActivity.class);

                intent.putExtra("email",Email);
                intent.putExtra("password",Password);
                startActivity(intent);

                //finish();
            }
        });
        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        super.onBackPressed();
    }


    private void updatePasswordStrengthView(String password) {

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        TextView strengthView = (TextView) findViewById(R.id.password_strength);
        if (TextView.VISIBLE != strengthView.getVisibility())
            return;

        if (password.isEmpty()) {
            strengthView.setText("");
            progressBar.setProgress(0);
            return;
        }

        PasswordStrength str = PasswordStrength.calculateStrength(password);
        strengthView.setText(str.getText(this));
        strengthView.setTextColor(str.getColor());

        progressBar.getProgressDrawable().setColorFilter(str.getColor(), android.graphics.PorterDuff.Mode.SRC_IN);
        if (str.getText(this).equals("Weak")) {
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
            strengthView.setTextColor(ColorStateList.valueOf(Color.RED));
            Strength=1;
            progressBar.setProgress(25);
        } else if (str.getText(this).equals("Medium")) {
            strengthView.setTextColor(ColorStateList.valueOf(Color.YELLOW));
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
            progressBar.setProgress(50);
            Strength=2;
        } else if (str.getText(this).equals("Strong")) {
            progressBar.setProgress(75);
            strengthView.setTextColor(ColorStateList.valueOf(Color.GREEN));
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
            Strength=3;
        } else {
            progressBar.setProgress(100);
            strengthView.setTextColor(ColorStateList.valueOf(Color.BLUE));
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
            Strength=4;
        }
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
