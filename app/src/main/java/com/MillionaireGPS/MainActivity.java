package com.MillionaireGPS;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.MillionaireGPS.AboutUs.FragAboutUs;
import com.MillionaireGPS.ContactUs.FragContactUs;
import com.MillionaireGPS.Customer.CustomerFragment;
import com.MillionaireGPS.GPS.GpsFragment;
import com.MillionaireGPS.ListVisitor.ListVisitorFragment;
import com.MillionaireGPS.Login.FragLogin;
import com.MillionaireGPS.Setting.mAnimation;
import com.MillionaireGPS.Splash.FragSplash;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    private String userName;
    private ConstraintLayout constExit;
    private FrameLayout frameCancel, frameOk;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
       // FinishFragStartFrag(new FragMain());
        FinishFragStartFrag(new FragSplash());
    }

    private void initView() {
        constExit = findViewById(R.id.constExit);
        frameOk = findViewById(R.id.frameOk);
        frameCancel = findViewById(R.id.frameCancel);
    }

    public void FinishFragStartFrag(Fragment newFragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_show, R.anim.fade_hide);
        ft.replace(R.id.relMaster, newFragment);
        ft.commit();
    }

    public void FinishFragStartFrag_ShareElement_NoBack(Fragment newFragment, List<View> views, int transId) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            newFragment.setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(transId));

            for (int i = 0; i < views.size(); i++)
                ft.addSharedElement(views.get(i), views.get(i).getTransitionName());
            ft.replace(R.id.relMaster, newFragment);
            ft.commit();
        } else {
            ft.replace(R.id.relMaster, newFragment);
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment newFragment = fm.findFragmentById(R.id.relMaster);

        if (newFragment instanceof FragAboutUs || newFragment instanceof FragContactUs || newFragment instanceof GpsFragment) {
            setFragmentWithBundle(new FragMain(), userName);
        }
        if (newFragment instanceof ListVisitorFragment || newFragment instanceof CustomerFragment) {
            setFragmentWithBundle(new GpsFragment(), userName);
        }

        if (newFragment instanceof FragMain || newFragment instanceof FragLogin) {
            constExit.setVisibility(View.VISIBLE);
            frameOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnimation.PressClick(v);
                    Toast.makeText(MainActivity.this, "با آرزوی موفقیت بیشتر شرکت میلیونر", Toast.LENGTH_SHORT).show();
                    finishAffinity();
                }
            });
            frameCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnimation.PressClick(v);
                    constExit.setVisibility(View.GONE);
                }
            });

        }
    }

    public void setFragmentWithBundle(Fragment newFragment, String name) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        newFragment.setArguments(bundle);
        userName = name;
        ft.setCustomAnimations(R.anim.fade_show, R.anim.fade_hide, R.anim.fade_show, R.anim.fade_hide);
        ft.addToBackStack(newFragment.getClass().getSimpleName());
        ft.replace(R.id.relMaster, newFragment);
        ft.commit();

    }
}
