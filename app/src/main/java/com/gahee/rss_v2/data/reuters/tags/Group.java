package com.gahee.rss_v2.data.reuters.tags;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "group", strict = false)
public class Group implements Parcelable {

    public Group(){

    }

    @Element(name = "content", required = false)
    private Content content;

    public Content getContent() {
        return content;
    }



    @Root(name = "content", strict = false)
    public static class Content implements Parcelable{

        public Content(){

        }

        @Attribute(name = "url", required = false)
        private String urlVideo;

        @Element(name = "thumbnail", required = false)
        private Thumbnail thumbnail;

        public Thumbnail getThumbnail() {
            return thumbnail;
        }

        public String getUrlVideo() {
            return urlVideo;
        }

        protected Content(Parcel in) {
            urlVideo = in.readString();
            thumbnail = in.readParcelable(Thumbnail.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(urlVideo);
            dest.writeParcelable(thumbnail, flags);
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
    }

    @Root(name = "thumbnail", strict = false)
    public static class Thumbnail implements Parcelable{

        public Thumbnail(){

        }

        @Attribute(name = "url", required = false)
        private String urlThumbnail;

        public String getUrlThumbnail() {
            return urlThumbnail;
        }

        protected Thumbnail(Parcel in) {
            urlThumbnail = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(urlThumbnail);
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
    }

    protected Group(Parcel in) {
        content = in.readParcelable(Content.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(content, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
}
