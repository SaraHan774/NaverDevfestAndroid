package com.gahee.rss_v2.data.reuters.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.gahee.rss_v2.data.reuters.tags.Item;

import java.util.ArrayList;
import java.util.List;

public class ChannelObj implements Parcelable {

    private final String mChannelTitle;
    private final String mChannelDescription;
    private final String mChannelLink;
    private final ArrayList<Item> mItemList;

    public ChannelObj(String mChannelTitle, String mChannelDescription, String mChannelLink, ArrayList<Item> mItemList) {
        this.mChannelTitle = mChannelTitle;
        this.mChannelDescription = mChannelDescription;
        this.mChannelLink = mChannelLink;
        this.mItemList = mItemList;
    }

    protected ChannelObj(Parcel in) {
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

    public static final Creator<ChannelObj> CREATOR = new Creator<ChannelObj>() {
        @Override
        public ChannelObj createFromParcel(Parcel in) {
            return new ChannelObj(in);
        }

        @Override
        public ChannelObj[] newArray(int size) {
            return new ChannelObj[size];
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
