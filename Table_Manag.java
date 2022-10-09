package org.o7planning.agelegecafe.ui.Table;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;

import org.o7planning.agelegecafe.R;
import org.o7planning.agelegecafe.ui.user_registeration.CreateFragment;
import org.o7planning.agelegecafe.ui.user_registeration.ModifyFragment;
import org.o7planning.agelegecafe.ui.user_registeration.userRegViewModel;

public class Table_Manag  extends Fragment {
    private org.o7planning.agelegecafe.ui.user_registeration.userRegViewModel galleryViewModel;

    private static View root=null;
    TabLayout tabLayout;
    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager=null;
    FragmentTransaction fragmentTransaction;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(userRegViewModel.class);
        root = inflater.inflate(R.layout.table_mang, container, false);
        tabLayout=(TabLayout)root.findViewById(R.id.tabLayout);
        frameLayout=(FrameLayout)root.findViewById(R.id.frameLayout);

        fragment = new Table_Reg();
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
                        fragment = new Table_Reg();
                        break;
                    case 1:
                        fragment = new Assign_TbGr();
                        break;
                    case 2:
                        fragment = new Assign_Table();
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
