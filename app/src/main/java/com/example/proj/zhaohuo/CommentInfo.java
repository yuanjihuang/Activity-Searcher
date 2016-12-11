package com.example.proj.zhaohuo;

/**
 * Created by Eafun on 2016/12/11.
 */

public class CommentInfo {
    private int userImgID;
    private String userImgUrl;
    private String userName;
    private String comment;

    public CommentInfo(int userImgID, String userImgUrl, String userName, String comment){
        this.userImgID = userImgID;
        this.userImgUrl = userImgUrl;
        this.userName = userName;
        this.comment = comment;
    }

    public int getUserImgID(){
        return userImgID;
    }

    public String getUserImgUrl(){
        return userImgUrl;
    }

    public String getUserName(){
        return userName;
    }

    public String getComment(){
        return comment;
    }
}
