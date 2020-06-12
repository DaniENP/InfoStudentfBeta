package com.example.infostudentfbeta.Mapa;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.infostudentfbeta.R;

public class Localizacion implements LocationListener {

    MapsActivity MapsActivity;
    TextView tvMensaje;


    public MapsActivity getMapsActivity(){
        return MapsActivity;

    }

    public void setMapsActivity(MapsActivity MapsActivity, TextView tvMensaje){
        this.MapsActivity = MapsActivity;
        this.tvMensaje = tvMensaje;
    }

    @Override
    public void onLocationChanged(Location location) {

        String texto = "Mi Ubicación es : \n" +
                "Latitud" + location.getLatitude() + "\n" +
                "Longitud" + location.getLongitude();

        tvMensaje.setText(texto);

        mapa(location.getLatitude(), location.getLongitude());


    }

    public void mapa(double lat, double lon){

        FragmentMaps fragment = new FragmentMaps();

        Bundle bundle = new Bundle();
        bundle.putDouble("lat", new Double(lat));
        bundle.putDouble("lon", new Double(lon));
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getMapsActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, fragment, null);
        fragmentTransaction.commit();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

        switch (status) {
            case LocationProvider.AVAILABLE:
                Log.d("debug","LocationProvider.AVALIABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("debug","LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("debug","LocationProvider.TEMPORARILY_UNAVALIABLE");
                break;



        }

    }

    @Override
    public void onProviderEnabled(String provider) {
        tvMensaje.setText("GPS activado");

    }

    @Override
    public void onProviderDisabled(String provider) {
        tvMensaje.setText("GPS Desactivado");

    }
}
