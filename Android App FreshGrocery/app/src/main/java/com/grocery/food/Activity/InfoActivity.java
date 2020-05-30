package com.grocery.food.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.grocery.food.Fragment.Info1Fragment;
import com.grocery.food.Fragment.Info2Fragment;
import com.grocery.food.Fragment.Info3Fragment;
import com.grocery.food.R;
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoActivity extends AppCompatActivity {


    public static ViewPager vpPager;
    MyPagerAdapter adapterViewPager;

    public static TextView btnNext;
    @BindView(R.id.btn_skip)
    TextView btnSkip;
    int selectPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
        btnNext = findViewById(R.id.btn_next);
        vpPager = findViewById(R.id.vpPager);

        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        ExtensiblePageIndicator extensiblePageIndicator = (ExtensiblePageIndicator) findViewById(R.id.flexibleIndicator);
        extensiblePageIndicator.initViewPager(vpPager);
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPage = position;
                if (position == 0) {
                    btnNext.setText("Next");
                } else if (position == 1) {
                    btnNext.setText("Next");

                } else if (position == 2) {
                    btnNext.setText("Finish");

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @OnClick({R.id.btn_next, R.id.btn_skip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:

                if (selectPage == 0) {
                    vpPager.setCurrentItem(1);
                } else if (selectPage == 1) {
                    vpPager.setCurrentItem(2);
                } else if (selectPage == 2) {
                    startActivity(new Intent(InfoActivity.this, LoginActivity.class));
                    finish();
                }
                break;
            case R.id.btn_skip:
                startActivity(new Intent(InfoActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return Info1Fragment.newInstance("0", "Next");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return Info2Fragment.newInstance("1", "Next");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return Info3Fragment.newInstance("2", "Finish");
                default:
                    return null;
            }

        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            Log.e("page", "" + position);
            return "Page " + position;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
//
            return fragment;
        }

    }
}
