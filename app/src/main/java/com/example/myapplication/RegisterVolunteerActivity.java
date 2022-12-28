package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.model.RegisterVoluModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterVolunteerActivity extends Activity {
    private RegisterVoluModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_volunteer);
        model = new RegisterVoluModel(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button register = findViewById(R.id.regvol);
        register.setOnClickListener(view -> {
            EditText name =  findViewById(R.id.volunteerName);
            EditText email =  findViewById(R.id.volEmailAddress);
            EditText password = findViewById(R.id.volPassword);
            EditText phone = findViewById(R.id.volPhone);
            Spinner cities = findViewById(R.id.cities_spinner);
            model.registerNewVolunteer(email.getText().toString(),password.getText().toString(),
                    name.getText().toString(),cities.getSelectedItem().toString(),
                    Integer.parseInt(phone.getText().toString()));
        });
    }
    public void goHomeVolunteer() {
        startActivity(new Intent(RegisterVolunteerActivity.this,HomeVolunteerActivity.class));
    }
    public void registerFailed() {
        Toast.makeText(RegisterVolunteerActivity.this, "Registration failed!", Toast.LENGTH_LONG).show();
    }
}
