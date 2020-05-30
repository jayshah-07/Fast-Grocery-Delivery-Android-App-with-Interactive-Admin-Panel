
package com.grocery.food.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class CatItem implements Serializable  {

    @SerializedName("catimg")
    private String mCatimg;
    @SerializedName("catname")
    private String mCatname;
    @SerializedName("id")
    private String mId;
    @SerializedName("count")
    private String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCatimg() {
        return mCatimg;
    }

    public void setCatimg(String catimg) {
        mCatimg = catimg;
    }

    public String getCatname() {
        return mCatname;
    }

    public void setCatname(String catname) {
        mCatname = catname;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

}
