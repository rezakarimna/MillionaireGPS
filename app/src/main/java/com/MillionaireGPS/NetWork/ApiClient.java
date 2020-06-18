package com.MillionaireGPS.NetWork;

import android.content.Context;

import com.MillionaireGPS.Setting.SharedPrefManage;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    public static Context context;
  //  private static final String BASE_URL = "http://manag.hafezianMillionaire.ir/api/";
    private static final String BASE_URL = "http://79.127.115.207:211/datasnap/rest/TserverMethods1/";

    public static OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(45, TimeUnit.MINUTES)
            .writeTimeout(45, TimeUnit.MINUTES)
            .connectTimeout(45, TimeUnit.MINUTES)
            .build();
    public static Retrofit getRetrofit(Context context){
        if (retrofit == null) {
            SharedPrefManage sharedPref = new SharedPrefManage(context);
            retrofit = new Retrofit.Builder().baseUrl(sharedPref.getSaveUrl() + "api/").client(client)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
