package com.gotomypub.customviews;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import java.text.DecimalFormat;

/**
 * Created by rohitd061 on 27/07/2017.
 * To apply format to value enter for currency
 */

public class CurrencyEdittext extends FontEditText {
    private TextWatcher textWatcher;

    public CurrencyEdittext(Context context) {
        super(context);
        setWatcher();
    }

    public CurrencyEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWatcher();
    }

    public CurrencyEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWatcher();
    }
    private void setWatcher(){
        textWatcher =new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                removeTextChangedListener(textWatcher);
                DecimalFormat formatter = new DecimalFormat("#,###,###");

                String digits=s.toString().replace(",","");
                digits= digits.trim();
                String currencyFormatString="0";

               try {
                   Double valDouble= Double.valueOf(digits);
                   currencyFormatString = formatter.format(valDouble);

               }
               catch (NumberFormatException e){
                   e.printStackTrace();
               }
               catch(IllegalArgumentException e){
                   e.printStackTrace();
               }
               catch(NullPointerException e){

               }
                setText(currencyFormatString);
                setSelection(currencyFormatString.length());


             /*   int length=s.length();
                if(prevLength>length)
                    return;

                String digit=s.toString();
                digit=digit.replace(",","");
                digit=digit.trim();
                length=digit.length();
                String currencyString="";
                int groupDigits=0;
                for(int index=length-1;index>=0;index++){

                    ++groupDigits;
                    if(groupDigits==4){
                        currencyString=","+currencyString;
                        groupDigits=0;

                    }
                    currencyString=digit.charAt(index)+currencyString;
                }*/

                addTextChangedListener(textWatcher);

                /*if(length%4==0){
                    CurrencyEdittext.this.removeTextChangedListener(textWatcher);
                 String firstchar=digit.substring(0,1);
                 String remainString=digit.substring(1,length);
                    String toSet=firstchar+","+remainString;
                    CurrencyEdittext.this.setText(toSet);
                    CurrencyEdittext.this.setSelection(toSet.length());
                    CurrencyEdittext.this.addTextChangedListener(textWatcher);

                }*/

            }
        };
        addTextChangedListener(textWatcher);
    }
}
