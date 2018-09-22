package com.gotomypub.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.gotomypub.R;
import com.gotomypub.utilitycomponents.fontsutility.FontCache;



/**
 * Created by rohitd061 on 27/06/2017.
 * to apply custom styles
 */

public class FontTextView extends AppCompatTextView {
    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public FontTextView(Context context) {
        super(context);
        applyCustomFont(context,null);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context,attrs);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context,attrs);
    }





    private void applyCustomFont(Context context, AttributeSet attrs) {
        int fontName= FontCache.FONT_VAG_ROUNDED_STD_LIGHT;
        if(attrs!=null){
            TypedArray attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.FontTextView);

            fontName= attributeArray.getInt(R.styleable.FontTextView_customfont,0);
            attributeArray.recycle();
        }

 Typeface customFont= FontCache.getTypeface(fontName, context);
        setTypeface(customFont);


    }
}
