package com.microsoft.office.EventRobot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by ricardol on 7/25/2016.
 */
public class MockEventManager implements IEventProvider {
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

        callback.onSuccess((JsonObject)parser.parse(fakeEvent));
    }
}
