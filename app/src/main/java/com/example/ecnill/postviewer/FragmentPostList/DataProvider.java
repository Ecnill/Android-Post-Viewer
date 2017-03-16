package com.example.ecnill.postviewer.FragmentPostList;

import com.example.ecnill.postviewer.Entities.Post;

import java.util.List;

/**
 * Created by ecnill on 14.3.17.
 */

interface DataProvider {

    List<Post> downloadNextItems();

    boolean areAllItemsDownload();

}