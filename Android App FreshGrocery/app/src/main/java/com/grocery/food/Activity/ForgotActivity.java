package com.grocery.food.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.grocery.food.Model.RestResponse;
import com.grocery.food.R;
import com.grocery.food.Utils.CustPrograssbar;
import com.grocery.food.Utils.SessionManager;
import com.grocery.food.retrofit.APIClient;
import com.grocery.food.retrofit.GetResult;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class ForgotActivity extends AppCompatActivity implements GetResult.MyListener {

    @BindView(R.id.ed_email)
    TextInputEditText edEmail;
    @BindView(R.id.ed_email1)
    TextInputLayout edEmail1;
    @BindView(R.id.ed_password)
    TextInputEditText edPassword;
    @BindView(R.id.ed_password1)
    TextInputLayout edPassword1;
    @BindView(R.id.ed_conpassword)
    TextInputEditText edConpassword;
    @BindView(R.id.ed_conpassword1)
    TextInputLayout edConpassword1;
    @BindView(R.id.ed_pin)
    TextInputEditText edPin;
    @BindView(R.id.ed_pin1)
    TextInputLayout edPin1;
    @BindView(R.id.btn_send)
    TextView btnSend;
    @BindView(R.id.btn_reenter)
    TextView btnReenter;
    @BindView(R.id.btn_submit)
    TextView btnSubmit;
    SessionManager sessionManager;
    CustPrograssbar custPrograssbar;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        ButterKnife.bind(this);
        custPrograssbar = new CustPrograssbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionManager = new SessionManager(ForgotActivity.this);

        if (sessionManager.getBooleanData("forgot")) {
            edEmail1.setVisibility(View.GONE);
            btnSend.setVisibility(View.GONE);
            btnReenter.setVisibility(View.VISIBLE);
            edPassword1.setVisibility(View.VISIBLE);
            edConpassword1.setVisibility(View.VISIBLE);
            edPin1.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.btn_send, R.id.btn_submit, R.id.btn_reenter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                ForgotPassword();
                break;
            case R.id.btn_reenter:
                sessionManager.setBooleanData("forgot", false);
                recreate();
                break;
            case R.id.btn_submit:
                if (validation()) {
                    SetPassword();
                }
                break;
        }
    }


    public boolean validation() {

        if (edPassword.getText().toString().isEmpty()) {
            edPassword.setError("Enter Name");
            return false;
        }
        if (edConpassword.getText().toString().isEmpty()) {
            edConpassword.setError("Enter Address");
            return false;
        }
        if (edPin.getText().toString().isEmpty()) {
            edPin.setError("Enter Landmark");
            return false;
        }
        if (!edPassword.getText().toString().equals(edConpassword.getText().toString())) {
            edPassword.setError("");
            edConpassword.setError("");
            return false;
        }


        return true;
    }

    private void ForgotPassword() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", edEmail.getText().toString());
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getForgot((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
            custPrograssbar.PrograssCreate(ForgotActivity.this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void SetPassword() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pin", edPin.getText().toString());
            jsonObject.put("password", edPassword.getText().toString());
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getPinmatch((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "2");
            custPrograssbar.PrograssCreate(ForgotActivity.this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        Log.e("result--", "" + result);
        custPrograssbar.ClosePrograssBar();
        try {
            if (callNo.equals("1")) {
                Gson gson = new Gson();
                RestResponse response = gson.fromJson(result.toString(), RestResponse.class);
                Toast.makeText(ForgotActivity.this, "" + response.getResponseMsg(), Toast.LENGTH_LONG).show();
                if (response.getResult().equals("true")) {
                    sessionManager.setBooleanData("forgot", true);
                    edEmail1.setVisibility(View.GONE);
                    btnSend.setVisibility(View.GONE);
                    btnReenter.setVisibility(View.VISIBLE);
                    edPassword1.setVisibility(View.VISIBLE);
                    edConpassword1.setVisibility(View.VISIBLE);
                    edPin1.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);
                }
            } else if (callNo.equals("2")) {
                Gson gson = new Gson();
                RestResponse response = gson.fromJson(result.toString(), RestResponse.class);
                Toast.makeText(ForgotActivity.this, "" + response.getResponseMsg(), Toast.LENGTH_LONG).show();
                if (response.getResult().equals("true")) {
                    sessionManager.setBooleanData("forgot", false);
                    finish();

                }
            }
        } catch (Exception e) {

        }
    }
}
