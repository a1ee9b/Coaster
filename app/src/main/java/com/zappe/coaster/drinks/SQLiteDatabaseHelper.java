package com.zappe.coaster.drinks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Jannik on 16.06.2016.
 */
public class SQLiteDatabaseHelper {

    SQLHelper helper;

    public SQLiteDatabaseHelper(Context context) {
        helper = new SQLHelper(context);
    }

    public ArrayList<DrinkModel> getDrinks() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(SQLHelper.TABLE_NAME, null, null, null, null, null, null);

        ArrayList<DrinkModel> drinks = new ArrayList<DrinkModel>();

        while (cursor.moveToNext()) {
            int indexId = cursor.getColumnIndex(SQLHelper.id);
            int nameId = cursor.getColumnIndex(SQLHelper.name);
            int amountId = cursor.getColumnIndex(SQLHelper.amount);
            int priceId = cursor.getColumnIndex(SQLHelper.price);

            int id = cursor.getInt(indexId);
            String name = cursor.getString(nameId);
            int amount = cursor.getInt(amountId);
            double price = cursor.getDouble(priceId);

            DrinkModel drinkModel = new DrinkModel(id, name, price, amount);
            drinks.add(drinkModel);
        }

        return drinks;
    }

    public long insertDrink(DrinkModel drink) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLHelper.name, drink.name);
        values.put(SQLHelper.amount, drink.amount);
        values.put(SQLHelper.price, drink.price);

        long id = db.insert(SQLHelper.TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public int updateDrink(DrinkModel drink) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLHelper.name, drink.name);
        contentValues.put(SQLHelper.price, drink.price);
        contentValues.put(SQLHelper.amount, drink.amount);

        String[] args = {String.valueOf(drink.id)};

        return db.update(SQLHelper.TABLE_NAME, contentValues, SQLHelper.id+"=?", args);
    }

    public int deleteDrink(DrinkModel drink) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String args[] = {String.valueOf(drink.id)};

        return db.delete(SQLHelper.TABLE_NAME, SQLHelper.id+"=?", args);
    }

    public class SQLHelper extends SQLiteOpenHelper {
        private static final int VERSION = 1;
        private static final String DATABASE_NAME = "drinksDB";
        private static final String TABLE_NAME = "drinks";
        private static final String id = "id";
        private static final String name = "name";
        private static final String price = "price";
        private static final String amount = "amount";

        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+name+" VARCHAR(255), "+price+" DOUBLE, "+amount+" INTEGER)";

        Context context;

        public SQLHelper(Context context) {
            super(context, DATABASE_NAME, null , VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
