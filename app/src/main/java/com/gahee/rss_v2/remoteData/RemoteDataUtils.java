package com.gahee.rss_v2.remoteData;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.gahee.rss_v2.retrofitNasa.NasaAPI;
import com.gahee.rss_v2.retrofitNasa.model.ArticleObj;
import com.gahee.rss_v2.retrofitNasa.model.ChannelObj;
import com.gahee.rss_v2.retrofitNasa.tags.Channel;
import com.gahee.rss_v2.retrofitNasa.tags.Item;
import com.gahee.rss_v2.retrofitNasa.tags.Rss;
import com.gahee.rss_v2.retrofitYT.Feed;
import com.gahee.rss_v2.retrofitYT.YoutubeAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataUtils {

    private static final String TAG = "RemoteDataUtils";

    private ArrayList<ChannelObj> mChannelObjArrayList = new ArrayList<>();
    private ArrayList<ArticleObj> mArticleObjArrayList = new ArrayList<>();
    private MutableLiveData<ArrayList<ChannelObj>> mChannelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ArticleObj>> mArticleMutableLiveData = new MutableLiveData<>();

    private static RemoteDataUtils instance;

    public static RemoteDataUtils getInstance() {
        if(instance == null){
            instance = new RemoteDataUtils();
        }
        return instance;
    }


    private void fetchData(){

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

    private void fetchYoutubeData(){
        YoutubeAPI youtubeAPI = RetrofitInstanceBuilder.getYoutubeApi();
        Call<Feed> call = youtubeAPI.getYoutubeChannel();
//        channel_id=UCD_grdLAvD4nqcqck2E-tuw
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                if(response.body() != null){
                    for(int i = 0; i < response.body().getEntries().size() ; i++){
                        Log.d(TAG, "youtube feed title : " + response.body().getTitle() + "\n"
                                + "author : " + response.body().getEntries().get(i).getAuthor().getName() + "\n"
                                + "media : " + response.body().getEntries().get(i).getMedia() + "\n"
                                + "media thumbnail : " + response.body().getEntries().get(i).getMedia().getThumbnail() );
                    }

                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                    Log.d(TAG, "failed to fetch from youtube" + t.getMessage());
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


    public MutableLiveData<ArrayList<ArticleObj>> getmArticleMutableLiveData() {
        return mArticleMutableLiveData;
    }

    public MutableLiveData<ArrayList<ChannelObj>> getmChannelMutableLiveData() {
        return mChannelMutableLiveData;
    }

    public void fetchRemoteData(){
        fetchData();
    }

    public void fetchYTRemoteData(){
        fetchYoutubeData();
    }
}
