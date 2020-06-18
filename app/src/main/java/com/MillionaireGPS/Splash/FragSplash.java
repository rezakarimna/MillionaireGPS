package com.MillionaireGPS.Splash;


import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.MillionaireGPS.FragMain;
import com.MillionaireGPS.Login.FragLogin;
import com.MillionaireGPS.MainActivity;
import com.MillionaireGPS.R;
import com.MillionaireGPS.Setting.Setting;
import com.MillionaireGPS.Setting.SharedPrefManage;
import com.MillionaireGPS.Setting.mAnimation;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragSplash extends Fragment {
    private ConstraintLayout constLoading , constUrl;
    private CircleImageView imgLogoUrl , imgLogo;
    private EditText editUrl;
    private Button btnSaveUrl;
    private ProgressBar progLoading;
    private Handler handler = new Handler();
    private SharedPrefManage sharedPref;

    View parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View parent = inflater.inflate(R.layout.frag_splash, container, false);
        this.parent = parent;
        initView();
        sharedPref = new SharedPrefManage(getContext());
        if (sharedPref.getIsLogin()) {
            constLoading.setVisibility(View.VISIBLE);
            constUrl.setVisibility(View.GONE);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Setting.setFragment(new FragMain(), getContext());
                }
            },2000);
        } else {
            constLoading.setVisibility(View.GONE);
            constUrl.setVisibility(View.VISIBLE);

        }
        btnSaveUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                if (editUrl.length() < 1) {
                    Toast.makeText(getContext(), "هیچ ادرسی وارد نشده است", Toast.LENGTH_SHORT).show();
                } else if (URLUtil.isValidUrl(editUrl.getText() + "/")) {
                    sharedPref.setSaveUrl(editUrl.getText() + "/");
                    List<View> views = new ArrayList<>();
                    views.add(parent.findViewById(R.id.imgLogo));
                    ((MainActivity) getActivity()).FinishFragStartFrag_ShareElement_NoBack(new FragLogin(), views, R.transition.transition_bound);
                } else
                    Toast.makeText(getContext(), "آدرس وارد شده نا معتبر می باشد", Toast.LENGTH_SHORT).show();

            }

        });

        return parent;
    }

    private void initView(){
        constLoading = parent.findViewById(R.id.constLoading);
        constUrl = parent.findViewById(R.id.constUrl);
        imgLogoUrl = parent.findViewById(R.id.imgLogoUrl);
        imgLogo = parent.findViewById(R.id.imgLogo);
        editUrl = parent.findViewById(R.id.editUrl);
        btnSaveUrl = parent.findViewById(R.id.btnSaveUrl);
        progLoading = parent.findViewById(R.id.progLoading);
    }

}
