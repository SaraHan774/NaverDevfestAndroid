package com.gahee.rss_v2.data.nasa.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ArticleObj implements Parcelable {

    private final String mArticleTitle;
    private final String mArticleLink;
    private final String mArticleDescription;
    private final String mArticleMedia;
    private final String mArticlePubDate;
    private final String mArticleSource;

    public ArticleObj(String mArticleTitle, String mArticleLink, String mArticleDescription, String mArticleMedia, String mArticlePubDate, String mArticleSource) {
        this.mArticleTitle = mArticleTitle;
        this.mArticleLink = mArticleLink;
        this.mArticleDescription = mArticleDescription;
        this.mArticleMedia = mArticleMedia;
        this.mArticlePubDate = mArticlePubDate;
        this.mArticleSource = mArticleSource;
    }

    private ArticleObj(Parcel in) {
        mArticleTitle = in.readString();
        mArticleLink = in.readString();
        mArticleDescription = in.readString();
        mArticleMedia = in.readString();
        mArticlePubDate = in.readString();
        mArticleSource = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mArticleTitle);
        dest.writeString(mArticleLink);
        dest.writeString(mArticleDescription);
        dest.writeString(mArticleMedia);
        dest.writeString(mArticlePubDate);
        dest.writeString(mArticleSource);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ArticleObj> CREATOR = new Creator<ArticleObj>() {
        @Override
        public ArticleObj createFromParcel(Parcel in) {
            return new ArticleObj(in);
        }

        @Override
        public ArticleObj[] newArray(int size) {
            return new ArticleObj[size];
        }
    };
}
