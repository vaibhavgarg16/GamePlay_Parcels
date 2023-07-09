package com.game.playparcels.ui.AccountTab;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.game.playparcels.R;
import com.game.playparcels.SevicesApi.ApiClient;
import com.preference.PowerPreference;

import java.net.MalformedURLException;
import java.net.URL;

import es.dmoral.toasty.Toasty;

public class SubscribeWebView extends AppCompatActivity {

    WebView sWebViewId;
    String emailStr, passwordStr;
    String auto = "&auto_login=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_web_view);
        sWebViewId = findViewById(R.id.sWebViewId);
        Toasty.success(SubscribeWebView.this, "Please Wait...", Toast.LENGTH_LONG).show();

        emailStr = PowerPreference.getDefaultFile().getString("email", "");
        passwordStr = PowerPreference.getDefaultFile().getString("password", "");

        final String emailencode = Base64.encodeToString(emailStr.getBytes(), Base64.DEFAULT);
        final String paswordencode = Base64.encodeToString(passwordStr.getBytes(), Base64.DEFAULT);
        final String val = "username=" + emailencode + "&pw_hwe=" + paswordencode + auto;

        if (Build.VERSION.SDK_INT < 18) {
            //speed webview
            sWebViewId.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        }


        sWebViewId.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        sWebViewId.getSettings().setJavaScriptEnabled(true);
        sWebViewId.getSettings().setDomStorageEnabled(true);
        sWebViewId.getSettings().setLoadWithOverviewMode(true);
        sWebViewId.getSettings().setUseWideViewPort(true);
        sWebViewId.getSettings().setSupportZoom(true);
        sWebViewId.getSettings().setBuiltInZoomControls(false);
        sWebViewId.getSettings().setEnableSmoothTransition(true);


        sWebViewId.loadUrl("file:///android_asset/subscription.html");
        sWebViewId.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                sWebViewId.loadUrl("javascript:init('" + val + "')");
            }
        });
    }
}