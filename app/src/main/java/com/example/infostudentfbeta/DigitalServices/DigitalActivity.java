package com.example.infostudentfbeta.DigitalServices;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.infostudentfbeta.Authentication.MainMenu;
import com.example.infostudentfbeta.Chat.Main_Chat;
import com.example.infostudentfbeta.DigitalFragments.Siafragment;
import com.example.infostudentfbeta.DigitalFragments.Sibufragment;
import com.example.infostudentfbeta.DigitalFragments.Sinsufragment;
import com.example.infostudentfbeta.DigitalFragments.Siunfragment;
import com.example.infostudentfbeta.Mapa.MapsActivity;
import com.example.infostudentfbeta.R;
import com.example.infostudentfbeta.TabLayout.SectionsPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.tabs.TabLayout;

public class DigitalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digitalservices);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        /*BottomNavigationView bottomnavdigital = findViewById(R.id.bottom_navigation_digital);
        bottomnavdigital.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        bottomnavdigital.setOnNavigationItemSelectedListener(nav_digitallistener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_digital,
                new Siafragment()).commit();*/

        BottomNavigationView bottomnavmenu = findViewById(R.id.bottom_navigation);
        bottomnavmenu.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        bottomnavmenu.setSelectedItemId(R.id.nav_services);
        bottomnavmenu.setOnNavigationItemSelectedListener(nav_menulistener);

    }

    /*BottomNavigationView.OnNavigationItemSelectedListener nav_digitallistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment =  null;

                    switch (item.getItemId()){
                        case R.id.nav_sia:
                            selectedFragment = new Siafragment();
                            break;
                        case R.id.nav_sinsu:
                            selectedFragment = new Sinsufragment();
                            break;
                        case R.id.nav_siun:
                            selectedFragment = new Siunfragment();
                            break;
                        case R.id.nav_sibu:
                            selectedFragment = new Sibufragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_digital,
                            selectedFragment).commit();

                    return true;
                }
            };*/

    public BottomNavigationView.OnNavigationItemSelectedListener nav_menulistener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.nav_map:
                            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                            finish();
                            break;
                        case R.id.nav_profile:
                            startActivity(new Intent(getApplicationContext(), MainMenu.class));
                            finish();
                            break;
                        case R.id.nav_chat:
                            startActivity(new Intent(getApplicationContext(), Main_Chat.class));
                            finish();
                            break;
                    }

                    return true;
                }
            };

}
