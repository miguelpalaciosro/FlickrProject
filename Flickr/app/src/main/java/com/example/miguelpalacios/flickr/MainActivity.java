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

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends FragmentActivity {

    ViewPager viewPager;
    FragmentStatePagerAdapter pagerAdapter;
    CirclePageIndicator indicator;
    SwipeRefreshLayout swipeRefreshLayout;
    FlickrClient client;
    int page = 2;

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
        });

    }

    /******************************************************************************/
    /* This method initialize the layouts and resize them.                        */
    /******************************************************************************/
    public void setLayouts(){
        // Get size of screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        // Identify layouts and resize them
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relative);
        ViewGroup.LayoutParams layout_description = relativeLayout.getLayoutParams();
        layout_description.width = width;
        layout_description.height = height;

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewGroup.LayoutParams pagerParams = viewPager.getLayoutParams();
        pagerParams.width = width;
        pagerParams.height = height - 35;

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNextPhotos();
                page++;
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        indicator = (CirclePageIndicator) findViewById(R.id.indicator) ;
    }

    /******************************************************************************/
    /* This method initialize the layouts and resize them.                        */
    /******************************************************************************/
    public void getNextPhotos(){
        client.getNextInterestingnessList(new AsyncHttpResponseHandler() {
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
            switch(position) {
                case 0:
                    return ScreenSlidePageFragment.newInstance(photo.getTitle(), photo.getUrl());
                case 1:
                    return ScreenSlidePageFragment.newInstance(photo.getTitle(), photo.getUrl());
                case 2:
                    return ScreenSlidePageFragment.newInstance(photo.getTitle(), photo.getUrl());
                case 3:
                    return ScreenSlidePageFragment.newInstance(photo.getTitle(), photo.getUrl());
                case 4:
                    return ScreenSlidePageFragment.newInstance(photo.getTitle(), photo.getUrl());
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}










