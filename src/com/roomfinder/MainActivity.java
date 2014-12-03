package com.roomfinder;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import com.dataProvider.DataProvider;


public class MainActivity extends Activity {

	 private AutoCompleteTextView autoComplete;;
	 private ArrayAdapter<String> adapter;
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DataProvider dataProvider = new DataProvider();
		String[] searchList = dataProvider.getSearchList();
		
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
					result = dataProvider.getRoomByName(value);
				} else {
					result = value;
				}
				
				if (result != "nothing found") {
					Button openMapButton = (Button) findViewById(R.id.open_map);
					openMapButton.setText(result);
				}
				
			}
		});
	}
}
