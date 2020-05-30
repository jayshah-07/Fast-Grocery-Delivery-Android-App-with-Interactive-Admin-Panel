package com.grocery.food.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Noti implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String Title;

    @SerializedName("img")
    @Expose
    private String Img;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("date")
    @Expose
    private String Date;

    @SerializedName("IS_READ")
    @Expose
    private int ISread;

    protected Noti(Parcel in) {
        id = in.readString();
        Title = in.readString();
        Img = in.readString();
        msg = in.readString();
        Date = in.readString();
        ISread = in.readInt();
    }

    public static final Creator<Noti> CREATOR = new Creator<Noti>() {
        @Override
        public Noti createFromParcel(Parcel in) {
            return new Noti(in);
        }

        @Override
        public Noti[] newArray(int size) {
            return new Noti[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getISread() {
        return ISread;
    }

    public void setISread(int ISread) {
        this.ISread = ISread;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(Title);
        dest.writeString(Img);
        dest.writeString(msg);
        dest.writeString(Date);
        dest.writeInt(ISread);
    }
}