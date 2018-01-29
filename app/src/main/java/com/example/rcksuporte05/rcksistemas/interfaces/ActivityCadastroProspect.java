package com.example.rcksuporte05.rcksistemas.interfaces;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.TabsAdapterProspect;
import com.example.rcksuporte05.rcksistemas.extras.SlidingTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 25/01/2018.
 */

public class ActivityCadastroProspect extends AppCompatActivity{
     TabsAdapterProspect tabsAdapterProspect;


    @BindView(R.id.toolbarFragsProspect)
    Toolbar toolbar;

    @BindView(R.id.stl_tabs_Prospect)
    SlidingTabLayout mSlidingTabLayout;

    @BindView(R.id.vp_tabs_prospect)
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_prospect);
        ButterKnife.bind(this);

        toolbar.setTitle("Cadastro de Prospect");


        tabsAdapterProspect = new TabsAdapterProspect(getSupportFragmentManager());
        mViewPager.setAdapter(tabsAdapterProspect);

        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(Color.WHITE);
        mSlidingTabLayout.setViewPager(mViewPager);

        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() != 0) {
            mViewPager.setCurrentItem(0);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
