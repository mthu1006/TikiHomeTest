package vn.thudlm.hometestjava.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by kien on 29-Aug-16.
 */
public class AppDialogManager {

    public static Dialog createLoadingDialog(Context context){
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

}
