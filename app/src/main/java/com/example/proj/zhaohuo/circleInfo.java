package com.example.proj.zhaohuo;

/**
 * Created by Administrator on 2016/12/10.
 */

public class circleInfo{
    private int imgID;
    private String circleName;
    private String briIntro;
    public circleInfo(int imgID, String circleName, String briIntro){
        this.imgID = imgID;
        this.circleName = circleName;
        this.briIntro = briIntro;
    }
    public int getImgID(){
        return imgID;
    }
    public String getCircleName(){
        return circleName;
    }
    public String getBriIntro(){
        return briIntro;
    }
}