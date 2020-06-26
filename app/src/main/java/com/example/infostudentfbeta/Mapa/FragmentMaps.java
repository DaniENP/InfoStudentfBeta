package com.example.infostudentfbeta.Mapa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
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

public class FragmentMaps extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnPolylineClickListener {


    private static final String TAG = "FragmentMaps";
    double lat, lon;
    GoogleMap mgooglemap;
    private GeoApiContext mGeoApiContext = null;
    private ArrayList<PolylineData> mPolylinesData = new ArrayList<>();
    private Marker mSelectedMarker = null;
    
    public FragmentMaps() {

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = super.onCreateView(layoutInflater, viewGroup, bundle);

        if (getArguments() == null) {
            this.lat = 4.635147;
            this.lon = -74.081095;
        }

        if (getArguments() != null) {
            this.lat = getArguments().getDouble("lat");
            this.lon = getArguments().getDouble("lon");


        }

        if (mGeoApiContext == null) {
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.api_string))
                    .build();
        }

        getMapAsync(this);
        return rootView;
    }


    private void resetSelectedMarker() {
        if(mSelectedMarker != null){
            mSelectedMarker.setSnippet("Determinar ruta");
            mSelectedMarker = null;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mgooglemap = googleMap;

        mgooglemap.setOnPolylineClickListener(this);

        mgooglemap.setOnInfoWindowClickListener(this);

        LatLng prueba = new LatLng(4.743315, -74.045261);

        LatLng latLngActual = new LatLng(lat, lon);

        LatLng CentrarMapa = new LatLng(4.636755, -74.083459);

        LatLng Bibliotecacentral = new LatLng(4.635287, -74.083213);

        LatLng DivisiondeRegistroyMatricula = new LatLng(4.634913, -74.082886);

        LatLng CafeteriaCentral = new LatLng(4.634521, -74.082673);

        LatLng CADE = new LatLng(4.637980, -74.081423);

        LatLng LaboratioriosIng = new LatLng(4.639203, -74.082696);

        LatLng canchas = new LatLng(4.640111, -74.08445);

        LatLng estadio = new LatLng(4.640324, -74.086445);

        LatLng AuditorioLeon = new LatLng(4.635694, -74.082346);

        LatLng FacultadIngenieria = new LatLng(4.637257, -74.082829);

        LatLng EdificioTakeuchi = new LatLng(4.637910, -74.082178);

        LatLng FacultadDerecho = new LatLng(4.635407, -74.083808);

        LatLng FacultadMedicina = new LatLng(4.636358, -74.084425);

        LatLng Enfermeria = new LatLng(4.635409, -74.084727);

        LatLng DepartamentoFarmacia = new LatLng(4.637208, -74.083834);

        LatLng DepartamentoQuimica = new LatLng(4.637550, -74.083971);

        LatLng CyT = new LatLng(4.638002, -74.084756);

        LatLng DepartamentoSociologia = new LatLng(4.634457, -74.083869);

        LatLng FacultadCienciasEconomicas = new LatLng(4.637158, -74.080753);

        LatLng FacultadOdontologia = new LatLng(4.634495, -74.085245);

        LatLng AulasCieniasHumanas = new LatLng(4.633995, -74.084707);

        LatLng DepartamentoAgronomia = new LatLng(4.635961, -74.087253);

        LatLng DepartamentoLenguasExtranjeras = new LatLng(4.632915, -74.084465);

        LatLng PostgradosCienciasHumanas = new LatLng(4.634120, -74.086439);

        LatLng AulasIngenieria = new LatLng(4.638428, -74.083662);

        LatLng FacultadMedicinaVeterinariaZootecnia = new LatLng(4.636079, -74.085379);

        LatLng DepartamentoGeociencias = new LatLng(4.633723, -74.085475);

        LatLng DepartamentoIngenieriaCivilAgricola = new LatLng(4.633556, -74.083997);

        LatLng DiseñoGrafico = new LatLng(4.633289, -74.083207);

        LatLng ConservatorioMusica = new LatLng(4.635764, -74.081283);

        LatLng DepartamentoCineyTv = new LatLng(4.640494, -74.085578);

        float zoom = 16;

        mgooglemap.moveCamera(CameraUpdateFactory.newLatLngZoom(CentrarMapa, zoom));

        mgooglemap.getUiSettings().setZoomControlsEnabled(true);

        mgooglemap.addMarker(new MarkerOptions().position(latLngActual).title("Mi posición actual").icon(bitmapDescriptorFromVector(this,R.mipmap.ic_icon_inicio))));

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

        mgooglemap.addMarker(new MarkerOptions().position(AuditorioLeon).title("Auditorio León de Greiff").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_auditorioleon)));

        mgooglemap.addMarker(new MarkerOptions().position(FacultadIngenieria).title("Facultad de Ingeniería").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadingenieria)));

        mgooglemap.addMarker(new MarkerOptions().position(EdificioTakeuchi).title("Eficio Takeuchi").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_edificiotakeuchi)));

        mgooglemap.addMarker(new MarkerOptions().position(FacultadDerecho).title("Facultad de Derecho").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadderecho)));

        mgooglemap.addMarker(new MarkerOptions().position(FacultadMedicina).title("Facultad de Medicina").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadmedicina)));

        mgooglemap.addMarker(new MarkerOptions().position(Enfermeria).title("Enfermeria").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_enfermeria)));

        mgooglemap.addMarker(new MarkerOptions().position(DepartamentoFarmacia).title("Departamento de Farmacia").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentofarmacia)));

        mgooglemap.addMarker(new MarkerOptions().position(DepartamentoQuimica).title("Departamento de Quimica").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentoquimica)));

        mgooglemap.addMarker(new MarkerOptions().position(CyT).title("Edificio de Ciencia y Tecnología CyT").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_cyt)));

        mgooglemap.addMarker(new MarkerOptions().position(DepartamentoSociologia).title("Departamento de Sociologia").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentosociologia)));

        mgooglemap.addMarker(new MarkerOptions().position(FacultadCienciasEconomicas).title("Facultad de Ciencias Económicas").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadcienciaseconomicas)));

        mgooglemap.addMarker(new MarkerOptions().position(FacultadOdontologia).title("Facultad de Odontología").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadodontologia)));

        mgooglemap.addMarker(new MarkerOptions().position(AulasCieniasHumanas).title("Aulas de Ciencias Humanas").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_aulascienciashumanas)));

        mgooglemap.addMarker(new MarkerOptions().position(DepartamentoAgronomia).title("Departamento de Agronomía").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentoagronomia)));

        mgooglemap.addMarker(new MarkerOptions().position(DepartamentoLenguasExtranjeras).title("Departamento de Lenguas Extranjeras").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentolenguasextranjeras)));

        mgooglemap.addMarker(new MarkerOptions().position(PostgradosCienciasHumanas).title("Postgrados de Ciencias Humanas").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_postgradoscienciashumanas)));

        mgooglemap.addMarker(new MarkerOptions().position(AulasIngenieria).title("Aulas de Ingeniería").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_aulasingenieria)));

        mgooglemap.addMarker(new MarkerOptions().position(FacultadMedicinaVeterinariaZootecnia).title("Facultad de Medicina Veterinaria y Zootecnia").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadmedicinaveterinariazootecnia)));

        mgooglemap.addMarker(new MarkerOptions().position(DepartamentoGeociencias).title("Edificio Manuel Ancizar. Departamento de Geociencias").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentogeociencias)));

        mgooglemap.addMarker(new MarkerOptions().position(DiseñoGrafico).title("Edificio Francisco de Paula Santander. Diseño Gráfico").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_disenografico)));

        mgooglemap.addMarker(new MarkerOptions().position(DepartamentoIngenieriaCivilAgricola).title("Edificio Antonio Nariño. Departamento de Ingeniería civil y Agrícola").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentoingenieriacivilagricola)));

        mgooglemap.addMarker(new MarkerOptions().position(ConservatorioMusica).title("Conservatorio de Música").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_conservatoriomusica)));

        mgooglemap.addMarker(new MarkerOptions().position(DepartamentoCineyTv).title("Departamento de Cine y Televisión").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentocineytv)));

        mgooglemap.addMarker(new MarkerOptions().position(prueba).title("more polylines?").snippet("Determinar ruta"));


        UiSettings settings = mgooglemap.getUiSettings();

        settings.setZoomControlsEnabled(true);

        mgooglemap.setMyLocationEnabled(true);

    }

    private BitmapDescriptor bitmapDescriptorFromVector (Context context, int vectorResId){

        Drawable vectorDrawable = ContextCompat.getDrawable(context,vectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());

        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);

    }


    @Override
    public void onInfoWindowClick(final Marker marker) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(marker.getSnippet())
                .setCancelable(true)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        resetSelectedMarker();
                        mSelectedMarker = marker;
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


    private void calculateDirections(Marker marker) {
        Log.d(TAG, "calculateDirections: calculating directions.");

        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(
                marker.getPosition().latitude,
                marker.getPosition().longitude
        );
        DirectionsApiRequest directions = new DirectionsApiRequest(mGeoApiContext);

        directions.alternatives(true);
        directions.origin(
                new com.google.maps.model.LatLng(lat, lon)
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
                Log.e(TAG, "calculateDirections: Failed to get directions: " + e.getMessage());

            }
        });
    }

    private void addPolylinesToMap(final DirectionsResult result) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: result routes: " + result.routes.length);
                if (mPolylinesData.size() > 0) {
                    for (PolylineData polylineData : mPolylinesData) {
                        polylineData.getPolyline().remove();
                    }
                    mPolylinesData.clear();
                    mPolylinesData = new ArrayList<>();
                }
                double duration = 999999999;
                for (DirectionsRoute route : result.routes) {
                    Log.d(TAG, "run: leg: " + route.legs[0].toString());
                    List<com.google.maps.model.LatLng> decodedPath = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());

                    List<LatLng> newDecodedPath = new ArrayList<>();

                    // This loops through all the LatLng coordinates of ONE polyline.
                    for (com.google.maps.model.LatLng latLng : decodedPath) {

//                        Log.d(TAG, "run: latlng: " + latLng.toString());

                        newDecodedPath.add(new LatLng(
                                latLng.lat,
                                latLng.lng
                        ));
                    }
                    Polyline polyline = mgooglemap.addPolyline(new PolylineOptions().addAll(newDecodedPath));
                    polyline.setColor(ContextCompat.getColor(getActivity(), R.color.Gray));
                    polyline.setClickable(true);
                    mPolylinesData.add(new PolylineData(polyline, route.legs[0]));

                    double tempDuration = route.legs[0].duration.inSeconds;
                    if (tempDuration < duration) {
                        duration = tempDuration;
                        onPolylineClick(polyline);
                        zoomRoute(polyline.getPoints());
                    }

                    //mSelectedMarker.setVisible(false);
                }
            }
        });
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        int index = 0;
        for (PolylineData polylineData : mPolylinesData) {
            index++;
            Log.d(TAG, "onPolylineClick: toString: " + polylineData.toString());
            if (polyline.getId().equals(polylineData.getPolyline().getId())) {
                polylineData.getPolyline().setColor(ContextCompat.getColor(getActivity(), R.color.Paleta));
                polylineData.getPolyline().setZIndex(1);

                LatLng endLocation = new LatLng(
                        //polylineData.getLeg().endLocation.lat,
                        //polylineData.getLeg().endLocation.lng);
                        mSelectedMarker.getPosition().latitude,
                        mSelectedMarker.getPosition().longitude);

                /*Marker marker = mgooglemap.addMarker(new MarkerOptions()
                        .position(endLocation)
                        .title(mSelectedMarker.getTitle())
                        .snippet("Duration:" + polylineData.getLeg().duration)
                );*/

                mSelectedMarker.setSnippet("Duración:   "+ polylineData.getLeg().duration);
                mSelectedMarker.showInfoWindow();

            } else {
                polylineData.getPolyline().setColor(ContextCompat.getColor(getActivity(), R.color.Gray));
                polylineData.getPolyline().setZIndex(0);
            }
        }

    }

    public void zoomRoute(List<LatLng> lstLatLngRoute) {

        if (mgooglemap == null || lstLatLngRoute == null || lstLatLngRoute.isEmpty()) return;

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for (LatLng latLngPoint : lstLatLngRoute)
            boundsBuilder.include(latLngPoint);

        int routePadding = 120;
        LatLngBounds latLngBounds = boundsBuilder.build();

        mgooglemap.animateCamera(
                CameraUpdateFactory.newLatLngBounds(latLngBounds, routePadding),
                600,
                null
        );
    }
}
