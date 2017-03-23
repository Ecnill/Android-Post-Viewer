package com.example.ecnill.postviewer.Network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ecnill.postviewer.Data.Entities.Owner;
import com.example.ecnill.postviewer.Data.Entities.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Created by ecnill on 14.3.17.
 */

public final class DataDownloadTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = DataDownloadTask.class.getSimpleName();

    private boolean done = false;
    @Getter private boolean noMoreItems = false;

    private final String mUrl;

    private final Object mLock = new Object();
    private List<Post> mResult = new ArrayList<>();

    public DataDownloadTask (String url) {
        mUrl = url;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpHandler sh = new HttpHandler();
        String jsonStr = sh.makeServiceCall(mUrl);
        Log.d(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jo = new JSONObject(jsonStr);
                if (jo.getBoolean("has_more")) {
                    JSONArray jsonArray = jo.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject o = jsonArray.getJSONObject(i);

                        JSONObject owObj = o.getJSONObject("owner");
                        if (owObj.has("user_id") && owObj.has("display_name") && owObj.has("profile_image")) {

                            long ownerId = owObj.getLong("user_id");
                            String ownerUrl = owObj.getString("profile_image");
                            String ownerName = owObj.getString("display_name");
                            int ownerRep = 0;

                            if (owObj.has("reputation")) {
                                ownerRep = owObj.getInt("reputation");
                            }

                            if (o.has("question_id") && o.has("title")) {
                                long postId = o.getLong("question_id");
                                String postTitle = o.getString("title");
                                String postHtml = "";
                                int postViewCount = 0;

                                if (o.has("view_count")) {
                                    postViewCount = o.getInt("view_count");
                                }
                                if (o.has("body")) {
                                    postHtml = o.getString("body");
                                }

                                Post post = new Post.PostBuilder(
                                        postId,
                                        postTitle,
                                        new Owner.OwnerBuilder(
                                                ownerId,
                                                ownerName,
                                                ownerUrl)
                                                .setReputation(ownerRep)
                                                .build())
                                        .setHtmlDetail(postHtml)
                                        .setViewCount(postViewCount)
                                        .build();
                                mResult.add(post);
                            }
                        }
                    }
                } else {
                    noMoreItems = true;
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            } finally {
                done = true;
                synchronized (mLock) {
                    mLock.notifyAll();
                }
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
        return null;
    }

    public List<Post> getDownloadedItems()  {
        synchronized (mLock) {
            if (!done) {
                try {
                    mLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return mResult;
    }

}
