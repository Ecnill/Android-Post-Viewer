package com.example.ecnill.postviewer.Data;

import com.example.ecnill.postviewer.Data.Entities.Post;
import com.example.ecnill.postviewer.Utils.Utils;
import com.example.ecnill.postviewer.Network.DataDownloadTask;

import java.util.List;

/**
 * Created by ecnill on 14.3.17.
 */

public class InternetProvider implements DataProvider {

    private static final String TAG = InternetProvider.class.getSimpleName();

//    private String mUrl = API_URL;

    private String mUrl = "https://raw.githubusercontent.com/Ecnill/Android-Post-Viewer/master/app/src/test/tmpurlfiles/example.url.com%26pagesize%3D5%26page%3D1";

    private int mUrlCounter = 1;
    private DataDownloadTask mDownloadTask;

    @Override
    public List<Post> downloadNextItems() {
        mDownloadTask = new DataDownloadTask(mUrl);
        mDownloadTask.execute();

        mUrlCounter++;
        mUrl = Utils.replaceLastChar(mUrl, mUrlCounter);
        return mDownloadTask.getResult();
    }

    @Override
    public boolean areAllItemsDownload() {
        return mDownloadTask != null && mDownloadTask.noMoreData();
    }

}