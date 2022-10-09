package org.o7planning.agelegecafe.ui.home;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.MainActivity;
import org.o7planning.agelegecafe.R;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class Daily_Transaction extends Fragment {
    private static TextView t1=null,t2=null,t3=null,t4=null;
    private static View root=null;
    private static DBManager dbManager;
    private static TableLayout table=null;
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static View.OnClickListener txClick2=null;
    private static View.OnClickListener txClick1=null;
    public static int Addwhol=0;
    public Daily_Transaction() {
        // Required empty public constructor
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.daily_transaction, container, false);
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
        table=root.findViewById(R.id.daily_menu);
        t4=root.findViewById(R.id.daily_tx4);
        LayoutInflater inflater1 = (LayoutInflater)root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View pop = inflater1.inflate(R.layout.vouch_ty_popup, null, false);
        int wid=pop.getMeasuredWidth();
        int hig=pop.getHeight();
        t4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
            try
            {
                //ImageView atnot = (ImageView)pop.findViewById(R.id.aNot);

                PopupWindow pw = new PopupWindow(pop, root.getWidth(), root.getHeight(), true);
                pw.showAsDropDown(root, 10, 5);
                //appendRow1(table,e1.getText().toString());
                //LayoutInflater inflater = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.vouch_ty_popup, null, false),wid,hig, true);
                //pw.showAtLocation(root, Gravity.BOTTOM, 0, 0);
            }
            catch (Exception ex)
            {
                Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            }
        });
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
            b=HomeFragment.B_Transac;
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
                String opp=s.get(5).toString();
                LinearLayout totLin=new LinearLayout(root.getContext());
                totLin.setOrientation(LinearLayout.HORIZONTAL);
                totLin.setWeightSum(1.0F);
                TextView l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText(itnm+"-"+itid);
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
                l1.setText("Total");
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
                l1.setText(opp);
                l1.setTextSize(12);
                totLin2.addView(l1,tosubParms1);
                l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText(itPpri);
                l1.setTextSize(12);
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                totLin2.addView(l1,tosubParms1);
                LinearLayout totLin3=new LinearLayout(root.getContext());
                totLin3.setOrientation(LinearLayout.VERTICAL);
                l1 = new TextView(root.getContext());
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                l1.setTextColor(Color.parseColor("#403F3F"));
                l1.setText("Time");
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
