package com.MillionaireGPS.Setting;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.MillionaireGPS.R;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Setting {
    public static String unitMoney , TodayDate = null;
    public static long time, customerCode;

    public static String GetUrl() {
        return "http://www.mashhadpasargad.ir/Image/";
    }

    public static String GetMoney() {
        return "ریال ";
    }

    public static String ServerError() {
        return "خطا در برقراری اتصال";
    }

    public static Typeface IranSensTypeBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/iran_bold.ttf");
    }

    public static Typeface IranSensType(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/iran.ttf");
    }

    public static void DefaultFont(TextView txt) {
        Typeface typeFace = Typeface.DEFAULT_BOLD;
        txt.setTypeface(typeFace);
    }

    public static boolean checkMobile(String strMobile, TextView txtError) {
        if (strMobile.length() < 11) {
            txtError.setVisibility(View.VISIBLE);
            txtError.setText("شماره تلفن خود را به صورت صحیح وارد کنید!");
            mAnimation.Viberation(txtError);
            return false;
        }
        if (!strMobile.startsWith("09")) {
            txtError.setVisibility(View.VISIBLE);
            txtError.setText("شماره تلفن همراه باید با 09 شروع شود");
            mAnimation.Viberation(txtError);
            return false;
        }
        return true;
    }

    public static boolean checkName(String strName, TextView txtError) {
        if (strName.length() < 2) {
            txtError.setVisibility(View.VISIBLE);
            txtError.setText("نام خود را به صورت صحیح وارد کنید!");
            mAnimation.Viberation(txtError);
            return false;
        }
        return true;
    }

    public static boolean checkPass(String strPass, TextView txtError) {
        if (strPass.length() < 4) {
            txtError.setVisibility(View.VISIBLE);
            txtError.setText("کد وارد شده صحیح نیست");
            mAnimation.Viberation(txtError);
            return false;
        }
        return true;
    }

    public static void OnRequestError(String strError, TextView txtError) {
        txtError.setVisibility(View.VISIBLE);
        txtError.setText(strError);
        mAnimation.Viberation(txtError);
    }

    public static void shareText(Context context, String subject, String body) {
        Intent txtIntent = new Intent(Intent.ACTION_SEND);
        txtIntent.setType("text/plain");
        txtIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        txtIntent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(txtIntent, "Share"));
    }

    public static long getTime() {
        long time;
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:30"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");
        date.setTimeZone(TimeZone.getTimeZone("GMT+3:30"));

        String localTime = date.format(currentLocalTime);
        if (cal.get(Calendar.HOUR) == 0)
            time = ((12 * 60) + cal.get(Calendar.MINUTE));
        else
            time = ((cal.get(Calendar.HOUR)) * 60) + cal.get(Calendar.MINUTE);
        Log.i("time2", "time9: " + cal.get(Calendar.HOUR) + " m " + cal.get(Calendar.MINUTE) + " localTime " + localTime + " time " + time);
        return time;
    }

    public static void setFragment(Fragment newFragment, Context context) {
        FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        newFragment.setArguments(bundle);
        ft.setCustomAnimations(R.anim.fade_show, R.anim.fade_hide);
        ft.replace(R.id.relMaster, newFragment);
        ft.commit();
    }

    public static void setFragmentWidthBack(Fragment newFragment, Context context, String UserName) {
        FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("UserName", UserName);
        newFragment.setArguments(bundle);
        ft.setCustomAnimations(R.anim.fade_show, R.anim.fade_hide, R.anim.fade_show, R.anim.fade_hide);
        ft.addToBackStack(newFragment.getClass().getSimpleName());
        ft.replace(R.id.relMaster, newFragment);
        ft.commit();
    }

    /* متد برای نمایش صحیح یا اعشاری تعداد محصولات در تکس ویو*/
    public static void setViewFloatLong(float value, TextView view, boolean isMoney , boolean isUnit) {
        long valueL = (long) value;
        float valueF = value;
        if (isMoney) {
            if (valueF == valueL) {
                if(isUnit){
                    view.setText(SplitText.GetOKPrice(valueL + "") + Setting.GetMoney());
                } else {
                    view.setText(SplitText.GetOKPrice(valueL + ""));
                }
            } else {
                if(isUnit){
                    view.setText(SplitText.GetOKPrice(valueF + "") + Setting.GetMoney());
                } else {
                    view.setText(SplitText.GetOKPrice(valueF + ""));
                }

            }
        } else {
            if (valueF == valueL) {
                view.setText(valueL + "");
            } else {
                view.setText(valueF + "");
            }
        }

    }

    public static void ShowAlertDialog(String msg, Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(msg).setCancelable(true);
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    public static void setDatePicker(final TextView textView, Context context) {
        DatePickerDialog dialog = new DatePickerDialog();
        dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                NumberFormat formatter = new DecimalFormat("00");
                String date = String.valueOf(year) + "/" +
                        formatter.format(monthOfYear + 1) + "/" +
                        formatter.format(dayOfMonth);
                String[] date1 = date.split("/");
             //  String finalDate = String.format(Locale.US, "%s/%s/%s", farsi(date1[0]), farsi(date1[1]), farsi(date1[2]));
               String finalDate = String.format(Locale.US, "%s/%s/%s", date1[0], date1[1], date1[2]);
                textView.setText(finalDate);
            }
        });
        FragmentManager fm = ((AppCompatActivity) context).getFragmentManager();
        dialog.setThemeDark(false);
        dialog.show(fm, "timePicker");
    }

    public static String farsi(String num) {
        char[] chars = {'۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'};
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < num.length(); i++) {
            if (Character.isDigit(num.charAt(i))) {
                stringBuilder.append(chars[(int) (num.charAt(i)) - 48]);
            }
        }
        return stringBuilder.toString();
    }

    public static String faToEn(String num) {
        return num
                .replace("۰", "0")
                .replace("۱", "1")
                .replace("۲", "2")
                .replace("۳", "3")
                .replace("۴", "4")
                .replace("۵", "5")
                .replace("۶", "6")
                .replace("۷", "7")
                .replace("۸", "8")
                .replace("۹", "9");
    }

    public static String getDateToday() {
        PersianCalendar now = new PersianCalendar();
        NumberFormat formatter = new DecimalFormat("00");
        String date = String.valueOf(now.getPersianYear()) + "/" +
                formatter.format(now.getPersianMonth() + 1) + "/" +
                formatter.format(now.getPersianDay());
        String[] date1 = date.split("/");
        TodayDate = String.format(Locale.US, "%s/%s/%s", date1[0], date1[1], date1[2]);

        return TodayDate;
    }

    public void setAnimation(final View showLayout , final View hideLayout , Context context){
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.anim.card_flip_left_in);
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

    public static void startFragment(Fragment newFragment, Context context) {
        FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_show, R.anim.fade_hide);
        ft.replace(R.id.relMaster, newFragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
        //   ft.commit();
    }

}
