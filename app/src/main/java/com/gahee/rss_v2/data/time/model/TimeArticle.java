package com.gahee.rss_v2.data.time.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.gahee.rss_v2.data.time.tags.Content;
import com.gahee.rss_v2.data.time.tags.Item;

import java.util.List;

public class TimeArticle implements Parcelable {

    private final String mArticletitle;
    private final String mArticlePubDate;
    private final String mArticleDescription;
    private final Item.Thumbnail mArticleThumbnail;
    private final List<Content> content;
    private final String mContentEncoded;
    private final String mArticleLink;

    private List<String> mYoutubeLinkIds;
    private List<String> mYoutubeThumbnailLink;


    public TimeArticle(String mArticletitle, String mArticlePubDate, String mArticleDescription, Item.Thumbnail mArticleThumbnail,List<Content> content,String mContentEncoded, String mArticleLink) {
        this.mArticletitle = mArticletitle;
        this.mArticlePubDate = mArticlePubDate;
        this.mArticleDescription = mArticleDescription;
        this.mArticleThumbnail = mArticleThumbnail;
        this.content = content;
        this.mContentEncoded = mContentEncoded;
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

    public List<Content> getContent() {
        return content;
    }

    public String getmContentEncoded() {
        return mContentEncoded;
    }

    public String getmArticleLink() {
        return mArticleLink;
    }

    public List<String> getmYoutubeThumbnailLinks() {
        return mYoutubeThumbnailLink;
    }

    public void setmYoutubeThumbnailLink(List<String> mYoutubeThumbnailLink) {
        this.mYoutubeThumbnailLink = mYoutubeThumbnailLink;
    }

    public void setmYoutubeLinkIds(List<String> mYoutubeLinkIds) {
        this.mYoutubeLinkIds = mYoutubeLinkIds;
    }

    public List<String> getmYoutubeLinkIds() {
        return mYoutubeLinkIds;
    }

    private TimeArticle(Parcel in) {
        mArticletitle = in.readString();
        mArticlePubDate = in.readString();
        mArticleDescription = in.readString();
        mArticleThumbnail = in.readParcelable(Item.Thumbnail.class.getClassLoader());
        content = in.readParcelable(Content.class.getClassLoader());
        mContentEncoded = in.readString();
        mArticleLink = in.readString();
        mYoutubeLinkIds = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mArticletitle);
        dest.writeString(mArticlePubDate);
        dest.writeString(mArticleDescription);
        dest.writeParcelable(mArticleThumbnail, flags);
        dest.writeTypedList(content);
        dest.writeString(mContentEncoded);
        dest.writeString(mArticleLink);
        dest.writeStringList(mYoutubeLinkIds);
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
