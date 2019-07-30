package vn.thudlm.hometestjava.utils;

import android.content.Context;

public class Validation {
    public static Context mContext;

    public static void onCreate(Context context){
        Validation.mContext=context;
    }

    public static boolean checkNullOrEmpty(String s){
        if(s==null)
            return true;
        else
            if(s.trim().equals("")||s.trim().equals("null"))
                return true;
            else return false;
    }

}
