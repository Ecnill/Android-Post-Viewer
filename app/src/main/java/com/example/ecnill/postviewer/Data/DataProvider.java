package com.example.ecnill.postviewer.Data;

import com.example.ecnill.postviewer.Data.Entities.Post;

import java.util.List;

/**
 * Created by ecnill on 14.3.17.
 */

public interface DataProvider {

    List<Post> downloadNextItems();

    boolean areAllItemsDownload();

}