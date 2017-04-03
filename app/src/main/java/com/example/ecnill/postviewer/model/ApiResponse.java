package com.example.ecnill.postviewer.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Created by ecnill on 2.4.17.
 */

public class ApiResponse {

    @SerializedName("items")
    @Getter
    private List<PostResponse> postResponses = null;

    @SerializedName("has_more")
    @Getter
    private boolean hasMore;

}