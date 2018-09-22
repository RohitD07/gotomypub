package com.gotomypub.utilitycomponents.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

/**
 * Created by rohitd061 on 26/01/2018.
 */

public class CustomAlertDialog extends AlertDialog {
    Context mContext;
     String mTitle;
     String msg;

    protected CustomAlertDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
