package com.game.playparcels.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.game.playparcels.ModelClasses.SearchGamesModelClass;
import com.game.playparcels.R;
import com.game.playparcels.SevicesApi.ApiClient;
import com.game.playparcels.SevicesApi.ApiInterface;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    EditText searchedit;
    String TAG = "SearchActivityTAG";
   // LinearLayout dataa;
    ImageView backbtn;

    Boolean Cross = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchedit = findViewById(R.id.search_edit_text);
        searchedit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, 0,0);
      // dataa=findViewById(R.id.dataa);

        backbtn = findViewById(R.id.searchbackbtn);




        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        searchedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.d(TAG, "onTextChanged: "+ count);
                if (count==0){
                    Cross=false;
                    searchedit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, 0,0);
                   // dataa.setVisibility(View.GONE);
                }
                else {
                    Cross=true;
                    searchedit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, R.drawable.ic_cancel_black_24dp,0);
                 //   dataa.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchedit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (Cross){
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (searchedit.getRight() - searchedit.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        searchedit.setText("");
                        return true;
                    }
                }}
                return false;
            }

        });


    }



    @Override
    public void onClick(View v) {

    }
}
