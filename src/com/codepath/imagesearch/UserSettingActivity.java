package com.codepath.imagesearch;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

public class UserSettingActivity extends PreferenceActivity {
	
	  @Override
    public void onCreate(Bundle savedInstanceState) 
    {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.user_setting);
            getActionBar().setDisplayHomeAsUpEnabled(true);
    }
	  @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case android.R.id.home:
	        	setResult(RESULT_OK);
	            this.finish();
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
	  
	  @Override
	  public void onBackPressed() {
		  setResult(RESULT_OK);
        this.finish();
	  }

}
