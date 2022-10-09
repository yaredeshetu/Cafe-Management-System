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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
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

public class Table_Reg extends Fragment {
    private static View root=null;
    private static DBManager dbManager;
    private static View.OnClickListener txClick2=null;
    private static TextView b1=null,b2=null;
    private static EditText e1=null;
    private static LinearLayout liner=null;
    private AlertDialog alert=null;
    private AlertDialog alert1=null;
    public Table_Reg()
    {

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.table_reg, container, false);
        try {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        } catch (Exception ex) {
            Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    Statement st = MainActivity.connection.createStatement();
                    Statement st1 = MainActivity.connection.createStatement();
                    ResultSet r=st.executeQuery("select tableN from tableList where id='"+itid+"'");
                    String itNm="";
                    while (r.next())
                    {
                        itNm=r.getString("tableN");
                    }
                    e1.setText(itNm);
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        };
        liner=root.findViewById(R.id.tab_scroll);
        b1=root.findViewById(R.id.tab_b1);
        b2=root.findViewById(R.id.tab_b2);
        e1=root.findViewById(R.id.tab_e1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                    builder.setTitle(R.string.app_name);
                    builder.setIcon(R.mipmap.glance_logo);
                    builder.setMessage("Are You Sure To Save This Table?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                            try
                            {
                                String s1=e1.getText().toString();
                                Statement st=MainActivity.connection.createStatement();
                                Statement st1=MainActivity.connection.createStatement();
                                ResultSet r=st.executeQuery("select * from tableList order by count");
                                int id_count=0;
                                String nm="";
                                while(r.next())
                                {
                                    nm=r.getString("tableN");
                                    id_count=r.getInt("count");
                                }
                                String tbId=ItemID(id_count);
                                if(s1.equalsIgnoreCase(nm))
                                {
                                    Toast.makeText(root.getContext(),"Table Name Is Already Exist.",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(root.getContext(),"Id="+tbId,Toast.LENGTH_LONG).show();
                                    st1.execute("insert into tableList(id,tableN,count) values('"+tbId+"','"+s1+"',"+it_Count+")");
                                    Toast.makeText(root.getContext(),"Success",Toast.LENGTH_LONG).show();
                                    e1.setText("");
                                    appendRow1("");
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

                    alert1 = builder.create();
                    alert1.show();
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                    builder.setTitle(R.string.app_name);
                    builder.setIcon(R.mipmap.glance_logo);
                    builder.setMessage("Are You Sure To Delete This Table?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                            try
                            {
                                String s1=e1.getText().toString();
                                Statement st=MainActivity.connection.createStatement();
                                st.execute("delete from tableList where tableN='"+s1+"'");
                                Toast.makeText(root.getContext(),"Success",Toast.LENGTH_LONG).show();
                                e1.setText("");
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
                appendRow1(ed);
            }
        });
        appendRow1("");
        return root;
    }
    private static String preFix(String vouchType)
    {
        String preF="Tb";
        if(vouchType.equalsIgnoreCase("Inside Item Sales"))
        {
            preF="IIS";
        }
        return preF;
    }
    private static int it_Count=0;
    private static String ItemID(int idco)
    {
        String item_id="";
        int digit=2;
        try
        {
            String preFix=preFix("");
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void appendRow1(String str) {
        try
        {
            liner.removeAllViews();
            LinearLayout.LayoutParams tosubParms=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            tosubParms.setMargins(5, 5, 5, 0);
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
            Statement st= MainActivity.connection.createStatement();
            ResultSet r=st.executeQuery("select * from tableList where tableN LIKE '%"+str+"%' order by id");
            while (r.next())
            {
                String t_id=r.getString("id");
                String t_nam=r.getString("tableN");
                LinearLayout MainLin=new LinearLayout(root.getContext());
                MainLin.setOrientation(LinearLayout.VERTICAL);
                MainLin.setBackgroundResource(R.drawable.border_round1);

                LinearLayout totLin=new LinearLayout(root.getContext());
                totLin.setOrientation(LinearLayout.VERTICAL);

                TextView l1 = new TextView(root.getContext());
                l1.setTextColor(Color.BLACK);
                l1.setText(t_id);
                l1.setTextSize(14);
                l1.setTypeface(null, Typeface.BOLD_ITALIC);
                l1.setTooltipText(t_id);
                l1.setOnClickListener(txClick2);
                totLin.setTooltipText(t_id);
                totLin.setOnClickListener(txClick2);
                totLin.addView(l1,tosubParms);

                LinearLayout totLin1=new LinearLayout(root.getContext());
                totLin1.setOrientation(LinearLayout.VERTICAL);

                totLin1.setTooltipText(t_id);
                totLin1.setOnClickListener(txClick2);
                l1 = new TextView(root.getContext());
                l1.setTooltipText(t_id);
                l1.setOnClickListener(txClick2);
                l1.setTextColor(Color.BLACK);
                l1.setText(t_nam);
                l1.setTextSize(14);
                totLin1.addView(l1,tosubParms);
                MainLin.setTooltipText(t_id);
                MainLin.setOnClickListener(txClick2);
                MainLin.addView(totLin,mainParms);
                MainLin.addView(totLin1,mainParms2);
                liner.addView(MainLin,mainParms);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
