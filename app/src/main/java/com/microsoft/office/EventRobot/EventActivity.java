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


public class EventActivity extends AppCompatActivity implements ICallback {
    IEventProvider eventProvider;
    JsonObject mMicrosoftEvent;
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
        mMicrosoftEvent = result;

    }

    @Override
    public void onFailure(Exception e) {

    }
    @Override
    public void onProvideAssistContent(AssistContent assistContent) {

        String structuredJson = null;

        structuredJson = eventProvider.convertToAssistContent(mMicrosoftEvent);
        assistContent.setStructuredData(structuredJson);
        super.onProvideAssistContent(assistContent);

    }

    private void fillScreenPrompts(JsonObject microsoftEvent){

    }
}
