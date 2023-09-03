package com.example.myapplication.model;



import com.example.myapplication.db.UserDB;
import com.example.myapplication.activitiy.LoginActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginModel {
    UserDB udb = new UserDB();
    LoginActivity activity;

    public LoginModel(LoginActivity activity){
        this.activity = activity;
    }

    public void login(String email, String pass){
        udb.login(email, pass, task -> {
            if (task.isSuccessful())
                udb.CheckUserType(task1 -> {
                    DocumentSnapshot document = task1.getResult();
                    if (document.exists())
                        activity.goVolHome();
                    else
                        activity.goAssHome();
                });
            else
                activity.FailureLogin();
        });
    }
}
