package com.delta.irfan.alpha.dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by irfan on 12/13/2015.
 */
public final class CustomerBusinessLogic extends SQLiteOpenHelper {


    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "customers";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_LAT= "lat";
        public static final String COLUMN_NAME_LNG= "lng";
        public static String getColumnLatAddress() {
            return COLUMN_NAME_LAT;
        }

        public static String getColumnLngAddress() {
            return COLUMN_NAME_LNG;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        String name = "";
        String phone = "";
        String email = "";
        String address = "";

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        String lat = "";
        String lng = "";

    }
    private static final int DATABASE_VERSION = 6;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NAME_PHONE + TEXT_TYPE + " PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_ADDRESS + TEXT_TYPE  + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_LAT + TEXT_TYPE  + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_LNG + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
    public CustomerBusinessLogic(Context context) {
        super(context, FeedEntry.TABLE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void addCustomer(FeedEntry feedentry){
        deleteCustomer(feedentry);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", feedentry.getName());
        values.put("phone", feedentry.getPhone());
        values.put("address", feedentry.getAddress());
        values.put("email", feedentry.getEmail());

        db.insert(feedentry.TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    public void deleteCustomer(FeedEntry feedEntry) {
        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(feedEntry.TABLE_NAME, KEY_ID + " = ?",
//                new String[]{String.valueOf(contact.getID())});
        db.delete(feedEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void updateCustomer(FeedEntry feedEntry){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("lat",feedEntry.lat);
        values.put("lng", feedEntry.lng);
        db.update(feedEntry.TABLE_NAME, values, feedEntry.COLUMN_NAME_PHONE + " = ?",
                new String[]{String.valueOf(feedEntry.getPhone())});
        db.close();
    }

    public FeedEntry readCustomer(String phone){
        FeedEntry feedEntry = new FeedEntry() ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(feedEntry.TABLE_NAME, new String [] {feedEntry.COLUMN_NAME_PHONE,
                                                                      feedEntry.COLUMN_NAME_ADDRESS,
                                                                      feedEntry.COLUMN_NAME_EMAIL,
                                                                      feedEntry.COLUMN_NAME_NAME,
                                                                      feedEntry.COLUMN_NAME_LAT,
                                                                      feedEntry.COLUMN_NAME_LNG
                                                                      },
                                 null, null, null, null, null, null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            feedEntry.setPhone(cursor.getString(0));
            feedEntry.setAddress(cursor.getString(1));
            feedEntry.setEmail(cursor.getString(2));
            feedEntry.setName(cursor.getString(3));
            feedEntry.setLat(cursor.getString(4));
            feedEntry.setLng(cursor.getString(5));
        }
        else{
            feedEntry.setPhone("Empty");
            feedEntry.setEmail("Empty");
            feedEntry.setName("Empty");
            feedEntry.setAddress("Empty");
            feedEntry.setLat("0.0");
            feedEntry.setLng("0.0");
        }

        db.close(); // Closing database connection
        return feedEntry;
    }




}
