
package com.grocery.food.Model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BannerItem {

    @SerializedName("bimg")
    private String mBimg;
    @SerializedName("id")
    private String mId;

    public String getBimg() {
        return mBimg;
    }

    public void setBimg(String bimg) {
        mBimg = bimg;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

}
