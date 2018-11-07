package com.troisado.topquizz.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ADO - TopQuizz on 2018-08-19
 */
public class User implements Parcelable {

    private String mPseudo;

    public User(String pseudo) {
        mPseudo = pseudo;
    }

    protected User(Parcel in) {
        mPseudo = in.readString();
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

    public String getPseudo() {
        return mPseudo;
    }

    public void setPseudo(String pseudo) {
        mPseudo = pseudo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPseudo);
    }
}
