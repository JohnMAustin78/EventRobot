package com.microsoft.office.EventRobot.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by johnau on 7/27/2016.
 */
public class EventRobot extends Application
        implements Application.OnProvideAssistDataListener{

    public EventRobot(){
        registerOnProvideAssistDataListener(this);
    }
    @Override
    public void onProvideAssistData(Activity activity, Bundle bundle) {

    }
}
