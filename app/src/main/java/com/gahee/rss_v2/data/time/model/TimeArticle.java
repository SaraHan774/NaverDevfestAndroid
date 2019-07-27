package com.gahee.rss_v2.data.time.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.gahee.rss_v2.data.time.tags.Item;

import java.util.List;

public class TimeArticle implements Parcelable {

    private String mArticletitle;
    private String mArticlePubDate;
    private String mArticleDescription;
    private Item.Thumbnail mArticleThumbnail;
    private String mContentEncoded;
    private String mArticleLink;
    private List<String> mYoutubeLink;


    public TimeArticle(String mArticletitle, String mArticlePubDate, String mArticleDescription, Item.Thumbnail mArticleThumbnail, String mContentEncoded, String mArticleLink) {
        this.mArticletitle = mArticletitle;
        this.mArticlePubDate = mArticlePubDate;
        this.mArticleDescription = mArticleDescription;
        this.mArticleThumbnail = mArticleThumbnail;
        this.mContentEncoded = mContentEncoded;
        this.mArticleLink = mArticleLink;
    }


    protected TimeArticle(Parcel in) {
        mArticletitle = in.readString();
        mArticlePubDate = in.readString();
        mArticleDescription = in.readString();
        mArticleThumbnail = in.readParcelable(Item.Thumbnail.class.getClassLoader());
        mContentEncoded = in.readString();
        mArticleLink = in.readString();
        mYoutubeLink = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mArticletitle);
        dest.writeString(mArticlePubDate);
        dest.writeString(mArticleDescription);
        dest.writeParcelable(mArticleThumbnail, flags);
        dest.writeString(mContentEncoded);
        dest.writeString(mArticleLink);
        dest.writeStringList(mYoutubeLink);
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

    public String getmContentEncoded() {
        return mContentEncoded;
    }

    public String getmArticleLink() {
        return mArticleLink;
    }

    public List<String> getmYoutubeLink() {
        return mYoutubeLink;
    }

    public void setmYoutubeLink(List<String> mYoutubeLink) {
        this.mYoutubeLink = mYoutubeLink;
    }
}
