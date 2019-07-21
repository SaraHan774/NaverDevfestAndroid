package com.gahee.rss_v2.retrofitYT;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "group", strict = false)
public class Media implements Parcelable{

    public Media(){

    }

    @Element(name = "title", required = false)
    private String title;

    @Element(name = "content", required = false)
    private Content content;

    @Element(name = "thumbnail", required = false)
    private Thumbnail thumbnail;

    @Element(name = "description", required = false)
    private String description;

    protected Media(Parcel in) {
        title = in.readString();
        content = in.readParcelable(Content.class.getClassLoader());
        thumbnail = in.readParcelable(Thumbnail.class.getClassLoader());
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeParcelable(content, flags);
        dest.writeParcelable(thumbnail, flags);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public Content getContent() {
        return content;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }

    @Root(name = "content", strict = false)
    public static class Content implements Parcelable {

        public Content(){

        }

        @Attribute(name = "url")
        private String contentUrl;

        protected Content(Parcel in) {
            contentUrl = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(contentUrl);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Content> CREATOR = new Creator<Content>() {
            @Override
            public Content createFromParcel(Parcel in) {
                return new Content(in);
            }

            @Override
            public Content[] newArray(int size) {
                return new Content[size];
            }
        };

        public String getContentUrl() {
            return contentUrl;
        }
    }

    @Root(name = "thumbnail", strict = false)
    public static class Thumbnail implements Parcelable{

        public Thumbnail(){

        }

        @Attribute(name = "url")
        private String thumbnailUrl;

        protected Thumbnail(Parcel in) {
            thumbnailUrl = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(thumbnailUrl);
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

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }
    }

}
