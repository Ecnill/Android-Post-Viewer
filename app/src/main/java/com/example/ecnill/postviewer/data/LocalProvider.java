package com.example.ecnill.postviewer.data;

import com.example.ecnill.postviewer.model.Post;
import com.example.ecnill.postviewer.ui.main.Question;

/**
 * Created by ecnill on 22.3.17.
 */

public final class LocalProvider implements Question.Model<Post> {

    private static final String TAG = LocalProvider.class.getSimpleName();

    private final PostsDatabaseHelper mDatabaseHelper;

    public LocalProvider(PostsDatabaseHelper postsDatabaseHelper) {
        this.mDatabaseHelper = postsDatabaseHelper;
    }

    @Override
    public void downloadNextItems(OnFinishedListener<Post> listener) {
        listener.onFinished(mDatabaseHelper.getAllPosts());
    }

    @Override
    public boolean hasMoreItems() {
        return true;
    }

}
