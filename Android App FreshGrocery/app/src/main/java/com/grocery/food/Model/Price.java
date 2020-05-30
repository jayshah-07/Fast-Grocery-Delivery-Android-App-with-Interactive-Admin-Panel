
package com.grocery.food.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Price implements Parcelable{

    @SerializedName("product_price")
    private String mProductPrice;
    @SerializedName("product_type")
    private String mProductType;

    protected Price(Parcel in) {
        mProductPrice = in.readString();
        mProductType = in.readString();
    }


    public static final Creator<Price> CREATOR = new Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel in) {
            return new Price(in);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };

    public String getProductPrice() {
        return mProductPrice;
    }

    public void setProductPrice(String productPrice) {
        mProductPrice = productPrice;
    }

    public String getProductType() {
        return mProductType;
    }

    public void setProductType(String productType) {
        mProductType = productType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mProductPrice);
        dest.writeString(mProductType);
    }
}
