package com.example.proj.zhaohuo;

/**
 * Created by Eafun on 2016/12/9.
 */

public class ActivityInfo {
    private int imgID;
    private String name;
    private String info;
    private int follow;

    public ActivityInfo(int imgID, String name, String info, int follow){
        this.imgID = imgID;
        this.name = name;
        this.info = info;
        this.follow = follow;
    }

    public int getImgID(){
        return imgID;
    }

    public String getName(){
        return name;
    }

    public String getInfo(){
        return info;
    }

    public int isFollow(){
        return follow;
    }
}
