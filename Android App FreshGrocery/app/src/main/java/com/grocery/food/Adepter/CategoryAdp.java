package com.grocery.food.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.grocery.food.Model.CatItem;
import com.grocery.food.R;
import com.grocery.food.retrofit.APIClient;

import java.util.List;

public class CategoryAdp extends RecyclerView.Adapter<CategoryAdp.MyViewHolder> {

    private Context mContext;
    private List<CatItem> categoryList;
    private RecyclerTouchListener listener;

    public interface RecyclerTouchListener {
        public void onClickItem(View v, int position);

        public void onLongClickItem(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_title);
            thumbnail = view.findViewById(R.id.imageView);


        }
    }

    public CategoryAdp(Context mContext, List<CatItem> categoryList, final RecyclerTouchListener listener) {
        this.mContext = mContext;
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        CatItem category = categoryList.get(position);
        holder.title.setText(category.getCatname() + "(" + category.getCount() + ")");
        Glide.with(mContext).load(APIClient.Base_URL + "/" + category.getCatimg()).thumbnail(Glide.with(mContext).load(R.drawable.ezgifresize)).into(holder.thumbnail);
//        Picasso.with(mContext).load(APIClient.Base_URL + "/" + category.getCatimg()).placeholder(R.drawable.ezgifresize).into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(category.getCount()) == 0) {
                    Toast.makeText(mContext, "Category Not Found !!!", Toast.LENGTH_SHORT).show();
                } else {
                    listener.onClickItem(v, Integer.parseInt(category.getId()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}