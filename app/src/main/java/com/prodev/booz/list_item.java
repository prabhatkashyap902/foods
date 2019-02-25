package com.prodev.booz;

import com.android.volley.toolbox.StringRequest;

public class list_item {

    // Store the id of the  movie poster
    private int mImageDrawable;
    // Store the name of the movie
    private String mName;
    // Store the release date of the movie
    private String mprice;
    private  String mqty;
    private String mtotal;
    String mid;

    // Constructor that is used to create an instance of the Movie object
    public list_item(String mName, String mprice,String  mqty,String mid) {
       // this.mImageDrawable = mImageDrawable;
        this.mName = mName;
        this.mprice = mprice;
        this.mqty=mqty;
        this.mid= mid;
    }

    public int getmImageDrawable() {
        return mImageDrawable;
    }

    public void setmImageDrawable(int mImageDrawable) {
        this.mImageDrawable = mImageDrawable;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPrice(){
        return mprice;
    }

    public void setmPrice(String mprice) {
        this.mprice = mprice;
    }

    public String getmQty() {
        return mqty;
    }

    public void setmQty(String mqty) {
        this.mqty = mqty;
    }

    public String getmId() {
        return mid;
    }
    public void setmId(String mid) {
        this.mid = mid;
    }


    public String getmTotal() {
        return mtotal;
    }
    public void setmTotal(String mtotal) {
        this.mtotal=mtotal;
    }


}
