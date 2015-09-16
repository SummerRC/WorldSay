package com.worldsay.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.worldsay.R;
import com.worldsay.util.ManagerUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by gekson on 2015/8/24.
 */
public class SearchDetailActivity extends Activity {


    @InjectView(R.id.iv_search)
    ImageView ivSearch;
    @InjectView(R.id.rl_search_button)
    RelativeLayout rlSearchButton;
    @InjectView(R.id.tv_cancel)
    TextView tvCancel;
    //    @InjectView(R.id.search_result_view)
//    ListView searchResultView;
    @InjectView(R.id.act_search_country)
    AutoCompleteTextView searchCountryAutoCompleteTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        List<String> strings = ManagerUtil.fromCsv(this);
        if (strings != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_search_result, strings);
            searchCountryAutoCompleteTextView.setAdapter(adapter);
            searchCountryAutoCompleteTextView.setThreshold(1);
        }
    }

    @OnClick(R.id.tv_cancel)
    public void cancel(View view) {
        finish();
    }
}
