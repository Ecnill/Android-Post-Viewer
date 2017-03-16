package com.example.ecnill.postviewer.FragmentPostList;

import com.example.ecnill.postviewer.Entities.Post;
import com.example.ecnill.postviewer.Utils.Utils;
import com.example.ecnill.postviewer.UtilsData.DataDownloadTask;

import java.util.List;

import static com.example.ecnill.postviewer.BuildConfig.API_URL;

/**
 * Created by ecnill on 14.3.17.
 */

class InternetProvider implements DataProvider {

    private static final String TAG = InternetProvider.class.getSimpleName();

    private String mUrl = API_URL;

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