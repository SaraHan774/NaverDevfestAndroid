package com.gahee.rss_v2.remoteSource;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gahee.rss_v2.data.reuters.model.ArticleObj;
import com.gahee.rss_v2.data.reuters.model.ChannelObj;
import com.gahee.rss_v2.data.time.model.TimeArticle;
import com.gahee.rss_v2.data.time.model.TimeChannel;
import com.gahee.rss_v2.data.wwf.model.WWFArticle;
import com.gahee.rss_v2.data.wwf.model.WWFChannel;

import java.util.ArrayList;

public class RemoteViewModel extends ViewModel {

    private final RemoteRepository mRemoteRepository;

    public RemoteViewModel(){
        mRemoteRepository = RemoteRepository.getInstance();
    }

    public MutableLiveData<ArrayList<ChannelObj>> getChannelMutableLiveData(){
        return mRemoteRepository.getReutersChannelMutableLiveData();
    }

    public MutableLiveData<ArrayList<ArticleObj>> getArticleMutableLiveData(){
        return mRemoteRepository.getReutersArticleMutableLiveData();
    }

    public MutableLiveData<ArrayList<TimeChannel>> getTimeChannelLiveData(){
        return mRemoteRepository.getTimeChannelLiveData();
    }

    public MutableLiveData<ArrayList<TimeArticle>> getTimeArticleLiveData(){
        return mRemoteRepository.getTimeArticleLiveData();
    }

    public MutableLiveData<ArrayList<WWFArticle>> getWwfArticleLiveData(){
        return mRemoteRepository.getWwfArticleLiveData();
    }

    public MutableLiveData<ArrayList<WWFChannel>> getWwfChannelLiveData(){
        return mRemoteRepository.getWwfChannelLiveData();
    }

    public void fetchReutersDataFromRepo(){
        mRemoteRepository.fetchReutersData();
    }

    public void fetchTimeDataFromRepo(){mRemoteRepository.fetchTimeData();}

    public void fetchWWFDataFromRepo(){mRemoteRepository.fetchWWFData();}

}
