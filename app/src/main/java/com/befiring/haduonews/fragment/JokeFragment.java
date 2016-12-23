package com.befiring.haduonews.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.util.SortedList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Button;

import com.befiring.haduonews.R;
import com.befiring.haduonews.widget.WuziqiPanel;
import com.dd.CircularProgressButton;

import butterknife.BindView;
import butterknife.ButterKnife;


public class JokeFragment extends Fragment {

    @BindView(R.id.submit)CircularProgressButton submit;
    @BindView(R.id.restart)Button restart;
    @BindView(R.id.wuziqi)WuziqiPanel wuziqi;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.from(getActivity()).inflate(R.layout.fragment_joke,container,false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {
//        ValueAnimator animator=ValueAnimator.ofFloat(0f,1f);
//        animator.setDuration(300);
//        animator.start();
//        float curTanslationX=submit.getTranslationX();
//        ObjectAnimator moveIn=ObjectAnimator.ofFloat(submit,"translationX",-500,0f);
//        ObjectAnimator rotate=ObjectAnimator.ofFloat(submit,"rotation",0f,360f);
//        ObjectAnimator fadeInOut=ObjectAnimator.ofFloat(submit,"alpha",1f,0f,1f);
//        AnimatorSet set=new AnimatorSet();
//        set.play(rotate).with(fadeInOut).after(moveIn);
//        set.setDuration(5000);
//        set.start();

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wuziqi.restart();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        submit.animate().y(500).setDuration(5000).setInterpolator(new BounceInterpolator()).start();
    }

}
