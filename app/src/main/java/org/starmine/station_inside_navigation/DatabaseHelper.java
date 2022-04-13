package org.starmine.station_inside_navigation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Search.db";
    private static final String DB_TABLE = "Search_History";

    private static final String NAME = "NAME";

    private static final String CREEATE_TABLE = "CREATE TABLE " + DB_TABLE +
            " (" + NAME + "TEXT " + ")";

    public DatabaseHelper(Context context){
        super (context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREEATE_TABLE);
    }

    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int i, int il) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        onCreate(sqLiteDatabase);
    }

    public boolean insertData (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);

        long result = db.insert(DB_TABLE, null, contentValues);

        return result != -1; // if result = -1 데이터 안넣어짐
    }

    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + DB_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }
}
