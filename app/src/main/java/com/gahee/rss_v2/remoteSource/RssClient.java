package com.gahee.rss_v2.remoteSource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.gahee.rss_v2.data.time.tags.Content;
import com.gahee.rss_v2.utils.StringUtils;
import com.gahee.rss_v2.data.reuters.ReutersAPI;
import com.gahee.rss_v2.data.reuters.model.ArticleReuters;
import com.gahee.rss_v2.data.reuters.model.ChannelReuters;
import com.gahee.rss_v2.data.reuters.tags.Channel;
import com.gahee.rss_v2.data.reuters.tags.Item;
import com.gahee.rss_v2.data.reuters.tags.Rss;
import com.gahee.rss_v2.data.time.TimeAPI;
import com.gahee.rss_v2.data.time.model.TimeArticle;
import com.gahee.rss_v2.data.time.model.TimeChannel;
import com.gahee.rss_v2.data.wwf.WwfAPI;
import com.gahee.rss_v2.data.wwf.model.WWFArticle;
import com.gahee.rss_v2.data.wwf.model.WWFChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RssClient{

    private static final String TAG = "RssClient";


    private final MutableLiveData<ArrayList<ChannelReuters>> mChannelMutableLiveData;
    private final MutableLiveData<ArrayList<ArticleReuters>> mArticleMutableLiveData;


    private final MutableLiveData<ArrayList<TimeChannel>> mTimeChannelLiveData;
    private final MutableLiveData<ArrayList<TimeArticle>> mTimeArticleLiveData;

    private final MutableLiveData<ArrayList<WWFChannel>> mWwfChannelLiveData;
    private final MutableLiveData<ArrayList<WWFArticle>> mWwfArticleLiveData;


    private static RssClient instance;

    public static RssClient getInstance() {
        if(instance == null){
            instance = new RssClient();
        }
        return instance;
    }

    private RssClient(){

        mChannelMutableLiveData = new MutableLiveData<>();
        mArticleMutableLiveData = new MutableLiveData<>();

        mTimeChannelLiveData = new MutableLiveData<>();
        mTimeArticleLiveData = new MutableLiveData<>();

        mWwfArticleLiveData = new MutableLiveData<>();
        mWwfChannelLiveData = new MutableLiveData<>();

    }


    private final ArrayList<ChannelReuters> mChannelReutersArrayList = new ArrayList<>();
    private final ArrayList<ArticleReuters> mArticleReutersArrayList = new ArrayList<>();

    private void fetchDataFromReuters(){
        ReutersAPI reutersAPI = RetrofitInstanceBuilder.getReutersAPI();
        Call<Rss> call = reutersAPI.getUSVideoTopNews();

        call.enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(@NonNull Call<Rss> call,@NonNull Response<Rss> response) {
                if(response.body() != null && response.body().getChannel() != null){
                    Channel channel = response.body().getChannel();
                    String title = channel.getChannelTitle();
                    String description = channel.getChannelDescription();
                    String link = channel.getChannelLink();
                    ArrayList<Item> listOfItems = channel.getItem();


                    Log.d(TAG, "channel : " + channel + "\n" + "title : " + title + "\n" + "description : " + description);

                    ChannelReuters channelReuters = new ChannelReuters(title, description, link, listOfItems);
                    mChannelReutersArrayList.add(channelReuters);

                    //loop through the list of items and store all the articles in one array
                    storeEachArticles(listOfItems, channel);
                }
                mChannelMutableLiveData.setValue(mChannelReutersArrayList);
//                mArticleMutableLiveData.setValue(mArticleReutersArrayList);
            }

            @Override
            public void onFailure(@NonNull Call<Rss> call, @NonNull Throwable t) {

                Log.d(TAG, "failed to fetch data :  " + t.getMessage());

            }
        });
    }




    private final ArrayList<TimeChannel> timeChannelArrayList = new ArrayList<>();
    private final ArrayList<TimeArticle> timeArticleArrayList = new ArrayList<>();

    private void fetchDataFromTime(){

        TimeAPI timeAPI = RetrofitInstanceBuilder.getTimeApi();
        Call<com.gahee.rss_v2.data.time.tags.Rss> call = timeAPI.getTimeEntertainment();
        call.enqueue(new Callback<com.gahee.rss_v2.data.time.tags.Rss>() {

            @Override
            public void onResponse(@NonNull Call<com.gahee.rss_v2.data.time.tags.Rss> call, @NonNull Response<com.gahee.rss_v2.data.time.tags.Rss> response) {
                if(response.body() != null){
                    String channelTitle = response.body().getChannel().getTitle();
                    String channelDescription = response.body().getChannel().getDescription();
                    List<com.gahee.rss_v2.data.time.tags.Item> items = response.body().getChannel().getItems();

                    TimeChannel timeChannel = new TimeChannel(channelTitle, channelDescription, items);
                    timeChannelArrayList.add(timeChannel);
                    mTimeChannelLiveData.setValue(timeChannelArrayList);
                    storeEachTimeArticle(items);
                }
            }

            @Override
            public void onFailure(@NonNull Call<com.gahee.rss_v2.data.time.tags.Rss> call, @NonNull Throwable t) {
                Log.d(TAG, "failed to fetch from time : " + "\n\n" +
                        Arrays.toString(t.getStackTrace()) +
                        " \n\n" + t.getMessage());
            }
        });

    }

    private final ArrayList<WWFChannel> wwfChannelArrayList = new ArrayList<>();
    private final ArrayList<WWFArticle> wwfArticleArrayList = new ArrayList<>();
    private void fetchDataFromWwf(){

        WwfAPI wwfAPI = RetrofitInstanceBuilder.getWwfAPI();
        Call<com.gahee.rss_v2.data.wwf.tags.Rss> call = wwfAPI.getWWFStories();
        call.enqueue(new Callback<com.gahee.rss_v2.data.wwf.tags.Rss>() {
            @Override
            public void onResponse(@NonNull Call<com.gahee.rss_v2.data.wwf.tags.Rss> call, @NonNull Response<com.gahee.rss_v2.data.wwf.tags.Rss> response) {
//                Log.d(TAG, "response body : " + response.body().getChannel().getItems().get(3).getContentEncoded());
                String title = response.body() != null ? response.body().getChannel().getTitle() : null;
                String link = response.body() != null ? response.body().getChannel().getGuid() : null;
                String description = response.body().getChannel().getDescription();
                List<com.gahee.rss_v2.data.wwf.tags.Item> items = response.body().getChannel().getItems();
                wwfChannelArrayList.add(new WWFChannel(title, link, description, items));
                storeEachWWFArticles(items);
                mWwfChannelLiveData.setValue(wwfChannelArrayList);
                Log.d(TAG, "onResponse: WWF " + response.body().getChannel().getTitle()
                 + " " + response.body().getChannel().getDescription());
            }

            @Override
            public void onFailure(@NonNull Call<com.gahee.rss_v2.data.wwf.tags.Rss> call, @NonNull Throwable t) {
                Log.d(TAG, "failed to fetch data from wwf : " + t.getMessage());
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
            String articlePubDate = item.getPubDate();
            String videoLink;
            if(item.getGroup() != null && item.getGroup().getContent() != null){
                videoLink = item.getGroup().getContent().getUrlVideo();
            }else{
                videoLink = "";
            }
            String thumbnailLink;
            if(item.getGroup() != null && item.getGroup().getContent().getThumbnail() != null){
                thumbnailLink = item.getGroup().getContent().getThumbnail().getUrlThumbnail();
            }else{
                thumbnailLink = "";
            }

//            Log.d(TAG, "article Link : " + articleLink + " \n" +
//                    "article video : " + videoLink+ "\n" +
//                    "thumbnail : " + thumbnailLink + "\n" +
//                    "article PubDate : " + articlePubDate + "\n");

            mArticleReutersArrayList.add(new ArticleReuters(articleTitle, articleLink, articleDescription, articlePubDate, videoLink, thumbnailLink));
        }
        mArticleMutableLiveData.setValue(mArticleReutersArrayList);
    }

    private void storeEachTimeArticle(List<com.gahee.rss_v2.data.time.tags.Item> items){

        if(items != null){
            for(int i = 0; i < items.size(); i++){
                com.gahee.rss_v2.data.time.tags.Item item = items.get(i);
                String articleTitle = item.getArticleTitle();
                String articlePubDate = item.getPubDate();

                String articleDescription = item.getArticleDesc();
                com.gahee.rss_v2.data.time.tags.Item.Thumbnail thumbnail = item.getThumbnail();
                String contentEncoded = item.getContentEncoded();
                String articleLink = item.getArticleLink();
                List<Content> content = item.getContent();

                TimeArticle timeArticle = new TimeArticle(articleTitle, articlePubDate, articleDescription, thumbnail, content,contentEncoded,articleLink);
                StringUtils.extractYoutubeIdFromArticle(item, timeArticle);

                timeArticleArrayList.add(timeArticle);
            }
            mTimeArticleLiveData.setValue(timeArticleArrayList);
        }
    }

    private void storeEachWWFArticles(List<com.gahee.rss_v2.data.wwf.tags.Item> items){
        if(items != null){
            for(com.gahee.rss_v2.data.wwf.tags.Item item : items){
                String title = item.getTitle();
                String link = item.getGuid();
                String pubdate = item.getPubDate();

                String description = item.getDescription();
                String cleanDescription = StringUtils.removeHtmlTagsFromString(description);

                String contentEncoded = item.getContentEncoded();
                WWFArticle wwfArticle = new WWFArticle(title, link, pubdate, cleanDescription, contentEncoded);


                //extract image assets from the article and set the string list value using a setter
                StringUtils.wwfExtractImageData(wwfArticle, item);

                //add article object to the arrayList
                wwfArticleArrayList.add(wwfArticle);
            }
            mWwfArticleLiveData.setValue(wwfArticleArrayList);
        }
    }




    public MutableLiveData<ArrayList<ArticleReuters>> getmArticleMutableLiveData() {
        return mArticleMutableLiveData;
    }

    public MutableLiveData<ArrayList<ChannelReuters>> getmChannelMutableLiveData() {
        return mChannelMutableLiveData;
    }


    public MutableLiveData<ArrayList<TimeChannel>> getmTimeChannelLiveData() {
        return mTimeChannelLiveData;
    }

    public MutableLiveData<ArrayList<TimeArticle>> getmTimeArticleLiveData() {
        return mTimeArticleLiveData;
    }

    public MutableLiveData<ArrayList<WWFArticle>> getmWwfArticleLiveData() {
        return mWwfArticleLiveData;
    }

    public MutableLiveData<ArrayList<WWFChannel>> getmWwfChannelLiveData() {
        return mWwfChannelLiveData;
    }



    public void fetchRemoteReutersData(){
        fetchDataFromReuters();
    }

    public void fetchRemoteTimeData(){
        fetchDataFromTime();
    }

    public void fetchRemoteWWFData(){ fetchDataFromWwf(); }
}
