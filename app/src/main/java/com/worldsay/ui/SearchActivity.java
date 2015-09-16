package com.worldsay.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.worldsay.R;
import com.worldsay.adpter.ContinentCountryAdapter;
import com.worldsay.pojo.Country;
import com.worldsay.util.UIHelper;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by gekson on 2015/8/23.
 */
public class SearchActivity extends Activity {

    @InjectView(R.id.rl_search)
    RelativeLayout searchView;
    @InjectView(R.id.tv_cancel)
    TextView cancelView;
    @InjectView(R.id.tv_select_continent)
    TextView selectAreaTextView;
    @InjectView(R.id.rv_country_list)
    RecyclerView countryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        countryRecyclerView.setLayoutManager(layoutManager);
        ArrayList<Country> mCountryList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mCountryList.add(new Country());
        }
        countryRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        ContinentCountryAdapter countryAdapter = new ContinentCountryAdapter(mCountryList);
        countryRecyclerView.setAdapter(countryAdapter);
    }

    @OnClick(R.id.tv_cancel)
    public void cancelSearch(View view) {
        finish();
    }

    @OnClick(R.id.rl_search)
    public void search(View view) {
        UIHelper.openActivity(this, SearchDetailActivity.class);
    }
}
