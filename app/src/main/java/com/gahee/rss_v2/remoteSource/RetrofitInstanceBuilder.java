package com.gahee.rss_v2.remoteSource;

import com.gahee.rss_v2.data.nasa.NasaAPI;
import com.gahee.rss_v2.data.time.TimeAPI;
import com.gahee.rss_v2.data.wwf.WwfAPI;
import com.gahee.rss_v2.data.youtube.YoutubeAPI;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static com.gahee.rss_v2.utils.Constants.NASA_BASE_URL;
import static com.gahee.rss_v2.utils.Constants.TIME_BASE_URL;
import static com.gahee.rss_v2.utils.Constants.WWF_BASE_URL;
import static com.gahee.rss_v2.utils.Constants.YOUTUBE_BASE_URL;

public class RetrofitInstanceBuilder {

    //from nasa xml
    private static final Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(NASA_BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create());
    private static final Retrofit retrofit = retrofitBuilder.build();
    private static final NasaAPI NASA_API = retrofit.create(NasaAPI.class);
    public static NasaAPI getNasaApi(){
        return NASA_API;
    }


    //from youtube xml
    private static final Retrofit.Builder retrofitBuilder_yt =
            new Retrofit.Builder()
                    .baseUrl(YOUTUBE_BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create());
    private static final Retrofit retrofit_yt = retrofitBuilder_yt.build();
    private static final YoutubeAPI YOUTUBE_API = retrofit_yt.create(YoutubeAPI.class);
    public static YoutubeAPI getYoutubeApi() {
        return YOUTUBE_API;
    }


    //from Time news xml
    private static final Retrofit.Builder retrofitBuilder_time = new Retrofit.Builder()
            .baseUrl(TIME_BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create());
    private static final Retrofit retrofit_time = retrofitBuilder_time.build();
    private static final TimeAPI TIME_API = retrofit_time.create(TimeAPI.class);
    public static TimeAPI getTimeApi() {
        return TIME_API;
    }

    //from world wildlife fund
    private static final Retrofit.Builder retrofitBuilder_wwf = new Retrofit.Builder()
            .baseUrl(WWF_BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create());
    private static final Retrofit retrofit_wwf = retrofitBuilder_time.build();
    private static final WwfAPI wwfAPI = retrofit_time.create(WwfAPI.class);
    public static WwfAPI getWwfAPI() {
        return wwfAPI;
    }

}