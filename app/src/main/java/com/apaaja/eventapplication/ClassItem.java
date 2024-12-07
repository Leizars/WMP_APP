package com.apaaja.eventapplication;

public class ClassItem {
    private String className;
    private String location;
   //private String time;

    public ClassItem(String className, String location, String time) {
        this.className = className;
        this.location = location;
        //this.time = time;
    }

    public String getClassName() {
        return className;
    }

    public String getLocation() {
        return location;
    }

    //public String getTime() {
        //return time;
    //}
}

