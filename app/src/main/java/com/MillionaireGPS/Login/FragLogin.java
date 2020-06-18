package com.MillionaireGPS.Login;


import android.gesture.Prediction;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MillionaireGPS.FragMain;
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
public class FragLogin extends Fragment {

    private TextView txtBtnLogin, txtError;
    private ProgressBar progLogin;
    private RelativeLayout relBtnLogin;
    private EditText editUserName, editPass;
    private SharedPrefManage prefManage;

    public FragLogin() {
        // Required empty public constructor
    }

    View parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.frag_login, container, false);
        this.parent = parent;
        initView();
        prefManage = new SharedPrefManage(getContext());
        relBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation.PressClick(v);
                txtError.setVisibility(View.GONE);
                if (editUserName.length() <= 0 || editPass.length() <= 0) {
                    txtError.setVisibility(View.VISIBLE);
                    mAnimation.Viberation(txtError);
                } else {
                    progLogin.setVisibility(View.VISIBLE);
                    txtBtnLogin.setVisibility(View.GONE);
                   // setLogin(Integer.parseInt(editUserName.getText().toString()), Long.parseLong(editPass.getText().toString()));
                    setLogin(setPostModel());
                    //   getPoints(setPostModel());
                }
            }
        });
        return parent;
    }

    private void initView() {
        editUserName = parent.findViewById(R.id.editUserName);
        editPass = parent.findViewById(R.id.editUserPass);
        relBtnLogin = parent.findViewById(R.id.relBtnLogin);
        txtBtnLogin = parent.findViewById(R.id.txtBtnLogin);
        progLogin = parent.findViewById(R.id.progLogin);
        txtError = parent.findViewById(R.id.txtError);

    }

/*    private void setLogin(final int userCode, final long pass) {
        ApiInterface apiInterface = ApiClient.getRetrofit(getContext()).create(ApiInterface.class);
        final Call<List<User>> userCall = apiInterface.getUser(userCode);
        userCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                User user = new User();

                if (response.isSuccessful()) {
                    if ((response.body() != null ? response.body().size() : 0) == 0) {
                        Toast.makeText(getContext(), "کاربری با این مشخصات وجود ندارد", Toast.LENGTH_SHORT).show();
                        progLogin.setVisibility(View.GONE);
                        txtBtnLogin.setVisibility(View.VISIBLE);
                    } else {
                        if (pass == response.body().get(0).getUserPass()) {
                            user = response.body().get(0);
                            Setting.unitMoney = user.getUnitMoney();
                            Setting.setFragmentWidthBack(new FragMain(), getContext(), user.getUserName());
                            prefManage.setIslogin(true);
                            progLogin.setVisibility(View.GONE);
                            txtBtnLogin.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getContext(), "نام کاربری یا رمز عبور شما اشتباه می باشد", Toast.LENGTH_SHORT).show();
                            progLogin.setVisibility(View.GONE);
                            txtBtnLogin.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "خطا2 در برقراری با سرور", Toast.LENGTH_SHORT).show();
                    progLogin.setVisibility(View.GONE);
                    txtBtnLogin.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.i("isLogin", "onResponse: " + t.getMessage());
                Toast.makeText(getContext(), "خطا3 در برقراری با سرور", Toast.LENGTH_SHORT).show();
                progLogin.setVisibility(View.GONE);
                txtBtnLogin.setVisibility(View.VISIBLE);

            }
        });
    }*/

    private JsonObject setPostModel() {
        JsonObject Object = new JsonObject();
        Object.addProperty("codev", editUserName.getText().toString());
        Object.addProperty("passv", editPass.getText().toString());
        Object.addProperty("Ime", editUserName.getText().toString());
        // Object.addProperty("Ime", Ime);
        Object.addProperty("Typev", "0");

        return Object;
    }
    private void setLogin(JsonObject object) {
        ApiInterface apiInterface = ApiClient.getRetrofit(getContext()).create(ApiInterface.class);
        final Call<List<User>> userCall = apiInterface.getUser(object);
        userCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = new ArrayList<>();
                if (response.isSuccessful()) {
                    if ((response.body() != null ? response.body().size() : 0) == 0) {
                        txtError.setText("کاربری با این مشخصات وجود ندارد");
                        mAnimation.Viberation(txtError);
                        progLogin.setVisibility(View.GONE);
                        txtBtnLogin.setVisibility(View.VISIBLE);
                    } else {
                      //  AppDatabase.getInstance(getContext()).getUserDA().deleteAll();
                        for (int i = 0; i < response.body().size(); i++) {
                            // users.add(new User(editUserName.getText().toString() , editUserName.getText().toString()));
                            //users.add(new User(response.body().get(i).getUserName(),response.body().get(i).getUserCode()));
                         //   AppDatabase.getInstance(getContext()).getUserDA().SetInsert(users.get(0));
                            prefManage.saveUser(response.body().get(0));
                        }
                        Log.i("infoUSer", "onCreateView: "+ response.body().get(0).getUserName() + response.body().get(0).getUserCode());
                        prefManage.setIslogin(true);
                        Setting.setFragmentWidthBack(new FragMain(), getContext(), response.body().get(0).getUserName());
                        progLogin.setVisibility(View.GONE);
                        txtBtnLogin.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getContext(), response.errorBody() +"خطا2 در برقراری با سرور", Toast.LENGTH_SHORT).show();
                    progLogin.setVisibility(View.GONE);
                    txtBtnLogin.setVisibility(View.VISIBLE);
                    Log.i("isLogin", "onResponse: " + response.errorBody());
                    Log.i("isLogin", "onResponse: " + response.message());


                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.i("isLogin", "onResponse: " + t.getMessage());
                Toast.makeText(getContext(), "خطا3 در برقراری با سرور", Toast.LENGTH_SHORT).show();
                progLogin.setVisibility(View.GONE);
                txtBtnLogin.setVisibility(View.VISIBLE);

            }
        });
    }

}
