package com.example.infostudentfbeta.CurrentLocation;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infostudentfbeta.Authentication.MainMenu;
import com.example.infostudentfbeta.Authentication.Register;
import com.example.infostudentfbeta.Chat.Main_Chat;
import com.example.infostudentfbeta.DigitalServices.DigitalActivity;
import com.example.infostudentfbeta.Mapa.MapsActivity;
import com.example.infostudentfbeta.NavigationDrawer.DrawerActivity;
import com.example.infostudentfbeta.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.util.ArrayList;
import java.util.List;

public class MapslocationActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener,  GoogleMap.OnPolylineClickListener{

    Button botonnavdrawer;
    ImageButton botonreset;

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    private static final String TAG = "MapslocationActivity";
    GoogleMap mgooglemap;
    private GeoApiContext mGeoApiContext = null;
    private ArrayList<PolylineData> mPolylinesData = new ArrayList<>();
    private ArrayList<Marker> mMarkers = new ArrayList<>();
    private Marker mSelectedMarker = null;

    String[] ubicaciones = {"Biblioteca Gabriel Garcia Marquez","División de registro y matrícula / Polideportivo","Cafeteria central","Centro de Atención de Estudiantes Ingeniería","Laboratorios Ingeniería","Canchas","Estadio Alfoso López Pumarejo","Facultad de Ingeniería","Eficio Takeuchi","Facultad de Derecho","Facultad de Medicina","Enfermeria","Departamento de Farmacia","Departamento de Quimica","Edificio de Ciencia y Tecnología CyT","Departamento de Sociologia","Facultad de Ciencias Económicas","Facultad de Odontología","Aulas de Ciencias Humanas","Departamento de Agronomía","Departamento de Lenguas Extranjeras","Postgrados de Ciencias Humanas","Aulas de Ingeniería","Facultad de Medicina Veterinaria y Zootecnia","Edificio Manuel Ancizar. Departamento de Geociencias","Edificio Francisco de Paula Santander. Diseño Gráfico","Edificio Antonio Nariño. Departamento de Ingeniería civil y Agrícola","Conservatorio de Música","Departamento de Cine y Televisión"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapslocation);

        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        bottomnav.setSelectedItemId(R.id.nav_map);
        bottomnav.setOnNavigationItemSelectedListener(navlistener);

        botonreset = findViewById(R.id.btn_reset_map);
        botonnavdrawer = findViewById(R.id.buttonNavDrawer);

        mMarkers.clear();
        mMarkers = new ArrayList<>();

        botonnavdrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DrawerActivity.class));
                finish();
            }
        });

        botonreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapslocationActivity.class));
                finish();
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        if (mGeoApiContext == null) {
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.api_string))
                    .build();
        }

    }

    /*public void calculateDirectionsfromListView(){
        for(Marker marker: mMarkers){
            if marker.getTitle().equals();

        }

    }*/

    public BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
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


    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;

                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(MapslocationActivity.this);

                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title("I am here");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        googleMap.addMarker(markerOptions);*/

        mgooglemap = googleMap;

        mgooglemap.setOnPolylineClickListener(this);

        mgooglemap.setOnInfoWindowClickListener(this);

        float zoom = 16;

        //Marker marker = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.634763,-74.021282)).title("test"));

        //MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(4.635763,-74.081282)).title("test");
        //MarkerOptions markerOptions1 = new MarkerOptions().position(new LatLng(4.635763,-74.081282)).title("test");
        //mgooglemap.addMarker(markerOptions);

        mgooglemap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(4.636755, -74.083459), zoom));

        mgooglemap.getUiSettings().setZoomControlsEnabled(true);

        mgooglemap.addMarker(new MarkerOptions().position(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude())).title("Mi posición actual").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_icon_inicio)));

        Marker BibliotecaCentral = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.635287, -74.083213)).title("Biblioteca Gabriel Garcia Marquez").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_biblioteca)));

        mMarkers.add(BibliotecaCentral);

        Marker DivisiondeRegistro = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.634913, -74.082886)).title("División de registro y matrícula / Polideportivo").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_registroymatricula)));
        mMarkers.add(DivisiondeRegistro);

        Marker CafeteriaCentral = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.634521, -74.082673)).title("Cafeteria central").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_cafeteriacentral)));

        mMarkers.add(CafeteriaCentral);

        Marker CADE = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.637980, -74.081423)).title("Centro de Atención de Estudiantes Ingeniería").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_cade)));

        mMarkers.add(CADE);

        Marker LaboratoriosIngenieria = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.639203, -74.082696)).title("Laboratorios Ingeniería").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_labing)));

        mMarkers.add(LaboratoriosIngenieria);

        Marker Canchas = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.640111, -74.08445)).title("Canchas").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_canchas)));

        mMarkers.add(Canchas);

        Marker Estadio = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.640324, -74.086445)).title("Estadio Alfonso López Pumarejo").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_estadio)));

        mMarkers.add(Estadio);

        Marker AuditorioLeon = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.635694, -74.082346)).title("Auditorio León de Greiff").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_auditorioleon)));

        mMarkers.add(AuditorioLeon);

        Marker FacultadIngenieria = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.637257, -74.082829)).title("Facultad de Ingeniería").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadingenieria)));

        mMarkers.add(FacultadIngenieria);

        Marker EdificioTakeuchi = mgooglemap.addMarker(new MarkerOptions().position( new LatLng(4.637910, -74.082178)).title("Eficio Takeuchi").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_edificiotakeuchi)));

        mMarkers.add(EdificioTakeuchi);

        Marker FacultadDerecho = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.635407, -74.083808)).title("Facultad de Derecho").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadderecho)));

        mMarkers.add(FacultadDerecho);

        Marker FacultadMedicina = mgooglemap.addMarker(new MarkerOptions().position( new LatLng(4.636358, -74.084425)).title("Facultad de Medicina").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadmedicina)));

        mMarkers.add(FacultadMedicina);

        Marker Enfermeria = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.635409, -74.084727)).title("Enfermeria").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_enfermeria)));

        mMarkers.add(Enfermeria);

        Marker DepartamentodeFarmacia = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.637208, -74.083834)).title("Departamento de Farmacia").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentofarmacia)));

        mMarkers.add(DepartamentodeFarmacia);

        Marker DepartamentodeQuimica = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.637550, -74.083971)).title("Departamento de Quimica").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentoquimica)));

        mMarkers.add(DepartamentodeQuimica);

        Marker EdiciodeCienciayTecnologia = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.638002, -74.084756)).title("Edificio de Ciencia y Tecnología CyT").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_cyt)));

        mMarkers.add(EdiciodeCienciayTecnologia);

        Marker DepartamentodeSociologia = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.634457, -74.083869)).title("Departamento de Sociologia").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentosociologia)));

        mMarkers.add(DepartamentodeSociologia);

        Marker FacultaddeCienciasEconomicas =mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.637158, -74.080753)).title("Facultad de Ciencias Económicas").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadcienciaseconomicas)));

        mMarkers.add(FacultaddeCienciasEconomicas);

        Marker FacultaddeOdontologia = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.634495, -74.085245)).title("Facultad de Odontología").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadodontologia)));

        mMarkers.add(FacultaddeOdontologia);

        Marker AulasdecienciasHumanas = mgooglemap.addMarker(new MarkerOptions().position( new LatLng(4.633995, -74.084707)).title("Aulas de Ciencias Humanas").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_aulascienciashumanas)));

        mMarkers.add(AulasdecienciasHumanas);

        Marker DepartamentodeAgronomia = mgooglemap.addMarker(new MarkerOptions().position( new LatLng(4.635961, -74.087253)).title("Departamento de Agronomía").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentoagronomia)));

        mMarkers.add(DepartamentodeAgronomia);

        Marker DepartamentodeLenguasExtranjeras = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.632915, -74.084465)).title("Departamento de Lenguas Extranjeras").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentolenguasextranjeras)));

        mMarkers.add(DepartamentodeLenguasExtranjeras);

        Marker PostgradosdeCienciasHumanas = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.634120, -74.086439)).title("Postgrados de Ciencias Humanas").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_postgradoscienciashumanas)));

        mMarkers.add(PostgradosdeCienciasHumanas);

        Marker AulasdeIngenieria = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.638428, -74.083662)).title("Aulas de Ingeniería").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_aulasingenieria)));

        mMarkers.add(AulasdeIngenieria);

        Marker FacultaddeVeterinaria = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.636079, -74.085379)).title("Facultad de Medicina Veterinaria y Zootecnia").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadmedicinaveterinariazootecnia)));

        mMarkers.add(FacultaddeVeterinaria);

        Marker DepartamentodeGeociencias = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.633723, -74.085475)).title("Edificio Manuel Ancizar. Departamento de Geociencias").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentogeociencias)));

        mMarkers.add(DepartamentodeGeociencias);

        Marker DiseñoGrafico = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.633289, -74.083207)).title("Edificio Francisco de Paula Santander. Diseño Gráfico").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_disenografico)));

        mMarkers.add(DiseñoGrafico);

        Marker IngenieriaCivilyAgricola = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.633556, -74.083997)).title("Edificio Antonio Nariño. Departamento de Ingeniería civil y Agrícola").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentoingenieriacivilagricola)));

        mMarkers.add(IngenieriaCivilyAgricola);

        Marker ConservatoriodeMusica = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.635764, -74.081283)).title("Conservatorio de Música").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_conservatoriomusica)));

        mMarkers.add(ConservatoriodeMusica);

        Marker DepartamentodeCineyTv = mgooglemap.addMarker(new MarkerOptions().position( new LatLng(4.640494, -74.085578)).title("Departamento de Cine y Televisión").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentocineytv)));

        mMarkers.add(DepartamentodeCineyTv);

        UiSettings settings = mgooglemap.getUiSettings();

        settings.setZoomControlsEnabled(true);

        mgooglemap.setMyLocationEnabled(true);

        //Toast.makeText(MapslocationActivity.this, "Numero: " + mMarkers.get(30), Toast.LENGTH_SHORT).show();

    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {

        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());

        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                break;

        }

    }

    private void resetSelectedMarker() {
        if (mSelectedMarker != null) {
            mSelectedMarker.setSnippet("Determinar ruta");
            mSelectedMarker = null;
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    private void calculateDirectionstomarker(Marker marker){
        /*for (marker)
         if reciclreyview.tostring().equals(marker.getTitle()){
           calculateDireections(marker)
        }*/
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
                new com.google.maps.model.LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())
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
                    polyline.setColor(ContextCompat.getColor(getApplicationContext(), R.color.Gray));
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
                polylineData.getPolyline().setColor(ContextCompat.getColor(getApplicationContext(), R.color.Paleta));
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

                mSelectedMarker.setSnippet("Duración:   " + polylineData.getLeg().duration);
                mSelectedMarker.showInfoWindow();

            } else {
                polylineData.getPolyline().setColor(ContextCompat.getColor(getApplicationContext(), R.color.Gray));
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
