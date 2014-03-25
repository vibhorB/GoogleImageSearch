package com.codepath.imagesearch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

public class FullImageActivity extends Activity {
	
	private ShareActionProvider miShareAction;
	private Handler handler = new Handler();
	private Runnable runnable;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_image);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		context = this;
		 Image img = (Image) getIntent().getSerializableExtra("result");
		 SmartImageView imView = (SmartImageView) findViewById(R.id.ivFull);
		 TextView tvTtl = (TextView) findViewById(R.id.txtFullTitle);
		 imView.setImageUrl(img.getImageUrl());
		 tvTtl.setText(img.getImageTitle());

		runnable = new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//Toast.makeText(context, "runnable", Toast.LENGTH_SHORT).show();
					setupShareAction();
					
				}
			};
			
			handler.postDelayed(runnable, 2000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.full_image, menu);
		 MenuItem item = menu.findItem(R.id.menu_item_share);
		    // Fetch and store ShareActionProvider
		 miShareAction = (ShareActionProvider) item.getActionProvider();
		 //miShareAction.setShareIntent(null);
		 //setupShareAction();
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
	
	public void setupShareAction() {
	    // Fetch Bitmap Uri locally
			 SmartImageView ivImage = (SmartImageView) findViewById(R.id.ivFull);
			    Uri bmpUri = getLocalBitmapUri(ivImage); // see previous section
			    // Create share intent as described above
			    if(bmpUri != null){
			    	Intent shareIntent = new Intent();
				    shareIntent.setAction(Intent.ACTION_SEND);
				    shareIntent.setAction(Intent.ACTION_SEND);
				    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
				    shareIntent.setType("image/*");
				    // Attach share event to the menu item provider
				    miShareAction.setShareIntent(shareIntent);
			    }else{
			    	Toast.makeText(this, "ColorDrawable, cant share this image", Toast.LENGTH_SHORT).show();
					 //finish();
			    }	   
	}
		
	public Uri getLocalBitmapUri(ImageView imageView) {
		 Bitmap bitmap;
		 Drawable drawable = imageView.getDrawable();
		if(drawable instanceof BitmapDrawable){
		    bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
		}else {
			//Toast.makeText(this, "Colordrawable", Toast.LENGTH_SHORT).show();
//			bitmap = Bitmap.createBitmap(drawable.getBounds().width(), drawable.getBounds().height(), Config.ARGB_8888);
//		    Canvas canvas = new Canvas(bitmap); 
//		    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//		    drawable.draw(canvas);
			return null;
		}
	    // Write image to default external storage directory   
	    Uri bmpUri = null;
	    try {
	    	String fileName = "share_"+ System.currentTimeMillis() +".png";
	        File file =  new File(Environment.getExternalStoragePublicDirectory(  
	            Environment.DIRECTORY_DOWNLOADS), fileName);  
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
