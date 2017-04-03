package com.example.ecnill.postviewer.ui.main;

import android.os.Handler;
import android.support.v4.app.Fragment;


import com.example.ecnill.postviewer.data.PostsDatabaseHelper;
import com.example.ecnill.postviewer.model.Post;
import com.example.ecnill.postviewer.ui.detail.DetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ecnill on 14.3.17.
 */

final class PostListPresenter implements Question.Presenter<Post>, Question.Model.OnFinishedListener<Post> {

    private static final String TAG = PostListPresenter.class.getSimpleName();

    private static final long ANIMATION_DELAY = 500;

    private final Question.View mPostListView;
    private final Question.Model<Post> mDataProvider;
    private final PostsDatabaseHelper mDatabaseHelper;

    private boolean mShouldShowEndListMess = true;

    private List<Post> mPostsList = new ArrayList<>();

    PostListPresenter(final Question.View postPostListView,
                      final Question.Model<Post> dataProvider,
                      final PostsDatabaseHelper databaseHelper) {
        this.mPostListView = postPostListView;
        this.mDataProvider = dataProvider;
        this.mDatabaseHelper = databaseHelper;
        initData();
    }

    @Override
    public void loadMore() {
        if (mPostListView != null) {
            Handler handler = new Handler();
            if (mDataProvider.hasMoreItems()) {
                mPostListView.showProgress();
                mDataProvider.downloadNextItems(this);
                handler.postDelayed(mPostListView::hideProgress, ANIMATION_DELAY);
            } else {
                if (mShouldShowEndListMess) {
                    mShouldShowEndListMess = false;
                    handler.postDelayed(mPostListView::showFinishLoadMessage, ANIMATION_DELAY);
                }
            }
        }
    }

    @Override
    public void itemClick(int pos) {
        Fragment fr = DetailFragment.newInstance(
                mPostsList.get(pos).getTitle(),
                mPostsList.get(pos).getHtmlDetail()
        );
        mPostListView.showNextFragment(fr);
    }

    @Override
    public List<Post> getAllData() {
        return mPostsList;
    }

    @Override
    public void onFinished(List<Post> items) {
        saveToDb(items);
        items.stream().filter(post -> !mPostsList.contains(post)).forEach(post -> {
            mPostsList.addAll(items);

            Runnable r = () -> mPostListView.notifyDataSetChanged(mPostsList.size() - 1);
            new Handler().post(r);
        });
    }

    private void initData() {
        if (mPostListView != null) {
            mDataProvider.downloadNextItems(this);
        }
    }

    private void saveToDb(final List<Post> posts) {
        Runnable r = () -> {
            for (Post post : posts) {
                mDatabaseHelper.createPost(post);
                mDatabaseHelper.createOwner(post.getOwner());
            }
        };
        new Handler().post(r);
    }

}
