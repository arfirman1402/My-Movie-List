package androidkejar.app.mymovielist.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Pref {
    public static void putFavorite(Context context, String value) {
        SharedPreferences credentialDataPref = context.getSharedPreferences(AppConstant.NAME_CACHE, Activity.MODE_PRIVATE);
        Editor editorPreference = credentialDataPref.edit();
        editorPreference.putString(AppConstant.NAME_KEY_FAVORITE, value);
        editorPreference.apply();
    }

    public static String getFavorite(Context context) {
        SharedPreferences credentialDataPref = context.getSharedPreferences(AppConstant.NAME_CACHE, Activity.MODE_PRIVATE);
        return credentialDataPref.getString(AppConstant.NAME_KEY_FAVORITE, "");
    }
}
