package com.gahee.rss_v2.data.time.tags;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class Rss implements Parcelable {

    public Rss(){

    }

    @Element(name = "channel")
    private Channel channel;


    private Rss(Parcel in) {
        channel = in.readParcelable(Channel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(channel, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Rss> CREATOR = new Creator<Rss>() {
        @Override
        public Rss createFromParcel(Parcel in) {
            return new Rss(in);
        }

        @Override
        public Rss[] newArray(int size) {
            return new Rss[size];
        }
    };

    public Channel getChannel() {
        return channel;
    }

}
