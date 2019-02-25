package com.prodev.booz;

public class item {
    int id;
    String name,restaurant;
    int price;
    int pic;
    String photo;
    item(int id,String name, int price, String photo){
        this.id=id;
        this.name=name;
        this.price=price;
        this.photo=photo;
    }
    item(int id,String name, int price,String restaurant, String photo){
        this.id=id;
        this.name=name;
        this.price=price;
        this.photo=photo;
        this.restaurant=restaurant;
    }
    item(String name,int pic){
        this.name=name;
        this.pic=pic;

    }

}
