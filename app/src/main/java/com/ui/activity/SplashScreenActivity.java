package com.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.data.RetrieveDataTask;
import com.data.Login;
import com.events.DataUpdatedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import timber.log.Timber;
import com.RoomFinderApplication;

public class SplashScreenActivity extends Activity implements View.OnClickListener{

    @Inject
    RetrieveDataTask retrieveDataTask;

    @Inject
    Bus eventsBus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        RoomFinderApplication.get(getApplicationContext()).inject(this);
		setContentView(R.layout.splash_screen);
        Timber.d("Start splash screen.");
		
		TextView textView = (TextView) findViewById(R.id.load);
		Login login = new Login();
		
		if (login.isLogin(this)){
			textView.setText("Loading...");
            Timber.d("Trigger update.");
            retrieveDataTask.execute(this);
		} else {
			TextView headlinePassword = (TextView) findViewById(R.id.HeadlineLoginTextView);
			TextView buttonPassword = (TextView) findViewById(R.id.loginButton);
			TextView enterTextViewPassword = (TextView) findViewById(R.id.loginEditText);
			headlinePassword.setVisibility(View.VISIBLE);
			buttonPassword.setVisibility(View.VISIBLE);
			enterTextViewPassword.setVisibility(View.VISIBLE);
			textView.setText("Waiting for Password ...");
			InputMethodManager imm = (InputMethodManager)getSystemService(
				      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(enterTextViewPassword.getWindowToken(), 0);
			imm.hideSoftInputFromWindow(buttonPassword.getWindowToken(), 0);
			buttonPassword.setOnClickListener(this);
		}
	}

    @Subscribe
    public void dataWasUpdated(DataUpdatedEvent event) {
        Timber.d("Update was done successfully ? = " + event.isSuccessFull() + ", Start main activity");
        ProgressBar spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        Intent i = new Intent(SplashScreenActivity.this,
                RoomActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("register at event bus");
        eventsBus.register(this);
    }

    @Override
    public void onPause() {
        super.onStop();
        Timber.d("unregister at event bus");
        eventsBus.unregister(this);
    }

	@Override
	public void onClick(View v) {
		
		Login login = new Login();
		
	      switch (v.getId()) {
          case R.id.loginButton:
              Timber.d("Button login pressed");
        	  TextView passwordEditText = (TextView) findViewById(R.id.loginEditText);
        	  String password = passwordEditText.getText().toString();
        	  
        	  if (login.checkPassword(password, this)) {
        		  TextView headlinePassword = (TextView) findViewById(R.id.HeadlineLoginTextView);
        		  TextView textView = (TextView) findViewById(R.id.load);
        		  textView.setText("Loading...");
        		  headlinePassword.setText("Password accepted - loading ...");
                  Timber.d("Trigger update.");
                  retrieveDataTask.execute(this);
        	  } else {
        		  TextView headlinePassword = (TextView) findViewById(R.id.HeadlineLoginTextView);
        		  headlinePassword.setText("Password not accepted !!!");
        	  }

              break;
          default:
              
              break;
      }
		
	}

}
