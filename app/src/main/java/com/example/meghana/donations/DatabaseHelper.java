package com.example.meghana.donations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by meghana on 20/5/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase sdb;

    public static final String DATABASE_NAME = "RegDb.db";
    public static final String TABLE_NAME = "Users_table";
    public static final String TABLE_NAME1 = "Organisations_table";
    public static final String TABLE_NAME2 = "Donations_table";

    public static final String COL_1 = "USER_ID";
    public static final String COL_2 = "FIRST_NAME";
    public static final String COL_3 = "LAST_NAME";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "PASSWORD";
    public static final String COL_6 = "MOBILE";
    public static final String COL_7 = "ADDRESS";

    public static final String COL_8 = "ORGANISATION_ID";
    public static final String COL_9 = "NAME";
    public static final String COL_10 = "CITY";
    public static final String COL_11 = "DESCRIPTION";

    public static final String COL_12 = "DON_ID";
    public static final String COL_13 = "AMOUNT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {


        Log.d("create table", "table1");
        db.execSQL("create table " + TABLE_NAME + "(USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME VARCHAR,LAST_NAME VARCHAR, EMAIL VARCHAR, PASSWORD VARCHAR,MOBILE VARCHAR,ADDRESS VARCHAR)");

        Log.d("create table", "table2");
        db.execSQL("create table " + TABLE_NAME1 + "(ORGANISATION_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR, CITY VARCHAR,DESCRIPTION VARCHAR)");

        Log.d("create table", "table3");
        db.execSQL("create table " + TABLE_NAME2 + "(DON_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID INTEGER,ORGANISATION_ID INTEGER,AMOUNT VARCHAR," +
                "FOREIGN KEY(USER_ID)REFERENCES Users_table(USER_ID),FOREIGN KEY(ORGANISATION_ID)REFERENCES Organisations_table(ORGANISATION_ID) )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME2);

        onCreate(db);
    }


    public void openDb() {
        sdb = this.getWritableDatabase();
    }

    public void closeDb() {
        sdb.close();
    }

    public boolean insertData(String firstname, String lastname, String email, String password, String mobile, String address) {
        openDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, firstname);
        contentValues.put(COL_3, lastname);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, password);
        contentValues.put(COL_6, mobile);
        contentValues.put(COL_7, address);
        long result = sdb.insert(TABLE_NAME, null, contentValues);
        closeDb();
        return result != -1;
    }

    public boolean authenticate(String email, String password) {
        Log.d("Entered", "into Authenticate");

        int flag = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("Entered", "authenticate method bef query..");
        Cursor cursor = db.query(TABLE_NAME, null, "EMAIL=? and PASSWORD=?", new String[]{email, password}, null, null, null);
        Log.d("Entered", "authenticate method after query..");
        if (cursor.getCount() > 0) {
            Log.d("Entered", "if loop");
            flag = 1;

        }

        cursor.close();
        db.close();

        return flag != -1;
    }

    public void saveDataToOrganisationTable(String name, String city, String desc) {

        openDb();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_9, name);
        contentValues.put(COL_10, city);
        contentValues.put(COL_11, desc);


        long sdataRes = sdb.insert(TABLE_NAME1, null, contentValues);
        Log.d("sdataRes", "" + sdataRes);

        closeDb();

    }

    public ArrayList<ObjectForUse> getDataFromOrganisationTable() {

        Log.d("enter", "getDataFromOrganisationTable: ");

        openDb();
//        Cursor cur = sdb.query(true, TABLE_NAME1, null, null, null, null, null, null, null);

        Cursor cur =  sdb.query(true, TABLE_NAME1+" ot", new String[]{COL_9, COL_10, COL_11,COL_8,
                "(select sum(amount) from Donations_table where organisation_id=ot.organisation_id) amount"  }, null, null, null, null, null, null );
        ArrayList<ObjectForUse> listData = new ArrayList<>();
        if (cur.getCount() > 0) {
            for (; cur.moveToNext(); ) {
                ObjectForUse obj = new ObjectForUse();
                obj.o_name = cur.getString(cur.getColumnIndex(COL_9));
                obj.o_city = cur.getString(cur.getColumnIndex(COL_10));
                obj.o_desc = cur.getString(cur.getColumnIndex(COL_11));
                obj.o_id= cur.getString(cur.getColumnIndex(COL_8));
                obj.o_amount=cur.getString(cur.getColumnIndex("amount"));
                listData.add(obj);

            }

            Log.d("list size", "" + listData.size());
        }
        cur.close();
        closeDb();
        return listData;
    }






    public String getUidFromEmail(String logEmail)
    {
        String u_id=null;
        openDb();
        Cursor cur = sdb.query(TABLE_NAME, null, "EMAIL=?", new String[]{logEmail}, null, null, null);
        if (cur.getCount()==1)
        {
            cur.moveToFirst();
            u_id =  cur.getString(cur.getColumnIndex("USER_ID"));
            Log.d("userId",u_id);
        }
        cur.close();
        closeDb();
        return u_id;
    }


    public boolean addDonateValue(String logEmail, String mny, String o_orgId) {

        boolean val =false;
        String uid =  getUidFromEmail(logEmail);

        openDb();
        ContentValues cont = new ContentValues();
        cont.put(COL_1, uid);
        cont.put(COL_8, o_orgId);
        cont.put(COL_13, mny);

        long mnyIns = sdb.insert(TABLE_NAME2, null, cont);

        Log.d("mnyIns",mnyIns+"");
        if (mnyIns>0)
        {
            val = true;
        }
        return val;

    }


    public String getTotal(String o_orgId) {
        openDb();
        Cursor cursor =  sdb.query(TABLE_NAME1+" ot", new String[]{"(select sum(amount) from donations_Table where organisation_id=ot.organisation_id) amount"  },
                "ORGANISATION_ID=?", new String[]{o_orgId}, null, null, null, null );
        cursor.moveToFirst();
        String total =cursor.getString(cursor.getColumnIndex("amount"));

        return total;

    }


}






