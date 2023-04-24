package com.example.lilly.models;

import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseUser;

@ParseClassName("_User")
public class User extends ParseUser implements Parcelable {
    public static final String KEY_FIRST_NAME = "firstName";
    public static final String KEY_LAST_NAME = "lastName";
    public static final String KEY_PROFILE_IMAGE = "profile_image";

//    public ParseFile getProfileImage() {
//        return getParseFile(KEY_PROFILE_IMAGE);
//    }
//    public void setProfileImage(ParseFile image) {
//        put(KEY_PROFILE_IMAGE, image);
//    }
    public void setKeyFirstName(String firstName)
    {
        put(KEY_FIRST_NAME,firstName);
    }
    public void setKeyLastName(String lastName)
    {
        put(KEY_LAST_NAME,lastName);
    }
    public String getKeyFirstName() {
        return getString(KEY_FIRST_NAME);
    }
}
