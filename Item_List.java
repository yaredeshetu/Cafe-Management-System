package org.o7planning.agelegecafe.ui.home;

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
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.o7planning.agelegecafe.Add_Item;
import org.o7planning.agelegecafe.Add_New_Item;
import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.MainActivity;
import org.o7planning.agelegecafe.R;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Item_List extends Fragment {
    public static EditText e1=null,e2=null,e3=null,e4=null;
    private static LinearLayout ma_l=null,l0=null,l01=null,l1=null,l2=null;
    private static TextView t2=null,t3=null,t4=null,t5=null,t6=null,t7=null,t8=null;
    private static View root=null;
    private static Spinner sp1=null;
    private static DBManager dbManager;
    private static TableLayout table=null;
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static View.OnClickListener txClick2=null;
    private static View.OnClickListener txClick1=null;
    public static int Addwhol=0;
    public Item_List() {
        // Required empty public constructor
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.item_list, container, false);
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
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        };
        e1=new EditText(root.getContext());
        e1.setHint("Search Items");
        ma_l=root.findViewById(R.id.daily_ml1);
        l0=root.findViewById(R.id.daily_li0);
        l01=root.findViewById(R.id.daily_li01);
        l1=root.findViewById(R.id.daily_li1);
        l2=root.findViewById(R.id.daily_li2);
        sp1=root.findViewById(R.id.daily_tx1);
        t5=root.findViewById(R.id.daily_tx5);
        t2=root.findViewById(R.id.daily_tx2);
        t3=root.findViewById(R.id.daily_tx3);
        t4=root.findViewById(R.id.daily_tx4);
        t6=root.findViewById(R.id.daily_tx6);
        t7=root.findViewById(R.id.daily_tx7);
        table=root.findViewById(R.id.daily_menu);
        LinearLayout.LayoutParams tosubParms=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        tosubParms.setMargins(5, 0, 0, 0);
        tosubParms.weight= 0.9F;
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Intent notificationIntent = new Intent(root.getContext(), Add_New_Item.class);
                    startActivity(notificationIntent);
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Intent notificationIntent = new Intent(root.getContext(), Add_New_Item.class);
                    startActivity(notificationIntent);
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    //ma_l.addView(l01);
                    t4.setBackgroundResource(R.drawable.close_24);
                    e1.setText("");
                    l0.removeView(l1);
                    l0.removeView(l2);
                    l0.addView(e1,tosubParms);
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    //ma_l.addView(l01);
                    t4.setBackgroundResource(R.drawable.close_24);
                    e1.setText("");
                    l0.removeView(l1);
                    l0.removeView(l2);
                    l0.addView(e1,tosubParms);
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    //ma_l.removeView(l01);
                    t4.setBackground(null);
                    l0.addView(l1);
                    l0.addView(l2);
                    l0.removeView(e1);
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        //ma_l.removeView(l01);
        t4.setBackground(null);
        appendRow1(table,"");
        return root;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void appendRow1(TableLayout table, String str) {
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
            ArrayList b=new ArrayList();
            b=HomeFragment.B_ITEM;
            int ss=b.size();
            int ss2=ss/2;
            for(int i=0;i<b.size();i++)
            {
                row = new TableRow(root.getContext());
                row.setWeightSum(1.0F);
                MainLin=new LinearLayout(root.getContext());
                MainLin.setOrientation(LinearLayout.VERTICAL);
                MainLin.setBackgroundResource(R.drawable.border_round1);
                MainLin.setWeightSum(1);
                ArrayList s= (ArrayList) b.get(i);
                String itid=s.get(0).toString();
                String itnm=s.get(1).toString();
                String itbal=s.get(2).toString();
                String itpri=s.get(3).toString();
                String itPpri=s.get(4).toString();
                LinearLayout totLin=new LinearLayout(root.getContext());
                totLin.setOrientation(LinearLayout.HORIZONTAL);
                totLin.setWeightSum(1.0F);
                TextView l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText(itid+"-"+itnm);
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
                l1.setText("Sales Price");
                l1.setTypeface(null, Typeface.ITALIC);
                l1.setTextSize(12);
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                totLin1.addView(l1,tosubParms1);
                l1 = new TextView(root.getContext());
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                l1.setTextColor(Color.BLACK);
                l1.setText(itpri);
                l1.setTextSize(14);
                totLin1.addView(l1,tosubParms);
                LinearLayout totLin2=new LinearLayout(root.getContext());
                totLin2.setOrientation(LinearLayout.VERTICAL);
                l1 = new TextView(root.getContext());
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                l1.setTextColor(Color.parseColor("#403F3F"));
                l1.setText("Purchase Price");
                l1.setTextSize(12);
                totLin2.addView(l1,tosubParms1);
                l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText(itPpri);
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
                l1.setText("In Stock");
                l1.setTextSize(12);
                totLin3.addView(l1,tosubParms);
                l1 = new TextView(root.getContext());
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                l1.setTextColor(Color.BLACK);
                l1.setText(itbal);
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
}
