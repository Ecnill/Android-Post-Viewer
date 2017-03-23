package com.example.ecnill.postviewer.Data;

import com.example.ecnill.postviewer.Data.Entities.Post;

import java.util.List;

/**
 * Created by ecnill on 22.3.17.
 */

public final class LocalProvider implements DataProvider {

    private static final String TAG = LocalProvider.class.getSimpleName();

    private final PostsDatabaseHelper mDatabaseHelper;

    public LocalProvider(PostsDatabaseHelper postsDatabaseHelper) {
        this.mDatabaseHelper = postsDatabaseHelper;
    }

    @Override
    public List<Post> downloadNextItems() {
        return mDatabaseHelper.getAllPosts();
    }

    @Override
    public boolean areAllItemsDownload() {
        return true;
    }

}
