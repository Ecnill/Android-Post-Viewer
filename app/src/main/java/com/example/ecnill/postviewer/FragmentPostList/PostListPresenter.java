package com.example.ecnill.postviewer.FragmentPostList;

import java.util.List;

/**
 * Created by ecnill on 14.3.17.
 */

interface PostListPresenter<T> {

    void loadMore();

    void itemClick(int pos);

    List<T> getAllData();

}
