package com.game.playparcels.ui.SettingsTab;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.game.playparcels.R;
import com.preference.PowerPreference;

import es.dmoral.toasty.Toasty;

public class PrivacyWebView extends AppCompatActivity {

    WebView pWebViewId;
    ProgressBar progressBar2;
    String emailStr, passwordStr;
    String auto = "&auto_login=1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_web_view);
        pWebViewId = findViewById(R.id.pWebViewId);
        progressBar2 = findViewById(R.id.progressBar2);

        Toasty.success(PrivacyWebView.this, "Please Wait...", Toast.LENGTH_LONG).show();

        emailStr = PowerPreference.getDefaultFile().getString("email", "");
        passwordStr = PowerPreference.getDefaultFile().getString("password", "");

        final String emailencode = Base64.encodeToString(emailStr.getBytes(), Base64.DEFAULT);
        final String paswordencode = Base64.encodeToString(passwordStr.getBytes(), Base64.DEFAULT);
        final String val = "username=" + emailencode + "&pw_hwe=" + paswordencode + auto;


        if (Build.VERSION.SDK_INT < 18) {
            //speed webview
            pWebViewId.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        }


        pWebViewId.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        pWebViewId.getSettings().setJavaScriptEnabled(true);
        pWebViewId.getSettings().setDomStorageEnabled(true);
        pWebViewId.getSettings().setLoadWithOverviewMode(true);
        pWebViewId.getSettings().setUseWideViewPort(true);
        pWebViewId.getSettings().setSupportZoom(true);
        pWebViewId.getSettings().setBuiltInZoomControls(false);
        pWebViewId.getSettings().setEnableSmoothTransition(true);


        pWebViewId.loadUrl("file:///android_asset/privacy.html");
        pWebViewId.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                pWebViewId.loadUrl("javascript:init('" + val + "')");
            }

        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar2.setVisibility(View.GONE);
            }
        }, 4000);

    }
}
