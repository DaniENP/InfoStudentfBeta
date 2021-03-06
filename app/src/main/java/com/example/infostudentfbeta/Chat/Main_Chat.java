package com.example.infostudentfbeta.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.infostudentfbeta.Authentication.MainMenu;
import com.example.infostudentfbeta.CurrentLocation.MapslocationActivity;
import com.example.infostudentfbeta.DigitalServices.DigitalActivity;
import com.example.infostudentfbeta.Mapa.MapsActivity;
import com.example.infostudentfbeta.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

/**
 * Class that contains methods of the chat menu
 */
public class Main_Chat extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    DatabaseReference reference;
    String userId;
    ArrayList<String> arrayList;

    EditText e1;
    ListView l1;
    ArrayAdapter<String> adapter;
    String name;

    private long backPressedTime;
    private Toast backToast;

    /**
     * Method to get username data, create chat rooms and set it on screen.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__chat);

        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        bottomnav.setSelectedItemId(R.id.nav_chat);
        bottomnav.setOnNavigationItemSelectedListener(navlistener);

        e1 = (EditText) findViewById(R.id.editText);
        l1 = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        l1.setAdapter(adapter);


        reference = FirebaseDatabase.getInstance().getReference().getRoot();


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            /**
             * Method to get and check username data
             * @param documentSnapshot
             * @param e
             */
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    name = (documentSnapshot.getString("fName"));

                }else {
                    Log.d("tag", "onEvent: Documento no existe");
                    name = "Juan Sebastian";
                }
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();


                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());

                }

                arrayList.clear();
                arrayList.addAll(set);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Main_Chat.this, "No network connectivity", Toast.LENGTH_SHORT).show();
            }
        });


        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Method to deploy chat room selected by user.
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(Main_Chat.this, chatroom.class);
                intent.putExtra("room_name", ((TextView) view).getText().toString());
                intent.putExtra("user_name", name);
                startActivity(intent);

            }
        });


    }


    /**
     * Method to add  a chat room and save it on firebase.
     * @param v
     */
    public void insert_data(View v) {

        Map<String, Object> map = new HashMap<>();
        map.put(e1.getText().toString(), "");
        reference.updateChildren(map);

    }

    public BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                /**
                 * Method to navigate between activities.
                 *
                 * @param item Menu item for the navigation button
                 * @return return boolean to keep navigation in progress
                 */
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.nav_map:
                            startActivity(new Intent(getApplicationContext(), MapslocationActivity.class));
                            finish();
                            break;
                        case R.id.nav_services:
                            startActivity(new Intent(getApplicationContext(), DigitalActivity.class));
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

    @Override
    public void onBackPressed() {
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

    //commit
}
