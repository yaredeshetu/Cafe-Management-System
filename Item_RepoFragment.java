package org.o7planning.agelegecafe.ui.item_report;

import static android.widget.TableLayout.OnClickListener;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.DatabaseHelper;
import org.o7planning.agelegecafe.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Item_RepoFragment extends Fragment {

    private Item_RepoViewModel galleryViewModel;
    private static View root=null;
    private static Button bt=null;
    private static CheckBox ch=null;
    private static EditText e1=null;
    private static TableLayout table1 =null;
    private static Spinner spin1=null;
    private static Spinner spin2=null;
    private static LinearLayout l1=null,l2=null;
    private static TableRow row1=null,row2=null;
    private static String userName="";
    private static ArrayList store=new ArrayList();
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static DBManager dbManager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(Item_RepoViewModel.class);
        root = inflater.inflate(R.layout.item_repo, container, false);

        try
        {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        try
        {
            Cursor cr=dbManager.fetch1();
            userName=cr.getString(0);
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }/*
        try {

            Cursor cr=dbManager.fetch2(userName);
            do{
                store.add(cr.getString(0));
            }while (cr.moveToNext());
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        */
        bt=root.findViewById(R.id.it_repbt);
        spin1=root.findViewById(R.id.it_repsp1);
        spin2=root.findViewById(R.id.it_repsp2);
        ch=root.findViewById(R.id.it_repch);
        e1=root.findViewById(R.id.it_repe1);
        l1=root.findViewById(R.id.it_replin1);
        l2=root.findViewById(R.id.it_replin2);
        row1=root.findViewById(R.id.it_reprow1);
        row2=root.findViewById(R.id.it_reprow2);
        table1=root.findViewById(R.id.it_repmenu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, store);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter);
        l1.removeView(l2);
        ch.setChecked(false);
        table1.removeAllViews();
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10, 0, 5, 10);
        table1.addView(row1, params);
        ch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    table1.removeAllViews();
                    TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.WRAP_CONTENT,
                            TableLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(10, 0, 5, 10);
                    if(ch.isChecked())
                    {
                        table1.addView(row2, params);
                    }
                    else
                    {
                        table1.addView(row1, params);
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String str=spin2.getSelectedItem().toString();
                    if(str.equalsIgnoreCase("Search All"))
                    {
                        l1.removeView(l2);
                    }
                    else
                    {
                        int count=l1.getChildCount();
                        if(count==1)
                            l1.addView(l2);
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(ch.isChecked())
                        appendRow1(table1,spin2.getSelectedItem().toString());
                    else
                        appendRow(table1,spin2.getSelectedItem().toString());
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;
    }/*
    private ArrayList search(String str,int choice)
    {
        ArrayList Big=new ArrayList();
        try
        {
            ArrayList s = new ArrayList();
            if(spin1.getChildCount()==0)
            {
                Toast.makeText(root.getContext(),"You Have Not Store.\nPlease Import Store For This User \nor Set Store For This User On Inventory Setting\nOr Contact System Admin.",Toast.LENGTH_LONG).show();
            }
            else
            {
                String store=spin1.getSelectedItem().toString();
                Cursor cr =null;
                if(choice==1)
                    cr = DBManager.database.rawQuery("SELECT " + DatabaseHelper.c1 +","+DatabaseHelper.c2+","+DatabaseHelper.c8+","+DatabaseHelper.c7+ " FROM " + DatabaseHelper.TABLE_NAME2+" where "+DatabaseHelper.c5+"='"+store+"' order by "+DatabaseHelper.c1, null);
                else if(choice==2)
                    cr = DBManager.database.rawQuery("SELECT " + DatabaseHelper.c1 +","+DatabaseHelper.c2+","+DatabaseHelper.c8+","+DatabaseHelper.c7+ " FROM " + DatabaseHelper.TABLE_NAME2+" where "+DatabaseHelper.c5+"='"+store+"' and "+DatabaseHelper.c1+"='"+str+"' order by "+DatabaseHelper.c1, null);
                double totBeg=0.0,totIn=0.0,totOut=0.0,totEnd=0.0;
                if (cr != null) {
                    cr.moveToFirst();
                }
                Big = new ArrayList();
                int count = 0;
                do {
                    count = count + 1;
                    s = new ArrayList();
                    String itId=cr.getString(0);
                    s.add(count);
                    s.add(itId);
                    s.add(cr.getString(1));
                    double begBal=cr.getDouble(2);
                    s.add(df.format(begBal));
                    double qtyIn=0.0,qtyOut=0.0;
                    Cursor cr1 = DBManager.database.rawQuery("SELECT SUM(" + DatabaseHelper.e5 +") AS qtyIn,SUM("+DatabaseHelper.e8+") AS qtyOut FROM " + DatabaseHelper.TABLE_NAME4+" where "+DatabaseHelper.e4+"='"+store+"' and "+DatabaseHelper.e9+"='true' and "+DatabaseHelper.e2+"='"+itId+"'", null);
                    if(cr1.moveToFirst())
                    {
                        qtyIn=cr1.getDouble(0);
                        qtyOut=cr1.getDouble(1);
                    }
                    s.add(df.format(qtyIn));
                    s.add(df.format(qtyOut));
                    double endBal=cr.getDouble(3);
                    s.add(df.format(endBal));
                    Big.add(s);
                    totBeg=totBeg+begBal;
                    totIn=totIn+qtyIn;
                    totOut=totOut+qtyOut;
                    totEnd=totEnd+endBal;
                } while (cr.moveToNext());
                s=new ArrayList();
                s.add("");
                s.add("");
                s.add("Total");
                s.add(df.format(totBeg));
                s.add(df.format(totIn));
                s.add(df.format(totOut));
                s.add(df.format(totEnd));
                Big.add(s);
            }
        }
        catch(Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return Big;
    }
    private ArrayList search1(String str,int choice)
    {
        ArrayList Big=new ArrayList();
        try
        {
            ArrayList s = new ArrayList();
            if(spin1.getChildCount()==0)
            {
                Toast.makeText(root.getContext(),"You Have Not Store.\nPlease Import Store For This User \nor Set Store For This User On Inventory Setting\nOr Contact System Admin.",Toast.LENGTH_LONG).show();
            }
            else
            {
                String store=spin1.getSelectedItem().toString();
                Cursor cr =null;
                if(choice==1)
                    cr = DBManager.database.rawQuery("SELECT " + DatabaseHelper.c1 +","+DatabaseHelper.c2+","+DatabaseHelper.c8+","+DatabaseHelper.c7+ " FROM " + DatabaseHelper.TABLE_NAME2+" where "+DatabaseHelper.c5+"='"+store+"' order by "+DatabaseHelper.c1, null);
                else if(choice==2)
                    cr = DBManager.database.rawQuery("SELECT " + DatabaseHelper.c1 +","+DatabaseHelper.c2+","+DatabaseHelper.c8+","+DatabaseHelper.c7+ " FROM " + DatabaseHelper.TABLE_NAME2+" where "+DatabaseHelper.c5+"='"+store+"' and "+DatabaseHelper.c1+"='"+str+"' order by "+DatabaseHelper.c1, null);
                double TotBeg=0.0,TotIn=0.0,TotOut=0.0,TotEnd=0.0;
                if (cr != null) {
                    cr.moveToFirst();
                }
                Big = new ArrayList();
                int count = 0;
                do {
                    double totBeg=0.0,totIn=0.0,totOut=0.0,totEnd=0.0;
                    count = count + 1;
                    s = new ArrayList();
                    String itId=cr.getString(0);
                    s.add(count);
                    s.add(itId);
                    s.add(cr.getString(1));
                    double begBal=cr.getDouble(2);
                    s.add("");
                    s.add("");
                    s.add("");
                    s.add("");
                    s.add("");
                    Big.add(s);
                    s = new ArrayList();
                    s.add("");
                    s.add("");
                    s.add("Beginning Bal");
                    s.add("");
                    s.add("");
                    s.add("");
                    s.add(df.format(begBal));
                    s.add("");
                    Big.add(s);
                    double begBalTemp=begBal;
                    double qtyIn=0.0,qtyOut=0.0;
                    Cursor cr1 = DBManager.database.rawQuery("SELECT " + DatabaseHelper.e5 +","+DatabaseHelper.e8+","+DatabaseHelper.e1+" FROM " + DatabaseHelper.TABLE_NAME4+" where "+DatabaseHelper.e4+"='"+store+"' and "+DatabaseHelper.e9+"='true' and "+DatabaseHelper.e2+"='"+itId+"'", null);
                    while (cr1.moveToNext())
                    {
                        qtyIn=cr1.getDouble(0);
                        qtyOut=cr1.getDouble(1);
                        String vouch=cr1.getString(2);
                        String timS="",vouchTy="";
                        Cursor cr2 = DBManager.database.rawQuery("SELECT " + DatabaseHelper.d16 +","+DatabaseHelper.d1+" FROM " + DatabaseHelper.TABLE_NAME3+" where "+DatabaseHelper.d21+"='true' and "+DatabaseHelper.d0+"='"+vouch+"'", null);
                        if(cr2.moveToFirst())
                        {
                            timS=cr2.getString(0);
                            vouchTy=cr2.getString(1);
                        }
                        s = new ArrayList();
                        s.add("");
                        s.add("");
                        s.add(vouch);
                        s.add(timS);
                        if(qtyIn==0)
                            s.add("");
                        else
                            s.add(df.format(qtyIn));
                        if(qtyOut==0)
                            s.add("");
                        else
                            s.add(df.format(qtyOut));
                        begBalTemp=begBalTemp+qtyIn;
                        begBalTemp=begBalTemp-qtyOut;
                        s.add(df.format(begBalTemp));
                        s.add(vouchTy);
                        Big.add(s);
                        totIn=totIn+qtyIn;
                        totOut=totOut+qtyOut;
                    }
                    totEnd=begBalTemp;
                    //totBeg=totBeg+begBal;
                    TotIn=TotIn+qtyIn;
                    TotOut=TotOut+qtyOut;
                    TotEnd=TotEnd+totEnd;
                    s=new ArrayList();
                    s.add("");
                    s.add("");
                    s.add("End Balance");
                    s.add("");
                    s.add(df.format(totIn));
                    s.add(df.format(totOut));
                    s.add(df.format(totEnd));
                    s.add("");
                    Big.add(s);
                } while (cr.moveToNext());
                s=new ArrayList();
                s.add("");
                s.add("");
                s.add("Total Balance");
                s.add("");
                s.add(df.format(TotIn));
                s.add(df.format(TotOut));
                s.add(df.format(TotEnd));
                s.add("");
                Big.add(s);
            }
        }
        catch(Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return Big;
    }
    */
    private void appendRow(TableLayout table, String str) {
        try
        {
            table.removeAllViews();
            ArrayList b=new ArrayList();
            /*
            if(str.equals("Search All"))
                b = new ArrayList();//search("",1);
            else
            {
                String str1=e1.getText().toString();
                if(str1.equals("")||str1.equals(" "))
                {
                    b = search(str1,1);
                }
                else
                {
                    b = search(str1,2);
                }
            }

             */
            Thread.sleep(200);
            TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(10, 5, 5, 10);
            table.addView(row1, params);
            for(int i=0;i<b.size();i++)
            {
                Thread.sleep(20);
                TableRow row = new TableRow(root.getContext());
                ArrayList s= (ArrayList) b.get(i);
                for(int j=0;j<s.size();j++)
                {
                    String val1=s.get(0).toString();
                    String val=s.get(j).toString();
                    TextView l1 = new TextView(root.getContext());
                    if(val1.equals(""))
                    {
                        l1.setTextColor(Color.BLACK);
                        l1.setBackgroundColor(Color.rgb(255,204,102));
                        l1.setTextSize(15);
                    }
                    else
                    {
                        l1.setTextColor(Color.BLACK);
                        l1.setBackgroundColor(Color.rgb(255,255,255));
                        l1.setTextSize(13);
                    }
                    l1.setText(val);
                    l1.setPadding(5,0,0,0);
                    row.addView(l1, new TableRow.LayoutParams());
                }
                int rm=i%2;
                if(rm==0)
                {
                    row.setBackgroundColor(Color.rgb(255,255,255));
                }
                else
                {
                    row.setBackgroundColor(Color.rgb(220,220,220));
                }
                table.addView(row,params);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private void appendRow1(TableLayout table, String str) {
        try
        {
            table.removeAllViews();
            ArrayList b=new ArrayList();
            /*
            if(str.equals("Search All"))
                b = search1("",1);
            else
            {
                String str1=e1.getText().toString();
                if(str1.equals("")||str1.equals(" "))
                {
                    b = search1(str1,1);
                }
                else
                {
                    b = search1(str1,2);
                }
            }

             */
            Thread.sleep(200);
            TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(10, 5, 5, 10);
            table.addView(row2, params);
            for(int i=0;i<b.size();i++)
            {
                Thread.sleep(20);
                TableRow row = new TableRow(root.getContext());
                ArrayList s= (ArrayList) b.get(i);
                for(int j=0;j<s.size();j++)
                {
                    String val1=s.get(0).toString();
                    String val2=s.get(1).toString();
                    String val20=s.get(2).toString();
                    String val3=s.get(3).toString();
                    String val=s.get(j).toString();
                    TextView l1 = new TextView(root.getContext());
                    if(val1.equals(""))
                    {
                        if(val2.equals(""))
                        {
                            if(val3.equals(""))
                            {
                                if(val20.equals("Total Balance"))
                                {
                                    l1.setTextColor(Color.BLACK);
                                    l1.setBackgroundColor(Color.rgb(255,87,34));
                                    l1.setTextSize(16);
                                    l1.setTypeface(null, Typeface.BOLD);
                                }
                                else
                                {
                                    l1.setTextColor(Color.BLACK);
                                    l1.setBackgroundColor(Color.rgb(255,204,102));
                                    l1.setTextSize(14);
                                    l1.setTypeface(null, Typeface.BOLD);
                                }
                            }
                            else
                            {
                                l1.setTextColor(Color.BLACK);
                                l1.setBackgroundColor(Color.rgb(255,255,255));
                                l1.setTextSize(13);
                            }
                        }
                        else
                        {
                            l1.setTextColor(Color.BLACK);
                            l1.setBackgroundColor(Color.rgb(255,255,155));
                            l1.setTextSize(14);
                            l1.setTypeface(null, Typeface.BOLD);
                        }
                    }
                    else
                    {
                        l1.setTextColor(Color.BLACK);
                        l1.setBackgroundColor(Color.rgb(204,204,204));
                        l1.setTextSize(16);
                        l1.setTypeface(null, Typeface.BOLD);
                    }
                    l1.setText(val);
                    l1.setPadding(5,0,0,0);
                    row.addView(l1, new TableRow.LayoutParams());
                }
                int rm=i%2;
                if(rm==0)
                {
                    row.setBackgroundColor(Color.rgb(255,255,255));
                }
                else
                {
                    row.setBackgroundColor(Color.rgb(220,220,220));
                }
                table.addView(row,params);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}