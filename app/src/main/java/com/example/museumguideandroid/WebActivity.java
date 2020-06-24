package com.example.museumguideandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        String URL = getIntent().getStringExtra("URL");
        WebView web = findViewById(R.id.webview);
        web.loadUrl(URL);
    }
}
