package com.example.infostudentfbeta.Mapa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.util.ArrayList;
import java.util.List;

public class FragmentMaps extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {


    private static final String TAG = "FragmentMaps" ;
    double lat, lon;
    GoogleMap mgooglemap;
    private GeoApiContext mGeoApiContext = null;


    public FragmentMaps(){

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = super.onCreateView(layoutInflater, viewGroup, bundle);

        if(getArguments() == null){
            this.lat = 4.7473155;
            this.lon = -74.0488611;
        }

        if(getArguments() != null){
            this.lat = getArguments().getDouble("lat");
            this.lon = getArguments().getDouble("lon");


        }

        if(mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.api_string))
                    .build();
        }

        getMapAsync(this);
        return rootView;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mgooglemap=googleMap;

        mgooglemap.setOnInfoWindowClickListener(this);

        LatLng latLngActual = new LatLng(lat,lon);

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

        mgooglemap.addMarker(new MarkerOptions().position(latLngActual).title("Mi posición actual"));

        mgooglemap.addMarker(new MarkerOptions().position(Bibliotecacentral).title("Biblioteca Gabriel Garcia Marquez").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_biblioteca)));

        mgooglemap.addMarker(new MarkerOptions().position(DivisiondeRegistroyMatricula).title("División de registro y matrícula / Polideportivo").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_registroymatricula)));

        mgooglemap.addMarker(new MarkerOptions().position(CafeteriaCentral).title("Cafeteria central").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_cafeteriacentral)));

        mgooglemap.addMarker(new MarkerOptions().position(CADE).title("Centro de Atención de Estudiantes Ingeniería").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_cade)));

        mgooglemap.addMarker(new MarkerOptions().position(LaboratioriosIng).title("Laboratorios Ingeniería").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_labing)));

        mgooglemap.addMarker(new MarkerOptions().position(canchas).title("Canchas").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_canchas)));

        mgooglemap.addMarker(new MarkerOptions().position(estadio).title("Estadio Alfonso López Pumarejo").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_estadio)));

        UiSettings settings = mgooglemap.getUiSettings();

        settings.setZoomControlsEnabled(true);

        mgooglemap.setMyLocationEnabled(true);

    }

    @Override
    public void onInfoWindowClick(final Marker marker) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(marker.getSnippet())
                .setCancelable(true)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick (@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id){
                        calculateDirections(marker);
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();

                    }

                });

        final AlertDialog alert = builder.create();
        alert.show();
    }


    private void calculateDirections(Marker marker){
        Log.d(TAG, "calculateDirections: calculating directions.");

        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(
                marker.getPosition().latitude,
                marker.getPosition().longitude
        );
        DirectionsApiRequest directions = new DirectionsApiRequest(mGeoApiContext);

        directions.alternatives(false);
        directions.origin(
                new com.google.maps.model.LatLng(lat,lon)
        );
        Log.d(TAG, "calculateDirections: destination: " + destination.toString());
        directions.destination(destination).setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                Log.d(TAG, "calculateDirections: routes: " + result.routes[0].toString());
                Log.d(TAG, "calculateDirections: duration: " + result.routes[0].legs[0].duration);
                Log.d(TAG, "calculateDirections: distance: " + result.routes[0].legs[0].distance);
                Log.d(TAG, "calculateDirections: geocodedWayPoints: " + result.geocodedWaypoints[0].toString());

                addPolylinesToMap(result);
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e(TAG, "calculateDirections: Failed to get directions: " + e.getMessage() );

            }
        });
    }
    private void addPolylinesToMap(final DirectionsResult result){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: result routes: " + result.routes.length);

                for(DirectionsRoute route: result.routes){
                    Log.d(TAG, "run: leg: " + route.legs[0].toString());
                    List<com.google.maps.model.LatLng> decodedPath = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());

                    List<LatLng> newDecodedPath = new ArrayList<>();

                    // This loops through all the LatLng coordinates of ONE polyline.
                    for(com.google.maps.model.LatLng latLng: decodedPath){

//                        Log.d(TAG, "run: latlng: " + latLng.toString());

                        newDecodedPath.add(new LatLng(
                                latLng.lat,
                                latLng.lng
                        ));
                    }
                    Polyline polyline =mgooglemap.addPolyline(new PolylineOptions().addAll(newDecodedPath));
                    polyline.setColor(ContextCompat.getColor(getActivity(), R.color.Paleta));
                    polyline.setClickable(true);

                }
            }
        });
    }

}
