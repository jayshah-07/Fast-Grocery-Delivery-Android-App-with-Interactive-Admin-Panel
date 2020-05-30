
package com.grocery.food.Model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Productinfo {

    @SerializedName("product_image")
    private String mProductImage;
    @SerializedName("product_name")
    private String mProductName;
    @SerializedName("product_price")
    private String mProductPrice;
    @SerializedName("product_qty")
    private String mProductQty;
    @SerializedName("product_weight")
    private String mProductWeight;
    @SerializedName("discount")
    private int Discount;

    public String getProductImage() {
        return mProductImage;
    }

    public void setProductImage(String productImage) {
        mProductImage = productImage;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public String getProductPrice() {
        return mProductPrice;
    }

    public void setProductPrice(String productPrice) {
        mProductPrice = productPrice;
    }

    public String getProductQty() {
        return mProductQty;
    }

    public void setProductQty(String productQty) {
        mProductQty = productQty;
    }

    public String getProductWeight() {
        return mProductWeight;
    }

    public void setProductWeight(String productWeight) {
        mProductWeight = productWeight;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }
}
