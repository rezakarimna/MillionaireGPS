package com.MillionaireGPS;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.MillionaireGPS.AboutUs.FragAboutUs;
import com.MillionaireGPS.ContactUs.FragContactUs;
import com.MillionaireGPS.GPS.GpsFragment;
import com.MillionaireGPS.Login.User;
import com.MillionaireGPS.Setting.Setting;
import com.MillionaireGPS.Setting.SharedPrefManage;
import com.MillionaireGPS.Setting.mAnimation;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragMain extends Fragment {

    private RelativeLayout relBtnContactUs, relBtnAboutUs, relBtnGpsFragment;
    private String name;
    private TextView txtNameUser;
    private SharedPrefManage prefManage;
    User user = new User();
    View parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View parent = inflater.inflate(R.layout.frag_main, container, false);
        this.parent = parent;
        initView();
        setBundle();
        prefManage = new SharedPrefManage(getContext());
        user = prefManage.getUser();
        txtNameUser.setText(user.getUserName());
        relBtnContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                Setting.setFragmentWidthBack(new FragContactUs(), getContext(), name);
            }
        });
        relBtnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                Setting.setFragmentWidthBack(new FragAboutUs(), getContext(), name);
            }
        });
        relBtnGpsFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                Setting.setFragmentWidthBack(new GpsFragment(), getContext(), name);
            }
        });

        return parent;
    }

    private void initView() {
        relBtnContactUs = parent.findViewById(R.id.relBtnContactUs);
        relBtnAboutUs = parent.findViewById(R.id.relBtnAboutUs);
        relBtnGpsFragment = parent.findViewById(R.id.relBtnGpsFragment);
        txtNameUser = parent.findViewById(R.id.txtNameUser);
    }

    private void setBundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            name = bundle.getString("UserName", "");

        }
    }
}
