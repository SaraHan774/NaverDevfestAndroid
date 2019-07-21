package com.gahee.rss_v2;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class URLReader {

    public static void main(String[] args) {

        String link = "https://www.nasa.gov/press-release/nasa-administrator-to-talk-moon-landing-anniversary-moon-to-mars-plans";
        String link2 = "http://www.nasa.gov/ames/nisv-podcast-live-the-science-of-heat-shields";
        String link3 = "https://movieweb.com/a-beautiful-day-in-the-neighborhood-movie-photos-tom-hanks/";
        String link4 = "https://movieweb.com/top-gun-2-trailer-maverick-comic-con/";
//        String reading = readFromUrl(link);
//        System.out.println(reading);
//        PrintWriter printWriter = null;
//        try {
//            printWriter = new PrintWriter("sample_nasa_html.txt");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        printWriter.println(reading);
        fetchingWithJsoup(link4);
    }

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
        System.out.println(document.getAllElements());
        Elements script = document.getElementsByTag("script");
        for(Element element : script){
            for(DataNode dataNode : element.dataNodes()){
                System.out.println(dataNode.getWholeData());
            }
            System.out.println("-----------------------------------------------------------------------");
        }
        return "";
    }
}
