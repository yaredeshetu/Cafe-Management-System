package org.o7planning.agelegecafe.ui.user_registeration;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.DatabaseHelper;
import org.o7planning.agelegecafe.MainActivity;
import org.o7planning.agelegecafe.R;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CreateFragment extends Fragment {
    private static Button bt=null,bt1=null;
    private static EditText e2=null,e3=null,e4=null,e5=null,e6=null;
    private static TextView e1=null,tx=null;
    private static View root=null;
    private static DBManager dbManager;
    private static Spinner spin=null;
    public CreateFragment() {
        // Required empty public constructor
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.create_user, container, false);
        try
        {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        bt=root.findViewById(R.id.usb1);
        bt1=root.findViewById(R.id.usb2);
        e1=root.findViewById(R.id.use1);
        e2=root.findViewById(R.id.use2);
        e3=root.findViewById(R.id.use3);
        e4=root.findViewById(R.id.use4);
        e5=root.findViewById(R.id.use5);
        e6=root.findViewById(R.id.use6);
        spin=root.findViewById(R.id.ussp1);
        tx=root.findViewById(R.id.use7);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    String s1=e1.getText().toString();
                    String s2=e2.getText().toString();
                    String s3=e3.getText().toString();
                    String s4=e4.getText().toString();
                    String s5=e5.getText().toString();
                    String s6=e6.getText().toString();
                    String s7=spin.getSelectedItem().toString();
                    String s8="Active";
                    if(s1.equals("")||s2.equals("")||s3.equals("")||s4.equals("")||s5.equals(""))
                    {
                        Toast.makeText(root.getContext(),"Fill Empty Places",Toast.LENGTH_LONG).show();
                        tx.setText("Fill Empty Places");
                    }
                    else
                    {
                        if(s4.equals(s5))
                        {
                            int ck=0;
                            Statement st=MainActivity.connection.createStatement();
                            ResultSet r=st.executeQuery("select userName from userAccount where userName='"+s3+"'");
                            while(r.next())
                            {
                                if(r.getString("userName").equalsIgnoreCase(s3))
                                {
                                    ck=1;
                                }
                            }
                            if(ck==1)
                            {
                                tx.setText("User Name Is Already Exist.");
                                Toast.makeText(root.getContext(),"User Name Is Already Exist.",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                ArrayList s=new ArrayList();
                                s.add(s1);
                                s.add(s2);
                                s.add(s3);
                                s.add(s4);
                                s.add(s6);
                                s.add(s7);
                                s.add(s8);
                                //dbManager.insert4(s,"false");
                                if(MainActivity.Connection_type.equalsIgnoreCase("LAN(PC)"))
                                {
                                    st.execute("insert into userAccount(empId, empName, userName, password, mobile, duety, active,sync,count) " +
                                            "values('"+s1+"','"+s2+"','"+s3+"','"+s4+"','"+s6+"','"+s7+"','"+s8+"','true',"+User_Count+")");
                                }
                                tx.setText("User Is Register Successfully.");
                                e1.setText(EmpID());
                                e2.setText("");
                                e3.setText("");
                                e4.setText("");
                                e5.setText("");
                                e6.setText("+251");
                                Toast.makeText(root.getContext(),"User Is Register Successfully.",Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            tx.setText("Password Miss Match");
                            Toast.makeText(root.getContext(),"Password Miss Match",Toast.LENGTH_LONG).show();
                        }
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        e1.setText(EmpID());
        return root;
    }

    private static int User_Count=0;
    private static String EmpID()
    {
        int digit=5;
        String item_id="";
        try
        {
            int idco=0;
            Statement st=MainActivity.connection.createStatement();
            ResultSet r=st.executeQuery("select count from userAccount order by count");
            while(r.next())
            {
                idco=r.getInt("count");
            }
            idco=idco+1;
            String jk=""+idco;
            if(jk.length()>=digit)
            {
                item_id="Emp-"+idco;
            }
            else
            {
                int diff=digit-jk.length();
                String pp="";
                for(int j=0;j<diff;j++)
                {
                    pp=pp+"0";
                }
                item_id="Emp-"+pp+idco;
            }
            User_Count=idco;
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return item_id;
    }
}
