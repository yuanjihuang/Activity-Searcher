package com.example.proj.zhaohuo;

/**
 * Created by Administrator on 2016/12/10.
 */

public class circleInfo{
    private int imgID;
    private String name;
    private String content;
    public circleInfo(int imgID, String circleName, String briIntro){
        this.imgID = imgID;
        this.name = circleName;
        this.content = briIntro;
    }
    public int getImgID(){
        return imgID;
    }
    public String getName(){
        return name;
    }
    public String getContent(){
        return content;
    }
}