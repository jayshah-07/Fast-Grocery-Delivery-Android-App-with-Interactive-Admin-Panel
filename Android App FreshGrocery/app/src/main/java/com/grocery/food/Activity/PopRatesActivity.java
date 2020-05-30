package com.grocery.food.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.grocery.food.Model.User;
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

public class PopRatesActivity extends Activity implements GetResult.MyListener {

    @BindView(R.id.ed_feedback)
    EditText edFeedback;
    @BindView(R.id.btn_submit)
    TextView btnSubmit;
    User user;
    SessionManager sessionManager;
    CustPrograssbar custPrograssbar;
    @BindView(R.id.img_vgood)
    ImageView imgVgood;
    @BindView(R.id.lvl_vgood)
    LinearLayout lvlVgood;
    @BindView(R.id.img_good)
    ImageView imgGood;
    @BindView(R.id.lvl_good)
    LinearLayout lvlGood;
    @BindView(R.id.img_notgood)
    ImageView imgNotgood;
    @BindView(R.id.lvl_notgood)
    LinearLayout lvlNotgood;
    String id;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_poprates);
        ButterKnife.bind(this);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        sessionManager = new SessionManager(PopRatesActivity.this);
        user = sessionManager.getUserDetails("");
        custPrograssbar = new CustPrograssbar();
        Intent intent = getIntent();
        id = intent.getStringExtra("oid");
    }

    private void UserFeedBack() {
        custPrograssbar.PrograssCreate(PopRatesActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("msg", edFeedback.getText().toString());
            jsonObject.put("rate", String.valueOf(selectRate));
            jsonObject.put("uid", user.getId());
            jsonObject.put("oid", id);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().Rates((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        custPrograssbar.ClosePrograssBar();
        Log.e("Feedback Respons", ":" + result);
        if (result != null) {
            try {
                JSONObject object = new JSONObject(result.toString());
                Toast.makeText(PopRatesActivity.this, "" + object.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                if (object.getString("Result").equals("true")) {
                    edFeedback.setText("");
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validation() {

//        if (edFeedback.getText().toString().isEmpty()) {
//            edFeedback.setError("Enter FeedBack");
//            return false;
//        }
        if (selectRate == 0) {
            Toast.makeText(PopRatesActivity.this, "Pleas select Rates..", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    int selectRate = 0;

    @OnClick({R.id.img_vgood, R.id.img_good, R.id.img_notgood, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_vgood:
                selectRate = 1;
                imgClick(1);
                break;
            case R.id.img_good:
                selectRate = 2;
                imgClick(2);
                break;
            case R.id.img_notgood:
                selectRate = 3;
                imgClick(3);
                break;
            case R.id.btn_submit:
                if (validation()) {
                    UserFeedBack();
                }
                break;
        }
    }

    public void imgClick(int item) {
        switch (item) {
            case 1:
                lvlGood.setBackgroundResource(R.drawable.rounded_search);
                lvlVgood.setBackgroundResource(R.drawable.rounded_search1);
                lvlNotgood.setBackgroundResource(R.drawable.rounded_search);
                break;
            case 2:
                lvlGood.setBackgroundResource(R.drawable.rounded_search1);
                lvlVgood.setBackgroundResource(R.drawable.rounded_search);
                lvlNotgood.setBackgroundResource(R.drawable.rounded_search);
                break;
            case 3:
                lvlGood.setBackgroundResource(R.drawable.rounded_search);
                lvlVgood.setBackgroundResource(R.drawable.rounded_search);
                lvlNotgood.setBackgroundResource(R.drawable.rounded_search1);
                break;
        }

    }
}
