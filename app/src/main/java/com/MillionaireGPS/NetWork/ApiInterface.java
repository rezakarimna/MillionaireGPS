package com.MillionaireGPS.NetWork;

import com.MillionaireGPS.Location;
import com.google.gson.JsonObject;
import com.MillionaireGPS.Login.User;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

   /* @POST("getManagmentReportBuy")
    Call<List<Buy>> getListBuy(@Body JsonObject object);

    @POST("getManagmentReportsale")
    Call<List<Sale>> getListSale(@Body JsonObject object);

    @POST("getManagmentReportMoresale")
    Call<List<MuchSale>> getListMuchSale(@Body JsonObject object);*/

    /*@GET("getListUsers")
    Call<List<User>> getUser(@Query("codek") int codek);*/

    @POST("Get_AppVisitor_visitor")
    Call<List<User>> getUser(@Body JsonObject object);

    @POST("getlocation")
    Call<List<Location>> getlocation(@Body JsonObject object);

}
