package org.o7planning.agelegecafe.ui.Table;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class Assign_TbGr extends Fragment {
    private static View root=null;
    private static DBManager dbManager;
    private static View.OnClickListener txClick2=null;
    private static View.OnClickListener txClick1=null;
    private static TextView b1=null,b2=null;
    private static LinearLayout liner=null;
    private AlertDialog alert=null;
    private static ArrayList store=new ArrayList();
    private static ArrayList store1=new ArrayList();
    private static Spinner spin=null,spin1=null;
    private static TextView tx1=null,tx2=null,tx3=null;
    public Assign_TbGr()
    {

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.assign_tb_gr, container, false);
        try {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        } catch (Exception ex) {
            Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        liner=root.findViewById(R.id.tab_scroll);
        b1=root.findViewById(R.id.tab_b1);
        b2=root.findViewById(R.id.tab_b2);
        spin = root.findViewById(R.id.tbsp1);
        spin1 = root.findViewById(R.id.tbsp2);
        tx1=root.findViewById(R.id.tbtx1);
        tx2=root.findViewById(R.id.tbtx2);
        tx3=root.findViewById(R.id.tbtx3);
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
                    tx1.setText(itid);
                    tx2.setText(itid1);
                    b2.setText("Remove");
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
                    tx1.setText(itid);
                    tx2.setText("");
                    b2.setText("Reset");
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
                tx3.setText("No Working Date Found");
                Toast.makeText(root.getContext(),"You Have To Set Working Date First.",Toast.LENGTH_LONG).show();
            }
            else
            {
                LocalDate cur1= LocalDate.parse(s16);
                DayOfWeek yu=cur1.getDayOfWeek();
                tx3.setText(s16+" ("+yu+")");
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
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Statement st1=MainActivity.connection.createStatement();
                    AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                    builder.setTitle(R.string.app_name);
                    builder.setIcon(R.mipmap.glance_logo);
                    builder.setMessage("Are You Sure To Save ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                            try
                            {
                                String s81=spin.getSelectedItem().toString();
                                String s91=spin1.getSelectedItem().toString();
                                String s8="";
                                String s9="";
                                int indx=0;
                                int indx3=0;
                                for(int i=0;i<s81.length();i++)
                                {
                                    if(s81.charAt(i)=='(')
                                    {
                                        indx=i;
                                    }
                                    if(s81.charAt(i)==')')
                                    {
                                        indx3=i;
                                        break;
                                    }
                                }
                                s8=s81.substring(0,indx);
                                String tbN=s81.substring((indx+1),indx3);
                                int indx1=0;
                                int indx2=0;
                                for(int i=0;i<s91.length();i++)
                                {
                                    if(s91.charAt(i)=='(')
                                    {
                                        indx1=i;
                                    }
                                    if(s91.charAt(i)==')')
                                    {
                                        indx2=i;
                                        break;
                                    }
                                }
                                s9=s91.substring(0,indx1);
                                int grI=Integer.parseInt(s9);
                                String grN=s91.substring((indx1+1),indx2);
                                Statement st=MainActivity.connection.createStatement();
                                Statement st1=MainActivity.connection.createStatement();
                                String empN="",userN="";
                                ResultSet r1=st1.executeQuery("select * from tableAssign where tableId='"+s8+"'");
                                while(r1.next())
                                {
                                    userN=r1.getString("tableId");
                                }
                                if(userN.equalsIgnoreCase(""))
                                {
                                    st.execute("delete from tableAssign where tableId="+s8);
                                    st.execute("insert into tableAssign(grId, grName, tableId, tableName) values("+ grI +",'"+grN+"','"+s8+"','"+tbN+"')");
                                    st.execute("delete from tableGroup where id="+grI);
                                    st.execute("insert into tableGroup(id, TableGr, assign) values("+grI+",'"+grN+"','false')");
                                    Toast.makeText(root.getContext(),"Success",Toast.LENGTH_LONG).show();
                                    store=new ArrayList();
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
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, store);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spin.setAdapter(adapter);
                                    appendRow1("");
                                }
                                else
                                {
                                    Toast.makeText(root.getContext(),"You Are Already Assign Table-"+tbN+" to Table Group-"+grN,Toast.LENGTH_LONG).show();
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
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    String s16 = "";
                    Statement st1=MainActivity.connection.createStatement();
                    ResultSet r1=st1.executeQuery("select date from workingDate");
                    int ck=0;
                    while(r1.next())
                    {
                        ck=1;
                        s16=r1.getString("date");
                    }
                    if(ck==0)
                    {
                        Toast.makeText(root.getContext(),"You Have To Set Working Date First.",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //
                        Statement st = MainActivity.connection.createStatement();
                        Statement st2 = MainActivity.connection.createStatement();
                        String str1=tx1.getText().toString();
                        String str2=tx2.getText().toString();
                        AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                        builder.setTitle(R.string.app_name);
                        builder.setIcon(R.mipmap.glance_logo);
                        if(str1.equals("")&&str2.equals(""))
                            builder.setMessage("Are You Sure To Reset All Table Assign ?");
                        else if(!str1.equals("")&&str2.equals(""))
                            builder.setMessage("Are You Sure To Reset Table Group-"+str1+" Assign ?");
                        else if(!str1.equals("")&&!str2.equals(""))
                            builder.setMessage("Are You Sure To Delete Table-"+str2+" To Table Group-"+str1+" ?");
                        String finalS1 = s16;
                        String finalS11 = s16;
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                dialog.dismiss();
                                try
                                {
                                    Statement st=MainActivity.connection.createStatement();
                                    if(str1.equals("")&&str2.equals(""))
                                    {
                                        st.execute("delete from tableAssign");
                                        st.execute("delete from tableGroup");
                                        st.execute("delete from assignGroup where date='"+MainActivity.Main_WORK_DATE+"'");
                                    }
                                    else if(!str1.equals("")&&str2.equals(""))
                                    {
                                        int grI=Integer.parseInt(str1);
                                        st.execute("delete from tableAssign where grId="+grI);
                                        st.execute("delete from tableGroup where id="+grI);
                                        st.execute("delete from assignGroup where date='"+MainActivity.Main_WORK_DATE+"' and grId="+grI);
                                    }
                                    else if(!str1.equals("")&&!str2.equals(""))
                                    {
                                        int grI=Integer.parseInt(str2);
                                        st.execute("delete from tableAssign where grId="+grI+" and tableId='"+str1+"'");
                                        int ck=0;
                                        ResultSet r=st1.executeQuery("select * from tableAssign where grId="+grI);
                                        while(r.next())
                                        {
                                            ck=1;
                                        }
                                        if(ck==0)
                                        {
                                            st.execute("delete from tableGroup where id="+grI);
                                            st.execute("delete from assignGroup where date='"+MainActivity.Main_WORK_DATE+"' and grId="+grI);
                                        }
                                    }
                                    Toast.makeText(root.getContext(),"Success",Toast.LENGTH_LONG).show();
                                    store=new ArrayList();
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
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, store);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spin.setAdapter(adapter);
                                    appendRow1("");
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
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        appendRow1("");
        return root;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void appendRow1(String str) {
        try
        {
            Statement st1=MainActivity.connection.createStatement();
            liner.removeAllViews();
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
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            mainParms2.setMargins(20, 5, 20, 20);
            LinearLayout.LayoutParams mainParms3=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            mainParms3.setMargins(20, 10, 20, 0);
            Statement st= MainActivity.connection.createStatement();
            ResultSet r=st.executeQuery("select * from tableGroup order by id");
            while (r.next())
            {
                LinearLayout MainLiner = new LinearLayout(root.getContext());
                MainLiner.setOrientation(LinearLayout.VERTICAL);
                MainLiner.setBackgroundResource(R.drawable.border_round1);
                int grId = r.getInt("id");
                String grNm=r.getString("TableGr");

                LinearLayout totLin1 = new LinearLayout(root.getContext());
                totLin1.setOrientation(LinearLayout.HORIZONTAL);
                totLin1.setWeightSum(1.0F);
                TextView l2 = new TextView(root.getContext());
                l2.setTextColor(Color.BLUE);
                l2.setText("Table Group-"+grId);
                l2.setTextSize(14);
                l2.setTypeface(null, Typeface.BOLD_ITALIC);
                l2.setTooltipText(""+grId);
                l2.setOnClickListener(txClick1);
                totLin1.addView(l2, tosubParms2);
                l2 = new TextView(root.getContext());
                l2.setTooltipText(""+grId);
                l2.setOnClickListener(txClick1);
                l2.setTextColor(Color.BLUE);
                l2.setText(grNm);
                l2.setTextSize(14);
                l2.setTypeface(null, Typeface.BOLD_ITALIC);
                totLin1.addView(l2, tosubParms2);
                totLin1.setTooltipText(""+grId);
                MainLiner.setTooltipText(""+grId);
                MainLiner.addView(totLin1, mainParms3);
                LinearLayout MainLin = new LinearLayout(root.getContext());
                MainLin.setOrientation(LinearLayout.VERTICAL);
                MainLin.setBackgroundResource(R.drawable.border_round1);
                ResultSet r1 = st1.executeQuery("select * from tableAssign where grId=" + grId+" order by tableId");
                while (r1.next())
                {
                    String t_id = r1.getString("tableId");
                    String empN1 = r1.getString("tableName");

                    LinearLayout totLin = new LinearLayout(root.getContext());
                    totLin.setOrientation(LinearLayout.HORIZONTAL);
                    totLin.setWeightSum(1.0F);

                    TextView l1 = new TextView(root.getContext());
                    l1.setTextColor(Color.BLACK);
                    l1.setText(t_id);
                    l1.setTextSize(14);
                    l1.setTypeface(null, Typeface.BOLD_ITALIC);
                    l1.setTooltipText(t_id);
                    l1.setOnClickListener(txClick2);
                    totLin.addView(l1, tosubParms);
                    l1 = new TextView(root.getContext());
                    l1.setTooltipText(t_id);
                    l1.setOnClickListener(txClick2);
                    l1.setTextColor(Color.BLACK);
                    l1.setText(empN1);
                    l1.setTextSize(14);
                    l1.setTypeface(null, Typeface.BOLD_ITALIC);
                    totLin.addView(l1, tosubParms);
                    totLin.setTooltipText(t_id);
                    MainLin.addView(totLin, mainParms);
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
