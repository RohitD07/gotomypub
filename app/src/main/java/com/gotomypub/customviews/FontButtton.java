package com.gotomypub.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.gotomypub.R;
import com.gotomypub.utilitycomponents.fontsutility.FontCache;




/**
 * Created by rohitd061 on 30/06/2017.
 * Custom  button to apply custom styles
 */

public class FontButtton extends AppCompatButton {
    public FontButtton(Context context) {
        super(context);
        applyCustomFont(context,null);
    }

    public FontButtton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context,attrs);
    }

    public FontButtton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context,attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        int fontName= FontCache.FONT_VAG_ROUNDED_STD_LIGHT;
        if(attrs!=null){
            TypedArray attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.FontButtton);

            fontName= attributeArray.getInt(R.styleable.FontButtton_customfont,0);
            attributeArray.recycle();
        }

        Typeface customFont= FontCache.getTypeface(fontName, context);
        setTypeface(customFont);


    }
}
