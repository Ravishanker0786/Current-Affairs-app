package com.prinfosys.news48.SqlLite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelperLike extends SQLiteOpenHelper {

    public static final String DATABASE_NAME_1 = "currentLike.db";
    public static final String CONTACTS_TABLE_NAME_1 = "liketab";
    public static final String CONTACTS_COLUMN_ID_1 = "id";
    public static final String CONTACTS_COLUMN_NAME_1 = "uno";
    private HashMap hp;

    public DBHelperLike(Context context) {
        super(context, DATABASE_NAME_1 , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table liketab " +
                        "(id integer primary key, uno text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS liketab");
        onCreate(db);
    }

    public boolean insertLike (String uno) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("uno", uno);
        db.insert("liketab", null, contentValues);
        return true;
    }

//    public Cursor getData(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
//        return res;
//    }

    public int numberOfRowsLike(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME_1);
        return numRows;
    }

//    public boolean updateContact (Integer id, String name) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("name", name);
//        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
//        return true;
//    }

    public Integer deleteLike (String uno) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("liketab",
                "uno = ? ",
                new String[] { uno });
    }

    public ArrayList<String> getAllLikes() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from liketab", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME_1)));
            res.moveToNext();
        }
        return array_list;
    }

    public String likeDislike(String id){
        return id;
    }
}
