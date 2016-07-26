package com.microsoft.office.EventRobot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

/**
 * Created by ricardol on 7/25/2016.
 */
public class MockEventManager implements IEventProvider {
    public void getNextEvent(ICallback callback) {
        String fakeEvent = "{\n" +
                "  \"attendees\": [{\"@odata.type\": \"microsoft.graph.attendee\"}],\n" +
                "  \"body\": {\"@odata.type\": \"microsoft.graph.itemBody\"},\n" +
                "  \"bodyPreview\": \"The preview of the body\",\n" +
                "  \"categories\": [\"A category\"],\n" +
                "  \"changeKey\": \"Change key\",\n" +
                "  \"createdDateTime\": \"String (timestamp)\",\n" +
                "  \"end\": {\"@odata.type\": \"microsoft.graph.dateTimeTimeZone\"},\n" +
                "  \"hasAttachments\": true,\n" +
                "  \"iCalUId\": \"string\",\n" +
                "  \"id\": \"string (identifier)\",\n" +
                "  \"importance\": \"String\",\n" +
                "  \"isAllDay\": true,\n" +
                "  \"isCancelled\": true,\n" +
                "  \"isOrganizer\": true,\n" +
                "  \"isReminderOn\": true,\n" +
                "  \"lastModifiedDateTime\": \"String (timestamp)\",\n" +
                "  \"location\": {\"@odata.type\": \"microsoft.graph.location\"},\n" +
                "  \"organizer\": {\"@odata.type\": \"microsoft.graph.recipient\"},\n" +
                "  \"originalEndTimeZone\": \"string\",\n" +
                "  \"originalStart\": \"String (timestamp)\",\n" +
                "  \"originalStartTimeZone\": \"string\",\n" +
                "  \"recurrence\": {\"@odata.type\": \"microsoft.graph.patternedRecurrence\"},\n" +
                "  \"reminderMinutesBeforeStart\": 1024,\n" +
                "  \"responseRequested\": true,\n" +
                "  \"responseStatus\": {\"@odata.type\": \"microsoft.graph.responseStatus\"},\n" +
                "  \"sensitivity\": \"String\",\n" +
                "  \"seriesMasterId\": \"string\",\n" +
                "  \"showAs\": \"String\",\n" +
                "  \"start\": {\"@odata.type\": \"microsoft.graph.dateTimeTimeZone\"},\n" +
                "  \"subject\": \"string\",\n" +
                "  \"type\": \"String\",\n" +
                "  \"webLink\": \"string\",\n" +
                "\n" +
                "  \"attachments\": [ { \"@odata.type\": \"microsoft.graph.attachment\" } ],\n" +
                "  \"calendar\": { \"@odata.type\": \"microsoft.graph.calendar\" },\n" +
                "  \"instances\": [ { \"@odata.type\": \"microsoft.graph.event\" }]\n" +
                "\n" +
                "}";

        JsonParser parser = new JsonParser();

        callback.onSuccess((JsonObject)parser.parse(fakeEvent));
    }
    public String convertToAssistContent(JsonObject microsoftEvent){



        String structuredJson = new JSONObject().toString();
        Object thing = microsoftEvent.get("organizer");
        String organizerName = null;
        if (thing.getClass().equals(JsonObject.class)) {
            organizerName = microsoftEvent
                    .getAsJsonObject("organizer")
                    .getAsJsonObject("emailAddress")
                    .getAsJsonPrimitive("name")
                    .getAsString();
        }

        String startDate = null;
        thing = microsoftEvent.get("start");
        if (thing.getClass().equals(JsonObject.class)){
             startDate = microsoftEvent
                    .getAsJsonObject("start")
                    .getAsJsonPrimitive("dateTime")
                    .getAsString();
        }
        String locationName = null;
        String locationAddressStreet = null;
        String locationAddressCity = null;
        String locationAddressState = null;
        String locationAddressPostalCode = null;
        String locationAddressCountry = null;

        thing = microsoftEvent.get("location");
        if (thing.getClass().equals(JsonObject.class)){
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

        return             structuredJson = "{\n" +
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
}
