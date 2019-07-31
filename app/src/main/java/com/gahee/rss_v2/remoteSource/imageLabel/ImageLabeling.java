package com.gahee.rss_v2.remoteSource.imageLabel;

import android.os.AsyncTask;
import android.util.Log;

import com.gahee.rss_v2.data.wwf.model.WWFArticle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.gahee.rss_v2.utils.Constants.DESCRIPTION;
import static com.gahee.rss_v2.utils.Constants.NETWORK_TIMEOUT;
import static com.gahee.rss_v2.utils.Constants.RESULTS;
import static com.gahee.rss_v2.utils.Constants.SCORE;

public class ImageLabeling {

    private static final String TAG = "ImageLabeling";
    private static String dataFromServer;

    public static String sendREST(String serverUrl, String jsonPostString) throws IllegalStateException{
        String inputLine;
        StringBuffer stringBuffer = new StringBuffer();
        if(dataFromServer != null){
            return dataFromServer;
        }else {
            try {
                Log.d(TAG, "sendREST: START");

                URL url = new URL(serverUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept-Charset", "UTF-8");
                connection.setConnectTimeout(NETWORK_TIMEOUT);
                connection.setReadTimeout(NETWORK_TIMEOUT);

                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(jsonPostString.getBytes("UTF-8"));
                outputStream.flush();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "UTF-8")
                );
                while ((inputLine = reader.readLine()) != null) {
                    stringBuffer.append(inputLine);
                }
                connection.disconnect();
                Log.d(TAG, "sendREST: END");

            } catch (Exception e) {
                Log.d(TAG, "sendREST: error " + e.getMessage());
                e.printStackTrace();
            }
            dataFromServer = stringBuffer.toString();
        }
        return dataFromServer;
    }

    private static class ImageLabelAsync extends AsyncTask<Void, Void, String> {
        private String serverUrl;
        private String jsonPostString;
        private WWFArticle wwfArticle;

        public ImageLabelAsync(String serverUrl, String jsonPostString, WWFArticle wwfArticle) {
            this.serverUrl = serverUrl;
            this.jsonPostString = jsonPostString;
            this.wwfArticle = wwfArticle;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return sendREST(serverUrl, jsonPostString);
        }

        @Override
        protected void onPostExecute(String responseFromServer) {
            new HandleResultsAsync(wwfArticle).execute(responseFromServer);
        }

    }

    public static AsyncTask<Void, Void, String> generateImageLabelsFromServer(String serverUrl, String jsonPostString, WWFArticle wwfArticle){
        ImageLabelAsync imageLabelAsync = new ImageLabelAsync(serverUrl, jsonPostString, wwfArticle);
        return imageLabelAsync.execute();
    }


    private static class HandleResultsAsync extends AsyncTask<String, Void, ArrayList<String>>{
        private WWFArticle wwfArticle;
        private ArrayList<String> listOfImageLabelResults = new ArrayList<>();

        public HandleResultsAsync(WWFArticle wwfArticle){
            this.wwfArticle = wwfArticle;
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {

            if(wwfArticle.getImageLabelResponse() != null){
                wwfArticle.setImageLabelResponse(wwfArticle.getImageLabelResponse());
            }else {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(strings[0]);

                    JSONObject labelResultJsonObj = jsonObject.getJSONObject(RESULTS);
                    if (listOfImageLabelResults != null) {
                        listOfImageLabelResults.clear();
                    }
                    while (labelResultJsonObj.keys().hasNext()) {
                        Log.d(TAG, "doInBackground: " + labelResultJsonObj.keys().hasNext());
                        String currentDynamicKey = labelResultJsonObj.keys().next();
                        JSONArray currentJsonArray = labelResultJsonObj.getJSONArray(currentDynamicKey);
                        for (int i = 0; i < currentJsonArray.length(); i++) {
                            JSONObject labelInfoJsonObj = (JSONObject) currentJsonArray.get(i);
                            Double score = labelInfoJsonObj.getDouble(SCORE);
                            String description = labelInfoJsonObj.getString(DESCRIPTION);
                            if (score > 0.8) {
                                listOfImageLabelResults.add(description);
                            }
                        }
                        return listOfImageLabelResults;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> stringArrayList) {
            if(wwfArticle.getImageLabelResponse() == null) {
                wwfArticle.setImageLabelResponse(stringArrayList);
            }
        }
     }

    }


