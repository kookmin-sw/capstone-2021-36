package com.example.capstone_36team;

import android.app.Application;

public class GlobalVariable extends Application {
    private String familyid;
    private String uid;

    public String getfamilyId() {
        return familyid;
    }
    public void setfamilyId(String id) {
        this.familyid = id; 
    }
    public String getuId() {
        return uid;
    }
    public void setuId(String id) {
        this.uid = id;
    }
}
