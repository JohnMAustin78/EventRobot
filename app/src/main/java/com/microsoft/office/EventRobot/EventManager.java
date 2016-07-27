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
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
                        try {
                            JsonObject result = (JsonObject) new JsonParser().parse(response).getAsJsonObject().getAsJsonArray("value").get(0);
                            callback.onSuccess(result);
                        }
                        catch(Exception e){
                            Log.e("EventProvider","Exception " + e.getMessage());
                        }
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

        mEventValues = makeKVEventMap();

        String startDate = null;
        String locationName = null;
        String locationAddressStreet = null;
        String locationAddressCity = null;
        String locationAddressState = null;
        String locationAddressPostalCode = null;
        String locationAddressCountry = null;
        String organizerName = null;
        try {


            mEventValues.put(
                    EventConstants.EVENT_RESERVATION_NUMBER
                    ,microsoftEvent.get("id").toString()
                            .replace("\"",""));
            mEventValues.put(
                    EventConstants.EVENT_RESERVATION_STATUS
                    ,"Confirmed");
            mEventValues.put(
                    EventConstants.EVENT_SUBJECT
                    ,microsoftEvent.get("subject").toString()
                            .replace("\"",""));
            mEventValues.put(
                    EventConstants.EVENT_DETAIL
                    ,microsoftEvent.get("bodyPreview").toString()
                            .replace("\"",""));

            Object thing = microsoftEvent.get("organizer");
            if (thing.getClass().equals(JsonObject.class)) {
                organizerName = microsoftEvent
                        .getAsJsonObject("organizer")
                        .getAsJsonObject("emailAddress")
                        .getAsJsonPrimitive("name")
                        .toString();
                organizerName = organizerName.replace("\"","");
            }

            mEventValues.put(EventConstants.EVENT_UNDER_NAME,organizerName);

            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
            try {
            if (thing.getClass().equals(JsonObject.class)) {
                startDate = microsoftEvent
                        .getAsJsonObject("start")
                        .getAsJsonPrimitive("dateTime")
                        .getAsString();
            }

                DateTime dt = formatter.parseDateTime(startDate);
                mEventValues.put(
                        EventConstants.EVENT_START_DATE
                        ,dt.toLocalDateTime().toString("dd/MM/yyyy"));


            } catch (IllegalArgumentException e){
                Log.e("EventManger","Exception in EventManager " + e.getMessage());
            } catch (Exception ex) {
                Log.e("EventManager","Exception in EventManager " + ex.getMessage());
            }

            thing = microsoftEvent.get("location");
            if (thing.getClass().equals(JsonObject.class)) {
                JsonObject location = microsoftEvent.getAsJsonObject("location");
                locationName = location
                        .getAsJsonPrimitive("displayName")
                        .getAsString();
                mEventValues.put(
                        EventConstants.EVENT_LOCATION_NAME
                        ,locationName);


                JsonObject physicalAddress = location.getAsJsonObject("address");
                locationAddressStreet = physicalAddress
                        .getAsJsonPrimitive("street")
                        .getAsString();
                mEventValues.put(
                        EventConstants.EVENT_LOCATION_STREET,
                        locationAddressStreet);

                locationAddressCity = physicalAddress
                        .getAsJsonPrimitive("city").getAsString();

                mEventValues.put(
                        EventConstants.EVENT_LOCATION_CITY,
                        locationAddressCity);

                locationAddressState = physicalAddress
                        .getAsJsonPrimitive("state").getAsString();
                mEventValues.put(
                        EventConstants.EVENT_LOCATION_STATE
                        ,locationAddressState);

                locationAddressPostalCode = physicalAddress
                        .getAsJsonPrimitive("postalCode").getAsString();
                mEventValues.put(
                        EventConstants.EVENT_LOCATION_POSTAL_CODE
                        ,locationAddressPostalCode);


                locationAddressCountry = physicalAddress
                        .getAsJsonPrimitive("countryOrRegion").getAsString();

            }
        } catch (NullPointerException e){
            Log.e("MockEventManager","Null pointer" + e.getMessage());
        }
        return  "{\n" +
                "  \"@context\": \"http://schema.org\",\n" +
                "  \"@type\": \"EventReservation\",\n" +
                "  \"reservationNumber\":\""+microsoftEvent.get("id").toString().replace("\"","")+"\",\n" +
                "  \"reservationStatus\": \"http://schema.org/Confirmed\",\n" +
                "  \"underName\": {\n" +
                "    \"@type\": \"Person\",\n" +
                "    \"name\":\""+organizerName+"\"\n" +
                "  },\n" +
                "  \"modifiedTime\": \"2013-05-01T08:30:00-08:00\",\n" +
                "  \"modifyReservationUrl\": \"https://outlook.office365.com/owa/?realm=MOD265542.onmicrosoft.com&exsvurl=1&ll-cc=1033&modurl=1&path=/calendar/view/Day\",\n"+
                "  \"reservationFor\": {\n" +
                "    \"@type\": \"BusinessEvent\",\n" +
                "    \"name\":\""+ microsoftEvent.get("subject").getAsString()+"\",\n" +
                "    \"startDate\":\""+startDate+"\",\n" +
                "    \"performer\": {\n" +
                "       \"@type\": \"Person\",\n" +
                "        \"name\":\"George Strait\"\n" +
                "    },\n" +
                "    \"location\": {\n" +
                "      \"@type\": \"Place\",\n" +
                "      \"name\":\""+locationName+"\",\n" +
                "      \"address\": {\n" +
                "        \"@type\": \"PostalAddress\",\n" +
                "        \"streetAddress\":\"" +locationAddressStreet+"\",\n" +
                "        \"addressLocality\":\""+locationAddressCity+"\",\n" +
                "        \"addressRegion\":\""+locationAddressState+"\",\n" +
                "        \"postalCode\":\"" +locationAddressPostalCode+ "\",\n" +
                "        \"addressCountry\":\""+locationAddressCountry+"\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

    }

    private HashMap<String, String> makeKVEventMap(){
        return new HashMap<String, String>();
    }

    @Override
    public HashMap<String, String> getDefaultEventValues() {
        if (mEventValues == null){
            mEventValues = new HashMap<String, String>();
            mEventValues.put(EventConstants.EVENT_RESERVATION_NUMBER,"123");
            mEventValues.put(EventConstants.EVENT_RESERVATION_STATUS,"Confirmed");
            mEventValues.put(EventConstants.EVENT_SUBJECT,"Discuss vacation");
            mEventValues.put(EventConstants.EVENT_DETAIL,"Vacation plans for the group");
            mEventValues.put(EventConstants.EVENT_UNDER_NAME,"John Austin");
            mEventValues.put(EventConstants.EVENT_START_DATE,"2014-01-01T00:00:00.0000000");
            mEventValues.put(EventConstants.EVENT_LOCATION_NAME,"Seattle Center");
            mEventValues.put(EventConstants.EVENT_LOCATION_STREET,"1234 Easy Street");
            mEventValues.put(EventConstants.EVENT_LOCATION_CITY,"Seattle");
            mEventValues.put(EventConstants.EVENT_LOCATION_STATE,"Wa");
            mEventValues.put(EventConstants.EVENT_LOCATION_POSTAL_CODE,"98433");
        }
        return mEventValues;
    }

}