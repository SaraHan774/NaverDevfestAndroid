package com.gahee.rss_v2.retrofit;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gahee.rss_v2.retrofit.model.ArticleObj;
import com.gahee.rss_v2.retrofit.model.ChannelObj;

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


}
