package org.o7planning.agelegecafe.ui.user_registeration;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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
import java.util.ArrayList;

public class ModifyFragment extends Fragment {
    private static Button bt=null,bt1=null;
    private static EditText e2=null,e4=null,e5=null,e6=null;
    private static TextView e1=null,e3=null,tx=null;
    private static View root=null;
    private static DBManager dbManager;
    private static Spinner spin=null,spin1=null;
    private static View.OnClickListener txClick2=null;
    private static TableRow row1=null;
    private static TableLayout table1=null;
    public ModifyFragment() {
        // Required empty public constructor
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.modify_user, container, false);
        try
        {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        txClick2=new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                RadioButton bt= (RadioButton) v;
                try
                {
                    TableRow roww= (TableRow) bt.getParent();
                    TableLayout tab= (TableLayout) roww.getParent();
                    String id=bt.getTooltipText().toString();
                    Toast.makeText(root.getContext(),"Id="+id,Toast.LENGTH_LONG).show();
                    int count = tab.getChildCount();
                    for
                    (int i = 1; i < count; i++) {
                        TableRow child = (TableRow) tab.getChildAt(i);

                        RadioButton bt1= (RadioButton) child.getChildAt(0);
                        if(!id.equalsIgnoreCase(bt1.getTooltipText().toString()))
                        {
                            bt1.setChecked(false);
                        }
                    }
                    bt.setChecked(true);
                    Cursor cr=null;//dbManager.fetch3(id);
                    do {
                        String itid=cr.getString(0);
                        String itnm=cr.getString(1);
                        String unt=cr.getString(2);
                        String sto=cr.getString(3);
                        double pri=cr.getDouble(4);
                        double bal= cr.getDouble(5);
                        tx.setText(itid);
                        e2.setText(itnm);
                        e4.setText(bal+"");
                        e5.setText(pri+"");
                    }while (cr.moveToNext());
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        };
        bt=root.findViewById(R.id.usb1);
        bt1=root.findViewById(R.id.usb2);
        e1=root.findViewById(R.id.use1);
        e2=root.findViewById(R.id.use2);
        e3=root.findViewById(R.id.use3);
        e4=root.findViewById(R.id.use4);
        e5=root.findViewById(R.id.use5);
        e6=root.findViewById(R.id.use6);
        spin=root.findViewById(R.id.modsp1);
        spin1=root.findViewById(R.id.modsp2);
        tx=root.findViewById(R.id.use7);
        row1=root.findViewById(R.id.modrow1);
        table1=root.findViewById(R.id.modmenu1);
        appendRow1(table1,"");
        return root;
    }
    private ArrayList search1(String str)
    {
        ArrayList Big=new ArrayList();
        try
        {
            ArrayList s = new ArrayList();
            Statement st= MainActivity.connection.createStatement();
            ResultSet r=st.executeQuery("select empId,empName,userName,mobile,duety,Active from userAccount");
            Big = new ArrayList();
            int count = 0;
            while (r.next())
            {
                s=new ArrayList();
                s.add(r.getString("empId"));
                s.add(r.getString("empName"));
                s.add(r.getString("userName"));
                s.add(r.getString("mobile"));
                s.add(r.getString("duety"));
                s.add(r.getString("Active"));
                Big.add(s);
                count = count + 1;
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
            table.addView(row1, params);
            for(int i=0;i<b.size();i++)
            {
                Thread.sleep(20);
                TableRow row = new TableRow(root.getContext());
                ArrayList s= (ArrayList) b.get(i);
                RadioButton b1=new RadioButton(root.getContext());
                row.addView(b1, new TableRow.LayoutParams());
                String tolTx=s.get(0).toString();
                b1.setTooltipText(tolTx);
                b1.setOnClickListener(txClick2);
                b1.setChecked(false);
                for(int j=0;j<s.size();j++)
                {
                    String val=s.get(j).toString();
                    TextView l1 = new TextView(root.getContext());
                    l1.setTextColor(Color.BLACK);
                    l1.setText(val);
                    l1.setTextSize(12);
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
