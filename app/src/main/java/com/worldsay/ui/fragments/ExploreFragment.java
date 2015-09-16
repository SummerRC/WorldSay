package com.worldsay.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.worldsay.Constants;
import com.worldsay.R;
import com.worldsay.adpter.ExploreAdapter;
import com.worldsay.api.RestPool;
import com.worldsay.pojo.ExploreOptionList;
import com.worldsay.ui.article.InformationDetails;
import com.worldsay.util.ManagerUtil;
import com.worldsay.util.SharedPreferenceUtils;
import com.worldsay.util.T;
import com.worldsay.util.UIHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by gekson on 2015/8/23.
 */
public class ExploreFragment extends Fragment {

    @InjectView(R.id.explore_list)
    ListView exploreListView;
    @InjectView(R.id.tv_type)
    TextView typeOption;
    @InjectView(R.id.tv_intelligent)
    TextView intelligentOption;

    private int country_id;
    private int nextPage = 1;
    private ExploreAdapter adapter;
    private List<ExploreOptionList> mDatas = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        country_id = SharedPreferenceUtils.getIntDate(getActivity(), Constants.COUNTRY_ID, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        ButterKnife.inject(this, view);
        init();
        return view;
    }

    private void init() {
        loadExploreList();
        adapter = new ExploreAdapter(getActivity(), mDatas, R.layout.item_explore);
        exploreListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        exploreListView.setAdapter(adapter);
        exploreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UIHelper.openActivity(getActivity(), InformationDetails.class);
            }
        });
    }

    private void loadExploreList() {
        RestPool.init().searchHomeList(country_id, 1, 1, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        try {
                            String body = ManagerUtil.fromBody(response);
                            HashMap<String, String> map = new HashMap<String, String>();
                            JSONObject jObject = new JSONObject(body);
                            Iterator<?> keys = jObject.keys();

                            while (keys.hasNext()) {
                                String key = (String) keys.next();
                                String value = jObject.getString(key);
                                map.put(key, value);
                            }
                            nextPage = Integer.valueOf(map.get("next_page"));
                            String ExploreListStr = (String) map.get("opts");
                            List<ExploreOptionList> list = new Gson().fromJson(ExploreListStr, new TypeToken<List<ExploreOptionList>>() {
                            }.getType());
                            mDatas.addAll(list);
                            adapter.notifyDataSetChanged();
                        } catch (Exception ignored) {
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        T.show(getActivity(), error.getMessage() + "", Toast.LENGTH_SHORT);
                    }
                }

        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
