package com.grocery.food.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.grocery.food.Activity.HomeActivity.txt_countcard;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mydatabase.db";
    public static final String TABLE_NAME = "items";

    public static final String ICOL_1 = "ID";
    public static final String ICOL_2 = "PID";
    public static final String ICOL_3 = "image";
    public static final String ICOL_4 = "title";
    public static final String ICOL_5 = "weight";
    public static final String ICOL_6 = "cost";
    public static final String ICOL_7 = "qty";
    public static final String ICOL_8 = "discount";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, PID TEXT , image TEXT ,title TEXT , weight TEXT , cost TEXT, qty TEXT , discount int )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(MyCart rModel) {
        if (getID(rModel.getPID(), rModel.getCost()) == -1) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ICOL_2, rModel.getPID());
            contentValues.put(ICOL_3, rModel.getImage());
            contentValues.put(ICOL_4, rModel.getTitle());
            contentValues.put(ICOL_5, rModel.getWeight());
            contentValues.put(ICOL_6, rModel.getCost());
            contentValues.put(ICOL_7, rModel.getQty());
            contentValues.put(ICOL_8, rModel.getDiscount());
            long result = db.insert(TABLE_NAME, null, contentValues);

            if (result == -1) {
                return false;
            } else {
                Cursor resw = getAllData();
                txt_countcard.setText("" + resw.getCount());
                return true;
            }
        } else {

            return updateData(rModel.getPID(), rModel.getCost(), rModel.getQty());
        }
    }

    private int getID(String pid, String cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{"PID"}, "PID =? AND cost =? ", new String[]{pid, cost}, null, null, null, null);
        if (c.moveToFirst()) //if the row exist then return the id
            return c.getInt(c.getColumnIndex("PID"));
        return -1;
    }

    public int getCard(String pid, String cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{"qty"}, "PID =? AND cost =? ", new String[]{pid, cost}, null, null, null, null);
        if (c.moveToFirst()) { //if the row exist then return the id
            return c.getInt(c.getColumnIndex("qty"));
        } else {
            return -1;

        }
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }


    public boolean updateData(String id, String cost, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ICOL_7, status);
        db.update(TABLE_NAME, contentValues, "PID = ? AND cost =?", new String[]{id, cost});
        Cursor res = getAllData();
        txt_countcard.setText("" + res.getCount());
        return true;

    }

    public void DeleteCard(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        txt_countcard.setText("0");
    }

    public Integer deleteRData(String id, String cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer a = db.delete(TABLE_NAME, "PID = ? AND cost =?", new String[]{id, cost});
        Cursor res = getAllData();
        txt_countcard.setText("" + res.getCount());
        return a;
    }


}