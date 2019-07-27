package com.gahee.rss_v2.remoteSource;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.gahee.rss_v2.data.reuters.model.ArticleObj;
import com.gahee.rss_v2.data.reuters.model.ChannelObj;
import com.gahee.rss_v2.data.time.model.TimeArticle;
import com.gahee.rss_v2.data.time.model.TimeChannel;
import com.gahee.rss_v2.data.wwf.model.WWFArticle;
import com.gahee.rss_v2.data.wwf.model.WWFChannel;
import com.gahee.rss_v2.data.youtube.model.YoutubeChannel;
import com.gahee.rss_v2.data.youtube.model.YoutubeVideo;

import java.util.ArrayList;

public class RemoteRepository {

    private final RssClient mRssClient;
    public static RemoteRepository instance;

    public static RemoteRepository getInstance() {
        if(instance == null){
            instance = new RemoteRepository();
        }
        return instance;
    }

    public RemoteRepository(){
        mRssClient = RssClient.getInstance();
    }

    private AsyncTask<Void, Void, Void> fetchReutersDataAsync;

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



    public MutableLiveData<ArrayList<ChannelObj>> getReutersChannelMutableLiveData(){
        return mRssClient.getmChannelMutableLiveData();
    }

    public MutableLiveData<ArrayList<ArticleObj>> getReutersArticleMutableLiveData(){
        return mRssClient.getmArticleMutableLiveData();
    }

    //youtube
    public void fetchYtData(){new FetchYtDataAsync(mRssClient).execute();}
    public MutableLiveData<ArrayList<YoutubeChannel>> getYoutubeChannelLiveData(){
        return mRssClient.getmYoutubeChannelLiveData();
    }
    public MutableLiveData<ArrayList<YoutubeVideo>> getYoutubeVideoLiveData(){
        return mRssClient.getmYoutubeVideoLiveData();
    }

    //Time news
    public void fetchTimeData(){new FetchTimeDataAsync(mRssClient).execute();}
    public MutableLiveData<ArrayList<TimeChannel>> getTimeChannelLiveData(){
        return mRssClient.getmTimeChannelLiveData();
    }

    public MutableLiveData<ArrayList<TimeArticle>> getTimeArticleLiveData(){
        return mRssClient.getmTimeArticleLiveData();
    }

    //WWF articles
    public void fetchWWFData(){new FetchWWFDataAsync(mRssClient).execute();}
    public MutableLiveData<ArrayList<WWFChannel>> getWwfChannelLiveData(){
        return  mRssClient.getmWwfChannelLiveData();
    }
    public MutableLiveData<ArrayList<WWFArticle>> getWwfArticleLiveData(){
        return mRssClient.getmWwfArticleLiveData();
    }




    //fetching data async task
    private static class FetchReutersDataAsync extends AsyncTask<Void, Void, Void>{
        RssClient mRssClient;

        public FetchReutersDataAsync(RssClient rssClient){
            this.mRssClient = rssClient;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mRssClient.fetchRemoteReutersData();
            return null;
        }
    }

    private static class FetchYtDataAsync extends AsyncTask<Void, Void, Void>{
        RssClient mRssClient;

        public FetchYtDataAsync(RssClient rssClient){
            this.mRssClient = rssClient;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mRssClient.fetchRemoteYoutubeData();
            return null;
        }
    }

    private static class FetchTimeDataAsync extends AsyncTask<Void, Void, Void>{
        RssClient mRssClient;

        public FetchTimeDataAsync(RssClient rssClient){
            this.mRssClient = rssClient;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mRssClient.fetchRemoteTimeData();
            return null;
        }
    }

    private static class FetchWWFDataAsync extends AsyncTask<Void, Void, Void>{
        RssClient mRssClient;

        public FetchWWFDataAsync(RssClient rssClient){
            this.mRssClient = rssClient;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mRssClient.fetchRemoteWWFData();
            return null;
        }
    }

}
