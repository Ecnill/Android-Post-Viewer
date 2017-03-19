package com.example.ecnill.postviewer.UI.Detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.ecnill.postviewer.R;
import com.example.ecnill.postviewer.Network.CustomWebViewClient;

/**
 * Created by ecnill on 14.3.17.
 */

public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();

    private String mTitle;
    private String mHtmlCode;

    public DetailFragment() {
        Log.i(TAG, "constructor.");
    }

    public static DetailFragment newInstance(String title, String htmlCode) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("html", htmlCode);
        detailFragment.setArguments(args);
        return detailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            mTitle = b.getString("title");
            mHtmlCode = b.getString("html");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Activity activity = getActivity();

        TextView title = (TextView) activity.findViewById(R.id.txt_detail_title);
        title.setText(mTitle);

        CustomWebViewClient webViewClient = new CustomWebViewClient(activity, false);

        WebView webView = (WebView) activity.findViewById(R.id.web_view);
        webView.setWebViewClient(webViewClient);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.loadData(mHtmlCode, "text/html; charset=utf-8", "UTF-8");
    }

}
