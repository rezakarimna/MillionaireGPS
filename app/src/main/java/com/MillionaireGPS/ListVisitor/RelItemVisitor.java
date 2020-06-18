package com.MillionaireGPS.ListVisitor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.MillionaireGPS.Customer.CustomerFragment;
import com.MillionaireGPS.Location;
import com.MillionaireGPS.NetWork.ApiClient;
import com.MillionaireGPS.NetWork.ApiInterface;
import com.MillionaireGPS.R;
import com.MillionaireGPS.Setting.Setting;
import com.MillionaireGPS.Setting.mAnimation;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RelItemVisitor extends RelativeLayout {
    private TextView txtVisitorName, txtVisitorCode, txtVisitorMobile, txtAsDate, txtToDate, txtShowLocationH, txtBtnLocationHistory, txtBtnListVisitorHistory;
    private RelativeLayout relVisitor, relBtnVisitorLocation;
    private ConstraintLayout constBtnShow, constShowHistory;
    List<Location> arraylist = new ArrayList<>();
    private ListVisitorFragment parent;
    private boolean isLastLocation;
    public RelItemVisitor(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_visitor, this, true);
        initView();
    }

    private void initView() {
        txtVisitorName = findViewById(R.id.txtVisitorName);
        txtVisitorCode = findViewById(R.id.txtVisitorCode);
        // txtVisitorMobile = findViewById(R.id.txtVisitorMobile);
        relVisitor = findViewById(R.id.relVisitor);
        txtBtnListVisitorHistory = findViewById(R.id.txtBtnListVisitorHistory);
        constShowHistory = findViewById(R.id.constShowHistory);
        txtBtnListVisitorHistory = findViewById(R.id.txtBtnListVisitorHistory);
        relBtnVisitorLocation = findViewById(R.id.relBtnVisitorLocation);
        txtAsDate = findViewById(R.id.txtAsDate);
        txtToDate = findViewById(R.id.txtToDate);
        txtShowLocationH = findViewById(R.id.txtShowLocationH);
    }

    public void onStart(final Location item, final ListVisitorFragment parent, final AdapterVisitor adapter, final Boolean isSelect, final int position) {
        this.parent = parent;
        txtVisitorName.setText(item.getXLocation());
        txtVisitorCode.setText(item.getCodev());

        txtAsDate.setText(Setting.getDateToday());
        txtToDate.setText(Setting.getDateToday());

        txtAsDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                Setting.setDatePicker(txtAsDate, getContext());
            }
        });
        txtToDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                Setting.setDatePicker(txtToDate, getContext());
            }
        });
        relBtnVisitorLocation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                isLastLocation = true;
                getLastLocation(setPostModel(item.getCodev() , "" , ""));

            }
        });
        txtBtnListVisitorHistory.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                adapter.setSelect(position, !isSelect);
            }
        });

        if (isSelect) {
            constShowHistory.setVisibility(VISIBLE);
        } else {
            constShowHistory.setVisibility(GONE);
        }

        txtShowLocationH.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                if (txtAsDate.length() != 10 || txtToDate.length() != 10) {
                    Toast.makeText(getContext(), "لطفا تاریخ را انتخاب کنید", Toast.LENGTH_SHORT).show();
                } else {
                    isLastLocation = false;
                    getLastLocation(setPostModel(item.getCodev() , txtAsDate.getText().toString() , txtToDate.getText().toString()));

                }
            }
        });
    }

    private JsonObject setPostModel(String codev , String date1 , String date2) {
        JsonObject Object = new JsonObject();
        Object.addProperty("codev", codev);
        Object.addProperty("date1", date1);
        Object.addProperty("date2", date2);
        Object.addProperty("time1", "");
        Object.addProperty("time2", "");
        return Object;
    }

    private void getLastLocation(JsonObject object) {
        ApiInterface apiInterface = ApiClient.getRetrofit(getContext()).create(ApiInterface.class);
        final Call<List<Location>> locationCall = apiInterface.getlocation(object);
        locationCall.enqueue(new Callback<List<Location>>() {
            List<Location> locationList = new ArrayList<>();
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (response.isSuccessful()) {
                    if ((response.body() != null ? response.body().size() : 0) == 0) {
                        Toast.makeText(getContext(), "لوکیشن یافت نشد", Toast.LENGTH_SHORT).show();
                    } else {
                        if (isLastLocation){
                            parent.sendLastLocationToMapActivity(response.body().get(response.body().size() - 1) , true , txtVisitorName.getText().toString());
                        } else {
                            locationList.addAll(response.body());
                            parent.sendListLocationToMapActivity(locationList , false, txtVisitorName.getText().toString());
                        }
                    }
                } else {
                    Toast.makeText(getContext(), response.errorBody() + "خطا2 در برقراری با سرور", Toast.LENGTH_SHORT).show();
                    Log.i("isLogin", "onResponse: " + response.errorBody());
                    Log.i("isLogin", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Log.i("isLogin", "onResponse: " + t.getMessage());
                Toast.makeText(getContext(), "خطا3 در برقراری با سرور", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
