package org.o7planning.agelegecafe.ui.item_registration;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.DatabaseHelper;
import org.o7planning.agelegecafe.MainActivity;
import org.o7planning.agelegecafe.R;
import org.o7planning.agelegecafe.ui.user_registeration.CreateFragment;
import org.o7planning.agelegecafe.ui.user_registeration.ModifyFragment;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ItemRegFragment extends Fragment {

    private ItemRegViewModel slideshowViewModel;
    private static View root=null;
    private static DBManager dbManager;
    private static TextView tx=null;
    private static TextInputLayout tx1=null;
    private static EditText e1=null,e2=null;
    private static Button b1=null,b2=null;
    private static int it_Count=0,ITEM_ID=0;
    private static String storeN="",Item_ID="";
    private AlertDialog alert=null,alert1=null;
    TabLayout tabLayout;
    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager=null;
    FragmentTransaction fragmentTransaction;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(ItemRegViewModel.class);

        root = inflater.inflate(R.layout.item_reg, container, false);
        try
        {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        tabLayout=(TabLayout)root.findViewById(R.id.tabLayout);
        frameLayout=(FrameLayout)root.findViewById(R.id.frameLayout);

        fragment = new CreateItFragment();
        fragmentManager=getChildFragmentManager();
        //fragmentManager = fragment.getChildFragmentManager();
        fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new CreateItFragment();
                        break;
                    case 1:
                        fragment = new ModifyItFragment();
                        break;
                }
                FragmentManager fm = getChildFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        it_Count=0;
        storeN="";
        Item_ID="";
        tx=root.findViewById(R.id.reg_it_tx);
        tx1=root.findViewById(R.id.reg_it_tx1);
        b1=root.findViewById(R.id.reg_it_bt1);
        b2=root.findViewById(R.id.reg_it_bt2);
        e1=root.findViewById(R.id.reg_it_e1);
        e2=root.findViewById(R.id.reg_it_e2);
        tx1.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    MainActivity.QrCho=0;
                    MainActivity.scanQr(e2);
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        tx.setText(ItemID());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    try {
                        MainActivity.connection = MainActivity.Conn();
                    } catch (Exception ex) {
                        Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    Statement st = MainActivity.connection.createStatement();
                    Statement st1 = MainActivity.connection.createStatement();
                    Statement st2 = MainActivity.connection.createStatement();
                    String itid=tx.getText().toString();
                    String itNam=e1.getText().toString();
                    String itCo=e2.getText().toString();
                    if(itNam.equals("")||itNam.equals(" "))
                    {
                        Toast.makeText(root.getContext(),"Please Type Item Name",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        String salPr=CreateItFragment.e1.getText().toString();
                        String purPr=CreateItFragment.e2.getText().toString();
                        String stoQt=ModifyItFragment.e1.getText().toString();
                        String stoMin=ModifyItFragment.e2.getText().toString();
                        String expD=ModifyItFragment.e3.getText().toString();
                        double wholPr=0.0,whoQt=0,saPr=0.0,prPr=0.0,stokQt=0.0,stokMin=0.0;
                        if(CreateItFragment.Addwhol==1)
                        {
                            String wh1=CreateItFragment.e3.getText().toString();
                            String wh2=CreateItFragment.e4.getText().toString();
                            if(wh1.equals(""))
                            {
                                wh1="0.0";
                            }
                            if(wh2.equals(""))
                            {
                                wh2="0.0";
                            }
                            wholPr=Double.parseDouble(wh1);
                            whoQt=Double.parseDouble(wh2);
                        }
                        if(salPr.equals(""))
                        {
                            salPr="0.0";
                        }
                        if(purPr.equals(""))
                        {
                            purPr="0.0";
                        }
                        if(stoQt.equals(""))
                        {
                            stoQt="0.0";
                        }
                        if(stoMin.equals(""))
                        {
                            stoMin="0.0";
                        }
                        prPr=Double.parseDouble(purPr);
                        saPr=Double.parseDouble(salPr);
                        stokMin=Double.parseDouble(stoMin);
                        stokQt=Double.parseDouble(stoQt);
                        st.execute("insert into Item(itemId, itemName, itemCode,status, count,expDate,remQty,isNotify, wholePr, minWholeQty)" +
                                " values('"+itid+"','"+itNam+"','"+itCo+"','Active',"+it_Count+",'"+expD+"',"+stokMin+",'true',"+wholPr+","+whoQt+")");
                        st.execute("insert into itemBalance(refItemId, storeId, balance, price, purPrice) " +
                                "values('"+itid+"','Main Store',"+stokQt+","+saPr+","+prPr+")");
                        Toast.makeText(root.getContext(),"Success",Toast.LENGTH_LONG).show();
                        tx.setText(ItemID());
                        e1.setText("");
                        e2.setText("");
                        CreateItFragment.e1.setText("");
                        CreateItFragment.e2.setText("");
                        CreateItFragment.e3.setText("");
                        CreateItFragment.e4.setText("");
                        ModifyItFragment.e1.setText("");
                        ModifyItFragment.e2.setText("");
                        ModifyItFragment.e3.setText("");
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
    private static String ItemID()
    {
        int digit=5;
        String item_id="";
        try
        {
            try {
                MainActivity.connection = MainActivity.Conn();
            } catch (Exception ex) {
                Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            Statement st2 = MainActivity.connection.createStatement();
            int idco=0;
            ResultSet cr=st.executeQuery("select count from Item order by count");
            while(cr.next())
            {
                idco=cr.getInt("count");
            }
            idco=idco+1;
            String jk=""+idco;
            if(jk.length()>=digit)
            {
                item_id="It-"+idco;
            }
            else
            {
                int diff=digit-jk.length();
                String pp="";
                for(int j=0;j<diff;j++)
                {
                    pp=pp+"0";
                }
                item_id="It-"+pp+idco;
            }
            it_Count=idco;
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return item_id;
    }
}