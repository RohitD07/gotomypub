package com.gotomypub.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.FileOutputStream;

/**
 * Created by rohitd061 on 24/07/2017.
 * To capture signature
 */

public class SignatureView extends View {

    private static final float STROKE_WIDTH = 5f;
    private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
    private final Paint paint = new Paint();
    private final Path path = new Path();

    private float lastTouchX;
    private float lastTouchY;
    private final RectF dirtyRect = new RectF();
    private Bitmap bitmap;

    public static float getStrokeWidth() {
        return STROKE_WIDTH;
    }

    private SignatureEnableListner signatureEnableListner;




    public SignatureView(Context context) {
        super(context);
        setPaint();
    }

    public SignatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setPaint();
    }

    public SignatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPaint();
    }

    private void setPaint(){
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(STROKE_WIDTH);
    }

    public void save(View view, String StoredPath) {

       View mContent= (View) getParent();

        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        try {
            // Output the file
            FileOutputStream mFileOutStream = new FileOutputStream(StoredPath,false);
            view.draw(canvas);

            // Convert the output file to Image such as .png
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, mFileOutStream);
            mFileOutStream.flush();
            mFileOutStream.close();

        } catch (Exception ignored) {

        }

    }

    public void clear() {
        path.reset();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        //mGetSign.setEnabled(true);
        signatureEnableListner.setEnable(true);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                lastTouchX = eventX;
                lastTouchY = eventY;
                return true;

            case MotionEvent.ACTION_MOVE:

            case MotionEvent.ACTION_UP:

                resetDirtyRect(eventX, eventY);
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);
                    expandDirtyRect(historicalX, historicalY);
                    path.lineTo(historicalX, historicalY);
                }
                path.lineTo(eventX, eventY);
                break;

            default:
                debug("Ignored touch event: " + event);
                return false;
        }

        invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

        lastTouchX = eventX;
        lastTouchY = eventY;

        return true;
    }

    private void debug(String string) {



    }

    private void expandDirtyRect(float historicalX, float historicalY) {
        if (historicalX < dirtyRect.left) {
            dirtyRect.left = historicalX;
        } else if (historicalX > dirtyRect.right) {
            dirtyRect.right = historicalX;
        }

        if (historicalY < dirtyRect.top) {
            dirtyRect.top = historicalY;
        } else if (historicalY > dirtyRect.bottom) {
            dirtyRect.bottom = historicalY;
        }
    }

    private void resetDirtyRect(float eventX, float eventY) {
        dirtyRect.left = Math.min(lastTouchX, eventX);
        dirtyRect.right = Math.max(lastTouchX, eventX);
        dirtyRect.top = Math.min(lastTouchY, eventY);
        dirtyRect.bottom = Math.max(lastTouchY, eventY);
    }

    public SignatureEnableListner getSignatureEnableListner() {
        return signatureEnableListner;
    }

    public void setSignatureEnableListner(SignatureEnableListner signatureEnableListner) {
        this.signatureEnableListner = signatureEnableListner;
    }

  public  interface SignatureEnableListner{
    void setEnable(boolean enable);
    }
    }



