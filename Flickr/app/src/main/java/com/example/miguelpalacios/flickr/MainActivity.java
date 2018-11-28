package com.example.miguelpalacios.flickr;

import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.loopj.android.http.AsyncHttpResponseHandler;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends FragmentActivity {

    ViewPager viewPager;
    FragmentStatePagerAdapter pagerAdapter;
    CirclePageIndicator indicator;
    SwipeRefreshLayout swipeRefreshLayout;
    FlickrClient client;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLayouts();
        getPublicTimeline();

    }

    /******************************************************************************/
    /* This method initialize the client to make the call for the photos.         */
    /* Once it gets the data it will send them to the PhotoCollection.            */
    /* Calls getPhoto() from PhotoCollection and set the pagerAdapter with the    */
    /* data.                                                                      */
    /******************************************************************************/
    public void getPublicTimeline() {
        client = new FlickrClient();

        client.getInterestingnessList(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("DEBUG****", "getPublicTimeline onSuccess()");
                PhotoCollection collection = new PhotoCollection(responseBody);
                ArrayList<Photo> photoList = collection.getPhotos();
                pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), photoList);
                viewPager.setAdapter(pagerAdapter);
                indicator.setViewPager(viewPager);


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("DEBUG", "Failed getInterestingnessList, status code " + statusCode);
            }
        },page);

    }

    /******************************************************************************/
    /* This method initialize the layouts and resize them.                        */
    /******************************************************************************/
    public void setLayouts(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page++;
                getNextPhotos();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        indicator = (CirclePageIndicator) findViewById(R.id.indicator) ;
    }

    /******************************************************************************/
    /* This method initialize the layouts and resize them.                        */
    /******************************************************************************/
    public void getNextPhotos(){
        client.getInterestingnessList(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("DEBUG****", "onSuccess()");
                PhotoCollection collection = new PhotoCollection(responseBody);
                ArrayList<Photo> photoList = collection.getPhotos();
                pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), photoList);
                viewPager.setAdapter(pagerAdapter);
                indicator.setViewPager(viewPager);


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("DEBUG", "Failed getInterestingnessList, status code " + statusCode);

            }
        },page);

    }

    /******************************************************************************/
    /* This class creates and handles the Fragments                               */
    /******************************************************************************/
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<Photo> photoList;

        public ScreenSlidePagerAdapter(FragmentManager fm, ArrayList<Photo> photoList) {
            super(fm);
            this.photoList = photoList;
        }

        @Override
        public Fragment getItem(int position) {
            Photo photo = photoList.get(position);
            return ScreenSlidePageFragment.newInstance(photo.getTitle(), photo.getUrl());
        }

        @Override
        public int getCount() {
            return photoList.size();
        }
    }
}










