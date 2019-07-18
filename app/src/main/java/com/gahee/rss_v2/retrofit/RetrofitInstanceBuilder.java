package com.gahee.rss_v2.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitInstanceBuilder {

    private static final String BASE_URL = "https://www.nasa.gov/rss/dyn/";
//    TWAN_vodcast.rss
    private static final Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create()); 
    
    private static final Retrofit retrofit = retrofitBuilder.build(); 
    
    private static final NasaAPI NASA_API = retrofit.create(NasaAPI.class);
    
    public static NasaAPI getNasaApi(){
        return NASA_API;
    }
}
