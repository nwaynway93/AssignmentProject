package com.example.dell.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

public class UserService {

    static SQLiteDatabase db;
    final static String PATH_DB=DBService.PATH_DB;

    public static boolean saveUser(UserData data){
        boolean flag=false;
        db=SQLiteDatabase.openDatabase(PATH_DB,null,SQLiteDatabase.CREATE_IF_NECESSARY);
        db.beginTransaction();
        try {
            String insertSQL = "INSERT INTO tblUser (name,age,email,description) VALUES ('" + data.getName() + "','" +
                    data.getAge() + "','" +data.getEmail() + "','" +data.getDescription() + "')";
            db.execSQL(insertSQL);

            db.setTransactionSuccessful();

            flag=true;
        }catch (Exception e){
            // Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
            db.endTransaction();
        }
        return flag;
    }


    //getalldata
    public static ArrayList<UserData> retrieveUser() {
        db=SQLiteDatabase.openDatabase(PATH_DB,null,SQLiteDatabase.CREATE_IF_NECESSARY);

        ArrayList<UserData> userDataList = new ArrayList<>();

        String selectQuery = "SELECT name,age,email,description FROM tblUser";
        try {
            int usernameCol;
            int userageCol ;
            int useremailCol;
            int userdescriptionCol;

            db.beginTransaction();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor!=null){
                usernameCol = cursor.getColumnIndex("name");
                userageCol = cursor.getColumnIndex("age");
                useremailCol = cursor.getColumnIndex("email");
                userdescriptionCol = cursor.getColumnIndex("description");

                while(cursor.moveToNext()){
                    UserData usrData = new UserData();
                    usrData.setName(cursor.getString(usernameCol));
                    usrData.setAge(cursor.getInt(userageCol));
                    usrData.setEmail(cursor.getString(useremailCol));
                    usrData.setDescription(cursor.getString(userdescriptionCol));
                    userDataList.add(usrData);
                }
                cursor.close();
            }

            db.endTransaction();
            db.close();
        }
        catch (SQLiteException e) {
            Log.i("SQLiteException: ",e.getMessage());
            db.close();
        }
        return  userDataList;
    }


}
