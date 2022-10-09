package org.o7planning.agelegecafe;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import org.o7planning.agelegecafe.ui.Store.StoreIssuGRV;
import org.o7planning.agelegecafe.ui.home.HomeFragment;
import org.o7planning.agelegecafe.ui.sales_invoice.Sales_InvoiceFragment;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Add_Item extends AppCompatActivity {
    private static TextView b1=null,b2=null;
    private static EditText e1=null,e2=null,e3=null;
    private static TextInputLayout tx3=null;
    private static TextView tx2=null;
    private static Context root=null;
    private static View root1=null;
    private static TableLayout table=null;
    private static TableRow row=null;
    private static View.OnClickListener txClick2=null;
    private static DBManager dbManager;
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static LinearLayout ll0=null,ll1=null;
    private static int mLastContentHeight = 0,eeCk=0;
    public static int Add_Item_Choice=0;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        root=getApplicationContext();
        root1 = getCurrentFocus();
        try
        {
            dbManager = new DBManager(root);
            dbManager.open();
        }
        catch (Exception ex)
        {
            Toast.makeText(root,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
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
                        Toast.makeText(root, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    if(ll0.getChildCount()==0)
                    {
                        ll0.addView(ll1);
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
                    e3.setText(itNm);
                    e3.setTooltipText(itid);
                    e2.setText(df.format(pri));
                    e1.setText("1");
                    tx2.setText(df.format(pri));
                }
                catch (Exception ex)
                {
                    Toast.makeText(root,ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        };
        b1=findViewById(R.id.add_it_bt1);
        b2=findViewById(R.id.add_it_bt2);
        e1=findViewById(R.id.add_it_ee1);
        e2=findViewById(R.id.add_it_ee2);
        e3=findViewById(R.id.add_it_ee3);
        tx2=findViewById(R.id.add_it_tx2);
        tx3=findViewById(R.id.add_it_tx3);
        table=findViewById(R.id.add_it_menu);
        row=findViewById(R.id.add_it_row1);
        ll0=findViewById(R.id.gpsbtnTakePicture);
        ll1=findViewById(R.id.add_liner);
        tx2.setText("");
        e1.setText("");
        e2.setText("");
        e3.setText("");
        e3.setTooltipText("");
        eeCk=0;
        View activityRootView = findViewById(R.id.add_main);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();

                activityRootView.getWindowVisibleDisplayFrame(r);

                int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > 100) {
                    //enter your code here
                    if(eeCk==1) {
                        if(ll0.getChildCount()==1)
                        {
                            ll0.removeView(ll1);
                        }
                        //Toast.makeText(root, "key Board On----------1", Toast.LENGTH_LONG).show();
                    }
                }else{
                    //enter code for hid
                    if(ll0.getChildCount()==0)
                    {
                        ll0.addView(ll1);
                    }
                    //Toast.makeText(root,"key Board Off---------1",Toast.LENGTH_LONG).show();
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
                try {
                    String s1=e1.getText().toString();
                    String s2=e2.getText().toString();
                    if(s1.equals("")||s1.equals(" "))
                    {
                        s1="0.00";
                    }
                    if(s2.equals("")||s2.equals(" "))
                    {
                        s2="0.00";
                    }
                    double s11=Double.parseDouble(s1);
                    double s22=Double.parseDouble(s2);
                    double s3=s11*s22;
                    tx2.setText(df.format(s3));
                }
                catch (Exception ex)
                {
                    Toast.makeText(root,ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String s1=e1.getText().toString();
                    String s2=e2.getText().toString();
                    if(s1.equals("")||s1.equals(" "))
                    {
                        s1="0.00";
                    }
                    if(s2.equals("")||s2.equals(" "))
                    {
                        s2="0.00";
                    }
                    double s11=Double.parseDouble(s1);
                    double s22=Double.parseDouble(s2);
                    double s3=s11*s22;
                    tx2.setText(df.format(s3));
                }
                catch (Exception ex)
                {
                    Toast.makeText(root,ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        e3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //got focus
                    eeCk=1;
                    //Toast.makeText(root,"key Board On",Toast.LENGTH_LONG).show();
                } else {
                    //lost focus
                    eeCk=0;
                    //Toast.makeText(root,"key Board Off",Toast.LENGTH_LONG).show();
                }
            }
        });
        e3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void afterTextChanged(Editable s) {
                String ed=e3.getText().toString();
                appendRow1(table,ed);
            }
        });
        tx3.setEndIconOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try
                {
                    String s1=e3.getText().toString();
                    appendRow1(table,s1);
                }
                catch (Exception ex)
                {
                    Toast.makeText(root,ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    String itNm=e3.getText().toString();
                    String itid=e3.getTooltipText().toString();
                    if(itNm.equals("")||itNm.equals(" "))
                    {

                    }
                    else
                    {
                        try {
                            MainActivity.connection = MainActivity.Conn();
                        } catch (Exception ex) {
                            Toast.makeText(root, ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        Statement st = MainActivity.connection.createStatement();
                        Statement st1 = MainActivity.connection.createStatement();
                        String qt=e1.getText().toString();
                        if(qt.equals(""))
                            qt="1.0";
                        double qty=Double.parseDouble(qt);
                        String pr=e2.getText().toString();
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
                        e3.setText("");
                        appendRow1(table,"");
                        if(Add_Item_Choice==1)
                            Sales_InvoiceFragment.appendRow1(b);
                        else if(Add_Item_Choice==2)
                            StoreIssuGRV.appendRow1(b);
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(root,ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    String itNm=e3.getText().toString();
                    String itid=e3.getTooltipText().toString();
                    if(itNm.equals("")||itNm.equals(" "))
                    {

                    }
                    else
                    {
                        try {
                            MainActivity.connection = MainActivity.Conn();
                        } catch (Exception ex) {
                            Toast.makeText(root, ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        Statement st = MainActivity.connection.createStatement();
                        Statement st1 = MainActivity.connection.createStatement();
                        String qt=e1.getText().toString();
                        if(qt.equals(""))
                            qt="1.0";
                        double qty=Double.parseDouble(qt);
                        String pr=e2.getText().toString();
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
                        onBackPressed();
                        if(Add_Item_Choice==1)
                            Sales_InvoiceFragment.appendRow1(b);
                        else if(Add_Item_Choice==2)
                            StoreIssuGRV.appendRow1(b);
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(root,ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        //attachKeyboardListeners();
        appendRow1(table,"");
    }
    private ArrayList search1(String str)
    {
        ArrayList Big=new ArrayList();
        try
        {
            try {
                MainActivity.connection = MainActivity.Conn();
            } catch (Exception ex) {
                Toast.makeText(root, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            Statement st2 = MainActivity.connection.createStatement();
            Big = new ArrayList();
            ResultSet cr =st.executeQuery("select itemId,itemName,balance,price,purPrice from itemList where itemName LIKE '%"+str+"%' order by itemName");
            while(cr.next())
            {
                String itid=cr.getString("itemId");
                String itNm=cr.getString("itemName");
                double bal=0.0,pri=0.0;
                bal=cr.getDouble("balance");
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
            Toast.makeText(root,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return Big;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Do extra stuff here
        //Toast.makeText(root,"Key Board Off ----------2",Toast.LENGTH_LONG).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void appendRow1(TableLayout table, String str) {
        try
        {
            table.removeAllViews();
            ArrayList b=new ArrayList();
            if(str.equals(""))
                b= HomeFragment.B_ITEM;
            else
                b=search1(str);
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
            table.addView(row, params);
            TableRow row = new TableRow(root);
            row.setWeightSum(1.0F);
            LinearLayout MainLin=new LinearLayout(root);
            MainLin.setOrientation(LinearLayout.HORIZONTAL);
            MainLin.setBackgroundResource(R.drawable.border_round1);
            MainLin.setWeightSum(1.0F);
            for(int i=0;i<b.size();i++)
            {
                row = new TableRow(root);
                row.setWeightSum(1.0F);
                MainLin=new LinearLayout(root);
                MainLin.setOrientation(LinearLayout.HORIZONTAL);
                MainLin.setBackgroundResource(R.drawable.border_round1);
                MainLin.setWeightSum(1);
                ArrayList s= (ArrayList) b.get(i);
                String itid=s.get(0).toString();
                String itnm=s.get(1).toString();
                String itbal=s.get(2).toString();
                String itpri=s.get(3).toString();
                LinearLayout totLin=new LinearLayout(root);
                totLin.setOrientation(LinearLayout.VERTICAL);

                TextView l1 = new TextView(root);
                l1.setTextColor(Color.BLACK);
                l1.setText(itnm);
                l1.setTextSize(14);
                l1.setTypeface(null, Typeface.BOLD_ITALIC);
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                totLin.addView(l1,tosubParms);
                l1 = new TextView(root);
                l1.setTextColor(Color.BLACK);
                l1.setTypeface(null, Typeface.ITALIC);
                l1.setText(itid);
                l1.setTextSize(14);
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                totLin.addView(l1,tosubParms1);
                totLin.setTooltipText(itid);
                totLin.setOnClickListener(txClick2);

                LinearLayout totLin1=new LinearLayout(root);
                totLin1.setOrientation(LinearLayout.VERTICAL);

                totLin1.setTooltipText(itid);
                totLin1.setOnClickListener(txClick2);
                l1 = new TextView(root);
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                l1.setTextColor(Color.BLACK);
                l1.setText("Price:-"+itpri);
                l1.setTextSize(14);
                totLin1.addView(l1,tosubParms);
                l1 = new TextView(root);
                l1.setTooltipText(itid);
                l1.setOnClickListener(txClick2);
                l1.setTextColor(Color.BLACK);
                l1.setText("In Stock:-"+itbal);
                l1.setTextSize(14);
                totLin1.addView(l1,tosubParms1);

                MainLin.setTooltipText(itid);
                MainLin.setOnClickListener(txClick2);
                MainLin.addView(totLin,mainParms);
                MainLin.addView(totLin1,mainParms2);
                row.addView(MainLin,rowParm);
                table.addView(row,params);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private static float convDpToPx(float dp)
    {
        DisplayMetrics mat= Resources.getSystem().getDisplayMetrics();
        float px=dp*(mat.densityDpi/160f);
        return Math.round(px);
    }
}
