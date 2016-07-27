package com.microsoft.office.EventRobot;

import android.app.SearchManager;
import android.app.assist.AssistContent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.microsoft.office.EventRobot.R.id.DetailTextView;
import static com.microsoft.office.EventRobot.R.id.LocationTextView2;
import static com.microsoft.office.EventRobot.R.id.ReservationTextView2;
import static com.microsoft.office.EventRobot.R.id.SubjectText;
import static com.microsoft.office.EventRobot.R.id.cityTextView;
import static com.microsoft.office.EventRobot.R.id.postalCodeTextView;
import static com.microsoft.office.EventRobot.R.id.startDateTextView2;
import static com.microsoft.office.EventRobot.R.id.stateTextView;
import static com.microsoft.office.EventRobot.R.id.statusTextView2;
import static com.microsoft.office.EventRobot.R.id.streetTextView;

public class EventActivity extends AppCompatActivity implements ICallback {
    IEventProvider eventProvider;
    JsonObject mMicrosoftEvent;

    @InjectView(SubjectText)
    TextView mEventName;


    @InjectView(DetailTextView)
    TextView mMeetingDetail;

    @InjectView(ReservationTextView2)
    TextView mReservationText;

    @InjectView(statusTextView2)
    TextView mReservationStatus;

    @InjectView(startDateTextView2)
    TextView mStartDate;

    @InjectView(LocationTextView2)
    TextView mLocationName;

    @InjectView(streetTextView)
    TextView mLocationStreet;

    @InjectView(cityTextView)
    TextView mLocationCity;

    @InjectView(stateTextView)
    TextView mLocationState;

    @InjectView(postalCodeTextView)
    TextView mPostalCode;

    // arguments for this activity
    public static final String ARG_GIVEN_NAME = "givenName";
    public static final String ARG_DISPLAY_ID = "displayableId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.inject(this);

        if (getIntent().hasExtra(SearchManager.QUERY)) {
            Toast.makeText(
                    EventActivity.this,
                    "Search query: " + getIntent().getStringExtra(SearchManager.QUERY),
                    Toast.LENGTH_LONG).show();
            eventProvider = new EventManager(this.getApplicationContext());

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
        mMicrosoftEvent = result;
        eventProvider.convertToAssistContent(mMicrosoftEvent);
        //get event values from map and populate screen
        mEventName.setText(
                eventProvider
                        .getDefaultEventValues()
                        .get(EventConstants.EVENT_SUBJECT));

        mMeetingDetail.setText(
                eventProvider
                        .getDefaultEventValues()
                        .get(EventConstants.EVENT_DETAIL)
        );
        mLocationCity.setText(
                eventProvider
                        .getDefaultEventValues()
                        .get(EventConstants.EVENT_LOCATION_CITY));
        mLocationName.setText(
                eventProvider
                        .getDefaultEventValues()
                        .get(EventConstants.EVENT_LOCATION_NAME));
        mLocationStreet.setText(
                eventProvider
                        .getDefaultEventValues()
                        .get(EventConstants.EVENT_LOCATION_STREET));
        mLocationState.setText(
                eventProvider
                        .getDefaultEventValues()
                        .get(EventConstants.EVENT_LOCATION_STATE));
        mPostalCode.setText(
                eventProvider
                        .getDefaultEventValues()
                        .get(EventConstants.EVENT_LOCATION_POSTAL_CODE));
        mReservationStatus.setText(
                eventProvider
                        .getDefaultEventValues()
                        .get(EventConstants.EVENT_RESERVATION_STATUS));
        mReservationText.setText(
                eventProvider
                        .getDefaultEventValues()
                        .get(EventConstants.EVENT_RESERVATION_NUMBER));
        mStartDate.setText(
                eventProvider
                        .getDefaultEventValues()
                        .get(EventConstants.EVENT_START_DATE));

    }

    @Override
    public void onFailure(Exception e) {
        if (e.getMessage() == null) {
            Log.e("EventRobot", "failure to return query value");
        } else {
            Log.e("EventRobot", e.getMessage());
        }
    }

    @Override
    public void onProvideAssistContent(AssistContent assistContent) {
        super.onProvideAssistContent(assistContent);

        String structuredJson = null;
        try {
            String attendeeJson = new JSONObject()
                    .put("@context", "http://schema.org")
                    .put("@type", "person")
                    .put("familyName", "Loo")
                    .put("givenName", "Ricardo").toString();
            String postalAddressJson = new JSONObject()
                    .put("@context", "http://schema.org")
                    .put("@type", "postalAddress")
                    .put("postalCode", "98466")
                    .put("streetAddress", "9726 Vision court")
                    .put("addressLocality", "Garden Grove")
                    .put("addressRegion", "CA").toString();

            structuredJson = new JSONObject()
                    .put("@context", "http://schema.org")
                    .put("@type", "event")
                    .put("attendee", attendeeJson)
                    .put("location", postalAddressJson)
                    .put("startDate", "2017-03-06T19:30:00-08:00")
                    .toString();


            Log.i("Json", structuredJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mMicrosoftEvent != null) {
            structuredJson = eventProvider.convertToAssistContent(mMicrosoftEvent);
        }
        assistContent.setStructuredData(structuredJson);

    }

    @Override
    public void onProvideAssistData(android.os.Bundle bundle) {
        super.onProvideAssistData(bundle);
        if (mMicrosoftEvent != null) {
            bundle.putString(
                    "Event data",
                    eventProvider
                            .convertToAssistContent(mMicrosoftEvent));
        }


    }

}
