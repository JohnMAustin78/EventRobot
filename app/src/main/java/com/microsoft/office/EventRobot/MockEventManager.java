package com.microsoft.office.EventRobot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
}
