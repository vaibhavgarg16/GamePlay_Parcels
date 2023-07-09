package com.game.playparcels.SingleCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.game.playparcels.Activity.MainActivity;
import com.game.playparcels.R;
import com.google.android.material.snackbar.Snackbar;

import es.dmoral.toasty.Toasty;

public class FullCategoryActivity extends AppCompatActivity {

    LinearLayout llone, lltwo, llthree;
    ProgressBar progressBar;
    Button toastone, toasttwo, toastthree, toastfour;
    ImageView imgone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_category);

        imgone = findViewById(R.id.imgone);
        progressBar = findViewById(R.id.progress_circular);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);


        imgone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameSingleFragment fragment1 = GameSingleFragment.newInstance();
                FragmentTransaction ft1 = getFragmentManager().beginTransaction().addToBackStack("tag");
                ft1.replace(R.id.container, fragment1);
                ft1.commit();

                // startActivity(new Intent( getApplicationContext(), GameSingleActivity.class));
            }
        });


    }
}
