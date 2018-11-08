package com.example.miguelpalacios.flickr;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

/******************************************************************************/
/* This class will take care of transforming the data to photos and returning */
/*  a list of the photos                                                      */
/******************************************************************************/
public class PhotoCollection {

    public static JSONArray photos;

    /******************************************************************************/
    /* Constructor will get data as a byte[] and it will convert it to a string.  */
    /*  From this string it will convert to JSON and create a JSONArray           */
    /******************************************************************************/
    public PhotoCollection(byte[] response) {
        String decode;
        try {
            decode = new String(response, "UTF-8");
            XmlToJson xmlToJson = new XmlToJson.Builder(decode).build();
            try {
                photos = xmlToJson.toJson().getJSONObject("rsp").getJSONObject("photos").getJSONArray("photo");
            }catch (JSONException e){
                e.printStackTrace();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /******************************************************************************/
    /* This method will get the JSONArray and will extract each photo to convet   */
    /* them to Photo objects. Each photo will be added to an ArrayList<Photo>     */
    /* and return the ArrayList when finish.                                      */
    /******************************************************************************/
    public ArrayList<Photo> getPhotos(){
        ArrayList<Photo> photoList = new ArrayList<Photo>();
        try {
        for(int i = 0; i < photos.length(); i++) {

                Photo photo = new Photo(photos.getJSONObject(i).getString("title"),
                                        photos.getJSONObject(i).getString("id"),
                                        photos.getJSONObject(i).getInt("server"),
                                        photos.getJSONObject(i).getInt("farm"),
                                        photos.getJSONObject(i).getString("secret"));
                photoList.add(photo);
        }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return photoList;
    }
}
