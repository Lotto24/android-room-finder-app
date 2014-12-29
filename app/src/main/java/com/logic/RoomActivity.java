package com.logic;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.data.DataProvider;

public class RoomActivity extends BaseSearchActivity {

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

}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


