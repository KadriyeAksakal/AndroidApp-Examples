package com.example.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME="USER.DB";
    public final static String TABLE_NAME="USERS";
    public final static String COL_ID="ID";
    public final static String COL_NAMESURNAME="NAMESURNAME";
    public final static String COL_PHONE="PHONE";
    SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY=" CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAMESURNAME +", "+ COL_PHONE +", TEXT)";
        db.execSQL(CREATE_TABLE_QUERY);
        this.database=db;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String UPGRADE_QUERY= " DROP TABLE IF EXİSTS "+ TABLE_NAME;
        db.execSQL(UPGRADE_QUERY);
        this.onCreate(db);
    }

    public boolean insertUser(String namesurname, String phone){
        database=this.getWritableDatabase();
        String query= "SELECT * FROM " + TABLE_NAME;
        Cursor cursor=database.rawQuery(query,null);
        int id=cursor.getCount();


        ContentValues value =new ContentValues();
        value.put(COL_ID,id);
        value.put(COL_NAMESURNAME,namesurname);
        value.put(COL_PHONE,phone);

        Long result= database.insert(TABLE_NAME,null,value);
        database.close();

        if(result == -1)
            return  false;
        else
            return true;

    }

    public String findUser(String nameSurname){
        database=this.getReadableDatabase();
        String query= " SELECT * FROM "+ TABLE_NAME +" WHERE NAMESURNAME = '"+nameSurname+"'" ;
        Cursor cursor=database.rawQuery(query,null);

        if(cursor.moveToFirst()){
            database.close();
            return cursor.getString(4);
        }
        else{
            database.close();
            return "not found";
        }
    }

    public Integer deleteUser(int id){
        database=this.getWritableDatabase();
        return database.delete(TABLE_NAME, COL_ID + " = ? ", new String[]{Integer.toString(id)});

       /* String INSERT_QUERY = "DELETE FROM "+TABLE_NAME+" WHERE NAME= '"+ id+"'";
        Cursor cursor=database.rawQuery(INSERT_QUERY,null);
       // Integer result=database.delete(TABLE_NAME,"id="+id,null);
        database.close();
        if (cursor.moveToFirst()){
            database.close();
            return true;
        }else{
            return false;
        }*/
      /*  if(result==-1)
            return false;
        else
            return true;
*/
    }

    public void updateUser(String id, String nameSurname, String phone){
        database=this.getWritableDatabase();
        try{
            ContentValues value= new ContentValues();
            value.put(COL_ID,id);
            value.put(COL_NAMESURNAME,nameSurname);
            value.put(COL_PHONE,phone);

            String where=COL_ID+ " = '" +id+ "'";
            database.update(TABLE_NAME,value,where,null);
        }catch (Exception e){

        }
        database.close();
    }

    public ArrayList<String> viewUsers(){
        database=this.getReadableDatabase();
        String query= " SELECT * FROM "+ TABLE_NAME;
        Cursor cursor=database.rawQuery(query,null);
        ArrayList<String> nameSurname= new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                nameSurname.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        return nameSurname;
    }
}
