package com.example.ecnill.postviewer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * Created by ecnill on 2.4.17.
 */

public class OwnerResponse {

    @SerializedName("reputation")
    @Expose
    @Getter
    private int reputation;

    @SerializedName("user_id")
    @Expose
    @Getter
    private long userId;

    @SerializedName("profile_image")
    @Expose
    @Getter
    private String profileImage;

    @SerializedName("display_name")
    @Expose
    @Getter
    private String displayName;

}
