package com.grocery.food.Adepter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.grocery.food.Activity.ItemDetailsActivity;
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

public class ItemAdp extends RecyclerView.Adapter<ItemAdp.ViewHolder> {


    private List<ProductItem> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context mContext;
    private int screenWidth;
    SessionManager sessionManager;
    public ItemAdp(Context context, List<ProductItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        sessionManager=new SessionManager(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_custome, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ProductItem datum = mData.get(position);
        Glide.with(mContext).load(APIClient.Base_URL + "/" + datum.getProductImage()).thumbnail(Glide.with(mContext).load(R.drawable.ezgifresize)).into(holder.imgIcon);

        holder.txtTitle.setText("" + datum.getProductName());
        if (!datum.getSellerName().equals("")) {
            holder.sellerName.setText("" + datum.getSellerName());
        } else {
            holder.sellerName.setVisibility(View.GONE);
        }
        if (!datum.getShortDesc().equals("")) {
            holder.shortDesc.setText("" + datum.getShortDesc());
            if (holder.shortDesc.getText().toString().length() < 90) {

            } else {
                makeTextViewResizable(holder.shortDesc, 3, "See More", true);
            }


        } else {
            holder.shortDesc.setVisibility(View.GONE);
        }
        holder.imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ItemDetailsActivity.class).putExtra("MyClass", datum).putParcelableArrayListExtra("MyList", datum.getPrice()));
            }
        });
        if (datum.getmDiscount() > 0) {
            holder.lvlOffer.setVisibility(View.VISIBLE);
            holder.txtOffer.setText(datum.getmDiscount() + "% Off");
        } else {
            holder.lvlOffer.setVisibility(View.GONE);

        }
        setJoinPlayrList(holder.lvlSubitem, datum);
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
        @BindView(R.id.seller_name)
        TextView sellerName;
        @BindView(R.id.short_desc)
        TextView shortDesc;
        @BindView(R.id.lvl_subitem)
        LinearLayout lvlSubitem;
        @BindView(R.id.lvl_offer)
        LinearLayout lvlOffer;


        @BindView(R.id.img_icon)
        ImageView imgIcon;


        ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

//    String getItem(int id) {
//        return mData.get(id);
//    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
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
                myCart.setWeight(datum.getPrice().get(i).getProductType());
                myCart.setCost(datum.getPrice().get(i).getProductPrice());
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
                        itemListFragment.updateItem();
                    }
                });


                txt_gram.setText("" + priceList.get(i).getProductType());

                if (datum.getmDiscount() > 0) {
                    double  res = (Double.parseDouble(priceList.get(i).getProductPrice()) / 100.0f)* datum.getmDiscount();
                    res = Integer.parseInt(priceList.get(i).getProductPrice()) - res;
                    txt_offer.setText(sessionManager.getStringData(CURRUNCY) + priceList.get(i).getProductPrice());
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


    public void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }

        ViewTreeObserver vto = tv.getViewTreeObserver();

        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                     final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false) {
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "See Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 3, ".. See More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    public class MySpannable extends ClickableSpan {

        private boolean isUnderline = true;

        /**
         * Constructor
         */
        public MySpannable(boolean isUnderline) {
            this.isUnderline = isUnderline;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(isUnderline);
            ds.setColor(Color.parseColor("#00A55D"));
        }

        @Override
        public void onClick(View widget) {


        }
    }
}