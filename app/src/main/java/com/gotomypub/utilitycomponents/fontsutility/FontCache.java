package com.gotomypub.utilitycomponents.fontsutility;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/*
 * Created by rohitd061 on 30/06/2017.
 * To apply Font
 */

public class FontCache {

    public static  final int FONT_VAG_ROUNDED_STD_LIGHT=0;

    public static  final int FONT_VAG_ROUNDED_STD_BOLD=1;
    public static  final int FONT_AWSOME=2;

    public static  final String FONT_VAG_ROUNDED_STD_BOLD_STRING="VAG_Rounded_Std_Bold.ttf";
    public static  final String FONT_VAG_ROUNDED_STD_LIGHT_STRING="VAG_Rounded_Std_Light.ttf";
    public static  final String FONT_AWSOME_TEXT="fasolid.ttf";


    private static final HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(int font, Context context) {
       String fontname= getFontName(font);
        Typeface typeface = fontCache.get(fontname);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontname);
            } catch (Exception e) {
                return null;
            }

            fontCache.put(fontname, typeface);
        }

        return typeface;
    }

    private static String getFontName(int fontname){
        return FONT_AWSOME_TEXT;
        /*switch (fontname){
            case FONT_VAG_ROUNDED_STD_BOLD:
                return FONT_VAG_ROUNDED_STD_BOLD_STRING;
            case FONT_VAG_ROUNDED_STD_LIGHT:
                return FONT_VAG_ROUNDED_STD_LIGHT_STRING;
            default:
                return FONT_VAG_ROUNDED_STD_LIGHT_STRING;
        }*/
    }

}
