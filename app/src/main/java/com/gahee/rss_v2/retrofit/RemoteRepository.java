package com.gahee.rss_v2.retrofit;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.gahee.rss_v2.retrofit.model.ArticleObj;
import com.gahee.rss_v2.retrofit.model.ChannelObj;

import java.util.ArrayList;

public class RemoteRepository {

    private final RemoteDataUtils mRemoteDataUtils;
    public static RemoteRepository instance;

    public static RemoteRepository getInstance() {
        if(instance == null){
            instance = new RemoteRepository();
        }
        return instance;
    }

    public RemoteRepository(){
        mRemoteDataUtils = RemoteDataUtils.getInstance();
    }

    public void fetchData(){
        new FetchDataAsync(mRemoteDataUtils).execute();
    }

    public MutableLiveData<ArrayList<ChannelObj>> getChannelMutableLiveData(){
        return mRemoteDataUtils.getmChannelMutableLiveData();
    }

    public MutableLiveData<ArrayList<ArticleObj>> getArticleMutableLiveData(){
        return mRemoteDataUtils.getmArticleMutableLiveData();
    }



    //fetching data async task
    private static class FetchDataAsync extends AsyncTask<Void, Void, Void>{
        RemoteDataUtils mRemoteDataUtils;

        public FetchDataAsync(RemoteDataUtils remoteDataUtils){
            this.mRemoteDataUtils = remoteDataUtils;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mRemoteDataUtils.fetchRemoteData();
            return null;
        }
    }

}
