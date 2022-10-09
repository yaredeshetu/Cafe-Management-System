package org.o7planning.agelegecafe.ui.opptra;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.DatabaseHelper;
import org.o7planning.agelegecafe.MainActivity;
import org.o7planning.agelegecafe.R;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class OppTraFragment extends Fragment {

    private org.o7planning.agelegecafe.ui.opptra.OppTraViewModel galleryViewModel;
    private static View root=null;
    private static CheckBox ch1=null,ch2=null,ch3=null,ch4=null,ch5=null,ch6=null;
    private static Button bt=null;
    private static TextView tv1=null,tv2=null,prog_tx=null,prog_tx1=null;
    private static ProgressBar prog_bar=null;
    private static RadioButton r1=null,r2=null,r3=null,r4=null;
    private static String userName="";
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static DBManager dbManager;
    String encodedImage="";
    private static int rowC1=0,rowC2=0,rowC3=0,rowC4=0,rowC5=0,rowC6=0;
    private static int rowC=0,DB_COUNT=0;
    private int i = 0;
    private AlertDialog alert=null;
    private Handler hdlr = new Handler();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(org.o7planning.agelegecafe.ui.opptra.OppTraViewModel.class);
        root = inflater.inflate(R.layout.opp_tra, container, false);
        try
        {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        bt=root.findViewById(R.id.oppb1);
        ch1=root.findViewById(R.id.oppch1);
        ch2=root.findViewById(R.id.oppch2);
        ch3=root.findViewById(R.id.oppch3);
        ch4=root.findViewById(R.id.oppch4);
        ch5=root.findViewById(R.id.oppch5);
        ch6=root.findViewById(R.id.oppch6);
        r1=root.findViewById(R.id.oppr1);
        r2=root.findViewById(R.id.oppr2);
        r3=root.findViewById(R.id.oppr3);
        r4=root.findViewById(R.id.oppr4);
        tv1=root.findViewById(R.id.opptv1);
        tv2=root.findViewById(R.id.opptv2);
        prog_tx=root.findViewById(R.id.prog_tx);
        prog_tx1=root.findViewById(R.id.prog_tx1);
        prog_bar=root.findViewById(R.id.prog_bar);
        DB_COUNT=0;
        try
        {
            Cursor cr=dbManager.fetch1();
            userName=cr.getString(0);
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv1.setText("Select Data Source");
                tv2.setText("Import Data Types");
                r1.setText("Get Data From Network Server");
                r2.setText("Get Data From Excel");
                ch5.setText("Erase Previous Data");
                bt.setText("Start Importing");
            }
        });
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv1.setText("Select Data Destination");
                tv2.setText("Export Data Types");
                r1.setText("Export Data To Network Server");
                r2.setText("Export Data To Excel");
                ch5.setText("Erase Inserted Data");
                bt.setText("Start Exporting");
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                builder.setTitle(R.string.app_name);
                if(r3.isChecked())
                {
                    builder.setMessage("Are You Sure To Import?");
                }
                else if(r4.isChecked())
                {
                    builder.setMessage("Are You Sure To Export?");
                }
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    public void onClick(DialogInterface dialog, int id)
    {
        dialog.dismiss();
        try
        {
            if(r1.isChecked()) {
                try {
                    MainActivity.connection = MainActivity.Conn();
                } catch (Exception ex) {
                    Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
                Statement st = MainActivity.connection.createStatement();
                Statement st1 = MainActivity.connection.createStatement();
                Statement st2 = MainActivity.connection.createStatement();
                Statement st3 = MainActivity.connection.createStatement();
                Statement st4 = MainActivity.connection.createStatement();
                Statement st5 = MainActivity.connection.createStatement();
                Statement st6 = MainActivity.connection.createStatement();
                if (bt.getText().toString().equalsIgnoreCase("Start Importing"))
                {/*
                    Toast.makeText(root.getContext(), "Importing ...", Toast.LENGTH_LONG).show();
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try
                            {
                                ResultSet cr = null;
                                if(ch1.isChecked())
                                {
                                    prog_tx1.setTextColor(Color.BLUE);
                                    prog_tx1.setText("Item Information Exporting ...");
                                    prog_bar.setProgress(0);
                                    prog_tx.setText("0%");
                                    Thread.sleep(2000);
                                    DB_COUNT = 0;
                                    if(ch6.isChecked())
                                        cr=st.executeQuery("select COUNT(itemId) AS COUN from item where sync='false'");
                                    else
                                        cr=st.executeQuery("select COUNT(itemId) AS COUN from item");
                                    while(cr.next())
                                    {
                                        DB_COUNT=cr.getInt("COUN");
                                    }
                                    if(DB_COUNT==0)
                                    {
                                        prog_tx1.setTextColor(Color.RED);
                                        prog_tx.setText("");
                                        prog_tx1.setText("No Item Information Has Found");
                                        Thread.sleep(1000);
                                    }
                                    else
                                    {
                                        prog_bar.setMax(DB_COUNT);
                                        int cc = 0;
                                        if (ch6.isChecked())
                                            cr=st.executeQuery("select id, itemId, itemName, unit, itemCat, store, itemPrice, itemBalance, begBalance, it_count from item where sync='false'");
                                        else
                                            cr=st.executeQuery("select id,itemId, itemName, unit, itemCat, store, itemPrice, itemBalance, begBalance, it_count from item");
                                        while (cr.next()) {
                                            cc=cc+1;
                                            String b1 = cr.getString("itemId");
                                            String b2 = cr.getString("itemName");
                                            double b3=cr.getDouble("unit");
                                            String b4 = cr.getString("itemCat");
                                            String b5 = cr.getString("store");
                                            double b6=cr.getDouble("itemPrice");
                                            double b7=cr.getDouble("itemBalance");
                                            double b8=cr.getDouble("begBalance");
                                            int b9=cr.getInt("it_count");
                                            prog_bar.setProgress(cc);
                                            double prec=(double) cc/prog_bar.getMax()*100.00;
                                            prog_tx.setText(df.format(prec)+"%");
                                            if(ch5.isChecked()) {
                                                dbManager.delete0(b1);
                                            }
                                            ArrayList S = new ArrayList();
                                            S.add(b1);
                                            S.add(b2);
                                            S.add(b3);
                                            S.add(b4);
                                            S.add(b5);
                                            S.add(b6);
                                            S.add(b7);
                                            S.add(b9);
                                            dbManager.insert2(S,"true");
                                            st1.execute("update item set sync='true' where itemId='"+b1+"'");
                                            dbManager.updateItem(b1);
                                        }
                                        prog_tx1.setText("Item Information Exported");
                                    }
                                }
                                if(ch2.isChecked())
                                {
                                    Thread.sleep(500);
                                    prog_tx1.setTextColor(Color.BLUE);
                                    prog_tx1.setText("Store Information Exporting ...");
                                    prog_bar.setProgress(0);
                                    prog_tx.setText("0%");
                                    Thread.sleep(2000);
                                    DB_COUNT=0;
                                    if(ch6.isChecked())
                                        cr =st.executeQuery("select COUNT(id) AS COUN from storeList where sync='false'");
                                    else
                                        cr =st.executeQuery("select COUNT(id) AS COUN from storeList");
                                    while (cr.next()) {
                                        DB_COUNT=cr.getInt("COUN");
                                    }
                                    if(DB_COUNT==0)
                                    {
                                        prog_tx1.setTextColor(Color.RED);
                                        prog_tx.setText("");
                                        prog_tx1.setText("No Store Information Has Found");
                                        Thread.sleep(1000);
                                    }
                                    else
                                    {
                                        prog_bar.setMax(DB_COUNT);
                                        int cc=0;
                                        if(ch6.isChecked())
                                            cr =st.executeQuery("select id, store from storeList where sync='false'");
                                        else
                                            cr =st.executeQuery("select id, store from storeList");
                                        while (cr.next()) {
                                            cc=cc+1;
                                            String b1 = cr.getString("store");
                                            prog_bar.setProgress(cc);
                                            double prec=(double) cc/prog_bar.getMax()*100.00;
                                            prog_tx.setText(df.format(prec)+"%");
                                            if(ch5.isChecked()) {
                                                dbManager.delete01(b1);
                                            }
                                            dbManager.insert1("", b1,"true");
                                            st1.execute("update storeList set sync='true' where store='"+b1+"'");
                                            dbManager.updateStore(b1);
                                        }
                                        prog_tx1.setText("Store Information Exported");
                                    }
                                }
                                if(ch3.isChecked())
                                {
                                    Thread.sleep(500);
                                    prog_tx1.setTextColor(Color.BLUE);
                                    prog_tx1.setText("Voucher Information Exporting ...");
                                    prog_bar.setProgress(0);
                                    prog_tx.setText("0%");
                                    Thread.sleep(2000);
                                    DB_COUNT=0;
                                    if(ch6.isChecked())
                                        cr = st.executeQuery("select COUNT(id) AS COUN from voucher where sync='false'");
                                    else
                                        cr = st.executeQuery("select COUNT(id) AS COUN from voucher");
                                    while (cr.next()) {
                                        DB_COUNT=cr.getInt("COUN");
                                    }
                                    if(DB_COUNT==0)
                                    {
                                        prog_tx1.setTextColor(Color.RED);
                                        prog_tx.setText("");
                                        prog_tx1.setText("No Voucher Information Has Found");
                                        Thread.sleep(1000);
                                    }
                                    else
                                    {
                                        prog_bar.setMax(DB_COUNT);
                                        int cc=0;
                                        if(ch6.isChecked())
                                            cr = st.executeQuery("select v_id, voucherType, name, mobile, tin, isCash, subTotal, total, orderTake, waiter, recieve, bal_due, pay_type, ref_no, description, price_word, timeStamp, userN, dirLat, dirLon, dirAlt, itemCount, status, bal_due_date, date, order_status from voucher where sync='false'");
                                        else
                                            cr = st.executeQuery("select v_id, voucherType, name, mobile, tin, isCash, subTotal, total, orderTake, waiter, recieve, bal_due, pay_type, ref_no, description, price_word, timeStamp, userN, dirLat, dirLon, dirAlt, itemCount, status, bal_due_date, date, order_status from voucher");
                                        while (cr.next()) {
                                            cc=cc+1;
                                            int b0=cr.getInt("v_id");
                                            String b1 = cr.getString("voucherType");
                                            //String vouchId=ItemID(b1);
                                            String b2 = cr.getString("name");
                                            String b3 = cr.getString("mobile");
                                            String b4 = cr.getString("tin");
                                            String b5 = cr.getString("isCash");
                                            double b6=cr.getDouble("subTotal");
                                            double b7=cr.getDouble("total");
                                            String b8 = cr.getString("orderTake");
                                            String b9 = cr.getString("waiter");
                                            double b10=cr.getDouble("recieve");
                                            double b11=cr.getDouble("bal_due");
                                            String b12 = cr.getString("pay_type");
                                            String b13 = cr.getString("ref_no");
                                            String b14 = cr.getString("description");
                                            String b15 = cr.getString("price_word");
                                            String b16 = cr.getString("timeStamp");
                                            String b17 = cr.getString("userN");
                                            double b18=cr.getDouble("dirLat");
                                            double b19=cr.getDouble("dirLon");
                                            int b20=cr.getInt("itemCount");
                                            String b21 = cr.getString("status");
                                            String b22 = cr.getString("bal_due_date");
                                            String b23 = cr.getString("date");
                                            String b24 = cr.getString("order_status");
                                            ArrayList h=new ArrayList();
                                            h.add(b1);
                                            h.add(b2);
                                            h.add(b3);
                                            h.add(b4);
                                            h.add(b5);
                                            h.add(b6);
                                            h.add(b7);
                                            h.add(b8);
                                            h.add(b9);
                                            h.add(b10);
                                            h.add(b11);
                                            h.add(b12);
                                            h.add(b13);
                                            h.add(b14);
                                            h.add(b15);
                                            h.add(b16);
                                            h.add(b17);
                                            h.add(b18);
                                            h.add(b19);
                                            h.add(b20);
                                            h.add(b21);
                                            h.add(b22);
                                            h.add(b23);
                                            h.add(b24);
                                            int e1=0;
                                            Cursor cursor1 = DBManager.database.rawQuery("SELECT " + DatabaseHelper.d0 + " FROM " + DatabaseHelper.TABLE_NAME3+" order by "+DatabaseHelper.d0, null);
                                            if(cursor1.moveToFirst())
                                            {
                                                do{
                                                    e1=cursor1.getInt(0);
                                                }while (cursor1.moveToNext());
                                            }
                                            e1=e1+1;
                                            ArrayList b=new ArrayList();
                                            ResultSet cr1 =st1.executeQuery("select id, voucherId, itemId, itemName, store, qtyIn, u_price, tot_price, qtyOut, status, unit from lineItem where v_id="+b0);
                                            while (cr1.next()) {
                                                ArrayList s=new ArrayList();
                                                String c2=cr1.getString("itemId");
                                                String c3=cr1.getString("itemName");
                                                String c4=cr1.getString("store");
                                                double c5=cr1.getDouble("qtyIn");
                                                double c6=cr1.getDouble("u_price");
                                                double c7=cr1.getDouble("tot_price");
                                                double c8=cr1.getDouble("qtyOut");
                                                String c9=cr1.getString("status");
                                                double c10=cr1.getDouble("unit");
                                                s.add(e1);
                                                s.add(c2);
                                                s.add(c3);
                                                s.add(c4);
                                                s.add(c5);
                                                s.add(c6);
                                                s.add(c7);
                                                s.add(c8);
                                                s.add(c10);
                                                s.add(c9);
                                                b.add(s);
                                            }
                                            prog_bar.setProgress(cc);
                                            double prec=(double) cc/prog_bar.getMax()*100.00;
                                            prog_tx.setText(df.format(prec)+"%");
                                            if(ch5.isChecked()) {
                                                dbManager.delete(b0);
                                                dbManager.delete02(b0);
                                            }
                                            dbManager.insert3(h,b,"true");
                                            st2.execute("update voucher set sync='true' where v_id="+b0);
                                            st2.execute("update lineItem set sync='true' where v_id="+b0);
                                            st2.execute("update voucher set v_id="+e1+" where v_id="+b0);
                                            st2.execute("update lineItem set v_id="+e1+" where v_id="+b0);
                                        }
                                        prog_tx1.setText("Voucher Information Exported");
                                    }
                                }
                                if(ch4.isChecked())
                                {
                                    Thread.sleep(500);
                                    prog_tx1.setTextColor(Color.BLUE);
                                    prog_tx1.setText("User Information Exporting ...");
                                    prog_bar.setProgress(0);
                                    prog_tx.setText("0%");
                                    Thread.sleep(2000);
                                    DB_COUNT=0;
                                    if(ch6.isChecked())
                                        cr = st.executeQuery("select COUNT(id) AS COUN from userAccount where sync='false'");
                                    else
                                        cr = st.executeQuery("select COUNT(id) AS COUN from userAccount");
                                    while (cr.next()) {
                                        DB_COUNT=cr.getInt("COUN");
                                    }
                                    if(DB_COUNT==0)
                                    {
                                        prog_tx1.setTextColor(Color.RED);
                                        prog_tx.setText("");
                                        prog_tx1.setText("No User Information Has Found");
                                        Thread.sleep(1000);
                                    }
                                    else
                                    {
                                        prog_bar.setMax(DB_COUNT);
                                        int cc=0;
                                        if(ch6.isChecked())
                                            cr = st.executeQuery("select id,empId, empName, userName, password, mobile, duety, active from userAccount where sync='false'");
                                        else
                                            cr = st.executeQuery("select id,empId, empName, userName, password, mobile, duety, active from userAccount");
                                        while (cr.next()) {
                                            cc=cc+1;
                                            String b1 = cr.getString("empId");
                                            String b2 = cr.getString("empName");
                                            String b3 = cr.getString("userName");
                                            String b4 = cr.getString("password");
                                            String b5 = cr.getString("mobile");
                                            String b6 = cr.getString("duety");
                                            String b7 = cr.getString("active");
                                            prog_bar.setProgress(cc);
                                            double prec=(double) cc/prog_bar.getMax()*100.00;
                                            prog_tx.setText(df.format(prec)+"%");
                                            if(ch5.isChecked()) {
                                                dbManager.delete03(b1);
                                            }
                                            ArrayList s=new ArrayList();
                                            s.add(b1);
                                            s.add(b2);
                                            s.add(b3);
                                            s.add(b4);
                                            s.add(b5);
                                            s.add(b6);
                                            s.add(b7);
                                            dbManager.insert4(s,"true");
                                            st1.execute("update userAccount set sync='true' where empId='"+b1+"'");
                                        }
                                        prog_tx1.setText("User Information Exported");
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(root.getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    thread.start();*/
                }
                else if (bt.getText().toString().equalsIgnoreCase("Start Exporting"))
                {/*
                    Toast.makeText(root.getContext(), "Exporting ...", Toast.LENGTH_LONG).show();
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try
                            {
                                Cursor cr = null;
                                if(ch1.isChecked())
                                {
                                    prog_tx1.setTextColor(Color.BLUE);
                                    prog_tx1.setText("Item Information Exporting ...");
                                    prog_bar.setProgress(0);
                                    prog_tx.setText("0%");
                                    Thread.sleep(2000);
                                    DB_COUNT=0;
                                    if(ch6.isChecked())
                                        cr = DBManager.database.rawQuery("SELECT COUNT("+DatabaseHelper.c0+") AS coun FROM " + DatabaseHelper.TABLE_NAME2 + " where " + DatabaseHelper.c10 + "='false'", null);
                                    else
                                        cr = DBManager.database.rawQuery("SELECT COUNT("+DatabaseHelper.c0+") AS coun FROM " + DatabaseHelper.TABLE_NAME2, null);
                                    while (cr.moveToNext())
                                    {
                                        DB_COUNT=cr.getInt(0);
                                    }
                                    if(DB_COUNT==0)
                                    {
                                        prog_tx1.setTextColor(Color.RED);
                                        prog_tx.setText("");
                                        prog_tx1.setText("No Item Information Has Found");
                                        Thread.sleep(1000);
                                    }
                                    else
                                    {
                                        prog_bar.setMax(DB_COUNT);
                                        int cc=0;
                                        if(ch6.isChecked())
                                            cr = DBManager.database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME2 + " where " + DatabaseHelper.c10 + "='false'", null);
                                        else
                                            cr = DBManager.database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME2, null);
                                        while (cr.moveToNext()) {
                                            cc=cc+1;
                                            String b1 = cr.getString(1);
                                            String b2 = cr.getString(2);
                                            double b3=cr.getDouble(3);
                                            String b4 = cr.getString(4);
                                            String b5 = cr.getString(5);
                                            double b6=cr.getDouble(6);
                                            double b7=cr.getDouble(7);
                                            double b8=cr.getDouble(8);
                                            int b9=cr.getInt(9);
                                            prog_bar.setProgress(cc);
                                            double prec=(double) cc/prog_bar.getMax()*100.00;
                                            prog_tx.setText(df.format(prec)+"%");
                                            if(ch5.isChecked()) {
                                                st.execute("delete from item where itemId='"+b1+"'");
                                            }
                                            st.execute("insert into item(itemId, itemName, unit, itemCat, store, itemPrice, itemBalance, begBalance, it_count,sync) " +
                                                    "values('"+b1+"',N'"+b2+"',"+b3+",'"+b4+"','"+b5+"',"+b6+","+b7+","+b8+","+b9+",'true')");
                                            dbManager.updateItem(b1);
                                        }
                                        prog_tx1.setText("Item Information Exported");
                                    }
                                }
                                if(ch2.isChecked())
                                {
                                    Thread.sleep(500);
                                    prog_tx1.setTextColor(Color.BLUE);
                                    prog_tx1.setText("Store Information Exporting ...");
                                    prog_bar.setProgress(0);
                                    prog_tx.setText("0%");
                                    Thread.sleep(2000);
                                    DB_COUNT=0;
                                    if(ch6.isChecked())
                                        cr = DBManager.database.rawQuery("SELECT COUNT("+DatabaseHelper.b0+") AS coun FROM " + DatabaseHelper.TABLE_NAME1 + " where " + DatabaseHelper.b3 + "='false'", null);
                                    else
                                        cr = DBManager.database.rawQuery("SELECT COUNT("+DatabaseHelper.b0+") AS coun FROM " + DatabaseHelper.TABLE_NAME1, null);
                                    while (cr.moveToNext()) {
                                        DB_COUNT=cr.getInt(0);
                                    }
                                    if(DB_COUNT==0)
                                    {
                                        prog_tx1.setTextColor(Color.RED);
                                        prog_tx.setText("");
                                        prog_tx1.setText("No Store Information Has Found");
                                        Thread.sleep(1000);
                                    }
                                    else
                                    {
                                        prog_bar.setMax(DB_COUNT);
                                        int cc=0;
                                        if(ch6.isChecked())
                                            cr = DBManager.database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME1 + " where " + DatabaseHelper.b3 + "='false'", null);
                                        else
                                            cr = DBManager.database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME1, null);
                                        while (cr.moveToNext()) {
                                            cc=cc+1;
                                            String b1 = cr.getString(1);
                                            prog_bar.setProgress(cc);
                                            double prec=(double) cc/prog_bar.getMax()*100.00;
                                            prog_tx.setText(df.format(prec)+"%");
                                            if(ch5.isChecked()) {
                                                st.execute("delete from storeList where store='"+b1+"'");
                                            }
                                            st.execute("insert into storeList(store,sync) values('"+b1+"','true')");
                                            dbManager.updateStore(b1);
                                        }
                                        prog_tx1.setText("Store Information Exported");
                                    }
                                }
                                if(ch3.isChecked())
                                {
                                    Thread.sleep(500);
                                    prog_tx1.setTextColor(Color.BLUE);
                                    prog_tx1.setText("Voucher Information Exporting ...");
                                    prog_bar.setProgress(0);
                                    prog_tx.setText("0%");
                                    Thread.sleep(2000);
                                    DB_COUNT=0;
                                    if(ch6.isChecked())
                                        cr = DBManager.database.rawQuery("SELECT COUNT("+DatabaseHelper.d0+") AS coun FROM " + DatabaseHelper.TABLE_NAME3 + " where " + DatabaseHelper.d25 + "='false'", null);
                                    else
                                        cr = DBManager.database.rawQuery("SELECT COUNT("+DatabaseHelper.d0+") AS coun FROM " + DatabaseHelper.TABLE_NAME3, null);
                                    while (cr.moveToNext()) {
                                        DB_COUNT=cr.getInt(0);
                                    }
                                    if(DB_COUNT==0)
                                    {
                                        prog_tx1.setTextColor(Color.RED);
                                        prog_tx.setText("");
                                        prog_tx1.setText("No Voucher Information Has Found");
                                        Thread.sleep(1000);
                                    }
                                    else
                                    {
                                        prog_bar.setMax(DB_COUNT);
                                        int cc=0;
                                        if(ch6.isChecked())
                                            cr = DBManager.database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME3 + " where " + DatabaseHelper.d25 + "='false'", null);
                                        else
                                            cr = DBManager.database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME3, null);
                                        while (cr.moveToNext()) {
                                            cc=cc+1;
                                            int b0=cr.getInt(0);
                                            String b1 = cr.getString(1);
                                            String vouchId=ItemID(b1);
                                            String b2 = cr.getString(2);
                                            String b3 = cr.getString(3);
                                            String b4 = cr.getString(4);
                                            String b5 = cr.getString(5);
                                            double b6=cr.getDouble(6);
                                            double b7=cr.getDouble(7);
                                            String b8 = cr.getString(8);
                                            String b9 = cr.getString(9);
                                            double b10=cr.getDouble(10);
                                            double b11=cr.getDouble(11);
                                            String b12 = cr.getString(12);
                                            String b13 = cr.getString(13);
                                            String b14 = cr.getString(14);
                                            String b15 = cr.getString(15);
                                            String b16 = cr.getString(16);
                                            String b17 = cr.getString(17);
                                            double b18=cr.getDouble(18);
                                            double b19=cr.getDouble(19);
                                            int b20=cr.getInt(20);
                                            String b21 = cr.getString(21);
                                            String b22 = cr.getString(22);
                                            String b23 = cr.getString(23);
                                            String b24 = cr.getString(24);
                                            prog_bar.setProgress(cc);
                                            double prec=(double) cc/prog_bar.getMax()*100.00;
                                            prog_tx.setText(df.format(prec)+"%");
                                            if(ch5.isChecked()) {
                                                st.execute("delete from voucher where v_id='"+b0+"'");
                                                st.execute("delete from lineItem where v_id='"+b0+"'");
                                            }
                                            st.execute("insert into voucher( voucherId, voucherType, name, mobile, tin, isCash, subTotal, total, orderTake, waiter, recieve, bal_due, pay_type, ref_no, description, price_word, timeStamp, userN, dirLat, dirLon, dirAlt, itemCount, status,bal_due_date, date, order_status, id_count,v_id,sync) " +
                                                    "values('" + vouchId + "','" + b1 + "','" + b2 + "','" + b3 + "','" + b4 + "','" + b5 + "'," + b6 + "," + b7 + ",'" + b8 + "','" + b9 + "'," + b10 + "," + b11 + ",'" + b12 + "','" + b13 + "','" + b14 + "','" + b15 + "','" + b16 + "','" + b17 + "'," + b18 + "," + b19 +","+b19+ "," + b20 + ",'" + b21 + "','" + b22 + "','" + b23 + "','" + b24 + "'," + it_Count +","+b0+ ",'true')");

                                            Cursor cr1 = DBManager.database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME4 + " where " + DatabaseHelper.e1 + "="+b0, null);
                                            while (cr1.moveToNext()) {
                                                String c2=cr1.getString(2);
                                                String c3=cr1.getString(3);
                                                String c4=cr1.getString(4);
                                                double c5=cr1.getDouble(5);
                                                double c6=cr1.getDouble(6);
                                                double c7=cr1.getDouble(7);
                                                double c8=cr1.getDouble(8);
                                                String c9=cr1.getString(9);
                                                double c10=cr1.getDouble(10);
                                                st1.execute("insert into lineItem(voucherId, itemId, itemName, store, qtyIn, u_price, tot_price, qtyOut, status, unit,v_id,sync) " +
                                                        "values('" + vouchId + "','" + c2 + "',N'" + c3 + "','" + c4 + "'," + c5 + "," + c6 + "," + c7 + "," + c8 + ",'" + c9 + "'," + c10 +","+b0+ ",'true')");
                                                dbManager.updateLineIt(b0,c2);
                                            }
                                            dbManager.updateVouch(b0);
                                        }
                                        prog_tx1.setText("Voucher Information Exported");
                                    }
                                }
                                if(ch4.isChecked())
                                {
                                    Thread.sleep(500);
                                    prog_tx1.setTextColor(Color.BLUE);
                                    prog_tx1.setText("User Information Exporting ...");
                                    prog_bar.setProgress(0);
                                    prog_tx.setText("0%");
                                    Thread.sleep(2000);
                                    DB_COUNT=0;
                                    if(ch6.isChecked())
                                        cr = DBManager.database.rawQuery("SELECT COUNT("+DatabaseHelper.f0+") AS coun FROM " + DatabaseHelper.TABLE_NAME5 + " where " + DatabaseHelper.f9 + "='false'", null);
                                    else
                                        cr = DBManager.database.rawQuery("SELECT COUNT("+DatabaseHelper.f0+") AS coun FROM " + DatabaseHelper.TABLE_NAME5, null);
                                    while (cr.moveToNext()) {
                                        DB_COUNT=cr.getInt(0);
                                    }
                                    if(DB_COUNT==0)
                                    {
                                        prog_tx1.setTextColor(Color.RED);
                                        prog_tx.setText("");
                                        prog_tx1.setText("No User Information Has Found");
                                        Thread.sleep(1000);
                                    }
                                    else
                                    {
                                        prog_bar.setMax(DB_COUNT);
                                        int cc=0;
                                        if(ch6.isChecked())
                                            cr = DBManager.database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME5 + " where " + DatabaseHelper.f9 + "='false'", null);
                                        else
                                            cr = DBManager.database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME5, null);
                                        while (cr.moveToNext()) {
                                            cc=cc+1;
                                            String b1 = cr.getString(1);
                                            String b2 = cr.getString(2);
                                            String b3 = cr.getString(3);
                                            String b4 = cr.getString(4);
                                            String b5 = cr.getString(5);
                                            String b6 = cr.getString(6);
                                            String b7 = cr.getString(7);
                                            prog_bar.setProgress(cc);
                                            double prec=(double) cc/prog_bar.getMax()*100.00;
                                            prog_tx.setText(df.format(prec)+"%");
                                            if(ch5.isChecked()) {
                                                st.execute("delete from userAccount where empId='"+b1+"'");
                                            }
                                            st.execute("insert into userAccount(empId, empName, userName, password, mobile, duety, active,sync) " +
                                                    "values('"+b1+"','"+b2+"','"+b3+"','"+b4+"','"+b5+"','"+b6+"','"+b7+"','true')");
                                            dbManager.updateUser(b1);
                                        }
                                        prog_tx1.setText("User Information Exported");
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(root.getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    thread.start();*/
                }
            }
            else
            {
                if (bt.getText().toString().equalsIgnoreCase("Start Importing"))
                {
                    try
                    {
                        ArrayList B = new ArrayList();
                        ArrayList S = new ArrayList();
                        File file = new File(Environment.getExternalStoragePublicDirectory(""), "Agelegel Cafe/sebtashImport.xls");
                        FileInputStream myInput = new FileInputStream(file);
                        POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
                        HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
                        HSSFSheet mySheet = myWorkBook.getSheetAt(0);
                        Iterator<Row> rowIter = mySheet.rowIterator();
                        while(rowIter.hasNext())
                        {
                            HSSFRow myRow = (HSSFRow) rowIter.next();
                            Iterator<Cell> cellIter = myRow.cellIterator();
                            S = new ArrayList();
                            while(cellIter.hasNext()){
                                HSSFCell myCell = (HSSFCell) cellIter.next();
                                S.add(myCell.toString());
                            }
                            B.add(S);
                        }
                        rowC = 0;
                        import1(rowC, B);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else if (bt.getText().toString().equalsIgnoreCase("Start Exporting"))
                {
                    Toast.makeText(root.getContext(), "Exporting", Toast.LENGTH_LONG).show();
                    File filePath = new File(Environment.getExternalStoragePublicDirectory(""), "Sebtash Water Meter/sebtashExport.xls");
                    //File filePath = new File(Environment.getExternalStorageDirectory() + "/Demo.xls");
                    HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
                    HSSFSheet hssfSheet = hssfWorkbook.createSheet("sebtashExport");
                    Cursor cr = null;//dbManager.fetch4("Not Read", yr, mn);
                    int count=0;
                    do {
                        String nam = cr.getString(1);
                        String sysId = cr.getString(2);
                        String billR = cr.getString(3);
                        String metId = cr.getString(4);
                        String v6 = cr.getString(5);
                        String v7 = cr.getString(6);
                        String v8 = cr.getString(7);
                        String v9 = cr.getString(8);
                        String readU = cr.getString(9);
                        String readN = cr.getString(10);
                        String timeS = cr.getString(11);
                        encodedImage ="";//cr.getString(13);
                        String mobile = cr.getString(14);
                        String reason = cr.getString(15);
                        String status= cr.getString(16);
                        String qr = cr.getString(17);
                        String v77 = cr.getString(18);
                        String v88 = cr.getString(19);
                        double lat1 = Double.parseDouble(v77);
                        double lon1 = Double.parseDouble(v88);
                        int curr = Integer.parseInt(v6);
                        double lat = Double.parseDouble(v7);
                        double lon = Double.parseDouble(v8);
                        double alt = Double.parseDouble(v9);
                        HSSFRow hssfRow = hssfSheet.createRow(count);
                        HSSFCell hssfCell = hssfRow.createCell(0);
                        hssfCell.setCellValue(nam);
                        hssfCell = hssfRow.createCell(1);
                        hssfCell.setCellValue(sysId);
                        hssfCell = hssfRow.createCell(2);
                        hssfCell.setCellValue(billR);
                        hssfCell = hssfRow.createCell(3);
                        hssfCell.setCellValue(metId);
                        hssfCell = hssfRow.createCell(4);
                        hssfCell.setCellValue(curr);
                        hssfCell = hssfRow.createCell(5);
                        hssfCell.setCellValue(lat);
                        hssfCell = hssfRow.createCell(6);
                        hssfCell.setCellValue(lon);
                        hssfCell = hssfRow.createCell(7);
                        hssfCell.setCellValue(alt);
                        hssfCell = hssfRow.createCell(8);
                        hssfCell.setCellValue(readU);
                        hssfCell = hssfRow.createCell(9);
                        hssfCell.setCellValue(readN);
                        hssfCell = hssfRow.createCell(10);
                        hssfCell.setCellValue(timeS);
                        hssfCell = hssfRow.createCell(11);
                        hssfCell.setCellValue(encodedImage);
                        hssfCell = hssfRow.createCell(12);
                        hssfCell.setCellValue(mobile);
                        hssfCell = hssfRow.createCell(13);
                        hssfCell.setCellValue(reason);
                        hssfCell = hssfRow.createCell(14);
                        hssfCell.setCellValue(status);
                        hssfCell = hssfRow.createCell(15);
                        hssfCell.setCellValue(qr);
                        hssfCell = hssfRow.createCell(16);
                        hssfCell.setCellValue(lat1);
                        hssfCell = hssfRow.createCell(17);
                        hssfCell.setCellValue(lon1);
                        count=count+1;
                    } while (cr.moveToNext());
                    if (!filePath.exists()){
                        filePath.createNewFile();
                    }

                    FileOutputStream fileOutputStream= new FileOutputStream(filePath);
                    hssfWorkbook.write(fileOutputStream);
                    Toast.makeText(root.getContext(),"Success",Toast.LENGTH_LONG).show();

                    if (fileOutputStream!=null){
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    });
    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            dialog.dismiss();
        }
    });

        alert = builder.create();
        alert.show();
            }
        });
        return root;
    }
    private static String preFix(String vouchType)
    {
        String preF="";
        if(vouchType.equalsIgnoreCase("Inside Item Sales"))
        {
            preF="IIS";
        }
        else if(vouchType.equalsIgnoreCase("Outside Item Sales"))
        {
            preF="OIS";
        }
        return preF;
    }
    private void import1(int row,ArrayList b)
    {
        try
        {
            try {
                MainActivity.connection = MainActivity.Conn();
            } catch (Exception ex) {
                Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            Statement st2 = MainActivity.connection.createStatement();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        prog_tx1.setTextColor(Color.BLUE);
                        prog_tx1.setText("Item Information Importing ...");
                        prog_bar.setProgress(0);
                        prog_tx.setText("0%");
                        Thread.sleep(2000);
                        DB_COUNT = 0;
                        DB_COUNT=b.size();
                        if(DB_COUNT==0)
                        {
                            prog_tx1.setTextColor(Color.RED);
                            prog_tx.setText("");
                            prog_tx1.setText("No Item Information Has Found");
                            Thread.sleep(1000);
                        }
                        else
                        {
                            prog_bar.setMax(DB_COUNT);
                            int cc = 0;
                            for (int i = row; i < b.size(); i++)
                            {
                                cc=cc+1;
                                String itid=ItemID();
                                ArrayList s= (ArrayList) b.get(i);
                                String itNam=s.get(0).toString();
                                String itCo=s.get(1).toString();
                                String itPri=s.get(2).toString();
                                if(itPri.equals("")||itPri.equals(" "))
                                    itPri="0.0";
                                Date d = new Date();
                                int yr = d.getYear() + 1900;
                                int mn = d.getMonth() + 1;
                                int dt = d.getDate();
                                int hr = d.getHours();
                                int min = d.getMinutes();
                                int sec = d.getSeconds();
                                String s16 = yr + "-" + mn + "-" + dt + " " + hr + ":" + min + ":" + sec;
                                double stokMin=1.0,wholPr=0.0,whoQt=0.0,stokQt=0.0;
                                double saPr=Double.parseDouble(itPri);
                                double prPr=Double.parseDouble(itPri);
                                st.execute("insert into Item(itemId, itemName, itemCode,status, count,expDate,remQty,isNotify, wholePr, minWholeQty)" +
                                        " values('"+itid+"','"+itNam+"','"+itCo+"','Active',"+it_Count1+",'"+s16+"',"+stokMin+",'true',"+wholPr+","+whoQt+")");
                                st.execute("insert into itemBalance(refItemId, storeId, balance, price, purPrice) " +
                                        "values('"+itid+"','Main Store',"+stokQt+","+saPr+","+prPr+")");
                                prog_bar.setProgress(cc);
                                double prec=(double) cc/prog_bar.getMax()*100.00;
                                prog_tx.setText(df.format(prec)+"%");
                            }
                            prog_tx1.setText("Item Information Exported");
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(root.getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
            thread.start();
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            rowC=rowC+1;
            import1(rowC,b);
        }
    }
    private static int it_Count1=0;
    private static String ItemID()
    {
        int digit=5;
        String item_id="";
        try
        {
            try {
                MainActivity.connection = MainActivity.Conn();
            } catch (Exception ex) {
                Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            Statement st2 = MainActivity.connection.createStatement();
            int idco=0;
            ResultSet cr=st.executeQuery("select count from Item order by count");
            while(cr.next())
            {
                idco=cr.getInt("count");
            }
            idco=idco+1;
            String jk=""+idco;
            if(jk.length()>=digit)
            {
                item_id="It-"+idco;
            }
            else
            {
                int diff=digit-jk.length();
                String pp="";
                for(int j=0;j<diff;j++)
                {
                    pp=pp+"0";
                }
                item_id="It-"+pp+idco;
            }
            it_Count1=idco;
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return item_id;
    }
    private static int it_Count=0;
    private static String ItemID(String vouchType)
    {
        String item_id="";
        int digit=5;
        try
        {
            String preFix=preFix(vouchType);
            Statement st = MainActivity.connection.createStatement();
            int idco=0;
            ResultSet cr =st.executeQuery("select id_count from voucher where voucherType='"+vouchType+"' order by id_count");
            while(cr.next())
            {
                idco=cr.getInt("id_count");
            }
            idco=idco+1;
            String jk=""+idco;
            if(jk.length()>=digit)
            {
                item_id=preFix+"-"+idco;
            }
            else
            {
                int diff=digit-jk.length();
                String pp="";
                for(int j=0;j<diff;j++)
                {
                    pp=pp+"0";
                }
                item_id=preFix+"-"+pp+idco;
            }
            it_Count=idco;
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return item_id;
    }
}