package com.gahee.rss_v2.retrofitTime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.gahee.rss_v2.retrofitTime.tags.Item;

public class TimeArticle implements Parcelable {

    private String mArticletitle;
    private String mArticlePubDate;
    private String mArticleDescription;
    private Item.Thumbnail mArticleThumbnail;
    private String mArticleLink;

    public TimeArticle(String mArticletitle, String mArticlePubDate, String mArticleDescription, Item.Thumbnail mArticleThumbnail, String mArticleLink) {
        this.mArticletitle = mArticletitle;
        this.mArticlePubDate = mArticlePubDate;
        this.mArticleDescription = mArticleDescription;
        this.mArticleThumbnail = mArticleThumbnail;
        this.mArticleLink = mArticleLink;
    }

    public String getmArticletitle() {
        return mArticletitle;
    }

    public String getmArticlePubDate() {
        return mArticlePubDate;
    }

    public String getmArticleDescription() {
        return mArticleDescription;
    }

    public Item.Thumbnail getmArticleThumbnail() {
        return mArticleThumbnail;
    }

    public String getmArticleLink() {
        return mArticleLink;
    }

    protected TimeArticle(Parcel in) {
        mArticletitle = in.readString();
        mArticlePubDate = in.readString();
        mArticleDescription = in.readString();
        mArticleThumbnail = in.readParcelable(Item.Thumbnail.class.getClassLoader());
        mArticleLink = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mArticletitle);
        dest.writeString(mArticlePubDate);
        dest.writeString(mArticleDescription);
        dest.writeParcelable(mArticleThumbnail, flags);
        dest.writeString(mArticleLink);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TimeArticle> CREATOR = new Creator<TimeArticle>() {
        @Override
        public TimeArticle createFromParcel(Parcel in) {
            return new TimeArticle(in);
        }

        @Override
        public TimeArticle[] newArray(int size) {
            return new TimeArticle[size];
        }
    };
}
