package com.example.capstone_36team;

import java.util.ArrayList;

public class Room {
    String name;
    ArrayList Plist = new ArrayList();
    public Room(String rname){
        name = rname;
    }
    public String getName(){
        return name;
    }
    public ArrayList<Product> getPlist(){
        return Plist;
    }
    public void addProduct(Product p){
        Plist.add(p);
    }
    public void delProduct(){}
}
