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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
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

public class MapslocationActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnPolylineClickListener {
    Button buttonlistview;
    Button botonnavdrawer;
    ImageButton botonreset;

    private long backPressedTime;
    private Toast backToast;

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    private static final String TAG = "MapslocationActivity";
    GoogleMap mgooglemap;
    private GeoApiContext mGeoApiContext = null;
    private ArrayList<PolylineData> mPolylinesData = new ArrayList<>();
    private ArrayList<Marker> mMarkers = new ArrayList<>();
    private Marker mSelectedMarker = null;

    private SearchView searchView;

    private ListView list;
    private String Locations[] = {"102.Biblioteca Gabriel Garcia Marquez", "103.Cafeteria central", "103.Division de registro y matricula / Polideportivo","104.Auditorio Leon de Greiff", "201.Facultad de Derecho", "205.Departamento de Sociologia", "210.Facultad de Odontologia", "212.Aulas de Ciencias Humanas", "214.Edificio Antonio Narino. Departamento de Ingenieria civil y Agricola", "217.Edificio Francisco de Paula Santander. Diseno Grafico", "224.Edificio Manuel Ancizar. Departamento de Geociencias", "225.Postgrados de Ciencias Humanas", "228.Enfermeria", "231.Departamento de Lenguas Extranjeras", "305.Conservatorio de Musica", "310.Facultad de Ciencias Economicas", "401.Facultad de Ingenieria", "404.Eficio Takeuchi", "408.Centro de Atencion de Estudiantes Ingenieria", "411.Laboratorios Ingenieria", "450.Departamento de Farmacia", "451.Departamento de Quimica", "453.Aulas de Ingenieria", "454.Edificio de Ciencia y Tecnología CyT", "471.Facultad de Medicina", "481.Facultad de Medicina Veterinaria y Zootecnia", "500.Departamento de Agronomia", "Canchas", "701.Departamento de Cine y Television", "731.Estadio Alfonso Lopez Pumarejo"};
    private String Intents[] = {"Determinar Ruta a edficio 102", "Determinar Ruta a Cafeteria Central", "Determinar Ruta a edficio 103", "Determinar Ruta a edificio 104", "Determinar Ruta a edficio 201", "Determinar Ruta a edficio 205", "Determinar Ruta a edficio 210", "Determinar Ruta a edficio 212", "Determinar Ruta a edficio 214", "Determinar Ruta a edficio 217", "Determinar Ruta a edficio 224", "Determinar Ruta a edficio 225", "Determinar Ruta a edficio 228", "Determinar Ruta a edficio 231", "Determinar Ruta a edficio 305", "Determinar Ruta a edficio 310", "Determinar Ruta a edficio 401", "Determinar Ruta a edficio 404", "Determinar Ruta a edficio 408", "Determinar Ruta a edficio 411", "Determinar Ruta a edficio 450", "Determinar Ruta a edficio 451", "Determinar Ruta a edficio 453", "Determinar Ruta a edficio 454", "Determinar Ruta a edficio 471", "Determinar Ruta a edficio 481", "Determinar Ruta a edficio 500", "Determinar Ruta a Canchas", "Determinar Ruta a edficio 701", "Determinar Ruta a edficio 731"};
    private int imgs[] = {R.drawable.biblioteca, R.drawable.cafeteriacentral, R.drawable.registroymatricula,R.drawable.auditorioleon, R.drawable.facultadderecho, R.drawable.departamentosociologia, R.drawable.facultadodontologia, R.drawable.aulascienciashumanas, R.drawable.departamentoingenieriacivilagricola, R.drawable.disenografico, R.drawable.departamentogeociencas, R.drawable.postgradoscienciashumanas, R.drawable.enfermeria, R.drawable.departamentolenguasextranjeras, R.drawable.conservatoriomusica, R.drawable.facultadcienciaseconomicas, R.drawable.facultadingenieria, R.drawable.edificiotakeuchi, R.drawable.cade, R.drawable.labing, R.drawable.departamentofarmacia, R.drawable.departamentoquimica, R.drawable.aulasingenieria, R.drawable.cyt, R.drawable.facultadmedicina, R.drawable.facultadmedicinaveterinariazootecnia, R.drawable.departamentoagronomia, R.drawable.canchas, R.drawable.departamentocineytv, R.drawable.estadio};


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
        buttonlistview = findViewById(R.id.button_listview);
        list = findViewById(R.id.list1);

