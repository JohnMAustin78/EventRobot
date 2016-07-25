package com.microsoft.office.EventRobot;

import com.google.gson.JsonObject;
import com.microsoft.office.EventRobot.ICallback;

/**
 * Created by ricardol on 7/25/2016.
 */
public interface IEventProvider {
    void getNextEvent(ICallback callback);
}
