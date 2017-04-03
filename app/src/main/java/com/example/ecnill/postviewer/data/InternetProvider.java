package com.example.ecnill.postviewer.data;

import android.support.annotation.NonNull;

import com.example.ecnill.postviewer.model.ApiResponse;
import com.example.ecnill.postviewer.model.Owner;
import com.example.ecnill.postviewer.model.OwnerResponse;
import com.example.ecnill.postviewer.model.Post;
import com.example.ecnill.postviewer.model.PostResponse;
import com.example.ecnill.postviewer.network.NetworkService;
import com.example.ecnill.postviewer.ui.main.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ecnill on 14.3.17.
 */

public final class InternetProvider implements Question.Model<Post> {

    private static final String TAG = InternetProvider.class.getSimpleName();

    private int mUrlCounter = 1;
    private final NetworkService mNetworkService;
    private boolean hasMore = true;

    public InternetProvider(NetworkService networkService) {
        this.mNetworkService = networkService;
    }

    @Override
    public void downloadNextItems(OnFinishedListener listener) {
        Call<ApiResponse> call = mNetworkService.getApi().getQuestionsApi(String.valueOf(mUrlCounter++));

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                listener.onFinished(getPostsList(response));
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                call.cancel();
            }
        });
    }

    @Override
    public boolean hasMoreItems() {
        return hasMore;
    }

    private List<Post> getPostsList(@NonNull Response<ApiResponse> response) {
        ApiResponse apiResponse = response.body();
        if (response.code() != 200) {
            return Collections.emptyList();
        }
        hasMore = apiResponse.isHasMore();

        List<Post> posts = new ArrayList<>();
        for(int i = 0; i < apiResponse.getPostResponses().size(); ++i) {
            PostResponse postResponse = apiResponse.getPostResponses().get(i);
            OwnerResponse ownerResponse = postResponse.getOwnerResponse();
            if (ownerResponse != null) {
                Post post = new Post.PostBuilder(
                        postResponse.getQuestionId(),
                        postResponse.getTitle(),
                        new Owner.OwnerBuilder(
                                ownerResponse.getUserId(),
                                ownerResponse.getDisplayName(),
                                ownerResponse.getProfileImage())
                                .setReputation(ownerResponse.getReputation())
                                .build())
                        .setHtmlDetail(postResponse.getBody())
                        .setViewCount(postResponse.getViewCount())
                        .build();
                posts.add(post);
            }
        }
        return posts;
    }

}
