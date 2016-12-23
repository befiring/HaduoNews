package com.befiring.haduonews.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.befiring.haduonews.R;
import com.befiring.haduonews.bean.News;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/14.
 */

public class NewsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<News> mList;
    private View.OnClickListener mListener;
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.ic_launcher)
            .showImageOnFail(R.mipmap.ic_launcher)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .build();

    public NewsRecyclerAdapter(List<News> mList, View.OnClickListener mListener) {
        this.mList = mList;
        this.mListener = mListener;
    }

    private enum ITEM_TYPE{
        ITEM1,
        ITEM2
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==ITEM_TYPE.ITEM1.ordinal()){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_first, parent, false);
            return new ViewHolder1(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
            return new ViewHolder2(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder1){
            ((ViewHolder1)holder).initData(mList.get(position));
        }else {
            ((ViewHolder2)holder).initData(mList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position==0?ITEM_TYPE.ITEM1.ordinal():ITEM_TYPE.ITEM2.ordinal();
    }



    class ViewHolder2 extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title_text)
        TextView titleText;
        @BindView(R.id.item_date_text)
        TextView dateText;
        @BindView(R.id.item_image)
        ImageView itemImage;

        ViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(mListener);
        }

        void initData(News news) {
            itemView.setTag(news.getUrl());
            titleText.setText(news.getTitle());
            dateText.setText(news.getDate());
            //显示图片的配置
            ImageLoader.getInstance().displayImage(news.getThumbnail_pic_s(), itemImage, options);

        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder{

        @BindView(R.id.item_top_image)ImageView topImage;
        @BindView(R.id.item1_title_text)TextView titleText;
        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(mListener);
        }
        void initData(News news){
            itemView.setTag(news.getUrl());
            titleText.setText(news.getTitle());
            ImageLoader.getInstance().displayImage(news.getThumbnail_pic_s(), topImage, options);
        }
    }
}
