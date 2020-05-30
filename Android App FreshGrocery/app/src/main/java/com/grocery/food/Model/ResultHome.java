package com.grocery.food.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultHome {

    @SerializedName("Banner")
    private List<BannerItem> bannerItems;

    @SerializedName("Catlist")
    private List<CatItem> catItems;

    @SerializedName("Productlist")
    private List<ProductItem> productItems;

    @SerializedName("Remain_notification")
    private int RemainNotification;

    @SerializedName("currency")
    private String currency;


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getRemainNotification() {
        return RemainNotification;
    }

    public void setRemainNotification(int remainNotification) {
        RemainNotification = remainNotification;
    }

    public List<BannerItem> getBannerItems() {
        return bannerItems;
    }

    public void setBannerItems(List<BannerItem> bannerItems) {
        this.bannerItems = bannerItems;
    }

    public List<CatItem> getCatItems() {
        return catItems;
    }

    public void setCatItems(List<CatItem> catItems) {
        this.catItems = catItems;
    }

    public List<ProductItem> getProductItems() {
        return productItems;
    }

    public void setProductItems(List<ProductItem> productItems) {
        this.productItems = productItems;
    }
}
