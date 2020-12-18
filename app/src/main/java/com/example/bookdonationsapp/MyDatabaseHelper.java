package com.example.bookdonationsapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String USERS_TABLE_NAME = "register";
    // register table columns
    public static final String COLUMN_1 = "ID";
    public static final String COLUMN_2 = "username";
    public static final String COLUMN_3 = "password";
    private static final String DATABASE_NAME = "BookDonations.db";
    private static final int DATABASE_VERSION = 3;
    // table names
    private static final String TABLE_NAME = "donations";
    // donations table columns
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_STUDENT_NAME = "studentName";
    private static final String COLUMN_STUDENT_ID = "studentID";
    private static final String COLUMN_BOOK_TITLE = "bookTitle";
    private static final String COLUMN_BOOK_AUTHOR = "bookAuthor";
    private static final String COLUMN_NUMOFBOOKS = "numOfBooks";
    // this file defines the database scheme
    private Context context;


    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating donations table
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_STUDENT_NAME + " TEXT, " +
                        COLUMN_STUDENT_ID + " TEXT, " +
                        COLUMN_BOOK_TITLE + " TEXT, " +
                        COLUMN_BOOK_AUTHOR + " TEXT, " +
                        COLUMN_NUMOFBOOKS + " INTEGER);";

        // creating users table
        String register_query =
                "CREATE TABLE " + USERS_TABLE_NAME +
                        " (" + COLUMN_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_2 + " TEXT, " +
                        COLUMN_3 + " TEXT);";

        // creating required tables
        db.execSQL(query);
        db.execSQL(register_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);

        // create new tables
        onCreate(db);
    }

    // adding a donation to the database
    void addBook(String studentName, String studentID, String bookTitle, String bookAuthor, int numOfBooks) {
        SQLiteDatabase db = this.getWritableDatabase();
        // we store all the data in the contentValues object to be passed to the database table
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_STUDENT_NAME, studentName);
        cv.put(COLUMN_STUDENT_ID, studentID);
        cv.put(COLUMN_BOOK_TITLE, bookTitle);
        cv.put(COLUMN_BOOK_AUTHOR, bookAuthor);
        cv.put(COLUMN_NUMOFBOOKS, numOfBooks);

        // inserting data into the table using db object
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to add", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        }

    }

    // method returns a cursor object to read data from the table
    // this method is called inside MainActivity
    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        // sqlite database object
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    // update data method
    void updateData(String row_id, String student_Name, String student_ID, String book_Title, String book_Author, String num_Of_Books) {

        // database object
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // storing values inside the object
        cv.put(COLUMN_STUDENT_NAME, student_Name);
        cv.put(COLUMN_STUDENT_ID, student_ID);
        cv.put(COLUMN_BOOK_TITLE, book_Title);
        cv.put(COLUMN_BOOK_AUTHOR, book_Author);
        cv.put(COLUMN_NUMOFBOOKS, num_Of_Books);

        long result = db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Donation " + row_id + " Successfully updated ", Toast.LENGTH_SHORT).show();

        }
    }

    // delete record method
    public void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
        }
    }

    // delete all donations
    void deleteAllDonations() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    // create user method
    public long addUser(String user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("username", user);
        contentValues.put("password", password);
        long res = db.insert(USERS_TABLE_NAME, null, contentValues);
        db.close();
        return res;
    }

    // checking if a user exists or not so that we go to login screen
    public boolean checkUser(String username, String password) {
        String[] columns = {COLUMN_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COLUMN_2 + "=?" + " and " + COLUMN_3 + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0)
            return true;
        else
            return false;
    }
}
