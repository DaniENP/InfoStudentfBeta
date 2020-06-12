package com.example.infostudentfbeta.Mapa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

import com.example.infostudentfbeta.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentMaps extends SupportMapFragment implements OnMapReadyCallback {

    double lat, lon;

    GoogleMap mgooglemap;

    public FragmentMaps(){

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = super.onCreateView(layoutInflater, viewGroup, bundle);

        if(getArguments() != null){
            this.lat = getArguments().getDouble("lat");
            this.lon = getArguments().getDouble("lon");


        }

        getMapAsync(this);

        return rootView;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mgooglemap=googleMap;

        //LatLng latLngActual = new LatLng(lat,lon);

        LatLng CentrarMapa = new LatLng(4.636755, -74.083459);

        LatLng Bibliotecacentral = new LatLng(4.635287,-74.083213);

        LatLng DivisiondeRegistroyMatricula = new LatLng(4.634913,-74.082886);

        LatLng CafeteriaCentral = new LatLng(4.634521,-74.082673);

        LatLng CADE = new LatLng(4.637980,-74.081423);

        LatLng LaboratioriosIng = new LatLng(4.639203,-74.082696);

        LatLng canchas = new LatLng(4.640111,-74.08445);

        LatLng estadio = new LatLng(4.640324,-74.086445);


        float zoom = 16 ;

        mgooglemap.moveCamera(CameraUpdateFactory.newLatLngZoom(CentrarMapa,zoom));

        mgooglemap.getUiSettings().setZoomControlsEnabled(true);

        //mgooglemap.addMarker(new MarkerOptions().position(latLngActual).title("Mi posición actual"));

        mgooglemap.addMarker(new MarkerOptions().position(Bibliotecacentral).title("Biblioteca Gabriel Garcia Marquez").snippet("La Biblioteca Gabriel Garcia Marquez más conocida como la biblioteca central de la Universidad Nacional de Colombia, es la biblioteca más grande de la institución y se encuentra en la plaza central de la misma")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_biblioteca)));

        mgooglemap.addMarker(new MarkerOptions().position(DivisiondeRegistroyMatricula).title("División de registro y matrícula / Polideportivo")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_registroymatricula)));

        mgooglemap.addMarker(new MarkerOptions().position(CafeteriaCentral).title("Cafeteria central")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_cafeteriacentral)));

        mgooglemap.addMarker(new MarkerOptions().position(CADE).title("Centro de Atención de Estudiantes Ingeniería")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_cade)));

        mgooglemap.addMarker(new MarkerOptions().position(LaboratioriosIng).title("Laboratorios Ingeniería")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_labing)));

        mgooglemap.addMarker(new MarkerOptions().position(canchas).title("Canchas")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_canchas)));

        mgooglemap.addMarker(new MarkerOptions().position(estadio).title("Estadio Alfonso López Pumarejo")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_estadio)));

        UiSettings settings = mgooglemap.getUiSettings();

        settings.setZoomControlsEnabled(true);

        mgooglemap.setMyLocationEnabled(true);
    }


}
