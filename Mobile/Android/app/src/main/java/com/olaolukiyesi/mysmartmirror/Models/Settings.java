package com.olaolukiyesi.mysmartmirror.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;

public class Settings implements Parcelable, Serializable {
    public boolean showTime;
    public boolean showDate;
    public boolean showWeather;
    public static Settings MapSettings(HashMap settings){
        Settings result = new Settings();
        if(settings.containsKey("showTime")){
            result.showTime = (boolean)settings.get("showTime");
        }
        if(settings.containsKey("showDate")){
            result.showTime = (boolean)settings.get("showDate");
        }
        if(settings.containsKey("showWeather")){
            result.showTime = (boolean)settings.get("showWeather");
        }

        return result;
    }
    public Settings(){
        showTime = true;
        showDate = true;
        showWeather = true;

    }

    protected Settings(Parcel in) {
        showTime = in.readByte() != 0;
        showDate = in.readByte() != 0;
        showWeather = in.readByte() != 0;
    }

    public static final Creator<Settings> CREATOR = new Creator<Settings>() {
        @Override
        public Settings createFromParcel(Parcel in) {
            return new Settings(in);
        }

        @Override
        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (showTime ? 1 : 0));
        dest.writeByte((byte) (showDate ? 1 : 0));
        dest.writeByte((byte) (showWeather ? 1 : 0));
    }
}
