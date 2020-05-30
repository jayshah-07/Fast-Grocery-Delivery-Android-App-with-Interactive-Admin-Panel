package com.grocery.food.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.AppBarLayout;
import com.grocery.food.BuildConfig;
import com.grocery.food.DataBase.DatabaseHelper;
import com.grocery.food.Fragment.CardFragment;
import com.grocery.food.Fragment.HomeFragment;
import com.grocery.food.Fragment.ItemListFragment;
import com.grocery.food.Fragment.MyOrderFragment;
import com.grocery.food.Fragment.OrderSumrryFragment;
import com.grocery.food.Model.User;
import com.grocery.food.R;
import com.grocery.food.Utils.CustPrograssbar;
import com.grocery.food.Utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.grocery.food.Fragment.ItemListFragment.itemListFragment;
import static com.grocery.food.Fragment.OrderSumrryFragment.ISORDER;


public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //    @BindView(R.id.nav_view)
//    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.lvl_actionsearch)
    LinearLayout lvlActionsearch;
    @BindView(R.id.lvl_home)
    LinearLayout lvlHome;
    @BindView(R.id.txt_actiontitle)
    TextView txtActiontitle;
    @BindView(R.id.fragment_frame)
    FrameLayout fragmentFrame;
    public static TextView txt_countcard;
    DatabaseHelper databaseHelper;

    SessionManager sessionManager;
    User user;
    public static HomeActivity homeActivity = null;
    Fragment fragment1 = null;


    @BindView(R.id.rlt_cart)
    RelativeLayout rltCart;
    @BindView(R.id.rlt_noti)
    RelativeLayout rltNoti;


    @BindView(R.id.txt_countcard)
    TextView txtCountcard;
    @BindView(R.id.img_noti)
    ImageView imgNoti;

    public static TextView txtNoti;


    public static CustPrograssbar custPrograssbar;

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.lvl_hidecart)
    LinearLayout lvlHidecart;
    @BindView(R.id.txtfirstl)
    TextView txtfirstl;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_email)
    TextView txtEmail;
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.myprofile)
    LinearLayout myprofile;

    @BindView(R.id.myoder)
    LinearLayout myoder;
    @BindView(R.id.address)
    LinearLayout address;
    @BindView(R.id.feedback)
    LinearLayout feedback;
    @BindView(R.id.contect)
    LinearLayout contect;
    @BindView(R.id.logout)
    LinearLayout logout;
    @BindView(R.id.about)
    LinearLayout about;
    @BindView(R.id.tramscondition)
    LinearLayout tramscondition;
    @BindView(R.id.privecy)
    LinearLayout privecy;
    @BindView(R.id.faq)
    LinearLayout faq;
    @BindView(R.id.drawer)
    LinearLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        txtNoti = findViewById(R.id.txt_noti);
        custPrograssbar = new CustPrograssbar();
        databaseHelper = new DatabaseHelper(HomeActivity.this);
        sessionManager = new SessionManager(HomeActivity.this);
        user = sessionManager.getUserDetails("");
        homeActivity = this;

        setDrawer();

    }

    public static HomeActivity getInstance() {
        return homeActivity;
    }

    public void ShowMenu() {
        Log.e("Test for instance", "--> ShowMenu");


        rltNoti.setVisibility(View.GONE);
        rltCart.setVisibility(View.VISIBLE);
    }

    private void setDrawer() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        char first = user.getName().charAt(0);
        Log.e("first", "-->" + first);
        txtfirstl.setText("" + first);
        txtName.setText("" + user.getName());
        txtEmail.setText("" + user.getEmail());
        txt_countcard = findViewById(R.id.txt_countcard);
        Cursor res = databaseHelper.getAllData();
        if (res.getCount() == 0) {
            txt_countcard.setText("0");
        } else {
            txt_countcard.setText("" + res.getCount());
        }
        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_frame, fragment).addToBackStack("HomePage").commit();

        addTextWatcher();
        txtActiontitle.setText("Hello " + user.getName());
    }

    public EditText passThisEditText() {
        return edSearch;
    }

    private void addTextWatcher() {
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (edSearch.getText().toString().trim().length() != 0) {
                    Bundle args = new Bundle();
                    args.putInt("id", 0);
                    args.putString("search", edSearch.getText().toString().trim());
                    if (fragment1 != null && ItemListFragment.getInstance() != null) {
                        ItemListFragment.getInstance().getSearch(s.toString());
                    } else {
                        fragment1 = new ItemListFragment();
                        fragment1.setArguments(args);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_frame, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commit();
                    }
                } else {
                    fragment1 = null;
                    getSupportFragmentManager().popBackStackImmediate();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_frame);
            if (fragment instanceof HomeFragment && fragment.isVisible()) {
                finish();
            } else if (fragment instanceof OrderSumrryFragment && fragment.isVisible() && ISORDER) {
                ISORDER = false;
                Intent i = new Intent(this, HomeActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(0, 0);
            } else if (fragment instanceof MyOrderFragment && fragment.isVisible()) {
                Intent i = new Intent(this, HomeActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(0, 0);
            }
        }
        edSearch.setText("");
        lvlActionsearch.setVisibility(View.VISIBLE);

        txtActiontitle.setText("Hello " + user.getName());

        if (itemListFragment == null) {

            rltNoti.setVisibility(View.VISIBLE);
            rltCart.setVisibility(View.VISIBLE);

        }
    }

    public void setdata() {

        rltNoti.setVisibility(View.VISIBLE);
        rltCart.setVisibility(View.VISIBLE);
    }

    public void serchviewHide() {

        lvlActionsearch.setVisibility(View.GONE);
    }

    public void titleChange(String s) {

        txtActiontitle.setText(s);
    }


    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    public void callFragment(Fragment fragmentClass) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_frame, fragmentClass).addToBackStack("adds").commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }


    @OnClick({R.id.img_close, R.id.myprofile, R.id.myoder, R.id.address, R.id.feedback, R.id.contect, R.id.logout, R.id.about, R.id.privecy, R.id.img_search, R.id.img_cart, R.id.img_noti, R.id.lvl_home, R.id.share})
    public void onViewClicked(View view) {
        Fragment fragment;
        Bundle args;
        switch (view.getId()) {
            case R.id.img_close:
                onBackPressed();
                break;
            case R.id.lvl_home:
                txtActiontitle.setText("Hello " + user.getName());
                fragment = new HomeFragment();
                callFragment(fragment);
                break;
            case R.id.myprofile:
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                break;
            case R.id.myoder:
                fragment = new MyOrderFragment();
                callFragment(fragment);
                break;
            case R.id.address:
                startActivity(new Intent(HomeActivity.this, AddressActivity.class));
                break;
            case R.id.feedback:
                startActivity(new Intent(HomeActivity.this, FeedBackActivity.class));
                break;
            case R.id.contect:
                startActivity(new Intent(HomeActivity.this, ContectusActivity.class));
                break;
            case R.id.logout:
                sessionManager.logoutUser();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.about:
                startActivity(new Intent(HomeActivity.this, AboutsActivity.class));
                break;
            case R.id.privecy:
                startActivity(new Intent(HomeActivity.this, TramsAndConditionActivity.class));
                break;
            case R.id.img_search:
                if (edSearch.getText().toString().trim().length() != 0) {
                    args = new Bundle();
                    args.putInt("id", 0);
                    args.putString("search", edSearch.getText().toString().trim());
                    fragment = new ItemListFragment();
                    fragment.setArguments(args);
                    callFragment(fragment);
                }
                break;
            case R.id.img_cart:
//                lvlActionsearch.setVisibility(View.GONE);
                txtActiontitle.setVisibility(View.VISIBLE);
                rltNoti.setVisibility(View.GONE);
                rltCart.setVisibility(View.VISIBLE);
                txtActiontitle.setText("MyCart");
                fragment = new CardFragment();
                callFragment(fragment);
                break;

            case R.id.img_noti:
                startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
                break;
            case R.id.share:
                shareApp();
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public static void notificationCount(int i) {

        if (i <= 0) {
            txtNoti.setVisibility(View.GONE);
        } else {
            txtNoti.setVisibility(View.VISIBLE);
            txtNoti.setText("" + i);
        }
    }

}
