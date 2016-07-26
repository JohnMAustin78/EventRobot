package com.microsoft.office.EventRobot;

import com.google.gson.JsonObject;

import java.util.Map;

/**
 * Created by ricardol on 7/25/2016.
 */
public interface IEventProvider {
    void getNextEvent(ICallback callback);
    String convertToAssistContent(JsonObject microsoftEvent);
    Map<String,String> getEventValues();
}
