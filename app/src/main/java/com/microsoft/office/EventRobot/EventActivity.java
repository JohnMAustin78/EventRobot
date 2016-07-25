package com.microsoft.office.EventRobot;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.w3c.dom.Text;


public class EventActivity extends AppCompatActivity implements ICallback {
    IEventProvider eventProvider;
    TextView eventDetail;
    TextView queryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        eventDetail = (TextView)findViewById(R.id.textView);
        queryTextView = (TextView)findViewById(R.id.queryTextView);
        if(getIntent().hasExtra(SearchManager.QUERY)){
            queryTextView.setText(getIntent().getStringExtra(SearchManager.QUERY));
            eventProvider = new MockEventManager();
            eventProvider.getNextEvent(this);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onSuccess(JsonObject result) {
        eventDetail.setText(result.toString());
    }

    @Override
    public void onFailure(Exception e) {

    }
}
