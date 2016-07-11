package com.example.yelowflash.assignment;

/**
 * Created by YELOWFLASH on 07/09/2016.
 */
public class Issue {
    String title, name, desc;

    public Issue(String title, String name, String desc) {
        this.title = title;
        this.name = name;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
