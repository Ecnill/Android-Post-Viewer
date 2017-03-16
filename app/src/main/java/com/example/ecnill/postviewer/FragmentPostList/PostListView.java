package com.example.ecnill.postviewer.FragmentPostList;

import android.support.v4.app.Fragment;

/**
 * Created by ecnill on 14.3.17.
 */

interface PostListView {

    void showProgress();

    void hideProgress();

    void showMessage(int resourceId);

    void notifyDataSetChanged(int pos);

    void loadMore();

    void showNextFragment(Fragment fragment);

}
