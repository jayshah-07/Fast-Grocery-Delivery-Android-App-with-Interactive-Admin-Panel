package com.grocery.food.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.grocery.food.Activity.AddressActivity;
import com.grocery.food.Activity.HomeActivity;
import com.grocery.food.Activity.RazerpayActivity;
import com.grocery.food.DataBase.DatabaseHelper;
import com.grocery.food.DataBase.MyCart;
import com.grocery.food.Model.RestResponse;
import com.grocery.food.Model.User;
import com.grocery.food.R;
import com.grocery.food.Utils.SessionManager;
import com.grocery.food.retrofit.APIClient;
import com.grocery.food.retrofit.GetResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.grocery.food.Utils.SessionManager.CURRUNCY;


public class OrderSumrryFragment extends Fragment implements GetResult.MyListener {

    @BindView(R.id.lvlordersumry)
    LinearLayout lvlordersumry;
    @BindView(R.id.txt_subtotal)
    TextView txtSubtotal;
    @BindView(R.id.txt_delivery)
    TextView txtDelivery;
    @BindView(R.id.txt_delevritital)
    TextView txtDelevritital;
    @BindView(R.id.txt_total)
    TextView txtTotal;
    @BindView(R.id.btn_cuntinus)
    TextView btnCuntinus;
    @BindView(R.id.lvlone)
    LinearLayout lvlone;
    @BindView(R.id.lvltwo)
    LinearLayout lvltwo;
    @BindView(R.id.txt_changeadress)
    TextView txtChangeadress;
    @BindView(R.id.txt_address)
    TextView txtAddress;

