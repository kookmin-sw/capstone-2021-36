package com.example.capstone_36team;

public class Product {
    String name;
    int count;
    int date = 0;
    public Product(String iname, int icount){
        name = iname;
        count = icount;
    }
    public Product(String iname, int icount, int idate){
        name = iname;
        count = icount;
        date = idate;
    }
    public String getName(){
        return name;
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