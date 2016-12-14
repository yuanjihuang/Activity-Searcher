package com.example.proj.zhaohuo;

/**
 * Created by Administrator on 2016/12/11.
 */

public class circleFollowerInfo {
    private int followerImgID;
    private String followerName;

    public circleFollowerInfo(int imgID, String name){
        this.followerImgID = imgID;
        this.followerName = name;
    }
    public int getCommentatorImgID(){
        return followerImgID;
    }
    public String getCommentatorName(){
        return followerName;
    }
}
