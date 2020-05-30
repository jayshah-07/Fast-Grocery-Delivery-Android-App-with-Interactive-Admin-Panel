package com.grocery.food.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.grocery.food.Activity.HomeActivity;
import com.grocery.food.Activity.ItemDetailsActivity;
import com.grocery.food.Activity.PopRatesActivity;
import com.grocery.food.Adepter.CategoryAdp;
import com.grocery.food.Adepter.ReletedItemAdp;
import com.grocery.food.Model.BannerItem;
import com.grocery.food.Model.CatItem;
import com.grocery.food.Model.Home;
import com.grocery.food.Model.ProductItem;
import com.grocery.food.Model.User;
import com.grocery.food.R;
import com.grocery.food.Utils.SessionManager;
import com.grocery.food.retrofit.APIClient;
import com.grocery.food.retrofit.GetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.grocery.food.Activity.HomeActivity.homeActivity;
import static com.grocery.food.Activity.HomeActivity.txtNoti;
import static com.grocery.food.Utils.SessionManager.CURRUNCY;


public class HomeFragment extends Fragment implements CategoryAdp.RecyclerTouchListener, ReletedItemAdp.ItemClickListener, GetResult.MyListener {


    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabview)
    TabLayout tabview;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.recycler_releted)
    RecyclerView recyclerReleted;
    Unbinder unbinder;
    @BindView(R.id.txt_viewll)
    TextView txtViewll;
    @BindView(R.id.txt_viewllproduct)
    TextView txtViewllproduct;
    private Context mContext;

    public HomeFragment() {
        // Required empty public constructor
    }


    CategoryAdp adapter;
    ReletedItemAdp adapterReletedi;
    List<CatItem> categoryList;
    public static List<ProductItem> ProductDatum;
    List<BannerItem> bannerDatumList;

    public static HomeFragment HomeListFragment;
    SessionManager sessionManager;
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        bannerDatumList = new ArrayList<>();
        sessionManager = new SessionManager(mContext);
        HomeListFragment = this;

        //Category SetData
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(mContext);
        mLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerReleted.setLayoutManager(mLayoutManager1);

        categoryList = new ArrayList<>();
        adapter = new CategoryAdp(mContext, categoryList, this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        ProductDatum = new ArrayList<>();
        adapterReletedi = new ReletedItemAdp(mContext, ProductDatum, this);
        recyclerReleted.setItemAnimator(new DefaultItemAnimator());
        recyclerReleted.setAdapter(adapterReletedi);


        user = sessionManager.getUserDetails("");
        getHome();

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClickItem(View v, int position) {

        homeActivity.ShowMenu();
        Bundle args = new Bundle();
        args.putInt("id", position);
        Fragment fragment = new SubCategoryFragment();
        fragment.setArguments(args);
        HomeActivity.getInstance().callFragment(fragment);
    }


    @Override
    public void onLongClickItem(View v, int position) {

    }

    @Override
    public void onItemClick(ProductItem productItem, int position) {
        mContext.startActivity(new Intent(mContext, ItemDetailsActivity.class).putExtra("MyClass", productItem).putParcelableArrayListExtra("MyList", productItem.getPrice()));
    }

    @OnClick({R.id.txt_viewll, R.id.txt_viewllproduct})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_viewll:
                CategoryFragment fragment = new CategoryFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("arraylist", (Serializable) categoryList);
                fragment.setArguments(bundle);
                HomeActivity.getInstance().callFragment(fragment);
                break;
            case R.id.txt_viewllproduct:
                PopularFragment fragmentp = new PopularFragment();

                HomeActivity.getInstance().callFragment(fragmentp);
                break;
        }
    }

    public class MyCustomPagerAdapter extends PagerAdapter {
        Context context;
        List<BannerItem> bannerDatumList;
        LayoutInflater layoutInflater;

        public MyCustomPagerAdapter(Context context, List<BannerItem> bannerDatumList) {
            this.context = context;
            this.bannerDatumList = bannerDatumList;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return bannerDatumList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = layoutInflater.inflate(R.layout.item_banner, container, false);
//            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            Glide.with(mContext).load(APIClient.Base_URL + "/" + bannerDatumList.get(position).getBimg()).placeholder(R.drawable.empty).into(imageView);
            container.addView(itemView);
            //listening to image click
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
                }
            });
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }


    private void getHome() {
        HomeActivity.custPrograssbar.PrograssCreate(mContext);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getHome((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "homepage");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.getInstance().setdata();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        try {


            if (callNo.equalsIgnoreCase("homepage") && result != null) {
                HomeActivity.custPrograssbar.ClosePrograssBar();
                Gson gson = new Gson();
                Home home = gson.fromJson(result.toString(), Home.class);

                categoryList.addAll(home.getResultHome().getCatItems());
                adapter.notifyDataSetChanged();

                bannerDatumList.addAll(home.getResultHome().getBannerItems());
                MyCustomPagerAdapter myCustomPagerAdapter = new MyCustomPagerAdapter(mContext, bannerDatumList);
                viewPager.setAdapter(myCustomPagerAdapter);
                tabview.setupWithViewPager(viewPager, true);

                ReletedItemAdp itemAdp = new ReletedItemAdp(mContext, home.getResultHome().getProductItems(), this);
                recyclerReleted.setAdapter(itemAdp);
                if (home.getResultHome().getRemainNotification() <= 0) {
                    txtNoti.setVisibility(View.GONE);
                } else {
                    txtNoti.setVisibility(View.VISIBLE);
                    txtNoti.setText("" + home.getResultHome().getRemainNotification());
                }
                sessionManager.setStringData(CURRUNCY, home.getResultHome().getCurrency());
                ProductDatum = new ArrayList<>();
                ProductDatum = home.getResultHome().getProductItems();

            } else if (callNo.equals("3")) {
                Log.e("response", "-->" + result.toString());
                try {
                    JSONObject object = new JSONObject(result.toString());
                    if (object.getString("Result").equalsIgnoreCase("true")) {
                        if (object.getInt("count") <= 0) {
                            txtNoti.setVisibility(View.GONE);
                        }
                        txtNoti.setText("" + object.getInt("count"));
                        if (!object.getString("orderid").equalsIgnoreCase("0")) {
                            startActivity(new Intent(mContext, PopRatesActivity.class).putExtra("oid", object.getString("orderid")));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

}
