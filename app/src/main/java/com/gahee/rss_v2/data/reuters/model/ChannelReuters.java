package com.gahee.rss_v2.data.reuters.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.gahee.rss_v2.data.reuters.tags.Item;

import java.util.ArrayList;

public class ChannelReuters implements Parcelable {

    private final String mChannelTitle;
    private final String mChannelDescription;
    private final String mChannelLink;
    private final ArrayList<Item> mItemList;

    public ChannelReuters(String mChannelTitle, String mChannelDescription, String mChannelLink, ArrayList<Item> mItemList) {
        this.mChannelTitle = mChannelTitle;
        this.mChannelDescription = mChannelDescription;
        this.mChannelLink = mChannelLink;
        this.mItemList = mItemList;
    }

    private ChannelReuters(Parcel in) {
        mChannelTitle = in.readString();
        mChannelDescription = in.readString();
        mChannelLink = in.readString();
        mItemList = in.createTypedArrayList(Item.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mChannelTitle);
        dest.writeString(mChannelDescription);
        dest.writeString(mChannelLink);
        dest.writeTypedList(mItemList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChannelReuters> CREATOR = new Creator<ChannelReuters>() {
        @Override
        public ChannelReuters createFromParcel(Parcel in) {
            return new ChannelReuters(in);
        }

        @Override
        public ChannelReuters[] newArray(int size) {
            return new ChannelReuters[size];
        }
    };

    public String getmChannelTitle() {
        return mChannelTitle;
    }

    public String getmChannelDescription() {
        return mChannelDescription;
    }

    public String getmChannelLink() {
        return mChannelLink;
    }

    public ArrayList<Item> getmItemList() {
        return mItemList;
    }



}
