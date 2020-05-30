package com.grocery.food.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocery.food.Activity.ItemDetailsActivity;
import com.grocery.food.Adepter.ReletedItemAllAdp;
import com.grocery.food.Model.ProductItem;
import com.grocery.food.Model.User;
import com.grocery.food.R;
import com.grocery.food.Utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.grocery.food.Fragment.HomeFragment.ProductDatum;


public class PopularFragment extends Fragment implements ReletedItemAllAdp.ItemClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView reyCategory;
    List<String> myListData = new ArrayList<>();
    SessionManager sessionManager;
    User userData;

    public PopularFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        sessionManager = new SessionManager(getActivity());
        userData = sessionManager.getUserDetails("");

        reyCategory.setHasFixedSize(true);
        reyCategory.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ReletedItemAllAdp itemAdp = new ReletedItemAllAdp(getActivity(), ProductDatum, this);
        reyCategory.setAdapter(itemAdp);
        return view;
    }

    @Override
    public void onItemClick(ProductItem productItem, int position) {
        getActivity().startActivity(new Intent(getActivity(), ItemDetailsActivity.class).putExtra("MyClass", productItem).putParcelableArrayListExtra("MyList", productItem.getPrice()));
    }


}
