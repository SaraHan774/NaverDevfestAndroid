package com.gahee.rss_v2.data.nasa.tags;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "item", strict = false)
public class Item implements Parcelable {

    @Element(name = "title", required = false)
    private String title;

    @Element(name = "link", required = false)
    private String link;

    @Element(name = "description", required = false)
    private String description;

    @Element(name = "enclosure", required = false)
    private Enclosure enclosure;

    @Element(name = "pubDate", required = false)
    private String pubDate;

    @Element(name = "source", required = false)
    private String source;

    public Item(){

    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getSource() {
        return source;
    }

    @Root(name = "enclosure", strict = false)
    public static class Enclosure implements Parcelable{

        @Attribute(name = "url")
        private String url;

        Enclosure(){

        }

        Enclosure(Parcel in) {
            url = in.readString();
        }

        public static final Creator<Enclosure> CREATOR = new Creator<Enclosure>() {
            @Override
            public Enclosure createFromParcel(Parcel in) {
                return new Enclosure(in);
            }

            @Override
            public Enclosure[] newArray(int size) {
                return new Enclosure[size];
            }
        };

        public String getUrl() {
            return url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(url);
        }
    }

    private Item(Parcel in) {
        title = in.readString();
        link = in.readString();
        description = in.readString();
        enclosure = in.readParcelable(Enclosure.class.getClassLoader());
        pubDate = in.readString();
        source = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(description);
        dest.writeParcelable(enclosure, flags);
        dest.writeString(pubDate);
        dest.writeString(source);
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
