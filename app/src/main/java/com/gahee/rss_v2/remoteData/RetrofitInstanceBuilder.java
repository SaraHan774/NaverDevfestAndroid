package com.gahee.rss_v2.remoteData;

import com.gahee.rss_v2.retrofitNasa.NasaAPI;
import com.gahee.rss_v2.retrofitYT.YoutubeAPI;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitInstanceBuilder {

    private static final String BASE_URL = "https://www.nasa.gov/rss/dyn/";
    public static final String BASE_URL_YT = "http://www.youtube.com/feeds/?";

    private static final Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create());

    private static final Retrofit.Builder retrofitBuilder_yt =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL_YT)
                    .addConverterFactory(SimpleXmlConverterFactory.create());

    private static final Retrofit retrofit = retrofitBuilder.build(); 
    public static final Retrofit retrofit_yt = retrofitBuilder_yt.build();

    private static final NasaAPI NASA_API = retrofit.create(NasaAPI.class);
    private static final YoutubeAPI YOUTUBE_API = retrofit_yt.create(YoutubeAPI.class);

    public static NasaAPI getNasaApi(){
        return NASA_API;
    }
    public static YoutubeAPI getYoutubeApi() {
        return YOUTUBE_API;
    }
}
