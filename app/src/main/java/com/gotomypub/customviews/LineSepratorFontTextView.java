package com.gotomypub.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by rohitd061 on 07/07/2017.
 * to apply custom styles
 */

public class LineSepratorFontTextView extends FontTextView {
    private Paint paint;
    public LineSepratorFontTextView(Context context) {
        super(context);
    }

    public LineSepratorFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineSepratorFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint =new Paint();
        paint.setColor(Color.parseColor("#F00000FF"));
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(0, getHeight(), 0, getHeight(), paint);
    }
}
