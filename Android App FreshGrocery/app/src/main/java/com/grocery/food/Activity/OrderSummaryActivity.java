package com.grocery.food.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.grocery.food.DataBase.DatabaseHelper;
import com.grocery.food.Model.RestResponse;
import com.grocery.food.Model.User;
import com.grocery.food.R;
import com.grocery.food.Utils.SessionManager;
import com.grocery.food.retrofit.APIClient;
import com.grocery.food.retrofit.GetResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class OrderSummaryActivity extends BaseActivity implements GetResult.MyListener {

    SessionManager sessionManager;
    @BindView(R.id.txt_item)
    TextView txtItem;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_total)
    TextView txtTotal;
    @BindView(R.id.txtdtime)
    TextView txtdtime;
    @BindView(R.id.txt_oderplce)
    TextView txtOderplce;
    User user;
    DatabaseHelper databaseHelper;
    @BindView(R.id.txtaddress)
    TextView txtaddress;

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
        setContentView(R.layout.activity_order_summary);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionManager = new SessionManager(OrderSummaryActivity.this);
        user = sessionManager.getUserDetails("");
        databaseHelper = new DatabaseHelper(OrderSummaryActivity.this);
        txtItem.setText("Price(" + sessionManager.getStringData("item") + "items)");
        txtPrice.setText("" + sessionManager.getStringData("totel"));
        txtTotal.setText("" + sessionManager.getStringData("totel"));
        txtdtime.setText("" + sessionManager.getStringData("setdate"));
        txtaddress.setText(user.getHno() + "-" + user.getSociety() + "\n " + user.getArea() + "\n" + user.getLandmark() + "" + "\n" + user.getMobile());
    }

    @OnClick(R.id.txt_oderplce)
    public void onViewClicked() {
        Cursor res = databaseHelper.getAllData();
        if (res.getCount() == 0) {
            // show message
            return;
        }
        if (user.getArea() != null || user.getSociety() != null || user.getHno() != null || user.getMobile() != null) {
            JSONArray jsonArray = new JSONArray();
            while (res.moveToNext()) {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", res.getString(0));
                    jsonObject.put("pid", res.getString(1));
                    jsonObject.put("image", res.getString(2));
                    jsonObject.put("title", res.getString(3));
                    jsonObject.put("weight", res.getString(4));
                    jsonObject.put("cost", res.getString(5));
                    jsonObject.put("qty", res.getString(6));
                    jsonArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            OrderPlace(jsonArray);
            Log.e("JsonList", jsonArray.toString());
        } else {
            startActivity(new Intent(OrderSummaryActivity.this, ProfileActivity.class));
        }

    }

    private void OrderPlace(JSONArray jsonArray) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            jsonObject.put("timesloat", sessionManager.getStringData("orderTime"));
            jsonObject.put("ddate", sessionManager.getStringData("orderdate"));
            jsonObject.put("total", sessionManager.getIntData("inttotel"));
            jsonObject.put("pname", jsonArray);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().Order((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        Log.e("response", "" + result.toString());
        try {
            Gson gson = new Gson();
            RestResponse response = gson.fromJson(result.toString(), RestResponse.class);
            Toast.makeText(OrderSummaryActivity.this, "" + response.getResponseMsg(), Toast.LENGTH_LONG).show();
            if (response.getResult().equals("true")) {
                databaseHelper.DeleteCard();
//            startActivity(new Intent(OrderSummaryActivity.this,HomeActivity.class));
                finish();
            }
        }catch (Exception e){

        }

    }
}
