package com.gahee.rss_v2.myFeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.gahee.rss_v2.R;
import com.gahee.rss_v2.databinding.ViewHolderFeedBinding;
import com.gahee.rss_v2.retrofitNasa.model.ChannelObj;

import java.util.ArrayList;

public class FeedRvAdapter extends RecyclerView.Adapter<FeedRvAdapter.FeedViewHolder>{

    private static final String TAG = "FeedRvAdapter";

    private Context mContext;
    private ArrayList<ChannelObj> mChannelObjs;


    public FeedRvAdapter(Context mContext, ArrayList<ChannelObj> channelObjs){
        this.mContext = mContext;
        mChannelObjs = channelObjs;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderFeedBinding viewHolderFeedBinding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext), R.layout.view_holder_feed, parent, false
        );
        return new FeedViewHolder(viewHolderFeedBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {

        ChannelObj channelObj = mChannelObjs.get(position);

        holder.bindChannelTitle(channelObj.getmChannelTitle());

        PagerAdapter pagerAdapter = new FeedPagerAdapter(mContext, channelObj);
        holder.bindViewPager(pagerAdapter);
    }

    @Override
    public int getItemCount() {
        return mChannelObjs.size();
    }

    class FeedViewHolder extends RecyclerView.ViewHolder{

        private final ViewHolderFeedBinding viewHolderFeedBinding;

        public FeedViewHolder(ViewHolderFeedBinding viewHolderFeedBinding) {
            super(viewHolderFeedBinding.getRoot());
            this.viewHolderFeedBinding = viewHolderFeedBinding;
        }

        public void bindChannelTitle(String string){
            viewHolderFeedBinding.tvViewHolderSample.setText(string);
        }

        public void bindViewPager(PagerAdapter pagerAdapter){
            viewHolderFeedBinding.setPagerAdapter(pagerAdapter);
        }
    }
}
