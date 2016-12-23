package com.befiring.haduonews.test;

import android.animation.TypeEvaluator;

import com.befiring.haduonews.test.Point;

/**
 * Created by Administrator on 2016/12/21.
 */

public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint= (Point) startValue;
        Point endPoint= (Point) endValue;
        float x=startPoint.getX()+fraction*(endPoint.getX()-startPoint.getX());
        float y=startPoint.getY()+fraction*(endPoint.getY()-startPoint.getY());
        return new Point(x,y);
    }
}
