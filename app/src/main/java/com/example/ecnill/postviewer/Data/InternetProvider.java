package com.example.ecnill.postviewer.Data;

import com.example.ecnill.postviewer.Data.Entities.Post;
import com.example.ecnill.postviewer.Network.DataDownloadTask;
import com.example.ecnill.postviewer.Utils.StringUtils;

import java.util.List;

import static com.example.ecnill.postviewer.BuildConfig.API_URL;

/**
 * Created by ecnill on 14.3.17.
 */

public final class InternetProvider implements DataProvider {

    private static final String TAG = InternetProvider.class.getSimpleName();

    private int mUrlCounter = 1;
    private DataDownloadTask mDownloadTask;
    private String mUrl = API_URL;

//    private String mUrl = "https://raw.githubusercontent.com/Ecnill/Android-Post-Viewer/master/app/src/test/tmpurlfiles/example.url.com%26pagesize%3D5%26page%3D1";

    @Override
    public List<Post> downloadNextItems() {
        mDownloadTask = new DataDownloadTask(mUrl);
        mDownloadTask.execute();

        mUrlCounter++;
        mUrl = StringUtils.replaceLastChar(mUrl, mUrlCounter);
        return mDownloadTask.getDownloadedItems();
    }

    @Override
    public boolean areAllItemsDownload() {
        return mDownloadTask != null && mDownloadTask.isNoMoreItems();
    }

}