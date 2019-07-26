package com.gahee.rss_v2.data.time.tags;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class Item implements Parcelable{

    public Item(){

    }

    @Element(name = "title")
    private String articleTitle;

    @Element(name = "pubDate")
    private String pubDate;

    @Element(name = "description")
    private String articleDesc;

    @Element(name = "thumbnail")
    private Thumbnail thumbnail;

    @Element(name = "encoded", required = false)
    private String contentEncoded;

    @Element(name = "origLink")
    private String articleLink;


    @Root(name = "thumbnail", strict = false)
    public static class Thumbnail implements Parcelable {

        public Thumbnail(){

        }

        @Attribute(name = "url")
        private String url;

        protected Thumbnail(Parcel in) {
            url = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(url);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Thumbnail> CREATOR = new Creator<Thumbnail>() {
            @Override
            public Thumbnail createFromParcel(Parcel in) {
                return new Thumbnail(in);
            }

            @Override
            public Thumbnail[] newArray(int size) {
                return new Thumbnail[size];
            }
        };

        public String getUrl() {
            return url;
        }
    }

    public Item(String articleTitle, String pubDate, String articleDesc, Thumbnail thumbnail, String contentEncoded, String articleLink) {
        this.articleTitle = articleTitle;
        this.pubDate = pubDate;
        this.articleDesc = articleDesc;
        this.thumbnail = thumbnail;
        this.contentEncoded = contentEncoded;
        this.articleLink = articleLink;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public String getContentEncoded() {
        return contentEncoded;
    }

    public String getArticleLink() {
        return articleLink;
    }

    protected Item(Parcel in) {
        articleTitle = in.readString();
        pubDate = in.readString();
        articleDesc = in.readString();
        thumbnail = in.readParcelable(Thumbnail.class.getClassLoader());
        contentEncoded = in.readString();
        articleLink = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(articleTitle);
        dest.writeString(pubDate);
        dest.writeString(articleDesc);
        dest.writeParcelable(thumbnail, flags);
        dest.writeString(contentEncoded);
        dest.writeString(articleLink);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
