package com.grocery.food.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.grocery.food.Model.Times;
import com.grocery.food.R;
import com.grocery.food.Utils.SessionManager;
import com.grocery.food.Utils.Utiles;
import com.grocery.food.retrofit.APIClient;
import com.grocery.food.retrofit.GetResult;

import org.json.JSONObject;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class DeliveryTimeActivity extends BaseActivity implements GetResult.MyListener {

    @BindView(R.id.txt_today)
    TextView txtToday;
    @BindView(R.id.time1)
    RadioButton time1;

    @BindView(R.id.radioGroup1)
    RadioGroup radioGroup1;
    @BindView(R.id.cmd_today)
    CardView cmdToday;
    @BindView(R.id.txt_tomorrow)
    TextView txtTomorrow;
    @BindView(R.id.time11)
    RadioButton time11;

    @BindView(R.id.radioGroup2)
    RadioGroup radioGroup2;
    @BindView(R.id.cmd_tomorrow)
    CardView cmdTomorrow;
    @BindView(R.id.txt_continus)
    TextView txtContinus;
    @BindView(R.id.txt_selecttime)
    TextView txtSelecttime;
    @BindView(R.id.txt_selecttime2)
    TextView txtSelecttime2;

    SessionManager sessionManager;

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
        setContentView(R.layout.activity_delivery_time2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(DeliveryTimeActivity.this);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = (RadioButton) findViewById(checkedId);
                txtSelecttime.setText(Utiles.getDate() + rb.getText());
                sessionManager.setStringData("setdate", "Today - " + Utiles.getDate() + rb.getText());
                sessionManager.setStringData("orderdate", ""+Utiles.getDate());
                sessionManager.setStringData("orderTime", ""+rb.getText());
                Validation();
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = (RadioButton) findViewById(checkedId);
                txtSelecttime2.setText(Utiles.getNextDate() + " " + rb.getText());
                sessionManager.setStringData("setdate", "Tomorrow - " + Utiles.getNextDate() + rb.getText());
                sessionManager.setStringData("orderdate", ""+ Utiles.getNextDate());
                sessionManager.setStringData("orderTime", ""+rb.getText());
                Validation();
            }
        });
        getTimeSlot();
        Validation();
//        Chacktimecunditioin();
    }


    private void Chacktimecunditioin() {

        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        Log.e("Day", "" + currentHour);
        if (currentHour >= 5) {
            time1.setVisibility(View.GONE);
        }
        if (currentHour >= 7) {

        }
        if (currentHour >= 15) {

        }
        if (currentHour >= 17) {

        }
    }

    private boolean Validation() {
        if (time1.isChecked() || time11.isChecked()) {
            txtContinus.setVisibility(View.VISIBLE);
        } else {
            txtContinus.setVisibility(View.GONE);
        }
        return true;
    }

    @OnClick({R.id.txt_today, R.id.txt_tomorrow, R.id.txt_continus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_today:
                if (cmdToday.getVisibility() != View.VISIBLE) {
                    cmdToday.setVisibility(View.VISIBLE);
                } else {
                    cmdToday.setVisibility(View.GONE);
                }
                break;
            case R.id.txt_tomorrow:
                if (cmdTomorrow.getVisibility() != View.VISIBLE) {
                    cmdTomorrow.setVisibility(View.VISIBLE);
                } else {
                    cmdTomorrow.setVisibility(View.GONE);
                }
                break;
            case R.id.txt_continus:
                startActivity(new Intent(DeliveryTimeActivity.this, OrderSummaryActivity.class));
                break;
        }
        Validation();
    }

    private void getTimeSlot() {
        JSONObject jsonObject = new JSONObject();


        JsonParser jsonParser = new JsonParser();
        Call<JsonObject> call = APIClient.getInterface().getTimeslot((JsonObject) jsonParser.parse(jsonObject.toString()));
        GetResult getResult = new GetResult();
        getResult.setMyListener(this);
        getResult.callForLogin(call, "1");
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        Log.e("Response", "->" + result);
        Gson gson = new Gson();
        Times times = gson.fromJson(result.toString(), Times.class);

        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String s = times.getData().get(0).getMintime().substring(0, 2);
        Log.e("Times", "-->" + s);
        Log.e("currentH", "-->" + currentHour);
        if (currentHour >= Integer.parseInt(s)) {
//            time1.setVisibility(View.GONE);
        }

        time1.setText(" " + times.getData().get(0).getMintime() + " - " + times.getData().get(0).getMaxtime());
        time11.setText(" " + times.getData().get(1).getMintime() + " - " + times.getData().get(1).getMaxtime());
    }
}
