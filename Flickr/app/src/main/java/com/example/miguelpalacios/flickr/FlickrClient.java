package com.example.miguelpalacios.flickr;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.*;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/******************************************************************************/
/* This class will take care of getting the data from Flicker API.            */
/* Here is set the key to make the call.                                      */
/******************************************************************************/
public class FlickrClient {

    private static final String REST_URL = "https://api.flickr.com/services/rest";
    private static final String REST_KEY = "9c741934f2de83817e839fd848244f50";
    private static final String REST_SECRET = "1d9aa3e1efed243e";

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static RequestParams params = new RequestParams();

    /******************************************************************************/
    /* Constructor will set the Key to be ready to make calls to the API.         */
    /* It also set the photos received per page.                                  */
    /******************************************************************************/
    public FlickrClient() {
        params.put("api_key", REST_KEY);
        params.put("per_page", 5);


    }

    /******************************************************************************/
    /* This method will make a call to get a list of interesting photos           */
    /******************************************************************************/
    public void getInterestingnessList(AsyncHttpResponseHandler handler, int pageNum) {
        params.put("page", pageNum);
        client.get(REST_URL + "/?method=flickr.interestingness.getList&format=rest", params, handler);
        Log.d("DEBUG", "Sending API call to getNextInterestingnessList");
    }

}
