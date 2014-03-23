package com.codepath.imagesearch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

public class FullImageActivity extends Activity {
	
	private ShareActionProvider miShareAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_image);
		
		 Image img = (Image) getIntent().getSerializableExtra("result");
		 SmartImageView imView = (SmartImageView) findViewById(R.id.ivFull);
	//	 try{
			 imView.setImageUrl(img.getImageUrl());
//		 }catch(RuntimeException ex){
//			 Toast.makeText(this, "Bad image, cant load", Toast.LENGTH_SHORT);
//			 finish();
//		 }finally{
//			 Toast.makeText(this, "Bad image, cant load", Toast.LENGTH_SHORT);
//			 finish();
//		 }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.full_image, menu);
		 MenuItem item = menu.findItem(R.id.menu_item_share);
		    // Fetch and store ShareActionProvider
		 miShareAction = (ShareActionProvider) item.getActionProvider();
		 setupShareAction();
		    // Return true to display menu
		    return true;
	}
	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//	    // Handle presses on the action bar items
//	    switch (item.getItemId()) {
//	        case R.id.menu_item_share:
//	        	setupShareAction();
//	            return true;
//	        
//	        default:
//	            return super.onOptionsItemSelected(item);
//	    }
//	}
	
	public void setupShareAction() {
	    // Fetch Bitmap Uri locally
//		try{
			 SmartImageView ivImage = (SmartImageView) findViewById(R.id.ivFull);
			    Uri bmpUri = getLocalBitmapUri(ivImage); // see previous section
			    // Create share intent as described above
			    Intent shareIntent = new Intent();
			    shareIntent.setAction(Intent.ACTION_SEND);
			    shareIntent.setAction(Intent.ACTION_SEND);
			    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
			    shareIntent.setType("image/*");
			    // Attach share event to the menu item provider
			    miShareAction.setShareIntent(shareIntent);
//		}catch(Exception ex){
//			Toast.makeText(this, "setupShareAction :Bad image, cant load", Toast.LENGTH_SHORT);
//			 finish();
//		}finally{
//			Toast.makeText(this, "setupShareAction :Bad image, cant load", Toast.LENGTH_SHORT);
//			 finish();
//		}
	   
	}
	
	public Uri getLocalBitmapUri(ImageView imageView) {
	    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
	    // Write image to default external storage directory   
	    Uri bmpUri = null;
	    try {
	        File file =  new File(Environment.getExternalStoragePublicDirectory(  
	            Environment.DIRECTORY_DOWNLOADS), "share_image.png");  
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
