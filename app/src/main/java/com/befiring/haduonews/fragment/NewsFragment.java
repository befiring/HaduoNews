package com.befiring.haduonews.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.befiring.haduonews.R;
import com.befiring.haduonews.activity.MainActivity;
import com.befiring.haduonews.app.Config;
import com.befiring.haduonews.bean.News;
import com.befiring.haduonews.bean.Result;
import com.befiring.haduonews.dao.helper.DaoHelper;
import com.befiring.haduonews.network.Network;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsFragment extends Fragment {

    public static final String[] tabTitle = new String[]{"头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
    public static final int[] colors = new int[]{R.color.colorPrimary, R.color.c2, R.color.c3, R.color.c4,
            R.color.c5, R.color.c6, R.color.c7, R.color.c8, R.color.c9, R.color.c10};

    @BindView(R.id.tab)TabLayout tabLayout;
    @BindView(R.id.viewpager)ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
//        tabLayout.setBackgroundColor(colors[8]);
        initView();
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                tabLayout.setBackgroundColor(colors[tabLayout.getSelectedTabPosition()]);
//                tabLayout.setSelectedTabIndicatorColor(colors[tabLayout.getSelectedTabPosition()]);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
        return view;
    }

    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    private void initView() {
        List<Fragment> fragments=new ArrayList<>();
        for (int i = 0; i < tabTitle.length; i++) {
            TabFrament fragment=TabFrament.newInstance(i+1);
            fragments.add(fragment);
        }
        viewPager.setAdapter(new PagerAdapter(getActivity().getSupportFragmentManager(),fragments));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }


    private class PagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return tabTitle.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle[position];
        }

    }

}
