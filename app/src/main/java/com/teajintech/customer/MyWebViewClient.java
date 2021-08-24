package com.teajintech.customer;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MyWebViewClient extends WebViewClient {
    private String TAG = "MyWebViewClient";
    private Context mApplicationContext = null;

    public MyWebViewClient(Context _applicationContext) {
        mApplicationContext = _applicationContext;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Log.i(TAG, "shouldOverrideUrlLoading(view:" + view + ", request:" + request + ")");
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.i(TAG, "onPageStarted(view:" + view + ", url:" + url + ", favicon:" + favicon + ")");
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.i(TAG, "onPageFinished(view:" + view + ", url:" + url + ")");
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Log.i(TAG, "onReceivedError() " + error.getErrorCode() + " ---> " + error.getDescription());
            onReceivedError(error.getErrorCode(), String.valueOf(error.getDescription()));
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Log.i(TAG, "onReceivedError() " + errorCode + " ---> " + description);
            onReceivedError(errorCode, description);
        }
    }

    private void onReceivedError(int errorCode, String description) {
        Log.i(getClass().getName(), "onReceivedError() " + errorCode + " ---> " + description);
        switch (errorCode) {
            case WebViewClient.ERROR_TIMEOUT: //연결 시간 초과
            case WebViewClient.ERROR_CONNECT: //서버로 연결 실패
                // case WebViewClient.ERROR_UNKNOWN: // 일반 오류
            case WebViewClient.ERROR_FILE_NOT_FOUND: //404
            case WebViewClient.ERROR_HOST_LOOKUP:
            case WebViewClient.ERROR_UNSUPPORTED_AUTH_SCHEME:
            case WebViewClient.ERROR_AUTHENTICATION:
            case WebViewClient.ERROR_PROXY_AUTHENTICATION:
            case WebViewClient.ERROR_IO:
            case WebViewClient.ERROR_REDIRECT_LOOP:
            case WebViewClient.ERROR_UNSUPPORTED_SCHEME:
            case WebViewClient.ERROR_FAILED_SSL_HANDSHAKE:
            case WebViewClient.ERROR_BAD_URL:
            case WebViewClient.ERROR_FILE:
            case WebViewClient.ERROR_TOO_MANY_REQUESTS:
            case WebViewClient.ERROR_UNSAFE_RESOURCE:
                Toast.makeText(mApplicationContext, "WebViewClient,onReceivedError(" + errorCode + ") 에러 발생 ",Toast.LENGTH_LONG).show();
                Log.e(TAG, "WebViewClient,onReceivedError(" + errorCode + ") 에러 발생 ");
                break;
        }
    }
}
