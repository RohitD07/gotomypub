package com.gotomypub.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.gotomypub.utilitycomponents.fontsutility.FontCache;



/**
 * Created by rohitd061 on 23/11/2017.
 */

public class FontAutoCompleteTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {
    public FontAutoCompleteTextView(Context context) {
        super(context);
        applyCustomFont(context,null);
    }

    public FontAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context,attrs);
    }

    public FontAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context,attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        int fontName= FontCache.FONT_VAG_ROUNDED_STD_LIGHT;
       /* if(attrs!=null){
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
