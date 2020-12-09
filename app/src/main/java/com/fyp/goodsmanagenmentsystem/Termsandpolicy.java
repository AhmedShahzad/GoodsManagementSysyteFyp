package com.fyp.goodsmanagenmentsystem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class Termsandpolicy extends AppCompatActivity {
    private WebView webView = null;
    TextView nodata;
    ProgressDialog progressDialog;
    ConnectionDetector connectionDetector;
    String url_Api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsandpolicy);

        nodata=findViewById(R.id.nodata);
        connectionDetector=new ConnectionDetector(Termsandpolicy.this);
        Toolbar toolbar=findViewById(R.id.top);
        TextView txttitle=findViewById(R.id.txttitle);
        TextView txtback=findViewById(R.id.txtback);
        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txttitle.setText("Terms And Services");
        try {
            url_Api = "https://www.khmerbargain.com/terms-of-service";

            webView = this.findViewById(R.id.webview);


            LoadUrlWebView( url_Api );
        }catch (Exception e){
        }
    if(connectionDetector.isConnectingToInternet())
    {
        nodata.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
    }
    else
    {
        nodata.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
    }
    }

    private void LoadUrlWebView( String url_api ) {
        try {
            webView.setWebViewClient(new WebViewClient());
            webView.setWebChromeClient(new MyWebChromeClient( url_api ));
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setAllowContentAccess(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(false);

            webView.loadUrl(url_api);
        } catch (Exception e) {
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        private String urlAccount;

        public MyWebChromeClient(String urlAccount) {
            this.urlAccount = urlAccount;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }
}
