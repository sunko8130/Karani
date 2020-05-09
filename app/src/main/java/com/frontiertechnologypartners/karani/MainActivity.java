package com.frontiertechnologypartners.karani;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import static com.frontiertechnologypartners.karani.Constant.URL;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private LinearLayout noConnectionLayout;
    private Button btnRetry;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // making notification bar transparent
        Util.changeStatusBarColor(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        init();
        loadUrl();
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUrl();
            }
        });

    }

    private void loadUrl() {
        if (Util.isNetworkAvailable(this)) {
            webView.setVisibility(View.VISIBLE);
            noConnectionLayout.setVisibility(View.GONE);
            progressDialog.show();
            webView.setWebViewClient(new CustomWebViewClient());
            webView.loadUrl(URL);
        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            webView.setVisibility(View.GONE);
            noConnectionLayout.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            hideErrorPage(view);
            loadUrl();
        }

    }


    private void hideErrorPage(WebView view) {
        // It will be blank
        String customErrorPageHtml = "<html></html>";
        view.loadData(customErrorPageHtml, "text/html", null);
    }

    private void init() {
        webView = findViewById(R.id.webView);
        noConnectionLayout = findViewById(R.id.no_connection_layout);
        btnRetry = findViewById(R.id.btn_retry);
    }



    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
