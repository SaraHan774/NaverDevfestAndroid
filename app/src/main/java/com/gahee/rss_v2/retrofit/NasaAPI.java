package com.gahee.rss_v2.retrofit;

import com.gahee.rss_v2.retrofit.tags.Rss;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NasaAPI {

    String BASE_URL = "https://www.nasa.gov/rss/dyn/";

    @GET("breaking_news.rss")
    Call<Rss> getBreakingNews();

    @GET("TWAN_vodcast.rss")
    Call<Rss> getVodcast();

    @GET("lg_image_of_the_day.rss")
    Call<Rss> getImageOfTheDay();

    @GET("NASA-in-Silicon-Valley.rss")
    Call<Rss> getNasaInSiliconValley();


//    	- image & text
//https://www.nasa.gov/rss/dyn/breaking_news.rss
//	- video
//https://www.nasa.gov/rss/dyn/TWAN_vodcast.rss
//	- image of the day
//https://www.nasa.gov/rss/dyn/lg_image_of_the_day.rss
//	- lot of mp3 files
//https://www.nasa.gov/rss/dyn/NASA-in-Silicon-Valley.rss
}
