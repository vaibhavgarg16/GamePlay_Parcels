package com.game.playparcels.SingleCategory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.game.playparcels.Activity.MainActivity;
import com.game.playparcels.R;

public class GameSingleActivity extends AppCompatActivity {
    LinearLayout relatedlinear,toastone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_single);

        relatedlinear=findViewById(R.id.relatedlinear);
        toastone=findViewById(R.id.toastone);


        relatedlinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),GameSingleActivity.class));
                GameSingleFragment fragment1 = GameSingleFragment.newInstance();
                FragmentTransaction ft1 = getFragmentManager().beginTransaction().addToBackStack( "tag" );
                ft1.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                ft1.replace(R.id.container,fragment1);
                ft1.commit();
            }
        });

        toastone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TSnackbar snackbar = TSnackbar.make(findViewById(R.id.coo), "Invalid Subscription", TSnackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
                snackbar.setAction("VIEW", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("frag","queue");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                });
                TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                textView.setTextColor(Color.BLACK);
                snackbar.show();

              /*  LayoutInflater inflater1 = getLayoutInflater();
                //already in queue
                View layout1 = inflater1.inflate(R.layout.queue_already_toast,
                        (ViewGroup) findViewById(R.id.toast_layout_root));



                // create a new Toast using context
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG); // set the duration for the Toast
                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 60);


                // Retrieve the Layout Inflater and inflate the layout from xml

                toast.setView(layout1); // set the inflated layout
                toast.show(); // display the custom Toast*/
            }
        });



    }


}
