package com.gahee.rss_v2.data.time;

import com.gahee.rss_v2.data.time.tags.Rss;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TimeAPI {

    String TIME_BASE_URL = "http://feeds2.feedburner.com/";

//    @GET("time/topstories")
//    Call<Rss> getTimeTopStories();
//
//    @GET("time/world")
//    Call<Rss> getTimeWorld();
//
//    @GET("time/business")
//    Call<Rss> getTimeBusiness();
//
//    @GET("time/scienceandhealth")
//    Call<Rss> getTimeScienceAndHealth();

    @GET("time/entertainment")
    Call<Rss> getTimeEntertainment();


}
