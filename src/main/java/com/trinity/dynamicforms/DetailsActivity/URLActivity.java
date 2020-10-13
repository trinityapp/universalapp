package com.trinity.dynamicforms.DetailsActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.trinity.dynamicforms.R;


public class URLActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        webView = (WebView)findViewById(R.id.webView);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        webView.loadUrl(url);

        final ProgressDialog progDailog = ProgressDialog.show(URLActivity.this, "Loading","Please wait...", true);
        progDailog.setCancelable(false);



//        webView = (WebView) findViewById(R.id.webview_compontent);



        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progDailog.show();
                view.loadUrl(url);

                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                progDailog.dismiss();
            }

        });

        webView.loadUrl(url);
    }
}
