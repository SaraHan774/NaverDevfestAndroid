package com.gahee.rss_v2.retrofit.tags;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "image", strict = false)
public class Image implements Serializable {

    @Element(name = "url", required = false)
    private String url;
    //topic image url

    public String getUrl() {
        return url;
    }
}