        MyAdapter adapter = new MyAdapter(this, Locations, imgs, Intents);
        list.setOnItemClickListener(listlistener);

        mMarkers.clear();
        mMarkers = new ArrayList<>();

        buttonlistview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setAdapter(adapter);
            }
        });


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

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String myLocations[];
        String myIntents[];
        int[] imgs;

        MyAdapter(Context c, String[] locations, int[] imgs, String[] intents) {
            super(c, R.layout.inflaterlistview, R.id.Locations, locations);

            this.context = c;
            this.imgs = imgs;
            this.myLocations = locations;
            this.myIntents = intents;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.inflaterlistview, parent, false);
            ImageView images = row.findViewById(R.id.logo);
            TextView myTitle = row.findViewById(R.id.Locations);
            TextView myDescription = row.findViewById(R.id.Intent);
            images.setImageResource(imgs[position]);
            myTitle.setText(Locations[position]);
            myDescription.setText(Intents[position]);
            return row;
        }
    }


    public ListView.OnItemClickListener listlistener =
            new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (Marker marker : mMarkers) {
                        if (marker.getTitle().equals(Locations[position])) {
                            list.setAdapter(null);
                            onInfoWindowClick(marker);
                        }
                    }
                }
            };

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

        mgooglemap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(4.636755, -74.083459), zoom));

        mgooglemap.getUiSettings().setZoomControlsEnabled(true);

        mgooglemap.addMarker(new MarkerOptions().position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).title("Mi posicion actual").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_icon_inicio)));

        Marker BibliotecaCentral = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.635287, -74.083213)).title("102.Biblioteca Gabriel Garcia Marquez").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_biblioteca)));

        mMarkers.add(BibliotecaCentral);

        Marker DivisiondeRegistro = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.634913, -74.082886)).title("103.Division de registro y matricula / Polideportivo").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_registroymatricula)));
        mMarkers.add(DivisiondeRegistro);

        Marker CafeteriaCentral = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.634521, -74.082673)).title("103.Cafeteria central").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_cafeteriacentral)));

        mMarkers.add(CafeteriaCentral);

        Marker CADE = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.637980, -74.081423)).title("408.Centro de Atencion de Estudiantes Ingenieria").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_cade)));

        mMarkers.add(CADE);

        Marker LaboratoriosIngenieria = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.639203, -74.082696)).title("411.Laboratorios Ingenieria").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_labing)));

        mMarkers.add(LaboratoriosIngenieria);

        Marker Canchas = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.640111, -74.08445)).title("Canchas").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_canchas)));

        mMarkers.add(Canchas);

        Marker Estadio = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.640324, -74.086445)).title("731.Estadio Alfonso Lopez Pumarejo").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_estadio)));

        mMarkers.add(Estadio);

        Marker AuditorioLeon = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.635694, -74.082346)).title("104.Auditorio Leon de Greiff").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_auditorioleon)));

        mMarkers.add(AuditorioLeon);

        Marker FacultadIngenieria = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.637257, -74.082829)).title("401.Facultad de Ingenieria").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadingenieria)));

        mMarkers.add(FacultadIngenieria);

        Marker EdificioTakeuchi = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.637910, -74.082178)).title("404.Eficio Takeuchi").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_edificiotakeuchi)));

        mMarkers.add(EdificioTakeuchi);

        Marker FacultadDerecho = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.635407, -74.083808)).title("201.Facultad de Derecho").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadderecho)));

        mMarkers.add(FacultadDerecho);

        Marker FacultadMedicina = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.636358, -74.084425)).title("471.Facultad de Medicina").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadmedicina)));

        mMarkers.add(FacultadMedicina);

        Marker Enfermeria = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.635409, -74.084727)).title("228.Enfermeria").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_enfermeria)));

        mMarkers.add(Enfermeria);

        Marker DepartamentodeFarmacia = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.637208, -74.083834)).title("450.Departamento de Farmacia").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentofarmacia)));

        mMarkers.add(DepartamentodeFarmacia);

        Marker DepartamentodeQuimica = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.637550, -74.083971)).title("451.Departamento de Quimica").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentoquimica)));

        mMarkers.add(DepartamentodeQuimica);

        Marker EdiciodeCienciayTecnologia = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.638002, -74.084756)).title("454.Edificio de Ciencia y Tecnología CyT").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_cyt)));

        mMarkers.add(EdiciodeCienciayTecnologia);

        Marker DepartamentodeSociologia = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.634457, -74.083869)).title("205.Departamento de Sociologia").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentosociologia)));

        mMarkers.add(DepartamentodeSociologia);

        Marker FacultaddeCienciasEconomicas = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.637158, -74.080753)).title("310.Facultad de Ciencias Economicas").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadcienciaseconomicas)));

        mMarkers.add(FacultaddeCienciasEconomicas);

        Marker FacultaddeOdontologia = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.634495, -74.085245)).title("210.Facultad de Odontologia").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadodontologia)));

        mMarkers.add(FacultaddeOdontologia);

        Marker AulasdecienciasHumanas = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.633995, -74.084707)).title("212.Aulas de Ciencias Humanas").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_aulascienciashumanas)));

        mMarkers.add(AulasdecienciasHumanas);

        Marker DepartamentodeAgronomia = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.635961, -74.087253)).title("500.Departamento de Agronomia").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentoagronomia)));

        mMarkers.add(DepartamentodeAgronomia);

        Marker DepartamentodeLenguasExtranjeras = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.632915, -74.084465)).title("231.Departamento de Lenguas Extranjeras").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentolenguasextranjeras)));

        mMarkers.add(DepartamentodeLenguasExtranjeras);

        Marker PostgradosdeCienciasHumanas = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.634120, -74.086439)).title("225.Postgrados de Ciencias Humanas").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_postgradoscienciashumanas)));

        mMarkers.add(PostgradosdeCienciasHumanas);

        Marker AulasdeIngenieria = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.638428, -74.083662)).title("453.Aulas de Ingenieria").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_aulasingenieria)));

        mMarkers.add(AulasdeIngenieria);

        Marker FacultaddeVeterinaria = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.636079, -74.085379)).title("481.Facultad de Medicina Veterinaria y Zootecnia").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_facultadmedicinaveterinariazootecnia)));

        mMarkers.add(FacultaddeVeterinaria);

        Marker DepartamentodeGeociencias = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.633723, -74.085475)).title("224.Edificio Manuel Ancizar. Departamento de Geociencias").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentogeociencias)));

        mMarkers.add(DepartamentodeGeociencias);

        Marker DisenoGrafico = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.633289, -74.083207)).title("217.Edificio Francisco de Paula Santander. Diseno Grafico").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_disenografico)));

        mMarkers.add(DisenoGrafico);

        Marker IngenieriaCivilyAgricola = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.633556, -74.083997)).title("214.Edificio Antonio Narino. Departamento de Ingenieria civil y Agricola").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_departamentoingenieriacivilagricola)));

        mMarkers.add(IngenieriaCivilyAgricola);

        Marker ConservatoriodeMusica = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.635764, -74.081283)).title("305.Conservatorio de Musica").snippet("Determinar ruta")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_conservatoriomusica)));

        mMarkers.add(ConservatoriodeMusica);

        Marker DepartamentodeCineyTv = mgooglemap.addMarker(new MarkerOptions().position(new LatLng(4.640494, -74.085578)).title("701.Departamento de Cine y Television").snippet("Determinar ruta")
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
        builder.setMessage(marker.getSnippet() + " a " + marker.getTitle())
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

    private void calculateDirectionstomarker(Marker marker) {
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

    @Override
    public void onBackPressed() {
        if (list.getAdapter() != null) {
            list.setAdapter(null);
        } else {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToast.cancel();
                super.onBackPressed();
                return;
            } else {
                backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
                backToast.show();
            }
            backPressedTime = System.currentTimeMillis();
        }
    }
}
