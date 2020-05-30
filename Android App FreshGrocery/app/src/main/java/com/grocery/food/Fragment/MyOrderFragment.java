package com.grocery.food.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.grocery.food.Activity.MyOrderListActivity;
import com.grocery.food.Model.Order;
import com.grocery.food.Model.OrderDatum;
import com.grocery.food.Model.User;
import com.grocery.food.R;
import com.grocery.food.Utils.CustPrograssbar;
import com.grocery.food.Utils.SessionManager;
import com.grocery.food.retrofit.APIClient;
import com.grocery.food.retrofit.GetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.grocery.food.Utils.SessionManager.CURRUNCY;


public class MyOrderFragment extends Fragment implements GetResult.MyListener {

    @BindView(R.id.lvl_mycard)
    LinearLayout lvlMycard;
    Unbinder unbinder;
    @BindView(R.id.txt_nodata)
    TextView txtNodata;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    SessionManager sessionManager;
    User user;
    List<OrderDatum> orderData;
    CustPrograssbar custPrograssbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);
        custPrograssbar = new CustPrograssbar();
        sessionManager = new SessionManager(getActivity());
        user = sessionManager.getUserDetails("");
        orderData = new ArrayList<>();
        getHistry();
        unbinder = ButterKnife.bind(this, view);


        return view;


    }

    private void getHistry() {
        custPrograssbar.PrograssCreate(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getHistory((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        try {


            custPrograssbar.ClosePrograssBar();
            Gson gson = new Gson();
            Order order = gson.fromJson(result.toString(), Order.class);
            if (order.getResult().equals("true")) {
                orderData = new ArrayList<>();
                orderData.addAll(order.getData());
                if (orderData.size() != 0) {
                    txtNodata.setVisibility(View.GONE);
                    setJoinPlayrList(lvlMycard, orderData);
                } else {
                    txtNodata.setVisibility(View.VISIBLE);
                }

            }
        } catch (Exception e) {

        }
    }

    private void setJoinPlayrList(LinearLayout lnrView, List<OrderDatum> orderData) {

        if (lnrView == null) {
            return;
        }

        lnrView.removeAllViews();
        int a = 0;
        if (orderData != null && orderData.size() > 0) {
            for (int i = 0; i < orderData.size(); i++) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                a = a + 1;
                View view = inflater.inflate(R.layout.custome_oder, null);
                TextView txt_orderid = view.findViewById(R.id.txt_orderid);
                TextView txt_odate = view.findViewById(R.id.txt_odate);
                TextView txt_status = view.findViewById(R.id.txt_status);
                TextView txt_total = view.findViewById(R.id.txt_total);

                txt_orderid.setText("Order #" + orderData.get(i).getId());
                txt_odate.setText("  " + orderData.get(i).getOrderDate());
                txt_total.setText(sessionManager.getStringData(CURRUNCY) + orderData.get(i).getTotalamt());
                if (orderData.get(i).getStatus().equalsIgnoreCase("completed")) {
                    txt_status.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                txt_status.setText("" + orderData.get(i).getStatus());

                lnrView.addView(view);

                int finalI = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().startActivity(new Intent(getActivity(), MyOrderListActivity.class).putExtra("oid", orderData.get(finalI).getId()));

                    }
                });
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (orderData.size() > 0) {
            getHistry();
        }
    }

}
