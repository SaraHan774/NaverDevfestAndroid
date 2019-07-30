package com.gahee.rss_v2.utils;

import android.util.Log;

import com.gahee.rss_v2.data.time.model.TimeArticle;
import com.gahee.rss_v2.data.time.tags.Item;
import com.gahee.rss_v2.data.wwf.model.WWFArticle;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class StringUtils {

    private static final String TAG = "StringUtils";


//    private static String fetchingWithJsoup(String link){
//        Connection connection = Jsoup.connect(link);
//        connection.maxBodySize(0);
//        Document document = null;
//        try {
//            document = connection.get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Elements content = document != null ? document.getAllElements() : null;
//
//        for(Element e : content){
//            Elements p = e.getElementsByTag("iframe");
//            for(Element tag : p){
//                System.out.println(p);
//            }
//        }
//        return "";
//    }


    //extracting youtube links from Time article
    public static void timeGetYoutubeLinksFromArticle(Item item, TimeArticle timeArticle){
        List<String> youtubeLinks = new ArrayList<>();
        youtubeLinks.clear();
        Document document = Jsoup.parse(item.getContentEncoded());
        Elements links = document.select("iframe");
        if(links != null){
            for(Element link : links){
                String s = link.attr("src");
                String id = StringUtils.getYoutubeVideoIDFromUrl(s);
                Log.d("youtube links : ", " ||| " + StringUtils.getYoutubeVideoIDFromUrl(s));
                youtubeLinks.add(id); //only set the ID
            }
            timeArticle.setmYoutubeLink(youtubeLinks);
        }else{
            timeArticle.setmYoutubeLink(null);
            }
        }


    public static String formatTIMEPubDateString(String pubDate){
        return pubDate.replace("+0000", "GMT");
    }

    public static String formatWWFPubDateString(String pubDate){
        Pattern pattern = Pattern.compile("2019");
        Matcher matcher = pattern.matcher(pubDate);
        int endIndex = 1;
        while (matcher.find()){
            endIndex = matcher.end();
        }
        return pubDate.substring(0, endIndex);
    }


    public static void wwfExtractImageTags
            (WWFArticle wwfArticle, com.gahee.rss_v2.data.wwf.tags.Item item){
        List<String> mediaLinks = new ArrayList<>();
        Document document = Jsoup.parse(item.getContentEncoded());
        Elements links = document.select("img");
        for(Element element : links){
            String link = element.attr("src");
            if(link.contains("jpg")){
                Log.d("image links : ", link);
                mediaLinks.add(link);
            }
        }
        wwfArticle.setExtractedMediaLinks(mediaLinks);
    }

    public static String removeHtmlTagsFromString
            (String stringWithTags){
        Document document = Jsoup.parse(stringWithTags);
        return document.text();
    }


    // extracts ID from this kind of url -> https://www.youtube.com/embed/XeUBwpx8FEg?feature=oembed
    private static String getYoutubeVideoIDFromUrl(String youtubeUrl){
        String temp = youtubeUrl.replace("https://www.youtube.com/embed/", "");
        return temp.replace("?feature=oembed", "");
    }
}
