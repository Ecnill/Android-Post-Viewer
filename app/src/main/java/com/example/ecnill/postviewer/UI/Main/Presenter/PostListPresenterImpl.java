package com.example.ecnill.postviewer.UI.Main.Presenter;

import android.os.Handler;
import android.support.v4.app.Fragment;

import com.example.ecnill.postviewer.Data.Entities.Post;
import com.example.ecnill.postviewer.Data.DataProvider;
import com.example.ecnill.postviewer.Data.PostsDatabaseHelper;
import com.example.ecnill.postviewer.R;
import com.example.ecnill.postviewer.UI.Detail.DetailFragment;
import com.example.ecnill.postviewer.UI.Main.PostListView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by ecnill on 14.3.17.
 */

public final class PostListPresenterImpl implements PostListPresenter<Post> {

    private static final String TAG = PostListPresenterImpl.class.getSimpleName();

    private static final long ANIMATION_DELAY = 500;

    private final PostListView mPostListView;
    private final DataProvider mDataProvider;
    private final PostsDatabaseHelper mDatabaseHelper;

    private boolean mShouldShowEndListMess = true;

    private List<Post> mPostsList = new ArrayList<>();

    public PostListPresenterImpl(final PostListView postPostListView,
                                 final DataProvider dataProvider,
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
            if (!mDataProvider.areAllItemsDownload()) {
                
                mPostListView.showProgress();
                List<Post> newPosts =  mDataProvider.downloadNextItems();
                saveToDb(newPosts);
                for(Post post : newPosts) {
                    if (!mPostsList.contains(post)) {
                        mPostsList.addAll(newPosts);

                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                mPostListView.notifyDataSetChanged(mPostsList.size() - 1);
                            }
                        };
                        new Handler().post(r);

                    }
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPostListView.hideProgress();
                    }
                }, ANIMATION_DELAY);
            } else {
                
                if (mShouldShowEndListMess) {
                    mShouldShowEndListMess = false;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPostListView.showMessage(R.string.all_data_loaded_message);
                        }
                    }, ANIMATION_DELAY);
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


    private void initData() {
        if (mPostListView != null) {
            List<Post> newPosts =  mDataProvider.downloadNextItems();
            mPostsList.addAll(newPosts);
        }
    }

    private void saveToDb(final List<Post> posts) {
        Runnable r = new Runnable(){
            public void run() {
                for (Post post : posts) {
                    mDatabaseHelper.createPost(post);
                    mDatabaseHelper.createOwner(post.getOwner());
                }
            }
        };
        new Handler().post(r);
    }

}
