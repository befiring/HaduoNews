package com.befiring.haduonews.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.befiring.haduonews.R;
import com.befiring.haduonews.activity.NewsDetailActivity;
import com.befiring.haduonews.adapter.NewsRecyclerAdapter;
import com.befiring.haduonews.app.Config;
import com.befiring.haduonews.bean.News;
import com.befiring.haduonews.bean.Result;
import com.befiring.haduonews.dao.NewsDao;
import com.befiring.haduonews.dao.helper.DaoHelper;
import com.befiring.haduonews.network.Network;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TabFrament extends BaseFragment {
    public static final String[] newsType = new String[]{"top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"};
    public static String TABLAYOUT_FRAGMENT = "tab_fragment";
    private Map<Integer,List<News>> cacheNews=new HashMap<>();
    private int type;
    private Timer timer;
    private TimerTask timerTask;
    private List<News> newses = new ArrayList<>();
    private NewsRecyclerAdapter mAdapter;
    @BindView(R.id.news_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    private void initData(){
        timer=new Timer();
        timerTask=new TimerTask() {
            @Override
            public void run() {
                getNews();
            }
        };
    }


    private void initView() {
        refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNews();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(0));
        mAdapter=new NewsRecyclerAdapter(newses,new OnNewsClickListener());
//        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(null);
        refreshUI();

    }

    private void getNews() {
        Network.getApiService().getNews2(newsType[type - 1], Config.API_KEY).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    Log.d("wm", str);
                    JSONObject object = new JSONObject(str);
                    Gson gson = new Gson();
                    Result result = gson.fromJson(object.getString("result"), Result.class);
                    newses = result.getData();

//                    Log.d("wm", "thread=" + Thread.currentThread().getName());
                    DaoHelper.getInstance().getNewsDao().insertOrReplaceInTx(newses);
//                    Log.d("wm","data from db="+DaoHelper.getInstance().getNewsDao().loadAll().size());
                    addToMemeryCache(newses);
//                    mAdapter.notifyDataSetChanged();
                    refreshUI();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void addToMemeryCache(List<News> newses){
        cacheNews.put(type,newses);
    }


    public static TabFrament newInstance(int type) {

            Bundle bundle = new Bundle();
            bundle.putSerializable(TABLAYOUT_FRAGMENT, type);
            TabFrament tabFrament = new TabFrament();
            tabFrament.setArguments(bundle);
            return tabFrament;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = (int) getArguments().getSerializable(TABLAYOUT_FRAGMENT);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       timer.schedule(timerTask,500);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
        if(timerTask!=null){
            timerTask.cancel();
            timerTask=null;
        }
    }

    class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = space;
        }
    }

    class OnNewsClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra("url", (String) view.getTag());
            getActivity().startActivity(intent);
        }
    }

   public void refreshUI(){
       mAdapter = new NewsRecyclerAdapter(cacheNews.get(type), new OnNewsClickListener());
       recyclerView.setAdapter(mAdapter);
       refreshLayout.setRefreshing(false);
   }

}
