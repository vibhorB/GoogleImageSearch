package com.codepath.search.api;

import com.loopj.android.http.*;

public class ImageSearchRestClient {
	
	private static AsyncHttpClient httpClient = new AsyncHttpClient();
	
	private static final String BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images?";
	
	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		httpClient.get(getAbsoluteUrl(url), params, responseHandler);
	  }

	  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		  httpClient.post(getAbsoluteUrl(url), params, responseHandler);
	  }

	  private static String getAbsoluteUrl(String relativeUrl) {
	      return BASE_URL + relativeUrl;
	  }

}
