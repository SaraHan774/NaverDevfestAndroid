package com.gahee.rss_v2.remoteDataSource;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gahee.rss_v2.retrofitNasa.model.ArticleObj;
import com.gahee.rss_v2.retrofitNasa.model.ChannelObj;
import com.gahee.rss_v2.retrofitTime.model.TimeArticle;
import com.gahee.rss_v2.retrofitTime.model.TimeChannel;
import com.gahee.rss_v2.retrofitYT.model.YoutubeChannel;
import com.gahee.rss_v2.retrofitYT.model.YoutubeVideo;

import java.util.ArrayList;

public class RemoteViewModel extends ViewModel {

    private final RemoteRepository mRemoteRepository;

    public RemoteViewModel(){
        mRemoteRepository = RemoteRepository.getInstance();
    }

    public MutableLiveData<ArrayList<ChannelObj>> getChannelMutableLiveData(){
        return mRemoteRepository.getChannelMutableLiveData();
    }

    public MutableLiveData<ArrayList<ArticleObj>> getArticleMutableLiveData(){
        return mRemoteRepository.getArticleMutableLiveData();
    }

    public MutableLiveData<ArrayList<YoutubeChannel>> getYoutubeChannelLiveData(){
        return mRemoteRepository.getYoutubeChannelLiveData();
    }

    public MutableLiveData<ArrayList<YoutubeVideo>> getYoutubeVideoLiveData(){
        return mRemoteRepository.getYoutubeVideoLiveData();
    }

    public MutableLiveData<ArrayList<TimeChannel>> getTimeChannelLiveData(){
        return mRemoteRepository.getTimeChannelLiveData();
    }

    public MutableLiveData<ArrayList<TimeArticle>> getTimeArticleLiveData(){
        return mRemoteRepository.getTimeArticleLiveData();
    }

    public void fetchNasaDataFromRemote(){
        mRemoteRepository.fetchData();
    }

    public void fetchYouTubeDataFromRemote(){mRemoteRepository.fetchYtData();}

    public void fetchTimeDataFromRemote(){mRemoteRepository.fetchTimeData();}

}
