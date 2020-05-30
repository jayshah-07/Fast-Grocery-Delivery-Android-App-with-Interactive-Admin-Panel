package com.grocery.food.Activity;

import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.grocery.food.DataBase.DatabaseHelper;
import com.grocery.food.DataBase.MyCart;
import com.grocery.food.Model.Price;
import com.grocery.food.Model.ProductItem;
import com.grocery.food.R;
import com.grocery.food.Utils.SessionManager;
import com.grocery.food.retrofit.APIClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.grocery.food.Fragment.ItemListFragment.itemListFragment;
import static com.grocery.food.Utils.SessionManager.CURRUNCY;

public class ItemDetailsActivity extends AppCompatActivity {


    ProductItem productItem;
    Price price;
    @BindView(R.id.imgP)
    ImageView imgP;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.txt_tcount)
    TextView txtTcount;
    @BindView(R.id.lvl_cart)
    RelativeLayout lvlCart;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_desc)
    TextView txtDesc;
    @BindView(R.id.lvl_pricelist)
    LinearLayout lvlPricelist;
    @BindView(R.id.btn_addtocart)
    Button btnAddtocart;
    ArrayList<Price> priceslist;
    DatabaseHelper databaseHelper;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        ButterKnife.bind(this);
        sessionManager=new SessionManager(this);
        databaseHelper = new DatabaseHelper(ItemDetailsActivity.this);
        productItem = (ProductItem) getIntent().getParcelableExtra("MyClass");
        priceslist = getIntent().getParcelableArrayListExtra("MyList");
        if (productItem != null) {
            txtTitle.setText("" + productItem.getProductName());
            txtDesc.setText("" + productItem.getShortDesc());
            Glide.with(this).load(APIClient.Base_URL + "/" + productItem.getProductImage()).thumbnail(Glide.with(this).load(R.drawable.ezgifresize)).into(imgP);
            setJoinPlayrList(lvlPricelist, productItem, priceslist);
            updateItem();
        }
    }

    public void updateItem() {
        Cursor res = databaseHelper.getAllData();
        if (res.getCount() == 0) {
            txtTcount.setText("0");

        } else {
            txtTcount.setText("" + res.getCount());
        }
    }

    @OnClick({R.id.img_back, R.id.lvl_cart, R.id.btn_addtocart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.lvl_cart:
                fragment();
                break;
            case R.id.btn_addtocart:
                finish();
                break;
        }
    }

    private void setJoinPlayrList(LinearLayout lnrView, ProductItem datum, List<Price> priceList) {

        lnrView.removeAllViews();
        final int[] count = {0};
        DatabaseHelper helper = new DatabaseHelper(lnrView.getContext());
        if (priceList != null && priceList.size() > 0) {
            for (int i = 0; i < priceList.size(); i++) {
                LayoutInflater inflater = LayoutInflater.from(this);

                View view = inflater.inflate(R.layout.custome_prize, null);

                TextView txt_price = view.findViewById(R.id.txt_price);
                TextView txt_gram = view.findViewById(R.id.txt_gram);
                TextView txt_offer = view.findViewById(R.id.txt_offer);
                TextView txtcount = view.findViewById(R.id.txtcount);
                LinearLayout img_mins = view.findViewById(R.id.img_mins);
                LinearLayout img_plus = view.findViewById(R.id.img_plus);
                MyCart myCart = new MyCart();
                myCart.setPID(datum.getId());
                myCart.setImage(datum.getProductImage());
                myCart.setTitle(datum.getProductName());
                myCart.setWeight(priceList.get(i).getProductType());
                myCart.setCost(priceList.get(i).getProductPrice());
                myCart.setDiscount(datum.getmDiscount());
                int qrt = helper.getCard(myCart.getPID(), myCart.getCost());
                if (qrt != -1) {
                    count[0] = qrt;
                    txtcount.setText("" + count[0]);
                    txtcount.setVisibility(View.VISIBLE);
                } else {
                    txtcount.setVisibility(View.VISIBLE);
                    img_mins.setVisibility(View.VISIBLE);
                }
                img_mins.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        count[0] = Integer.parseInt(txtcount.getText().toString());

                        count[0] = count[0] - 1;
                        if (count[0] <= 0) {
                            img_mins.setVisibility(View.VISIBLE);
                            txtcount.setText("0");
                            txtcount.setVisibility(View.VISIBLE);
                            helper.deleteRData(myCart.getPID(), myCart.getCost());
                        } else {
                            txtcount.setVisibility(View.VISIBLE);
                            txtcount.setText("" + count[0]);
                            myCart.setQty(String.valueOf(count[0]));
                            Log.e("INsert", "--> " + helper.insertData(myCart));
                        }
                        if (itemListFragment != null)
                            itemListFragment.updateItem();
                        updateItem();
                    }
                });

                img_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtcount.setVisibility(View.VISIBLE);
                        img_mins.setVisibility(View.VISIBLE);
                        count[0] = Integer.parseInt(txtcount.getText().toString());

                        count[0] = count[0] + 1;
                        txtcount.setText("" + count[0]);
                        myCart.setQty(String.valueOf(count[0]));
                        Log.e("INsert", "--> " + helper.insertData(myCart));
                        if (itemListFragment != null)
                            itemListFragment.updateItem();
                        updateItem();
                    }
                });


                txt_gram.setText("" + priceList.get(i).getProductType());


                if (datum.getmDiscount() > 0) {
                    double  res = (Double.parseDouble(priceList.get(i).getProductPrice()) / 100.0f)* datum.getmDiscount();
                    res = Integer.parseInt(priceList.get(i).getProductPrice()) - res;
                    txt_offer.setText(sessionManager.getStringData(CURRUNCY)+ priceList.get(i).getProductPrice());
                    txt_offer.setPaintFlags(txt_offer.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    txt_price.setText(sessionManager.getStringData(CURRUNCY)  + res);
                } else {
                    txt_offer.setVisibility(View.GONE);
                    txt_price.setText(sessionManager.getStringData(CURRUNCY)  + priceList.get(i).getProductPrice());
                }
                lnrView.addView(view);
            }
        }
    }

    public void fragment() {
        SessionManager.ISCART = true;
        finish();

    }


}
