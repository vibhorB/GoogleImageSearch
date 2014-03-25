package com.codepath.imagesearch;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class FilterActivity extends Activity {
	
	Spinner sizeSpinner;
	Spinner colorSpinner;
	Spinner typeSpinner;
	EditText site;
	TextView submit;
	TextView cancel;
	Filters filter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		sizeSpinner = (Spinner) findViewById(R.id.spSize);
		colorSpinner = (Spinner) findViewById(R.id.spColor);
		typeSpinner = (Spinner) findViewById(R.id.spType);
		site = (EditText) findViewById(R.id.etSite);
		submit = (TextView) findViewById(R.id.tvSearch);
		cancel = (TextView) findViewById(R.id.tvCancel);
		
		filter = (Filters) getIntent().getSerializableExtra(ImageActivity.FILTER_KEY);
		initSpinners();
		initSiteText();
		setupButton();
	}
	
	private void initSiteText() {
		site.setText(filter.getSite());
		site.setSelection(site.getText().length());	
	}

	private void setupButton() {
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setFiltersAndFinishActivity();
			}
		});
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}

	protected void setFiltersAndFinishActivity() {
		String size = sizeSpinner.getSelectedItem().toString();
		String color = colorSpinner.getSelectedItem().toString();
		String type = typeSpinner.getSelectedItem().toString();
		String s = site.getText().toString();
		
		Filters filter = new Filters(s, color, size, type);
		
		Intent data = new Intent();
		data.putExtra(ImageActivity.FILTER_KEY, filter);
		setResult(RESULT_OK, data);
		//close this screen and go back
		finish();
		
	}

	private void initSpinners() {
		
		initSizeSpinner();
		initColorSpinner();
		initTypeSpinner();
	}

	private void initTypeSpinner() {
		List<String> type = new ArrayList<String>();
		type.add("any");
		type.add("face");
		type.add("photo");
		type.add("clipart");
		type.add("lineart");
		
		ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type);
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(typeAdapter);
		
		String current = filter.getType();
		if(current == null || current.equals("") || current.equals("any")){
			typeSpinner.setSelection(0);
		}else if(current.equals("face")){
			typeSpinner.setSelection(1);
		}else if(current.equals("photo")){
			typeSpinner.setSelection(2);
		}else if(current.equals("clipart")){
			typeSpinner.setSelection(3);
		}else if(current.equals("lineart")){
			typeSpinner.setSelection(4);
		}

	}

	private void initColorSpinner() {
		List<String> color = new ArrayList<String>();
		color.add("any");
		color.add("black");
		color.add("blue");
		color.add("brown");
		color.add("gray");
		color.add("green");
		color.add("orange");
		color.add("pink");
		color.add("purple");
		color.add("red");
		color.add("teal");
		color.add("white");
		color.add("yellow");
		
		ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, color);
		colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		colorSpinner.setAdapter(colorAdapter);
		
		String current = filter.getColor();
		if(current == null || current.equals("") || current.equals("any")){
			colorSpinner.setSelection(0);
		}else if(current.equals("black")){
			colorSpinner.setSelection(1);
		}else if(current.equals("blue")){
			colorSpinner.setSelection(2);
		}else if(current.equals("brown")){
			colorSpinner.setSelection(3);
		}else if(current.equals("gray")){
			colorSpinner.setSelection(4);
		}else if(current.equals("green")){
			colorSpinner.setSelection(5);
		}else if(current.equals("orange")){
			colorSpinner.setSelection(6);
		}else if(current.equals("pink")){
			colorSpinner.setSelection(7);
		}else if(current.equals("purple")){
			colorSpinner.setSelection(8);
		}else if(current.equals("red")){
			colorSpinner.setSelection(9);
		}else if(current.equals("teal")){
			colorSpinner.setSelection(10);
		}else if(current.equals("white")){
			colorSpinner.setSelection(11);
		}else if(current.equals("yellow")){
			colorSpinner.setSelection(12);
		}
	}

	private void initSizeSpinner() {
		List<String> size = new ArrayList<String>();
		size.add("any");
		size.add("icon");
		size.add("small");
		size.add("medium");
		size.add("large");
		size.add("xlarge");
		
		ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, size);
		sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sizeSpinner.setAdapter(sizeAdapter);
		
		String current = filter.getSize();
		if(current == null || current.equals("") || current.equals("any")){
			sizeSpinner.setSelection(0);
		}else if(current.equals("icon")){
			sizeSpinner.setSelection(1);
		}else if(current.equals("small")){
			sizeSpinner.setSelection(2);
		}else if(current.equals("medium")){
			sizeSpinner.setSelection(3);
		}else if(current.equals("large")){
			sizeSpinner.setSelection(4);
		}else if(current.equals("xlarge")){
			sizeSpinner.setSelection(5);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filter, menu);
		return true;
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

}
