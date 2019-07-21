package com.gahee.rss_v2.retrofitYT;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeAPI {

    String BASE_URL_YT = "http://www.youtube.com/feeds/";

    @GET("videos.xml?channel_id=UCD_grdLAvD4nqcqck2E-tuw")
    Call<Feed> getYoutubeChannel();
//https://www.youtube.com/feeds/
}
