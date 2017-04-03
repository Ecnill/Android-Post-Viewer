package com.example.ecnill.postviewer.network;

import com.example.ecnill.postviewer.model.ApiResponse;

import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.ecnill.postviewer.BuildConfig.BASE_URL;

/**
 * Created by ecnill on 2.4.17.
 */

public class NetworkService {

    private static final String baseUrl = BASE_URL;

    @Getter private final QuestionAPI api;

    public NetworkService() {
        this(baseUrl);
    }

    private NetworkService(String url) {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        api = retrofit.create(QuestionAPI.class);
    }

    public interface QuestionAPI {
        @GET(baseUrl + "/2.2/questions?filter=withbody&order=desc&sort=creation&site=cooking&pagesize=5")
        Call<ApiResponse> getQuestionsApi(@Query("page") String pageCounter);
    }

}