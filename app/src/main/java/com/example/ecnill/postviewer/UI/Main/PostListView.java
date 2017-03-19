package com.example.ecnill.postviewer.UI.Main;

import android.support.v4.app.Fragment;

/**
 * Created by ecnill on 14.3.17.
 */

public interface PostListView {

    void showProgress();

    void hideProgress();

    void showMessage(int resourceId);

    void notifyDataSetChanged(int pos);

    void loadMore();

    void showNextFragment(Fragment fragment);

}
