package com.example.infostudentfbeta.NavigationDrawer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.infostudentfbeta.CurrentLocation.MapslocationActivity;
import com.example.infostudentfbeta.InformationFragments.BibliotecasFragment;
import com.example.infostudentfbeta.InformationFragments.DivisionFragment;
import com.example.infostudentfbeta.InformationFragments.BienestarFragment;
import com.example.infostudentfbeta.Mapa.MapsActivity;
import com.example.infostudentfbeta.R;
import com.google.android.material.navigation.NavigationView;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new DivisionFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_división);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_división:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DivisionFragment()).commit();
                break;
            case R.id.nav_bienestar:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BienestarFragment()).commit();
                break;
            case R.id.nav_bibliotecas:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BibliotecasFragment()).commit();
                break;
            case R.id.nav_mapa:
                startActivity(new Intent(getApplicationContext(), MapslocationActivity.class));
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);

        }else {
            startActivity(new Intent(getApplicationContext(), MapslocationActivity.class));
            finish();
        }
    }

}
