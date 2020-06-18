package com.MillionaireGPS.Setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.MillionaireGPS.Login.User;

public class SharedPrefManage {

    private static final String NAME_SHARED_PREF = "shared_pref";
    private static final String UNIT_MONEY = "unitMoney";
    private SharedPreferences preferences;
    private static final String SAVE_URL = "SAVE_URL";
    private static final String ISLOGIN = "ISLOGIN";
    private static final String USER_NAME = "user_name";
    private static final String USER_CODE = "user_code";
    public SharedPrefManage(Context context) {
        preferences = context.getSharedPreferences(NAME_SHARED_PREF , Context.MODE_PRIVATE);
    }

    public void setSaveUrl(String url){
        SharedPreferences.Editor editorUrl = preferences.edit();
        editorUrl.putString(SAVE_URL , url);
        editorUrl.apply();
    }

    public String getSaveUrl(){
        return preferences.getString(SAVE_URL , "");
    }

    public void setIslogin(boolean islogin){
        SharedPreferences.Editor  editorIsLogin = preferences.edit();
        editorIsLogin.putBoolean(ISLOGIN , islogin);
        editorIsLogin.apply();
    }

    public boolean getIsLogin(){
        return preferences.getBoolean(ISLOGIN , false);
    }

    public String getUnitMoney(){
        return preferences.getString(UNIT_MONEY , "ریال");
    }

    public void saveUnitMoney(String unitMoney){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(UNIT_MONEY , unitMoney);
        editor.apply();
    }

    public void saveUser(User user){
        if (user != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(USER_NAME , user.getUserName());
            editor.putString(USER_CODE , user.getUserCode());
            editor.apply();
        }
    }
    public User getUser(){
        User user = new User();
        user.setUserName(preferences.getString(USER_NAME, ""));
        user.setUserCode(preferences.getString(USER_CODE, ""));
        return user;
    }
}
