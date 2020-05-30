package com.grocery.food.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;

import com.grocery.food.Model.User;
import com.grocery.food.R;
import com.grocery.food.Utils.SessionManager;
import com.grocery.food.Utils.Utiles;

import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;

public class FirstActivity extends ActivityManagePermission {
    private static int SPLASH_TIME_OUT = 2000;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        sessionManager = new SessionManager(FirstActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                if (Utiles.internetChack()) {
                    User user = sessionManager.getUserDetails("");
                    if (user != null) {
                        Intent i = new Intent(FirstActivity.this, HomeActivity.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(FirstActivity.this, InfoActivity.class);
                        startActivity(i);
                    }
                    finish();
                } else {
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(FirstActivity.this);
                    //Setting message manually and performing action on button click
                    builder.setMessage("Please Check Your Internet Connection")
                            .setCancelable(false)
                            .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }
        }, SPLASH_TIME_OUT);



//        askCompactPermissions(new String[]{
//                PermissionUtils.Manifest_READ_PHONE_STATE}, new PermissionResult() {
//            @Override
//            public void permissionGranted() {
//
//
//
//            }
//
//            @Override
//            public void permissionDenied() {
//            }
//
//            @Override
//            public void permissionForeverDenied() {
//            }
//        });
    }



}
