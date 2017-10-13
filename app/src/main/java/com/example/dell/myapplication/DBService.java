package com.example.dell.myapplication;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;

public class DBService {
    static SQLiteDatabase db_master;
    public final static String PATH_DB= Environment.getExternalStorageDirectory().getPath()+"/UserDB";

    public static void createDBAndTable(){
        String tblName="tblUser";
        try {
            db_master = SQLiteDatabase.openDatabase(PATH_DB, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            db_master.beginTransaction();
            String checkSQL = "SELECT name FROM sqlite_master where type='table' AND name='" + tblName + "'";
            Cursor c = db_master.rawQuery(checkSQL, null);
            if (c.getCount() == 0) {
                String createTable = "CREATE TABLE " + tblName + " (syskey integer PRIMARY KEY AUTOINCREMENT,name text,age integer,email text,description text)";
                db_master.execSQL(createTable);
            }
            db_master.setTransactionSuccessful();
        }catch (SQLiteException e){
            Log.e("Open DB & Create Table:",e.getMessage());
        }finally {
            if(db_master!=null)
            db_master.endTransaction();
        }

    }
}
