package org.o7planning.agelegecafe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Table Name
    public static final String TABLE_NAME = "connection";
    /*
    public static final String TABLE_NAME1 = "store";
    public static final String TABLE_NAME2 = "item";
    public static final String TABLE_NAME3 = "voucher";
    public static final String TABLE_NAME4 = "lineItem";
    public static final String TABLE_NAME5 = "users";
*/
    // Table columns

    // Table columns
    public static final String a0 = "_id";
    public static final String a1 = "userName";
    public static final String a2 = "password";
    public static final String a3 = "url";
    public static final String a4 = "port";
    public static final String a5 = "user";
    public static final String a6 = "pass";
    public static final String a7 = "ad_user";
    public static final String a8 = "ad_pass";
    public static final String a9 = "conn_type";
    public static final String a10 = "auto_date";
    public static final String a11 = "date_from";
    public static final String a12 = "auto_assign";

    /*
        public static final String b0 = "_id";
        public static final String b2 = "store";
        public static final String b3 = "sync";

        public static final String c0 = "_id";
        public static final String c1 = "itemId";
        public static final String c2 = "itemName";
        public static final String c3 = "unit";
        public static final String c4 = "itemCat";
        public static final String c5 = "store";
        public static final String c6 = "itemPrice";
        public static final String c7 = "itemBalance";
        public static final String c8 = "begBalance";
        public static final String c9 = "it_count";
        public static final String c10 = "sync";

        public static final String d0 = "_id";
        public static final String d1 = "VoucherType";
        public static final String d2 = "name";
        public static final String d3 = "mobile";
        public static final String d4 = "tin";
        public static final String d5 = "isCash";
        public static final String d6 = "subTotal";
        public static final String d7 = "total";
        public static final String d8 = "orderTake";
        public static final String d9 = "waiter";
        public static final String d10 = "receive";
        public static final String d11 = "bal_due";
        public static final String d12 = "pay_type";
        public static final String d13 = "ref_no";
        public static final String d14 = "description";
        public static final String d15 = "price_word";
        public static final String d16 = "timeStamp";
        public static final String d17 = "user";
        public static final String d18 = "dirLat";
        public static final String d19 = "dirLon";
        public static final String d20 = "itemCount";
        public static final String d21 = "status";
        public static final String d22 = "bal_due_date";
        public static final String d23 = "date";
        public static final String d24 = "order_status";
        public static final String d25 = "sync";

        public static final String e0 = "_id";
        public static final String e1 = "voucherId";
        public static final String e2 = "itemId";
        public static final String e3 = "itemName";
        public static final String e4 = "store";
        public static final String e5 = "qtyIn";
        public static final String e6 = "u_price";
        public static final String e7 = "tot_price";
        public static final String e8 = "qtyOut";
        public static final String e9 = "status";
        public static final String e10 = "unit";
        public static final String e11 = "sync";

        public static final String f0 = "_id";
        public static final String f1 = "empId";
        public static final String f2 = "empName";
        public static final String f3 = "userName";
        public static final String f4 = "password";
        public static final String f5 = "Mobile";
        public static final String f6 = "Privilage";
        public static final String f7 = "status";
        public static final String f8 = "profPic";
        public static final String f9 = "sync";
        // Database Information

     */
    static final String DB_NAME = "agelegelHotel.DB";

    // database version
    static final int DB_VERSION = 1;
    private static Context context1=null;

    // Creating table query

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + a0
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + a1 + " TEXT, " + a2 + " TEXT, "+ a3 + " TEXT, "+ a4 + " TEXT, "+ a5 + " TEXT, "+ a6 + " TEXT, "+ a7 + " TEXT, "+ a8 + " TEXT, "+ a9 + " TEXT, "+ a10 + " TEXT, "+ a11 + " TEXT, "+a12+" TEXT);";
    /*private static final String CREATE_TABLE1 = "create table " + TABLE_NAME1 + "(" + b0
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + b2 + " TEXT, "+ b3+" TEXT);";
    private static final String CREATE_TABLE2 = "create table " + TABLE_NAME2 + "(" + c0
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + c1 + " TEXT, " + c2 + " TEXT, "+ c3 + " DOUBLE, "+ c4 + " TEXT, "+ c5 + " TEXT, "+ c6 + " DOUBLE, "+ c7 + " DOUBLE, "+ c8 + " DOUBLE, "+ c9 + " INTEGER, "+ c10+" TEXT);";
    private static final String CREATE_TABLE3 = "create table " + TABLE_NAME3 + "(" + d0
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + d1 + " TEXT, " + d2 + " TEXT, " + d3 + " TEXT, " + d4 + " TEXT, " + d5 + " TEXT, " + d6 + " DOUBLE, " + d7 + " DOUBLE, " + d8 + " TEXT, " + d9 + " TEXT, " + d10 + " DOUBLE, "
            + d11 + " DOUBLE, " + d12 + " TEXT, " + d13 + " TEXT," + d14 + " TEXT," + d15 + " TEXT, " + d16 + " TIMESTAMP, "+ d17 + " TEXT, " + d18 + " DOUBLE, " + d19 + " DOUBLE, " + d20 + " INTEGER, "+ d21 + " INTEGER, "+ d22 + " TEXT, "+ d23 + " TIMESTAMP, " + d24 + " TEXT, "+ d25+" TEXT);";
    private static final String CREATE_TABLE4 = "create table " + TABLE_NAME4 + "(" + e0
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + e1 + " INTEGER, " + e2 + " TEXT, " + e3 + " TEXT, " + e4 + " TEXT, " + e5 + " DOUBLE, " + e6 + " DOUBLE, "+ e7 + " DOUBLE, " + e8 + " DOUBLE, "+ e9 + " TEXT, " + e10 + " DOUBLE, "+ e11+" TEXT);";
    private static final String CREATE_TABLE5 = "create table " + TABLE_NAME5 + "(" + f0
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + f1 + " TEXT, " + f2 + " TEXT, " + f3 + " TEXT, " + f4 + " TEXT, " + f5 + " TEXT, " + f6 + " TEXT, "+ f7 + " TEXT, " + f8 + " BLOB, "+ f9+" TEXT);";
    */
    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        context1=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(CREATE_TABLE);
            /*
            db.execSQL(CREATE_TABLE1);
            db.execSQL(CREATE_TABLE2);
            db.execSQL(CREATE_TABLE3);
            db.execSQL(CREATE_TABLE4);
            db.execSQL(CREATE_TABLE5);

             */
        }
        catch (Exception ex)
        {
            Toast.makeText(context1,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        /*
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME5);

         */
        onCreate(db);
    }
}
