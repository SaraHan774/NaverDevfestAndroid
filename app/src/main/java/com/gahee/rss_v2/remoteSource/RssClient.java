package com.gahee.rss_v2.remoteSource;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.android.MediaManager;
import com.cloudinary.utils.ObjectUtils;
import com.gahee.rss_v2.ParsingUtils;
import com.gahee.rss_v2.data.reuters.ReutersAPI;
import com.gahee.rss_v2.data.reuters.model.ArticleObj;
import com.gahee.rss_v2.data.reuters.model.ChannelObj;
import com.gahee.rss_v2.data.reuters.tags.Channel;
import com.gahee.rss_v2.data.reuters.tags.Item;
import com.gahee.rss_v2.data.reuters.tags.Rss;
import com.gahee.rss_v2.data.time.TimeAPI;
import com.gahee.rss_v2.data.time.model.TimeArticle;
import com.gahee.rss_v2.data.time.model.TimeChannel;
import com.gahee.rss_v2.data.wwf.WwfAPI;
import com.gahee.rss_v2.data.wwf.model.WWFArticle;
import com.gahee.rss_v2.data.wwf.model.WWFChannel;
import com.gahee.rss_v2.data.youtube.model.YoutubeChannel;
import com.gahee.rss_v2.data.youtube.model.YoutubeVideo;
import com.gahee.rss_v2.data.youtube.tags.Entry;
import com.gahee.rss_v2.data.youtube.tags.Feed;
import com.gahee.rss_v2.data.youtube.YoutubeAPI;
import com.gahee.rss_v2.data.youtube.tags.Media;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RssClient {

    private static final String TAG = "RssClient";


    private MutableLiveData<ArrayList<ChannelObj>> mChannelMutableLiveData;
    private MutableLiveData<ArrayList<ArticleObj>> mArticleMutableLiveData;

    private MutableLiveData<ArrayList<YoutubeChannel>> mYoutubeChannelLiveData;
    private MutableLiveData<ArrayList<YoutubeVideo>> mYoutubeVideoLiveData;

    private MutableLiveData<ArrayList<TimeChannel>> mTimeChannelLiveData;
    private MutableLiveData<ArrayList<TimeArticle>> mTimeArticleLiveData;

    private MutableLiveData<ArrayList<WWFChannel>> mWwfChannelLiveData;
    private MutableLiveData<ArrayList<WWFArticle>> mWwfArticleLiveData;


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

        mYoutubeChannelLiveData = new MutableLiveData<>();
        mYoutubeVideoLiveData = new MutableLiveData<>();

        mTimeChannelLiveData = new MutableLiveData<>();
        mTimeArticleLiveData = new MutableLiveData<>();

        mWwfArticleLiveData = new MutableLiveData<>();
        mWwfChannelLiveData = new MutableLiveData<>();

    }


    private ArrayList<ChannelObj> mChannelObjArrayList = new ArrayList<>();
    private ArrayList<ArticleObj> mArticleObjArrayList = new ArrayList<>();

    private void fetchDataFromReuters(){
        ReutersAPI reutersAPI = RetrofitInstanceBuilder.getReutersAPI();
        Call<Rss> call = reutersAPI.getUSVideoTopNews();

        call.enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                if(response.body() != null && response.body().getChannel() != null){
                    Channel channel = response.body().getChannel();
                    String title = channel.getChannelTitle();
                    String description = channel.getChannelDescription();
                    String link = channel.getChannelLink();
                    ArrayList<Item> listOfItems = channel.getItem();


                    Log.d(TAG, "channel : " + channel + "\n" + "title : " + title + "\n" + "description : " + description);

                    ChannelObj channelObj = new ChannelObj(title, description, link, listOfItems);
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
        Call<com.gahee.rss_v2.data.time.tags.Rss> call = timeAPI.getTimeEntertainment();
        call.enqueue(new Callback<com.gahee.rss_v2.data.time.tags.Rss>() {

            @Override
            public void onResponse(Call<com.gahee.rss_v2.data.time.tags.Rss> call, Response<com.gahee.rss_v2.data.time.tags.Rss> response) {
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
            public void onFailure(Call<com.gahee.rss_v2.data.time.tags.Rss> call, Throwable t) {
                Log.d(TAG, "failed to fetch from time : " + "\n\n" +
                        t.getStackTrace() +
                        " \n\n" + t.getMessage());
            }
        });

    }

    private ArrayList<WWFChannel> wwfChannelArrayList = new ArrayList<>();
    private ArrayList<WWFArticle> wwfArticleArrayList = new ArrayList<>();
    private void fetchDataFromWwf(){

        WwfAPI wwfAPI = RetrofitInstanceBuilder.getWwfAPI();
        Call<com.gahee.rss_v2.data.wwf.tags.Rss> call = wwfAPI.getWWFStories();
        call.enqueue(new Callback<com.gahee.rss_v2.data.wwf.tags.Rss>() {
            @Override
            public void onResponse(Call<com.gahee.rss_v2.data.wwf.tags.Rss> call, Response<com.gahee.rss_v2.data.wwf.tags.Rss> response) {
//                Log.d(TAG, "response body : " + response.body().getChannel().getItems().get(3).getContentEncoded());
                String title = response.body().getChannel().getTitle();
                String link = response.body().getChannel().getGuid();
                String description = response.body().getChannel().getDescription();
                List<com.gahee.rss_v2.data.wwf.tags.Item> items = response.body().getChannel().getItems();
                wwfChannelArrayList.add(new WWFChannel(title, link, description, items));
                storeEachWWFArticles(items);
                mWwfChannelLiveData.setValue(wwfChannelArrayList);
            }

            @Override
            public void onFailure(Call<com.gahee.rss_v2.data.wwf.tags.Rss> call, Throwable t) {
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

            mArticleObjArrayList.add(new ArticleObj(articleTitle, articleLink, articleDescription, articlePubDate, videoLink, thumbnailLink));
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

    private void storeEachTimeArticle(List<com.gahee.rss_v2.data.time.tags.Item> items){

        if(items != null){
            for(com.gahee.rss_v2.data.time.tags.Item item : items){
                String articleTitle = item.getArticleTitle();
                String articlePubDate = item.getPubDate();

                String articleDescription = item.getArticleDesc();
                com.gahee.rss_v2.data.time.tags.Item.Thumbnail thumbnail = item.getThumbnail();
                String contentEncoded = item.getContentEncoded();
                String articleLink = item.getArticleLink();

//                Log.d(TAG,  articleTitle + "\n" + articlePubDate +"\n" + articleDescription + "\n" + thumbnail
//                + "\n" + contentEncoded + articleLink);

                TimeArticle timeArticle = new TimeArticle(articleTitle, articlePubDate, articleDescription, thumbnail, contentEncoded,articleLink);
                ParsingUtils.timeGetYoutubeLinksFromArticle(item, timeArticle);
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
                String cleanDescription = ParsingUtils.removeHtmlTagsFromString(description);

                String contentEncoded = item.getContentEncoded();
                WWFArticle wwfArticle = new WWFArticle(title, link, pubdate, cleanDescription, contentEncoded);


                //extract image assets from the article and set the string list value using a setter
                ParsingUtils.wwfExtractImageTags(wwfArticle, item);

                //add article object to the arrayList
                wwfArticleArrayList.add(wwfArticle);
            }
            mWwfArticleLiveData.setValue(wwfArticleArrayList);
        }
    }

//    private void uploadVideosToCloudinary(Context context, String mp4Link, String videoTitle){
//        Map config = new HashMap();
//        config.put("cloud_name", "drsh35cxq");
//        config.put("api_key", "572891948791234");
//        config.put("api_secret", "07xSHzIVSi40iWYjIPWn2xhiVAU");
//
//        MediaManager.init(context, config);
//
////        String sample2 = "https://vm.reuters.tv/39a3c/qoudodaik11-1404k.mp4";
//
//        Cloudinary cloudinary = new Cloudinary(config);
//        try {
//            cloudinary.uploader().uploadLarge(mp4Link, ObjectUtils.asMap("resource_type", "video"));
//            MediaManager.get().url()
//                    .transformation(new Transformation().width(250).flags("animated").fetchFormat("auto").crop("scale"))
//                    .resourceType("video").generate("dog.gif");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Log.d(TAG, "cloudinary : " + cloudinary);
//    }
//
//    private class CloudAsync extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... voids) {
////            uploadVideosToCloudinary();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            Log.d(TAG, "upload finished to cloudinary");
//        }
//    }



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

    public MutableLiveData<ArrayList<WWFArticle>> getmWwfArticleLiveData() {
        return mWwfArticleLiveData;
    }

    public MutableLiveData<ArrayList<WWFChannel>> getmWwfChannelLiveData() {
        return mWwfChannelLiveData;
    }

    public void fetchRemoteReutersData(){
        fetchDataFromReuters();
    }

    public void fetchRemoteYoutubeData(){
        fetchDataFromYoutube();
    }

    public void fetchRemoteTimeData(){
        fetchDataFromTime();
    }

    public void fetchRemoteWWFData(){ fetchDataFromWwf(); }
}
