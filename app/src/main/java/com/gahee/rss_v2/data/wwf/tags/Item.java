package com.gahee.rss_v2.data.wwf.tags;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class Item implements Parcelable {

    @Element(name ="title", required = false)
    private String title;

    @Element(name = "guid", required = false)
    private String guid;

    @Element(name = "pubdate", required = false)
    private String pubDate;

    @Element(name = "description", required = false)
    private String description;

    @Element(name = "encoded", required = false)
    private String contentEncoded;

    public Item(){

    }


    private Item(Parcel in) {
        title = in.readString();
        guid = in.readString();
        pubDate = in.readString();
        description = in.readString();
        contentEncoded = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(guid);
        dest.writeString(pubDate);
        dest.writeString(description);
        dest.writeString(contentEncoded);
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

    public String getTitle() {
        return title;
    }

    public String getGuid() {
        return guid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getDescription() {
        return description;
    }

    public String getContentEncoded() {
        return contentEncoded;
    }
}
