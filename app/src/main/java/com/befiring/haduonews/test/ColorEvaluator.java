package com.befiring.haduonews.test;

import android.animation.TypeEvaluator;



public class ColorEvaluator implements TypeEvaluator {

    private int mCurrentRed = -1;

    private int mCurrentGreen = -1;

    private int mCurrentBlue = -1;
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        String startColor= (String) startValue;
        String endColor= (String) endValue;
        int startRed=Integer.parseInt(startColor.substring(1,3),16);
        return null;
    }
}
