package com.game.playparcels.ui;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;


import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.game.playparcels.R;
import com.game.playparcels.SingleCategory.FullCategoryFragment;

import androidx.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {

    private static int SPLASH_TIME_OUT = 2000;

    public SplashFragment() {
        // Required empty public constructor
    }
    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       return  inflater.inflate(R.layout.fragment_splash, container, false);

    }


    @Override
    public void onViewCreated(View vv, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(vv, savedInstanceState);
        WebView wv = (WebView) vv.findViewById(R.id.weee);
        // wv.loadUrl("file:///android_asset/repeatonce.gif");
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.setBackgroundColor(Color.WHITE);

        String html = "<html><head></head><body> <img src=\""+ "file:///android_asset/repeatonce.gif" + "\"> </body></html>";
        wv.loadDataWithBaseURL("", html, "text/html","utf-8", "");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Log.d("poipoipoipoi", "run: ");
               /* Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("frag","home");
                startActivity(intent);*/


                Bundle bundle= new Bundle();
                bundle.putString("frag","home");

                FullCategoryFragment fragment1 = FullCategoryFragment.newInstance();
                FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                fragment1.setArguments(bundle);
                ft1.replace(R.id.container,fragment1);
                ft1.commit();

            }
        }, SPLASH_TIME_OUT);
    }
}
