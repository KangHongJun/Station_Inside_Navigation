package org.starmine.station_inside_navigation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Search.db";
    private static final String DB_TABLE = "Search_History";

    private static final String ID = "ID";
    private static final String NAME = "NAME";

    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE +
            " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT " + ")";

    public DatabaseHelper(Context context){
        super (context, DB_NAME, null, 3);
    }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int i, int il) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        onCreate(sqLiteDatabase);
    }

    public boolean insertData (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String query = "select ID from " + DB_TABLE
                + " where " + NAME + "= '"+ name + "'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() == 0) {
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

    public int getTableRowCount() {
        String countQuery = "SELECT * FROM " + DB_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }
}
