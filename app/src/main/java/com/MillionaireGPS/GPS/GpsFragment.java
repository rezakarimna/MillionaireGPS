package com.MillionaireGPS.GPS;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.MillionaireGPS.Customer.CustomerFragment;
import com.MillionaireGPS.FragMain;
import com.MillionaireGPS.ListVisitor.ListVisitorFragment;
import com.MillionaireGPS.Location;
import com.MillionaireGPS.NetWork.ApiClient;
import com.MillionaireGPS.NetWork.ApiInterface;
import com.MillionaireGPS.R;
import com.MillionaireGPS.Setting.SendData;
import com.MillionaireGPS.Setting.Setting;
import com.MillionaireGPS.Setting.mAnimation;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GpsFragment extends Fragment {
    private RelativeLayout relBtnListVisitor, relBtnCustomer;
    private ImageView imgBack;
    private String name;
    View parent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_gps, container, false);
        this.parent = parent;
        initView();
        setBundle();
        relBtnListVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                SendData.newInstance().setListVisitor(true);
                Setting.setFragmentWidthBack(new ListVisitorFragment(), getContext(), name);
            }
        });
       /* relBtnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                SendData.newInstance().setListVisitor(false);
                Setting.setFragmentWidthBack(new CustomerFragment(), getContext(), name);
            }
        });*/
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                Setting.setFragmentWidthBack(new FragMain(), getContext(), name);
            }
        });
        return parent;
    }

    private void setBundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            name = bundle.getString("UserName", "");

        }
    }

    private void initView() {
        relBtnListVisitor = parent.findViewById(R.id.relBtnListVisitor);
      //  relBtnCustomer = parent.findViewById(R.id.relBtnCustomer);
        imgBack = parent.findViewById(R.id.imgBack);
    }


}
