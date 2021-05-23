package com.example.capstone_36team;

public class Product {
    String id;
    String name;
    int count;
    int date = 0;
    String placedetail;
    String placelog;
    public Product(String iid,String iname, String iplace ,int icount){
        id = iid;
        name = iname;
        count = icount;
        placedetail = iplace;
    }
    public Product(String iid,String iname, String iplace ,int icount, int idate){
        id = iid;
        name = iname;
        count = icount;
        placedetail = iplace;
        date = idate;
    }
    public String getId() { return id; }
    public String getName(){
        return name;
    }
    public String getDetailPlace(){
        return placedetail;
    }
    public int getCount(){
        return count;
    }
    public void setName(String sname){
        name = sname;
    }
    public void setCount(int scount){
        count = scount;
    }
    public void setPlacedetail(String splace){
        placedetail = splace;
    }
    public void setPlacelog(String splog){
        placedetail = splog;
    }
    public void upCount(){
        count += 1;
    }
    public void downCount(){
        count -= 1;
    }
    public int getDate(){
        return date;
    }

}