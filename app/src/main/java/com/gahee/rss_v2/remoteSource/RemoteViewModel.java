package com.gahee.rss_v2.remoteSource;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gahee.rss_v2.data.reuters.model.ArticleReuters;
import com.gahee.rss_v2.data.reuters.model.ChannelReuters;
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

    public MutableLiveData<ArrayList<ChannelReuters>> getChannelMutableLiveData(){
        return mRemoteRepository.getReutersChannelMutableLiveData();
    }

    public MutableLiveData<ArrayList<ArticleReuters>> getArticleMutableLiveData(){
        return mRemoteRepository.getReutersArticleMutableLiveData();
    }

    public MutableLiveData<ArrayList<TimeChannel>> getTimeChannelMutableLiveData(){
        return mRemoteRepository.getTimeChannelLiveData();
    }

    public MutableLiveData<ArrayList<TimeArticle>> getTimeArticleMutableLiveData(){
        return mRemoteRepository.getTimeArticleLiveData();
    }

    public MutableLiveData<ArrayList<WWFArticle>> getWwfArticleMutableLiveData(){
        return mRemoteRepository.getWwfArticleLiveData();
    }

    public MutableLiveData<ArrayList<WWFChannel>> getWwfChannelMutableLiveData(){
        return mRemoteRepository.getWwfChannelLiveData();
    }

    public void fetchReutersDataFromRepo(){
        mRemoteRepository.fetchReutersData();
    }

    public void fetchTimeDataFromRepo(){mRemoteRepository.fetchTimeData();}

    public void fetchWWFDataFromRepo(){mRemoteRepository.fetchWWFData();}

    public void cancelAsyncReuters(){mRemoteRepository.cancelReutersAsync();}

    public void cancelAsyncWWF(){mRemoteRepository.cancelWWFAsync();}

    public void cancelAsyncTIME(){mRemoteRepository.cancelTIMEAsync();}

}
