package org.o7planning.agelegecafe.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.R;

public class Parties extends Fragment {
    public static EditText e1=null,e2=null,e3=null,e4=null;
    private static LinearLayout l=null,l1=null,l2=null;
    private static TextView t1=null,t2=null,t3=null,t4=null;
    private static View root=null;
    private static DBManager dbManager;
    public static int Addwhol=0;
    public Parties() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.parties, container, false);
        try {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        } catch (Exception ex) {
            Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return root;
    }
}
