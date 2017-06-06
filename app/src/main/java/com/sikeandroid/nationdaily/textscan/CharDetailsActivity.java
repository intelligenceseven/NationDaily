package com.sikeandroid.nationdaily.textscan;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sikeandroid.nationdaily.R;
import com.sikeandroid.nationdaily.main.DailyNationFragment;

public class CharDetailsActivity extends AppCompatActivity {

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private String Url = "http://www.xtwroot.top/404.png";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_details);

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled( true );
        }

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mWebView = (WebView) findViewById(R.id.nation_history_web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);


        Intent intent = getIntent();
        if (intent != null) {
            Url = intent.getStringExtra(DailyNationFragment.NATION_HISTORY_URL);
        }

        mWebView.loadUrl(Url);

        mWebView.setWebViewClient(new WebViewClient() {

        });

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
                if(newProgress == mProgressBar.getMax())
                    mProgressBar.setVisibility(View.GONE);
                else
                    mProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onBackPressed() {

        if(mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        }
        else{
            super.onBackPressed();
        }
    }
}
