package com.example.lilly;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;


public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.application_id))
                .clientKey(getString(R.string.client_key_id))
                .server(getString(R.string.server)).build());
    }
}
