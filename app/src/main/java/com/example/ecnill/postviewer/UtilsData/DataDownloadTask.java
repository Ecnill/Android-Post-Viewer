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

                        Owner owner = new Owner();

                        JSONObject owObj = o.getJSONObject("owner");
                        if (owObj.has("user_id")) {
                            owner.setId(owObj.getLong("user_id"));
                        }
                        if (owObj.has("display_name")) {
                            owner.setName(owObj.getString("display_name"));
                        }
                        if (owObj.has("profile_image")) {
                            owner.setProfileImageUrl(owObj.getString("profile_image"));
                        }
                        if (owObj.has("reputation")) {
                            owner.setReputation(owObj.getInt("reputation"));
                        }

                        Post post = new Post();
                        if (o.has("question_id")) {
                            post.setId(o.getLong("question_id"));
                        }
                        if (o.has("view_count")) {
                            post.setViewCount(o.getInt("view_count"));
                        }
                        if (o.has("title")) {
                            post.setTitle(o.getString("title"));
                        }
                        if (o.has("body")) {
                            post.setHtmlDetail(o.getString("body"));
                        }
                        post.setOwner(owner);

                        result.add(post);
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
