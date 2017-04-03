package com.example.ecnill.postviewer.ui.main;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by ecnill on 1.4.17.
 */

public interface Question {

    interface Model<T> {
        interface OnFinishedListener<T> {
            void onFinished(List<T> items);
        }
        void downloadNextItems(OnFinishedListener<T> listener);
        boolean hasMoreItems();
    }

    interface View {
        void showProgress();
        void hideProgress();
        void showFinishLoadMessage();
        void notifyDataSetChanged(int pos);
        void loadMore();
        void showNextFragment(Fragment fragment);
    }

    interface Presenter<T> {
        void loadMore();
        void itemClick(int pos);
        List<T> getAllData();
    }
}
