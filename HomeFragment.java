package org.o7planning.agelegecafe.ui.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavGraph;

import com.google.android.material.tabs.TabLayout;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.DatabaseHelper;
import org.o7planning.agelegecafe.MainActivity;
import org.o7planning.agelegecafe.R;
import org.o7planning.agelegecafe.ui.Table.Table_Reg;
import org.o7planning.agelegecafe.ui.item_registration.CreateItFragment;
import org.o7planning.agelegecafe.ui.item_registration.ModifyItFragment;
import org.o7planning.agelegecafe.ui.sales_invoice.Sales_InvoiceFragment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private static View root=null;
    private static DBManager dbManager;
    public static LinearLayout l0=null,l1=null,l2=null;
    private static EditText e1=null,e2=null;
    private static TextView tx=null,b2=null,b1=null,tx2=null,timTx=null;
    public static TextView t1=null,t01=null,t2=null,t02=null,t3=null,t03=null,t4=null,t04=null;
    private static ProgressDialog progress=null;
    private static Handler progHand=new Handler();
    private static int fileS=0,progStat=0;
    private static int DB_COUNT=0;
    private static TabLayout tabLayout;
    private static FrameLayout frameLayout;
    private static Fragment fragment = null;
    private static Fragment fragment1 = null;
    private static Fragment fragment2 = null;
    private static FragmentManager fragmentManager=null;
    private static FragmentTransaction fragmentTransaction;
    public static AppCompatActivity activity_replace=null;
    public static ArrayList B_ITEM=new ArrayList();
    public static ArrayList B_Transac=new ArrayList();
    public static ArrayList B_Collected=new ArrayList();
    public static ArrayList B_UnCollected=new ArrayList();
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static DecimalFormat df1= new DecimalFormat("#,###.00");
    public HomeFragment()
    {

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        root = inflater.inflate(R.layout.fragment_home, container, false);
        try
        {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        l0=root.findViewById(R.id.homelin);
        l1=root.findViewById(R.id.homelin1);
        l2=root.findViewById(R.id.homelin2);
        b1=root.findViewById(R.id.homb1);
        b2=root.findViewById(R.id.homb2);
        e1=root.findViewById(R.id.home1);
        e2=root.findViewById(R.id.home2);
        tx=root.findViewById(R.id.homtx);
        tx2=root.findViewById(R.id.hom_tx2);
        timTx=root.findViewById(R.id.hom_tx3);

        t1=root.findViewById(R.id.home_tx1);
        t01=root.findViewById(R.id.home_tx01);
        t2=root.findViewById(R.id.home_tx2);
        t02=root.findViewById(R.id.home_tx02);
        t3=root.findViewById(R.id.home_tx3);
        t03=root.findViewById(R.id.home_tx03);
        t4=root.findViewById(R.id.home_tx4);
        t04=root.findViewById(R.id.home_tx04);
        MainActivity.TimeTx1=timTx;
        try
        {
            tabLayout=(TabLayout)root.findViewById(R.id.home_tabLayout);
            frameLayout=(FrameLayout)root.findViewById(R.id.home_frameLayout);
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
/*
        fragment = new Daily_Transaction();
        fragmentManager=getChildFragmentManager();
        //fragmentManager = fragment.getChildFragmentManager();
        fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
 */
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new Daily_Transaction();
                        break;
                    case 1:
                        if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Waiter"))
                        {
                            Collected_Table.CHOICE = 1;
                            fragment = new Collected_Table();
                        }
                        else
                            fragment = new Parties();
                        break;
                    case 2:
                        if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Waiter"))
                        {
                            Uncollected_Table.CHOICE=1;
                            fragment = new Uncollected_Table();
                        }
                        else
                            fragment = new Item_List();
                        break;
                }
                FragmentManager fm = getChildFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                //FragmentTransaction ft = fm.openTransaction();
                ft.replace(R.id.home_frameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        String vv="Forgot Password ?";
        SpannableString content = new SpannableString(vv);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tx2.setText(content);
        if(MainActivity.NEW_OPEN==0)
        {
            l0.removeView(l2);
        }
        else
        {
            l0.removeView(l1);
            MainActivity.nav(1,MainActivity.Emp_NAME,MainActivity.Emp_USERNAME);
        }
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connec();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    activity_replace= (AppCompatActivity) root.getContext();

                    Fragment inputFragment = new Sales_InvoiceFragment();


                    FragmentTransaction fragmentTransaction = activity_replace.getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main, inputFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    /*
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
                    progress=new ProgressDialog(root.getContext());
                    progress.setCancelable(true);
                    progress.setMessage("Synchronize From Mobile To Server");
                    progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progress.setProgress(0);
                    //progress.setMax(100);
                    progress.show();
                    progStat=0;
                    fileS=0;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try
                            {
                                Cursor cr =null;
                                DB_COUNT=0;
                                cr = DBManager.database.rawQuery("SELECT COUNT("+DatabaseHelper.c0+") AS coun FROM " + DatabaseHelper.TABLE_NAME2 + " where " + DatabaseHelper.c10 + "='false'", null);
                                while (cr.moveToNext())
                                {
                                    DB_COUNT=cr.getInt(0);
                                }
                                cr = DBManager.database.rawQuery("SELECT COUNT("+DatabaseHelper.b0+") AS coun FROM " + DatabaseHelper.TABLE_NAME1 + " where " + DatabaseHelper.b3 + "='false'", null);
                                while (cr.moveToNext()) {
                                    DB_COUNT=DB_COUNT+cr.getInt(0);
                                }
                                cr = DBManager.database.rawQuery("SELECT COUNT("+DatabaseHelper.d0+") AS coun FROM " + DatabaseHelper.TABLE_NAME3 + " where " + DatabaseHelper.d25 + "='false'", null);
                                while (cr.moveToNext()) {
                                    DB_COUNT=DB_COUNT+cr.getInt(0);
                                }
                                cr = DBManager.database.rawQuery("SELECT COUNT("+DatabaseHelper.f0+") AS coun FROM " + DatabaseHelper.TABLE_NAME5 + " where " + DatabaseHelper.f9 + "='false'", null);
                                while (cr.moveToNext()) {
                                    DB_COUNT=DB_COUNT+cr.getInt(0);
                                }
                                if(DB_COUNT==0)
                                {
                                    progress.setMessage("Synchronize From Mobile To Server. No Data Has Found");
                                    Thread.sleep(1000);
                                }
                                else
                                {
                                    progress.setMax(DB_COUNT);
                                    cr = DBManager.database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME2 + " where " + DatabaseHelper.c10 + "='false'", null);
                                    while (cr.moveToNext()) {
                                        progStat=progStat+1;
                                        String b1 = cr.getString(1);
                                        String b2 = cr.getString(2);
                                        double b3=cr.getDouble(3);
                                        String b4 = cr.getString(4);
                                        String b5 = cr.getString(5);
                                        double b6=cr.getDouble(6);
                                        double b7=cr.getDouble(7);
                                        double b8=cr.getDouble(8);
                                        int b9=cr.getInt(9);
                                        st.execute("delete from item where itemId='"+b1+"'");
                                        st.execute("insert into item(itemId, itemName, unit, itemCat, store, itemPrice, itemBalance, begBalance, it_count,sync) " +
                                                "values('"+b1+"',N'"+b2+"',"+b3+",'"+b4+"','"+b5+"',"+b6+","+b7+","+b8+","+b9+",'true')");
                                        dbManager.updateItem(b1);
                                        progress.setProgress(progStat);
                                        Thread.sleep(50);
                                    }
                                    cr = DBManager.database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME1 + " where " + DatabaseHelper.b3 + "='false'", null);
                                    while (cr.moveToNext()) {
                                        progStat=progStat+1;
                                        String b1 = cr.getString(1);
                                        st.execute("delete from storeList where store='"+b1+"'");
                                        st.execute("insert into storeList(store,sync) values('"+b1+"','true')");
                                        dbManager.updateStore(b1);
                                        progress.setProgress(progStat);
                                        Thread.sleep(50);
                                    }
                                    cr = DBManager.database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME3 + " where " + DatabaseHelper.d25 + "='false'", null);
                                    while (cr.moveToNext()) {
                                        progStat=progStat+1;
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
                                        st.execute("delete from voucher where v_id='"+b0+"'");
                                        st.execute("delete from lineItem where v_id='"+b0+"'");
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
                                        progress.setProgress(progStat);
                                        Thread.sleep(50);
                                    }
                                    cr = DBManager.database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME5 + " where " + DatabaseHelper.f9 + "='false'", null);
                                    while (cr.moveToNext()) {
                                        progStat=progStat+1;
                                        String b1 = cr.getString(1);
                                        String b2 = cr.getString(2);
                                        String b3 = cr.getString(3);
                                        String b4 = cr.getString(4);
                                        String b5 = cr.getString(5);
                                        String b6 = cr.getString(6);
                                        String b7 = cr.getString(7);
                                        st.execute("delete from userAccount where empId='"+b1+"'");
                                        st.execute("insert into userAccount(empId, empName, userName, password, mobile, duety, active,sync) " +
                                                "values('"+b1+"','"+b2+"','"+b3+"','"+b4+"','"+b5+"','"+b6+"','"+b7+"','true')");
                                        dbManager.updateUser(b1);
                                        progress.setProgress(progStat);
                                        Thread.sleep(50);
                                    }
                                }
                                if(progStat>=DB_COUNT)
                                {
                                    try
                                    {
                                        Thread.sleep(1000);
                                        //progress.dismiss();
                                    }
                                    catch (Exception ex)
                                    {
                                        Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                    progress.dismiss();
                                }
                            }
                            catch (Exception ex)
                            {
                                Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }).start();

                     */
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try
                {
                    int ck=0;
                    String s1=e1.getText().toString();
                    String s2=e2.getText().toString();
                    if(s1.equals("SebTash")&&s2.equals("p@$$4admin"))
                    {
                        ck=1;
                        MainActivity.Emp_ID="Emp-Sebtash";
                        MainActivity.Emp_NAME="AGELEGEL Administrator";
                        MainActivity.Emp_USERNAME="sebtash";
                        MainActivity.Emp_MOBILE="+251960095066";
                        MainActivity.Emp_PRIVILAGE="Administrator";
                        Toast.makeText(root.getContext(),"Login Success",Toast.LENGTH_LONG).show();
                        l0.removeView(l1);
                        l0.addView(l2);
                        MainActivity.NEW_OPEN=1;
                        MainActivity.nav(1,MainActivity.Emp_NAME,MainActivity.Emp_USERNAME);
                        l0.setBackgroundResource(R.mipmap.back_g4);

                        fragment = new Daily_Transaction();

                        fragmentManager=getChildFragmentManager();
                        //fragmentManager = fragment.getChildFragmentManager();
                        fragmentTransaction =fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.home_frameLayout, fragment);
                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        fragmentTransaction.commit();
                    }
                    else
                    {
                        Connec();
                        Statement st = MainActivity.connection.createStatement();
                        Statement st1 = MainActivity.connection.createStatement();
                        ResultSet rs=st.executeQuery("select empId, empName, userName, mobile, duety, active from userAccount where active='Active' and userName='"+s1+"' and password='"+s2+"'");
                        while (rs.next())
                        {
                            ck = 1;
                            MainActivity.Emp_ID = rs.getString("empId");
                            MainActivity.Emp_NAME = rs.getString("empName");
                            MainActivity.Emp_USERNAME = rs.getString("userName");
                            MainActivity.Emp_MOBILE = rs.getString("mobile");
                            MainActivity.Emp_PRIVILAGE = rs.getString("duety");
                            Toast.makeText(root.getContext(), "Login Success", Toast.LENGTH_LONG).show();
                            l0.removeView(l1);
                            l0.addView(l2);
                            l0.setBackgroundResource(R.mipmap.back_g4);
                            MainActivity.NEW_OPEN = 1;
                            MainActivity.nav(1, MainActivity.Emp_NAME, MainActivity.Emp_USERNAME);
                            Connec();
                            fragment = new Daily_Transaction();
                            fragmentManager=getChildFragmentManager();
                            fragmentTransaction =fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.home_frameLayout, fragment);
                            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            fragmentTransaction.commit();
                            UpperDashboard(1);
                            if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Waiter"))
                            {
                                tabLayout.getTabAt(1).setText("Collected Table");
                                tabLayout.getTabAt(2).setText("UnCollected Table");
                            }
                        }
                    }
                    if(ck==0)
                    {
                        Toast.makeText(root.getContext(),"Login Error",Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        tx.setBackgroundResource(R.mipmap.offline);
        if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Waiter"))
        {
            tabLayout.getTabAt(1).setText("Collected Table");
            tabLayout.getTabAt(2).setText("UnCollected Table");
        }
        UpperDashboard(MainActivity.NEW_OPEN);
        return root;
    }
    private static void UpperDashboard(int open)
    {
        try
        {
            //MainActivity.connection=MainActivity.Conn();
            if(open==1)
            {
                Connec();
                Statement st00 = MainActivity.connection.createStatement();
                Statement st11 = MainActivity.connection.createStatement();
                Statement st22 = MainActivity.connection.createStatement();
                Statement st01 = MainActivity.connection.createStatement();
                Statement st02 = MainActivity.connection.createStatement();
                Statement st03 = MainActivity.connection.createStatement();
                Statement st04 = MainActivity.connection.createStatement();
                HomeFragment.t1.setText("Br. 0.00");
                HomeFragment.t2.setText("Br. 0.00");
                HomeFragment.t3.setText("Br. 0.00");
                HomeFragment.t4.setText("Br. 0.00");
                if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Waiter"))
                {
                    HomeFragment.t01.setText("Cash IN");
                    HomeFragment.t02.setText("Cash OUT");
                    HomeFragment.t03.setText(" Today Balance");
                    HomeFragment.t04.setText("Total Balance");
                    double debit=0.0,credit=0.0,totBal=0.0;
                    ResultSet r1=st00.executeQuery("select SUM(debit) AS debit,SUM(credit) AS credit from ledger where vouchStatus='true' and userName='"+MainActivity.Emp_USERNAME+"' and workDate='"+MainActivity.Main_WORK_DATE+"'");
                    while (r1.next())
                    {
                        debit=r1.getDouble("debit");
                        credit=r1.getDouble("credit");
                    }
                    totBal=debit-credit;
                    double debit1=0.0,credit1=0.0,totBal1=0.0;
                    ResultSet rr1=st00.executeQuery("select SUM(debit) AS debit,SUM(credit) AS credit from ledger where vouchStatus='true' and userName='"+MainActivity.Emp_USERNAME+"'");
                    while (rr1.next())
                    {
                        debit1=rr1.getDouble("debit");
                        credit1=rr1.getDouble("credit");
                    }
                    totBal1=debit1-credit1;
                    if(debit>0)
                        t1.setText(df1.format(debit));
                    else
                        t1.setText(df.format(debit));
                    if(credit>0)
                        t2.setText(df1.format(credit));
                    else
                        t2.setText(df.format(credit));
                    if(totBal>0)
                        t3.setText(df1.format(totBal));
                    else
                        t3.setText(df.format(totBal));
                    if(totBal1>0)
                        t4.setText(df1.format(totBal1));
                    else
                        t4.setText(df.format(totBal1));
                }
                else if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Cashier"))
                {
                    HomeFragment.t01.setText("Today Balance");
                    HomeFragment.t02.setText("Cash IN");
                    HomeFragment.t03.setText("Cash OUT");
                    HomeFragment.t04.setText("End Balance");
                    double debit=0.0,credit=0.0,totBal=0.0;
                    ResultSet r1=st00.executeQuery("select SUM(debit) AS debit,SUM(credit) AS credit from ledger where vouchStatus='true' and userName='"+MainActivity.Emp_USERNAME+"' and workDate='"+MainActivity.Main_WORK_DATE+"'");
                    while (r1.next())
                    {
                        debit=r1.getDouble("debit");
                        credit=r1.getDouble("credit");
                    }
                    totBal=debit-credit;
                    double debit1=0.0,credit1=0.0,totBal1=0.0;
                    ResultSet rr1=st00.executeQuery("select SUM(debit) AS debit,SUM(credit) AS credit from ledger where vouchStatus='true' and userName='"+MainActivity.Emp_USERNAME+"'");
                    while (rr1.next())
                    {
                        debit1=rr1.getDouble("debit");
                        credit1=rr1.getDouble("credit");
                    }
                    totBal1=debit1-credit1;
                    if(debit>0)
                        t2.setText(df1.format(debit));
                    else
                        t2.setText(df.format(debit));
                    if(credit>0)
                        t3.setText(df1.format(credit));
                    else
                        t3.setText(df.format(credit));
                    if(totBal>0)
                        t1.setText(df1.format(totBal));
                    else
                        t1.setText(df.format(totBal));
                    if(totBal1>0)
                        t4.setText(df1.format(totBal1));
                    else
                        t4.setText(df.format(totBal1));
                }
                else if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Manager"))
                {
                    HomeFragment.t01.setText("Today Income");
                    HomeFragment.t02.setText("Today Expenses");
                    HomeFragment.t03.setText("Today Balance");
                    HomeFragment.t04.setText("Total Balance");
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public int doOpp()
    {
        while (fileS<=1000)
        {
            fileS++;
            if(fileS==1000)
            {
                return 10;
            }
            else if(fileS==2000)
            {
                return 20;
            }
            else if(fileS==3000)
            {
                return 30;
            }
            else if(fileS==4000)
            {
                return 40;
            }
        }
        return 100;
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
    public static void Connec()
    {
        try
        {
            Connection con = MainActivity.Conn();
            MainActivity.connection=con;
            Statement st = con.createStatement();
            MainActivity.connection=con;
            HomeFragment.tx.setBackgroundResource(R.mipmap.online);
        }
        catch (Exception ex)
        {
            HomeFragment.tx.setBackgroundResource(R.mipmap.offline);
        }
    }
}