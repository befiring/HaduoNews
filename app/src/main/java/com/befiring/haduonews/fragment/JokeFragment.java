package com.befiring.haduonews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.befiring.haduonews.R;

/**
 * Created by Administrator on 2016/12/15.
 */

public class JokeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.from(getActivity()).inflate(R.layout.fragment_joke,container,false);
        return view;
    }
}
