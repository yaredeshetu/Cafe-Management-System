package org.o7planning.agelegecafe.ui.item_registration;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.R;

import java.util.ArrayList;

public class ModifyItFragment extends Fragment {
    public static EditText e1=null,e2=null,e3=null;
    private static TextInputLayout tx1=null;
    private static View root=null;
    private static DBManager dbManager;
    public ModifyItFragment() {
        // Required empty public constructor
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.item_reg_stock, container, false);
        try
        {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        tx1=root.findViewById(R.id.reg_it_st_tx1);
        e1=root.findViewById(R.id.reg_it_st_e1);
        e2=root.findViewById(R.id.reg_it_st_e2);
        e3=root.findViewById(R.id.reg_it_st_e3);
        return root;
    }
}
