package com.roomfinder;

import java.text.DecimalFormat;

import com.dataProvider.DataProvider;
import com.touchImageView.*;
import com.touchImageView.TouchImageView.OnTouchImageViewListener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class RoomActivity extends Activity{
	
	private AutoCompleteTextView autoComplete;;
	private ArrayAdapter<String> adapter;
	private static final String TAG = RoomActivity.class.getName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room);
		
		DataProvider dataProvider = new DataProvider();
		String[] searchList = dataProvider.getSearchList(this);
		
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,searchList);
		autoComplete = (AutoCompleteTextView) findViewById(R.id.autoComplete);
		
		

		// set adapter for the auto complete fields
		autoComplete.setAdapter(adapter);
		
		// specify the minimum type of characters before drop-down list is shown
		autoComplete.setThreshold(1);
		
		// when the user clicks an item of the drop-down list
		autoComplete.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
						
				DataProvider dataProvider = new DataProvider();
				String value = arg0.getItemAtPosition(arg2).toString();
				String result = "nothing found";
				

				
				if (dataProvider.isValueName(value)) {
					result = dataProvider.getRoomByName(value, RoomActivity.this);
					TextView roomTextView = (TextView) findViewById(R.id.roomTextView);
					roomTextView.setText("Room: " + result);
                    closeSoftKeyboard();
				} else {
					result = value;
				}
				
				if (result != "nothing found") {
//					Button openMapButton = (Button) findViewById(R.id.open_map);
//					openMapButton.setText(result);
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
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


