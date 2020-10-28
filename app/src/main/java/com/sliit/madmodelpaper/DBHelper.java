package com.sliit.madmodelpaper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UserInfo.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserProfile.Users.TABLE_NAME + " (" +
                    UserProfile.Users.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserProfile.Users.COLUMN_USERNAME + " TEXT," +
                    UserProfile.Users.COLUMN_PASSWORD + " TEXT," +
                    UserProfile.Users.COLUMN_DOB + " TEXT," +
                    UserProfile.Users.COLUMN_GENDER + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserProfile.Users.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onDowngrade(db, oldVersion, newVersion);
    }

    public long addInfo(String username, String password, String dateOfBirth, String gender) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(UserProfile.Users.COLUMN_USERNAME, username);
        values.put(UserProfile.Users.COLUMN_PASSWORD, password);
        values.put(UserProfile.Users.COLUMN_DOB, dateOfBirth);
        values.put(UserProfile.Users.COLUMN_GENDER, gender);

        // Insert the new row, returning the primary key value of the new row
        long result = db.insert(UserProfile.Users.TABLE_NAME, null, values);

        return result;
    }

    public boolean updateInfor(String id, String username, String password, String dateOfBirth, String gender) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(UserProfile.Users.COLUMN_USERNAME, username);
        values.put(UserProfile.Users.COLUMN_DOB, dateOfBirth);
        values.put(UserProfile.Users.COLUMN_PASSWORD, password);
        values.put(UserProfile.Users.COLUMN_GENDER, gender);

        // Which row to update, based on the title
        String whereClause = UserProfile.Users.COLUMN_ID + " LIKE ?";
        String[] whereArgs = {id};

        // Insert the new row, returning the primary key value of the new row
        int result = db.update(UserProfile.Users.TABLE_NAME, values, whereClause, whereArgs);

        if(result > 0) {
            return true;
        }
        else{
            return false;
        }
    }

    public List readAllInfor(){
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.Users.COLUMN_USERNAME,
                UserProfile.Users.COLUMN_DOB,
                UserProfile.Users.COLUMN_PASSWORD,
                UserProfile.Users.COLUMN_GENDER
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = UserProfile.Users.COLUMN_ID + " ASC";

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,   // The table to query
                projection, // The array of columns to return (pass null to get all)
                null,   // The columns for the WHERE clause
                null,   // The values for the WHERE clause
                null,   // don't group the rows
                null,   // don't filter by row groups
                sortOrder   // The sort order
        );

        List user = new ArrayList<>();
        while(cursor.moveToNext()) {
            long id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(
                            UserProfile.Users.COLUMN_ID));
            user.add(id);
        }
        cursor.close();

        return user;
    }

    public List readAllInfor(String un){
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.Users.COLUMN_ID,
                UserProfile.Users.COLUMN_USERNAME,
                UserProfile.Users.COLUMN_PASSWORD,
                UserProfile.Users.COLUMN_DOB,
                UserProfile.Users.COLUMN_GENDER
        };

        String selection = UserProfile.Users.COLUMN_USERNAME + " LIKE ?";
        String[] selectionArgs = {un};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = UserProfile.Users.COLUMN_ID + " ASC";

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,   // The table to query
                projection, // The array of columns to return (pass null to get all)
                selection,   // The columns for the WHERE clause
                selectionArgs,   // The values for the WHERE clause
                null,   // don't group the rows
                null,   // don't filter by row groups
                sortOrder   // The sort order
        );

        List user = new ArrayList<>();
        while(cursor.moveToNext()) {
            int ID = cursor.getInt(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_ID));
            String USERNAME = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_USERNAME));
            String PASSWORD = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_PASSWORD));
            String DOB = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_DOB));
            String GENDER = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_GENDER));

            user.add(ID);
            user.add(USERNAME);
            user.add(PASSWORD);
            user.add(DOB);
            user.add(GENDER);
        }
        cursor.close();

        return user;
    }

    public boolean deleteInfo(String id){
        SQLiteDatabase db = getReadableDatabase();

        // Define 'where' part of query.
        String selection = UserProfile.Users.COLUMN_ID + " LIKE ?";

        // Specify arguments in placeholder order.
        String[] selectionArgs = {id};

        // Issue SQL statement.
        int result = db.delete(UserProfile.Users.TABLE_NAME,
                                selection,
                                selectionArgs);

        if (result > 0){
            return true;
        }
        else {
            return false;
        }
    }
}
