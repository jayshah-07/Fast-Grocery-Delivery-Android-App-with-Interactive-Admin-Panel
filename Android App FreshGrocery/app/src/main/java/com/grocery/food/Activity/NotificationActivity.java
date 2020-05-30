package com.grocery.food.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.grocery.food.Model.Noti;
import com.grocery.food.Model.ResNoti;
import com.grocery.food.Model.User;
import com.grocery.food.R;
import com.grocery.food.Utils.SessionManager;
import com.grocery.food.retrofit.APIClient;
import com.grocery.food.retrofit.GetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;


public class NotificationActivity extends AppCompatActivity implements GetResult.MyListener {

    @BindView(R.id.lvl_myorder)
    LinearLayout lvlMyorder;
    @BindView(R.id.txt_notiempty)
    TextView txtNotiempty;
    User user;
    SessionManager sessionManager;


    List<Noti> notiList;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        context=this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notification");
        sessionManager = new SessionManager(NotificationActivity.this);
        user = sessionManager.getUserDetails("");

        getNotification();

    }

    private void getNotification() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getNoti((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setNotiList(LinearLayout lnrView, List<Noti> list) {
        lnrView.removeAllViews();
        int a = 0;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Noti noti = list.get(i);
                LayoutInflater inflater = LayoutInflater.from(NotificationActivity.this);
                a = a + 1;
                View view = inflater.inflate(R.layout.custome_noti, null);
                LinearLayout lvl_bgcolor = view.findViewById(R.id.lvl_bgcolor);
                TextView txt_name = view.findViewById(R.id.txt_orderid);
                ImageView imgNoti = view.findViewById(R.id.imag_noti);

                txt_name.setText(" " + noti.getTitle());
                Glide.with(this).asBitmap().load(APIClient.Base_URL + noti.getImg()).placeholder(R.drawable.empty_noti).into(imgNoti);
//
                if (noti.getISread() == 0) {
                    lvl_bgcolor.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                } else {
                    lvl_bgcolor.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
                lnrView.addView(view);
                lvl_bgcolor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        noti.setISread(1);
                        startActivity(new Intent(NotificationActivity.this, NotificationDetailsActivity.class).putExtra("myclass", noti));
                    }
                });
            }
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        try {


            Log.e("Response", "-->" + result.toString());
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                ResNoti resNoti = gson.fromJson(result.toString(), ResNoti.class);
                if (resNoti.getResult().equalsIgnoreCase("true")) {
                    notiList = new ArrayList<>();
                    notiList = resNoti.getData();
                    if (notiList.size() != 0) {
                        txtNotiempty.setVisibility(View.GONE);
                        setNotiList(lvlMyorder, notiList);
                    } else {
                        txtNotiempty.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(NotificationActivity.this, resNoti.getResponseMsg(), Toast.LENGTH_LONG).show();
                }
//            getNotificationUpdate();
            } else if (callNo.equalsIgnoreCase("2")) {
//            txtNoti.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (notiList != null && notiList.size() != 0)
            setNotiList(lvlMyorder, notiList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
