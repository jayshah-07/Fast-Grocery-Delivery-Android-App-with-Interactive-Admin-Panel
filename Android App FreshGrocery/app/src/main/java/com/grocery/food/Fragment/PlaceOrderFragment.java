package com.grocery.food.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.grocery.food.Activity.HomeActivity;
import com.grocery.food.Model.Times;
import com.grocery.food.R;
import com.grocery.food.Utils.CustPrograssbar;
import com.grocery.food.retrofit.APIClient;
import com.grocery.food.retrofit.GetResult;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

public class PlaceOrderFragment extends Fragment implements View.OnClickListener, GetResult.MyListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.radiogroup)
    RadioGroup rdgTime;

    Unbinder unbinder;
    @BindView(R.id.radiogroup2)
    RadioGroup rdPayment;
    @BindView(R.id.btn_cuntinus)
    TextView btnCuntinus;
    @BindView(R.id.img_ldate)
    ImageView imgLdate;
    @BindView(R.id.txt_selectdate)
    TextView txtSelectdate;
    @BindView(R.id.img_rdate)
    ImageView imgRdate;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    int Day = 1;
    CustPrograssbar custPrograssbar;

    public PlaceOrderFragment() {
        // Required empty public constructor
    }

    public static PlaceOrderFragment newInstance(String param1, String param2) {
        PlaceOrderFragment fragment = new PlaceOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plase_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        custPrograssbar = new CustPrograssbar();
        getTimeSlot();
        setPaymentList();
        txtSelectdate.setText("" + getCurrentDate());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getTimeSlot() {
        custPrograssbar.PrograssCreate(getActivity());
        JSONObject jsonObject = new JSONObject();
        JsonParser jsonParser = new JsonParser();
        Call<JsonObject> call = APIClient.getInterface().getTimeslot((JsonObject) jsonParser.parse(jsonObject.toString()));
        GetResult getResult = new GetResult();
        getResult.setMyListener(this);
        getResult.callForLogin(call, "1");
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        try {

            custPrograssbar.ClosePrograssBar();
            RadioButton rdbtn = null;
            Log.e("Response", "->" + result);
            Gson gson = new Gson();
            Times times = gson.fromJson(result.toString(), Times.class);
            for (int i = 0; i < times.getData().size(); i++) {
                rdbtn = new RadioButton(getActivity());
                rdbtn.setId(View.generateViewId());
                rdbtn.setText(times.getData().get(i).getMintime() + " - " + times.getData().get(i).getMaxtime());
                rdbtn.setOnClickListener(this);
                rdgTime.addView(rdbtn);
            }
            rdgTime.check(rdbtn.getId());
        } catch (Exception e) {

        }
    }

    private void setPaymentList() {
//        String[] paymen = {"Pickup myself", "Cash on delivery", "Pay with online"};
        String[] paymen = {getResources().getString(R.string.pic_myslf), getResources().getString(R.string.cash_delivery), getResources().getString(R.string.pay_online)};
        RadioButton rdbtn = null;
        for (int i = 0; i < 3; i++) {
            rdbtn = new RadioButton(getActivity());
            rdbtn.setId(View.generateViewId());
            rdbtn.setText(paymen[i]);
            rdbtn.setOnClickListener(this);
            rdPayment.addView(rdbtn);
        }
        rdPayment.check(rdbtn.getId());

    }

    @OnClick()
    public void onViewClicked() {


    }

    @OnClick({R.id.img_ldate, R.id.img_rdate, R.id.btn_cuntinus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_ldate:
                minusDate(txtSelectdate.getText().toString());
                break;
            case R.id.img_rdate:
                addDate(txtSelectdate.getText().toString());
                break;
            case R.id.btn_cuntinus:

                int selectedId = rdgTime.getCheckedRadioButtonId();
                RadioButton selectTime = rdgTime.findViewById(selectedId);
                int selectedId2 = rdPayment.getCheckedRadioButtonId();
                RadioButton selectpayment = rdPayment.findViewById(selectedId2);
                Toast.makeText(getActivity(),
                        selectpayment.getText(), Toast.LENGTH_SHORT).show();
                rdgTime.check(selectedId);

                OrderSumrryFragment fragment = new OrderSumrryFragment();
                Bundle bundle = new Bundle();
                bundle.putString("DATE", txtSelectdate.getText().toString());
                bundle.putString("TIME", selectTime.getText().toString());
                bundle.putString("PAYMENT", selectpayment.getText().toString());
                fragment.setArguments(bundle);
                HomeActivity.getInstance().callFragment(fragment);
                break;
        }
    }

    private String getCurrentDate() {
        Date d = Calendar.getInstance().getTime();
        System.out.println("Current time => " + d);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(d);
        try {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, Day);  // number of days to add
            formattedDate = df.format(c.getTime());
            c.setTime(df.parse(formattedDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    private String addDate(String dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Date strDate = null;
        try {
            strDate = sdf.parse(dt);
            if ((System.currentTimeMillis() + 432000000) < strDate.getTime()) {
                Log.e("date change ", "--> 1");
                return dt;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        try {

            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, Day);  // number of days to add
            dt = sdf.format(c.getTime());
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Day++;
        txtSelectdate.setText("" + dt);
        return dt;
    }

    private String minusDate(String dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date strDate = null;
        try {
            strDate = sdf.parse(dt);
            if ((System.currentTimeMillis() + 86400000) > strDate.getTime()) {
                Log.e("date change ", "--> 1");
                return dt;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Day--;
        try {

            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, Day);  // number of days to add
            dt = sdf.format(c.getTime());
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtSelectdate.setText("" + dt);
        return dt;
    }
}
