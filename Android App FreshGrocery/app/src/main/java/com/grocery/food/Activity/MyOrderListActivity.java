package com.grocery.food.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.grocery.food.Model.MyOrder;
import com.grocery.food.Model.Productinfo;
import com.grocery.food.Model.RestResponse;
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

import static com.grocery.food.Utils.SessionManager.CURRUNCY;

public class MyOrderListActivity extends BaseActivity implements GetResult.MyListener {

    User user;
    SessionManager sessionManager;
    @BindView(R.id.lvl_myorder)
    LinearLayout lvlMyorder;
    @BindView(R.id.txttotal)
    TextView txttotal;
    @BindView(R.id.txt_dcharge)
    TextView txtDcharge;
    @BindView(R.id.txtsubtotal)
    TextView txtSubtotal;
    @BindView(R.id.txtstatus)
    TextView txtstatus;
    @BindView(R.id.order_date)
    TextView orderDate;
    @BindView(R.id.timesloat)
    TextView timesloat;
    @BindView(R.id.txtpayment)
    TextView txtpayment;

    String oid;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("OrderList ");
        sessionManager = new SessionManager(MyOrderListActivity.this);
        user = sessionManager.getUserDetails("");
        Intent intent = getIntent();
        id = intent.getStringExtra("oid");
        getHistry(id);
    }

    private void getHistry(String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("uid", user.getId());
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getPlist((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getOdercancle() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("oid", id);
            jsonObject.put("uid", user.getId());
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getOdercancle((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        try {


            if (callNo.equalsIgnoreCase("1")) {
                List<Productinfo> list = new ArrayList<>();
                Gson gson = new Gson();
                MyOrder myOrder = gson.fromJson(result.toString(), MyOrder.class);
                if (myOrder.getResult().equals("true")) {
                    list.addAll(myOrder.getProductinfo());
                    int temp = Integer.parseInt(myOrder.getTotalAmt()) - Integer.parseInt(myOrder.getdCharge());
                    if (myOrder.getStatus().equalsIgnoreCase(getResources().getString(R.string.pic_myslf))) {
                        txtpayment.setVisibility(View.VISIBLE);
                    } else {
                        txtpayment.setVisibility(View.GONE);

                    }
                    txtstatus.setText("Status : " + myOrder.getStatus());
                    orderDate.setText("Order Date : " + myOrder.getOrderDate());
                    timesloat.setText("Times : " + myOrder.getTimesloat());
                    txtDcharge.setText("Delivery: " + sessionManager.getStringData(CURRUNCY) + myOrder.getdCharge());
                    txtSubtotal.setText("Total: " + sessionManager.getStringData(CURRUNCY) + temp);
                    txttotal.setText("Total Amt : " + sessionManager.getStringData(CURRUNCY) + myOrder.getTotalAmt());
                    oid = myOrder.getmOrderid();
                    if (myOrder.getmIsrated().equalsIgnoreCase("No") && myOrder.getStatus().equalsIgnoreCase("completed")) {
                        item.setVisible(true);

                    } else {
                        item.setVisible(false);

                    }
                    if (myOrder.getStatus().equalsIgnoreCase("pending") || myOrder.getStatus().equalsIgnoreCase(getResources().getString(R.string.pic_myslf))) {
                        itemC.setVisible(true);
                    } else {
                        itemC.setVisible(false);
                    }
                    setOrderList(lvlMyorder, list);
                }
            } else if (callNo.equalsIgnoreCase("2")) {
                Log.e("Response", "-->" + result);
                Gson gson = new Gson();
                RestResponse response = gson.fromJson(result.toString(), RestResponse.class);
                Toast.makeText(MyOrderListActivity.this, response.getResponseMsg(), Toast.LENGTH_LONG).show();
                if (response.getResult().equalsIgnoreCase("true")) {
                    finish();
                }
            }

        } catch (Exception e) {

        }
    }

    private void setOrderList(LinearLayout lnrView, List<Productinfo> list) {

        lnrView.removeAllViews();
        int a = 0;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                LayoutInflater inflater = LayoutInflater.from(MyOrderListActivity.this);
                a = a + 1;
                View view = inflater.inflate(R.layout.custome_myoder, null);
                ImageView img_icon = view.findViewById(R.id.img_icon);
                TextView txt_name = view.findViewById(R.id.txt_name);
                TextView txt_qty = view.findViewById(R.id.txt_qty);
                TextView txt_weight = view.findViewById(R.id.txt_weight);
                TextView txt_price = view.findViewById(R.id.txt_price);

                Glide.with(MyOrderListActivity.this).load(APIClient.Base_URL + "/" + list.get(i).getProductImage()).thumbnail(Glide.with(MyOrderListActivity.this).load(R.drawable.lodingimage)).into(img_icon);
                txt_name.setText(" " + list.get(i).getProductName());
                txt_qty.setText(" Qty :" + list.get(i).getProductQty());
                txt_weight.setText(" " + list.get(i).getProductWeight());

                double ress = (Double.parseDouble(list.get(i).getProductPrice()) * list.get(i).getDiscount()) / 100;
                ress = Double.parseDouble(list.get(i).getProductPrice()) - ress;

                txt_price.setText(sessionManager.getStringData(CURRUNCY) + ress);

                lnrView.addView(view);

            }
        }
    }

    MenuItem item;
    MenuItem itemC;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rates_menu, menu);
        item = menu.findItem(R.id.item_rates);
        itemC = menu.findItem(R.id.item_cancel);
        item.setVisible(false);
        itemC.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.item_rates:
                startActivity(new Intent(MyOrderListActivity.this, RatesActivity.class).putExtra("oid", oid));
                return true;
            case R.id.item_cancel:
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(this);
                //Setting message manually and performing action on button click
                builder.setMessage("Are you sour cancel this order ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                getOdercancle();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                alert.show();


                return true;
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
