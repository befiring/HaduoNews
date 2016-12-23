package com.befiring.haduonews.test;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2016/12/21.
 */

public class MyAnimView extends View {

    public static final float RADIUS=50F;
    private String color;
    private Point currentPoint;
    private Paint mPaint;
    public MyAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(currentPoint==null){
            currentPoint=new Point(RADIUS,RADIUS);
            drawCircle(canvas);
            startAnimation();
        }else {
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x=currentPoint.getX();
        float y=currentPoint.getY();
        canvas.drawCircle(x,y,RADIUS,mPaint);
    }

    private void startAnimation() {
        Point startPoint=new Point(getWidth()/2,RADIUS);
        Point endPoint=new Point(getWidth()/2,getHeight()-RADIUS);
        ValueAnimator animator=ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentPoint=(Point)valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(2500);
        animator.start();

    }
}
