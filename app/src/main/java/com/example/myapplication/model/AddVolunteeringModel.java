package com.example.myapplication.model;

import com.example.myapplication.db.AssociationDB;
import com.example.myapplication.db.VolunteeringDB;
import com.example.myapplication.activitiy.AddVolunteeringActivity;
import com.example.myapplication.model.objects.Association;
import com.example.myapplication.model.objects.Volunteering;
import java.util.Date;
import java.util.HashMap;

public class AddVolunteeringModel {
    VolunteeringDB vdb = new VolunteeringDB();
    AssociationDB adb = new AssociationDB();
    Association association;
    AddVolunteeringActivity activity;

    public AddVolunteeringModel(AddVolunteeringActivity activity){
        this.activity = activity;
        initAssociation();
    }

    private void initAssociation() {
        adb.getAssociation(documentSnapshot -> association = documentSnapshot.toObject(Association.class));
    }

    public void addNewVol(String title, String city, String phone, Date from, Date un, int num_volunteers){
        Volunteering volunteering = new Volunteering(vdb.generateNewID(),association.getName(),
                title,city, association.getCategory(), phone, from,un,
                adb.getRefernce(association.getUid()), num_volunteers, num_volunteers, new HashMap<>());
        vdb.setVolunteering(volunteering);
        association.getMy_volunteering().put(volunteering.getUid(),vdb.getDocumentReference(volunteering));
        adb.updateAssociation(association);
        activity.goToHome();
    }
}
