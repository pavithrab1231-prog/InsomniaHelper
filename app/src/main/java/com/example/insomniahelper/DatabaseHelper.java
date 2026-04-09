package com.example.insomniahelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "insomnia.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_USERS = "users";
    private static final String TABLE_JOURNAL = "journal";

    private static DatabaseHelper instance;
    private SQLiteDatabase mDatabase;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = super.getWritableDatabase();
        }
        return mDatabase;
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = super.getReadableDatabase();
        }
        return mDatabase;
    }

    // Prevents manual closing of the database helper from other parts of the app
    @Override
    public synchronized void close() {
        // Do nothing to keep the singleton connection alive
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT UNIQUE, password TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_JOURNAL + " (id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT UNIQUE, mood INTEGER, sleep_quality INTEGER, note TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNAL);
        onCreate(db);
    }

    public boolean registerUser(String username, String email, String password) {
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("password", password);
        return getWritableDatabase().insert(TABLE_USERS, null, values) != -1;
    }

    public boolean checkUser(String email, String password) {
        Cursor cursor = null;
        try {
            cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email=? AND password=?", new String[]{email, password});
            return cursor != null && cursor.getCount() > 0;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public boolean saveJournalEntry(String date, int mood, int sleepQuality, String note) {
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("mood", mood);
        values.put("sleep_quality", sleepQuality);
        values.put("note", note);

        Cursor cursor = null;
        try {
            cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_JOURNAL + " WHERE date=?", new String[]{date});
            if (cursor != null && cursor.getCount() > 0) {
                return getWritableDatabase().update(TABLE_JOURNAL, values, "date=?", new String[]{date}) > 0;
            } else {
                return getWritableDatabase().insert(TABLE_JOURNAL, null, values) != -1;
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public Cursor getTodayEntry(String date) {
        return getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_JOURNAL + " WHERE date=?", new String[]{date});
    }

    public Cursor getAllEntries() {
        return getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_JOURNAL + " ORDER BY date DESC LIMIT 7", null);
    }

    public int getDaysLoggedCount() {
        Cursor cursor = null;
        try {
            cursor = getReadableDatabase().rawQuery("SELECT COUNT(*) FROM " + TABLE_JOURNAL, null);
            if (cursor != null && cursor.moveToFirst()) return cursor.getInt(0);
            return 0;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public float getAverageMood() {
        Cursor cursor = null;
        try {
            cursor = getReadableDatabase().rawQuery("SELECT AVG(mood) FROM " + TABLE_JOURNAL, null);
            if (cursor != null && cursor.moveToFirst()) return cursor.getFloat(0);
            return 0;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public float getAverageSleep() {
        Cursor cursor = null;
        try {
            cursor = getReadableDatabase().rawQuery("SELECT AVG(sleep_quality) FROM " + TABLE_JOURNAL, null);
            if (cursor != null && cursor.moveToFirst()) return cursor.getFloat(0);
            return 0;
        } finally {
            if (cursor != null) cursor.close();
        }
    }
}