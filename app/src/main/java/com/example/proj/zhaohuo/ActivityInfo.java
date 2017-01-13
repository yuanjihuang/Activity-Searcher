package com.example.proj.zhaohuo;

/**
 * Created by Eafun on 2016/12/9.
 */

public class ActivityInfo {
    private Integer imgID;
    private String imgUrl;
    private String name;
    private String info;
    private String remark;
    private Integer follow;

    public ActivityInfo(int imgID, String imgUrl,String name, String info, String remark, int follow){
        this.imgID = imgID;
        this.imgUrl = imgUrl;
        this.name = name;
        this.info = info;
        this.remark = remark;
        this.follow = follow;
    }

    public Integer getImgID(){
        return imgID;
    }

    public String getImgUrl(){
        return imgUrl;
    }

    public String getName(){
        return name;
    }

    public String getInfo(){
        return info;
    }

    public String getRemark(){
        return remark;
    }

    public Integer isFollow(){
        return follow;
    }
}
