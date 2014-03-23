package com.codepath.imagesearch;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.squareup.picasso.Picasso;

public class ImageAdapter extends ArrayAdapter<Image>{

	String TAG = "ImageAdapter";
	private Context context;
    private List<Image> images;

    public ImageAdapter(Context context, List<Image> images) 
    {
    	super(context, R.layout.image, images);
        Log.i(TAG," in set adapter lstRecipes "+ images.size() );
        this.context = context;
        this.images = images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
        ViewHolder holder;
        if (convertView == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView  = layoutInflater.inflate(R.layout.image, parent, false);
            holder = new ViewHolder();
            holder.txtTitle = (TextView)convertView.findViewById(R.id.txtTitle);
            holder.imgGrid = (SmartImageView)convertView.findViewById(R.id.imgGrid);

            convertView.setTag(holder);
        }
        else 
        {
             holder=(ViewHolder) convertView.getTag();
        }
        try
        {
            holder.txtTitle.setText(images.get(position).getImageTitle());
            holder.imgGrid.setImageUrl(images.get(position).getImageUrl());
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }

       return convertView;
    }

    public static class ViewHolder
    {
        private TextView txtTitle;
        private SmartImageView imgGrid;
    }
}