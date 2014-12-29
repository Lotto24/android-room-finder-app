package com.logic;


import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.data.DataProvider;

public class BaseSearchActivity extends Activity {

    protected AutoCompleteTextView autoComplete;
    protected ArrayAdapter<String> adapter;
    protected static final String NO_RESULTS = "nothing found";

    protected void setupView() {
        DataProvider dataProvider = new DataProvider();
        String[] searchList = dataProvider.getSearchList(this);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, searchList);
        autoComplete = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        autoComplete.setAdapter(adapter);
        // specify the minimum type of characters before drop-down list is shown
        autoComplete.setThreshold(1);

    }

}
