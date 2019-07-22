package com.gahee.rss_v2.data.youtube.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.gahee.rss_v2.data.youtube.tags.Entry;

import java.util.List;

public class YoutubeChannel implements Parcelable {

    private String channelId;
    private String channelTitle;
    private List<Entry> entries;

    public YoutubeChannel(String channelId, String channelTitle, List<Entry> entries) {
        this.channelId = channelId;
        this.channelTitle = channelTitle;
        this.entries = entries;
    }

    protected YoutubeChannel(Parcel in) {
        channelId = in.readString();
        channelTitle = in.readString();
        entries = in.createTypedArrayList(Entry.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(channelId);
        dest.writeString(channelTitle);
        dest.writeTypedList(entries);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<YoutubeChannel> CREATOR = new Creator<YoutubeChannel>() {
        @Override
        public YoutubeChannel createFromParcel(Parcel in) {
            return new YoutubeChannel(in);
        }

        @Override
        public YoutubeChannel[] newArray(int size) {
            return new YoutubeChannel[size];
        }
    };

    public String getChannelId() {
        return channelId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public List<Entry> getEntries() {
        return entries;
    }
}
