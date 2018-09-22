package com.gotomypub.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.gotomypub.utilitycomponents.fontsutility.FontCache;


/**
 * Created by taralsoni on 09/02/18.
 */

public class FontEditTextBold extends AppCompatEditText {
    public FontEditTextBold(Context context) {
        super(context);
        applyCustomFont(context,null);
    }

    public FontEditTextBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context,attrs);
    }

    public FontEditTextBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context,attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        int fontName= FontCache.FONT_VAG_ROUNDED_STD_BOLD;
        /*if(attrs!=null){
           TypedArray attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.FontTextView);

            fontName= attributeArray.getString(R.styleable.FontTextView_font);
            attributeArray.recycle();
        }*/

        Typeface customFont= FontCache.getTypeface(fontName, context);
        setTypeface(customFont);


    }
}
