package com.codepath.imagesearch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.codepath.search.api.ImageSearchRestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.image.SmartImageView;

public class ImageActivity extends Activity {

	private Filters filters;
	
	public static final String COLOR_KEY = "imgcolor";
	public static final String SIZE_KEY = "imgsz";
	public static final String TYPE_KEY = "imgtype";
	public static final String SITE_KEY = "as_sitesearch";
	private GridView imgGridView;
	private ArrayList<Image> images = new ArrayList<Image>();
	private ImageAdapter imgAdapter;
	private SearchView searchView;
	
	private int FILTER_REQUEST_CODE = 2;
	public static String FILTER_KEY = "result";
	private String queryVal;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		context = this;
		filters = new Filters();
		setGridView();
		
	}
	
	private void setGridView(){
		imgGridView = (GridView) findViewById(R.id.gvImage);
		//images = new ArrayList<Image>();
		//images.add(im);
		imgAdapter = new ImageAdapter(ImageActivity.this, images);
		imgGridView.setAdapter(imgAdapter);

		imgGridView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	Intent intent = new Intent(getApplicationContext(),
	        			FullImageActivity.class);
	        			Image img = images.get(position);
	        			intent.putExtra("result", img);
	        			startActivity(intent);
	        }
	    });
		
		imgGridView.setOnItemLongClickListener(new OnItemLongClickListener() {
	        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
	        	onShareItem(v);
	        	return true;
	        }
	    });
		
		imgGridView.setOnScrollListener(new EndlessScrollListener() {
	        @Override
	        public void onLoadMore(int page, int totalItemsCount) {
	        	if(page<=8){
	        		makeNetworkCall(queryVal, page);
	        	}
	        }
	        });
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.image, menu);
		 MenuItem searchItem = menu.findItem(R.id.action_search);
		    searchView = (SearchView) searchItem.getActionView();
		    try{
		    	searchView.setOnQueryTextListener(new OnQueryTextListener() {
				       @Override
				       public boolean onQueryTextSubmit(String query) {
				    	   queryVal = query;
				    	   prepareCall();
				            return true;
				       }

				       @Override
				       public boolean onQueryTextChange(String newText) {
				           return false;
				       }
				   });
		    }catch(Exception ex){
		    	System.out.println(ex.getStackTrace());
		    }
		    
		   return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_filter:
	        	launchFilter();
	        	return true;
	        case R.id.action_reset:
	        	filters = new Filters();
	        	prepareCall();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void prepareCall(){
		clearAdapter();
 	   	System.gc();
 	   	if(!isEmpty(queryVal)){
 	 	   	makeNetworkCall(queryVal, 0);
 	   	}
	}
	
	private void clearAdapter() {
		imgAdapter.clear();
		imgAdapter.notifyDataSetInvalidated();
		
	}

	private void launchFilter() {
		Intent i = new Intent(ImageActivity.this, FilterActivity.class);
		i.putExtra(FILTER_KEY, filters);
		startActivityForResult(i, FILTER_REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == FILTER_REQUEST_CODE){
			if(resultCode == RESULT_OK){
				filters = (Filters) data.getSerializableExtra(FILTER_KEY);
				prepareCall();
			}
		}
	}

	private void makeNetworkCall(String query, int page){
		String q = "q="+Uri.encode(query)+"&v=1.0&&rsz=8&start="+page*7;
		ImageSearchRestClient.get(q, constructRequestParam(), new JsonHttpResponseHandler(){
			
			public void onSuccess(JSONObject jsonResponse){
				try {
					JSONObject responseData = (JSONObject) jsonResponse.get("responseData");
					JSONArray results = responseData.getJSONArray("results");
					parseJSONAndExtractImage(results);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			public void onFailure(Throwable e, JSONObject error) {
			    // Handle the failure and alert the user to retry
			   Toast.makeText(context, "Network failure : "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
			  }
		});
		
	}
	
	private void parseJSONAndExtractImage(JSONArray jsonImages) throws JSONException{
		ArrayList<Image> list = new ArrayList<Image>();
		for(int i = 0; i < jsonImages.length(); i++){
			JSONObject jsonImg = jsonImages.getJSONObject(i);
			String thumb = jsonImg.getString("tbUrl");
			if(isEmpty(thumb)){
				Toast.makeText(this, "thumb url null", Toast.LENGTH_SHORT).show();
				return;
			}
			Image img = new Image(jsonImg.getString("url"), jsonImg.getString("titleNoFormatting"), thumb );
			list.add(img);
		}
		imgAdapter.addAll(list);
	}
	
	private RequestParams constructRequestParam(){
		RequestParams params = new RequestParams();
		
		if(!isEmpty(filters.getColor())){
			params.put(COLOR_KEY, filters.getColor());
		}
		if(!isEmpty(filters.getSize())){
			params.put(SIZE_KEY, filters.getSize());
		}
		if(!isEmpty(filters.getType())){
			params.put(TYPE_KEY, filters.getType());
		}
		if(!isEmpty(filters.getSite())){
			params.put(SITE_KEY, filters.getSite());
		}
		
		return params;
	}
	
	private boolean isEmpty(String str){
		if(str == null || str.trim().length() == 0 || str.equals("") || str.equals("any")){
			return true;
		}
		return false;
	}
	
	public void onShareItem(View v) {
	    // Get access to bitmap image from view
	    SmartImageView ivImage = (SmartImageView) findViewById(R.id.imgGrid);
	    Uri bmpUri = getLocalBitmapUri(ivImage);
	    if (bmpUri != null) {
	        // Construct a ShareIntent with link to image
	        Intent shareIntent = new Intent();
	        shareIntent.setAction(Intent.ACTION_SEND);
	        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
	        shareIntent.setType("image/*");
	        // Launch sharing dialog for image
	        startActivity(Intent.createChooser(shareIntent, "Share Content"));	
	    } else {
	        // ...sharing failed, handle error
	    	Toast.makeText(this, "ColorDrawable, cant share this image", Toast.LENGTH_SHORT).show();
	    }
	}

	public Uri getLocalBitmapUri(ImageView imageView) {
	    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
	    // Write image to default external storage directory   
	    Uri bmpUri = null;
	    Drawable drawable = imageView.getDrawable();
		if(drawable instanceof BitmapDrawable){
		    bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
		}else {
			return null;
//			Toast.makeText(this, "Colordrawable", Toast.LENGTH_SHORT).show();
//			bitmap = Bitmap.createBitmap(drawable.getBounds().width(), drawable.getBounds().height(), Config.ARGB_8888);
//		    Canvas canvas = new Canvas(bitmap); 
//		    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//		    drawable.draw(canvas);
		}
	    try {
	    	String fileName = "share_"+ System.currentTimeMillis() +".png";
	        File file =  new File(Environment.getExternalStoragePublicDirectory(  
	            Environment.DIRECTORY_DOWNLOADS), fileName);  
	    	//File file = new File(Environment.getDataDirectory(), "share_image.png");
	        FileOutputStream out = new FileOutputStream(file);
	        bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
	        out.close();
	        bmpUri = Uri.fromFile(file);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return bmpUri;
	}

}
