package com.example.rcksuporte05.rcksistemas.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rcksuporte05.rcksistemas.Helper.CampanhaHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.TabsAdapterCampanha;
import com.example.rcksuporte05.rcksistemas.util.SlidingTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemCampanhaActivity extends AppCompatActivity {

    @BindView(R.id.vp_tabs)
    public ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.stl_tabs)
    SlidingTabLayout mSlidingTabLayout;

    private TabsAdapterCampanha tabsAdapterCampanha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_campanha);
        ButterKnife.bind(this);

        tabsAdapterCampanha = new TabsAdapterCampanha(getSupportFragmentManager());
        mViewPager.setAdapter(tabsAdapterCampanha);

        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(Color.WHITE);
        mSlidingTabLayout.setViewPager(mViewPager);

        toolbar.setTitle(CampanhaHelper.getCampanhaComercialCab().getNomeCampanha());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mViewPager.getCurrentItem() != 0) {
                    mViewPager.setCurrentItem(0);
                } else {
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (getIntent().getIntExtra("detalhe", 0) != 1)
            CampanhaHelper.clear();
        super.onDestroy();
    }
}
