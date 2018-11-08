package com.example.miguelpalacios.flickr;

/******************************************************************************/
/* This object class is to keep the data of a photo together                  */
/******************************************************************************/
public class Photo {

    public  String title;
    public  String id;
    public  int server;
    public  int farm;
    public  String secret;
    public  String url;

    public Photo(){

    }

    public Photo(String title, String id, int server, int farm, String secret){
        this.title = title;
        this.id = id;
        this.server = server;
        this.farm = farm;
        this.secret = secret;
        this.url = "https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_m.jpg";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setServer(int server) {
        this.server = server;
    }

    public  void setFarm(int farm) {
        this.farm = farm;
    }

    public  void setSecret(String secret) {
        this.secret = secret;
    }

    public  String getUrl() {
        return "https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_m.jpg";
    }

    public  String getTitle() {
        return title;
    }
}
