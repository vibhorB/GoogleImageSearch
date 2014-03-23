package com.codepath.imagesearch;

import java.io.Serializable;

public class Image implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1725960434153069386L;
	private String imageUrl;
	private String imageTitle;
	private String thumbUrl;
	public Image(String imageUrl, String imageTitle, String thumbUrl) {
		super();
		this.imageUrl = imageUrl;
		this.imageTitle = imageTitle;
		this.thumbUrl = thumbUrl;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageTitle() {
		return imageTitle;
	}
	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

}
