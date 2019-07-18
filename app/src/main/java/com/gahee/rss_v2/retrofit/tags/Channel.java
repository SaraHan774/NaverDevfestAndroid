package com.gahee.rss_v2.retrofit.tags;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "channel", strict = false)
public class Channel implements Serializable {

    @Element(name = "title")
    private String title;

    @Element(name = "description")
    private String description;

    @Element(name = "link")
    private String link;
//    http://www.nasa.gov/
    @ElementList(entry = "item", inline = true, required = false)
    private Item item;

    @Element(name = "image", required = false)
    private Image image;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public Item getItem() {
        return item;
    }

    public Image getImage() {
        return image;
    }
}
