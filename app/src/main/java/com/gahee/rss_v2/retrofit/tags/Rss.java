package com.gahee.rss_v2.retrofit.tags;

import org.simpleframework.xml.Element;

import java.io.Serializable;

public class Rss implements Serializable {

    @Element(name = "channel")
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }
}
