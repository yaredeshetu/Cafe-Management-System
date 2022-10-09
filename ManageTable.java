package org.o7planning.agelegecafe.ui.Manage_Table;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import org.o7planning.agelegecafe.R;
import org.o7planning.agelegecafe.ui.home.Collected_Table;
import org.o7planning.agelegecafe.ui.home.Uncollected_Table;

public class ManageTable extends Fragment {
    private static View root=null;
    TabLayout tabLayout;
    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager=null;
    FragmentTransaction fragmentTransaction;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.manage_table, container, false);
        tabLayout=(TabLayout)root.findViewById(R.id.tabLayout);
        frameLayout=(FrameLayout)root.findViewById(R.id.frameLayout);

        fragment = new Daily_Table_Transaction();
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
                        fragment = new Daily_Table_Transaction();
                        break;
                    case 1:
                        Collected_Table.CHOICE=2;
                        fragment = new Collected_Table();
                        break;
                    case 2:
                        Uncollected_Table.CHOICE=2;
                        fragment = new Uncollected_Table();
                        break;
                    case 3:
                        fragment = new Search_Transaction();
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
