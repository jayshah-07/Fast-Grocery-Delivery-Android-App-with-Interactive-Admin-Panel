
package com.grocery.food.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class MyOrder {

    @SerializedName("order_date")
    private String mOrderDate;
    @SerializedName("productinfo")
    private List<Productinfo> mProductinfo;
    @SerializedName("ResponseCode")
    private String mResponseCode;
    @SerializedName("ResponseMsg")
    private String mResponseMsg;
    @SerializedName("Result")
    private String mResult;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("timesloat")
    private String mTimesloat;
    @SerializedName("total_amt")
    private String mTotalAmt;

    @SerializedName("Israted")
    private String mIsrated;

    @SerializedName("orderid")
    private String mOrderid;
    @SerializedName("d_charge")
    private String dCharge;

    public String getdCharge() {
        return dCharge;
    }

    public void setdCharge(String dCharge) {
        this.dCharge = dCharge;
    }

    public String getmOrderid() {
        return mOrderid;
    }

    public void setmOrderid(String mOrderid) {
        this.mOrderid = mOrderid;
    }

    public String getmIsrated() {
        return mIsrated;
    }

    public void setmIsrated(String mIsrated) {
        this.mIsrated = mIsrated;
    }

    public String getOrderDate() {
        return mOrderDate;
    }

    public void setOrderDate(String orderDate) {
        mOrderDate = orderDate;
    }

    public List<Productinfo> getProductinfo() {
        return mProductinfo;
    }

    public void setProductinfo(List<Productinfo> productinfo) {
        mProductinfo = productinfo;
    }

    public String getResponseCode() {
        return mResponseCode;
    }

    public void setResponseCode(String responseCode) {
        mResponseCode = responseCode;
    }

    public String getResponseMsg() {
        return mResponseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        mResponseMsg = responseMsg;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTimesloat() {
        return mTimesloat;
    }

    public void setTimesloat(String timesloat) {
        mTimesloat = timesloat;
    }

    public String getTotalAmt() {
        return mTotalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        mTotalAmt = totalAmt;
    }

}
