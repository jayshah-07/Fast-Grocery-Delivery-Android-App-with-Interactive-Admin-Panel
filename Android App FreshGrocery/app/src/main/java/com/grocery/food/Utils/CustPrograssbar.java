package com.grocery.food.Utils;

import android.app.ProgressDialog;
import android.content.Context;

public class CustPrograssbar {

    ProgressDialog progressDialog;

    public void PrograssCreate(Context context) {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                return;
            } else {
                progressDialog = new ProgressDialog(context);
                if (progressDialog != null && !progressDialog.isShowing()) {

                    progressDialog.setMessage("Progress...");
                    progressDialog.show();
                }
            }
        } catch (Exception e) {

        }
    }

    public void ClosePrograssBar() {
        if (progressDialog != null) {
            try {
                progressDialog.cancel();
            } catch (Exception e) {
            }
        }

    }
}
