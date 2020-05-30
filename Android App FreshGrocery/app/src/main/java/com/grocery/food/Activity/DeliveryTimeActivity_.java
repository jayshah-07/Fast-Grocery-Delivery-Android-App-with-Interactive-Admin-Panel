package com.grocery.food.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.grocery.food.R;
import com.grocery.food.Utils.SessionManager;
import com.grocery.food.Utils.Utiles;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeliveryTimeActivity_ extends AppCompatActivity {

    @BindView(R.id.txt_today)
    TextView txtToday;
    @BindView(R.id.time1)
    RadioButton time1;
    @BindView(R.id.time2)
    RadioButton time2;
    @BindView(R.id.time3)
    RadioButton time3;
    @BindView(R.id.time4)
    RadioButton time4;
    @BindView(R.id.radioGroup1)
    RadioGroup radioGroup1;
    @BindView(R.id.cmd_today)
    CardView cmdToday;
    @BindView(R.id.txt_tomorrow)
    TextView txtTomorrow;
    @BindView(R.id.time11)
    RadioButton time11;
    @BindView(R.id.time22)
    RadioButton time22;
    @BindView(R.id.time33)
    RadioButton time33;
    @BindView(R.id.time44)
    RadioButton time44;
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
        setContentView(R.layout.activity_delivery_time2_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        sessionManager=new SessionManager(DeliveryTimeActivity_.this);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = (RadioButton) findViewById(checkedId);
                txtSelecttime.setText(Utiles.getDate() + rb.getText());
                sessionManager.setStringData("setdate", "Today - "+Utiles.getDate() + rb.getText());
                Validation();
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = (RadioButton) findViewById(checkedId);
                txtSelecttime2.setText(Utiles.getNextDate() + rb.getText());
                sessionManager.setStringData("setdate", "Tomorrow - "+Utiles.getNextDate() + rb.getText());
                Validation();
            }
        });
        Validation();
        Chacktimecunditioin();
    }


    private void Chacktimecunditioin() {

        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        Log.e("Day", "" + currentHour);
        if (currentHour >= 5) {
            time1.setVisibility(View.GONE);
        }
        if (currentHour >= 7) {
            time2.setVisibility(View.GONE);

        }
        if (currentHour >= 15) {
            time3.setVisibility(View.GONE);

        }
        if (currentHour >= 17) {
            time4.setVisibility(View.GONE);

        }
    }

    private boolean Validation() {
        if (time1.isChecked() || time2.isChecked() || time3.isChecked() || time4.isChecked() || time11.isChecked() || time22.isChecked() || time33.isChecked() || time44.isChecked()) {
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
                startActivity(new Intent(DeliveryTimeActivity_.this, OrderSummaryActivity.class));
                break;
        }
        Validation();
    }

}
