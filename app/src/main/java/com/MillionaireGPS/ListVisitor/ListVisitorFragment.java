package com.MillionaireGPS.ListVisitor;


import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MillionaireGPS.FragMain;
import com.MillionaireGPS.Location;
import com.MillionaireGPS.Login.User;
import com.MillionaireGPS.MainActivity;
import com.MillionaireGPS.Maps.MapActivity;
import com.MillionaireGPS.NetWork.ApiClient;
import com.MillionaireGPS.NetWork.ApiInterface;
import com.MillionaireGPS.R;
import com.MillionaireGPS.Setting.Setting;
import com.MillionaireGPS.Setting.SharedPrefManage;
import com.MillionaireGPS.Setting.mAnimation;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListVisitorFragment extends Fragment {

    private TextView txtDateLocationHistory, txtBtnLocationHistory, txtBtnListVisitor, txtnameV;
    private EditText editSearchVisitorName;
    private RecyclerView recVisitorList;
    private ImageView imgBack;
    private String name;
    private List<String> listNameVisitors = new ArrayList<>();
    private List<Location> listVisitors = new ArrayList<>();
    private RelativeLayout relError;
    private ProgressBar progCircel;
    private SharedPrefManage prefManage;
    private SwipeRefreshLayout swipeRefresh;
    User user = new User();
    View parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_list_visitor, container, false);
        this.parent = parent;
        initView();
        setBundle();
        progCircel.setVisibility(View.VISIBLE);
        prefManage = new SharedPrefManage(getContext());
        user = prefManage.getUser();


        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getVisitorList(setPostModel(user.getUserCode()));
            }
        });

        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                getVisitorList(setPostModel(user.getUserCode()));
            }
        });

        getVisitorList(setPostModel(user.getUserCode()));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                Setting.setFragmentWidthBack(new FragMain(), getContext(), name);
            }
        });

        editSearchVisitorName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productSearch(s, listVisitors);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return parent;
    }

    private void initView() {
        editSearchVisitorName = parent.findViewById(R.id.editSearchVisitorName);
        recVisitorList = parent.findViewById(R.id.recVisitorList);
        imgBack = parent.findViewById(R.id.imgBack);
        relError = parent.findViewById(R.id.relError);
        progCircel = parent.findViewById(R.id.progCircel);
        swipeRefresh = parent.findViewById(R.id.swipeRefresh);
    }

    private void setBundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            name = bundle.getString("UserName", "");

        }
    }

    private JsonObject setPostModel(String userCode) {
        JsonObject Object = new JsonObject();
        Object.addProperty("codev", userCode);
        Object.addProperty("date1", "");
        Object.addProperty("date2", "");
        Object.addProperty("time1", "00:00");
        Object.addProperty("time2", "00:00");
        return Object;
    }

    private void getVisitorList(JsonObject object) {
        ApiInterface apiInterface = ApiClient.getRetrofit(getContext()).create(ApiInterface.class);
        final Call<List<Location>> locationCall = apiInterface.getlocation(object);
        locationCall.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (response.isSuccessful()) {
                    if ((response.body() != null ? response.body().size() : 0) == 0) {
                        Toast.makeText(getActivity(), "وزیتوری یافت نشد", Toast.LENGTH_SHORT).show();
                        progCircel.setVisibility(View.GONE);
                        relError.setVisibility(View.VISIBLE);
                        swipeRefresh.setRefreshing(false);
                    } else {
                        for (int i = 0; i < response.body().size(); i++) {
                            listVisitors.add(response.body().get(i));
                        }
                        progCircel.setVisibility(View.GONE);
                        relError.setVisibility(View.GONE);
                        setRecyclerView(listVisitors);
                        swipeRefresh.setRefreshing(false);
                    }

                } else {
                    Toast.makeText(getContext(), response.errorBody() + "خطا2 در برقراری با سرور", Toast.LENGTH_SHORT).show();
                    Log.i("isLogin", "onResponse: " + response.errorBody());
                    Log.i("isLogin", "onResponse: " + response.message());
                    progCircel.setVisibility(View.GONE);
                    relError.setVisibility(View.GONE);
                    swipeRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Log.i("isLogin", "onResponse: " + t.getMessage());
                Toast.makeText(getContext(), "خطا3 در برقراری با سرور", Toast.LENGTH_SHORT).show();
                progCircel.setVisibility(View.GONE);
                relError.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
            }
        });

    }

    private void setRecyclerView(List<Location> visitorsList) {
        AdapterVisitor adapterVisitor = new AdapterVisitor(visitorsList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recVisitorList.setLayoutManager(layoutManager);
        recVisitorList.setAdapter(adapterVisitor);
        recVisitorList.setItemAnimator(new DefaultItemAnimator());
        adapterVisitor.notifyDataSetChanged();
    }

    private void productSearch(CharSequence c, List<Location> visitorsList) {
        List<Location> newvisitors = new ArrayList<>();
        if (visitorsList == null) {
            Toast.makeText(getContext(), "هیچ وزیتوری وجود ندارد", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < visitorsList.size(); i++) {
                if (visitorsList.get(i).getXLocation().contains(c)) {
                    newvisitors.add(visitorsList.get(i));
                } else {
                    setRecyclerView(visitorsList);
                }
            }
            setRecyclerView(newvisitors);
        }
    }

    public void moveToActivityMaps(boolean isLastLocation, String visitorName) {
        Intent intent = new Intent(getActivity(), MapActivity.class);
        intent.putExtra("isLastLocation", isLastLocation);
        intent.putExtra("VisitorName", visitorName);
        startActivity(intent);

    }

    void sendLastLocationToMapActivity(Location lastLocation, boolean isLastLocation, String VisitorName){
        Intent intent = new Intent(getActivity(), MapActivity.class);
        intent.putExtra("lastLocation", lastLocation);
        intent.putExtra("isLastLocation", isLastLocation);
        intent.putExtra("VisitorName", VisitorName);
        startActivity(intent);

    }
    void sendListLocationToMapActivity(List<Location> locationList, boolean isLastLocation, String VisitorName){
        Intent intent = new Intent(getActivity(), MapActivity.class);
        intent.putParcelableArrayListExtra("locationList", (ArrayList<? extends Parcelable>) locationList);
        intent.putExtra("isLastLocation", isLastLocation);
        intent.putExtra("VisitorName", VisitorName);
        startActivity(intent);
    }
}
