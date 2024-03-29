package org.starmine.station_inside_navigation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import piruincopy.quickaction.ActionItem;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Search.db";
    private static final String DB_TABLE = "Search_History";
    private static final String BOOKMARK_TABLE = "subway_bookmark";

    private static final String ID = "ID";
    private static final String NAME = "NAME";

    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE +
            " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT " + ")";

    private static final String CREATE_BOOKMARK = "CREATE TABLE " + BOOKMARK_TABLE +
            " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT " + ")";


    public DatabaseHelper(Context context){
        super (context, DB_NAME, null, 14);
    }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_BOOKMARK);
    }


    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int i, int il) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BOOKMARK_TABLE);

        onCreate(sqLiteDatabase);
    }

    public boolean insertData (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String query1 = "select * from " + DB_TABLE
                + " where " + NAME + "= '"+ name + "'";
        String query2 = "select * from " + BOOKMARK_TABLE
                + " where " + NAME + "= '"+ name + "'";
        Cursor cursor1 = db.rawQuery(query1, null);
        Cursor cursor2 = db.rawQuery(query2, null);
        if(cursor1.getCount() == 0 && cursor2.getCount() == 0) {
            contentValues.put(NAME, name);
        }
        long result = db.insert(DB_TABLE, null, contentValues);
        return result != -1; // if result = -1 데이터 안넣어짐
    }

    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + DB_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public Cursor BookmarkviewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + BOOKMARK_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public int getTableRowCount() {
        String countQuery = "SELECT * FROM " + DB_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    public int BookmarkgetTableRowCount() {
        String countQuery = "SELECT * FROM " + BOOKMARK_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    public void deleteData (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteDB = "delete from " + DB_TABLE + " where " + NAME + " = '" + name + "'";
        db.execSQL(deleteDB);
    }

    public boolean insertBookmark (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String query = "select * from " + BOOKMARK_TABLE
                + " where " + NAME + "= '"+ name + "'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() == 0) {
            contentValues.put(NAME, name);
        }
        long result = db.insert(BOOKMARK_TABLE, null, contentValues);
        return result != -1; // if result = -1 데이터 안넣어짐
    }

    public void deleteBookmark (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteDB = "delete from " + BOOKMARK_TABLE + " where " + NAME + " = '" + name + "'";
        db.execSQL(deleteDB);
    }

    public void alldelete(){
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteBookmark = "delete from " + BOOKMARK_TABLE;
        String deleteHistory = "delete from " + DB_TABLE;
        db.execSQL(deleteHistory);
        db.execSQL(deleteBookmark);
    }

    public int BookmarkBtn(String curStation){
        String countQuery = "select * from " + BOOKMARK_TABLE
                + " where " + NAME + "= '"+ curStation + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int num = 0;

        if (cursor.getCount() > 0)
            num = 1;
        else if (cursor.getCount() == 0)
            num = 0;

        return num;
    }
}
