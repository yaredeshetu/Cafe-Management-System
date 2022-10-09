package org.o7planning.agelegecafe.ui.purchase_invoice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.R;

public class Purchase_Fragment extends Fragment {
    private static View root=null;
    private static DBManager dbManager;
    private static Button b1=null,b2=null,b3=null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.item_purchase, container, false);
        try {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        } catch (Exception ex) {
            Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return root;
    }
}
