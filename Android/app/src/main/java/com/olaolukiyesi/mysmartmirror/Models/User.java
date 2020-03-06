package com.olaolukiyesi.mysmartmirror.Models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class User implements Parcelable {
    protected User(Parcel in) {
        userID = in.readString();
        verified = in.readByte() != 0;
        email = in.readString();
        userSettings = in.readParcelable(new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                return super.loadClass(name);
            }
        });

    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserID() {
        return userID;
    }

    public boolean isVerified() {
        return verified;
    }

    public String getEmail() {
        return email;
    }

    public User(String userID, String email) {
        this.userID = userID;
        this.email = email;
        this.verified = false;
        this.userSettings = new Settings();
    }
    public User(){

    }

    public User(String userID, boolean verified, String email, HashMap userSettings) {
        this.userID = userID;
        this.verified = verified;
        this.email = email;
        this.userSettings = Settings.MapSettings(userSettings);
    }

    String userID;
    boolean verified;
    String email;

    public void setUserSettings(Settings userSettings) {
        this.userSettings = userSettings;
    }

    Settings userSettings;

    public Settings getUserSettings() {
        return userSettings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.userID);
        dest.writeParcelable(this.userSettings,PARCELABLE_WRITE_RETURN_VALUE);

    }
}

