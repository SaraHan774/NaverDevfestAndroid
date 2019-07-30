package com.gahee.rss_v2.data.wwf.tags;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.util.List;

@Root(name = "channel", strict = false)
public class Channel implements Parcelable {

    @Path("title")
    @Text(required = false)
    private String title;

    @Path("guid")
    @Text(required = false)
    private String guid;

    @Path("description")
    @Text(required = false)
    private String description;

    @ElementList(entry =  "item", inline = true)
    private List<Item> items;

    public Channel(){

    }

    Channel(Parcel in) {
        title = in.readString();
        guid = in.readString();
        description = in.readString();
        items = in.createTypedArrayList(Item.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(guid);
        dest.writeString(description);
        dest.writeTypedList(items);
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

    public String getTitle() {
        return title;
    }

    public String getGuid() {
        return guid;
    }

    public String getDescription() {
        return description;
    }

    public List<Item> getItems() {
        return items;
    }
}
