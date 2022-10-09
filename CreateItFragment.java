package org.o7planning.agelegecafe.ui.item_registration;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.DatabaseHelper;
import org.o7planning.agelegecafe.MainActivity;
import org.o7planning.agelegecafe.R;

import java.util.ArrayList;

public class CreateItFragment extends Fragment {
    public static EditText e1=null,e2=null,e3=null,e4=null;
    private static LinearLayout l=null,l1=null,l2=null;
    private static TextView t1=null,t2=null,t3=null,t4=null;
    private static View root=null;
    private static DBManager dbManager;
    public static int Addwhol=0;
    public CreateItFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.item_reg_price, container, false);
        try
        {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        t1=root.findViewById(R.id.reg_it_pr_tx1);
        t2=root.findViewById(R.id.reg_it_pr_tx2);
        t3=root.findViewById(R.id.reg_it_pr_tx3);
        t4=root.findViewById(R.id.reg_it_pr_tx4);
        e1=root.findViewById(R.id.reg_it_pr_e1);
        e2=root.findViewById(R.id.reg_it_pr_e2);
        e3=root.findViewById(R.id.reg_it_pr_e3);
        e4=root.findViewById(R.id.reg_it_pr_e4);
        l=root.findViewById(R.id.reg_it_pr_l);
        l1=root.findViewById(R.id.reg_it_pr_l1);
        l2=root.findViewById(R.id.reg_it_pr_l2);
        l.removeView(l2);
        Addwhol=0;
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l.removeView(l1);
                l.addView(l2);
                Addwhol=1;
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l.removeView(l1);
                l.addView(l2);
                Addwhol=1;
            }//9990039504
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l.removeView(l2);
                l.addView(l1);
                Addwhol=0;
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l.removeView(l2);
                l.addView(l1);
                Addwhol=0;
            }
        });
        return root;
    }
}
