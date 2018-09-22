package com.gotomypub.customviews;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by rohitd061 on 28/07/2017.
 * To apply translucent effect
 */

class TextDrawable extends Drawable {

    private final String text;
    private final Paint paint;

    public TextDrawable(String text, Float textSize, int color) {

        this.text = text;

        paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);
      //  paint.setFakeBoldText(true);
       // paint.setShadowLayer(6f, 0, 0, Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);
    }

    public TextDrawable(String text, Float textSize, int color, Typeface typeface) {

        this.text = text;

        paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);
        //paint.setFakeBoldText(true);
       // paint.setShadowLayer(6f, 0, 0, Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTypeface(typeface);
    }
    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawText(text, 0, 0, paint);

    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;

    }
}
