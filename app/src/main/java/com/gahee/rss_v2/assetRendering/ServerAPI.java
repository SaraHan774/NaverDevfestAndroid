package com.gahee.rss_v2.assetRendering;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ServerAPI {

    String BASE = "http://www.nasa.gov/sites/default/files/atoms/";


    @GET("video/{url}")
    @Streaming
    Call<ResponseBody> downloadFile(@Path("url") String url);


}
