package com.gahee.rss_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gahee.rss_v2.remoteSource.imageLabel.ImageLabeling;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    TextView textView;
    public static final String BASE_URL = "http://ec2-54-146-136-82.compute-1.amazonaws.com:8000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ArrayList<String> links = new ArrayList<>();
        String link = "https://www.bbcgoodfood.com/sites/default/files/guide/guide-image/2017/07/apples-700x350.png";

       Button button = findViewById(R.id.button);
        textView = findViewById(R.id.textviewtest);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                links.add(link);

            }
        });
        String json = "{\"urls\":[\n" +
                "\t\"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/the-lion-king-mufasa-simba-1554901700.jpg\", \"https://www.indiewire.com/wp-content/uploads/2019/04/Screen-Shot-2019-04-03-at-8.20.41-AM.png\"\n" +
                "]}";

//        Log.d(TAG, "onCreate: result ========" + result);

    }

}
