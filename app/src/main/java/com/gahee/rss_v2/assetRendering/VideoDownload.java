package com.gahee.rss_v2.assetRendering;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VideoDownload {

    static String destinationDirectory = "C:\\xampp\\htdocs\\api\\upload_files\\files\\";
    static String BASE_URL = "http://www.nasa.gov/sites/default/files/atoms/";
    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build();

    static ServerAPI serverAPI = retrofit.create(ServerAPI.class);

//    public static void main(String[] args) {
//        initDownload("url", "file name . mp4");
//    }

    private static void initDownload(String url, String fileName){

        Call<ResponseBody> requestBodyCall
                = serverAPI.downloadFile(url);
        requestBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("response : " + response.body().byteStream());
                writeResponseBodyToDisk(response.body(), fileName+".mp4");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private static boolean writeResponseBodyToDisk(ResponseBody body, String fileName) {
        try {
            File file = new File(destinationDirectory + fileName);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    System.out.println("read : " + read);
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

}
