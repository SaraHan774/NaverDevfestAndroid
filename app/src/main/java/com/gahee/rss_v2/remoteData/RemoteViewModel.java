package com.gahee.rss_v2.remoteData;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gahee.rss_v2.retrofitNasa.model.ArticleObj;
import com.gahee.rss_v2.retrofitNasa.model.ChannelObj;

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

    public void fetchDataFromRemote(){
        mRemoteRepository.fetchData();
    }

    public void fetchYTDataFromRemote(){mRemoteRepository.fetchYtData();}


}
