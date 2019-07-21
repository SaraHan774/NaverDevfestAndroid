package com.gahee.rss_v2.remoteDataSource;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.gahee.rss_v2.retrofitNasa.NasaAPI;
import com.gahee.rss_v2.retrofitNasa.model.ArticleObj;
import com.gahee.rss_v2.retrofitNasa.model.ChannelObj;
import com.gahee.rss_v2.retrofitNasa.tags.Channel;
import com.gahee.rss_v2.retrofitNasa.tags.Item;
import com.gahee.rss_v2.retrofitNasa.tags.Rss;
import com.gahee.rss_v2.retrofitTime.TimeAPI;
import com.gahee.rss_v2.retrofitTime.model.TimeArticle;
import com.gahee.rss_v2.retrofitTime.model.TimeChannel;
import com.gahee.rss_v2.retrofitYT.model.YoutubeChannel;
import com.gahee.rss_v2.retrofitYT.model.YoutubeVideo;
import com.gahee.rss_v2.retrofitYT.tags.Entry;
import com.gahee.rss_v2.retrofitYT.tags.Feed;
import com.gahee.rss_v2.retrofitYT.YoutubeAPI;
import com.gahee.rss_v2.retrofitYT.tags.Media;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataUtils {

    private static final String TAG = "RemoteDataUtils";


    private MutableLiveData<ArrayList<ChannelObj>> mChannelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ArticleObj>> mArticleMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<ArrayList<YoutubeChannel>> mYoutubeChannelLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<YoutubeVideo>> mYoutubeVideoLiveData = new MutableLiveData<>();

    private MutableLiveData<ArrayList<TimeChannel>> mTimeChannelLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TimeArticle>> mTimeArticleLiveData = new MutableLiveData<>();


    private static RemoteDataUtils instance;

    public static RemoteDataUtils getInstance() {
        if(instance == null){
            instance = new RemoteDataUtils();
        }
        return instance;
    }

    private ArrayList<ChannelObj> mChannelObjArrayList = new ArrayList<>();
    private ArrayList<ArticleObj> mArticleObjArrayList = new ArrayList<>();
    private void fetchDataFromNasa(){

        NasaAPI nasaAPI = RetrofitInstanceBuilder.getNasaApi();
        Call<Rss> call = nasaAPI.getBreakingNews();

        call.enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                if(response.body() != null && response.body().getChannel() != null){
                    Channel channel = response.body().getChannel();
                    String title = channel.getChannelTitle();
                    String description = channel.getChannelDescription();
                    String link = channel.getChannelLink();
                    List<Item> listOfItems = channel.getItem();
                    String imageUrl = channel.getImage() != null ? channel.getImage().getUrl() : null;

                    Log.d(TAG, "channel : " + channel + "\n" + "title : " + title + "\n" + "description : " + description + "\n" + "Image : " + imageUrl + "\n");
                    ChannelObj channelObj = new ChannelObj(title, description, link, listOfItems, imageUrl);
                    mChannelObjArrayList.add(channelObj);

                    //loop through the list of items and store all the articles in one array
                    storeEachArticles(listOfItems, channel);
                }
                mChannelMutableLiveData.setValue(mChannelObjArrayList);
                mArticleMutableLiveData.setValue(mArticleObjArrayList);
            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {

                Log.d(TAG, "failed to fetch data :  " + t.getMessage());

            }
        });
    }


    private ArrayList<YoutubeChannel> youtubeChannelArrayList = new ArrayList<>();
    private ArrayList<YoutubeVideo> youtubeVideoArrayList = new ArrayList<>();
    private void fetchDataFromYoutube(){
        YoutubeAPI youtubeAPI = RetrofitInstanceBuilder.getYoutubeApi();
        Call<Feed> call = youtubeAPI.getYoutubeChannel();
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                if(response.body() != null && response.body().getEntries() != null){
                    String channelId = response.body().getChannelId();
                    String channelTitle = response.body().getTitle();
                    List<Entry> entries = response.body().getEntries();
                    YoutubeChannel youtubeChannel = new YoutubeChannel(channelId, channelTitle, entries);
                    youtubeChannelArrayList.add(youtubeChannel);

                    mYoutubeChannelLiveData.setValue(youtubeChannelArrayList);
                    storeEachVideos(entries);
//                        Log.d(TAG, "youtube feed title : " + response.body().getTitle() + "\n"
//                                + "author : " + response.body().getEntries().get(i).getAuthor().getName() + "\n"
//                                + "media : " + response.body().getEntries().get(i).getMedia() + "\n"
//                                + "media thumbnail : " + response.body().getEntries().get(i).getMedia().getThumbnail() )

                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                    Log.d(TAG, "failed to fetch from youtube" + t.getMessage());
            }
        });
    }


    private ArrayList<TimeChannel> timeChannelArrayList = new ArrayList<>();
    private ArrayList<TimeArticle> timeArticleArrayList = new ArrayList<>();

    private void fetchDataFromTime(){

        TimeAPI timeAPI = RetrofitInstanceBuilder.getTimeApi();
        Call<com.gahee.rss_v2.retrofitTime.tags.Rss> call = timeAPI.getTimeEntertainment();
        call.enqueue(new Callback<com.gahee.rss_v2.retrofitTime.tags.Rss>() {
            @Override
            public void onResponse(Call<com.gahee.rss_v2.retrofitTime.tags.Rss> call, Response<com.gahee.rss_v2.retrofitTime.tags.Rss> response) {
                if(response.body() != null){
                    String channelTitle = response.body().getChannel().getTitle();
                    String channelDescription = response.body().getChannel().getDescription();
                    List<com.gahee.rss_v2.retrofitTime.tags.Item> items = response.body().getChannel().getItems();

                    TimeChannel timeChannel = new TimeChannel(channelTitle, channelDescription, items);
                    timeChannelArrayList.add(timeChannel);
                    mTimeChannelLiveData.setValue(timeChannelArrayList);
                    storeEachTimeArticle(items);
                }
            }

            @Override
            public void onFailure(Call<com.gahee.rss_v2.retrofitTime.tags.Rss> call, Throwable t) {
                Log.d(TAG, "failed to fetch from time : " + "\n\n" +
                        t.getStackTrace() +
                        " \n\n\n" + t.getMessage());
            }
        });

    }

    //this will be mainly used for search
    private void storeEachArticles(List<Item> listOfItems, Channel channel){
        for(int i = 0; i < listOfItems.size(); i++){
            Item item = channel.getItem().get(i);
            String articleTitle = item.getTitle();
            String articleLink = item.getLink();
            String articleDescription = item.getDescription();
            String articleMedia = item.getEnclosure().getUrl();
            String articlePubDate = item.getPubDate();
            String articleSource = item.getSource();

//            Log.d(TAG, "article Link : " + articleLink + " \n" +
//                    "article Media : " + articleMedia + "\n" +
//                    "article PubDate : " + articlePubDate + "\n");
            mArticleObjArrayList.add(new ArticleObj(articleTitle, articleLink, articleDescription, articleMedia, articlePubDate, articleSource));
        }
    }


    private void storeEachVideos(List<Entry> entries){
        if(entries != null){
            for(Entry entry : entries){
                String videoId = entry.getVideoid();
                String videoChannelId = entry.getChannelId();
                String videoTitle = entry.getTitle();
                String videoAuthor = entry.getAuthor().getName();
                String videoPubDate = entry.getPublished();
                Media media = entry.getMedia();
                YoutubeVideo youtubeVideo = new YoutubeVideo(videoId, videoChannelId, videoTitle, videoAuthor, videoPubDate, media);
                youtubeVideoArrayList.add(youtubeVideo);
            }
            mYoutubeVideoLiveData.setValue(youtubeVideoArrayList);
        }
    }

    private void storeEachTimeArticle(List<com.gahee.rss_v2.retrofitTime.tags.Item> items){
        if(items != null){
            for(com.gahee.rss_v2.retrofitTime.tags.Item item : items){
                String articleTitle = item.getArticleTitle();
                String articlePubDate = item.getPubDate();
                String articleDescription = item.getArticleDesc();
                com.gahee.rss_v2.retrofitTime.tags.Item.Thumbnail thumbnail = item.getThumbnail();
                String articleLink = item.getArticleLink();

                TimeArticle timeArticle = new TimeArticle(articleTitle, articlePubDate, articleDescription, thumbnail, articleLink);
                timeArticleArrayList.add(timeArticle);
            }
            mTimeArticleLiveData.setValue(timeArticleArrayList);
        }
    }


    public MutableLiveData<ArrayList<ArticleObj>> getmArticleMutableLiveData() {
        return mArticleMutableLiveData;
    }

    public MutableLiveData<ArrayList<ChannelObj>> getmChannelMutableLiveData() {
        return mChannelMutableLiveData;
    }

    public MutableLiveData<ArrayList<YoutubeChannel>> getmYoutubeChannelLiveData() {
        return mYoutubeChannelLiveData;
    }

    public MutableLiveData<ArrayList<YoutubeVideo>> getmYoutubeVideoLiveData() {
        return mYoutubeVideoLiveData;
    }

    public MutableLiveData<ArrayList<TimeChannel>> getmTimeChannelLiveData() {
        return mTimeChannelLiveData;
    }

    public MutableLiveData<ArrayList<TimeArticle>> getmTimeArticleLiveData() {
        return mTimeArticleLiveData;
    }

    public void fetchRemoteData(){
        fetchDataFromNasa();
    }

    public void fetchRemoteYoutubeData(){
        fetchDataFromYoutube();
    }

    public void fetchRemoteTimeData(){
        fetchDataFromTime();
    }
}
