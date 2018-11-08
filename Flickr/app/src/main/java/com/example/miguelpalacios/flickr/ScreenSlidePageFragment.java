package com.example.miguelpalacios.flickr;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/******************************************************************************/
/* This class is to handle the fragment for the photo and the title           */
/******************************************************************************/
public class ScreenSlidePageFragment extends Fragment {

    private String title;
    private String url;

    public static ScreenSlidePageFragment newInstance(String title, String url){
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
        title = getArguments().getString("title");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        TextView titleLabel = (TextView) view.findViewById(R.id.title_label);
        titleLabel.setText(title);
        ImageView image = (ImageView) view.findViewById(R.id.image_holder);
        Picasso.get().load(url).into(image);
        Log.d("DEBUG****", "ScreenSlidePageFragment: " + url);

        return view;
    }

}
