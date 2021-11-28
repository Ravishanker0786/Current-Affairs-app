package com.prinfosys.news48;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class AboutPrivacyContact extends AppCompatActivity {
    WebView webView;
    String head,url;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_privacy_contact);
        webView = findViewById(R.id.about_privacy_contact);
        head = getIntent().getStringExtra("heading");
        url = getIntent().getStringExtra("view_url");
        webView = findViewById(R.id.about_privacy_contact);
        webView.loadUrl(url);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle(head);
        myToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        setSupportActionBar(myToolbar);
//        myToolbar.setVisibility(View.GONE);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
            }
        });
    }
}