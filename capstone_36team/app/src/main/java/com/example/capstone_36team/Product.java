package com.example.capstone_36team;

public class Product {
    String name;
    int count;
    public Product(String iname, int icount){
        name = iname;
        count = icount;
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
}
