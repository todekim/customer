package com.teajintech.customer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Message;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private final String TAG="main_activity";
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        Resources res = getResources();
        String homeUrl = res.getString(R.string.homeUrl);

        webview = findViewById(R.id.webView);

        webview.setNetworkAvailable(true);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);//Tells the WebView to enable JavaScript execution.
        settings.setDomStorageEnabled(true);//Sets whether the DOM storage API is enabled.
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//Tells JavaScript to open windows automatically.
        settings.setSupportMultipleWindows(true);//Sets whether the WebView whether supports multiple windows.
        webview.setWebChromeClient(new MyWebChromeClient());
        webview.setWebViewClient(new MyWebViewClient(getApplicationContext()));
        webview.loadUrl(homeUrl);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (webview.canGoBack()) { // 뒤로가기 눌렀을 때, 뒤로 갈 곳이 있을 경우
            webview.goBack(); // 뒤로가기
        } else {//뒤로 갈 곳이 없는 경우
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("종료!")
                    .setMessage("종료하시겠습니까?")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("아니오", null)
                    .show();
        }
    }
}

