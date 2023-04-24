package com.example.lilly.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Info {

    public String title;
    public String data;

    public Info(String t, String d) {
        title = t;
        data = d;

    }

    public String getData(){
        return data;
    }

    public String getTitle() {
        return title;
    }


}

