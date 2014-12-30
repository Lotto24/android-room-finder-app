package com.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.Constants;
import com.data.DataProvider;
import com.squareup.picasso.Picasso;
import com.ui.view.TouchImageView;

import timber.log.Timber;

public class RoomActivity extends Activity {

    protected AutoCompleteTextView autoComplete;
    protected ArrayAdapter<String> adapter;
    protected static final String NO_RESULTS = "nothing found";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room);

        setupView();

		// when the user clicks an item of the drop-down list
		autoComplete.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
						
				DataProvider dataProvider = new DataProvider();
				String value = arg0.getItemAtPosition(arg2).toString();

				if (dataProvider.isValueName(value)) {
					String result = dataProvider.getRoomByName(value, RoomActivity.this);
					TextView roomTextView = (TextView) findViewById(R.id.roomTextView);
					roomTextView.setText("Room: " + result);
                    TouchImageView imageView = (TouchImageView) findViewById(R.id.mapView);
                    closeSoftKeyboard();
				}
				
			}
		});

        autoComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView roomTextView = (TextView) findViewById(R.id.autoComplete);
            }
        });

	}

    private void closeSoftKeyboard() {

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

    }

    private void setupView() {
        DataProvider dataProvider = new DataProvider();
        String[] searchList = dataProvider.getSearchList(this);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, searchList);
        autoComplete = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        autoComplete.setAdapter(adapter);
        // specify the minimum type of characters before drop-down list is shown
        autoComplete.setThreshold(1);
        TouchImageView roomImageView = (TouchImageView) findViewById(R.id.mapView);
        Timber.d("load room image " + Constants.IMAGE_ROOM_URL);
        Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);
        Picasso.with(getApplicationContext()).load(Constants.IMAGE_ROOM_URL).into(roomImageView);

    }

}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


