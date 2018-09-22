package com.gotomypub.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.gotomypub.R;
import com.gotomypub.utilitycomponents.fontsutility.FontCache;

public class IconTextView extends TextView {
    public IconTextView(Context context) {
        super(context);
        applyCustomFont(context,null);
    }

    public IconTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context,attrs);
    }

    public IconTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context,attrs);
    }

    public IconTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont(context,attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        int fontName= FontCache.FONT_AWSOME;
        if(attrs!=null){
            TypedArray attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.IconTextView);

            fontName= attributeArray.getInt(R.styleable.IconTextView_iconcustomfont,0);
            attributeArray.recycle();
        }

        Typeface customFont= FontCache.getTypeface(fontName, context);
        setTypeface(customFont);


    }
}
