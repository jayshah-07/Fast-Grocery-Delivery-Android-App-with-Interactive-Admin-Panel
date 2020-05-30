
package com.grocery.food.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Home {



    @SerializedName("ResponseCode")
    private String mResponseCode;
    @SerializedName("ResponseMsg")
    private String mResponseMsg;
    @SerializedName("Result")
    private String mResult;

    @SerializedName("ResultData")
    @Expose
    private ResultHome resultData;

    public ResultHome getResultHome() {
        return resultData;
    }

    public void setResultHome(ResultHome resultData) {
        this.resultData = resultData;
    }

    public String getmResponseCode() {
        return mResponseCode;
    }

    public void setmResponseCode(String mResponseCode) {
        this.mResponseCode = mResponseCode;
    }

    public String getmResponseMsg() {
        return mResponseMsg;
    }

    public void setmResponseMsg(String mResponseMsg) {
        this.mResponseMsg = mResponseMsg;
    }

    public String getmResult() {
        return mResult;
    }

    public void setmResult(String mResult) {
        this.mResult = mResult;
    }
}
