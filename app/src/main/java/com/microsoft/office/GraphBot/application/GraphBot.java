package com.microsoft.office.GraphBot.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by johnau on 7/27/2016.
 */
public class GraphBot extends Application
        implements Application.OnProvideAssistDataListener{

    public GraphBot(){
        registerOnProvideAssistDataListener(this);
    }
    @Override
    public void onProvideAssistData(Activity activity, Bundle bundle) {

    }
}
