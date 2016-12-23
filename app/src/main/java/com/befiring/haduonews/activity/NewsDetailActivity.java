package com.befiring.haduonews.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.befiring.haduonews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/14.
 */

public class NewsDetailActivity extends AppCompatActivity{
    @BindView(R.id.web_view)WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        String url=getIntent().getStringExtra("url");
        webView.loadUrl(url);
    }
}
