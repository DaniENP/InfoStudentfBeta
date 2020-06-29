package com.example.infostudentfbeta.InformationFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.infostudentfbeta.CurrentLocation.MapslocationActivity;
import com.example.infostudentfbeta.R;

public class UnFragment  extends Fragment {

    Button b;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_un,container,false);

        /*b=v.findViewById(R.id.boton_prueba);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapslocationActivity.class));
            }
        });*/


        return v;
    }
}
