package com.example.infostudentfbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.infostudentfbeta.Mapa.MapsActivity;

public class MainActivity extends AppCompatActivity {

    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = (Button) findViewById(R.id.botonMapa);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps();


            }
        });


    }

    public void openMaps(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }


}
