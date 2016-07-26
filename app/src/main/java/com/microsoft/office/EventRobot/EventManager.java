package com.microsoft.office.EventRobot;

import android.content.Context;
import org.joda.time.DateTime;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ricardol on 7/25/2016.
 */
public class EventManager implements IEventProvider {
    private Context mContext;
    static String EVENT_ENDPOINT = "https://graph.microsoft.com/v1.0/me/calendarview?$top=1&$orderby=start/datetime"
            + "&StartDateTime=" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date())
            + "&EndDateTime=" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(DateTime.now().plusDays(1).toDate());

    public EventManager(Context context) {
        mContext = context;
    }

    @Override
    public void getNextEvent(final ICallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, EVENT_ENDPOINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JsonObject result = (JsonObject)new JsonParser().parse(response).getAsJsonObject().getAsJsonArray("value").get(0);
                        callback.onSuccess(result);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + AuthenticationManager.getInstance().getAccessToken());
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }
}
