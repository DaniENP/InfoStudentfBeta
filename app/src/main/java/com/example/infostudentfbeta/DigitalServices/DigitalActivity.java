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

        BottomNavigationView bottomnavmenu = findViewById(R.id.bottom_navigation);
        bottomnavmenu.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        bottomnavmenu.setSelectedItemId(R.id.nav_services);
        bottomnavmenu.setOnNavigationItemSelectedListener(nav_menulistener);

    }

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
