package org.o7planning.agelegecafe.ui.Manage_Table;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.MainActivity;
import org.o7planning.agelegecafe.R;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Daily_Table_Transaction extends Fragment {
    private static TextView t1=null,t2=null,t3=null,t4=null;
    private static View root=null;
    private static DBManager dbManager;
    private static TableLayout table=null;
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static View.OnClickListener txClick2=null;
    private static View.OnClickListener txClick1=null;
    private static View.OnClickListener txClick3=null;
    public static ArrayList B_Table_Transaction=new ArrayList();
    private static PopupWindow pw=null;
    public static int SelectRR=0,SelectRR1=0;
    public Daily_Table_Transaction() {
        // Required empty public constructor
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.daily_table_transaction, container, false);
        try {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        } catch (Exception ex) {
            Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        SelectRR1=0;
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
        txClick3=new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    TableRow.LayoutParams tx_Parm1 = new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT
                    );
                    tx_Parm1.setMargins(5, 5, 5, 5);
                    tx_Parm1.weight = 0.4F;
                    TableRow.LayoutParams tx_Parm2 = new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT
                    );
                    tx_Parm2.setMargins(5, 5, 5, 5);
                    tx_Parm2.weight = 0.2F;
                    LinearLayout.LayoutParams mainParms1=new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    mainParms1.setMargins(15, 0, 5, 0);
                    String itid = v.getTooltipText().toString();
                    LinearLayout tot3 = (LinearLayout) v.getParent();
                    LinearLayout tot0 = (LinearLayout) tot3.getParent();
                    LinearLayout mainL = (LinearLayout) tot0.getParent();
                    if (mainL.getChildCount() == 2)
                    {
                        LinearLayout totLin1 = new LinearLayout(root.getContext());
                        totLin1.setOrientation(LinearLayout.VERTICAL);
                        Statement st = MainActivity.connection.createStatement();
                        Statement st1 = MainActivity.connection.createStatement();
                        Statement st2 = MainActivity.connection.createStatement();
                        ResultSet rs1 = st.executeQuery("select * from tableOrderDetail where orderId='" + itid + "'");
                        while (rs1.next()) {
                            LinearLayout totLin10 = new LinearLayout(root.getContext());
                            totLin10.setOrientation(LinearLayout.VERTICAL);
                            String vouchId = rs1.getString("voucherId");
                            TextView l1 = new TextView(root.getContext());
                            l1.setTextColor(Color.BLACK);
                            l1.setText(vouchId);
                            l1.setTextSize(12);
                            l1.setTypeface(null, Typeface.BOLD_ITALIC);
                            totLin10.addView(l1);
                            LinearLayout totLin101 = new LinearLayout(root.getContext());
                            totLin101.setOrientation(LinearLayout.HORIZONTAL);
                            totLin101.setWeightSum(1.0F);
                            l1 = new TextView(root.getContext());
                            l1.setTextColor(Color.GREEN);
                            l1.setText("Item Name");
                            l1.setTextSize(11);
                            l1.setTypeface(null, Typeface.BOLD_ITALIC);
                            totLin101.addView(l1, tx_Parm1);
                            l1 = new TextView(root.getContext());
                            l1.setTextColor(Color.GREEN);
                            l1.setText("Qty");
                            l1.setTextSize(11);
                            l1.setTypeface(null, Typeface.BOLD_ITALIC);
                            totLin101.addView(l1, tx_Parm2);
                            l1 = new TextView(root.getContext());
                            l1.setTextColor(Color.GREEN);
                            l1.setText("U Price");
                            l1.setTextSize(11);
                            l1.setTypeface(null, Typeface.BOLD_ITALIC);
                            totLin101.addView(l1, tx_Parm2);
                            l1 = new TextView(root.getContext());
                            l1.setTextColor(Color.GREEN);
                            l1.setText("Tot Pri");
                            l1.setTextSize(11);
                            l1.setTypeface(null, Typeface.BOLD_ITALIC);
                            totLin101.addView(l1, tx_Parm2);
                            totLin10.addView(totLin101);
                            int couc = 0;
                            ResultSet rs2 = st1.executeQuery("select * from lineItem1 where voucherId='" + vouchId + "' order by itemId");
                            while (rs2.next()) {
                                totLin101 = new LinearLayout(root.getContext());
                                totLin101.setOrientation(LinearLayout.HORIZONTAL);
                                totLin101.setWeightSum(1.0F);
                                couc = couc + 1;
                                String itName = rs2.getString("itemName");
                                double qty = rs2.getDouble("qtyOut");
                                double uPri = rs2.getDouble("u_price");
                                double tPri = rs2.getDouble("tot_price");
                                l1 = new TextView(root.getContext());
                                l1.setTextColor(Color.BLACK);
                                l1.setText(itName);
                                l1.setTextSize(12);
                                l1.setTypeface(null, Typeface.NORMAL);
                                totLin101.addView(l1, tx_Parm1);
                                l1 = new TextView(root.getContext());
                                l1.setTextColor(Color.BLACK);
                                l1.setText(df.format(qty));
                                l1.setTextSize(12);
                                l1.setTypeface(null, Typeface.NORMAL);
                                totLin101.addView(l1, tx_Parm2);
                                l1 = new TextView(root.getContext());
                                l1.setTextColor(Color.BLACK);
                                l1.setText(df.format(uPri));
                                l1.setTextSize(12);
                                l1.setTypeface(null, Typeface.NORMAL);
                                totLin101.addView(l1, tx_Parm2);
                                l1 = new TextView(root.getContext());
                                l1.setTextColor(Color.BLACK);
                                l1.setText(df.format(tPri));
                                l1.setTextSize(12);
                                l1.setTypeface(null, Typeface.NORMAL);
                                totLin101.addView(l1, tx_Parm2);
                                totLin10.addView(totLin101);
                            }
                            totLin1.addView(totLin10);
                        }
                        mainL.addView(totLin1,mainParms1);
                    }
                    else if(mainL.getChildCount()==3)
                    {
                        mainL.removeViewAt(2);
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
        View pop = inflater1.inflate(R.layout.waiter_list, null, false);
        int wid=pop.getMeasuredWidth();
        int hig=pop.getHeight();
        LinearLayout linearLayout=pop.findViewById(R.id.daily_li1);
        TextView textView1=pop.findViewById(R.id.wai_or_b1);
        TextView textView2=pop.findViewById(R.id.wai_or_b2);
        TextView textView3=pop.findViewById(R.id.wai_tx1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.selectedWaiter=new ArrayList();
                for (int i=0;i<linearLayout.getChildCount();i++)
                {
                    CheckBox chch= (CheckBox) linearLayout.getChildAt(i);
                    if(chch.isChecked())
                    {
                        String s81=chch.getText().toString();
                        int indx=0;
                        for(int ii=0;ii<s81.length();ii++)
                        {
                            if(s81.charAt(ii)=='(')
                            {
                                indx=ii;
                                break;
                            }
                        }
                        String s8=s81.substring(0,indx);
                        MainActivity.selectedWaiter.add(s8);
                    }
                }
                SelectRR1=1;
                appendRow1(table,"");
                pw.dismiss();
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw.dismiss();
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw.dismiss();
            }
        });
        try
        {
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            Statement st2 = MainActivity.connection.createStatement();
            TableRow.LayoutParams tx_Parm4=new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            tx_Parm4.setMargins(5,5,5,5);
            String sqlsq="select empName,userName from userAccount where duety='Waiter'";
            if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Waiter"))
                sqlsq="select empName,userName from userAccount where duety='Waiter' and userName='"+MainActivity.Emp_USERNAME+"'";
            ResultSet rs=st.executeQuery(sqlsq);
            while(rs.next())
            {
                String userN=rs.getString("userName");
                String str=rs.getString("userName")+"("+rs.getString("empName")+")";
                CheckBox checkBox=new CheckBox(pop.getContext());
                int ckck=0;
                for(int i=0;i<MainActivity.selectedWaiter.size();i++)
                {
                    if(userN.equalsIgnoreCase(MainActivity.selectedWaiter.get(i).toString()))
                    {
                        ckck=1;
                    }
                }
                if(ckck==1)
                    checkBox.setChecked(true);
                else if(ckck==0)
                    checkBox.setChecked(false);
                checkBox.setGravity(Gravity.LEFT);
                checkBox.setText(str);
                linearLayout.addView(checkBox,tx_Parm4);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        t4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try
                {
                    //ImageView atnot = (ImageView)pop.findViewById(R.id.aNot);

                    pw = new PopupWindow(pop, root.getWidth(), root.getHeight(), true);
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
    private static ArrayList search44(String str)
    {
        ArrayList Big=new ArrayList();
        try
        {
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            Statement st2 = MainActivity.connection.createStatement();
            Big = new ArrayList();
            String sqlsq="select * from tableOrder";
            if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Waiter"))
                sqlsq="select * from tableOrder where waiter='"+MainActivity.Emp_USERNAME+"'";
            ResultSet rs=st.executeQuery(sqlsq);

            while (rs.next())
            {
                String ordId=rs.getString("id");
                String tabId=rs.getString("tableId");
                String dateTra=rs.getString("workDate");
                String wait=rs.getString("waiter");
                int ckc=0;
                for (int i=0;i<MainActivity.selectedWaiter.size();i++)
                {
                    if(wait.equalsIgnoreCase(MainActivity.selectedWaiter.get(i).toString()))
                    {
                        ckc=1;
                    }
                }
                if(ckc==1)
                {
                    String empN = "";
                    ResultSet cc = st1.executeQuery("select empName from userAccount where userName='" + wait + "'");
                    while (cc.next()) {
                        empN = cc.getString("empName");
                    }
                    int noOford = rs.getInt("noOrder");
                    String tableN = "";
                    ResultSet rs2 = st2.executeQuery("select * from tableList where id='" + tabId + "'");
                    while (rs2.next())
                    {
                        tableN = rs2.getString("tableN");
                    }
                    double bal = 0.0;
                    ResultSet rs1 = st1.executeQuery("select SUM(balanace) AS bal from tableOrderDetail where orderId='" + ordId + "'");
                    while (rs1.next())
                    {
                        bal = rs1.getDouble("bal");
                    }
                    ArrayList s = new ArrayList();
                    s.add(ordId);
                    s.add(tabId);
                    s.add(bal);
                    s.add(noOford);
                    s.add(tableN);
                    s.add(empN);
                    s.add(dateTra);
                    Big.add(s);
                }
            }
        }
        catch(Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return Big;
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
            TableRow.LayoutParams tx_Parm3=new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            tx_Parm3.setMargins(5,5,5,5);
            tx_Parm3.weight= 0.2F;
            TableRow.LayoutParams tx_Parm4=new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            tx_Parm4.setMargins(5,5,5,5);
            tx_Parm4.weight= 0.8F;
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
            if(SelectRR1==0)
                b= B_Table_Transaction;
            if(SelectRR1==1)
            {
                b=search44("");
                SelectRR1=0;
            }
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
                String tableN=s.get(4).toString();
                String empN=s.get(5).toString();
                String dateTra=s.get(6).toString();
                LinearLayout totLin=new LinearLayout(root.getContext());
                totLin.setOrientation(LinearLayout.HORIZONTAL);
                totLin.setWeightSum(1.0F);

                LinearLayout totLinn=new LinearLayout(root.getContext());
                totLinn.setOrientation(LinearLayout.HORIZONTAL);
                totLinn.setWeightSum(1.0F);
                TextView l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText(itnm+"-"+tableN);
                l1.setTextSize(14);
                l1.setTypeface(null, Typeface.BOLD_ITALIC);
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                totLinn.addView(l1,tx_Parm3);
                l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText(dateTra);
                l1.setTextSize(14);
                l1.setGravity(Gravity.RIGHT);
                l1.setTypeface(null, Typeface.BOLD_ITALIC);
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                totLinn.addView(l1,tx_Parm4);
                totLinn.setTooltipText(itid);
                totLinn.setOnClickListener(txClick2);

                totLin.addView(totLinn,tx_Parm1);

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
                l1.setText("No of Order");
                l1.setTypeface(null, Typeface.ITALIC);
                l1.setTextSize(11);
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
                l1.setText(empN);
                l1.setTextSize(12);
                totLin2.addView(l1,tosubParms1);
                l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLUE);
                String vv="Show Detail";
                SpannableString content = new SpannableString(vv);
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                l1.setText(content);
                l1.setTextSize(12);
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick3);
                totLin2.addView(l1,tosubParms1);
                LinearLayout totLin3=new LinearLayout(root.getContext());
                totLin3.setOrientation(LinearLayout.VERTICAL);
                l1 = new TextView(root.getContext());
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                l1.setTextColor(Color.parseColor("#403F3F"));
                l1.setText("Table Balance");
                l1.setTextSize(11);
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
                totLin0.addView(totLin3,mainParms2);
                totLin0.addView(totLin2,mainParms2);
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
