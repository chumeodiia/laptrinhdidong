package com.example.recyclerviewwithindicator_search.model;

import java.io.Serializable;

public class IconModel implements Serializable {
    private int imgID;
    private String desc;

    public IconModel(int imgID, String desc) {
        this.imgID = imgID;
        this.desc = desc;
    }

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