    // TODO: Rename and change types of parameters
    private String TIME;
    private String DATA;
    private String PAYMENT;
    double TOTAL;
    public static int paymentsucsses = 0;
    public static boolean ISORDER = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            TIME = getArguments().getString("TIME");
            DATA = getArguments().getString("DATE");
            PAYMENT = getArguments().getString("PAYMENT");
        }
    }

    DatabaseHelper databaseHelper;
    List<MyCart> myCarts;
    SessionManager sessionManager;
    Unbinder unbinder;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_sumrry, container, false);
        unbinder = ButterKnife.bind(this, view);
        databaseHelper = new DatabaseHelper(getActivity());
        sessionManager = new SessionManager(getActivity());
        user = sessionManager.getUserDetails("");
        txtAddress.setText(user.getHno() + "," + user.getSociety() + "," + user.getArea() + "," + user.getLandmark() + "," + user.getName());
        myCarts = new ArrayList<>();
        Cursor res = databaseHelper.getAllData();
        if (res.getCount() == 0) {
            // show message
            Toast.makeText(getActivity(), "NO DATA FOUND", Toast.LENGTH_LONG).show();
        }
        while (res.moveToNext()) {
            MyCart rModel = new MyCart();
            rModel.setID(res.getString(0));
            rModel.setPID(res.getString(1));
            rModel.setImage(res.getString(2));
            rModel.setTitle(res.getString(3));
            rModel.setWeight(res.getString(4));
            rModel.setCost(res.getString(5));
            rModel.setQty(res.getString(6));
            rModel.setDiscount(res.getInt(7));
            myCarts.add(rModel);
        }
        setJoinPlayrList(lvlordersumry, myCarts);


        return view;

    }

    private void setJoinPlayrList(LinearLayout lnrView, List<MyCart> myCarts) {

        lnrView.removeAllViews();
        final int[] count = {0};
        double[] totalAmount = {0};
        DatabaseHelper helper = new DatabaseHelper(getActivity());
        if (myCarts != null && myCarts.size() > 0) {
            for (int i = 0; i < myCarts.size(); i++) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                MyCart cart = myCarts.get(i);
                View view = inflater.inflate(R.layout.custome_sumrry, null);
                ImageView img_icon = view.findViewById(R.id.img_icon);
                TextView txt_title = view.findViewById(R.id.txt_title);
                TextView txt_priceitem = view.findViewById(R.id.txt_priceanditem);
                TextView txt_price = view.findViewById(R.id.txt_price);

                Glide.with(getActivity()).load(APIClient.Base_URL + "/" + cart.getImage()).thumbnail(Glide.with(getActivity()).load(R.drawable.lodingimage)).into(img_icon);

                double  res = (Double.parseDouble(cart.getCost()) / 100.0f)* cart.getDiscount();

                res = Integer.parseInt(cart.getCost()) - res;
                txt_priceitem.setText(sessionManager.getStringData(CURRUNCY) + res);
                txt_title.setText("" + cart.getTitle());

                MyCart myCart = new MyCart();
                myCart.setPID(cart.getPID());
                myCart.setImage(cart.getImage());
                myCart.setTitle(cart.getTitle());
                myCart.setWeight(cart.getWeight());
                myCart.setCost(cart.getCost());


                int qrt = helper.getCard(myCart.getPID(), myCart.getCost());
                txt_priceitem.setText(qrt + " item x " + sessionManager.getStringData(CURRUNCY) + res);

                double temp = res * qrt;
                txt_price.setText(sessionManager.getStringData(CURRUNCY) + temp);
                totalAmount[0] = totalAmount[0] + temp;


                lnrView.addView(view);
            }
        }
        txtSubtotal.setText(sessionManager.getStringData(CURRUNCY) + totalAmount[0]);
        if (PAYMENT.equalsIgnoreCase(getResources().getString(R.string.pic_myslf))) {
            txtDelivery.setVisibility(View.VISIBLE);
            txtDelevritital.setVisibility(View.VISIBLE);
            txtDelivery.setText(sessionManager.getStringData(CURRUNCY) + "0");
        } else {
            totalAmount[0] = totalAmount[0] + sessionManager.getIntData("dcharge");
            txtDelivery.setText(sessionManager.getStringData(CURRUNCY) + sessionManager.getIntData("dcharge"));
        }
        txtTotal.setText(sessionManager.getStringData(CURRUNCY) + totalAmount[0]);
        btnCuntinus.setText("Place Order - " + sessionManager.getStringData(CURRUNCY) + totalAmount[0]);
        TOTAL = totalAmount[0];
    }

    private void OrderPlace(JSONArray jsonArray) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            jsonObject.put("timesloat", TIME);
            jsonObject.put("ddate", DATA);
            jsonObject.put("total", TOTAL);
            jsonObject.put("p_method", PAYMENT);
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
        try {


        Log.e("response", "" + result.toString());
        lvlone.setVisibility(View.GONE);
        lvltwo.setVisibility(View.VISIBLE);
        Gson gson = new Gson();
        RestResponse response = gson.fromJson(result.toString(), RestResponse.class);
        Toast.makeText(getActivity(), "" + response.getResponseMsg(), Toast.LENGTH_LONG).show();
        if (response.getResult().equals("true")) {
            databaseHelper.DeleteCard();
            ISORDER = true;
//            startActivity(new Intent(OrderSummaryActivity.this,HomeActivity.class));

        }
        }catch (Exception e){

        }
    }


    @OnClick({R.id.txt_changeadress, R.id.btn_cuntinus, R.id.txt_trackorder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_changeadress:
                startActivity(new Intent(getActivity(), AddressActivity.class));
                break;
            case R.id.txt_trackorder:
                ClearFragment();
                break;
            case R.id.btn_cuntinus:
                if (PAYMENT.equalsIgnoreCase(getResources().getString(R.string.pay_online))) {
                    startActivity(new Intent(getActivity(), RazerpayActivity.class).putExtra("amount", TOTAL));
                } else {
                    sendorderServer();
                }

                break;
        }
    }

    public void ClearFragment() {
//        FragmentManager fm = getActivity().getSupportFragmentManager();
//        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
//            if (i == 1) {
//                Log.e("fragment ", "-->" + fm.getFragments().get(i));
//            } else {
//                fm.popBackStack();
//            }
//
//        }

        SessionManager sessionManager = new SessionManager(getActivity());
        User user = sessionManager.getUserDetails("");


        HomeActivity.getInstance().titleChange("Hello " + user.getName());

        MyOrderFragment homeFragment = new MyOrderFragment();
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getFragmentManager().beginTransaction().replace(R.id.fragment_frame, homeFragment).addToBackStack(null).commit();
    }

    private void sendorderServer() {
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
            startActivity(new Intent(getActivity(), AddressActivity.class));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sessionManager != null) {
            user = sessionManager.getUserDetails("");
            txtAddress.setText(user.getHno() + "," + user.getSociety() + "," + user.getArea() + "," + user.getLandmark() + "," + user.getName());
            setJoinPlayrList(lvlordersumry, myCarts);
        }
        if (paymentsucsses == 1) {
            paymentsucsses = 0;
            sendorderServer();
        }

    }


}
