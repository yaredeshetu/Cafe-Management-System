package org.o7planning.agelegecafe.ui.Manage_Table;

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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.MainActivity;
import org.o7planning.agelegecafe.R;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class Search_Transaction extends Fragment {
    private static View root=null;
    private static DBManager dbManager;
    private static View.OnClickListener txClick2=null;
    private static View.OnClickListener txClick1=null;
    private static LinearLayout liner=null;
    private AlertDialog alert=null;
    private static ArrayList store=new ArrayList();
    private static ArrayList store1=new ArrayList();
    private static Spinner spin=null,spin1=null;
    private static DecimalFormat df = new DecimalFormat("0.00");

    public Search_Transaction()
    {

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.search_transaction, container, false);
        try {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        } catch (Exception ex) {
            Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        liner=root.findViewById(R.id.tab_scroll);
        spin = root.findViewById(R.id.tbsp1);
        spin1 = root.findViewById(R.id.tbsp2);
        txClick2=new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try
                {
                    String itid=v.getTooltipText().toString();
                    LinearLayout totLin= (LinearLayout) v.getParent();
                    LinearLayout mainL= (LinearLayout) totLin.getParent();
                    LinearLayout mainLiner= (LinearLayout) mainL.getParent();
                    LinearLayout mainLiner1= (LinearLayout) mainLiner.getParent();
                    String itid1=mainLiner.getTooltipText().toString();
                    for(int i=0;i<mainL.getChildCount();i++)
                    {
                        LinearLayout subL= (LinearLayout) mainL.getChildAt(i);
                        String iti=subL.getTooltipText().toString();
                        if(iti.equalsIgnoreCase(itid))
                        {
                            subL.setBackgroundResource(R.drawable.back1);
                        }
                        else
                        {
                            subL.setBackgroundResource(0);
                        }
                    }
                    for(int i=0;i<mainLiner1.getChildCount();i++)
                    {
                        LinearLayout subL= (LinearLayout) mainLiner1.getChildAt(i);
                        String iti=subL.getTooltipText().toString();
                        if(iti.equalsIgnoreCase(itid1))
                        {
                            subL.setBackgroundResource(R.drawable.liner_selection);
                        }
                        else
                        {
                            subL.setBackgroundResource(R.drawable.border_round1);
                            LinearLayout dd= (LinearLayout) subL.getChildAt(1);
                            for(int j=0;j<dd.getChildCount();j++)
                            {
                                LinearLayout dd1= (LinearLayout) dd.getChildAt(j);
                                dd1.setBackgroundResource(0);
                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        };
        txClick1=new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try
                {
                    String itid=v.getTooltipText().toString();
                    LinearLayout totLin= (LinearLayout) v.getParent();
                    LinearLayout mainL= (LinearLayout) totLin.getParent();
                    LinearLayout mainLiner= (LinearLayout) mainL.getParent();
                    for(int i=0;i<mainLiner.getChildCount();i++)
                    {
                        LinearLayout subL= (LinearLayout) mainLiner.getChildAt(i);
                        String iti=subL.getTooltipText().toString();
                        if(iti.equalsIgnoreCase(itid))
                        {
                            subL.setBackgroundResource(R.drawable.liner_selection);
                        }
                        else
                        {
                            subL.setBackgroundResource(R.drawable.border_round1);
                        }
                        LinearLayout dd= (LinearLayout) subL.getChildAt(1);
                        for(int j=0;j<dd.getChildCount();j++)
                        {
                            LinearLayout dd1= (LinearLayout) dd.getChildAt(j);
                            dd1.setBackgroundResource(0);
                        }
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        };
        store=new ArrayList();
        try {
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            Statement st2=MainActivity.connection.createStatement();
            String s16 = "";
            ResultSet r1=st2.executeQuery("select date from workingDate");
            int ckk=0;
            while(r1.next())
            {
                ckk=1;
                s16=r1.getString("date");
            }
            if(ckk==0)
            {
                Toast.makeText(root.getContext(),"You Have To Set Working Date First.",Toast.LENGTH_LONG).show();
            }
            else
            {
                LocalDate cur1= LocalDate.parse(s16);
                DayOfWeek yu=cur1.getDayOfWeek();
                ResultSet rs=st.executeQuery("select id,tableN from tableList");
                while(rs.next())
                {
                    String tbb=rs.getString("id");
                    int ck=0;
                    ResultSet r=st1.executeQuery("select * from tableAssign where tableId='"+tbb+"'");
                    while(r.next())
                    {
                        ck=1;
                    }
                    if(ck==0)
                    {
                        store.add(rs.getString("id")+"("+rs.getString("tableN")+")");
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        store1=new ArrayList();
        try {
            int count=1;
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            ResultSet rs=st.executeQuery("select empName,userName from userAccount where duety='Waiter'");
            while(rs.next())
            {
                store1.add(count+"(TbGr-"+count+")");
                count=count+1;
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, store);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, store1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin1.setAdapter(adapter1);
        appendRow1("");
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
            String sqlsq="select * from tableOrder where workDate='"+str+"'";
            if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Waiter"))
                sqlsq="select * from tableOrder where workDate='"+str+"' and waiter='"+MainActivity.Emp_USERNAME+"'";
            ResultSet rs=st.executeQuery(sqlsq);
            //ResultSet rs=st.executeQuery("select * from tableOrder where workDate='"+str+"'");
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
    private void appendRow1(String str) {
        try
        {
            Statement st1=MainActivity.connection.createStatement();
            liner.removeAllViews();
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
            LinearLayout.LayoutParams tosubParms1=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            tosubParms1.setMargins(5, 5, 5, 0);
            LinearLayout.LayoutParams tosubParms=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            tosubParms.setMargins(5, 5, 5, 5);
            tosubParms.weight=0.5F;
            LinearLayout.LayoutParams tosubParms2=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            tosubParms2.setMargins(5, 5, 5, 5);
            tosubParms2.weight=0.5F;
            LinearLayout.LayoutParams mainParms=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            mainParms.setMargins(5, 5, 5, 5);
            LinearLayout.LayoutParams mainParms2=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            mainParms2.setMargins(20, 5, 20, 20);
            LinearLayout.LayoutParams mainParms3=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            mainParms3.setMargins(20, 10, 20, 0);
            LinearLayout.LayoutParams mainParms1=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            mainParms1.setMargins(5, 0, 5, 0);
            Statement st= MainActivity.connection.createStatement();
            ResultSet r=st.executeQuery("select workDate from tableOrder group by workDate order by workDate desc");
            while (r.next())
            {
                LinearLayout MainLiner = new LinearLayout(root.getContext());
                MainLiner.setOrientation(LinearLayout.VERTICAL);
                MainLiner.setBackgroundResource(R.drawable.border_round1);
                String grId = r.getString("workDate");

                LinearLayout totLin11 = new LinearLayout(root.getContext());
                totLin11.setOrientation(LinearLayout.HORIZONTAL);
                totLin11.setWeightSum(1.0F);
                TextView l2 = new TextView(root.getContext());
                l2.setTextColor(Color.BLUE);
                l2.setText("Daily Date-"+grId);
                l2.setTextSize(10);
                l2.setTypeface(null, Typeface.BOLD_ITALIC);
                l2.setTooltipText(""+grId);
                l2.setOnClickListener(txClick1);
                totLin11.addView(l2, tosubParms2);
                l2 = new TextView(root.getContext());
                l2.setTooltipText(""+grId);
                l2.setOnClickListener(txClick1);
                l2.setTextColor(Color.BLUE);
                String vv="Show Detail";
                SpannableString content = new SpannableString(vv);
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                l2.setText(content);
                l2.setTextSize(14);
                l2.setGravity(Gravity.RIGHT);
                l2.setTypeface(null, Typeface.BOLD_ITALIC);
                totLin11.addView(l2, tosubParms2);
                totLin11.setTooltipText(""+grId);
                MainLiner.setTooltipText(""+grId);
                MainLiner.addView(totLin11, mainParms3);
                LinearLayout MainLin=new LinearLayout(root.getContext());
                MainLin.setOrientation(LinearLayout.VERTICAL);
                MainLin.setBackgroundResource(R.drawable.border_round1);
                //MainLin.setWeightSum(1.0F);
                ArrayList b=new ArrayList();
                b=search44(grId);
                for(int i=0;i<b.size();i++)
                {
                    /*
                    MainLin=new LinearLayout(root.getContext());
                    MainLin.setOrientation(LinearLayout.HORIZONTAL);
                    MainLin.setBackgroundResource(R.drawable.border_round1);
                    MainLin.setWeightSum(1);*/

                    ArrayList s= (ArrayList) b.get(i);
                    String itid=s.get(0).toString();
                    String itnm=s.get(1).toString();
                    String itbal=s.get(2).toString();
                    String itpri=s.get(3).toString();
                    String tableN=s.get(4).toString();
                    String empN=s.get(5).toString();
                    String dateTra=s.get(6).toString();
                    LinearLayout totLin1=new LinearLayout(root.getContext());
                    totLin1.setOrientation(LinearLayout.VERTICAL);
                    TextView l1 = new TextView(root.getContext());
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
                    //l1.setOnClickListener(txClick3);
                    l1.setTextColor(Color.parseColor("#403F3F"));
                    l1.setText(empN);
                    l1.setTextSize(12);
                    totLin2.addView(l1,tosubParms1);
                    l1 = new TextView(root.getContext());
                    l1.setTextColor(Color.BLACK);
                    l1.setText(itnm+"-"+tableN);
                    l1.setTextSize(10);
                    l1.setTooltipText(itid);
                    //l1.setOnClickListener(txClick3);
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
                    totLin0.addView(totLin2,mainParms2);
                    totLin0.addView(totLin1,mainParms2);
                    totLin0.addView(totLin3,mainParms2);
                    MainLin.addView(totLin0,mainParms1);
                }
                MainLiner.addView(MainLin, mainParms2);
                liner.addView(MainLiner,mainParms);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
