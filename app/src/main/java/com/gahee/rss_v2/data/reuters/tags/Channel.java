package com.gahee.rss_v2.data.reuters.tags;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "channel", strict = false)
public class Channel implements Parcelable {

    @Element(name = "title", required = false)
    private String channelTitle;

    @Element(name = "description", required = false)
    private String channelDescription;

    @Path("channel/link")
    @Element(name = "link", required = false)
    private String channelLink;
//    http://www.nasa.gov/
    @ElementList(entry = "item", inline = true, required = false)
    private ArrayList<Item> item;


    public Channel(){

    }

    Channel(Parcel in) {
        channelTitle = in.readString();
        channelDescription = in.readString();
        channelLink = in.readString();
        item = in.createTypedArrayList(Item.CREATOR);
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(channelTitle);
        dest.writeString(channelDescription);
        dest.writeString(channelLink);
        dest.writeList(item);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Channel> CREATOR = new Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel in) {
            return new Channel(in);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public String getChannelLink() {
        return channelLink;
    }

    public ArrayList<Item> getItem() {
        return item;
    }

}
