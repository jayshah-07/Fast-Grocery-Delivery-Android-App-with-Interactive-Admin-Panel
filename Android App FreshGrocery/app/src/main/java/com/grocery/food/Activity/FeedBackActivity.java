package com.grocery.food.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
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

public class FeedBackActivity extends BaseActivity implements GetResult.MyListener {

    @BindView(R.id.ed_feedback)
    TextInputEditText edFeedback;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    User user;
    SessionManager sessionManager;
    CustPrograssbar custPrograssbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(FeedBackActivity.this);
        user = sessionManager.getUserDetails("");
        custPrograssbar = new CustPrograssbar();
    }

    private void UserFeedBack() {
        custPrograssbar.PrograssCreate(FeedBackActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("msg", edFeedback.getText().toString());
            jsonObject.put("rate", String.valueOf(ratingBar.getRating()));
            jsonObject.put("uid", user.getId());
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().SendFeed((JsonObject) jsonParser.parse(jsonObject.toString()));
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
        try {
            if (result != null) {
                try {
                    JSONObject object = new JSONObject(result.toString());
                    Toast.makeText(FeedBackActivity.this, "" + object.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                    if (object.getString("Result").equals("true")) {
                        ratingBar.setRating(0.0f);
                        edFeedback.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {

        }
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (validation()) {
            UserFeedBack();
        }
    }

    private boolean validation() {

        if (edFeedback.getText().toString().isEmpty()) {
            edFeedback.setError("Enter FeedBack");
            return false;
        }
        if (String.valueOf(ratingBar.getRating()).equals("0.0")) {
            Toast.makeText(FeedBackActivity.this, "Give Rating", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
