package com.example.capstone_36team;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.capstone_36team.ui.dashboard.DashboardFragment;
import com.example.capstone_36team.ui.home.HomeFragment;
import com.example.capstone_36team.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static android.icu.text.DisplayContext.LENGTH_SHORT;
import static androidx.core.view.ViewCompat.setBackground;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MeowBottomNavigation navView = findViewById(R.id.bottomNavigation);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.add(new MeowBottomNavigation.Model(1,R.drawable.ic_room));
        navView.add(new MeowBottomNavigation.Model(2,R.drawable.ic_food));
        navView.add(new MeowBottomNavigation.Model(3,R.drawable.ic_info));

        navView.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });

        navView.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                Fragment fragment = null;
                switch (item.getId()){
                    case 1:
                        fragment = new HomeFragment();
                        LinearLayout layout = (LinearLayout)findViewById(R.id.container);
                        layout.setBackgroundResource(R.drawable.funitureimage);
                        break;

                    case 2:
                        fragment = new DashboardFragment();
                        LinearLayout layout2 = (LinearLayout)findViewById(R.id.container);
                        layout2.setBackgroundResource(R.drawable.refrimage);
                        break;

                    case 3:
                        fragment = new NotificationsFragment();
                        LinearLayout layout3 = (LinearLayout)findViewById(R.id.container);
                        layout3.setBackgroundResource(R.drawable.infoimage);
                        break;
                }

                loadFragment(fragment);


            }
        });
        navView.show(1,true);
    }

    private void loadFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment,fragment)
                .commit();

    }

    public void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }


}