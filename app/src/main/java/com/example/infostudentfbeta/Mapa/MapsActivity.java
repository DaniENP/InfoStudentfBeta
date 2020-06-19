package com.example.infostudentfbeta.Mapa;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.infostudentfbeta.Authentication.MainMenu;
import com.example.infostudentfbeta.Chat.Main_Chat;
import com.example.infostudentfbeta.DigitalServices.DigitalActivity;
import com.example.infostudentfbeta.NavigationDrawer.DrawerActivity;
import com.example.infostudentfbeta.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class MapsActivity extends AppCompatActivity {

    TextView tvMensaje;
    Button botonnavdrawer;


    private static final long MIN_TIME = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        bottomnav.setSelectedItemId(R.id.nav_map);
        bottomnav.setOnNavigationItemSelectedListener(navlistener);


        botonnavdrawer = findViewById(R.id.buttonNavDrawer);
        tvMensaje = findViewById(R.id.tvMensaje);

        botonnavdrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DrawerActivity.class));
                finish();
            }
        });

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000 );
        } else {
            iniciarLocalizacion();
        }


    }

    private void iniciarLocalizacion(){
        LocationManager services = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Localizacion local =  new Localizacion();

        local.setMapsActivity(this,tvMensaje);

        final boolean gpsEnabled = services.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(!gpsEnabled){

            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000 );
            return;
        }

        //services.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME,1,local);
        //services.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,1,local);
        tvMensaje.setText("Localización agregada");


    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]grantResults){
        if(requestCode == 1000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                iniciarLocalizacion();
                return;
            }

        }

    }

    public BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.nav_services:
                            startActivity(new Intent(getApplicationContext(), DigitalActivity.class));
                            finish();
                            break;
                        case R.id.nav_chat:
                            startActivity(new Intent(getApplicationContext(), Main_Chat.class));
                            finish();
                            break;
                        case R.id.nav_profile:
                            startActivity(new Intent(getApplicationContext(), MainMenu.class));
                            finish();
                            break;
                    }

                    return true;
                }
            };



}
