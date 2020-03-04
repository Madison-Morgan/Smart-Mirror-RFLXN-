package com.olaolukiyesi.mysmartmirror.Models;


import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    protected User(Parcel in) {
        userID = in.readString();
        verified = in.readByte() != 0;
        email = in.readString();
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
    }
    public User(){

    }

    String userID;
    boolean verified;
    String email;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.userID);

    }
}

