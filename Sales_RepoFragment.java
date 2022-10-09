package org.o7planning.agelegecafe.ui.sales_report;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.R;
import com.google.android.material.tabs.TabLayout;

import java.text.DecimalFormat;

public class Sales_RepoFragment extends Fragment {

    private Sales_RepoViewModel galleryViewModel;
    private static View root=null;
    TabLayout tabLayout;
    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager=null;
    FragmentTransaction fragmentTransaction;
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static DBManager dbManager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(Sales_RepoViewModel.class);
        root = inflater.inflate(R.layout.sales_repo, container, false);

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

        fragment = new InsideReport();
        fragmentManager=getChildFragmentManager();
        fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new InsideReport();
                        //InsideReport.appendRow(InsideReport.table);
                        break;
                    case 1:
                        fragment = new OutsideReport();
                        //OutsideReport.appendRow(OutsideReport.table);
                        break;
                    case 2:
                        fragment = new GeneralSalesReport();
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
        return root;
    }
}