package org.o7planning.agelegecafe;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import org.o7planning.agelegecafe.ui.order_view.canceledFragment;
import org.o7planning.agelegecafe.ui.order_view.doneProcessFragment;
import org.o7planning.agelegecafe.ui.order_view.onProcessFragment;
import org.o7planning.agelegecafe.ui.order_view.orderListFragment;

public class MainActivity1 extends AppCompatActivity {
    TabLayout tabLayout;
    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager=null;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            if(extras.getInt("id")==1)
            {
                setContentView(R.layout.order_view);
                tabLayout=(TabLayout)findViewById(R.id.tabLayout);
                frameLayout=(FrameLayout)findViewById(R.id.frameLayout);

                fragment = new orderListFragment();
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction =fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();

                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        Fragment fragment = null;
                        switch (tab.getPosition()) {
                            case 0:
                                fragment = new orderListFragment();
                                break;
                            case 1:
                                fragment = new onProcessFragment();
                                break;
                            case 2:
                                fragment = new doneProcessFragment();
                                break;
                            case 3:
                                fragment = new canceledFragment();
                                break;
                        }
                        FragmentManager fm = getSupportFragmentManager();
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
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.select();
            }
            else if(extras.getInt("id")==2)
            {
                TabLayout.Tab tab = tabLayout.getTabAt(1);
                tab.select();
            }
        }
    }
}
