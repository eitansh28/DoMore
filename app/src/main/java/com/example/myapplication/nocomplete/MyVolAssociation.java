package com.example.myapplication.nocomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.VolunteeringAdapter;
import com.example.myapplication.objects.Association;
import com.example.myapplication.objects.Volunteering;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MyVolAssociation extends AppCompatActivity {
    public ArrayList<Volunteering> volList = new ArrayList<>();
    private ListView listView;
    private VolunteeringAdapter adapter;
    AlertDialog loadingDialog;
    Association association;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vol_association);
        listView = findViewById(R.id.myVolAss_ListView);
        adapter = new VolunteeringAdapter(this, volList);
        listView.setAdapter(adapter);

        // dialog "loading"
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        loadingDialog = builder.create();
        loadingDialog.show();
        // init association

        DocumentReference dr = db.collection("associations").document(auth.getCurrentUser().getUid());
        dr.get().addOnSuccessListener(task -> {
            association = task.toObject(Association.class);
            setUpData();
        });


    }

    private void setUpData() {
        if (this.association.getMy_volunteering().size() > 0){
            DocumentReference dr = this.db.collection("associations").document(association.getUid());
            Query query = this.db.collection("volunteering").whereEqualTo("association",dr);
            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Volunteering v = document.toObject(Volunteering.class);
                        this.volList.add(v);
                        this.adapter.notifyDataSetChanged();
                    }
                }
            });
        }
        else {
            Toast.makeText(MyVolAssociation.this,"עדיין לא הוספת התנדבויות",Toast.LENGTH_SHORT).show();
        }
        this.loadingDialog.dismiss();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.listView.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
            alert.setMessage("מה ברצונך לעשות עם התנדבות זו?");
            alert.setPositiveButton("ערוך", (dialog, which) -> {
                //Todo new dialog with full details
                dialog.dismiss();
            }).setNegativeButton("מחק", (dialog, which) -> {
                Volunteering v = (Volunteering) this.listView.getItemAtPosition(position);
                this.association.removeVolunteering(v);
                v.deleteVolunteering();
                this.volList.remove(v);
                this.adapter.notifyDataSetChanged();
                dialog.dismiss();
            }).create().show();
            //ToDo
        });

    }
}