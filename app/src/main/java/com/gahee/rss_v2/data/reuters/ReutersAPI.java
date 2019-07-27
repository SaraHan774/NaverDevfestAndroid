package com.gahee.rss_v2.data.reuters;

import com.gahee.rss_v2.data.reuters.tags.Rss;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ReutersAPI {

    String BASE_URL = "http://feeds.reuters.com/reuters/";

    @GET("USVideoTopNews")
    Call<Rss> getUSVideoTopNews();


}
