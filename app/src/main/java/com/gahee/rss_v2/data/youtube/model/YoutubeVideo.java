package com.gahee.rss_v2.data.youtube.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.gahee.rss_v2.data.youtube.tags.Media;

public class YoutubeVideo implements Parcelable {

    private String mVideoId;
    private String mVideoChannelId;
    private String mVideoTitle;
    private String mVideoAuthorName;
    private String mVideoPubDate;
    private Media mVideoMedia;

    public YoutubeVideo(String mVideoId, String mVideoChannelId, String mVideoTitle, String mVideoAuthorName, String mVideoPubDate, Media mVideoMedia) {
        this.mVideoId = mVideoId;
        this.mVideoChannelId = mVideoChannelId;
        this.mVideoTitle = mVideoTitle;
        this.mVideoAuthorName = mVideoAuthorName;
        this.mVideoPubDate = mVideoPubDate;
        this.mVideoMedia = mVideoMedia;
    }

    protected YoutubeVideo(Parcel in) {
        mVideoId = in.readString();
        mVideoChannelId = in.readString();
        mVideoTitle = in.readString();
        mVideoAuthorName = in.readString();
        mVideoPubDate = in.readString();
        mVideoMedia = in.readParcelable(Media.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mVideoId);
        dest.writeString(mVideoChannelId);
        dest.writeString(mVideoTitle);
        dest.writeString(mVideoAuthorName);
        dest.writeString(mVideoPubDate);
        dest.writeParcelable(mVideoMedia, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<YoutubeVideo> CREATOR = new Creator<YoutubeVideo>() {
        @Override
        public YoutubeVideo createFromParcel(Parcel in) {
            return new YoutubeVideo(in);
        }

        @Override
        public YoutubeVideo[] newArray(int size) {
            return new YoutubeVideo[size];
        }
    };

    public String getmVideoId() {
        return mVideoId;
    }

    public String getmVideoChannelId() {
        return mVideoChannelId;
    }

    public String getmVideoTitle() {
        return mVideoTitle;
    }

    public String getmVideoAuthorName() {
        return mVideoAuthorName;
    }

    public String getmVideoPubDate() {
        return mVideoPubDate;
    }

    public Media getmVideoMedia() {
        return mVideoMedia;
    }
}
