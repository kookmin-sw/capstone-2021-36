package com.example.capstone_36team;

import android.app.Application;

public class GlobalVariable extends Application {
    private String familyid;

    public String getId() {
        return familyid;
    }
    public void setId(String id) {
        this.familyid = id; 
    }
}
