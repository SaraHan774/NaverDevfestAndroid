package com.gahee.rss_v2.remoteSource;

import com.gahee.rss_v2.data.reuters.ReutersAPI;
import com.gahee.rss_v2.data.time.TimeAPI;
import com.gahee.rss_v2.data.wwf.WwfAPI;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static com.gahee.rss_v2.utils.Constants.REUTERS_BASE_URL;
import static com.gahee.rss_v2.utils.Constants.TIME_BASE_URL;
import static com.gahee.rss_v2.utils.Constants.WWF_BASE_URL;

class RetrofitInstanceBuilder {

    //from reuters xml
    private static final Retrofit.Builder retrofitBuilder_reuters =
            new Retrofit.Builder()
            .baseUrl(REUTERS_BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create());
    private static final Retrofit retrofit = retrofitBuilder_reuters.build();
    private static final ReutersAPI REUTERS_API = retrofit.create(ReutersAPI.class);
    public static ReutersAPI getReutersAPI(){
        return REUTERS_API;
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
    private static final Retrofit retrofit_wwf = retrofitBuilder_wwf.build();
    private static final WwfAPI wwfAPI = retrofit_wwf.create(WwfAPI.class);
    public static WwfAPI getWwfAPI() {
        return wwfAPI;
    }

}
