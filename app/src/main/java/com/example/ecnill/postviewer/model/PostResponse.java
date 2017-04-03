package com.example.ecnill.postviewer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * Created by ecnill on 2.4.17.
 */

public class PostResponse {

    @SerializedName("owner")
    @Expose
    @Getter
    private OwnerResponse ownerResponse;

    @SerializedName("view_count")
    @Expose
    @Getter
    private int viewCount;

    @SerializedName("question_id")
    @Expose
    @Getter
    private long questionId;

    @SerializedName("title")
    @Expose
    @Getter
    private String title;

    @SerializedName("body")
    @Expose
    @Getter
    private String body;

}