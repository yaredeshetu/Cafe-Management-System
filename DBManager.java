package org.o7planning.agelegecafe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBManager {

    public static DatabaseHelper dbHelper;

    private Context context;

    public static SQLiteDatabase database;

    public DBManager(Context c) {

        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new org.o7planning.agelegecafe.DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String a1,String a2,String a3,String a4,String a5,String a6,String a9,String a10,String a11,String a12) {
        try {
            String a7="admin";
            String a8="admin";
            ContentValues contentValue = new ContentValues();
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.a1, a1);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.a2, a2);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.a3, a3);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.a4, a4);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.a5, a5);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.a6, a6);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.a7, a7);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.a8, a8);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.a9, a9);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.a10, a10);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.a11, a11);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.a12, a12);
            database.insert(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME, null, contentValue);
        }
        catch (Exception ex)
        {
            Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }/*
    public void insert1(String b1,String b2,String sync) {
        try {
            String b3=sync;
            ContentValues contentValue = new ContentValues();
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.b2, b2);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.b3, b3);
            database.insert(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME1, null, contentValue);
        }
        catch (Exception ex)
        {
            Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void insert2(ArrayList s,String sync) {
        try {
            String c1=s.get(0).toString();
            String c2=s.get(1).toString();
            String c3=s.get(2).toString();
            String c4=s.get(3).toString();
            String c5=s.get(4).toString();
            double c6=Double.parseDouble(s.get(5).toString());
            double c7=Double.parseDouble(s.get(6).toString());
            double c8=c7;
            int c9=Integer.parseInt(s.get(7).toString());
            String c10=sync;
            ContentValues contentValue = new ContentValues();
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.c1, c1);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.c2, c2);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.c3, c3);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.c4, c4);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.c5, c5);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.c6, c6);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.c7, c7);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.c8, c8);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.c9, c9);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.c10, c10);
            database.insert(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME2, null, contentValue);
        }
        catch (Exception ex)
        {
            Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void insert4(ArrayList s,String sync) {
        try {
            String c1=s.get(0).toString();
            String c2=s.get(1).toString();
            String c3=s.get(2).toString();
            String c4=s.get(3).toString();
            String c5=s.get(4).toString();
            String c6=s.get(5).toString();
            String c7=s.get(6).toString();
            String c8=sync;
            ContentValues contentValue = new ContentValues();
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.f1, c1);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.f2, c2);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.f3, c3);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.f4, c4);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.f5, c5);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.f6, c6);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.f7, c7);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.f9, c8);
            database.insert(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME5, null, contentValue);
        }
        catch (Exception ex)
        {
            Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void insert3(ArrayList h,ArrayList b,String sync) {
        try {
            String s1=h.get(0).toString();
            String s2=h.get(1).toString();
            String s3=h.get(2).toString();
            String s4=h.get(3).toString();
            String s5=h.get(4).toString();
            double s6=Double.parseDouble(h.get(5).toString());
            double s7=Double.parseDouble(h.get(6).toString());
            String s8=h.get(7).toString();
            String s9=h.get(8).toString();
            double s10=Double.parseDouble(h.get(9).toString());
            double s11=Double.parseDouble(h.get(10).toString());
            String s12=h.get(11).toString();
            String s13=h.get(12).toString();
            String s14=h.get(13).toString();
            String s15=h.get(14).toString();
            String s16=h.get(15).toString();
            String s17=h.get(16).toString();
            double s18=Double.parseDouble(h.get(17).toString());
            double s19=Double.parseDouble(h.get(18).toString());
            int s20=Integer.parseInt(h.get(19).toString());
            String s21=h.get(20).toString();
            String s22=h.get(21).toString();
            String s23=h.get(22).toString();
            String s24=h.get(23).toString();
            String s25=sync;
            ContentValues contentValue1 = new ContentValues();
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d1, s1);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d2, s2);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d3, s3);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d4, s4);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d5, s5);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d6, s6);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d7, s7);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d8, s8);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d9, s9);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d10, s10);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d11, s11);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d12, s12);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d13, s13);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d14, s14);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d15, s15);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d16, s16);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d17, s17);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d18, s18);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d19, s19);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d20, s20);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d21, s21);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d22, s22);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d23, s23);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d24, s24);
            contentValue1.put(org.o7planning.agelegecafe.DatabaseHelper.d25, s25);
            database.insert(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME3, null, contentValue1);
            for(int i=0;i<b.size();i++)
            {
                ArrayList s= (ArrayList) b.get(i);
                String c1 = s.get(0).toString();
                String c2 = s.get(1).toString();
                String c3 = s.get(2).toString();
                String c4 = s.get(3).toString();
                double c5 = Double.parseDouble(s.get(4).toString());
                double c6 = Double.parseDouble(s.get(5).toString());
                double c7 = Double.parseDouble(s.get(6).toString());
                double c8 = Double.parseDouble(s.get(7).toString());
                String c10 = s.get(8).toString();
                String c9 = s.get(9).toString();
                String c11=sync;
                ContentValues contentValue = new ContentValues();
                contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.e1, c1);
                contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.e2, c2);
                contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.e3, c3);
                contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.e4, c4);
                contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.e5, c5);
                contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.e6, c6);
                contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.e7, c7);
                contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.e8, c8);
                contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.e9, c9);
                contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.e10, c10);
                contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.e11, c11);
                database.insert(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME4, null, contentValue);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }*/
    public Cursor fetch() {
        String[] columns = new String[] {org.o7planning.agelegecafe.DatabaseHelper.a3, org.o7planning.agelegecafe.DatabaseHelper.a4, org.o7planning.agelegecafe.DatabaseHelper.a5, org.o7planning.agelegecafe.DatabaseHelper.a6 , org.o7planning.agelegecafe.DatabaseHelper.a7 , org.o7planning.agelegecafe.DatabaseHelper.a8 , org.o7planning.agelegecafe.DatabaseHelper.a9 };
        Cursor cursor = database.query(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetch1() {
        String[] columns = new String[] {org.o7planning.agelegecafe.DatabaseHelper.a1 };
        Cursor cursor = database.query(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetch10() {
        String[] columns = new String[] {DatabaseHelper.a10,DatabaseHelper.a11,DatabaseHelper.a12 };
        Cursor cursor = database.query(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    /*
    public Cursor fetch4() {
        String[] columns = new String[] {org.o7planning.agelegecafe.DatabaseHelper.f3 };
        Cursor cursor = database.query(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME5, columns, " order by "+ org.o7planning.agelegecafe.DatabaseHelper.f0, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetch44(String userN) {
        String[] columns = new String[] {org.o7planning.agelegecafe.DatabaseHelper.f2 };
        Cursor cursor = database.query(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME5, columns, org.o7planning.agelegecafe.DatabaseHelper.f3+"='"+userN+"'", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetch2(String user) {
        String[] columns = new String[] {org.o7planning.agelegecafe.DatabaseHelper.b2, org.o7planning.agelegecafe.DatabaseHelper.b0 };
        Cursor cursor = database.query(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME1, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetch21(String str) {
        String[] columns = new String[] {org.o7planning.agelegecafe.DatabaseHelper.b2, org.o7planning.agelegecafe.DatabaseHelper.b0 };
        Cursor cursor = database.query(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME1, columns, org.o7planning.agelegecafe.DatabaseHelper.b2+" LIKE '%"+str+"%'", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetch30(String user) {
        String[] columns = new String[] {org.o7planning.agelegecafe.DatabaseHelper.c3, org.o7planning.agelegecafe.DatabaseHelper.c6};
        Cursor cursor = database.query(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME2, columns, org.o7planning.agelegecafe.DatabaseHelper.c1+"='"+user+"'", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetch3(String user) {
        String[] columns = new String[] {org.o7planning.agelegecafe.DatabaseHelper.c1, org.o7planning.agelegecafe.DatabaseHelper.c2, org.o7planning.agelegecafe.DatabaseHelper.c3, org.o7planning.agelegecafe.DatabaseHelper.c5 , org.o7planning.agelegecafe.DatabaseHelper.c6, org.o7planning.agelegecafe.DatabaseHelper.c8, org.o7planning.agelegecafe.DatabaseHelper.c0};
        Cursor cursor = database.query(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME2, columns, org.o7planning.agelegecafe.DatabaseHelper.c1+"='"+user+"'", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetch45(String user,String itId) {
        String[] columns = new String[] {org.o7planning.agelegecafe.DatabaseHelper.c7};
        Cursor cursor = database.query(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME2, columns, org.o7planning.agelegecafe.DatabaseHelper.c1+"='"+itId+"' and "+ org.o7planning.agelegecafe.DatabaseHelper.c5+"='"+user+"'", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetch31(String user,String str) {
        String[] columns = new String[] {org.o7planning.agelegecafe.DatabaseHelper.c1, org.o7planning.agelegecafe.DatabaseHelper.c2, org.o7planning.agelegecafe.DatabaseHelper.c3, org.o7planning.agelegecafe.DatabaseHelper.c7, org.o7planning.agelegecafe.DatabaseHelper.c6 };
        Cursor cursor = database.query(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME2, columns, org.o7planning.agelegecafe.DatabaseHelper.c2+" LIKE '%"+str+"%'", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetch41(String user,String str) {
        String[] columns = new String[] {org.o7planning.agelegecafe.DatabaseHelper.f1, org.o7planning.agelegecafe.DatabaseHelper.f2, org.o7planning.agelegecafe.DatabaseHelper.f3, org.o7planning.agelegecafe.DatabaseHelper.f4, org.o7planning.agelegecafe.DatabaseHelper.f5, org.o7planning.agelegecafe.DatabaseHelper.f6, org.o7planning.agelegecafe.DatabaseHelper.f7 };
        Cursor cursor = database.query(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME5, columns, org.o7planning.agelegecafe.DatabaseHelper.f2+" LIKE '%"+str+"%'", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public int update0(ArrayList s,int _id) {
        int i =-1;
        try
        {
            ContentValues contentValues = new ContentValues();
            String c1=s.get(0).toString();
            String c2=s.get(1).toString();
            String c3=s.get(2).toString();
            String c5=s.get(3).toString();
            double c6=Double.parseDouble(s.get(4).toString());
            double c8=Double.parseDouble(s.get(5).toString());
            ContentValues contentValue = new ContentValues();
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.c2, c2);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.c3, c3);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.c5, c5);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.c6, c6);
            contentValue.put(org.o7planning.agelegecafe.DatabaseHelper.c8, c8);
            i= database.update(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME2, contentValues, org.o7planning.agelegecafe.DatabaseHelper.c0+"="+_id, null);
        }
            catch (Exception ex)
        {
            Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return i;
    }
    public int update4(int _id,String ordSt) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(org.o7planning.agelegecafe.DatabaseHelper.d24, ordSt);
        int i = database.update(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME3, contentValues, org.o7planning.agelegecafe.DatabaseHelper.d0+"="+_id, null);
        return i;
    }
    public int updateStore(String _id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(org.o7planning.agelegecafe.DatabaseHelper.b3, "true");
        int i = database.update(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME1, contentValues, org.o7planning.agelegecafe.DatabaseHelper.b2+"='"+_id+"'", null);
        return i;
    }
    public int updateItem(String _id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(org.o7planning.agelegecafe.DatabaseHelper.c10, "true");
        int i = database.update(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME2, contentValues, org.o7planning.agelegecafe.DatabaseHelper.c1+"='"+_id+"'", null);
        return i;
    }
    public int updateUser(String _id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(org.o7planning.agelegecafe.DatabaseHelper.f9, "true");
        int i = database.update(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME5, contentValues, org.o7planning.agelegecafe.DatabaseHelper.f1+"='"+_id+"'", null);
        return i;
    }
    public int updateVouch(int _id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(org.o7planning.agelegecafe.DatabaseHelper.d25, "true");
        int i = database.update(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME3, contentValues, org.o7planning.agelegecafe.DatabaseHelper.d0+"="+_id, null);
        return i;
    }
    public int updateLineIt(int _id,String itid) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(org.o7planning.agelegecafe.DatabaseHelper.e11, "true");
        int i = database.update(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME4, contentValues, org.o7planning.agelegecafe.DatabaseHelper.e1+"="+_id+" and "+ org.o7planning.agelegecafe.DatabaseHelper.e2+"='"+itid+"'", null);
        return i;
    }*/
    public int update(String _id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(org.o7planning.agelegecafe.DatabaseHelper.a8, _id);
        int i = database.update(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME, contentValues, null, null);
        return i;
    }
    /*
    public int update10(String _id,double qty,String store) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(org.o7planning.agelegecafe.DatabaseHelper.c7, qty);
        int i = database.update(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME2, contentValues, org.o7planning.agelegecafe.DatabaseHelper.c1+"='"+_id+"' and "+ org.o7planning.agelegecafe.DatabaseHelper.c5+"='"+store+"'", null);
        return i;
    }

    public int update(int _id,String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(org.o7planning.agelegecafe.DatabaseHelper.d21, str);
        int i = database.update(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME3, contentValues, org.o7planning.agelegecafe.DatabaseHelper.d0+"="+_id, null);
        return i;
    }*/

    public void delete() {

        database.delete(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME, null, null);
    }/*
    public void delete0(String _id) {

        database.delete(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME2, org.o7planning.agelegecafe.DatabaseHelper.c1+"='"+_id+"'", null);
    }
    public void delete01(String _id) {

        database.delete(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME1, org.o7planning.agelegecafe.DatabaseHelper.b2+"='"+_id+"'", null);
    }
    public void delete02(int _id) {

        database.delete(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME4, org.o7planning.agelegecafe.DatabaseHelper.e1+"="+_id, null);
    }
    public void delete03(String _id) {

        database.delete(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME5, org.o7planning.agelegecafe.DatabaseHelper.f1+"='"+_id+"'", null);
    }
    public void delete(int _id) {

        database.delete(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME3, org.o7planning.agelegecafe.DatabaseHelper.d0+"="+_id, null);
    }

    public void delete1() {

        database.delete(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME1, null, null);
    }
    public void delete2() {

        database.delete(org.o7planning.agelegecafe.DatabaseHelper.TABLE_NAME2, null, null);
    }
    */
}

