package com.logic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.data.DataProvider;

import timber.log.Timber;

public class MainActivity extends BaseSearchActivity implements View.OnClickListener {

    private int mShortAnimationDuration;

    @Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        setupView();

        Button searchButton = (Button) findViewById(R.id.open_map);
		searchButton.setOnClickListener(this);

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);


        // when the user clicks an item of the drop-down list
		autoComplete.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
						
				DataProvider dataProvider = new DataProvider();
				String value = arg0.getItemAtPosition(arg2).toString();
				String result;

				if (dataProvider.isValueName(value)) {
					result = dataProvider.getRoomByName(value, MainActivity.this);
				} else {
					result = value;
				}
				
				if (result != NO_RESULTS) {
					Button openMapButton = (Button) findViewById(R.id.open_map);
					openMapButton.setText(result);
				}
				
			}
		});
	}
	

	@Override
	public void onClick(View v) {

	      switch (v.getId()) {
          case R.id.open_map:
              Timber.d("open map clicked");
  			  Intent i = new Intent(MainActivity.this, RoomActivity.class);
			  startActivity(i);
              break;
          default:
              
              break;
      }
		
	}

	
}
