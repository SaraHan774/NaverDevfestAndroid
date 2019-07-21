package com.gahee.rss_v2.remoteData;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.gahee.rss_v2.retrofitNasa.model.ArticleObj;
import com.gahee.rss_v2.retrofitNasa.model.ChannelObj;

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

    //youtube
    public void fetchYtData(){new FetchYtDataAsync(mRemoteDataUtils).execute();}



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

    private static class FetchYtDataAsync extends AsyncTask<Void, Void, Void>{
        RemoteDataUtils mRemoteDataUtils;

        public FetchYtDataAsync(RemoteDataUtils remoteDataUtils){
            this.mRemoteDataUtils = remoteDataUtils;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mRemoteDataUtils.fetchYTRemoteData();
            return null;
        }
    }

}