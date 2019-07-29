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

import javax.net.ssl.HttpsURLConnection;

public class StringUtils {


    static String readFromUrl(String link){ //network connection이 null을 반환함.
        HttpsURLConnection urlConnection  = null;
        BufferedReader reader = null;
        String data = null;

        try{

            URL request = new URL(link);
            urlConnection = (HttpsURLConnection)request.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int resCode = urlConnection.getResponseCode();
            System.out.println("res code: " + resCode);
            if(resCode != HttpsURLConnection.HTTP_OK)
                return null;

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            //buffer 로 바꿔보기
            String line;
            while((line = reader.readLine()) != null){ //disconnection boilerplate 제거하기!!!
                builder.append(line);
                builder.append("\n");
                if(builder.length() == 0){
                    return null;
                }
            }
            data = builder.toString();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(urlConnection != null){ //안하면 null pointer exception
                urlConnection.disconnect();
            }
            if(reader != null){
                try{
                    reader.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }

        }
        return data;
    }

    private static String fetchingWithJsoup(String link){
        Connection connection = Jsoup.connect(link);
        connection.maxBodySize(0);
        Document document = null;
        try {
            document = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements content = document.getAllElements();

        for(Element e : content){
            Elements p = e.getElementsByTag("iframe");
            for(Element tag : p){
                System.out.println(p);
            }
        }
        return "";
    }


    //extracting youtube links from Time article
    public static void timeGetYoutubeLinksFromArticle(Item item, TimeArticle timeArticle){
        List<String> youtubeLinks = new ArrayList<>();
        if(youtubeLinks != null){
            youtubeLinks.clear();
        }
        Document document = Jsoup.parse(item.getContentEncoded());
        Elements links = document.select("iframe");
        if(links != null){
            for(Element link : links){
                String s = link.attr("src");
                Log.d("youtube links : ", " ||| " + StringUtils.getYoutubeVideoIDFromUrl(s));
                youtubeLinks.add(link.attr("src"));
            }
            timeArticle.setmYoutubeLink(youtubeLinks);
        }else{
            timeArticle.setmYoutubeLink(null);
            }
        }

        public static String formatPubDateString(String pubDate){
            String formattedDate = pubDate.replace("+0000", "GMT");
            return formattedDate;
        }

//    public static void getImagesFromWWFArticle(List<com.gahee.rss_v2.data.wwf.tags.Item> items){
//        for(com.gahee.rss_v2.data.wwf.tags.Item item: items){
//            Document document = Jsoup.parse(item.getContentEncoded());
//            Elements links = document.select("img");
//            for(Element element : links){
//                Log.d("image links : ", element.attr("src") + "\n");
//                String link = element.attr("src");
//                if(link.contains("jpg")){
//                   extractedMediaLinks.add(link);
//                }
//            }
//            //set the media link to article object
//        }
//    }

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
        String cleanString = document.text();
        return cleanString;
    }
//
//    public static String getYoutubeThumbnailUrlFromVideoUrl(String videoUrl) {
//        return "http://img.youtube.com/vi/"+getYoutubeVideoIdFromUrl(videoUrl) + "/0.jpg";
//    }
//
//    public static String getYoutubeVideoIdFromUrl(String youtubeUrl) {
//        youtubeUrl = youtubeUrl.replace("&feature=youtu.be", "");
//        if (youtubeUrl.toLowerCase().contains("youtu.be")) {
//            return youtubeUrl.substring(youtubeUrl.lastIndexOf("/") + 1);
//        }
//        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
//        Pattern compiledPattern = Pattern.compile(pattern);
//        Matcher matcher = compiledPattern.matcher(youtubeUrl);
//        if (matcher.find()) {
//            return matcher.group();
//        }
//        return null;
//    }

    // extracts ID from this kind of url -> https://www.youtube.com/embed/XeUBwpx8FEg?feature=oembed
    public static String getYoutubeVideoIDFromUrl(String youtubeUrl){
        if(!youtubeUrl.equals("") || youtubeUrl != null){
            String temp = youtubeUrl.replace("https://www.youtube.com/embed/", "");
            String videoId = temp.replace("?feature=oembed", "");
            return videoId;
        }else{
            return "";
        }
    }
}
