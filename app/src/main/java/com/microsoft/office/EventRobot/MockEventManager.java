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
                    .getAsJsonObject("name")
                    .getAsString();
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
                    "    \"name\": \"Foo Fighters Concert\",\n" +
                    "    \"startDate\": \"2017-03-06T19:30:00-08:00\",\n" +
                    "    \"location\": {\n" +
                    "      \"@type\": \"Place\",\n" +
                    "      \"name\": \"AT&T Park\",\n" +
                    "      \"address\": {\n" +
                    "        \"@type\": \"PostalAddress\",\n" +
                    "        \"streetAddress\": \"24 Willie Mays Plaza\",\n" +
                    "        \"addressLocality\": \"San Francisco\",\n" +
                    "        \"addressRegion\": \"CA\",\n" +
                    "        \"postalCode\": \"94107\",\n" +
                    "        \"addressCountry\": \"US\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";

    }
}
