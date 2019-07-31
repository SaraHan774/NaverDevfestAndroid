package com.gahee.rss_v2.utils;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.gahee.rss_v2.data.time.model.TimeArticle;
import com.gahee.rss_v2.data.time.tags.Item;
import com.gahee.rss_v2.data.wwf.model.WWFArticle;
import com.gahee.rss_v2.remoteSource.imageLabel.ImageLabeling;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gahee.rss_v2.utils.Constants.IMAGE_LABELING_SERVER_URL;

public class StringUtils{

    private static final String TAG = "StringUtils";

    public static void main(String[] args) throws JSONException {
        ArrayList<String> links = new ArrayList<>();
        String link = "https://www.bbcgoodfood.com/sites/default/files/guide/guide-image/2017/07/apples-700x350.png";
        links.add(link);
////        buildPOSTRequestJSONFromStrings(links);
//
        Map<String, ArrayList<String>> map = new HashMap<>();
        map.put("urls", links);

        Gson gson = new Gson();
        String json = gson.toJson(map);

        System.out.println(json);


    }


    //extracting youtube links from Time article
    public static void extractYoutubeIdFromArticle(Item item, TimeArticle timeArticle){
        List<String> youtubeLinkIds = new ArrayList<>();
        youtubeLinkIds.clear();
        Document document = Jsoup.parse(item.getContentEncoded());
        Elements links = document.select("iframe");
        if(links != null){
            for(Element link : links){
                String s = link.attr("src");

                String id = StringUtils.getYoutubeVideoIDFromUrl(s);
                youtubeLinkIds.add(id); //only set the ID
            }
            timeArticle.setmYoutubeThumbnailLink(generateYoutubeVideoThumbnailFromIds(youtubeLinkIds));

            timeArticle.setmYoutubeLinkIds(youtubeLinkIds);
        }
    }

    public static List<String> generateYoutubeVideoThumbnailFromIds(List<String> videoIds){
        List<String> thumbnailLinks = new ArrayList<>();
        if(thumbnailLinks != null){
            thumbnailLinks.clear();
        }
        for(String id : videoIds){
            StringBuffer stringBuffer = new StringBuffer().append("http://i3.ytimg.com/vi/")
                    .append(id).append("/hqdefault.jpg");
            thumbnailLinks.add(stringBuffer.toString());

            Log.d(TAG, "generateYoutubeVideoThumbnailFromIds: " + stringBuffer);
        }
        return thumbnailLinks;
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
        //send image labeling request
        String jsonPostString = StringUtils.buildPOSTRequestJSONFromArrayList((ArrayList<String>) mediaLinks);
        ImageLabeling.generateImageLabelsFromServer(IMAGE_LABELING_SERVER_URL, jsonPostString, wwfArticle);
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
        String temp2 = temp.replace("?feature=oembed", "");
        //youtube IDs are always (at least for now) 11 digits
        //make sure no other tokens are mixed with the id string.
        String elevenDigits = temp2.substring(0, 11);
        return elevenDigits;
    }

    private static void getYoutubeWatchUrlFromIds(List<String> ids){
        List<String> links = new ArrayList<>();
        for(String id : ids){
            String watchLink = "http://www.youtube.com/watch?v=" + id;
            links.add(watchLink);
        }
    }


    /*
    * for working with JSON objects
    * */
    public static String buildPOSTRequestJSONFromArrayList(ArrayList<String> stringArrayList){
        Map<String, ArrayList<String>> map = new HashMap<>();
        map.put("urls", stringArrayList);
        Gson gson = new Gson();
//        Log.d("MMM", "buildPOSTRequestJSONFromArrayList: " + gson.toJson(map)); OK
        return gson.toJson(map);
    }


}
