package com.gahee.rss_v2.data.wwf;

import com.gahee.rss_v2.data.wwf.tags.Rss;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WwfAPI {
    String BASE_URL = "http://feeds.feedburner.com/";

    @GET("WWFStories")
    Call<Rss> getWWFStories();

}
