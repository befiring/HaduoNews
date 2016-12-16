package com.befiring.haduonews.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.befiring.haduonews.R;
import com.befiring.haduonews.app.Config;
import com.befiring.haduonews.bean.Result;
import com.befiring.haduonews.fragment.JokeFragment;
import com.befiring.haduonews.fragment.MeFragment;
import com.befiring.haduonews.fragment.NewsFragment;
import com.befiring.haduonews.fragment.TabFrament;
import com.befiring.haduonews.network.Network;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
//    public static final String[] tabTitle = new String[]{"头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
//
//    @BindView(R.id.tab)TabLayout tabLayout;
//    @BindView(R.id.viewpager)ViewPager viewPager;
    @BindView(R.id.btn_news)RadioButton btnNews;
    @BindView(R.id.btn_joke)RadioButton btnJoke;
    @BindView(R.id.btn_mine)RadioButton btnMine;

    NewsFragment newsFragment=new NewsFragment();
    JokeFragment jokeFragment=new JokeFragment();
    MeFragment meFragment=new MeFragment();

//    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setBottomIcon();
        initEvent();
        setDefaultFragment();
//        initView();
//        initEvent();
//        setSupportActionBar(toolbar);

//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);

//            actionBar.hide();
//        }
        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

    }

//    private void initView() {
//        List<Fragment> fragments=new ArrayList<>();
//        for (int i = 0; i < tabTitle.length; i++) {
//            fragments.add(TabFrament.newInstance(i+1));
//        }
//        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(),fragments));
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//
//        setBottomIcon();
//    }

    private void setBottomIcon() {
        Drawable drawableNews= ContextCompat.getDrawable(this, R.drawable.bottom_news_selector);
        drawableNews.setBounds(0,0,50,50);
        btnNews.setCompoundDrawables(null,drawableNews,null,null);
        Drawable drawableJoke= ContextCompat.getDrawable(this,R.drawable.bottom_news_selector);
        drawableJoke.setBounds(0,0,50,50);
        btnJoke.setCompoundDrawables(null,drawableJoke,null,null);
        Drawable drawableMe= ContextCompat.getDrawable(this,R.drawable.bottom_me_selector);
        drawableMe.setBounds(0,0,50,50);
        btnMine.setCompoundDrawables(null,drawableMe,null,null);
        btnNews.setChecked(true);
    }

    private void initEvent(){
         btnNews.setOnCheckedChangeListener(new MyOncheckedChangeListener());
         btnJoke.setOnCheckedChangeListener(new MyOncheckedChangeListener());
         btnMine.setOnCheckedChangeListener(new MyOncheckedChangeListener());
    }

    public void setDefaultFragment(){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.content,newsFragment);
        transaction.add(R.id.content,jokeFragment);
        transaction.add(R.id.content,meFragment);
        hideFragments(transaction);
        transaction.show(newsFragment);
        transaction.commit();
    }

    public void setActionBarBackground(int color){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setIcon(R.mipmap.ic_launcher);
            actionBar.setBackgroundDrawable(new ColorDrawable(color));
        }
    }


//    private class PagerAdapter extends FragmentPagerAdapter{
//
//        private List<Fragment> fragments;
//
//        public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
//            super(fm);
//            this.fragments = fragments;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return fragments.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return tabTitle.length;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return tabTitle[position];
//        }
//    }

    class MyOncheckedChangeListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            if(checked){
                hideFragments(transaction);
                switch (compoundButton.getId()){
                    case R.id.btn_news:
                        transaction.show(newsFragment);
                        break;
                    case R.id.btn_joke:
                        transaction.show(jokeFragment);
                        break;
                    case R.id.btn_mine:
                        transaction.show(meFragment);
                        break;
                }
            }

            transaction.commit();
            compoundButton.setTextColor(ContextCompat.getColor(MainActivity.this,checked?R.color.colorPrimary:R.color.black));
        }
    }

    private void hideFragments(FragmentTransaction transaction){
        transaction.hide(newsFragment);
        transaction.hide(jokeFragment);
        transaction.hide(meFragment);
    }
}
