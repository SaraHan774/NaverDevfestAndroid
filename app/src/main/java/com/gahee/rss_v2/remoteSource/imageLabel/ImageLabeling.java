package com.gahee.rss_v2.remoteSource.imageLabel;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLabeling {
    private static final String TAG = "ImageLabeling";

    public static String sendREST(String serverUrl, String jsonPostString) throws IllegalStateException{
        String inputLine = null;
        StringBuffer stringBuffer = new StringBuffer();

        try{
            Log.d(TAG, "sendREST: START");

            URL url = new URL(serverUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(jsonPostString.getBytes("UTF-8"));
            outputStream.flush();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8")
            );
            while((inputLine = reader.readLine()) != null){
                stringBuffer.append(inputLine);
            }
            connection.disconnect();
            Log.d(TAG, "sendREST: END");

        }catch (Exception e){
            Log.d(TAG, "sendREST: error " + e.getMessage());
            e.printStackTrace();
        }
        Log.d(TAG, "sendREST: " + stringBuffer.toString());
        return stringBuffer.toString();
    }

    //async task loader -> on load finished

    private static class MyAsync extends AsyncTask<Void, Void, String>{
        private String serverUrl;
        private String jsonPostString;

        public MyAsync(String serverUrl, String jsonPostString){
            this.serverUrl = serverUrl;
            this.jsonPostString = jsonPostString;
        }
        @Override
        protected String doInBackground(Void... voids) {
            sendREST(serverUrl, jsonPostString);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
        }
    }

    public static String doBackgroundWork(String first, String second){
        return String.valueOf(new MyAsync(first, second).execute());
    }

}
