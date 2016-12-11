package com.example.proj.zhaohuo;

/**
 * Created by Administrator on 2016/12/11.
 */

public class circleCommentatorInfo {
    private int commentatorImgID;
    private String commentatorName;
    private String commentContent;

    public circleCommentatorInfo(int imgID, String name, String commentContent){
        this.commentatorImgID = imgID;
        this.commentatorName = name;
        this.commentContent = commentContent;
    }
    public int getCommentatorImgID(){
        return commentatorImgID;
    }
    public String getCommentatorName(){
        return commentatorName;
    }
    public String getCommentContent(){
        return commentContent;
    }
}
