package com.gahee.rss_v2.retrofitTime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.gahee.rss_v2.retrofitTime.tags.Item;

import java.util.List;

public class TimeChannel implements Parcelable {
    private String mChannelTitle;
    private String mChannelDescription;
    private List<Item> mChannelItems;

    public TimeChannel(String mChannelTitle, String mChannelDescription, List<Item> mChannelItems) {
        this.mChannelTitle = mChannelTitle;
        this.mChannelDescription = mChannelDescription;
        this.mChannelItems = mChannelItems;
    }

    public String getmChannelTitle() {
        return mChannelTitle;
    }

    public String getmChannelDescription() {
        return mChannelDescription;
    }

    public List<Item> getmChannelItems() {
        return mChannelItems;
    }

    protected TimeChannel(Parcel in) {
        mChannelTitle = in.readString();
        mChannelDescription = in.readString();
        mChannelItems = in.createTypedArrayList(Item.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mChannelTitle);
        dest.writeString(mChannelDescription);
        dest.writeTypedList(mChannelItems);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TimeChannel> CREATOR = new Creator<TimeChannel>() {
        @Override
        public TimeChannel createFromParcel(Parcel in) {
            return new TimeChannel(in);
        }

        @Override
        public TimeChannel[] newArray(int size) {
            return new TimeChannel[size];
        }
    };
}
