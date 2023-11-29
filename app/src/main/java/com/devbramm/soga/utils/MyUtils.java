package com.devbramm.soga.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.TextView;

import com.devbramm.soga.R;

public class MyUtils {
    public static class ViewDialog {
        public void showDialog(Activity activity, String msg){
            final Dialog dialog = new Dialog(activity);
            dialog.dismiss();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.util_dialog_layout);

            TextView text = (TextView) dialog.findViewById(R.id.dialog_message);
            text.setText(msg);

            dialog.show();

        }
    }
}
