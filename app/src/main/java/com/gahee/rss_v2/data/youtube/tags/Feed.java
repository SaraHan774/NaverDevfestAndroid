package com.gahee.rss_v2.data.youtube.tags;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "feed", strict = false)
public class Feed implements Parcelable {

    public Feed(){

    }

    @Element(name = "channelid", required = false)
    private String channelId;

    @Element(name = "title", required = false)
    private String title;

    @ElementList(inline = true, entry = "entry", required = false)
    private List<Entry> entries;
//https://www.youtube.com/channel/UCD_grdLAvD4nqcqck2E-tuw


    public String getChannelId() {
        return channelId;
    }

    public String getTitle() {
        return title;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    protected Feed(Parcel in) {
        channelId = in.readString();
        title = in.readString();
        entries = in.createTypedArrayList(Entry.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(channelId);
        dest.writeString(title);
        dest.writeTypedList(entries);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };
}
