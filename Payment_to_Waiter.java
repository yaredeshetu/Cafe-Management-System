package org.o7planning.agelegecafe.ui.Payment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.GPSTracker;
import org.o7planning.agelegecafe.MainActivity;
import org.o7planning.agelegecafe.R;
import org.o7planning.agelegecafe.ui.home.HomeFragment;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Payment_to_Waiter extends Fragment {

    private static View root=null;
    private static DBManager dbManager;
    private static View.OnClickListener txClick2=null;
    private static TextView b1=null,b2=null,tx1=null,tx2=null,tx3=null,tx4=null,tx5=null;
    private static EditText e1=null,e2=null,e3=null;
    private static TableLayout table=null;
    private AlertDialog alert=null;
    private AlertDialog alert1=null;
    private static String tabID="",UsName="",Work_Date="",PREFIX="";
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static DecimalFormat df1= new DecimalFormat("#,###.00");
    private static View.OnClickListener txClick1=null;
    private static GPSTracker gps;
    public Payment_to_Waiter()
    {

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.payment_waiter, container, false);
        try {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        } catch (Exception ex) {
            Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        txClick1=new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try
                {
                    String itid=v.getTooltipText().toString();
                    try {
                        MainActivity.connection = MainActivity.Conn();
                    } catch (Exception ex) {
                        Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    Statement st = MainActivity.connection.createStatement();
                    Statement st1 = MainActivity.connection.createStatement();
                    ResultSet r=st.executeQuery("select * from Item where itemId='"+itid+"'");
                    String itNm="";
                    while (r.next())
                    {
                        itNm=r.getString("itemName");
                    }
                    double pri=0.0;
                    ResultSet cr1=st1.executeQuery("select * from itemBalance where refItemId='"+itid+"'");
                    while (cr1.next())
                    {
                        pri=cr1.getDouble("price");
                    }
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.putExtra(Intent.EXTRA_TEXT, "Dear Sir/Madam,\nThanks For Doing Business with us.\nHere are Item Details.\nItem Name:"+itNm+"\nSales Price:"+pri+"\nThank You.\nGlance Cafe\n\nSystem By:Sebtash Technology:-09 60 09 50 66");
                    startActivity(Intent.createChooser(share, "Share Text"));
                    Toast.makeText(root.getContext(),"Item Name:"+itNm+"\nSales Price:"+pri, Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        };
        txClick2=new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try
                {
                    String itid=v.getTooltipText().toString();
                    UsName=itid;
                    for(int i=0;i<table.getChildCount();i++)
                    {
                        TableRow Mrow= (TableRow) table.getChildAt(i);
                        LinearLayout Mlin= (LinearLayout) Mrow.getChildAt(0);
                        String toT=Mlin.getTooltipText().toString();
                        if(itid.equalsIgnoreCase(toT))
                        {
                            LinearLayout Tlin= (LinearLayout) Mlin.getChildAt(0);
                            TextView Tv1= (TextView) Tlin.getChildAt(0);
                            tx4.setText(Tv1.getText().toString());
                            tx4.setTypeface(null, Typeface.BOLD_ITALIC);
                            Mlin.setBackgroundResource(R.drawable.border_round_select);
                        }
                        else
                        {
                            Mlin.setBackgroundResource(R.drawable.border_round1);
                        }
                    }
                    Statement st1 = MainActivity.connection.createStatement();
                    Statement st2 = MainActivity.connection.createStatement();
                    double debit=0.0,credit=0.0,totBal=0.0;
                    double debit1=0.0,credit1=0.0,totBal1=0.0;
                    ResultSet rr1=st1.executeQuery("select SUM(debit) AS debit,SUM(credit) AS credit from ledger where vouchStatus='true' and userName='"+itid+"'");
                    while (rr1.next())
                    {
                        debit=rr1.getDouble("debit");
                        credit=rr1.getDouble("credit");
                    }
                    totBal=debit-credit;
                    tx3.setText(df.format(totBal));
                    ResultSet rr2=st2.executeQuery("select SUM(debit) AS debit,SUM(credit) AS credit from ledger where vouchStatus='true' and userName='"+itid+"' and workDate='"+Work_Date+"'");
                    while (rr2.next())
                    {
                        debit1=rr2.getDouble("debit");
                        credit1=rr2.getDouble("credit");
                    }
                    totBal1=debit1-credit1;
                    tx2.setText(df.format(totBal1));
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        };
        table=root.findViewById(R.id.pay_wa_scroll);
        b1=root.findViewById(R.id.pay_wa_b1);
        b2=root.findViewById(R.id.pay_wa_b2);
        tx1=root.findViewById(R.id.pay_wa_tx1);
        tx2=root.findViewById(R.id.pay_wa_tx2);
        tx3=root.findViewById(R.id.pay_wa_tx3);
        tx4=root.findViewById(R.id.pay_wa_tx4);
        tx5=root.findViewById(R.id.pay_wa_tx5);
        e1=root.findViewById(R.id.pay_wa_e1);
        e2=root.findViewById(R.id.pay_wa_e2);
        e3=root.findViewById(R.id.pay_wa_e3);
        PREFIX="";
        Date d = new Date();
        int yr = d.getYear() + 1900;
        int mn = d.getMonth() + 1;
        int dt = d.getDate();
        String s166 = yr + "-" + mn + "-" + dt;
        e2.setText(s166);
        e1.setText(ItemID("Payment To Waiter"));
        try {
            Statement st1 = MainActivity.connection.createStatement();
            Statement st2=MainActivity.connection.createStatement();
            String s16 = "";
            Work_Date="";
            ResultSet r1=st2.executeQuery("select date from workingDate");
            int ckk=0;
            while(r1.next())
            {
                ckk=1;
                s16=r1.getString("date");
            }
            if(ckk==0)
            {
                tx1.setText("No Working Date Found");
                Toast.makeText(root.getContext(),"You Have To Set Working Date First.",Toast.LENGTH_LONG).show();
            }
            else
            {
                LocalDate cur1= LocalDate.parse(s16);
                DayOfWeek yu=cur1.getDayOfWeek();
                tx1.setText(s16+" ("+yu+")");
                Work_Date=s16;
            }
            double debit=0.0,credit=0.0,totBal=0.0;
            r1=st1.executeQuery("select SUM(debit) AS debit,SUM(credit) AS credit from ledger where vouchStatus='true' and userName='"+MainActivity.Emp_USERNAME+"'");
            while (r1.next())
            {
                debit=r1.getDouble("debit");
                credit=r1.getDouble("credit");
            }
            totBal=debit-credit;
            if(totBal>0)
                tx5.setTextColor(Color.parseColor("#00FF00"));
            else
                tx5.setTextColor(Color.parseColor("#FF0000"));
            tx5.setText(df.format(totBal));
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    String ss3=e3.getText().toString();
                    if(ss3.equals(""))
                    {
                        Toast.makeText(root.getContext(),"Please Insert Payment Amount.",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        double s331=Double.parseDouble(ss3);
                        Statement stmt=MainActivity.connection.createStatement();
                        double debit=0.0,credit=0.0,totBal=0.0;
                        ResultSet r1=stmt.executeQuery("select SUM(debit) AS debit,SUM(credit) AS credit from ledger where vouchStatus='true' and userName='"+MainActivity.Emp_USERNAME+"'");
                        while (r1.next())
                        {
                            debit=r1.getDouble("debit");
                            credit=r1.getDouble("credit");
                        }
                        totBal=debit-credit;
                        if(s331>totBal)
                        {
                            Toast.makeText(root.getContext(),"You Haven't enough Balance to Pay.",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                            builder.setTitle(R.string.app_name);
                            builder.setIcon(R.mipmap.glance_logo);
                            builder.setMessage("Are You Sure ?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    dialog.dismiss();
                                    try
                                    {
                                        String ss1=e1.getText().toString();
                                        String voucherId = e1.getText().toString();
                                        Date d = new Date();
                                        int yr = d.getYear() + 1900;
                                        int mn = d.getMonth() + 1;
                                        int dt = d.getDate();
                                        int hr = d.getHours();
                                        int min = d.getMinutes();
                                        int sec = d.getSeconds();
                                        String s16 = yr + "-" + mn + "-" + dt + " " + hr + ":" + min + ":" + sec;
                                        double s33=Double.parseDouble(ss3);
                                        double s44=0.0;
                                        String Vto=tx4.getText().toString();
                                        String V_TYPE="Payment To Waiter";
                                        String price_word="";
                                        double dddss=s33;
                                        DecimalFormat df = new DecimalFormat("0.00");
                                        String Vprice=df.format(dddss);
                                        double ddds=Double.parseDouble(Vprice);
                                        int dd=(int) (ddds/1);
                                        String ww="";
                                        if(dd>0)
                                        {
                                            ww=NumToWord(dd);
                                        }
                                        else
                                        {
                                            ww="Zero";
                                        }
                                        double hkj=ddds-dd;
                                        String kl=df.format(hkj);
                                        double hj=Double.parseDouble(kl);
                                        hj=hj*100;
                                        int dd1=(int) (hj/1);
                                        if(dd1>0)
                                        {
                                            String ww1=NumToWord(dd1);
                                            price_word=ww+" Birr and "+ww1+" Cents";
                                        }
                                        else
                                        {
                                            price_word=ww+" Birr";
                                        }
                                        Statement st = MainActivity.connection.createStatement();
                                        String Vpurpose="Daily Waiter Account Establishment";
                                        String isjour1="true";
                                        String Vbackward="no";
                                        String Vforward="no";
                                        String sql="insert into voucher values('"+voucherId+"','"+Vto+"',"+s33+",0,"+s33+",'"+V_TYPE+"','"+Work_Date
                                                +"','Daily Waiter Account Establishment','true','false','false','false','"+MainActivity.Emp_USERNAME+"','no','no',"
                                                +"0,'NUL',"+it_Count+",'"+s16+"','"+price_word+"','"+PREFIX+"','Prepared','false','false')";
                                        st.execute(sql);
                                        st.execute("insert into ledger(voucherId,userName,timeStamp,debit,credit,vouchStatus,workDate) " +
                                                "values('"+ss1+"','"+UsName+"','"+s16+"',"+s33+","+s44+",'true','"+Work_Date+"')");
                                        st.execute("insert into ledger(voucherId,userName,timeStamp,debit,credit,vouchStatus,workDate) " +
                                                "values('"+ss1+"','"+MainActivity.Emp_USERNAME+"','"+s16+"',"+s44+","+s33+",'true','"+Work_Date+"')");
                                        Toast.makeText(root.getContext(),"Success",Toast.LENGTH_LONG).show();
                                        e3.setText("");
                                        tx3.setText("");
                                        tx2.setText("");
                                        e1.setText(ItemID("Payment To Waiter"));
                                        double debit=0.0,credit=0.0,totBal=0.0;
                                        ResultSet rr1=st.executeQuery("select SUM(debit) AS debit,SUM(credit) AS credit from ledger where vouchStatus='true' and userName='"+MainActivity.Emp_USERNAME+"'");
                                        while (rr1.next())
                                        {
                                            debit=rr1.getDouble("debit");
                                            credit=rr1.getDouble("credit");
                                        }
                                        totBal=debit-credit;
                                        tx5.setText(df.format(totBal));
                                        appendRow1(table);
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
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        appendRow1(table);
        return root;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void appendRow1(TableLayout table) {
        try
        {
            table.removeAllViews();
            TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(5, 0, 0, 10);
            TableRow.LayoutParams rowParm=new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            rowParm.setMargins(5,5,5,5);
            rowParm.weight= 1.0F;
            TableRow.LayoutParams tx_Parm1=new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            tx_Parm1.setMargins(5,5,5,5);
            tx_Parm1.weight= 0.9F;
            TableRow.LayoutParams tx_Parm2=new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            tx_Parm2.setMargins(5,5,5,5);
            tx_Parm2.weight= 0.1F;
            LinearLayout.LayoutParams tosubParms=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            tosubParms.setMargins(5, 5, 5, 0);
            LinearLayout.LayoutParams tosubParms1=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            tosubParms1.setMargins(5, 5, 5, 5);
            LinearLayout.LayoutParams mainParms=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            mainParms.setMargins(5, 5, 5, 0);
            mainParms.weight= 0.4F;
            LinearLayout.LayoutParams mainParms2=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            mainParms2.setMargins(10, 5, 10, 5);
            mainParms2.weight= 0.6F;
            LinearLayout.LayoutParams mainParms1=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            mainParms1.setMargins(5, 0, 5, 0);
            TableRow row = new TableRow(root.getContext());
            row.setWeightSum(1.0F);
            LinearLayout MainLin=new LinearLayout(root.getContext());
            MainLin.setOrientation(LinearLayout.HORIZONTAL);
            MainLin.setBackgroundResource(R.drawable.border_round1);
            MainLin.setWeightSum(1.0F);
            Statement st = MainActivity.connection.createStatement();
            Statement st2 = MainActivity.connection.createStatement();
            Statement st1=MainActivity.connection.createStatement();
            ResultSet r=st.executeQuery("select empName,userName from userAccount where duety='Waiter'");
            while (r.next())
            {
                row = new TableRow(root.getContext());
                row.setWeightSum(1.0F);
                MainLin=new LinearLayout(root.getContext());
                MainLin.setOrientation(LinearLayout.VERTICAL);
                MainLin.setBackgroundResource(R.drawable.border_round1);
                MainLin.setWeightSum(1);
                String itid=r.getString("userName");
                String itnm=r.getString("empName");
                double debit=0.0,credit=0.0,totBal=0.0;
                ResultSet r1=st1.executeQuery("select SUM(debit) AS debit,SUM(credit) AS credit from ledger where vouchStatus='true' and userName='"+itid+"'");
                while (r1.next())
                {
                    debit=r1.getDouble("debit");
                    credit=r1.getDouble("credit");
                }
                totBal=debit-credit;
                LinearLayout totLin=new LinearLayout(root.getContext());
                totLin.setOrientation(LinearLayout.HORIZONTAL);
                totLin.setWeightSum(1.0F);
                TextView l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText(itnm);
                l1.setTextSize(14);
                l1.setTypeface(null, Typeface.BOLD_ITALIC);
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                totLin.addView(l1,tx_Parm1);
                totLin.setTooltipText(itid);
                totLin.setOnClickListener(txClick2);
                l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setTypeface(null, Typeface.ITALIC);
                l1.setBackgroundResource(R.drawable.share_24);
                l1.setTextSize(14);
                l1.setTooltipText(itid);
                l1.setGravity(Gravity.RIGHT);
                l1.setOnClickListener(txClick1);
                totLin.addView(l1,tx_Parm2);
                totLin.setTooltipText(itid);
                totLin.setOnClickListener(txClick2);

                LinearLayout totLin1=new LinearLayout(root.getContext());
                totLin1.setOrientation(LinearLayout.VERTICAL);
                l1 = new TextView(root.getContext());
                l1.setTextColor(Color.parseColor("#403F3F"));
                l1.setText("Balance");
                l1.setTypeface(null, Typeface.ITALIC);
                l1.setTextSize(12);
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                totLin1.addView(l1,tosubParms1);
                l1 = new TextView(root.getContext());
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                l1.setTextColor(Color.BLACK);
                l1.setText(df.format(totBal));
                l1.setTextSize(14);
                totLin1.addView(l1,tosubParms);
                LinearLayout totLin2=new LinearLayout(root.getContext());
                totLin2.setOrientation(LinearLayout.VERTICAL);
                l1 = new TextView(root.getContext());
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                l1.setTextColor(Color.parseColor("#403F3F"));
                l1.setText("Debit(+)");
                l1.setTextSize(12);
                totLin2.addView(l1,tosubParms1);
                l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText(df.format(debit));
                l1.setTextSize(14);
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                totLin2.addView(l1,tosubParms1);
                LinearLayout totLin3=new LinearLayout(root.getContext());
                totLin3.setOrientation(LinearLayout.VERTICAL);
                l1 = new TextView(root.getContext());
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                l1.setTextColor(Color.parseColor("#403F3F"));
                l1.setText("Credit(-)");
                l1.setTextSize(12);
                totLin3.addView(l1,tosubParms);
                l1 = new TextView(root.getContext());
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                l1.setTextColor(Color.BLACK);
                l1.setText(df.format(credit));
                l1.setTextSize(14);
                totLin3.addView(l1,tosubParms1);
                MainLin.setTooltipText(itid);
                MainLin.setOnClickListener(txClick2);
                LinearLayout totLin0=new LinearLayout(root.getContext());
                totLin0.setOrientation(LinearLayout.HORIZONTAL);
                totLin0.addView(totLin1,mainParms2);
                totLin0.addView(totLin2,mainParms2);
                totLin0.addView(totLin3,mainParms2);
                MainLin.addView(totLin,mainParms);
                MainLin.addView(totLin0,mainParms1);
                row.addView(MainLin,rowParm);
                table.addView(row,params);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private static String preFix(String vouchType)
    {
        String preF="";
        if(vouchType.equalsIgnoreCase("Payment To Waiter"))
        {
            preF="PW";
        }
        else if(vouchType.equalsIgnoreCase("Outside Item Sales"))
        {
            preF="OIS";
        }
        else if(vouchType.equalsIgnoreCase("Sales Order"))
        {
            preF="SO";
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
            try {
                MainActivity.connection = MainActivity.Conn();
            } catch (Exception ex) {
                Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            String preFix=preFix(vouchType);
            PREFIX=preFix;
            int idco=0;
            ResultSet cr =st.executeQuery("select idCount from voucher where voucherType='"+vouchType+"' order by idCount");
            while(cr.next())
            {
                idco=cr.getInt("idCount");
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

    public static String NumToWord(int n)
    {
        String WORD="";
        WORD="";
        String n1=n+"";
        int ncount=0;
        ncount=n1.length();
        String hund[]={" "," "," Hundered "," Thousand"," Million"," Billion"};
        int i=ncount-1;
        int numun=0;
        String chchch="";
        String  one[]={" "," One"," Two"," Three"," Four"," Five"," Six"," Seven"," Eight"," Nine"," Ten"," Eleven"," Twelve"," Thirteen"," Fourteen","Fifteen"," Sixteen"," Seventeen"," Eighteen"," Nineteen"};
        String ten[]={" "," "," Twenty"," Thirty"," Forty"," Fifty"," Sixty"," Seventy"," Eighty"," Ninety"};
        while(i>=0)
        {
            if(n==0)
            {
                i=-1;
            }
            if(n>0&&n<100)
            {
                //toWord((n%100)," ");
                numun=(n%100);
                chchch=" ";
                if(numun> 19)
                {
                    WORD=WORD+ten[numun/10]+" "+one[numun%10];
                }
                else
                {
                    WORD=WORD+one[numun];
                }
                WORD=WORD+chchch;
                break;
            }
            else if(n>=100&&n<1000)
            {
                int val=(int) Math.pow(10, i);
                int vs=n/val;
                n=n%val;
                //toWord(vs,hund[i]);
                numun=vs;
                chchch=hund[i];
                if(numun> 19)
                {
                    WORD=WORD+ten[numun/10]+" "+one[numun%10];
                }
                else
                {
                    WORD=WORD+one[numun];
                }
                if(vs>0)
                    WORD=WORD+chchch;
                i=i-1;
            }
            else if(n>=1000&&n<1000000)
            {
                int c=i%3;
                if(c==0)
                {
                    int val=(int) Math.pow(10, i);
                    int vs=n/val;
                    n=n%val;
                    //toWord(vs,hund[i]);
                    numun=vs;
                    chchch=hund[i];
                    if(numun> 19)
                    {
                        WORD=WORD+ten[numun/10]+" "+one[numun%10];
                    }
                    else
                    {
                        WORD=WORD+one[numun];
                    }
                    if(vs>0)
                        WORD=WORD+chchch;
                }
                else if(c==1)
                {
                    i=i-c;
                    int val=(int) Math.pow(10, i);
                    int vs=n/val;
                    n=n%val;
                    //toWord(vs,hund[i]);
                    numun=vs;
                    chchch=hund[i];
                    if(numun> 19)
                    {
                        WORD=WORD+ten[numun/10]+" "+one[numun%10];
                    }
                    else
                    {
                        WORD=WORD+one[numun];
                    }
                    if(vs>0)
                        WORD=WORD+chchch;
                }
                else if(c==2)
                {
                    i=i-c;
                    int val=(int) Math.pow(10, i);
                    int vs=n/val;
                    n=n%val;
                    String hund2[]={" "," "," Hundered "};
                    for(int j=c;j>=0;j--)
                    {
                        if(vs>0&&vs<100)
                        {
                            //toWord((vs%100)," ");
                            numun=vs%100;
                            chchch=" ";
                            if(numun> 19)
                            {
                                WORD=WORD+ten[numun/10]+" "+one[numun%10];
                            }
                            else
                            {
                                WORD=WORD+one[numun];
                            }
                            WORD=WORD+chchch;
                            break;
                        }
                        else if(vs>=100&&vs<1000)
                        {
                            int val2=(int) Math.pow(10, j);
                            int vs2=vs/val2;
                            vs=vs%val2;
                            //toWord(vs2,hund2[j]);
                            numun=vs2;
                            chchch=hund2[j];
                            if(numun> 19)
                            {
                                WORD=WORD+ten[numun/10]+" "+one[numun%10];
                            }
                            else
                            {
                                WORD=WORD+one[numun];
                            }
                            if(vs2>0)
                                WORD=WORD+chchch;
                        }
                    }
                    chchch=hund[i];
                    WORD=WORD+chchch;
                }
                i=i-1;
            }
            else if(n>=1000000000)
            {
                int c=i%3;
                int in=ncount-3-c-2;
                if(c==0)
                {
                    int val=(int) Math.pow(10, i);
                    int vs=n/val;
                    n=n%val;
                    //toWord(vs,hund[i]);
                    numun=vs;
                    chchch=hund[in];
                    if(numun> 19)
                    {
                        WORD=WORD+ten[numun/10]+" "+one[numun%10];
                    }
                    else
                    {
                        WORD=WORD+one[numun];
                    }
                    if(vs>0)
                        WORD=WORD+chchch;
                    i=i-1;
                    ncount=ncount-1;
                }
            }
            else if(n>=1000000&&n<1000000000)
            {
                int c=i%3;
                int in=ncount-3-c;
                if(c==0)
                {
                    int val=(int) Math.pow(10, i);
                    int vs=n/val;
                    n=n%val;
                    //toWord(vs,hund[i]);
                    numun=vs;
                    chchch=hund[in];
                    if(numun> 19)
                    {
                        WORD=WORD+ten[numun/10]+" "+one[numun%10];
                    }
                    else
                    {
                        WORD=WORD+one[numun];
                    }
                    //if(vs>0)
                    WORD=WORD+chchch;
                }
                else if(c==1)
                {
                    i=i-c;
                    int val=(int) Math.pow(10, i);
                    int vs=n/val;
                    n=n%val;
                    //toWord(vs,hund[i]);
                    numun=vs;
                    chchch=hund[in];
                    if(numun> 19)
                    {
                        WORD=WORD+ten[numun/10]+" "+one[numun%10];
                    }
                    else
                    {
                        WORD=WORD+one[numun];
                    }
                    //if(vs>0)
                    WORD=WORD+chchch;
                }
                else if(c==2)
                {
                    i=i-c;
                    int val=(int) Math.pow(10, i);
                    int vs=n/val;
                    n=n%val;
                    String hund2[]={" "," "," Hundered "};
                    for(int j=c;j>=0;j--)
                    {
                        if(vs>0&&vs<100)
                        {
                            //toWord((vs%100)," ");
                            numun=vs%100;
                            chchch=" ";
                            if(numun> 19)
                            {
                                WORD=WORD+ten[numun/10]+" "+one[numun%10];
                            }
                            else
                            {
                                WORD=WORD+one[numun];
                            }
                            WORD=WORD+chchch;
                            break;
                        }
                        else if(vs>=100&&vs<1000)
                        {
                            int val2=(int) Math.pow(10, j);
                            int vs2=vs/val2;
                            vs=vs%val2;
                            //toWord(vs2,hund2[j]);
                            numun=vs2;
                            chchch=hund2[j];
                            if(numun> 19)
                            {
                                WORD=WORD+ten[numun/10]+" "+one[numun%10];
                            }
                            else
                            {
                                WORD=WORD+one[numun];
                            }
                            if(vs2>0)
                                WORD=WORD+chchch;
                        }
                    }
                    chchch=hund[in];
                    //if(vs>0)
                    WORD=WORD+chchch;
                }
                i=i-1;
            }
        }
        return WORD;
    }
}
