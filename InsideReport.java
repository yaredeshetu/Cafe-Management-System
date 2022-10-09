package org.o7planning.agelegecafe.ui.sales_report;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.DatabaseHelper;
import org.o7planning.agelegecafe.MainActivity;
import org.o7planning.agelegecafe.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InsideReport extends Fragment {
    private static View root=null;
    private static DBManager dbManager;
    public static TableLayout table=null;
    private static TableRow row1=null;
    private static CheckBox ch1=null,ch2=null,ch3=null;
    private static TextView tx1=null,tx2=null;
    private static Button b1=null,b2=null;
    private static LinearLayout l1=null,l2=null,l3=null;
    private static Spinner spin=null;
    private int mYear, mMonth, mDay, mHour, mMinute;
    public InsideReport() {
        // Required empty public constructor
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.inside_repo, container, false);
        try
        {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        spin=root.findViewById(R.id.reposp1);
        ch1=root.findViewById(R.id.repoch1);
        ch2=root.findViewById(R.id.repoch2);
        ch3=root.findViewById(R.id.repoch3);
        table=root.findViewById(R.id.or_lis_menu);
        row1=root.findViewById(R.id.vouchrow1);
        tx1=root.findViewById(R.id.repotx1);
        tx2=root.findViewById(R.id.repotx2);
        b1=root.findViewById(R.id.repobt1);
        b2=root.findViewById(R.id.repobt2);
        l1=root.findViewById(R.id.replin1);
        l2=root.findViewById(R.id.replin2);
        l3=root.findViewById(R.id.replin3);
        l1.removeView(l2);
        l3.setVisibility(View.INVISIBLE);
        Date d = new Date();
        int yr = d.getYear() + 1900;
        int mn = d.getMonth() + 1;
        int dt = d.getDate();
        String s16 = yr + "-" + mn + "-" + dt;
        tx1.setText(s16);
        tx2.setText(s16);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String str=spin.getSelectedItem().toString();
                    if(str.equalsIgnoreCase("Select Date"))
                    {
                        LinearLayout.LayoutParams mainParms1=new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        mainParms1.setMargins(0, 5, 0, 0);
                        l1.addView(l2,mainParms1);
                    }
                    else
                    {
                        l1.removeView(l2);
                    }
                    if(ch1.isChecked())
                        appendRow1(table);
                    else
                        appendRow(table);
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
        ch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ch3.isChecked())
                {
                    l3.setVisibility(View.VISIBLE);
                }
                else
                {
                    l3.setVisibility(View.INVISIBLE);
                }
                if(ch1.isChecked())
                    appendRow1(table);
                else
                    appendRow(table);
            }
        });
        tx1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(root.getContext(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    tx1.setText(year + "-" + (monthOfYear + 1) + "-" +dayOfMonth );
                                    if(ch1.isChecked())
                                        appendRow1(table);
                                    else
                                        appendRow(table);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(root.getContext(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    tx1.setText(year + "-" + (monthOfYear + 1) + "-" +dayOfMonth );
                                    if(ch1.isChecked())
                                        appendRow1(table);
                                    else
                                        appendRow(table);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
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
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(root.getContext(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    tx2.setText(year + "-" + (monthOfYear + 1) + "-" +dayOfMonth );
                                    if(ch1.isChecked())
                                        appendRow1(table);
                                    else
                                        appendRow(table);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        tx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(root.getContext(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    tx2.setText(year + "-" + (monthOfYear + 1) + "-" +dayOfMonth );
                                    if(ch1.isChecked())
                                        appendRow1(table);
                                    else
                                        appendRow(table);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        ch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ch1.isChecked())
                    appendRow1(table);
                else
                    appendRow(table);
            }
        });
        ch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ch1.isChecked())
                    appendRow1(table);
                else
                    appendRow(table);
            }
        });
        if(ch1.isChecked())
            appendRow1(table);
        else
            appendRow(table);
        return root;
    }
    /*
    private static String sear()
    {
        String sql="";
        String str=spin.getSelectedItem().toString();
        if(str.equalsIgnoreCase("Daily"))
        {
            Date d = new Date();
            int yr = d.getYear() + 1900;
            int mn = d.getMonth() + 1;
            int dt = d.getDate();
            String s16 = yr + "-" + mn + "-" + dt;
            sql=" and "+DatabaseHelper.d23+"='"+s16+"'";
        }
        else if(str.equalsIgnoreCase("Select Date"))
        {
            if(ch3.isChecked())
            {
                String s16 =tx1.getText().toString();
                String s17 =tx2.getText().toString();
                sql=" and "+DatabaseHelper.d23+">='"+s16+"' and "+DatabaseHelper.d23+"<='"+s17+"'";
            }
            else
            {
                String s16 =tx1.getText().toString();
                sql=" and "+DatabaseHelper.d23+"='"+s16+"'";
            }
        }
        return sql;
    }
    public static ArrayList search1()
    {
        ArrayList Big=new ArrayList();
        try
        {
            Big = new ArrayList();
            String vType="Inside Item Sales";
            String sql=sear();
            Cursor cr2 = DBManager.database.rawQuery("SELECT " +DatabaseHelper.f2+","+DatabaseHelper.f3 + " FROM " + DatabaseHelper.TABLE_NAME5+" where "+DatabaseHelper.f6+"='Waiter'", null);
            while(cr2.moveToNext())
            {
                String empName = cr2.getString(0);
                ArrayList s = new ArrayList();
                double Tunt = 0.0, Tqty = 0.0, Tpri = 0.0;
                int Tcoun = 0;
                ArrayList b1 = new ArrayList();
                Cursor cr = DBManager.database.rawQuery("SELECT " + DatabaseHelper.d0 + "," + DatabaseHelper.d20 + "," + DatabaseHelper.d7 + "," + DatabaseHelper.d9 + "," + DatabaseHelper.d16 + " FROM " + DatabaseHelper.TABLE_NAME3 + " where " + DatabaseHelper.d17 + "='" + MainActivity.Emp_USERNAME + "' and " + DatabaseHelper.d1 + "='" + vType + "' and " + DatabaseHelper.d21 + "='true'" + " and " + DatabaseHelper.d9 + "='" + empName + "'" + sql + " order by " + DatabaseHelper.d23 + " desc," + DatabaseHelper.d16 + " desc", null);
                while (cr.moveToNext()) {
                    s = new ArrayList();
                    int vouchId = cr.getInt(0);
                    String empN = cr.getString(3);
                    int coun = cr.getInt(1);
                    double pri = cr.getDouble(2);
                    if (ch2.isChecked()) {
                        s.add("Waiter Name:-"+empName);
                        s.add(coun);
                        s.add(pri);
                        s.add(empN);
                        s.add(cr.getString(4));
                    }
                    b1 = new ArrayList();
                    Cursor cr1 = null;
                    if (ch2.isChecked())
                        cr1 = DBManager.database.rawQuery("SELECT " + DatabaseHelper.e3 + "," + DatabaseHelper.e10 + "," + DatabaseHelper.e8 + "," + DatabaseHelper.e7 + " FROM " + DatabaseHelper.TABLE_NAME4 + " where " + DatabaseHelper.e1 + "=" + vouchId, null);
                    else
                        cr1 = DBManager.database.rawQuery("SELECT SUM(" + DatabaseHelper.e10 + ") AS qtyOut,SUM(" + DatabaseHelper.e8 + ") AS qtyOut FROM " + DatabaseHelper.TABLE_NAME4 + " where " + DatabaseHelper.e1 + "=" + vouchId, null);
                    while (cr1.moveToNext()) {
                        if (ch2.isChecked()) {
                            ArrayList s1 = new ArrayList();
                            s1.add(cr1.getString(0));
                            s1.add(cr1.getDouble(1));
                            s1.add(cr1.getDouble(2));
                            s1.add(cr1.getDouble(3));
                            b1.add(s1);
                        } else {
                            Tunt = Tunt + cr1.getDouble(0);
                            Tqty = Tqty + cr1.getDouble(1);
                        }
                    }
                    if (ch2.isChecked()) {
                        s.add(b1);
                        Big.add(s);
                    }
                    Tcoun=Tcoun+coun;
                    Tpri=Tpri+pri;
                }
                if(!ch2.isChecked())
                {
                    s = new ArrayList();
                    s.add("Waiter Name:-"+empName);
                    s.add(Tcoun);
                    s.add(Tpri);
                    s.add(empName);
                    if (spin.getSelectedItem().toString().equalsIgnoreCase("Select Date"))
                        s.add("Date Range");
                    else
                        s.add(spin.getSelectedItem().toString());
                    b1 = new ArrayList();
                    b1.add(Tunt);
                    b1.add(Tqty);
                    s.add(b1);
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

     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void appendRow1(TableLayout table) {
        try
        {
            table.removeAllViews();
            ArrayList b=new ArrayList();
            //b=search1();
            Thread.sleep(200);
            TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(5, 5, 5, 5);
            LinearLayout.LayoutParams mainParms=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            mainParms.setMargins(5, 5, 5, 0);
            LinearLayout.LayoutParams mainParms2=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            mainParms2.setMargins(10, 5, 10, 5);
            LinearLayout.LayoutParams mainParms1=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            mainParms1.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams mainParms3=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            mainParms3.setMargins(5, 0, 5, 0);
            TableRow.LayoutParams rowParm=new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            rowParm.setMargins(5,5,5,5);
            LinearLayout.LayoutParams tosubParms2=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            tosubParms2.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams tosubParms=new LinearLayout.LayoutParams(
                    (int) convDpToPx(130),
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            tosubParms.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams tosubParms1=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            tosubParms1.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams waisubParms=new LinearLayout.LayoutParams(
                    (int) convDpToPx(160),
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            waisubParms.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams waisubParms1=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            waisubParms1.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams itsubParms=new LinearLayout.LayoutParams(
                    (int) convDpToPx(310),
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            itsubParms.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams qtysubParms=new LinearLayout.LayoutParams(
                    (int) convDpToPx(95),
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            qtysubParms.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams qtysubParms1=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            qtysubParms1.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams btsubParms=new LinearLayout.LayoutParams(
                    (int) convDpToPx(150),
                    (int) convDpToPx(30)
            );
            btsubParms.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams btsubParms1=new LinearLayout.LayoutParams(
                    (int) convDpToPx(75),
                    (int) convDpToPx(30)
            );
            btsubParms1.setMargins(5, 0, 5, 0);
            if(b.size()==0)
                table.addView(row1, params);
            String eNam="";
            int TitCoun=0;
            double TgraTot=0.0,Tqty=0.0,Tunt=0.0;
            TableRow row = new TableRow(root.getContext());
            LinearLayout MainLin=new LinearLayout(root.getContext());
            MainLin.setOrientation(LinearLayout.VERTICAL);
            MainLin.setBackgroundResource(R.drawable.back_white);
            for(int i=0;i<b.size();i++)
            {
                Thread.sleep(20);
                row = new TableRow(root.getContext());
                MainLin=new LinearLayout(root.getContext());
                MainLin.setOrientation(LinearLayout.VERTICAL);
                MainLin.setBackgroundResource(R.drawable.back_white);
                ArrayList s= (ArrayList) b.get(i);
                LinearLayout totLin=new LinearLayout(root.getContext());
                totLin.setOrientation(LinearLayout.HORIZONTAL);
                totLin.setBackgroundResource(R.drawable.round_corner3);
                LinearLayout waiTLin=new LinearLayout(root.getContext());
                waiTLin.setOrientation(LinearLayout.HORIZONTAL);
                String eName=s.get(0).toString();
                if(!eNam.equalsIgnoreCase(eName))
                {
                    row = new TableRow(root.getContext());
                    MainLin=new LinearLayout(root.getContext());
                    MainLin.setOrientation(LinearLayout.VERTICAL);
                    MainLin.setBackgroundResource(R.drawable.back_white);
                    LinearLayout GtotLiner = new LinearLayout(root.getContext());
                    GtotLiner.setOrientation(LinearLayout.HORIZONTAL);
                    GtotLiner.setBackgroundResource(R.drawable.round_corner);
                    TextView l1 = new TextView(root.getContext());
                    l1.setTextColor(Color.WHITE);
                    l1.setText(eName);
                    l1.setTextSize(16);
                    l1.setTypeface(null, Typeface.BOLD_ITALIC);
                    GtotLiner.addView(l1, tosubParms2);
                    MainLin.addView(GtotLiner, mainParms3);
                    row.addView(MainLin, rowParm);
                    table.addView(row, params);
                    eNam=eName;
                }
                row = new TableRow(root.getContext());
                MainLin=new LinearLayout(root.getContext());
                MainLin.setOrientation(LinearLayout.VERTICAL);
                MainLin.setBackgroundResource(R.drawable.back_white);
                int itCoun=Integer.parseInt(s.get(1).toString());
                double graTot=Double.parseDouble(s.get(2).toString());
                TitCoun=TitCoun+itCoun;
                TgraTot=TgraTot+graTot;
                String wai=s.get(3).toString();
                String timS=s.get(4).toString();
                TextView l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText("Total Item:-"+itCoun);
                l1.setTextSize(14);
                l1.setTypeface(null, Typeface.BOLD_ITALIC);
                totLin.addView(l1,tosubParms);
                l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText("Total Price:-"+graTot);
                l1.setTextSize(14);
                l1.setTypeface(null, Typeface.BOLD_ITALIC);
                totLin.addView(l1,tosubParms1);

                l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText("Waiter:-"+wai);
                l1.setTextSize(10);
                waiTLin.addView(l1,waisubParms);
                l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText("Time:-"+timS);
                l1.setTextSize(10);
                waiTLin.addView(l1,waisubParms1);
                ArrayList s1 = (ArrayList) s.get(5);
                if(ch2.isChecked())
                {
                    for (int j = 0; j < s1.size(); j++) {
                        ArrayList s2 = (ArrayList) s1.get(j);
                        String val = s2.get(0).toString();
                        double unt = Double.parseDouble(s2.get(1).toString());
                        double qty = Double.parseDouble(s2.get(2).toString());
                        double pri = Double.parseDouble(s2.get(3).toString());
                        Tqty=Tqty+qty;
                        Tunt=Tunt+unt;
                        LinearLayout itLin = new LinearLayout(root.getContext());
                        LinearLayout qtyLin = new LinearLayout(root.getContext());
                        itLin.setOrientation(LinearLayout.HORIZONTAL);
                        qtyLin.setOrientation(LinearLayout.HORIZONTAL);
                        l1 = new TextView(root.getContext());
                        l1.setTextColor(Color.BLACK);
                        l1.setText("Item Name:-" + val);
                        l1.setTextSize(14);
                        l1.setTypeface(null, Typeface.BOLD);
                        itLin.addView(l1, itsubParms);
                        l1 = new TextView(root.getContext());
                        l1.setTextColor(Color.BLACK);
                        l1.setText("Unit:-" + unt);
                        l1.setTextSize(14);
                        qtyLin.addView(l1, qtysubParms);
                        l1 = new TextView(root.getContext());
                        l1.setTextColor(Color.BLACK);
                        l1.setText("Qty:-" + qty);
                        l1.setTextSize(14);
                        qtyLin.addView(l1, qtysubParms);
                        l1 = new TextView(root.getContext());
                        l1.setTextColor(Color.BLACK);
                        l1.setText("Price:-" + pri);
                        l1.setTextSize(14);
                        qtyLin.addView(l1, qtysubParms1);
                        MainLin.addView(itLin, mainParms);
                        MainLin.addView(qtyLin, mainParms1);
                    }
                }
                else
                {
                    double unt = Double.parseDouble(s1.get(0).toString());
                    double qty = Double.parseDouble(s1.get(1).toString());
                    Tqty=Tqty+qty;
                    Tunt=Tunt+unt;
                    LinearLayout totLin1=new LinearLayout(root.getContext());
                    totLin1.setOrientation(LinearLayout.HORIZONTAL);
                    totLin1.setBackgroundResource(R.drawable.round_corner3);
                    l1 = new TextView(root.getContext());
                    l1.setTextColor(Color.BLACK);
                    l1.setText("Total Unit:-"+unt);
                    l1.setTextSize(14);
                    l1.setTypeface(null, Typeface.BOLD_ITALIC);
                    totLin1.addView(l1,tosubParms);
                    l1 = new TextView(root.getContext());
                    l1.setTextColor(Color.BLACK);
                    l1.setText("Total Qty:-"+qty);
                    l1.setTextSize(14);
                    l1.setTypeface(null, Typeface.BOLD_ITALIC);
                    totLin1.addView(l1,tosubParms1);

                    MainLin.addView(totLin1,mainParms);
                }
                MainLin.addView(totLin,mainParms);
                MainLin.addView(waiTLin,mainParms2);
                row.addView(MainLin,rowParm);
                table.addView(row,params);
            }
            row = new TableRow(root.getContext());
            MainLin=new LinearLayout(root.getContext());
            MainLin.setOrientation(LinearLayout.VERTICAL);
            MainLin.setBackgroundResource(R.drawable.back_white);
            LinearLayout GtotLin1=new LinearLayout(root.getContext());
            GtotLin1.setOrientation(LinearLayout.HORIZONTAL);
            GtotLin1.setBackgroundResource(R.drawable.round_corner1);
            TextView l1 = new TextView(root.getContext());
            l1.setTextColor(Color.BLACK);
            l1.setText("TOTAL UNIT:-"+Tunt);
            l1.setTextSize(12);
            l1.setTypeface(null, Typeface.BOLD_ITALIC);
            GtotLin1.addView(l1,tosubParms);
            l1 = new TextView(root.getContext());
            l1.setTextColor(Color.BLACK);
            l1.setText("TOTAL QTY:-"+Tqty);
            l1.setTextSize(12);
            l1.setTypeface(null, Typeface.BOLD_ITALIC);
            GtotLin1.addView(l1,tosubParms1);
            MainLin.addView(GtotLin1,mainParms2);
            //row.addView(MainLin,rowParm);
            //table.addView(row,params);

            LinearLayout GtotLin2=new LinearLayout(root.getContext());
            GtotLin2.setOrientation(LinearLayout.HORIZONTAL);
            GtotLin2.setBackgroundResource(R.drawable.round_corner1);
            l1 = new TextView(root.getContext());
            l1.setTextColor(Color.BLACK);
            l1.setText("TOTAL ITEM:-"+TitCoun);
            l1.setTextSize(12);
            l1.setTypeface(null, Typeface.BOLD_ITALIC);
            GtotLin2.addView(l1,tosubParms);
            l1 = new TextView(root.getContext());
            l1.setTextColor(Color.BLACK);
            l1.setText("GRAND TOTAL:-"+TgraTot);
            l1.setTextSize(12);
            l1.setTypeface(null, Typeface.BOLD_ITALIC);
            GtotLin2.addView(l1,tosubParms1);
            MainLin.addView(GtotLin2,mainParms2);
            row.addView(MainLin,rowParm);
            table.addView(row,params);
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    /*
    public static ArrayList search()
    {
        ArrayList Big=new ArrayList();
        try
        {
            ArrayList s = new ArrayList();
            Big = new ArrayList();
            String vType="Inside Item Sales";
            String sql=sear();
            Cursor cr = DBManager.database.rawQuery("SELECT " + DatabaseHelper.d0+","+DatabaseHelper.d20+","+DatabaseHelper.d7+","+DatabaseHelper.d9+","+DatabaseHelper.d16+" FROM " + DatabaseHelper.TABLE_NAME3+" where "+DatabaseHelper.d17+"='"+MainActivity.Emp_USERNAME+"' and "+DatabaseHelper.d1+"='"+vType+"' and "+DatabaseHelper.d21+"='true'"+sql+" order by "+DatabaseHelper.d23+" desc,"+DatabaseHelper.d16+" desc", null);
            while(cr.moveToNext())
            {
                s = new ArrayList();
                int vouchId=cr.getInt(0);
                String empN=cr.getString(3);
                s.add(vouchId);
                s.add(cr.getInt(1));
                s.add(cr.getDouble(2));
                s.add(empN);
                s.add(cr.getString(4));
                ArrayList b1 = new ArrayList();
                Cursor cr1 =null;
                if(ch2.isChecked())
                    cr1 = DBManager.database.rawQuery("SELECT " + DatabaseHelper.e3+","+DatabaseHelper.e10+","+DatabaseHelper.e8+","+DatabaseHelper.e7+" FROM " + DatabaseHelper.TABLE_NAME4+" where "+DatabaseHelper.e1+"="+vouchId, null);
                else
                    cr1 = DBManager.database.rawQuery("SELECT SUM(" + DatabaseHelper.e10+") AS qtyOut,SUM("+DatabaseHelper.e8+") AS qtyOut FROM " + DatabaseHelper.TABLE_NAME4+" where "+DatabaseHelper.e1+"="+vouchId, null);
                while(cr1.moveToNext())
                {
                    if(ch2.isChecked())
                    {
                        ArrayList s1 = new ArrayList();
                        s1.add(cr1.getString(0));
                        s1.add(cr1.getDouble(1));
                        s1.add(cr1.getDouble(2));
                        s1.add(cr1.getDouble(3));
                        b1.add(s1);
                    }
                    else
                    {
                        b1.add(cr1.getDouble(0));
                        b1.add(cr1.getDouble(1));
                    }
                }
                s.add(b1);
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void appendRow(TableLayout table) {
        try
        {
            table.removeAllViews();
            ArrayList b=new ArrayList();
            //b=search();
            Thread.sleep(200);
            TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(5, 5, 5, 5);
            LinearLayout.LayoutParams mainParms=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            mainParms.setMargins(5, 5, 5, 0);
            LinearLayout.LayoutParams mainParms2=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            mainParms2.setMargins(10, 5, 10, 5);
            LinearLayout.LayoutParams mainParms1=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            mainParms1.setMargins(5, 0, 5, 0);
            TableRow.LayoutParams rowParm=new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            rowParm.setMargins(5,5,5,5);
            LinearLayout.LayoutParams tosubParms=new LinearLayout.LayoutParams(
                    (int) convDpToPx(130),
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            tosubParms.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams tosubParms1=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            tosubParms1.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams waisubParms=new LinearLayout.LayoutParams(
                    (int) convDpToPx(160),
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            waisubParms.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams waisubParms1=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            waisubParms1.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams itsubParms=new LinearLayout.LayoutParams(
                    (int) convDpToPx(310),
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            itsubParms.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams qtysubParms=new LinearLayout.LayoutParams(
                    (int) convDpToPx(95),
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            qtysubParms.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams qtysubParms1=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            qtysubParms1.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams btsubParms=new LinearLayout.LayoutParams(
                    (int) convDpToPx(150),
                    (int) convDpToPx(30)
            );
            btsubParms.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams btsubParms1=new LinearLayout.LayoutParams(
                    (int) convDpToPx(75),
                    (int) convDpToPx(30)
            );
            btsubParms1.setMargins(5, 0, 5, 0);
            if(b.size()==0)
                table.addView(row1, params);
            int TitCoun=0;
            double TgraTot=0.0,Tqty=0.0,Tunt=0.0;
            TableRow row = new TableRow(root.getContext());
            LinearLayout MainLin=new LinearLayout(root.getContext());
            MainLin.setOrientation(LinearLayout.VERTICAL);
            MainLin.setBackgroundResource(R.drawable.back_white);
            for(int i=0;i<b.size();i++)
            {
                Thread.sleep(20);
                row = new TableRow(root.getContext());
                MainLin=new LinearLayout(root.getContext());
                MainLin.setOrientation(LinearLayout.VERTICAL);
                MainLin.setBackgroundResource(R.drawable.back_white);
                ArrayList s= (ArrayList) b.get(i);
                String tolTx=s.get(0).toString();
                LinearLayout totLin=new LinearLayout(root.getContext());
                totLin.setOrientation(LinearLayout.HORIZONTAL);
                totLin.setBackgroundResource(R.drawable.round_corner3);
                LinearLayout waiTLin=new LinearLayout(root.getContext());
                waiTLin.setOrientation(LinearLayout.HORIZONTAL);
                int itCoun=Integer.parseInt(s.get(1).toString());
                double graTot=Double.parseDouble(s.get(2).toString());
                TitCoun=TitCoun+itCoun;
                TgraTot=TgraTot+graTot;
                String wai=s.get(3).toString();
                String timS=s.get(4).toString();
                TextView l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText("Total Item:-"+itCoun);
                l1.setTextSize(14);
                l1.setTypeface(null, Typeface.BOLD_ITALIC);
                totLin.addView(l1,tosubParms);
                l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText("Total Price:-"+graTot);
                l1.setTextSize(14);
                l1.setTypeface(null, Typeface.BOLD_ITALIC);
                totLin.addView(l1,tosubParms1);

                l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText("Waiter:-"+wai);
                l1.setTextSize(10);
                waiTLin.addView(l1,waisubParms);
                l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText("Time:-"+timS);
                l1.setTextSize(10);
                waiTLin.addView(l1,waisubParms1);
                ArrayList s1 = (ArrayList) s.get(5);
                if(ch2.isChecked())
                {
                    for (int j = 0; j < s1.size(); j++) {
                        ArrayList s2 = (ArrayList) s1.get(j);
                        String val = s2.get(0).toString();
                        double unt = Double.parseDouble(s2.get(1).toString());
                        double qty = Double.parseDouble(s2.get(2).toString());
                        double pri = Double.parseDouble(s2.get(3).toString());
                        Tqty=Tqty+qty;
                        Tunt=Tunt+unt;
                        LinearLayout itLin = new LinearLayout(root.getContext());
                        LinearLayout qtyLin = new LinearLayout(root.getContext());
                        itLin.setOrientation(LinearLayout.HORIZONTAL);
                        qtyLin.setOrientation(LinearLayout.HORIZONTAL);
                        l1 = new TextView(root.getContext());
                        l1.setTextColor(Color.BLACK);
                        l1.setText("Item Name:-" + val);
                        l1.setTextSize(14);
                        l1.setTypeface(null, Typeface.BOLD);
                        itLin.addView(l1, itsubParms);
                        l1 = new TextView(root.getContext());
                        l1.setTextColor(Color.BLACK);
                        l1.setText("Unit:-" + unt);
                        l1.setTextSize(14);
                        qtyLin.addView(l1, qtysubParms);
                        l1 = new TextView(root.getContext());
                        l1.setTextColor(Color.BLACK);
                        l1.setText("Qty:-" + qty);
                        l1.setTextSize(14);
                        qtyLin.addView(l1, qtysubParms);
                        l1 = new TextView(root.getContext());
                        l1.setTextColor(Color.BLACK);
                        l1.setText("Price:-" + pri);
                        l1.setTextSize(14);
                        qtyLin.addView(l1, qtysubParms1);
                        MainLin.addView(itLin, mainParms);
                        MainLin.addView(qtyLin, mainParms1);
                    }
                }
                else
                {
                    double unt = Double.parseDouble(s1.get(0).toString());
                    double qty = Double.parseDouble(s1.get(1).toString());
                    Tqty=Tqty+qty;
                    Tunt=Tunt+unt;
                    LinearLayout totLin1=new LinearLayout(root.getContext());
                    totLin1.setOrientation(LinearLayout.HORIZONTAL);
                    totLin1.setBackgroundResource(R.drawable.round_corner3);
                    l1 = new TextView(root.getContext());
                    l1.setTextColor(Color.BLACK);
                    l1.setText("Total Unit:-"+unt);
                    l1.setTextSize(14);
                    l1.setTypeface(null, Typeface.BOLD_ITALIC);
                    totLin1.addView(l1,tosubParms);
                    l1 = new TextView(root.getContext());
                    l1.setTextColor(Color.BLACK);
                    l1.setText("Total Qty:-"+qty);
                    l1.setTextSize(14);
                    l1.setTypeface(null, Typeface.BOLD_ITALIC);
                    totLin1.addView(l1,tosubParms1);

                    MainLin.addView(totLin1,mainParms);
                }
                MainLin.addView(totLin,mainParms);
                MainLin.addView(waiTLin,mainParms2);
                row.addView(MainLin,rowParm);
                table.addView(row,params);
            }
            row = new TableRow(root.getContext());
            MainLin=new LinearLayout(root.getContext());
            MainLin.setOrientation(LinearLayout.VERTICAL);
            MainLin.setBackgroundResource(R.drawable.back_white);
            LinearLayout GtotLin1=new LinearLayout(root.getContext());
            GtotLin1.setOrientation(LinearLayout.HORIZONTAL);
            GtotLin1.setBackgroundResource(R.drawable.round_corner1);
            TextView l1 = new TextView(root.getContext());
            l1.setTextColor(Color.BLACK);
            l1.setText("TOTAL UNIT:-"+Tunt);
            l1.setTextSize(12);
            l1.setTypeface(null, Typeface.BOLD_ITALIC);
            GtotLin1.addView(l1,tosubParms);
            l1 = new TextView(root.getContext());
            l1.setTextColor(Color.BLACK);
            l1.setText("TOTAL QTY:-"+Tqty);
            l1.setTextSize(12);
            l1.setTypeface(null, Typeface.BOLD_ITALIC);
            GtotLin1.addView(l1,tosubParms1);
            MainLin.addView(GtotLin1,mainParms2);
            //row.addView(MainLin,rowParm);
            //table.addView(row,params);

            LinearLayout GtotLin2=new LinearLayout(root.getContext());
            GtotLin2.setOrientation(LinearLayout.HORIZONTAL);
            GtotLin2.setBackgroundResource(R.drawable.round_corner1);
            l1 = new TextView(root.getContext());
            l1.setTextColor(Color.BLACK);
            l1.setText("TOTAL ITEM:-"+TitCoun);
            l1.setTextSize(12);
            l1.setTypeface(null, Typeface.BOLD_ITALIC);
            GtotLin2.addView(l1,tosubParms);
            l1 = new TextView(root.getContext());
            l1.setTextColor(Color.BLACK);
            l1.setText("GRAND TOTAL:-"+TgraTot);
            l1.setTextSize(12);
            l1.setTypeface(null, Typeface.BOLD_ITALIC);
            GtotLin2.addView(l1,tosubParms1);
            MainLin.addView(GtotLin2,mainParms2);
            row.addView(MainLin,rowParm);
            table.addView(row,params);
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private static float convDpToPx(float dp)
    {
        DisplayMetrics mat= Resources.getSystem().getDisplayMetrics();
        float px=dp*(mat.densityDpi/160f);
        return Math.round(px);
    }
}
