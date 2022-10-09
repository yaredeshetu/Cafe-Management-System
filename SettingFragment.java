package org.o7planning.agelegecafe.ui.setting;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.MainActivity;
import org.o7planning.agelegecafe.R;

import java.sql.Connection;
import java.sql.Statement;

public class SettingFragment extends Fragment {

    private org.o7planning.agelegecafe.ui.setting.SettingViewModel galleryViewModel;
    private static View root=null;
    private EditText ed1=null,ed2=null,ed3=null,ed4=null;
    private Button b1=null;
    private static Spinner spin=null;
    private Button b3=null;
    private EditText e1=null,e2=null;
    private TextView te1=null,te2=null;
    private static RadioButton rd1=null,rd2=null;
    private static CheckBox ch1=null,ch2=null;
    private static RadioGroup rdgr=null;
    private static LinearLayout lay=null,lay2=null;
    private DBManager dbManager;
    private static String adUs="admin",adPa="admin";
    private int progressChangedValue = 0,progressChangedValue2 = 0,progressChangedValue3 = 0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(org.o7planning.agelegecafe.ui.setting.SettingViewModel.class);
        root = inflater.inflate(R.layout.setting, container, false);

        try
        {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        lay2=root.findViewById(R.id.setview2);
        lay=root.findViewById(R.id.setview1);
        b1=root.findViewById(R.id.setbt1);
        b3=root.findViewById(R.id.setbt3);
        e1=root.findViewById(R.id.setue1);
        e2=root.findViewById(R.id.setue2);
        ed1=root.findViewById(R.id.sete1);
        ed2=root.findViewById(R.id.sete2);
        ed3=root.findViewById(R.id.sete3);
        ed4=root.findViewById(R.id.sete4);
        spin=root.findViewById(R.id.setsp1);
        te1=root.findViewById(R.id.setue01);
        te2=root.findViewById(R.id.setue02);
        ch1=root.findViewById(R.id.setch1);
        ch2=root.findViewById(R.id.setch2);
        rd1=root.findViewById(R.id.setr1);
        rd2=root.findViewById(R.id.setr2);
        rdgr=root.findViewById(R.id.setrdgr);
        lay.setVisibility(View.INVISIBLE);
        try
        {
            Cursor cr=dbManager.fetch();
            ed1.setText(cr.getString(0));
            ed2.setText(cr.getString(1));
            ed3.setText(cr.getString(2));
            ed4.setText(cr.getString(3));
            adUs=cr.getString(4);
            adPa=cr.getString(5);
            String conn_type=cr.getString(6);
            int indx=0;
            for(int i=0;i<spin.getChildCount();i++)
            {
                if(conn_type.equalsIgnoreCase(spin.getChildAt(i).toString()))
                {
                    indx=i;
                }
            }
            spin.setSelection(indx);
            Cursor cr1=dbManager.fetch10();
            if (cr1!=null)
            {
                String auD=cr1.getString(0);
                String frD=cr1.getString(1);
                String auA=cr1.getString(2);
                if(auD.equalsIgnoreCase("true"))
                {
                    ch1.setChecked(true);
                    rdgr.setVisibility(View.VISIBLE);
                    if(frD.equalsIgnoreCase("From Server"))
                    {
                        rd1.setChecked(true);
                        rd2.setChecked(false);
                    }
                    else if(frD.equalsIgnoreCase("From Mobile"))
                    {
                        rd2.setChecked(true);
                        rd1.setChecked(false);
                    }
                }
                else
                {
                    ch1.setChecked(false);
                    rdgr.setVisibility(View.INVISIBLE);
                }
                if(auA.equalsIgnoreCase("true"))
                {
                    ch2.setChecked(true);
                }
                else
                    ch2.setChecked(false);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(b3.getText().toString().equalsIgnoreCase("Login"))
                    {
                        String userN = e1.getText().toString();
                        String pass = e2.getText().toString();
                        if (userN.equals(adUs) && pass.equals(adPa))
                        {
                            Toast.makeText(root.getContext(), "Success", Toast.LENGTH_LONG).show();
                            lay.setVisibility(View.VISIBLE);
                            b3.setText("Change Password");
                            te1.setText("Password:-");
                            te2.setText("Confirm:-");
                        }
                        else
                        {
                            Toast.makeText(root.getContext(), "Wrong User Name Or Password", Toast.LENGTH_LONG).show();
                            lay.setVisibility(View.INVISIBLE);
                        }
                    }
                    else if(b3.getText().toString().equalsIgnoreCase("Change Password"))
                    {
                        String adPass=e1.getText().toString();
                        String adPass1=e2.getText().toString();
                        if(adPass.equals(adPass1))
                        {
                            dbManager.update(adPass);
                            Toast.makeText(root.getContext(), "Password Change Successfully", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(root.getContext(), "Password Mis Match", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    String a1="admin";
                    String a2="123";
                    String a3=ed1.getText().toString();
                    String a4=ed2.getText().toString();
                    String a5=ed3.getText().toString();
                    String a6=ed4.getText().toString();
                    String a7=spin.getSelectedItem().toString();
                    if(a1.equals("")||a1.equals(" ")||a3.equals("")||a3.equals(" ")||a4.equals("")||a4.equals(" ")||a5.equals("")||a5.equals(" ")||a6.equals("")||a6.equals(" "))
                    {
                        Toast.makeText(root.getContext(),"Please Fill Empty Fields",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        MainActivity.Connection_type=a7;
                        String auD="false";
                        String frD="";
                        String auA="false";
                        if(ch1.isChecked())
                        {
                            auD="true";
                            if(rd1.isChecked())
                                frD="From Server";
                            else if(rd2.isChecked())
                                frD="From Mobile";
                        }
                        if(ch2.isChecked())
                        {
                            auA="true";
                        }
                        dbManager.delete();
                        dbManager.insert(a1,a2,a3,a4,a5,a6,a7,auD,frD,auA);
                        Thread.sleep(200);
                        Connection con = MainActivity.Conn();
                        Statement st = con.createStatement();
                        st.execute("delete from autoSetting");
                        st.execute("insert into autoSetting(auto_date, date_from, auto_assign, assign_from) " +
                                "values('"+auD+"','"+frD+"','"+auA+"','"+frD+"')");
                        Toast.makeText(root.getContext(),"Success",Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;
    }
}