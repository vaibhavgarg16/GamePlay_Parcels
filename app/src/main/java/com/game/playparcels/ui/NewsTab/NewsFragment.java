package com.game.playparcels.ui.NewsTab;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.game.playparcels.Activity.FingerprintActivity;
import com.game.playparcels.R;

import com.game.playparcels.ui.SettingsTab.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class NewsFragment extends Fragment {


    public static NewsFragment newInstance() {
        return new NewsFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.news_fragment, container, false);


    }

    ScrollView newsscroll;

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        newsscroll = v.findViewById(R.id.newsscroll);

    }



}










