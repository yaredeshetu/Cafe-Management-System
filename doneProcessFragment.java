package org.o7planning.agelegecafe.ui.order_view;

import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class doneProcessFragment extends Fragment {
    private static View root=null;
    private static DBManager dbManager;
    private static TableLayout table=null;
    private static TableRow row1=null;
    private static View.OnClickListener txClick=null;
    public doneProcessFragment() {
        // Required empty public constructor
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.done_process, container, false);
        try
        {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        txClick=new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                TextView bton= (TextView) v;
                try
                {
                    String Order_id=bton.getTooltipText().toString();
                    int orderID=Integer.parseInt(Order_id);
                    String Ord_action=bton.getText().toString();
                    if(Ord_action.equalsIgnoreCase("View"))
                    {

                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        };
        table=root.findViewById(R.id.or_lis_menu);
        row1=root.findViewById(R.id.vouchrow1);
        appendRow(table);
        return root;
    }
    private ArrayList search()
    {
        ArrayList Big=new ArrayList();
        try
        {
            ArrayList s = new ArrayList();
            Big = new ArrayList();
            if(MainActivity.Connection_type.equalsIgnoreCase("LAN(PC)"))
            {
                Statement st = MainActivity.connection.createStatement();
                Statement st1 = MainActivity.connection.createStatement();
                Statement st2 = MainActivity.connection.createStatement();
                ResultSet rs=null;
                if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Cashier")||MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Manager")||MainActivity.Emp_USERNAME.equalsIgnoreCase("admin"))
                    rs=st.executeQuery("select * from voucher1 where status='true' and order_status='Done' order by timeStamp desc");
                else
                    rs=st.executeQuery("select * from voucher1 where orderTake='"+MainActivity.Emp_USERNAME+"' and status='true' and order_status='Done' order by timeStamp desc");
                while (rs.next())
                {
                    s = new ArrayList();
                    String vouchId=rs.getString("voucherId");
                    String wa=rs.getString("waiter");
                    String empN="";
                    ResultSet cc=st1.executeQuery("select empName from userAccount where userName='"+wa+"'");
                    while (cc.next()){
                        empN=cc.getString("empName");
                    }
                    s.add(vouchId);
                    s.add(rs.getInt("itemCount"));
                    s.add(rs.getDouble("total"));
                    s.add(empN);
                    s.add(rs.getString("timeStamp"));
                    ArrayList b1 = new ArrayList();
                    ResultSet cr1=st1.executeQuery("select * from lineItem1 where voucherId='"+vouchId+"'");
                    while (cr1.next())
                    {
                        ArrayList s1 = new ArrayList();
                        s1.add(cr1.getString("itemName"));
                        s1.add(cr1.getDouble("qtyOut"));
                        s1.add(cr1.getDouble("qtyOut"));
                        s1.add(cr1.getDouble("tot_price"));
                        b1.add(s1);
                    }
                    s.add(b1);
                    Big.add(s);
                }
            }
            else if(MainActivity.Connection_type.equalsIgnoreCase("LAN(Mobile)"))
            {
                /*
                Cursor cr = DBManager.database.rawQuery("SELECT " + DatabaseHelper.d0+","+DatabaseHelper.d20+","+DatabaseHelper.d7+","+DatabaseHelper.d9+","+DatabaseHelper.d16+" FROM " + DatabaseHelper.TABLE_NAME3+" where "+DatabaseHelper.d8+"='"+ MainActivity.Emp_USERNAME +"' and "+DatabaseHelper.d21+"='true' and "+DatabaseHelper.d24+"='Done' order by "+DatabaseHelper.d23+" desc,"+DatabaseHelper.d16+" desc", null);
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
                    Cursor cr1 = DBManager.database.rawQuery("SELECT " + DatabaseHelper.e3+","+DatabaseHelper.e10+","+DatabaseHelper.e8+","+DatabaseHelper.e7+" FROM " + DatabaseHelper.TABLE_NAME4+" where "+DatabaseHelper.e1+"="+vouchId, null);
                    while(cr1.moveToNext())
                    {
                        ArrayList s1 = new ArrayList();
                        s1.add(cr1.getString(0));
                        s1.add(cr1.getDouble(1));
                        s1.add(cr1.getDouble(2));
                        s1.add(cr1.getDouble(3));
                        b1.add(s1);
                    }
                    s.add(b1);
                    Big.add(s);
                }*/
            }
        }
        catch(Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return Big;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void appendRow(TableLayout table) {
        try
        {
            table.removeAllViews();
            ArrayList b=new ArrayList();
            b=search();
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
            mainParms.setMargins(5, 5, 5, 5);
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
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            itsubParms.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams qtysubParms=new LinearLayout.LayoutParams(
                    (int) convDpToPx(90),
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
            for(int i=0;i<b.size();i++)
            {
                Thread.sleep(20);
                TableRow row = new TableRow(root.getContext());
                ArrayList s= (ArrayList) b.get(i);
                String tolTx=s.get(0).toString();
                LinearLayout MainLin=new LinearLayout(root.getContext());
                LinearLayout butLin=new LinearLayout(root.getContext());
                MainLin.setOrientation(LinearLayout.VERTICAL);
                MainLin.setBackgroundResource(R.drawable.back_white);
                LinearLayout totLin=new LinearLayout(root.getContext());
                LinearLayout waiTLin=new LinearLayout(root.getContext());
                totLin.setOrientation(LinearLayout.HORIZONTAL);
                totLin.setBackgroundResource(R.drawable.round_corner3);
                waiTLin.setOrientation(LinearLayout.HORIZONTAL);
                int itCoun=Integer.parseInt(s.get(1).toString());
                double graTot=Double.parseDouble(s.get(2).toString());
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
                ArrayList s1= (ArrayList) s.get(5);
                for(int j=0;j<s1.size();j++)
                {
                    ArrayList s2= (ArrayList) s1.get(j);
                    String val=s2.get(0).toString();
                    double unt=Double.parseDouble(s2.get(1).toString());
                    double qty=Double.parseDouble(s2.get(2).toString());
                    double pri=Double.parseDouble(s2.get(3).toString());
                    LinearLayout itLin=new LinearLayout(root.getContext());
                    LinearLayout qtyLin=new LinearLayout(root.getContext());
                    itLin.setOrientation(LinearLayout.HORIZONTAL);
                    qtyLin.setOrientation(LinearLayout.HORIZONTAL);
                    l1 = new TextView(root.getContext());
                    l1.setTextColor(Color.BLACK);
                    l1.setText("Item Name:-"+val);
                    l1.setTextSize(14);
                    l1.setTypeface(null, Typeface.BOLD);
                    itLin.addView(l1,itsubParms);
                    l1 = new TextView(root.getContext());
                    l1.setTextColor(Color.BLACK);
                    l1.setText("Unit:-"+unt);
                    l1.setTextSize(14);
                    qtyLin.addView(l1,qtysubParms);
                    l1 = new TextView(root.getContext());
                    l1.setTextColor(Color.BLACK);
                    l1.setText("Qty:-"+qty);
                    l1.setTextSize(14);
                    qtyLin.addView(l1,qtysubParms);
                    l1 = new TextView(root.getContext());
                    l1.setTextColor(Color.BLACK);
                    l1.setText("Price:-"+pri);
                    l1.setTextSize(14);
                    qtyLin.addView(l1,qtysubParms1);
                    MainLin.addView(itLin,mainParms);
                    MainLin.addView(qtyLin,mainParms1);
                }
                MainLin.addView(totLin,mainParms);
                MainLin.addView(waiTLin,mainParms);
                l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setBackgroundResource(R.drawable.round_corner);
                l1.setText("View");
                l1.setTextSize(14);
                l1.setTypeface(null, Typeface.BOLD);
                l1.setTooltipText(tolTx);
                l1.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL);
                l1.setOnClickListener(txClick);
                butLin.addView(l1,btsubParms1);
                butLin.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL);
                MainLin.addView(butLin,mainParms);
                row.addView(MainLin,rowParm);
                table.addView(row,params);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private float convDpToPx(float dp)
    {
        DisplayMetrics mat= Resources.getSystem().getDisplayMetrics();
        float px=dp*(mat.densityDpi/160f);
        return Math.round(px);
    }
}
