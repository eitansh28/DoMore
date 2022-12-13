package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        Button temp = findViewById(R.id.first5);
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    login.checkCollection(auth,db,v.getContext());
                }
            }
        });

        Button log = findViewById(R.id.open_activity_button);
        log.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, login.class)));
        Button association_reg = (Button) findViewById(R.id.first2);
        Button volunteer_reg = (Button) findViewById(R.id.first3);
        TextView hk = (TextView)findViewById(R.id.whatkind);
        hk.setVisibility(View.GONE);
        association_reg.setVisibility(View.GONE);
        volunteer_reg.setVisibility(View.GONE);
        Button reg = findViewById(R.id.first);
        reg.setVisibility(View.VISIBLE);
        reg.setOnClickListener(v -> {
            //when play is clicked show stop button and hide play button
            reg.setVisibility(View.GONE);
            hk.setVisibility(View.VISIBLE);
            association_reg.setVisibility(View.VISIBLE);
            volunteer_reg.setVisibility(View.VISIBLE);
        });
        association_reg.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, register_association.class)));
        volunteer_reg.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, register_volunteer.class)));
//        myButton.setOnClickListener(this::onMyButtonClicked);

    }
    private void onMyButtonClicked(View view) {
        if (view.getId() == R.id.first) {
            startActivity(new Intent(MainActivity.this, register_association.class));
        }
    }

}