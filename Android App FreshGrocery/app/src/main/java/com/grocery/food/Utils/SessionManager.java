package com.grocery.food.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.grocery.food.Model.User;

public class SessionManager {
    private final SharedPreferences mPrefs;
    private Context mContext;
    SharedPreferences.Editor mEditor;
    public static String USERDATA = "Userdata";
    public static boolean ISCART=false;
    public static String AREA = "area";
    public static String CURRUNCY = "currncy";

    public SessionManager(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPrefs.edit();
        mContext = context;
    }

    public void setStringData(String key, String val) {
        mEditor.putString(key, val);
        mEditor.commit();
    }

    public String getStringData(String key) {
        return mPrefs.getString(key, "");
    }

    public void setBooleanData(String key, Boolean val) {
        mEditor.putBoolean(key, val);
        mEditor.commit();
    }

    public boolean getBooleanData(String key) {
        return mPrefs.getBoolean(key, false);
    }

    public void setIntData(String key, int val) {
        mEditor.putInt(key, val);
        mEditor.commit();
    }

    public int getIntData(String key) {
        return mPrefs.getInt(key, 0);
    }

    public void setUserDetails(String key, User val) {
        mEditor.putString(USERDATA, new Gson().toJson(val));
        mEditor.commit();
    }

    public User getUserDetails(String key) {
        return new Gson().fromJson(mPrefs.getString(USERDATA, ""), User.class);
    }
    public void logoutUser() {
        mEditor.clear();
        mEditor.commit();
    }
}
