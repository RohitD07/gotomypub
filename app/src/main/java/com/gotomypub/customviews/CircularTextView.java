package com.gotomypub.customviews;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

/**
 * Created by rohitd061 on 30/06/2017.
 * To show text in circular textview
 */

public class CircularTextView extends FontTextView {
    private float strokeWidth=1f;
    private int strokeColor;
    private int solidColor;
    private Context mContext;
    public CircularTextView(Context context) {
        super(context);
        mContext =context;
    }

    public CircularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext =context;
    }

    public CircularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext =context;
    }

    @Override
    protected void onDraw(Canvas canvas){

        solidColor = ContextCompat.getColor(mContext, android.R.color.black);
        strokeColor = ContextCompat.getColor(mContext, android.R.color.white);
        Paint circlePaint = new Paint();
        circlePaint.setColor(solidColor);
        circlePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        Paint strokePaint = new Paint();
        strokePaint.setColor(strokeColor);
        strokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        int  h = getHeight();
        int  w = getWidth();

        int diameter = h > w ? h : w;
        int radius = diameter/2;

        setHeight(diameter);
        setWidth(diameter);

        canvas.drawCircle(diameter / 2 , diameter / 2, radius, strokePaint);

        canvas.drawCircle(diameter / 2, diameter / 2, radius- strokeWidth, circlePaint);

        draw(canvas);
    }

    public void setStrokeWidth(int dp)
    {
        float scale = getContext().getResources().getDisplayMetrics().density;
        strokeWidth = dp*scale;

    }

    public void setStrokeColor(String color)
    {
        strokeColor = Color.parseColor(color);
    }

    public void setSolidColor(String color)
    {
        solidColor = Color.parseColor(color);

    }
}
