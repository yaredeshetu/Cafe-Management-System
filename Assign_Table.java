package org.o7planning.agelegecafe.ui.Table;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import java.util.Date;

public class Assign_Table  extends Fragment {
    private static View root=null;
    private static DBManager dbManager;
    private static View.OnClickListener txClick2=null;
    private static TextView b1=null,b2=null,b3=null;
    private static LinearLayout liner=null;
    private AlertDialog alert=null;
    private static ArrayList store=new ArrayList();
    private static ArrayList store1=new ArrayList();
    private static Spinner spin=null,spin1=null;
    private static TextView tx1=null,tx2=null,tx3=null;
    private static String tabID="";
    public Assign_Table()
    {

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.assign_table, container, false);
        try {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        } catch (Exception ex) {
            Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        liner=root.findViewById(R.id.tab_scroll);
        b1=root.findViewById(R.id.tab_b1);
        b2=root.findViewById(R.id.tab_b2);
        b3=root.findViewById(R.id.tab_b3);
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
                    tabID=itid;
                    for(int i=0;i<liner.getChildCount();i++)
                    {
                        LinearLayout Mlin= (LinearLayout) liner.getChildAt(i);
                        String toT=Mlin.getTooltipText().toString();
                        if(itid.equalsIgnoreCase(toT))
                        {
                            LinearLayout Tlin= (LinearLayout) Mlin.getChildAt(0);
                            TextView Tv1= (TextView) Tlin.getChildAt(0);
                            TextView Tv2= (TextView) Tlin.getChildAt(1);
                            tx1.setText(Tv1.getText().toString());
                            tx2.setText(Tv2.getText().toString());
                            tx1.setTypeface(null, Typeface.BOLD_ITALIC);
                            tx2.setTypeface(null, Typeface.BOLD_ITALIC);
                            Mlin.setBackgroundResource(R.drawable.border_round_select);
                        }
                        else
                        {
                            Mlin.setBackgroundResource(R.drawable.border_round1);
                        }
                    }
                    try {
                        MainActivity.connection = MainActivity.Conn();
                    } catch (Exception ex) {
                        Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        };
        tabID="";
        store=new ArrayList();
        store1=new ArrayList();
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
                //tableGroup(id, TableGr, assign)
                ResultSet rs=st.executeQuery("select id, TableGr from tableGroup");
                while(rs.next())
                {
                    int  tbb=rs.getInt("id");
                    int ck=0;
                    ResultSet r=st1.executeQuery("select * from assignGroup where grId="+tbb+" and date='"+s16+"'");
                    while(r.next())
                    {
                        ck=1;
                    }
                    if(ck==0)
                    {
                        store.add(rs.getString("id")+"("+rs.getString("TableGr")+")");
                    }
                }
                rs=st.executeQuery("select empName,userName from userAccount where duety='Waiter' order by count");
                while(rs.next())
                {
                    String tbb=rs.getString("userName");
                    int ck=0;
                    ResultSet r=st1.executeQuery("select * from assignGroup where userName='"+tbb+"' and date='"+s16+"'");
                    while(r.next())
                    {
                        ck=1;
                    }
                    if(ck==0)
                    {
                        store1.add(rs.getString("userName")+"("+rs.getString("empName")+")");
                    }
                }
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                        builder.setTitle(R.string.app_name);
                        builder.setIcon(R.mipmap.glance_logo);
                        builder.setMessage("Are You Sure To Save On This Date-"+s16+"?");
                        String finalS1 = s16;
                        String finalS11 = s16;
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
                                    for(int i=0;i<s81.length();i++)
                                    {
                                        if(s81.charAt(i)=='(')
                                        {
                                            indx=i;
                                            break;
                                        }
                                    }
                                    s8=s81.substring(0,indx);
                                    int indx1=0;
                                    for(int i=0;i<s91.length();i++)
                                    {
                                        if(s91.charAt(i)=='(')
                                        {
                                            indx1=i;
                                            break;
                                        }
                                    }
                                    s9=s91.substring(0,indx1);
                                    int groId=Integer.parseInt(s8);
                                    String userN=s9;
                                    Statement st=MainActivity.connection.createStatement();
                                    Statement st1=MainActivity.connection.createStatement();
                                    String GrName="";
                                    ResultSet cr5=st.executeQuery("select * from tableGroup where id="+groId);
                                    while (cr5.next())
                                    {
                                        GrName=cr5.getString("TableGr");
                                    }
                                    st.execute("delete from assignGroup where date='"+MainActivity.Main_WORK_DATE+"' and userName='"+userN+"' and grId="+groId);
                                    st.execute("insert into assignGroup(date, userName, grId, grName) " +
                                            "values('"+MainActivity.Main_WORK_DATE+"','"+userN+"',"+groId+",'"+GrName+"')");
                                    Toast.makeText(root.getContext(),"Success",Toast.LENGTH_LONG).show();
                                    store=new ArrayList();
                                    store1=new ArrayList();
                                    ResultSet rs=st.executeQuery("select id, TableGr from tableGroup order by id");
                                    while(rs.next())
                                    {
                                        int tbb=rs.getInt("id");
                                        int ck=0;
                                        ResultSet r=st1.executeQuery("select * from assignGroup where grId="+tbb+" and date='"+finalS11+"'");
                                        while(r.next())
                                        {
                                            ck=1;
                                        }
                                        if(ck==0)
                                        {
                                            store.add(rs.getString("id")+"("+rs.getString("TableGr")+")");
                                        }
                                    }
                                    rs=st.executeQuery("select empName,userName from userAccount where duety='Waiter' order by count");
                                    while(rs.next())
                                    {
                                        String tbb=rs.getString("userName");
                                        int ck=0;
                                        ResultSet r=st1.executeQuery("select * from assignGroup where userName='"+tbb+"' and date='"+finalS11+"'");
                                        while(r.next())
                                        {
                                            ck=1;
                                        }
                                        if(ck==0)
                                        {
                                            store1.add(rs.getString("userName")+"("+rs.getString("empName")+")");
                                        }
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, store);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spin.setAdapter(adapter);
                                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, store1);
                                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spin1.setAdapter(adapter1);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                        builder.setTitle(R.string.app_name);
                        builder.setIcon(R.mipmap.glance_logo);
                        if(tabID.equals(""))
                            builder.setMessage("Are You Sure To Delete This Assign on this Date-"+s16+" ?");
                        else
                            builder.setMessage("Are You Sure To Delete Group-"+tabID+" Assign on this Date-"+s16+" ?");
                        String finalS1 = s16;
                        String finalS11 = s16;
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                dialog.dismiss();
                                try
                                {
                                    Statement st=MainActivity.connection.createStatement();
                                    if(tabID.equals(""))
                                        st.execute("delete from assignGroup where date='"+finalS1+"'");
                                    else
                                    {
                                        int groId=Integer.parseInt(tabID);
                                        st.execute("delete from assignGroup where date='" + finalS1 + "' and grId=" + groId);
                                    }
                                    Toast.makeText(root.getContext(),"Success",Toast.LENGTH_LONG).show();
                                    store=new ArrayList();
                                    store1=new ArrayList();
                                    ResultSet rs=st.executeQuery("select id, TableGr from tableGroup order by id");
                                    while(rs.next())
                                    {
                                        String tbb=rs.getString("id");
                                        int ck=0;
                                        ResultSet r=st1.executeQuery("select * from assignGroup where grId='"+tbb+"' and date='"+finalS11+"'");
                                        while(r.next())
                                        {
                                            ck=1;
                                        }
                                        if(ck==0)
                                        {
                                            store.add(rs.getString("id")+"("+rs.getString("TableGr")+")");
                                        }
                                    }
                                    rs=st.executeQuery("select empName,userName from userAccount where duety='Waiter' order by count");
                                    while(rs.next())
                                    {
                                        String tbb=rs.getString("userName");
                                        int ck=0;
                                        ResultSet r=st1.executeQuery("select * from assignGroup where userName='"+tbb+"' and date='"+finalS11+"'");
                                        while(r.next())
                                        {
                                            ck=1;
                                        }
                                        if(ck==0)
                                        {
                                            store1.add(rs.getString("userName")+"("+rs.getString("empName")+")");
                                        }
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, store);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spin.setAdapter(adapter);
                                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, store1);
                                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spin1.setAdapter(adapter1);
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
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                    builder.setTitle(R.string.app_name);
                    builder.setIcon(R.mipmap.glance_logo);
                    builder.setMessage("Are You Sure To Assign Group To Waiter Automatically?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                            try
                            {
                                Statement st01 = MainActivity.connection.createStatement();
                                Statement st02 = MainActivity.connection.createStatement();
                                Statement st03 = MainActivity.connection.createStatement();
                                Statement st04 = MainActivity.connection.createStatement();
                                Statement st05 = MainActivity.connection.createStatement();
                                Statement st06 = MainActivity.connection.createStatement();
                                Statement st07 = MainActivity.connection.createStatement();
                                Statement st08 = MainActivity.connection.createStatement();
                                Statement st09 = MainActivity.connection.createStatement();
                                ArrayList TbGr=new ArrayList();
                                ResultSet cr3=st02.executeQuery("select * from tableGroup order by id");
                                while (cr3.next())
                                {
                                    TbGr.add(cr3.getInt("id"));
                                }
                                ArrayList UserGr=new ArrayList();
                                ResultSet cr4=st02.executeQuery("select * from userAccount where duety = 'Waiter' order by count");
                                while (cr4.next())
                                {
                                    UserGr.add(cr4.getString("userName"));
                                }
                                int ckck2=0;
                                ResultSet cr1=st01.executeQuery("select * from assignGroup");
                                while (cr1.next())
                                {
                                    ckck2=1;
                                }
                                if(ckck2==0)
                                {
                                    for(int i=0;i<UserGr.size();i++)
                                    {
                                        String userN=UserGr.get(i).toString();
                                        int groId=Integer.parseInt(TbGr.get(i).toString());
                                        String GrName="";
                                        ResultSet cr5=st05.executeQuery("select * from tableGroup where id="+groId);
                                        while (cr5.next())
                                        {
                                            GrName=cr5.getString("TableGr");
                                        }
                                        st06.execute("delete from assignGroup where date='"+MainActivity.Main_WORK_DATE+"' and userName='"+userN+"' and grId="+groId);
                                        st06.execute("insert into assignGroup(date, userName, grId, grName) " +
                                                "values('"+MainActivity.Main_WORK_DATE+"','"+userN+"',"+groId+",'"+GrName+"')");
                                    }
                                }
                                else
                                {
                                    int ckck1=0;
                                    ResultSet cr7=st01.executeQuery("select * from assignGroup where date='"+MainActivity.Main_WORK_DATE+"'");
                                    while (cr7.next())
                                    {
                                        ckck1=ckck1+1;
                                    }
                                    if(ckck1!=0||(ckck1!=TbGr.size()))
                                    {
                                        st06.execute("delete from assignGroup where date='"+MainActivity.Main_WORK_DATE+"'");
                                    }
                                    ArrayList UsGr=new ArrayList();
                                    ResultSet cr2=st02.executeQuery("select TOP("+TbGr.size()+") * from assignGroup order by date desc,grId");
                                    while (cr2.next())
                                    {
                                        ArrayList UsGr1=new ArrayList();
                                        UsGr1.add(cr2.getString("userName"));
                                        UsGr1.add(cr2.getInt("grId"));
                                        UsGr.add(UsGr1);
                                    }
                                    if(UsGr.size()>0)
                                    {
                                        for(int i=0;i<UsGr.size();i++)
                                        {
                                            ArrayList Usgr1= (ArrayList) UsGr.get(i);
                                            String userN=Usgr1.get(0).toString();
                                            int groId=Integer.parseInt(Usgr1.get(1).toString());
                                            groId=groId+1;
                                            int ckc=0;
                                            ResultSet cr5=st04.executeQuery("select * from tableGroup where id="+groId);
                                            while (cr5.next())
                                            {
                                                ckc=1;
                                            }
                                            if(ckc==0)
                                            {
                                                ResultSet cr6=st05.executeQuery("select TOP(1) * from tableGroup order by id");
                                                while (cr6.next())
                                                {
                                                    groId=cr6.getInt("id");
                                                }
                                            }
                                            String GrName="";
                                            cr5=st03.executeQuery("select * from tableGroup where id="+groId);
                                            while (cr5.next())
                                            {
                                                GrName=cr5.getString("TableGr");
                                            }
                                            st06.execute("delete from assignGroup where date='"+MainActivity.Main_WORK_DATE+"' and userName='"+userN+"' and grId="+groId);
                                            st06.execute("insert into assignGroup(date, userName, grId, grName) " +
                                                    "values('" + MainActivity.Main_WORK_DATE + "','" + userN + "'," + groId + ",'" + GrName + "')");
                                        }
                                        ArrayList store=new ArrayList();
                                        ArrayList store1=new ArrayList();
                                        ResultSet rs=st07.executeQuery("select id, TableGr from tableGroup");
                                        while(rs.next())
                                        {
                                            String tbb=rs.getString("id");
                                            int ck=0;
                                            ResultSet r=st08.executeQuery("select * from assignGroup where grId='"+tbb+"' and date='"+MainActivity.Main_WORK_DATE+"'");
                                            while(r.next())
                                            {
                                                ck=1;
                                            }
                                            if(ck==0)
                                            {
                                                store.add(rs.getInt("id"));
                                            }
                                        }
                                        rs=st07.executeQuery("select empName,userName from userAccount where duety='Waiter' order by count");
                                        while(rs.next())
                                        {
                                            String tbb=rs.getString("userName");
                                            int ck=0;
                                            ResultSet r=st09.executeQuery("select * from assignGroup where userName='"+tbb+"' and date='"+MainActivity.Main_WORK_DATE+"'");
                                            while(r.next())
                                            {
                                                ck=1;
                                            }
                                            if(ck==0)
                                            {
                                                store1.add(rs.getString("userName"));
                                            }
                                        }
                                        for(int i=0;i<store1.size();i++)
                                        {
                                            String userN=store1.get(i).toString();
                                            int groId=Integer.parseInt(store.get(i).toString());
                                            String GrName="";
                                            ResultSet cr5=st05.executeQuery("select * from tableGroup where id="+groId);
                                            while (cr5.next())
                                            {
                                                GrName=cr5.getString("TableGr");
                                            }
                                            st06.execute("delete from assignGroup where date='"+MainActivity.Main_WORK_DATE+"' and userName='"+userN+"' and grId="+groId);
                                            st06.execute("insert into assignGroup(date, userName, grId, grName) " +
                                                    "values('"+MainActivity.Main_WORK_DATE+"','"+userN+"',"+groId+",'"+GrName+"')");
                                        }
                                    }
                                    else
                                    {
                                        for(int i=0;i<UserGr.size();i++)
                                        {
                                            String userN=UserGr.get(i).toString();
                                            int groId=Integer.parseInt(TbGr.get(i).toString());
                                            String GrName="";
                                            ResultSet cr5=st05.executeQuery("select * from tableGroup where id="+groId);
                                            while (cr5.next())
                                            {
                                                GrName=cr5.getString("TableGr");
                                            }
                                            st06.execute("delete from assignGroup where date='"+MainActivity.Main_WORK_DATE+"' and userName='"+userN+"' and grId="+groId);
                                            st06.execute("insert into assignGroup(date, userName, grId, grName) " +
                                                    "values('"+MainActivity.Main_WORK_DATE+"','"+userN+"',"+groId+",'"+GrName+"')");
                                        }
                                    }
                                }
                                Toast.makeText(root.getContext(),"Success",Toast.LENGTH_LONG).show();
                                store=new ArrayList();
                                store1=new ArrayList();
                                ResultSet rs=st01.executeQuery("select id, TableGr from tableGroup order by id");
                                while(rs.next())
                                {
                                    int tbb=rs.getInt("id");
                                    int ck=0;
                                    ResultSet r=st02.executeQuery("select * from assignGroup where grId="+tbb+" and date='"+MainActivity.Main_WORK_DATE+"'");
                                    while(r.next())
                                    {
                                        ck=1;
                                    }
                                    if(ck==0)
                                    {
                                        store.add(rs.getString("id")+"("+rs.getString("TableGr")+")");
                                    }
                                }
                                rs=st01.executeQuery("select empName,userName from userAccount where duety='Waiter' order by count");
                                while(rs.next())
                                {
                                    String tbb=rs.getString("userName");
                                    int ck=0;
                                    ResultSet r=st02.executeQuery("select * from assignGroup where userName='"+tbb+"' and date='"+MainActivity.Main_WORK_DATE+"'");
                                    while(r.next())
                                    {
                                        ck=1;
                                    }
                                    if(ck==0)
                                    {
                                        store1.add(rs.getString("userName")+"("+rs.getString("empName")+")");
                                    }
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, store);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin.setAdapter(adapter);
                                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, store1);
                                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin1.setAdapter(adapter1);
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
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        try
        {
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        appendRow1("");
        return root;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void appendRow1(String str) {
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
                LinearLayout.LayoutParams mainParms=new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                mainParms.setMargins(5, 5, 5, 5);
                LinearLayout.LayoutParams mainParms2=new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                mainParms2.setMargins(10, 5, 10, 5);
                Statement st= MainActivity.connection.createStatement();
                ResultSet r=st.executeQuery("select * from assignGroup where date ='"+MainActivity.Main_WORK_DATE+"' order by grId");
                while (r.next())
                {
                    String t_id=r.getString("grId");
                    String wa=r.getString("userName");
                    String empN=r.getString("grName");
                    String empN1="";
                    ResultSet cc=st1.executeQuery("select empName from userAccount where userName='"+wa+"'");
                    while (cc.next()){
                        empN1=cc.getString("empName");
                    }
                    LinearLayout MainLin=new LinearLayout(root.getContext());
                    MainLin.setOrientation(LinearLayout.VERTICAL);
                    MainLin.setBackgroundResource(R.drawable.border_round1);

                    LinearLayout totLin=new LinearLayout(root.getContext());
                    totLin.setOrientation(LinearLayout.HORIZONTAL);
                    totLin.setWeightSum(1.0F);

                    TextView l1 = new TextView(root.getContext());
                    l1.setTextColor(Color.BLACK);
                    l1.setText(t_id);
                    l1.setTextSize(14);
                    l1.setTypeface(null, Typeface.BOLD_ITALIC);
                    l1.setTooltipText(t_id);
                    l1.setOnClickListener(txClick2);
                    totLin.addView(l1,tosubParms);
                    l1 = new TextView(root.getContext());
                    l1.setTooltipText(t_id);
                    l1.setOnClickListener(txClick2);
                    l1.setTextColor(Color.BLACK);
                    l1.setText(empN);
                    l1.setTextSize(14);
                    l1.setTypeface(null, Typeface.BOLD_ITALIC);
                    totLin.setTooltipText(t_id);
                    totLin.setOnClickListener(txClick2);
                    totLin.addView(l1,tosubParms);

                    LinearLayout totLin1=new LinearLayout(root.getContext());
                    totLin1.setOrientation(LinearLayout.HORIZONTAL);

                    totLin1.setTooltipText(t_id);
                    totLin1.setOnClickListener(txClick2);
                    l1 = new TextView(root.getContext());
                    l1.setTooltipText(t_id);
                    l1.setOnClickListener(txClick2);
                    l1.setTextColor(Color.BLACK);
                    l1.setText(empN1);
                    l1.setTextSize(14);
                    totLin1.addView(l1,tosubParms1);

                    MainLin.setTooltipText(t_id);
                    MainLin.setOnClickListener(txClick2);
                    MainLin.addView(totLin,mainParms);
                    MainLin.addView(totLin1,mainParms);
                    liner.addView(MainLin,mainParms);
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
