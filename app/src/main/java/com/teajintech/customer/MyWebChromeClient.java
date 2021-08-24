package com.teajintech.customer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class MyWebChromeClient extends WebChromeClient {
    private final String TAG = "MyWebChromeClient";

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        Log.i(TAG, "onProgressChanged(view:" + view.toString() + ", newProgress:" + newProgress + ")");
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        //MyLog.toastMakeTextShow(view.getContext(), "TAG", "window.open 협의가 필요합니다.");
        WebView newWebView = new WebView(view.getContext());
        WebSettings webSettings = newWebView.getSettings();
        WebSettings settings = newWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);

        //final Dialog dialog = new Dialog(view.getContext(),R.style.Theme_DialogFullScreen);

        final Dialog dialog = new Dialog(view.getContext());
        WindowManager.LayoutParams wmlp = new WindowManager.LayoutParams();
        wmlp.copyFrom(dialog.getWindow().getAttributes());
        wmlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmlp.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.setContentView(newWebView);
        dialog.show();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    //Log.toastMakeTextShow(view.getContext(), "TAG", "KEYCODE_BACK");
                    if (newWebView.canGoBack()) {
                        newWebView.goBack();
                    } else {
                        //Log.toastMakeTextShow(view.getContext(), "TAG", "Window.open 종료");
                        dialog.dismiss();
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });

        Window window = dialog.getWindow();
        window.setAttributes(wmlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        newWebView.setWebViewClient(new MyWebViewClient(view.getContext()));
        newWebView.setWebChromeClient(new MyWebChromeClient() {
            @Override
            public void onCloseWindow(WebView window) {
                dialog.dismiss();
            }
        });
        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        transport.setWebView(newWebView);
        resultMsg.sendToTarget();
        return true;
    }

    @Override
    public void onCloseWindow(WebView window) {
        Log.i(getClass().getName(), "onCloseWindow");
        window.setVisibility(View.GONE);
        window.destroy();
        //mWebViewSub=null;
        super.onCloseWindow(window);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        Log.i(getClass().getName(), "onJsAlert() url:" + url + ", message:" + message);
        //return super.onJsAlert(view, url, message, result);
        new AlertDialog.Builder(view.getContext()).setTitle("").setMessage(message).setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result.confirm();
            }
        }).setCancelable(false).create().show();
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        Log.i(getClass().getName(), "onJsConfirm() url:" + url + ", message" + message);
        //return super.onJsConfirm(view, url, message, result);
        new AlertDialog.Builder(view.getContext()).setTitle("").setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result.confirm();
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result.cancel();
            }
        }).create().show();
        return true;
    }
}
