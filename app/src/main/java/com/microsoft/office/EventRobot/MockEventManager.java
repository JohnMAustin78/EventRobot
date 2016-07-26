package com.microsoft.office.EventRobot;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;

/**
 * Created by ricardol on 7/25/2016.
 */
public class MockEventManager implements IEventProvider {

    private HashMap<String,String> mEventValues;
    public void getNextEvent(ICallback callback) {
        String fakeEvent = "{\n" +
                "            \"@odata.etag\": \"W/\\\"K7T5f6aJskSLQawF2628UAAAE2Tftg==\\\"\",\n" +
                "            \"id\": \"AAMkAGQxZWY4MzQyLTBlNTgtNDg4Ni04MmY2LWM0ZmY5ZDRjY2FhNwBGAAAAAAAPC8PkwEUDQopHgzCVd-1RBwArtPl-pomyRItBrAXbrbxQAAAAAAENAAArtPl-pomyRItBrAXbrbxQAAATYsH5AAA=\",\n" +
                "            \"createdDateTime\": \"2016-07-25T22:17:49.820599Z\",\n" +
                "            \"lastModifiedDateTime\": \"2016-07-25T22:17:50.6143209Z\",\n" +
                "            \"changeKey\": \"K7T5f6aJskSLQawF2628UAAAE2Tftg==\",\n" +
                "            \"categories\": [],\n" +
                "            \"originalStartTimeZone\": \"Pacific Standard Time\",\n" +
                "            \"originalEndTimeZone\": \"Pacific Standard Time\",\n" +
                "            \"responseStatus\": {\n" +
                "                \"response\": \"organizer\",\n" +
                "                \"time\": \"0001-01-01T00:00:00Z\"\n" +
                "            },\n" +
                "            \"iCalUId\": \"040000008200E00074C5B7101A82E00800000000867E2561C2E6D1010000000000000000100000009A87567E17E09D4783489BBCC67E6792\",\n" +
                "            \"reminderMinutesBeforeStart\": 15,\n" +
                "            \"isReminderOn\": true,\n" +
                "            \"hasAttachments\": false,\n" +
                "            \"subject\": \"Test event\",\n" +
                "            \"body\": {\n" +
                "                \"contentType\": \"html\",\n" +
                "                \"content\": \"<html>\\r\\n<head>\\r\\n<meta http-equiv=\\\"Content-Type\\\" content=\\\"text/html; charset=utf-8\\\">\\r\\n<style type=\\\"text/css\\\" style=\\\"\\\">\\r\\n<!--\\r\\np\\r\\n\\t{margin-top:0;\\r\\n\\tmargin-bottom:0}\\r\\n-->\\r\\n</style>\\r\\n</head>\\r\\n<body dir=\\\"ltr\\\">\\r\\n<div id=\\\"divtagdefaultwrapper\\\" style=\\\"font-size:12pt; color:#000000; background-color:#FFFFFF; font-family:Calibri,Arial,Helvetica,sans-serif\\\">\\r\\n<p><br>\\r\\n</p>\\r\\n</div>\\r\\n</body>\\r\\n</html>\\r\\n\"\n" +
                "            },\n" +
                "            \"bodyPreview\": \"\",\n" +
                "            \"importance\": \"normal\",\n" +
                "            \"sensitivity\": \"normal\",\n" +
                "            \"start\": {\n" +
                "                \"dateTime\": \"2016-07-25T22:30:00.0000000\",\n" +
                "                \"timeZone\": \"UTC\"\n" +
                "            },\n" +
                "            \"end\": {\n" +
                "                \"dateTime\": \"2016-07-25T23:00:00.0000000\",\n" +
                "                \"timeZone\": \"UTC\"\n" +
                "            },\n" +
                "            \"location\": {\n" +
                "                \"displayName\": \"Seattle\",\n" +
                "                \"address\": {\n" +
                "                    \"street\": \"\",\n" +
                "                    \"city\": \"Seattle\",\n" +
                "                    \"state\": \"Washington\",\n" +
                "                    \"countryOrRegion\": \"United States\",\n" +
                "                    \"postalCode\": \"\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"isAllDay\": false,\n" +
                "            \"isCancelled\": false,\n" +
                "            \"isOrganizer\": true,\n" +
                "            \"recurrence\": null,\n" +
                "            \"responseRequested\": true,\n" +
                "            \"seriesMasterId\": null,\n" +
                "            \"showAs\": \"busy\",\n" +
                "            \"type\": \"singleInstance\",\n" +
                "            \"attendees\": [\n" +
                "                {\n" +
                "                    \"status\": {\n" +
                "                        \"response\": \"none\",\n" +
                "                        \"time\": \"0001-01-01T00:00:00Z\"\n" +
                "                    },\n" +
                "                    \"type\": \"required\",\n" +
                "                    \"emailAddress\": {\n" +
                "                        \"name\": \"Alex Darrow\",\n" +
                "                        \"address\": \"AlexD@MOD265542.onmicrosoft.com\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"status\": {\n" +
                "                        \"response\": \"none\",\n" +
                "                        \"time\": \"0001-01-01T00:00:00Z\"\n" +
                "                    },\n" +
                "                    \"type\": \"required\",\n" +
                "                    \"emailAddress\": {\n" +
                "                        \"name\": \"Zrinka Makovac\",\n" +
                "                        \"address\": \"ZrinkaM@MOD265542.onmicrosoft.com\"\n" +
                "                    }\n" +
                "                }\n" +
                "            ],\n" +
                "            \"organizer\": {\n" +
                "                \"emailAddress\": {\n" +
                "                    \"name\": \"MOD Administrator\",\n" +
                "                    \"address\": \"admin@MOD265542.onmicrosoft.com\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"webLink\": \"https://outlook.office365.com/owa/?ItemID=AAMkAGQxZWY4MzQyLTBlNTgtNDg4Ni04MmY2LWM0ZmY5ZDRjY2FhNwBGAAAAAAAPC8PkwEUDQopHgzCVd%2F1RBwArtPl%2FpomyRItBrAXbrbxQAAAAAAENAAArtPl%2FpomyRItBrAXbrbxQAAATYsH5AAA%3D&exvsurl=1&viewModel=ICalendarItemDetailsViewModelFactory\"\n" +
                "        }";

        JsonParser parser = new JsonParser();
        //Fill event value map for UI.
        callback.onSuccess((JsonObject)parser.parse(fakeEvent));
    }
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
