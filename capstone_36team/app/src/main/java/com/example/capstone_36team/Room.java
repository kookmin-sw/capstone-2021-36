
package com.example.capstone_36team;
import com.example.capstone_36team.Product;

import java.util.ArrayList;

public class Room {
    String name;

    ArrayList<Product> Plist = new ArrayList<Product>();
    Product p;
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
    public void delProduct(String dname){
        for(int i=0;i<Plist.size();i++){
            p = Plist.get(i);
            String str = Plist.get(i).getName();
            if(str.equals(dname)) {
                Plist.remove(i);
            }
        }
    }
    public Product searchProduct(String sname){
        for(int i=0;i<Plist.size();i++){
            String str = Plist.get(i).getName();
            if(str.equals(sname)){
                return Plist.get(i);
            };
        }
        return null;
    };
}