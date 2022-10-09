package org.o7planning.agelegecafe.ui.sales_invoice;

import static android.widget.TableLayout.OnClickListener;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;

import org.o7planning.agelegecafe.Add_Item;
import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.DatabaseHelper;
import org.o7planning.agelegecafe.GPSTracker;
import org.o7planning.agelegecafe.MainActivity;
import org.o7planning.agelegecafe.MainActivity1;
import org.o7planning.agelegecafe.R;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Sales_InvoiceFragment extends Fragment {

    private Sales_InvoiceViewModel galleryViewModel;
    private static EditText ee22=null;
    private static View root=null;
    private static Button b4=null,b1=null,b2=null,b3=null;
    private static Button bt=null,bt2=null,bt3=null;
    //private static TableLayout table1 =null,table2=null;
    private static EditText ed1=null,ed2=null,ed3=null,ed4=null,e1=null;
    private static LinearLayout l4=null;
    private static RadioButton rd1=null,rd2=null;
    private static RadioButton r1=null,r2=null,r3=null,r4=null,r5=null,r6=null;
    private static AlertDialog alert=null;
    private static TableRow itrow=null,itrow1=null;
    private static GPSTracker gps;
    private static ArrayList store=new ArrayList();
    private static ArrayList store2=new ArrayList();
    private static Spinner spin=null,spin2=null;
    private static String userName="";
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static DBManager dbManager;
    private static String Item_Id="";
    private static String Item_Id2="";
    private static OnClickListener txClick=null;
    private static OnClickListener txClick2=null;
    private static ArrayList radGroup=new ArrayList();
    private static RelativeLayout reLa1=null,reLa2=null;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static int radCh=0,itView=0;
    private static WebView myWebView;
    private static TableLayout table=null;
    private static ArrayList LineItem=new ArrayList();
    private static RadioButton rad1=null,rad2=null;
    private static TextView tx5=null,t1=null,t2=null,t3=null,t4=null,t5=null,t6=null,t7=null,t8=null,t9=null,t10=null,t11=null,t12=null,t13=null,t15=null,t16=null,t17=null,t18=null,t19=null;
    private static LinearLayout li=null,li1=null,li2=null,li3=null,li4=null,li5=null,li6=null,Itli7=null;
    private static TextInputLayout t14=null;
    public Sales_InvoiceFragment()
    {

    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(Sales_InvoiceViewModel.class);
        root = inflater.inflate(R.layout.sales_order, container, false);

        try
        {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        ed1=root.findViewById(R.id.sal_or_e1);
        ed2=root.findViewById(R.id.sal_or_e2);
        ed3=root.findViewById(R.id.sal_or_e3);
        t1=root.findViewById(R.id.sal_or_tx1);
        t2=root.findViewById(R.id.sal_or_tx2);
        t3=root.findViewById(R.id.sal_or_tx3);
        t4=root.findViewById(R.id.sal_or_tx4);
        t5=root.findViewById(R.id.sal_or_tx5);
        t6=root.findViewById(R.id.sal_or_tx6);
        t7=root.findViewById(R.id.sal_or_tx7);
        t8=root.findViewById(R.id.sal_or_tx8);
        t9=root.findViewById(R.id.sal_or_tx9);
        t10=root.findViewById(R.id.sal_or_tx10);
        t11=root.findViewById(R.id.sal_or_tx11);
        t12=root.findViewById(R.id.sal_or_tx12);
        t13=root.findViewById(R.id.sal_or_tx13);
        t14=root.findViewById(R.id.sal_or_ti2);
        t15=root.findViewById(R.id.sal_or_b1);
        t16=root.findViewById(R.id.sal_or_b2);
        t17=root.findViewById(R.id.sal_or_tx14);
        t18=root.findViewById(R.id.sal_or_tx15);
        t19=root.findViewById(R.id.sal_or_tx16);
        tx5=root.findViewById(R.id.pay_wa_tx5);
        rad1=root.findViewById(R.id.ordr1);
        rad2=root.findViewById(R.id.ordr2);
        li=root.findViewById(R.id.sal_or_li);
        li1=root.findViewById(R.id.sal_or_li1);
        li2=root.findViewById(R.id.sal_or_li2);
        li3=root.findViewById(R.id.sal_or_li3);
        li4=root.findViewById(R.id.sal_or_li4);
        li5=root.findViewById(R.id.sal_or_li5);
        li6=root.findViewById(R.id.sal_or_li6);
        Itli7=root.findViewById(R.id.sal_or_li7);
        try
        {
            Cursor cr=dbManager.fetch1();
            userName=cr.getString(0);
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        LineItem=new ArrayList();
        t15.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Save();
            }
        });
        t16.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Save();
            }
        });
        itView=0;
        t4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int cc=li.getChildCount();
                if(cc>1)
                {
                    li.removeView(Itli7);
                }
                else
                {
                    li.addView(Itli7);
                }
            }
        });
        t5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int cc=li.getChildCount();
                if(cc>1)
                {
                    li.removeView(Itli7);
                }
                else
                {
                    li.addView(Itli7);
                }
            }
        });
        t6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    int val=li4.getChildCount();
                    if(val==2)
                    {
                        li4.removeView(li5);
                    }
                    else if(val==1)
                    {
                        li4.addView(li5);
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        t7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    int val=li4.getChildCount();
                    if(val==2)
                    {
                        li4.removeView(li5);
                    }
                    else if(val==1)
                    {
                        li4.addView(li5);
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        t14.setEndIconOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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

                                    ed2.setText(year + "-" + (monthOfYear + 1) + "-" +dayOfMonth );
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
        LayoutInflater inflater1 = (LayoutInflater)root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View pop = inflater1.inflate(R.layout.item_list_popup, null, false);
        int wid=pop.getMeasuredWidth();
        int hig=pop.getHeight();
        e1=pop.findViewById(R.id.it_pop_e1);
        table=pop.findViewById(R.id.it_pop_menu);
        ed2.setText(MainActivity.Main_WORK_DATE);
        li6.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try
                {
                    //PopupWindow pw = new PopupWindow(pop, root.getWidth(),(root.getHeight()-100), true);
                    //pw.showAsDropDown(root, 10, 5);
                    //appendRow1(table,e1.getText().toString());
                    Add_Item.Add_Item_Choice=1;
                    Intent notificationIntent = new Intent(root.getContext(), Add_Item.class);
                    startActivity(notificationIntent);
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void afterTextChanged(Editable s) {
                String ed=e1.getText().toString();
                appendRow1(table,ed);
            }
        });
        t1.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try
                {
                    //PopupWindow pw = new PopupWindow(pop, root.getWidth(), (root.getHeight()-150), true);
                    //pw.showAsDropDown(root, 10, 5);
                    //appendRow1(table,e1.getText().toString());
                    Add_Item.Add_Item_Choice=1;
                    Intent notificationIntent = new Intent(root.getContext(), Add_Item.class);
                    startActivity(notificationIntent);
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        t2.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try
                {
                    //PopupWindow pw = new PopupWindow(pop, root.getWidth(), (root.getHeight()-50), true);
                    //pw.showAsDropDown(root, 10, 5);
                    //appendRow1(table,e1.getText().toString());
                    Add_Item.Add_Item_Choice=1;
                    Intent notificationIntent = new Intent(root.getContext(), Add_Item.class);
                    startActivity(notificationIntent);
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        ee22=new EditText(root.getContext());
        t3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    MainActivity.QrCho=1;
                    MainActivity.scanQr(ee22);
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        try
        {
            li.removeAllViews();
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        store=new ArrayList();
        try {
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            double debit=0.0,credit=0.0,totBal=0.0;
            ResultSet rr1=st1.executeQuery("select SUM(debit) AS debit,SUM(credit) AS credit from ledger where vouchStatus='true' and userName='"+MainActivity.Emp_USERNAME+"'");
            while (rr1.next())
            {
                debit=rr1.getDouble("debit");
                credit=rr1.getDouble("credit");
            }
            totBal=debit-credit;
            if(totBal>0)
                tx5.setTextColor(Color.parseColor("#00FF00"));
            else
                tx5.setTextColor(Color.parseColor("#FF0000"));
            tx5.setText(df.format(totBal));
            store.add("Select One");
            ResultSet rs=st.executeQuery("select empName,userName from userAccount where duety='Cashier'");
            while(rs.next())
            {
                store.add(rs.getString("userName")+"("+rs.getString("empName")+")");
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        store2=new ArrayList();
        try {
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            store2.add("Select One");
            int tbGr=0;
            ResultSet rs=st.executeQuery("select date, userName, grId, grName from assignGroup where date='"+MainActivity.Main_WORK_DATE+"' and userName='"+MainActivity.Emp_USERNAME+"'");
            while(rs.next())
            {
                tbGr=rs.getInt("grId");
            }
            ResultSet rs1=st.executeQuery("select tableId from tableAssign where grId="+tbGr);
            while(rs1.next())
            {
                store2.add(rs1.getString("tableId"));
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        spin = root.findViewById(R.id.salsp);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, store);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin2 = root.findViewById(R.id.salsp2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, store2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(adapter2);
        if(store.size()>=2)
            spin.setSelection(1);
        t8.setText("Total Disc:0.0");
        t9.setText("Total Qty:0.0");
        t10.setText("Total Tax:0.0");
        t11.setText("Sub Total:0.0");
        ed1.setText(ItemID("Sales Order"));
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tabId=spin2.getSelectedItem().toString();
                if(tabId.equalsIgnoreCase("Select One"))
                {

                }
                else
                {
                    String orderId=TableOrdID(tabId);
                    try
                    {
                        Statement st = MainActivity.connection.createStatement();
                        t18.setText(orderId);
                        double bal=0.0;
                        ResultSet rr=st.executeQuery("select * from tableOrderDetail where orderId='"+orderId+"'");
                        while (rr.next())
                        {
                            bal=bal+rr.getDouble("balanace");
                        }
                        t19.setText(df.format(bal));
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        rad2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String tabId=spin2.getSelectedItem().toString();
                if(tabId.equalsIgnoreCase("Select One"))
                {

                }
                else
                {
                    String orderId1=t18.getText().toString();
                    AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                    builder.setTitle(R.string.app_name);
                    builder.setIcon(R.mipmap.glance_logo);
                    builder.setMessage("Are You Sure To Close This Table Balance?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                            try
                            {
                                Statement st1=MainActivity.connection.createStatement();
                                st1.execute("update tableOrder set isClose='true' where id='"+orderId1+"'");
                                String orderId=TableOrdID(tabId);
                                try
                                {
                                    Statement st = MainActivity.connection.createStatement();
                                    t18.setText(orderId);
                                    double bal=0.0;
                                    ResultSet rr=st.executeQuery("select * from tableOrderDetail where orderId='"+orderId+"'");
                                    while (rr.next())
                                    {
                                        bal=bal+rr.getDouble("balance");
                                    }
                                    t19.setText(df.format(bal));
                                }
                                catch (Exception ex)
                                {
                                    Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
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
            }
        });
        return root;
    }
    private void Save()
    {
        try
        {
            String s81 = spin.getSelectedItem().toString();
            String s91 = spin2.getSelectedItem().toString();
            String s7=t13.getText().toString();
            if(s81.equalsIgnoreCase("Select One")||s91.equalsIgnoreCase("Select One"))
            {
                if(s91.equalsIgnoreCase("Select One"))
                {
                    Toast.makeText(root.getContext(), "Please Select Table", Toast.LENGTH_LONG).show();
                }
                if(s81.equalsIgnoreCase("Select One"))
                {
                    Toast.makeText(root.getContext(), "Please Select Cashier", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Statement stat1=MainActivity.connection.createStatement();
                double totBa=Double.parseDouble(s7);
                double debit=0.0,credit=0.0,totBal=0.0;
                ResultSet rr1=stat1.executeQuery("select SUM(debit) AS debit,SUM(credit) AS credit from ledger where vouchStatus='true' and userName='"+MainActivity.Emp_USERNAME+"' and workDate='"+MainActivity.Main_WORK_DATE+"'");
                while (rr1.next())
                {
                    debit=rr1.getDouble("debit");
                    credit=rr1.getDouble("credit");
                }
                totBal=debit-credit;
                if(totBa>totBal)
                {
                    Toast.makeText(root.getContext(), "You Haven't Enough Balance To Order.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String voucherId = ed1.getText().toString();
                    String s1="";
                    String s5="";
                    String s10="0.00";
                    String s11="0.00";
                    String s22="";
                    s1 = "Sales Order";
                    s5="true";
                    s10="0.00";
                    s11="0.00";
                    s22="";
                    String s2="";
                    String s3="";
                    String s4="";
                    String s6=t12.getText().toString();
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
                    s9=s91;
                    String s12="Cash";
                    String s13="";
                    if(!s12.equalsIgnoreCase("Cash"))
                    {
                        s13="";
                    }
                    String s14=ed3.getText().toString();
                    String s15=t17.getText().toString();
                    Date d = new Date();
                    int yr = d.getYear() + 1900;
                    int mn = d.getMonth() + 1;
                    int dt = d.getDate();
                    int hr = d.getHours();
                    int min = d.getMinutes();
                    int sec = d.getSeconds();
                    String s16 = yr + "-" + mn + "-" + dt + " " + hr + ":" + min + ":" + sec;
                    String s17=MainActivity.Emp_USERNAME;
                    double s18=0.00;
                    double s19=0.00;
                    double s199=0.00;
                    gps = new GPSTracker(root.getContext());
                    if(gps.canGetLocation())
                    {
                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();
                        double altitude = gps.getAltitude();
                        s18=latitude;
                        s19=longitude;
                        s199=altitude;
                    }
                    else
                    {
                        gps.showSettingsAlert();
                    }
                    int s20=LineItem.size();
                    String s21="true";
                    String s23 = yr + "-" + mn + "-" + dt;
                    String s24="Ordered";
                    /*int e1=0;
                    Cursor cursor1 = DBManager.database.rawQuery("SELECT " + DatabaseHelper.d0 + " FROM " + DatabaseHelper.TABLE_NAME3+" order by "+DatabaseHelper.d0, null);
                    if(cursor1.moveToFirst())
                    {
                        do{
                            e1=cursor1.getInt(0);
                        }while (cursor1.moveToNext());
                    }
                    e1=e1+1;*/
                    ArrayList h=new ArrayList();
                    h.add(s1);
                    h.add(s2);
                    h.add(s3);
                    h.add(s4);
                    h.add(s5);
                    h.add(s6);
                    h.add(s7);
                    h.add(s8);
                    h.add(s9);
                    h.add(s10);
                    h.add(s11);
                    h.add(s12);
                    h.add(s13);
                    h.add(s14);
                    h.add(s15);
                    h.add(s16);
                    h.add(s17);
                    h.add(s18);
                    h.add(s19);
                    h.add(s20);
                    h.add(s21);
                    h.add(s22);
                    h.add(s23);
                    h.add(s24);
                    ArrayList b=new ArrayList();
                    ArrayList s=new ArrayList();
                    for(int i=0;i<LineItem.size();i++)
                    {
                        ArrayList b1= (ArrayList) LineItem.get(i);
                        String itid=b1.get(0).toString();
                        String itNm=b1.get(1).toString();
                        double qt=Double.parseDouble(b1.get(2).toString());
                        double pri=Double.parseDouble(b1.get(3).toString());
                        double toPri=qt*pri;
                        s=new ArrayList();
                        s.add(voucherId);
                        s.add(itid);
                        s.add(itNm);
                        s.add("Main Store");
                        s.add("0.00");
                        s.add(df.format(pri));
                        s.add(df.format(toPri));
                        s.add(df.format(qt));
                        s.add(df.format(qt));
                        s.add("true");
                        b.add(s);
                    }
                    if(s7.equals("0.00"))
                    {
                        Toast.makeText(root.getContext(), "Fill Empty Fields.", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        if(MainActivity.Connection_type.equalsIgnoreCase("LAN(PC)"))
                        {
                            //dbManager.insert3(h,b,"false");
                            int ckc=0;
                            /*
                            for(int i=1;i<LineItem.size();i++)
                            {
                                ArrayList b1= (ArrayList) LineItem.get(i);
                                String itid=b.get(0).toString();
                                String itNm=b.get(1).toString();
                                double qt=Double.parseDouble(b.get(2).toString());
                                TextView txx1 = (TextView) child.getChildAt(1);
                                TextView txx7 = (TextView) child.getChildAt(7);
                                TextView txx4 = (TextView) child.getChildAt(4);
                                Cursor cr = dbManager.fetch45(txx7.getText().toString(),txx1.getText().toString());
                                double count = 0;
                                do {
                                    count=cr.getDouble(0);
                                } while (cr.moveToNext());
                                double qty=Double.parseDouble(txx4.getText().toString());
                                double qty1=count-qty;
                                int ii=dbManager.update10(txx1.getText().toString(),qty1,txx7.getText().toString());
                                if(ii==1)
                                    ckc=ckc+1;
                            }*/
                            MainActivity.connection = MainActivity.Conn();
                            Statement st = MainActivity.connection.createStatement();
                            Statement st1 = MainActivity.connection.createStatement();
                            Statement st2 = MainActivity.connection.createStatement();
                            Statement st3 = MainActivity.connection.createStatement();
                            s1 = h.get(0).toString();
                            s2 = h.get(1).toString();
                            s3 = h.get(2).toString();
                            s4 = h.get(3).toString();
                            s5 = h.get(4).toString();
                            double ss6 = Double.parseDouble(h.get(5).toString());
                            double ss7 = Double.parseDouble(h.get(6).toString());
                            s8 = h.get(7).toString();
                            s9 = h.get(8).toString();
                            double ss10 = Double.parseDouble(h.get(9).toString());
                            double ss11 = Double.parseDouble(h.get(10).toString());
                            s12 = h.get(11).toString();
                            s13 = h.get(12).toString();
                            s14 = h.get(13).toString();
                            s15 = h.get(14).toString();
                            s16 = h.get(15).toString();
                            s17 = h.get(16).toString();
                            s18 = Double.parseDouble(h.get(17).toString());
                            s19 = Double.parseDouble(h.get(18).toString());
                            s20 = Integer.parseInt(h.get(19).toString());
                            s21 = h.get(20).toString();
                            s22 = h.get(21).toString();
                            s23 = h.get(22).toString();
                            s24 = h.get(23).toString();
                            String sqsq="insert into voucher1( voucherId, voucherType, name, mobile, tin, isCash, subTotal, total, cashier,[table],waiter, recieve, bal_due, pay_type, ref_no, description, price_word, timeStamp, userN, dirLat, dirLon, dirAlt, itemCount, status,bal_due_date, date, order_status, id_count,v_id,sync) " +
                                    "values('" + voucherId + "','" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "','" + s5 + "'," + ss6 + "," + ss7 + ",'" + s8 + "','" + s9 +"','"+MainActivity.Emp_USERNAME+ "'," + ss10 + "," + ss11 + ",'" + s12 + "','" + s13 + "','" + s14 + "','" + s15 + "','" + s16 + "','" + s17 + "'," + s18 + "," + s19 +","+s199+ "," + s20 + ",'" + s21 + "','" + s22 + "','" + s23 + "','" + s24 + "'," + it_Count +","+it_Count+ ",'true')";
                            //Toast.makeText(root.getContext(), sqsq, Toast.LENGTH_LONG).show();
                            st.execute(sqsq);
                            for (int i = 0; i < b.size(); i++) {
                                s = (ArrayList) b.get(i);
                                String c1 = s.get(0).toString();
                                String c2 = s.get(1).toString();
                                String c3 = s.get(2).toString();
                                String c4 = s.get(3).toString();
                                double c5 = Double.parseDouble(s.get(4).toString());
                                double c6 = Double.parseDouble(s.get(5).toString());
                                double c7 = Double.parseDouble(s.get(6).toString());
                                double c8 = Double.parseDouble(s.get(7).toString());
                                String c9 = "true";
                                double c10 = Double.parseDouble(s.get(8).toString());
                                st1.execute("insert into lineItem1(voucherId, itemId, itemName, store, qtyIn, u_price, tot_price, qtyOut, status, unit,v_id,sync) " +
                                        "values('" + voucherId + "','" + c2 + "',N'" + c3 + "','" + c4 + "'," + c5 + "," + c6 + "," + c7 + "," + c8 + ",'" + c9 + "'," + c10 +","+it_Count+ ",'true')");
                                //dbManager.updateLineIt(e1,c2);
                            }
                            String Vpurpose="Daily Waiter Account Establishment";
                            String isjour1="true";
                            String Vbackward="no";
                            String Vforward="no";
                            String sql="insert into voucher values('"+voucherId+"','Sales Order',"+ss6+",0,"+ss7+",'"+s1+"','"+MainActivity.Main_WORK_DATE
                                    +"','Daily Waiter Account Establishment','true','false','false','false','"+MainActivity.Emp_USERNAME+"','no','no',"
                                    +s20+",'NUL',"+it_Count+",'"+s16+"','"+s15+"','"+PRE_FIX+"','Prepared','false','false')";
                            //dbManager.updateVouch(e1);
                            st1.execute(sql);
                            String tbOrd=t18.getText().toString();
                            if(rad1.isChecked())
                            {
                                int ckck=0;
                                ResultSet rs1=st2.executeQuery("select * from tableOrder where id='"+tbOrd+"'");
                                while(rs1.next())
                                {
                                    ckck=1;
                                }
                                if(ckck==0)
                                {
                                    st3.execute("insert into tableOrder(id, workDate, waiter, tableId, noOrder, isClose, status) " +
                                            "values('"+tbOrd+"','"+MainActivity.Main_WORK_DATE+"','"+MainActivity.Emp_USERNAME+"','"+s9+"',"+tab_Ord_Count+",'false','active')");
                                }
                                st3.execute("insert into tableOrderDetail(orderId, voucherId, balanace, status) " +
                                        "values('"+tbOrd+"','"+voucherId+"',"+ss7+",'Active')");
                            }
                            else if(rad2.isChecked())
                            {
                                st3.execute("insert into tableOrder(id, workDate, waiter, tableId, noOrder, isClose, status) " +
                                        "values('"+tbOrd+"','"+MainActivity.Main_WORK_DATE+"','"+MainActivity.Emp_USERNAME+"','"+s9+"',"+tab_Ord_Count+",'false','active')");
                                st3.execute("insert into tableOrderDetail(orderId, voucherId, balanace, status) " +
                                        "values('"+tbOrd+"','"+voucherId+"',"+ss7+",'Active')");
                            }
                            Toast.makeText(root.getContext(), "Success", Toast.LENGTH_LONG).show();
                            ed1.setText(ItemID("Sales Order"));
                            li.removeAllViews();
                            t12.setText("0.00");
                            t13.setText("0.00");
                            t17.setText("");
                            ed3.setText("");
                            spin2.setSelection(0);
                            t8.setText("Total Disc:0.0");
                            t9.setText("Total Qty:0.0");
                            t10.setText("Total Tax:0.0");
                            t11.setText("Sub Total:0.0");
                            LineItem=new ArrayList();
                        }
                        printWebView(h,voucherId);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList search1(String str)
    {
        ArrayList Big=new ArrayList();
        try
        {
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            Statement st2 = MainActivity.connection.createStatement();
            Big = new ArrayList();
            ResultSet cr =st.executeQuery("select itemId,itemName,price from itemList where itemName LIKE '%"+str+"%'");
            while(cr.next())
            {
                String itid=cr.getString("itemId");
                String itNm=cr.getString("itemName");
                double bal=0.0,pri=0.0;
                pri=cr.getDouble("price");
                ArrayList s = new ArrayList();
                s.add(itid);
                s.add(itNm);
                s.add(bal);
                s.add(pri);
                Big.add(s);
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
            ArrayList b=new ArrayList();
            b=search1(str);
            Thread.sleep(200);
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
            LinearLayout.LayoutParams tosubParms=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            tosubParms.setMargins(5, 5, 5, 0);
            LinearLayout.LayoutParams tosubParms1=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
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
            //table.addView(row, params);
            TableRow row = new TableRow(root.getContext());
            row.setWeightSum(1.0F);
            LinearLayout MainLin=new LinearLayout(root.getContext());
            MainLin.setOrientation(LinearLayout.HORIZONTAL);
            MainLin.setBackgroundResource(R.drawable.border_round1);
            MainLin.setWeightSum(1.0F);
            for(int i=0;i<b.size();i++)
            {
                Thread.sleep(20);
                row = new TableRow(root.getContext());
                row.setWeightSum(1.0F);
                MainLin=new LinearLayout(root.getContext());
                MainLin.setOrientation(LinearLayout.HORIZONTAL);
                MainLin.setBackgroundResource(R.drawable.border_round1);
                MainLin.setWeightSum(1);
                ArrayList s= (ArrayList) b.get(i);
                String itid=s.get(0).toString();
                String itnm=s.get(1).toString();
                String itbal=s.get(2).toString();
                String itpri=s.get(3).toString();
                LinearLayout totLin=new LinearLayout(root.getContext());
                totLin.setOrientation(LinearLayout.VERTICAL);

                TextView l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText(itnm);
                l1.setTextSize(14);
                l1.setTypeface(null, Typeface.BOLD_ITALIC);
                l1.setTooltipText(itid);
                //l1.setOnClickListener(txClick2);
                totLin.addView(l1,tosubParms);
                l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setTypeface(null, Typeface.ITALIC);
                l1.setText(itid);
                l1.setTextSize(14);
                l1.setTooltipText(itid);
                //l1.setOnClickListener(txClick2);
                totLin.addView(l1,tosubParms1);
                totLin.setTooltipText(itid);
                //totLin.setOnClickListener(txClick2);

                LinearLayout totLin1=new LinearLayout(root.getContext());
                totLin1.setOrientation(LinearLayout.VERTICAL);

                totLin1.setTooltipText(itid);
                //totLin1.setOnClickListener(txClick2);
                l1 = new TextView(root.getContext());
                l1.setTooltipText(itid);
                //l1.setOnClickListener(txClick2);
                l1.setTextColor(Color.BLACK);
                l1.setText("Price:-"+itpri);
                l1.setTextSize(14);
                totLin1.addView(l1,tosubParms);
                l1 = new TextView(root.getContext());
                l1.setTooltipText(itid);
                //l1.setOnClickListener(txClick2);
                l1.setTextColor(Color.BLACK);
                l1.setText("In Stock:-"+itbal);
                l1.setTextSize(14);
                totLin1.addView(l1,tosubParms1);

                MainLin.setTooltipText(itid);
                //MainLin.setOnClickListener(txClick2);
                MainLin.addView(totLin,mainParms);
                MainLin.addView(totLin1,mainParms2);
                row.addView(MainLin,rowParm);
                table.addView(row,params);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void itemCode(String cod)
    {
        try
        {
            try {
                MainActivity.connection = MainActivity.Conn();
            } catch (Exception ex) {
                Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            ResultSet r=st.executeQuery("select * from Item where itemCode='"+cod+"'");
            String itNm="";
            String itid="";
            while (r.next())
            {
                itNm=r.getString("itemName");
                itid=r.getString("itemId");
            }
            String qt="";
            if(qt.equals(""))
                qt="1.0";
            double qty=Double.parseDouble(qt);
            String pr="";
            double pri=0.0;
            if(pr.equals(""))
            {
                ResultSet cr1=st1.executeQuery("select * from itemBalance where refItemId='"+itid+"'");
                while (cr1.next())
                {
                    pri=cr1.getDouble("price");
                }
            }
            else
            {
                pri=Double.parseDouble(pr);
            }
            ArrayList b=new ArrayList();
            b.add(itid);
            b.add(itNm);
            b.add(qty);
            b.add(pri);
            //onBackPressed();
            Sales_InvoiceFragment.appendRow1(b);
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void appendRow1(ArrayList b) {
        LineItem.add(b);
        try
        {
            LinearLayout.LayoutParams txParm1=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            txParm1.setMargins(0, 10, 0, 0);
            LinearLayout.LayoutParams txParm=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            txParm.setMargins(5, 5, 0, 0);
            LinearLayout.LayoutParams txParm2=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            txParm2.setMargins(5, 0, 0, 0);
            LinearLayout.LayoutParams txParm3=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            txParm3.setMargins(0, 0, 5, 0);
            LinearLayout.LayoutParams txParm4=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            txParm4.setMargins(0, 5, 5, 0);
            String itid=b.get(0).toString();
            String itNm=b.get(1).toString();
            double qt=Double.parseDouble(b.get(2).toString());
            double pri=Double.parseDouble(b.get(3).toString());
            double toPri=qt*pri;
            String s9=t9.getText().toString();
            int indx=0;
            for(int i=0;i<s9.length();i++)
            {
                if(s9.charAt(i)==':')
                {
                    indx=i;
                    break;
                }
            }
            String s99=s9.substring(indx+1,s9.length());
            double qtS9=Double.parseDouble(s99);
            qtS9=qtS9+qt;
            t9.setText("Total Qty:"+df.format(qtS9));

            String s11=t11.getText().toString();
            indx=0;
            for(int i=0;i<s11.length();i++)
            {
                if(s11.charAt(i)==':')
                {
                    indx=i;
                    break;
                }
            }
            String s111=s11.substring(indx+1,s11.length());
            double qtS11=Double.parseDouble(s111);
            qtS11=qtS11+toPri;
            t11.setText("Sub Total:"+df.format(qtS11));
            t12.setText(df.format(qtS11));
            t13.setText(df.format(qtS11));
            String price_word="";
            double dddss=qtS11;
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
            t17.setText(price_word);
            li.removeAllViews();
            ArrayList ItLin=new ArrayList();
            int liCount=0;
            for(int i=0;i<(Itli7.getChildCount()-1);i++)
            {
                LinearLayout ll= (LinearLayout) Itli7.getChildAt(i);
                ItLin.add(ll);
                liCount=liCount+1;
            }
            liCount=liCount+1;
            Itli7.removeAllViews();
            for(int i=0;i<ItLin.size();i++)
            {
                LinearLayout ll= (LinearLayout) ItLin.get(i);
                Itli7.addView(ll,txParm1);
            }
            LinearLayout.LayoutParams lin1=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            lin1.setMargins(0, 0, 0, 5);
            LinearLayout.LayoutParams lin2=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            lin2.setMargins(0, 5, 0, 0);
            LinearLayout.LayoutParams sublin1=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            sublin1.setMargins(10, 10, 0, 5);
            sublin1.weight=0.5F;
            LinearLayout.LayoutParams sublin2=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            sublin2.setMargins(0, 10, 10, 5);
            sublin2.weight=0.5F;
            li.addView(li1,lin1);

            LinearLayout MainLin=new LinearLayout(root.getContext());
            MainLin.setOrientation(LinearLayout.HORIZONTAL);
            MainLin.setBackgroundResource(R.drawable.back_gray);
            MainLin.setWeightSum(1.0F);

            LinearLayout subLin1=new LinearLayout(root.getContext());
            subLin1.setOrientation(LinearLayout.VERTICAL);
            subLin1.setGravity(Gravity.CENTER|Gravity.LEFT);

            TextView l1 = new TextView(root.getContext());
            l1.setTextColor(Color.BLACK);
            l1.setText(liCount+"."+itNm);
            l1.setTextSize(13);
            l1.setTypeface(null, Typeface.BOLD);
            l1.setTooltipText(itid);
            //l1.setOnClickListener(txClick2);
            subLin1.addView(l1,txParm2);
            l1 = new TextView(root.getContext());
            l1.setTextColor(Color.parseColor("#403F3F"));
            l1.setText("Item Subtotal");
            l1.setTextSize(13);
            l1.setTooltipText(itid);
            //l1.setOnClickListener(txClick2);
            subLin1.setTooltipText(itid);
            subLin1.addView(l1,txParm);
            MainLin.addView(subLin1,sublin1);

            LinearLayout subLin2=new LinearLayout(root.getContext());
            subLin2.setOrientation(LinearLayout.VERTICAL);
            subLin2.setGravity(Gravity.CENTER|Gravity.RIGHT);
            subLin2.setTooltipText(itid);
            l1 = new TextView(root.getContext());
            l1.setTextColor(Color.parseColor("#403F3F"));
            l1.setTypeface(null, Typeface.BOLD);
            l1.setText("Br."+df.format(toPri));
            l1.setTextSize(13);
            l1.setGravity(Gravity.RIGHT);
            l1.setTooltipText(itid);
            //l1.setOnClickListener(txClick2);
            subLin2.addView(l1,txParm3);
            l1 = new TextView(root.getContext());
            l1.setTextColor(Color.parseColor("#403F3F"));
            l1.setText(qt+" X "+pri+"=Br. "+toPri);
            l1.setTextSize(13);
            l1.setTooltipText(itid);
            l1.setGravity(Gravity.RIGHT);
            //l1.setOnClickListener(txClick2);
            subLin2.addView(l1,txParm4);
            MainLin.setTooltipText(itid);
            MainLin.addView(subLin2,sublin2);

            Itli7.addView(MainLin,txParm1);
            Itli7.addView(li2,lin2);
            li.addView(Itli7,lin1);
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private static int tab_Ord_Count=0;
    private static String TableOrdID(String vouchType)
    {
        String item_id="";
        int digit=8;
        try
        {
            try {
                MainActivity.connection = MainActivity.Conn();
            } catch (Exception ex) {
                Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            int idco=0;
            String isClo="true";
            ResultSet cr =st.executeQuery("select * from tableOrder where tableId='"+vouchType+"' order by noOrder");
            while(cr.next())
            {
                idco=cr.getInt("noOrder");
                isClo=cr.getString("isClose");
            }
            if(isClo.equalsIgnoreCase("true"))
            {
                idco = idco + 1;
            }
            String jk=""+idco;
            if(jk.length()>=digit)
            {
                item_id=vouchType+"-"+idco;
            }
            else
            {
                int diff=digit-jk.length();
                String pp="";
                for(int j=0;j<diff;j++)
                {
                    pp=pp+"0";
                }
                item_id=vouchType+"-"+pp+idco;
            }
            tab_Ord_Count=idco;
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return item_id;
    }
    private static String PRE_FIX="";
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
            PRE_FIX=preFix;
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
    private void printWebView(ArrayList h,String vouchId) {
        String s1=h.get(0).toString();
        String s2=h.get(1).toString();
        String s3=h.get(2).toString();
        String s4=h.get(3).toString();
        String s5=h.get(4).toString();
        double s6=Double.parseDouble(h.get(5).toString());
        double s7=Double.parseDouble(h.get(6).toString());
        String s8=h.get(7).toString();
        String s9=h.get(8).toString();
        double s10=Double.parseDouble(h.get(9).toString());
        double s11=Double.parseDouble(h.get(10).toString());
        String s12=h.get(11).toString();
        String s13=h.get(12).toString();
        String s14=h.get(13).toString();
        String s15=h.get(14).toString();
        String s16=h.get(15).toString();
        String s17=h.get(16).toString();
        double s18=Double.parseDouble(h.get(17).toString());
        double s19=Double.parseDouble(h.get(18).toString());
        int s20=Integer.parseInt(h.get(19).toString());
        String s21=h.get(20).toString();
        String s22=h.get(21).toString();
        String s23=h.get(22).toString();
        WebView webView = new WebView(root.getContext());
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view,
                                                    WebResourceRequest request)
            {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPageFinished(WebView view, String url)
            {
                createWebPrintJob(view);
                myWebView = null;
            }
        });
        String ty="Sales Order";
        String prefix="SO-";
        String addPa="";
        if(s5.equalsIgnoreCase("true"))
        {
            addPa="";
        }
        else
        {
            addPa="          <tr>\n" +
                    "            <td colspan=\"2\">Received</td>\n" +
                    "            <td colspan=\"5\" class=\"total\">"+s10+"</td>\n" +
                    "          </tr>\n"+
            "          <tr>\n" +
                    "            <td colspan=\"2\">Balance Due</td>\n" +
                    "            <td colspan=\"5\" class=\"total\">"+s11+"</td>\n" +
                    "          </tr>\n" +
                    "          <tr>\n" +
                    "            <td colspan=\"2\">Due Date</td>\n" +
                    "            <td colspan=\"5\" class=\"total\">"+s22+"</td>\n" +
                    "          </tr>\n" ;
        }
        String htmlTable="";
        for(int i=0;i<LineItem.size();i++)
        {
            ArrayList b1= (ArrayList) LineItem.get(i);
            String itid=b1.get(0).toString();
            String itNm=b1.get(1).toString();
            double qt=Double.parseDouble(b1.get(2).toString());
            double pri=Double.parseDouble(b1.get(3).toString());
            double toPri=qt*pri;
            String r1=itid;
            String r2=itNm;
            String r4=df.format(qt);
            String r5=df.format(pri);
            String r6=df.format(toPri);
            htmlTable=htmlTable+"          <tr>\n" +
                    "            <td class=\"service\">"+r1+"</td>\n" +
                    "            <td class=\"desc\">"+r2+"</td>\n" +
                    "            <td class=\"qty\">"+r4+"</td>\n" +
                    "            <td class=\"U.Pri\">"+r5+"</td>\n" +
                    "            <td class=\"total\">"+r6+"</td>\n" +
                    "          </tr>\n" ;
        }
        String htmlDocument =
                "<html><body><h1>Glance Cafe</h1><p>"
                        + "This is some sample content.</p>" +
                        "<h3 align='center'>"+ty+"</h3>" +
                        "<p>Voucher To</p></body></html>";
        String htmlDoc="<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>Example 1</title>\n" +style
                +
                "  </head>\n" +
                "  <body>\n" +
                "    <header class=\"clearfix\">\n" +
                "      <h1>"+ty+"</h1>\n" +
                "      <div id=\"company\" class=\"clearfix\">\n" +
                "        <div>Glance Cafe</div>\n" +
                "        <div>Hossana,Ethiopia</div>\n" +
                "        <div>+251960095066</div>\n" +
                "        <div><a href=\"https://m.facebook.com/pg/Sebtash-Technology-102724298118445/posts/\">Sebtash Facebook Page</a></div>\n" +
                "      </div>\n" +
                "      <div id=\"project\">\n" +
                "        <div><span>Voucher ID</span> "+vouchId+"</div>\n" +
                "        <div><span>Voucher T0</span> "+s2+"</div>\n" +
                "        <div><span>Mobile</span>"+s3+"</div>\n" +
                "        <div><span>Tin No</span>"+s4+"</div>\n" +
                "        <div><span>DATE</span>"+s16+"</div>\n" +
                "        <div><span>Payment Type</span>"+s12+"</div>\n" +
                "      </div>\n" +
                "    </header>\n" +
                "    <main>\n" +
                "      <table>\n" +
                "        <thead>\n" +
                "          <tr>\n" +
                "            <th class=\"service\">Item Id</th>\n" +
                "            <th class=\"desc\">Item Name</th>\n" +
                "            <th>QTY</th>\n" +
                "            <th>U.Price</th>\n" +
                "            <th>TOTAL</th>\n" +
                "          </tr>\n" +
                "        </thead>\n" +
                "        <tbody>\n" +htmlTable+
                "          <tr>\n" +
                "            <td colspan=\"2\">SUBTOTAL</td>\n" +
                "            <td colspan=\"5\" class=\"total\">"+s6+"</td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td colspan=\"2\">Discount %</td>\n" +
                "            <td colspan=\"5\" class=\"total\">"+s8+"</td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td colspan=\"2\">TAX %</td>\n" +
                "            <td colspan=\"5\" class=\"total\">"+s9+"</td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td colspan=\"2\" class=\"grand total\">GRAND TOTAL</td>\n" +
                "            <td colspan=\"5\" class=\"grand total\">"+s7+"</td>\n" +
                "          </tr>\n" +addPa+
                "        </tbody>\n" +
                "      </table>\n" +
                "      <div id=\"notices\">\n" +
                "        <div>In Word:</div>\n" +
                "        <div class=\"notice\">"+s15+"</div>\n" +
                "      </div>\n" +
                "    </main>\n" +
                "    <footer>\n" +
                "      <p align='left'>Sebtash Technology ©Copy Right 2022:Invoice was created on a computer and is valid without the signature and seal.</p>\n" +
                "    </footer>\n" +
                "  </body>\n" +
                "</html>";
        webView.loadDataWithBaseURL(null, htmlDoc,
                "text/HTML", "UTF-8", null);

        myWebView = webView;
    }
   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
   private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) root.getContext().getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter("MyDocument");

        String jobName = getString(R.string.app_name) + " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
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
    private static String style="    <style>.clearfix:after {\n" +
            "  content: \"\";\n" +
            "  display: table;\n" +
            "  clear: both;\n" +
            "}\n" +
            "\n" +
            "a {\n" +
            "  color: #5D6975;\n" +
            "  text-decoration: underline;\n" +
            "}\n" +
            "\n" +
            "body {\n" +
            "  position: relative;\n" +
            "  width: 21cm;  \n" +
            "  height: 29.7cm; \n" +
            "  margin: 0 auto; \n" +
            "  color: #001028;\n" +
            "  background: #FFFFFF; \n" +
            "  font-family: Arial, sans-serif; \n" +
            "  font-size: 12px; \n" +
            "  font-family: Arial;\n" +
            "}\n" +
            "\n" +
            "header {\n" +
            "  padding: 10px 0;\n" +
            "  margin-bottom: 30px;\n" +
            "}\n" +
            "\n" +
            "#logo {\n" +
            "  text-align: center;\n" +
            "  margin-bottom: 10px;\n" +
            "}\n" +
            "\n" +
            "#logo img {\n" +
            "  width: 90px;\n" +
            "}\n" +
            "\n" +
            "h1 {\n" +
            "  border-top: 1px solid  #5D6975;\n" +
            "  border-bottom: 1px solid  #5D6975;\n" +
            "  color: #5D6975;\n" +
            "  font-size: 2.4em;\n" +
            "  line-height: 1.4em;\n" +
            "  font-weight: normal;\n" +
            "  text-align: center;\n" +
            "  margin: 0 0 20px 0;\n" +
            "  background: url(dimension.png);\n" +
            "}\n" +
            "\n" +
            "#project {\n" +
            "  float: left;\n" +
            "}\n" +
            "\n" +
            "#project span {\n" +
            "  color: #5D6975;\n" +
            "  text-align: right;\n" +
            "  width: 52px;\n" +
            "  margin-right: 10px;\n" +
            "  display: inline-block;\n" +
            "  font-size: 0.8em;\n" +
            "}\n" +
            "\n" +
            "#company {\n" +
            "  float: right;\n" +
            "  text-align: right;\n" +
            "}\n" +
            "\n" +
            "#project div,\n" +
            "#company div {\n" +
            "  white-space: nowrap;        \n" +
            "}\n" +
            "\n" +
            "table {\n" +
            "  width: 100%;\n" +
            "  border-collapse: collapse;\n" +
            "  border-spacing: 0;\n" +
            "  margin-bottom: 5px;\n" +
            "}\n" +
            "\n" +
            "table tr:nth-child(2n-1) td {\n" +
            "  background: #F5F5F5;\n" +
            "}\n" +
            "\n" +
            "table th,\n" +
            "table td {\n" +
            "  text-align: center;\n" +
            "}\n" +
            "\n" +
            "table th {\n" +
            "  padding: 5px 5px;\n" +
            "  color: #5D6975;\n" +
            "  border-bottom: 1px solid #C1CED9;\n" +
            "  white-space: nowrap;        \n" +
            "  font-weight: normal;\n" +
            "}\n" +
            "\n" +
            "table .service,\n" +
            "table .desc {\n" +
            "  text-align: left;\n" +
            "}\n" +
            "\n" +
            "table td {\n" +
            "  padding: 5px;\n" +
            "  text-align: right;\n" +
            "}\n" +
            "\n" +
            "table td.service,\n" +
            "table td.desc {\n" +
            "  vertical-align: top;\n" +
            "}\n" +
            "\n" +
            "table td.unit,\n" +
            "table td.qty,\n" +
            "table td.total {\n" +
            "  font-size: 1.2em;\n" +
            "}\n" +
            "\n" +
            "table td.grand {\n" +
            "  border-top: 1px solid #5D6975;;\n" +
            "}\n" +
            "\n" +
            "#notices .notice {\n" +
            "  color: #5D6975;\n" +
            "  font-size: 1.2em;\n" +
            "}\n" +
            "\n" +
            "footer {\n" +
            "  color: #5D6975;\n" +
            "  width: 100%;\n" +
            "  height: 30px;\n" +
            "  position: absolute;\n" +
            "  bottom: 0;\n" +
            "  border-top: 1px solid #C1CED9;\n" +
            "  padding: 8px 0;\n" +
            "  text-align: center;\n" +
            "}" +
            "</style>";
}