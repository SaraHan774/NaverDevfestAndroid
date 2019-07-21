package com.gahee.rss_v2.remoteDataSource;

import com.gahee.rss_v2.retrofitNasa.NasaAPI;
import com.gahee.rss_v2.retrofitTime.TimeAPI;
import com.gahee.rss_v2.retrofitYT.YoutubeAPI;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitInstanceBuilder {

    //from nasa xml
    private static final String BASE_URL = "https://www.nasa.gov/rss/dyn/";
    private static final Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create());
    private static final Retrofit retrofit = retrofitBuilder.build();
    private static final NasaAPI NASA_API = retrofit.create(NasaAPI.class);
    public static NasaAPI getNasaApi(){
        return NASA_API;
    }

    //from youtube xml
    private static final String BASE_URL_YT = "http://www.youtube.com/feeds/?";
    private static final Retrofit.Builder retrofitBuilder_yt =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL_YT)
                    .addConverterFactory(SimpleXmlConverterFactory.create());
    private static final Retrofit retrofit_yt = retrofitBuilder_yt.build();
    private static final YoutubeAPI YOUTUBE_API = retrofit_yt.create(YoutubeAPI.class);
    public static YoutubeAPI getYoutubeApi() {
        return YOUTUBE_API;
    }


    //from Time news xml
    public static final String BASE_URL_TIME = "http://feeds2.feedburner.com/";
    private static final Retrofit.Builder retrofitBuilder_time = new Retrofit.Builder()
            .baseUrl(BASE_URL_TIME)
            .addConverterFactory(SimpleXmlConverterFactory.create());
    private static final Retrofit retrofit_time = retrofitBuilder_time.build();
    private static final TimeAPI TIME_API = retrofit_time.create(TimeAPI.class);
    public static TimeAPI getTimeApi() {
        return TIME_API;
    }

}
