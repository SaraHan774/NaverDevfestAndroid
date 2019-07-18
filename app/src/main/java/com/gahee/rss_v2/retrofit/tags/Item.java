package com.gahee.rss_v2.retrofit.tags;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "item", strict = false)
public class Item implements Serializable {

    @Element(name = "title")
    private String title;

    @Element(name = "link")
    private String link;

    @Element(name = "description")
    private String description;

    @Element(name = "enclosure")
    private Enclosure enclosure;

    @Element(name = "pubdate")
    private String pubDate;

    @Element(name = "source")
    private String source;


    @Root(name = "enclosure", strict = false)
    public static class Enclosure{

        @Attribute(name = "url")
        private String url;

        public String getUrl() {
            return url;
        }
    }
}
