package com.worldsay.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.worldsay.Base.BaseActivity;
import com.worldsay.R;
import com.worldsay.adpter.CountryAdapter;
import com.worldsay.api.RestPool;
import com.worldsay.pojo.Country;
import com.worldsay.pojo.HotCountryList;
import com.worldsay.pojo.MessageList;
import com.worldsay.util.ManagerUtil;
import com.worldsay.util.T;
import com.worldsay.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by gekson on 2015/8/19.
 */
public class CountryActivity extends BaseActivity {
    private List<Country> hotCountryList = new ArrayList<>();
    @InjectView(R.id.rv_country_hot)
    RecyclerView hotCountryRecyclerView;
    private CountryAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View.inflate(this, R.layout.activity_countryselect, contentView);
        setTitle(R.string.title_country);
        setRightButton(R.drawable.icon_search);
        init();
        requestServiceHotCountries();
    }

    /**
     * 请求热门国家列表
     */
    private void requestServiceHotCountries() {
        RestPool.init().hotCountryList(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                hotCountryList.clear();
                String body = ManagerUtil.fromBody(response);
                HotCountryList countryListResponse = new Gson().fromJson(body, HotCountryList.class);
                List<Country> countries = countryListResponse.getHot_countries();
                if (countries != null && countries.size() >= 0) {
                    hotCountryList.addAll(countries);
                    countryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                T.showShort(CountryActivity.this,error.getMessage());
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        ButterKnife.inject(this);
        topRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.openActivity(CountryActivity.this, SearchActivity.class);
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        hotCountryRecyclerView.setLayoutManager(layoutManager);
        countryAdapter = new CountryAdapter(CountryActivity.this, hotCountryList);
        hotCountryRecyclerView.setAdapter(countryAdapter);
        hotCountryRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }
}
