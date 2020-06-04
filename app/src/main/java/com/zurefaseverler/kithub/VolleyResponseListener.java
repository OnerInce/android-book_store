package com.zurefaseverler.kithub;

import org.json.JSONException;

import java.util.ArrayList;

public interface VolleyResponseListener {
    void onResponse(String response) throws JSONException;
}
