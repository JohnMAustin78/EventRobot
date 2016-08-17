package com.microsoft.office.GraphBot;

import com.google.gson.JsonObject;

/**
 * Created by ricardol on 7/25/2016.
 */
public interface ICallback {
    void onSuccess(JsonObject result);
    void onFailure(Exception e);
}
