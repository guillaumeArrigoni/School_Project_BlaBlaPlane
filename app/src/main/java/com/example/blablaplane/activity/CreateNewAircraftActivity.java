package com.example.blablaplane.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.blablaplane.R;
import com.example.blablaplane.fragments.ModifyProfile_dialogFragment;
import com.example.blablaplane.object.DataBase;
import com.example.blablaplane.object.aircraft.Aircraft;
import com.example.blablaplane.object.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CreateNewAircraftActivity extends AppCompatActivity {

    EditText name, type, nbPassenger, picture, maxRange;
    Button confirmButton, returnButton;
    CardView confirmCard, returnCard;
    DatabaseReference aircraftRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_aircraft);

        name = findViewById(R.id.Name);
        type = findViewById(R.id.type);
        nbPassenger = findViewById(R.id.numberPassenger);
        picture = findViewById(R.id.picture);
        maxRange = findViewById(R.id.maxRange);

        confirmButton = findViewById(R.id.RegisterButton);
        returnButton = findViewById(R.id.ReturnButton);
        confirmCard = findViewById(R.id.cardView4);
        returnCard = findViewById(R.id.cardView5);

        //TODO : adapt to now database of aircraft
        Aircraft aircraft = new Aircraft(name.getText().toString(), type.getText().toString(), Integer.parseInt(nbPassenger.getText().toString()), Integer.parseInt(picture.getText().toString()), Integer.parseInt(maxRange.getText().toString()));
        this.aircraftRef = DataBase.AIRCRAFT_REFERENCE.child(String.valueOf(aircraft.getId()));

        //TODO : idk if a database is created with all the aircraft, create it or find another solution
        View.OnClickListener confirm = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("") || type.getText().toString().equals("") || nbPassenger.getText().toString().equals("") || picture.getText().toString().equals("") || maxRange.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Merci de remplir toutes les sections", Toast.LENGTH_SHORT).show();
                } else {

                    aircraftRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(CreateNewAircraftActivity.this, "⚠️ Ce nom est déjà utilisé !", Toast.LENGTH_SHORT).show();
                            } else {
                                DataBase.AIRCRAFT_REFERENCE.child(aircraft.getId().setValue(aircraft).addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        // Store the user id in the cache of the app
                                        SharedPreferences preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("aircraft_id", String.valueOf(aircraft.getId()));
                                        editor.apply();

                                        // Go to the home page and display a confirmation message
                                        Toast.makeText(CreateNewAircraftActivity.this, "✅ L'appareil a bien été créé", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CreateNewAircraftActivity.this, SwitcherActivity.class);
                                        finish();
                                    } else {
                                        Toast.makeText(CreateNewAircraftActivity.this, "⚠️ Erreur lors de la création de l'appareil, veuillez réessayer", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(CreateNewAircraftActivity.this, "⚠️ Erreur lors de la création de l'appareil, veuillez réessayer", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        };

        View.OnClickListener returnView = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };


        this.returnButton.setOnClickListener(returnView);
        this.returnCard.setOnClickListener(returnView);

        this.confirmButton.setOnClickListener(confirm);
        this.confirmCard.setOnClickListener(confirm);

    }
}
