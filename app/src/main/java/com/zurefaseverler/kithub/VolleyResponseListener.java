package com.zurefaseverler.kithub;

import org.json.JSONException;

public interface VolleyResponseListener {
    void onResponse(String response) throws JSONException;
}
