package com.MillionaireGPS.Customer;


import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
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
import com.MillionaireGPS.ListVisitor.AdapterVisitor;
import com.MillionaireGPS.ListVisitor.Visitors;
import com.MillionaireGPS.Maps.MapActivity;
import com.MillionaireGPS.R;
import com.MillionaireGPS.Setting.Setting;
import com.MillionaireGPS.Setting.mAnimation;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerFragment extends Fragment {
    private TextView btnSendInfo, toDate, asDate, txtError;
    private ProgressBar progressBar;
    private RecyclerView recyclerCustomer;
    private RelativeLayout relGetDate, relError, relMainCustomerSearch;
    private EditText editCustomerName;
    private ImageView imgCloseSearch, imgBack;
    private String customerCode, customerName, name;

    public CustomerFragment() {
        // Required empty public constructor
    }

    View parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View parent = inflater.inflate(R.layout.fragment_customer, container, false);
        this.parent = parent;
        initView();
        setBundle();
        asDate.setText(Setting.getDateToday());
        toDate.setText(Setting.getDateToday());
        asDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setDatePicker(asDate);
                Setting.setDatePicker(asDate, getContext());
            }
        });
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setDatePicker(toDate);
                Setting.setDatePicker(toDate, getContext());
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                Setting.setFragmentWidthBack(new FragMain(), getContext(), name);
            }
        });
        btnSendInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                if (toDate.length() != 10 || asDate.length() != 10 || editCustomerName.length() == 0) {
                    Toast.makeText(getContext(), "اطلاعات را کامل وارد کنید", Toast.LENGTH_SHORT).show();
                } else {
                    //  customerCode = AppDatabase.getInstance(getContext()).getCustomerListDA().getCode(txtCustomerName.getText().toString());
                    //   getCustomerBill(jsonObject());
                    relGetDate.setVisibility(View.GONE);
                    recyclerCustomer.setVisibility(View.VISIBLE);
                    //setRecyclerView(getItem());
                }
            }
        });

        return parent;
    }

    private void initView() {
        asDate = parent.findViewById(R.id.editAsDate);
        toDate = parent.findViewById(R.id.editToDate);
        btnSendInfo = parent.findViewById(R.id.btnSendInfo);
        progressBar = parent.findViewById(R.id.progCircel);
        recyclerCustomer = parent.findViewById(R.id.recyclerCustomer);
        relGetDate = parent.findViewById(R.id.relGetDate);
        relError = parent.findViewById(R.id.relError);
        txtError = parent.findViewById(R.id.txtError);
        editCustomerName = parent.findViewById(R.id.editCustomerName);
        imgBack = parent.findViewById(R.id.imgBack);

    }

    private void setBundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            name = bundle.getString("UserName", "");

        }

    }


    public void setAnimation(final View showLayout , final View hideLayout){
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.anim.card_flip_left_in);
        set.setTarget(showLayout); // set the view you want to animate
        set.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLayout.setVisibility(View.VISIBLE);
                showLayout.setVisibility(View.GONE);
            }
        }, 600);
    }

    public void moveToActivityMaps(boolean isLastLocation , String visitorName){
        Intent intent = new Intent(getActivity(), MapActivity.class);
        intent.putExtra("isLastLocation" ,isLastLocation);
        intent.putExtra("VisitorName" , visitorName);
        startActivity(intent);

    }
    private List<Visitors> getItem() {
        List<Visitors> visitors = new ArrayList<>();
        visitors.add(new Visitors("آدینه پور", "1071001", "09151236547"));
        visitors.add(new Visitors("هاشمی بیرجند", "1071002", "09151247896"));
        visitors.add(new Visitors("حافظیان", "1071003", "091536985214"));

        return visitors;
    }
}
