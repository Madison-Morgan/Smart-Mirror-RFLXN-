package com.olaolukiyesi.mysmartmirror;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Set;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

public class MirrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);
        initWeb();
        initFAB();
        
    }

    private void initFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Snackbar.make(view, "Settings are in Development.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                loadSettings();

            }
        });

    }

    private void loadSettings() {
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
        Animatoo.animateSlideUp(this);
    }

    private void initWeb() {
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebContentsDebuggingEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(LOAD_NO_CACHE);
        myWebView.loadUrl("https://pr.gg/dashboard");
    }
}
