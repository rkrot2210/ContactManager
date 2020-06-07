package com.rk.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.Currency;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "DB_CONTACT";
    private static final String TABLE_NAME = "PhoneBook";

    private static final String ID = "id_contact";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String EMAIL = "email";

    SQLiteDatabase sqLiteDatabase;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db = getWritableDatabase();
        db.execSQL("CREATE TABLE "+TABLE_NAME+" ( "+ID +" TEXT PRIMARY KEY, "+ NAME +" TEXT, "
         + SURNAME +" TEXT, "+ EMAIL+" TEXT "+")");
        ContentValues newValues = new ContentValues();
        newValues.put(ID,"(xxx)-xxx-xx-xx");
        newValues.put(NAME,"User");
        newValues.put(SURNAME,"SurnameUser");
        newValues.put(EMAIL,"example@gmail.com");

        db.insert(TABLE_NAME,null,newValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void deleteDB()
    {
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE_NAME);
    }

    public void insertContactDB(Contact contact){
        ContentValues newValues = new ContentValues();
        newValues.put(ID,contact.getPhoneNumber());
        newValues.put(NAME,contact.getName());
        newValues.put(SURNAME,contact.getSurname());
        newValues.put(EMAIL,contact.getEmail());
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(TABLE_NAME,null,newValues);
      //  sqLiteDatabase.close();
    }
    public Cursor getTableData(){
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,  new String[]{ID, NAME, SURNAME, EMAIL}, null, null, null, null, null);
       // sqLiteDatabase.close();
        return cursor;
    }
    public void deleteContact(Contact contact)
    {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,
                ID+" = ?",
                new String[] {contact.getPhoneNumber()});
    }
    public void updateContact(String oldId,String newID,String newName,String newSurname,String newEmail)
    {
        ContentValues newValues = new ContentValues();
        newValues.put(ID,newID);
        newValues.put(NAME,newName);
        newValues.put(SURNAME,newSurname);
        newValues.put(EMAIL,newEmail);
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(TABLE_NAME,newValues,
                ID+" = ?", new String[] {oldId});
    }
}
