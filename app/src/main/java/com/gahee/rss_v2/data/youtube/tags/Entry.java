package com.gahee.rss_v2.data.youtube.tags;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "entry", strict = false)
public class Entry implements Parcelable{

    @Element(name="id", required =  false)
    private String videoid;

    @Element(name = "channelId", required = false)
    private String channelId;

    @Element(name = "title", required = false)
    private String title;

    @Element(name = "author", required = false)
    private Author author;

    @Element(name = "published", required = false)
    private String published;

    @Element(name = "group", required = false)
    private Media media ;

    public Entry(){

    }

    protected Entry(Parcel in) {
        videoid = in.readString();
        channelId = in.readString();
        title = in.readString();
        author = in.readParcelable(Author.class.getClassLoader());
        published = in.readString();
        media = in.readParcelable(Media.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(videoid);
        dest.writeString(channelId);
        dest.writeString(title);
        dest.writeParcelable(author, flags);
        dest.writeString(published);
        dest.writeParcelable(media, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Entry> CREATOR = new Creator<Entry>() {
        @Override
        public Entry createFromParcel(Parcel in) {
            return new Entry(in);
        }

        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };

    public String getVideoid() {
        return videoid;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public String getPublished() {
        return published;
    }

    public Media getMedia() {
        return media;
    }

    @Root(name = "author", strict = false)
    public static class Author implements Parcelable {

        @Element(name = "name", required = false)
        private String name;

        public Author(){

        }

        public String getName() {
            return name;
        }

        protected Author(Parcel in) {
            name = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Author> CREATOR = new Creator<Author>() {
            @Override
            public Author createFromParcel(Parcel in) {
                return new Author(in);
            }

            @Override
            public Author[] newArray(int size) {
                return new Author[size];
            }
        };


    }

}
