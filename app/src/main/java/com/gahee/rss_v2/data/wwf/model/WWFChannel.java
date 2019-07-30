package com.gahee.rss_v2.data.wwf.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.gahee.rss_v2.data.wwf.tags.Item;

import java.util.List;

public class WWFChannel implements Parcelable {

    private final String title;
    private final String guid;
    private final String description;
    private final List<Item> items;

    public WWFChannel(String title, String guid, String description, List<Item> items) {
        this.title = title;
        this.guid = guid;
        this.description = description;
        this.items = items;
    }

    private WWFChannel(Parcel in) {
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

    public static final Creator<WWFChannel> CREATOR = new Creator<WWFChannel>() {
        @Override
        public WWFChannel createFromParcel(Parcel in) {
            return new WWFChannel(in);
        }

        @Override
        public WWFChannel[] newArray(int size) {
            return new WWFChannel[size];
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
