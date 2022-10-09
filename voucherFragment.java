package org.o7planning.agelegecafe.ui.voucher;

import static android.widget.TableLayout.OnClickListener;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.DatabaseHelper;
import org.o7planning.agelegecafe.R;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class voucherFragment extends Fragment {

    private voucherViewModel galleryViewModel;
    private static View root=null;
    private static Button bt=null;
    private static TableLayout table1 =null;
    private static Spinner spin1=null;
    private static TableRow row1=null;
    private static String userName="";
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static DBManager dbManager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(voucherViewModel.class);
        root = inflater.inflate(R.layout.voucher, container, false);

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
        }
        bt=root.findViewById(R.id.vouchbt);
        spin1=root.findViewById(R.id.vouchsp1);
        row1=root.findViewById(R.id.vouchrow1);
        table1=root.findViewById(R.id.vouchmenu);
        table1.removeAllViews();
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10, 0, 5, 10);
        table1.addView(row1, params);
        bt.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    appendRow(table1);
                    //0982480773
                    //
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;
    }
    /*
    private ArrayList search()
    {
        ArrayList Big=new ArrayList();
        try
        {
            double t_total=0.0,t_rec=0.0,t_bala=0.0;
            int t_itCoun=0;
            String sql="";
            if(spin1.getSelectedItem().toString().equals("All Voucher Type"))
            {
                sql="";
            }
            else if(spin1.getSelectedItem().toString().equals("Cash Item Sales"))
            {
                sql=" and "+DatabaseHelper.d1+"='Inside Item Sales'";
            }
            else if(spin1.getSelectedItem().toString().equals("Credit Item Sales"))
            {
                sql=" and "+DatabaseHelper.d1+"='Outside Item Sales'";
            }
            else if(spin1.getSelectedItem().toString().equals("Cash Item Purchase"))
            {
                sql=" and "+DatabaseHelper.d1+"='Cash Item Purchase'";
            }
            else if(spin1.getSelectedItem().toString().equals("Credit Item Purchase"))
            {
                sql=" and "+DatabaseHelper.d1+"='Credit Item Purchase'";
            }
            Cursor cr1 = DBManager.database.rawQuery("SELECT " + DatabaseHelper.d23+","+DatabaseHelper.d2+","+DatabaseHelper.d8+","+DatabaseHelper.d9+","+DatabaseHelper.d7+","+DatabaseHelper.d10+","+DatabaseHelper.d11+","+DatabaseHelper.d1+","+DatabaseHelper.d20+","+DatabaseHelper.d0+","+DatabaseHelper.d21+" FROM " + DatabaseHelper.TABLE_NAME3+" where ("+DatabaseHelper.d21+"='true' or "+DatabaseHelper.d21+"='void')"+sql+" order by "+DatabaseHelper.d0+" desc", null);
            while (cr1.moveToNext())
            {
                String dat=cr1.getString(0);
                String s1=cr1.getString(1);
                String s2=cr1.getString(2);
                String s3=cr1.getString(3);
                double s4=cr1.getDouble(4);
                double s5=cr1.getDouble(5);
                double s6=cr1.getDouble(6);
                String s7=cr1.getString(7);
                int s8=cr1.getInt(8);
                int s9=cr1.getInt(9);
                String s10=cr1.getString(10);
                DateFormat formatter ;
                Date date ;
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                date = (Date)formatter.parse(dat);
                Timestamp timeStampDate = new Timestamp(date.getTime());
                int yr=timeStampDate.getYear()+1900;
                int mn=timeStampDate.getMonth()+1;
                int dy=timeStampDate.getDate();
                String t2Date=yr+"-"+mn+"-"+dy;
                ArrayList s=new ArrayList();
                s.add(s9);
                s.add(t2Date);
                s.add(s1);
                s.add(s2);
                s.add(s3);
                s.add(df.format(s4));
                s.add(df.format(s5));
                s.add(df.format(s6));
                s.add(s7);
                s.add(s8);
                s.add(s10);
                Big.add(s);
                t_total=t_total+s4;
                t_rec=t_rec+s5;
                t_bala=t_bala+s6;
                t_itCoun=t_itCoun+s8;
            }
            ArrayList s=new ArrayList();
            s.add("");
            s.add("Total");
            s.add("");
            s.add("");
            s.add("");
            s.add(df.format(t_total));
            s.add(df.format(t_rec));
            s.add(df.format(t_bala));
            s.add("");
            s.add(t_itCoun);
            s.add("");
            Big.add(s);
        }
        catch(Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return Big;
    }*/
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void appendRow(TableLayout table) {
        try
        {
            table.removeAllViews();
            ArrayList b=new ArrayList();
            //b = search();
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
                String val0=s.get(0).toString();
                for(int j=0;j<(s.size()-1);j++)
                {
                    String val2=s.get((s.size()-1)).toString();
                    String val1=s.get(1).toString();
                    String val=s.get(j).toString();
                    TextView l1 = new TextView(root.getContext());
                    if(val1.equals("Total"))
                    {
                        l1.setTextColor(Color.BLACK);
                        l1.setBackgroundColor(Color.rgb(255,204,102));
                        l1.setTextSize(15);
                    }
                    else
                    {
                        if(val2.equalsIgnoreCase("Void"))
                            l1.setTextColor(Color.RED);
                        else
                            l1.setTextColor(Color.BLACK);
                        l1.setBackgroundColor(Color.rgb(255,255,255));
                        l1.setTextSize(13);
                    }
                    l1.setText(val);
                    l1.setTooltipText(val0);
                    l1.setPadding(5,0,0,0);
                    registerForContextMenu(l1);
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
    private static TextView relativeLayout=null;
    private void updateDB(int id,String menuStr)
    {
        try
        {
            if(menuStr.equals("Void"))
            {
                //dbManager.update(id,menuStr);
            }
            else if(menuStr.equals("Delete"))
            {
                //dbManager.delete(id);
            }
            Toast.makeText(root.getContext(),"Voucher Is "+menuStr+" Successfully",Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // you can set menu header with title icon etc
        try
        {
            relativeLayout= (TextView) v;
            menu.setHeaderTitle("Choose a color");
            // add menu items
            menu.add(0, v.getId(), 0, "View");
            menu.add(0, v.getId(), 0, "Print");
            menu.add(0, v.getId(), 0, "Edit");
            menu.add(0, v.getId(), 0, "Sync");
            menu.add(0, v.getId(), 0, "Void");
            menu.add(0, v.getId(), 0, "Delete");
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    // menu item select listener
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        try
        {
            updateDB(Integer.parseInt(relativeLayout.getTooltipText().toString()),item.getTitle().toString());
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return true;
    }
}