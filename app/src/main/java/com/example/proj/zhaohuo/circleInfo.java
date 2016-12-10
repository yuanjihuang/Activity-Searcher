package com.example.proj.zhaohuo;

/**
 * Created by Administrator on 2016/12/10.
 */

public class circleInfo{
    private int imgID;
    private String circleName;
    private String relatedActivity;
    private String briIntro;
    public circleInfo(int imgID, String circleName, String relatedActivity, String briIntro){
        this.imgID = imgID;
        this.circleName = circleName;
        this.relatedActivity = relatedActivity;
        this.briIntro = briIntro;
    }
    public int getImgID(){
        return imgID;
    }
    public String getCircleName(){
        return circleName;
    }
    public String getRelatedActivity(){
        return relatedActivity;
    }
    public String getBriIntro(){
        return briIntro;
    }
}