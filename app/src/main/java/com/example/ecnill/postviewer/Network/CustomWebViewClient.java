package com.example.ecnill.postviewer.Network;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ecnill on 14.3.17.
 */

public final class CustomWebViewClient extends WebViewClient {

    private static final String TAG = CustomWebViewClient.class.getSimpleName();
    private boolean shouldShowInExternalBrowser = false;
    private final Activity mActivity;

    public CustomWebViewClient(final Activity activity, final boolean shouldShowInExternalBrowser) {
        this.mActivity = activity;
        this.shouldShowInExternalBrowser = shouldShowInExternalBrowser;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        final Uri uri = Uri.parse(url);
        return handleUri(uri);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        final Uri uri = request.getUrl();
        return handleUri(uri);
    }

    private boolean handleUri(final Uri uri) {
        if (!shouldShowInExternalBrowser) {
            return false;
        } else {
            final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mActivity.startActivity(intent);
            return true;
        }
    }

}

