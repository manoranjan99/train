package com.manoranjank.trianinfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Manoranjan K on 25-05-2019.
 */

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DB_NAME = "Trainbookdata";
    private static final int DB_VERSION = 1;

    DatabaseManager(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE STDATA("
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"NAME TEXT, "
                +"SEATNO INTEGER, "
                +"TRAIN TEXT,"
                +"PNR TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE TABLE STDATA("
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"NAME TEXT, "
                +"SEATNO INTEGER, "
                +"TRAIN TEXT,"
                +"PNR TEXT );");
    }

    public void insertdata(String name,int seatno,String train,String PNR)
    {
         SQLiteDatabase db=this.getWritableDatabase();
        ContentValues mDetails=new ContentValues();
        mDetails.put("NAME",name);
        mDetails.put("SEATNO",seatno);
        mDetails.put("TRAIN",train);
        mDetails.put("PNR",PNR);
         db.insert("STDATA",null,mDetails);
       //  db.close();
    }





    public void updatedata(int iid,String name,int seatno,String train,String PNR)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues updetails=new ContentValues();
        updetails.put("NAME",name);
        updetails.put("SEATNO",seatno);
        updetails.put("TRAIN",train);
        updetails.put("PNR",PNR);
        db.update("STDATA",updetails,"_id=?",new String[]{String.valueOf(iid)});
    }

    public Cursor getdata()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res = db.query("STDATA", new String[]{"_id", "NAME", "SEATNO", "TRAIN","PNR"}, null, null, null, null, null);
        return res;
    }


    public int getseatno(String namet)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        //String selectquery="SELECT COUNT(*) FROM STDATA WHERE TRAIN='"+namet+"'";
      //  select count(*) from users where uname='" + loginname + "' and pwd='" + loginpass +"'", null);
        //mCount.moveToFirst();
        Cursor res=db.rawQuery("SELECT COUNT(*) FROM STDATA WHERE TRAIN='"+namet+"'",null);
        res.moveToFirst();
        int count= res.getInt(0);
     //   Cursor res = db.query("STDATA",new String[]{"TRAIN"},namet,null,null,null,null);
        return count;

    }
    public void deletedata(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("STDATA", "_id = ?",new String[]{String.valueOf(id)});
    }

}
