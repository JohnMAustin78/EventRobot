package com.microsoft.office.EventRobot;

import android.widget.TextView;

import android.app.SearchManager;
import android.app.assist.AssistContent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.w3c.dom.Text;
import org.json.JSONException;
import org.json.JSONObject;


public class EventActivity extends AppCompatActivity implements ICallback {
    IEventProvider eventProvider;
    TextView eventDetail;
    TextView queryTextView;
    // arguments for this activity
    public static final String ARG_GIVEN_NAME = "givenName";
    public static final String ARG_DISPLAY_ID = "displayableId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        eventDetail = (TextView)findViewById(R.id.textView);
        queryTextView = (TextView)findViewById(R.id.queryTextView);
        if(getIntent().hasExtra(SearchManager.QUERY)){
            queryTextView.setText(getIntent().getStringExtra(SearchManager.QUERY));
            eventProvider = new MockEventManager();
            eventProvider.getNextEvent(this);
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onSuccess(JsonObject result) {
        eventDetail.setText(result.toString());
    }

    @Override
    public void onFailure(Exception e) {

    }
    @Override
    public void onProvideAssistContent(AssistContent assistContent) {
        //  super.onProvideAssistContent(assistContent);

        String structuredJson = null;
        try {
            String attendeeJson = new JSONObject()
                    .put("@context","http://schema.org")
                    .put("@type","person")
                    .put("familyName","Smith")
                    .put("givenName","Brad").toString();
            String postalAddressJson = new JSONObject()
                    .put("@context","http://schema.org")
                    .put("@type","postalAddress")
                    .put("postalCode","98466")
                    .put("streetAddress","9726 Vision court")
                    .put("addressLocality","Garden Grove")
                    .put("addressRegion","CA").toString();

            structuredJson = new JSONObject()
                    .put("@context","http://schema.org")
                    .put("@type","event")
                    .put("attendee", attendeeJson)
                    .put("location", postalAddressJson)
                    .put("startDate", "2017-03-06T19:30:00-08:00")
                    .toString();

//            structuredJson = "{\n" +
//                    "  \"@context\": \"http://schema.org\",\n" +
//                    "  \"@type\": \"EventReservation\",\n" +
//                    "  \"reservationNumber\": \"E123456789\",\n" +
//                    "  \"reservationStatus\": \"http://schema.org/Confirmed\",\n" +
//                    "  \"underName\": {\n" +
//                    "    \"@type\": \"Person\",\n" +
//                    "    \"name\": \"John Smith\"\n" +
//                    "  },\n" +
//                    "  \"reservationFor\": {\n" +
//                    "    \"@type\": \"Event\",\n" +
//                    "    \"name\": \"Foo Fighters Concert\",\n" +
//                    "    \"startDate\": \"2017-03-06T19:30:00-08:00\",\n" +
//                    "    \"location\": {\n" +
//                    "      \"@type\": \"Place\",\n" +
//                    "      \"name\": \"AT&T Park\",\n" +
//                    "      \"address\": {\n" +
//                    "        \"@type\": \"PostalAddress\",\n" +
//                    "        \"streetAddress\": \"24 Willie Mays Plaza\",\n" +
//                    "        \"addressLocality\": \"San Francisco\",\n" +
//                    "        \"addressRegion\": \"CA\",\n" +
//                    "        \"postalCode\": \"94107\",\n" +
//                    "        \"addressCountry\": \"US\"\n" +
//                    "      }\n" +
//                    "    }\n" +
//                    "  }\n" +
//                    "}";

            Log.i("Json", structuredJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assistContent.setStructuredData(structuredJson);
        super.onProvideAssistContent(assistContent);

    }
}
