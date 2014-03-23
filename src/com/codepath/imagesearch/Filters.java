package com.codepath.imagesearch;

import java.io.Serializable;

public class Filters implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4891110283800099250L;
	private String site;
	private String color;
	private String size;
	private String type;
	
	public Filters(){
		
	}
	
	public Filters(String site, String color, String size, String type){
		this.site = site;
		this.color = color;
		this.size = size;
		this.type = type;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
