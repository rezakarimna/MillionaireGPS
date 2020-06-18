package com.MillionaireGPS;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Location implements Parcelable {

    @SerializedName("Codev")
    String codev ;
    @SerializedName("XLocation")
    String XLocation ;
    @SerializedName("YLocation")
    String YLocation ;
    @SerializedName("time")
    String time ;
    @SerializedName("date")
    String date ;

    protected Location(Parcel in) {
        codev = in.readString();
        XLocation = in.readString();
        YLocation = in.readString();
        time = in.readString();
        date = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    public String getCodev() {
        return codev;
    }

    public void setCodev(String codev) {
        this.codev = codev;
    }

    public String getXLocation() {
        return XLocation;
    }

    public void setXLocation(String XLocation) {
        this.XLocation = XLocation;
    }

    public String getYLocation() {
        return YLocation;
    }

    public void setYLocation(String YLocation) {
        this.YLocation = YLocation;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(codev);
        dest.writeString(XLocation);
        dest.writeString(YLocation);
        dest.writeString(time);
        dest.writeString(date);
    }
}