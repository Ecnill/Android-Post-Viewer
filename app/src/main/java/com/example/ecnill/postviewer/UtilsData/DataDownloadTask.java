package com.example.ecnill.postviewer.UtilsData;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ecnill.postviewer.Entities.Owner;
import com.example.ecnill.postviewer.Entities.Post;
import com.example.ecnill.postviewer.Utils.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ecnill on 14.3.17.
 */

public class DataDownloadTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = DataDownloadTask.class.getSimpleName();

    private boolean done = false;
    private boolean noMoreItems = false;

    private String mUrl;

    private List<Post> result = new ArrayList<>();

    private final Object lock = new Object();

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
                                result.add(post);
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
                synchronized (lock) {
                    lock.notifyAll();
                }
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) { }

    public List<Post> getResult()  {
        synchronized (lock) {
            if (!done) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public boolean noMoreData() {
        return noMoreItems;
    }

}
