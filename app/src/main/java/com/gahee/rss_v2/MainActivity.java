package com.gahee.rss_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.gahee.rss_v2.retrofit.NasaAPI;
import com.gahee.rss_v2.retrofit.RetrofitInstanceBuilder;
import com.gahee.rss_v2.retrofit.model.ArticleObj;
import com.gahee.rss_v2.retrofit.model.ChannelObj;
import com.gahee.rss_v2.retrofit.tags.Channel;
import com.gahee.rss_v2.retrofit.tags.Image;
import com.gahee.rss_v2.retrofit.tags.Item;
import com.gahee.rss_v2.retrofit.tags.Rss;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<ChannelObj> mChannelObjArrayList = new ArrayList<>();
    private ArrayList<ArticleObj> mArticleObjArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

                    mChannelObjArrayList.add(new ChannelObj(title, description, link, listOfItems, imageUrl));
                    for(int i = 0; i < listOfItems.size(); i++){
                        Item item = channel.getItem().get(i);
                        String articleTitle = item.getTitle();
                        String articleLink = item.getLink();
                        String articleDescription = item.getDescription();
                        String articleMedia = item.getEnclosure().getUrl();
                        String articlePubDate = item.getPubDate();
                        String articleSource = item.getSource();

                        Log.d(TAG, "article Link : " + articleLink + " \n" +
                                "article Media : " + articleMedia + "\n" +
                                "article PubDate : " + articlePubDate + "\n");
                        mArticleObjArrayList.add(new ArticleObj(articleTitle, articleLink, articleDescription, articleMedia, articlePubDate, articleSource));
                    }
                }
            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {

                Log.d(TAG, "failed to fetch data :  " + t.getMessage());

            }
        });


    }


}
