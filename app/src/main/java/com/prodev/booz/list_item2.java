package com.prodev.booz;

public class list_item2 {

    // Store the id of the  movie poster
    private int mImageDrawable;
    // Store the name of the movie
    private String mName;
    // Store the release date of the movie
    private String mprice;
    private  String mqty;
    private String mtotal;
    private String mbill_no;
    String mid;

    // Constructor that is used to create an instance of the Movie object
    public list_item2(String mbill_no,String mName, String mprice, String  mqty, String mid) {
       // this.mImageDrawable = mImageDrawable;
        this.mName = mName;
        this.mprice = mprice;
        this.mqty=mqty;
        this.mid= mid;
        this.mbill_no=mbill_no;
    }

    public int getmImageDrawable() {
        return mImageDrawable;
    }
    public void setmImageDrawable(int mImageDrawable) {
        this.mImageDrawable = mImageDrawable;
    }

    public String getmBill_no(){return mbill_no;}
    public void setmBill_no(String mbill_no){this.mbill_no=mbill_no;}

    public String getmName() { return mName; }
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
