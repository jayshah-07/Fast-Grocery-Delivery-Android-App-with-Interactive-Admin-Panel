package com.grocery.food.Adepter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.grocery.food.DataBase.DatabaseHelper;
import com.grocery.food.DataBase.MyCart;
import com.grocery.food.Model.Price;
import com.grocery.food.Model.ProductItem;
import com.grocery.food.R;
import com.grocery.food.Utils.SessionManager;
import com.grocery.food.retrofit.APIClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.grocery.food.Fragment.ItemListFragment.itemListFragment;
import static com.grocery.food.Utils.SessionManager.CURRUNCY;

public class ReletedItemAdp extends RecyclerView.Adapter<ReletedItemAdp.ViewHolder> {


    private List<ProductItem> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context mContext;
    private int screenWidth;
    DatabaseHelper helper;
    SessionManager sessionManager;
    public ReletedItemAdp(Context context, List<ProductItem> data, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
        this.mClickListener = itemClickListener;
        sessionManager=new SessionManager(mContext);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        helper = new DatabaseHelper(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.releteditem_custome, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.priceoofer.setPaintFlags(holder.priceoofer.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        ProductItem datum = mData.get(position);
        Glide.with(mContext).load(APIClient.Base_URL + "/" + datum.getProductImage()).thumbnail(Glide.with(mContext).load(R.drawable.lodingimage)).into(holder.imgIcon);

        holder.txtTitle.setText("" + datum.getProductName());

        if (datum.getmDiscount() > 0) {
            double  res = (Double.parseDouble(datum.getPrice().get(0).getProductPrice()) / 100.0f)* datum.getmDiscount();

            res = Integer.parseInt(datum.getPrice().get(0).getProductPrice()) - res;
            holder.priceoofer.setText(sessionManager.getStringData(CURRUNCY)  + datum.getPrice().get(0).getProductPrice());
            holder.txtPrice.setText(sessionManager.getStringData(CURRUNCY)  + res);
            holder.lvlOffer.setVisibility(View.VISIBLE);
            holder.txtOffer.setText(datum.getmDiscount() + "% Off");
        } else {
            holder.lvlOffer.setVisibility(View.GONE);
            holder.priceoofer.setVisibility(View.GONE);
            holder.txtPrice.setText(sessionManager.getStringData(CURRUNCY)  + datum.getPrice().get(0).getProductPrice());
        }


        int qrt = helper.getCard(datum.getId(), datum.getPrice().get(0).getProductPrice());
        if (qrt >= 1) {
            holder.lvlCardbg.setBackground(mContext.getResources().getDrawable(R.drawable.bg_red_shape));
            holder.imgCard.setImageDrawable(mContext.getDrawable(R.drawable.ic_minus_rounded));
        } else {
            holder.lvlCardbg.setBackground(mContext.getResources().getDrawable(R.drawable.bg_green_plus));
            holder.imgCard.setImageDrawable(mContext.getDrawable(R.drawable.ic_plus_rounded));

        }
        holder.lvlCardbg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qrt = helper.getCard(datum.getId(), datum.getPrice().get(0).getProductPrice());
                if (qrt >= 1) {
                    Toast.makeText(mContext, "This product is already insert", Toast.LENGTH_SHORT).show();

                } else {

                    MyCart myCart = new MyCart();
                    myCart.setPID(datum.getId());
                    myCart.setImage(datum.getProductImage());
                    myCart.setTitle(datum.getProductName());
                    myCart.setWeight(datum.getPrice().get(0).getProductType());
                    myCart.setCost(datum.getPrice().get(0).getProductPrice());
                    myCart.setQty("1");
                    myCart.setDiscount(datum.getmDiscount());
                    Log.e("INsert", "--> " + helper.insertData(myCart));
                    holder.lvlCardbg.setBackground(mContext.getResources().getDrawable(R.drawable.bg_red_shape));
                    holder.imgCard.setImageDrawable(mContext.getDrawable(R.drawable.ic_minus_rounded));
                }

            }
        });
//        setJoinPlayrList(holder.lvlSubitem, datum);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.txt_offer)
        TextView txtOffer;
        @BindView(R.id.price)
        TextView txtPrice;
        @BindView(R.id.priceoofer)
        TextView priceoofer;

        @BindView(R.id.lvl_subitem)
        LinearLayout lvlSubitem;

        @BindView(R.id.lvl_offer)
        LinearLayout lvlOffer;
        @BindView(R.id.img_icon)
        ImageView imgIcon;

        @BindView(R.id.lvl_cardbg)
        LinearLayout lvlCardbg;
        @BindView(R.id.img_card)
        ImageView imgCard;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(mData.get(getAdapterPosition()), getAdapterPosition());
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(ProductItem productItem, int position);
    }

    private void setJoinPlayrList(LinearLayout lnrView, ProductItem datum) {
        List<Price> priceList = datum.getPrice();
        lnrView.removeAllViews();
        final int[] count = {0};
        DatabaseHelper helper = new DatabaseHelper(lnrView.getContext());
        if (priceList != null && priceList.size() > 0) {
            for (int i = 0; i < priceList.size(); i++) {
                LayoutInflater inflater = LayoutInflater.from(mContext);

                View view = inflater.inflate(R.layout.custome_prize, null);
//                TextView txt_gram = view.findViewById(R.id.txt_gram);
                TextView txt_price = view.findViewById(R.id.txt_price);
                TextView txtcount = view.findViewById(R.id.txtcount);
                ImageView img_mins = view.findViewById(R.id.img_mins);
                ImageView img_plus = view.findViewById(R.id.img_plus);
                MyCart myCart = new MyCart();
                myCart.setPID(datum.getId());
                myCart.setImage(datum.getProductImage());
                myCart.setTitle(datum.getProductName());
                myCart.setWeight(datum.getPrice().get(i).getProductType());
                myCart.setCost(datum.getPrice().get(i).getProductPrice());
                int qrt = helper.getCard(myCart.getPID(), myCart.getCost());
                if (qrt != -1) {
                    count[0] = qrt;
                    txtcount.setText("" + count[0]);
                    txtcount.setVisibility(View.VISIBLE);
                } else {
                    txtcount.setVisibility(View.INVISIBLE);
                    img_mins.setVisibility(View.INVISIBLE);
                }
                img_mins.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        count[0] = Integer.parseInt(txtcount.getText().toString());
                        count[0] = count[0] - 1;
                        if (count[0] <= 0) {
                            img_mins.setVisibility(View.INVISIBLE);
                            txtcount.setText("" + count[0]);
                            txtcount.setVisibility(View.INVISIBLE);
                            helper.deleteRData(myCart.getPID(), myCart.getCost());
                        } else {
                            txtcount.setVisibility(View.VISIBLE);
                            txtcount.setText("" + count[0]);
                            myCart.setQty(String.valueOf(count[0]));
                            Log.e("INsert", "--> " + helper.insertData(myCart));
                        }
                        if (itemListFragment != null)
                            itemListFragment.updateItem();
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
                    }
                });

//                txt_gram.setText(" " + priceList.get(i).getProductType());
                txt_price.setText(sessionManager.getStringData(CURRUNCY)  + priceList.get(i).getProductPrice());

                lnrView.addView(view);
            }
        }
    }
}