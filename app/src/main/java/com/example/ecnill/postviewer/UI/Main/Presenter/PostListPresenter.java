package com.example.ecnill.postviewer.UI.Main.Presenter;

import java.util.List;

/**
 * Created by ecnill on 14.3.17.
 */

public interface PostListPresenter<T> {

    void loadMore();

    void itemClick(int pos);

    List<T> getAllData();

}
