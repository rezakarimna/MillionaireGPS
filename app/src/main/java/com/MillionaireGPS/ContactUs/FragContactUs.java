package com.MillionaireGPS.ContactUs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.MillionaireGPS.FragMain;
import com.MillionaireGPS.R;
import com.MillionaireGPS.Setting.Setting;
import com.MillionaireGPS.Setting.mAnimation;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragContactUs extends Fragment {

    private String name;
    private ImageView imgBack;
    public FragContactUs() {
        // Required empty public constructor
    }

    View parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.frag_contact_us, container, false);
        this.parent = parent;
        initView();
        setBundel();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                Setting.setFragmentWidthBack(new FragMain() , getContext() , name);
            }
        });
        return parent;
    }
    private void initView() {
        imgBack = parent.findViewById(R.id.imgBack);

    }

    private void setBundel() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            name = bundle.getString("UserName", "");

        }
    }
}
