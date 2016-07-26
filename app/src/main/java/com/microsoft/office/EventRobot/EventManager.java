package com.microsoft.office.EventRobot;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ricardol on 7/25/2016.
 */
public class EventManager implements IEventProvider {
    private Context mContext;
    private HashMap<String,String> mEventValues;
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
                        JsonObject result = (JsonObject) new JsonParser().parse(response).getAsJsonObject().getAsJsonArray("value").get(0);
                        callback.onSuccess(result);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + AuthenticationManager.getInstance().getAccessToken());
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    @Override
    public String convertToAssistContent(JsonObject microsoftEvent){

        String startDate = null;
        String locationName = null;
        String locationAddressStreet = null;
        String locationAddressCity = null;
        String locationAddressState = null;
        String locationAddressPostalCode = null;
        String locationAddressCountry = null;
        String organizerName = null;

        try {


            Object thing = microsoftEvent.get("organizer");
            if (thing.getClass().equals(JsonObject.class)) {
                organizerName = microsoftEvent
                        .getAsJsonObject("organizer")
                        .getAsJsonObject("emailAddress")
                        .getAsJsonPrimitive("name")
                        .toString();
                // .getAsString();
            }

            thing = microsoftEvent.get("start");
            if (thing.getClass().equals(JsonObject.class)) {
                startDate = microsoftEvent
                        .getAsJsonObject("start")
                        .getAsJsonPrimitive("dateTime")
                        .getAsString();
            }


            thing = microsoftEvent.get("location");
            if (thing.getClass().equals(JsonObject.class)) {
                JsonObject location = microsoftEvent.getAsJsonObject("location");
                locationName = location
                        .getAsJsonPrimitive("displayName")
                        .getAsString();
                JsonObject physicalAddress = location.getAsJsonObject("address");
                locationAddressStreet = physicalAddress
                        .getAsJsonPrimitive("street")
                        .getAsString();

                locationAddressCity = physicalAddress
                        .getAsJsonPrimitive("city").getAsString();

                locationAddressState = physicalAddress
                        .getAsJsonPrimitive("state").getAsString();

                locationAddressPostalCode = physicalAddress
                        .getAsJsonPrimitive("postalCode").getAsString();

                locationAddressCountry = physicalAddress
                        .getAsJsonPrimitive("countryOrRegion").getAsString();

            }
        } catch (NullPointerException e){
            Log.e("MockEventManager","Null pointer" + e.getMessage());
        }
        return  "{\n" +
                "  \"@context\": \"http://schema.org\",\n" +
                "  \"@type\": \"EventReservation\",\n" +
                "  \"reservationNumber\":\"E123456789\",\n" +
                "  \"reservationStatus\": \"http://schema.org/Confirmed\",\n" +
                "  \"underName\": {\n" +
                "    \"@type\": \"Person\",\n" +
                "    \"name\":"+organizerName+"\n" +
                "  },\n" +
                "  \"reservationFor\": {\n" +
                "    \"@type\": \"Event\",\n" +
                "    \"name\":"+ microsoftEvent.get("subject").getAsString()+",\n" +
                "    \"startDate\":"+startDate+",\n" +
                "    \"location\": {\n" +
                "      \"@type\": \"Place\",\n" +
                "      \"name\":"+locationName+",\n" +
                "      \"address\": {\n" +
                "        \"@type\": \"PostalAddress\",\n" +
                "        \"streetAddress\":" +locationAddressStreet+",\n" +
                "        \"addressLocality\":"+locationAddressCity+",\n" +
                "        \"addressRegion\":"+locationAddressState+",\n" +
                "        \"postalCode\":" +locationAddressPostalCode+ ",\n" +
                "        \"addressCountry\":"+locationAddressCountry+"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

    }

    @Override
    public HashMap<String, String> getEventValues() {
        EventConstants eventConstants = new EventConstants();
        if (mEventValues == null){
            mEventValues = new HashMap<String, String>();
            mEventValues.put(eventConstants.EVENT_RESERVATION_NUMBER,"123");
            mEventValues.put(eventConstants.EVENT_RESERVATION_STATUS,"Confirmed");
            mEventValues.put(eventConstants.EVENT_SUBJECT,"Discuss vacation");
            mEventValues.put(eventConstants.EVENT_DETAIL,"Vacation plans for the group");
            mEventValues.put(eventConstants.EVENT_UNDER_NAME,"John Austin");
            mEventValues.put(eventConstants.EVENT_START_DATE,"2014-01-01T00:00:00Z");
            mEventValues.put(eventConstants.EVENT_LOCATION_NAME,"Seattle Center");
            mEventValues.put(eventConstants.EVENT_LOCATION_STREET,"1234 Easy Street");
            mEventValues.put(eventConstants.EVENT_LOCATION_CITY,"Seattle");
            mEventValues.put(eventConstants.EVENT_LOCATION_STATE,"Wa");
            mEventValues.put(eventConstants.EVENT_LOCATION_POSTAL_CODE,"98433");
        }
        return mEventValues;
    }

}