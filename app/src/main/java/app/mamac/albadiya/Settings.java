package app.mamac.albadiya;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by T on 22-11-2016.
 */

public class Settings {
//    public static String SERVER_URL = "http://naqshapp.com/albadiya/api/";
    public static String SERVER_URL = "http://albadiya.net/api/";
    public static String  PAYMENT_URL = "http://naqshapp.com/albadiya/Tap-app.php?";
    public static  String PAY_URL    = "http://clients.yellowsoft.in/lawyers/Tap2.php?";


    public  static  final String mem_id="mem_id";
    public  static  final String mem_name="mem_name";

    public  static void SetUserId(Context context,String id,String mem_name){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(mem_id,id);
        editor.commit();
    }
    public  static void SetUserId(Context context,String id){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(mem_id,id);
        editor.commit();
    }
    public  static String GetUserId(Context context) {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(mem_id,"-1");
    }



}

