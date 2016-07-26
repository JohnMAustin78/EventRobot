package com.microsoft.office.EventRobot;

import com.google.gson.JsonObject;

/**
 * Created by ricardol on 7/25/2016.
 */
public interface IEventProvider {
    void getNextEvent(ICallback callback);
    String convertToAssistContent(JsonObject microsoftEvent);
}
