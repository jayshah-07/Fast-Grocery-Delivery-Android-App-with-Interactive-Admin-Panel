
package com.grocery.food.Model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class TimeDatum {

    @SerializedName("id")
    private String mId;
    @SerializedName("maxtime")
    private String mMaxtime;
    @SerializedName("mintime")
    private String mMintime;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getMaxtime() {
        return mMaxtime;
    }

    public void setMaxtime(String maxtime) {
        mMaxtime = maxtime;
    }

    public String getMintime() {
        return mMintime;
    }

    public void setMintime(String mintime) {
        mMintime = mintime;
    }

}
