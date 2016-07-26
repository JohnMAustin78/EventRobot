package com.microsoft.office.EventRobot;

import android.app.SearchManager;
import android.app.assist.AssistContent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.JsonObject;

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
import static com.microsoft.office.EventRobot.R.id.subjectTextView;



public class EventActivity extends AppCompatActivity implements ICallback {
    IEventProvider eventProvider;
    JsonObject mMicrosoftEvent;



    @InjectView(subjectTextView)
    TextView mEventName;

    @InjectView(SubjectText)
    TextView mSubjectText;

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

    TextView mqueryTextView;
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

        if(getIntent().hasExtra(SearchManager.QUERY)){
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
        mMicrosoftEvent = result;

        //get event values from map and populate screen

    }

    @Override
    public void onFailure(Exception e) {

    }
    @Override
    public void onProvideAssistContent(AssistContent assistContent) {

        String structuredJson = null;

        if (mMicrosoftEvent != null) {
            structuredJson = eventProvider.convertToAssistContent(mMicrosoftEvent);
            assistContent.setStructuredData(structuredJson);
        }
        super.onProvideAssistContent(assistContent);

    }

    private void fillScreenPrompts(JsonObject microsoftEvent){

    }
}
