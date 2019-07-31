package com.gahee.rss_v2.remoteSource;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.gahee.rss_v2.data.reuters.model.ArticleReuters;
import com.gahee.rss_v2.data.reuters.model.ChannelReuters;
import com.gahee.rss_v2.data.time.model.TimeArticle;
import com.gahee.rss_v2.data.time.model.TimeChannel;
import com.gahee.rss_v2.data.wwf.model.WWFArticle;
import com.gahee.rss_v2.data.wwf.model.WWFChannel;

import java.util.ArrayList;

public class RemoteRepository {

    private final RssClient mRssClient;
    private static RemoteRepository instance;

    public static RemoteRepository getInstance() {
        if(instance == null){
            instance = new RemoteRepository();
        }
        return instance;
    }

    private RemoteRepository(){
        mRssClient = RssClient.getInstance();
    }

    private AsyncTask<Void, Void, Void> fetchReutersDataAsync;
    private AsyncTask<Void, Void, Void> fetchWWFDataAsync;
    private AsyncTask<Void, Void, Void> fetchTIMEDataAsync;


    public void fetchReutersData(){
        fetchReutersDataAsync = new FetchReutersDataAsync(mRssClient).execute();
    }

    //minimizing memory leak
    //call this in on stop of the app
    public void cancelReutersAsync(){
        if (fetchReutersDataAsync.getStatus() == AsyncTask.Status.RUNNING) {
            fetchReutersDataAsync.cancel(true);
        }
        fetchReutersDataAsync = null;
    }

    public void cancelWWFAsync(){
        if (fetchWWFDataAsync.getStatus() == AsyncTask.Status.RUNNING) {
            fetchWWFDataAsync.cancel(true);
        }
        fetchWWFDataAsync = null;
    }

    public void cancelTIMEAsync(){
        if (fetchTIMEDataAsync.getStatus() == AsyncTask.Status.RUNNING) {
            fetchTIMEDataAsync.cancel(true);
        }
        fetchTIMEDataAsync = null;
    }



    public MutableLiveData<ArrayList<ChannelReuters>> getReutersChannelMutableLiveData(){
        return mRssClient.getmChannelMutableLiveData();
    }

    public MutableLiveData<ArrayList<ArticleReuters>> getReutersArticleMutableLiveData(){
        return mRssClient.getmArticleMutableLiveData();
    }

    //Time news
    public void fetchTimeData(){
        fetchTIMEDataAsync = new FetchTimeDataAsync(mRssClient).execute();
    }
    public MutableLiveData<ArrayList<TimeChannel>> getTimeChannelLiveData(){
        return mRssClient.getmTimeChannelLiveData();
    }

    public MutableLiveData<ArrayList<TimeArticle>> getTimeArticleLiveData(){
        return mRssClient.getmTimeArticleLiveData();
    }

    //WWF articles
    public void fetchWWFData(){
        fetchWWFDataAsync = new FetchWWFDataAsync(mRssClient).execute();
    }
    public MutableLiveData<ArrayList<WWFChannel>> getWwfChannelLiveData(){
        return  mRssClient.getmWwfChannelLiveData();
    }
    public MutableLiveData<ArrayList<WWFArticle>> getWwfArticleLiveData(){
        return mRssClient.getmWwfArticleLiveData();
    }




    //fetching data async task
    private static class FetchReutersDataAsync extends AsyncTask<Void, Void, Void>{
        final RssClient mRssClient;

        FetchReutersDataAsync(RssClient rssClient){
            this.mRssClient = rssClient;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mRssClient.fetchRemoteReutersData();
            return null;
        }
    }


    private static class FetchTimeDataAsync extends AsyncTask<Void, Void, Void>{
        final RssClient mRssClient;

        FetchTimeDataAsync(RssClient rssClient){
            this.mRssClient = rssClient;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mRssClient.fetchRemoteTimeData();
            return null;
        }
    }

    private static class FetchWWFDataAsync extends AsyncTask<Void, Void, Void>{
        final RssClient mRssClient;

        FetchWWFDataAsync(RssClient rssClient){
            this.mRssClient = rssClient;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mRssClient.fetchRemoteWWFData();
            return null;
        }
    }

}
