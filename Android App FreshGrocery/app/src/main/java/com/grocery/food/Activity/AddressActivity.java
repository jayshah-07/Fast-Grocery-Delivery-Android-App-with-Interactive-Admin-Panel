package com.grocery.food.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.grocery.food.Model.Area;
import com.grocery.food.Model.AreaD;
import com.grocery.food.Model.LoginUser;
import com.grocery.food.Model.User;
import com.grocery.food.R;
import com.grocery.food.Utils.SessionManager;
import com.grocery.food.Utils.Utiles;
import com.grocery.food.retrofit.APIClient;
import com.grocery.food.retrofit.GetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class AddressActivity extends BaseActivity implements GetResult.MyListener {

    @BindView(R.id.ed_username)
    TextInputEditText edUsername;

    @BindView(R.id.ed_landmark)
    TextInputEditText edLandmark;

    @BindView(R.id.ed_alternatmob)
    TextInputEditText edAlternatmob;


    SessionManager sessionManager;
    User user;

    @BindView(R.id.ed_hoousno)
    TextInputEditText edHoousno;
    @BindView(R.id.ed_society)
    TextInputEditText edSociety;

    @BindView(R.id.ed_pinno)
    TextInputEditText edPinno;


    String areaSelect;
    List<AreaD> areaDS = new ArrayList<>();
    @BindView(R.id.spinner)
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(AddressActivity.this);
        user = sessionManager.getUserDetails("");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaSelect = areaDS.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        GetArea();
        setcountaint(user);
    }

    private void setcountaint(User user) {
        edUsername.setText("" + user.getName());
        edHoousno.setText("" + user.getHno());
        edSociety.setText("" + user.getSociety());
//        edArea.setText("" + user.getArea());
        edPinno.setText("" + user.getPincode());
        edLandmark.setText("" + user.getLandmark());

        edAlternatmob.setText("" + user.getMobile());

    }

    private void GetArea() {
        JSONObject jsonObject = new JSONObject();
        JsonParser jsonParser = new JsonParser();
        Call<JsonObject> call = APIClient.getInterface().getArea((JsonObject) jsonParser.parse(jsonObject.toString()));
        GetResult getResult = new GetResult();
        getResult.setMyListener(this);
        getResult.callForLogin(call, "2");

    }

    @OnClick(R.id.txt_save)
    public void onViewClicked() {
        if (validation()) {
            UpdateUser();
        }
    }

    private void UpdateUser() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            jsonObject.put("name", edUsername.getText().toString());
            jsonObject.put("hno", edHoousno.getText().toString());
            jsonObject.put("society", edSociety.getText().toString());
            jsonObject.put("area", areaSelect);
            jsonObject.put("landmark", edLandmark.getText().toString());
            jsonObject.put("pincode", edPinno.getText().toString());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("mobile", edAlternatmob.getText().toString());
            jsonObject.put("password", user.getPassword());
            jsonObject.put("imei", Utiles.getIMEI(AddressActivity.this));
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().UpdateProfile((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        Log.e("response", "-->" + result);
        try {

        if (callNo.equalsIgnoreCase("1")) {
            Gson gson = new Gson();
            LoginUser response = gson.fromJson(result.toString(), LoginUser.class);
            Toast.makeText(AddressActivity.this, "" + response.getResponseMsg(), Toast.LENGTH_LONG).show();
            if (response.getResult().equals("true")) {
                sessionManager.setIntData("dcharge", response.getdCharge());
                sessionManager.setUserDetails("", response.getUser());
                finish();
            }
        } else if (callNo.equalsIgnoreCase("2")) {
            Gson gson = new Gson();
            Area area = gson.fromJson(result.toString(), Area.class);
            areaDS = area.getData();
            List<String> Arealist = new ArrayList<>();
            for (int i = 0; i < areaDS.size(); i++) {
                if (areaDS.get(i).getStatus().equalsIgnoreCase("1")) {
                    Arealist.add(areaDS.get(i).getName());
                }
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Arealist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
            int spinnerPosition = dataAdapter.getPosition(user.getArea());
            spinner.setSelection(spinnerPosition);
        }
        }catch (Exception e){

        }
    }

    public boolean validation() {
        if (edUsername.getText().toString().isEmpty()) {
            edUsername.setError("Enter Name");
            return false;
        }
        if (edHoousno.getText().toString().isEmpty()) {
            edHoousno.setError("Enter House No");
            return false;
        }
        if (edSociety.getText().toString().isEmpty()) {
            edSociety.setError("Enter Society");
            return false;
        }

        if (edLandmark.getText().toString().isEmpty()) {
            edLandmark.setError("Enter Landmark");
            return false;
        }
        if (edPinno.getText().toString().isEmpty()) {
            edPinno.setError("Enter Pincode");
            return false;
        }

        if (edAlternatmob.getText().toString().isEmpty() || !validatePhoneNumber(edAlternatmob.getText().toString())) {
            edAlternatmob.setError("Enter Valid mobile");
            return false;
        }
        return true;
    }



    private static boolean validatePhoneNumber(String phoneNo) {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) return true;
            //validating phone number with -, . or spaces
        else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
            //validating phone number with extension length from 3 to 5
        else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
            //validating phone number where area code is in braces ()
        else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
            //return false if nothing matches the input
        else return false;

    }
}
