package com.worldsay.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.worldsay.Constants;
import com.worldsay.R;
import com.worldsay.adpter.MessageAdapter;
import com.worldsay.pojo.MessageList;
import com.worldsay.api.RestPool;
import com.worldsay.ui.article.ArticleInfoDetails;
import com.worldsay.util.ManagerUtil;
import com.worldsay.util.SharedPreferenceUtils;
import com.worldsay.util.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by gekson on 2015/8/23.
 */
public class MessageFragment extends Fragment {

    private MessageAdapter adapter;

    @InjectView(R.id.message_list)
    ListView messageListView;
    private List<MessageList.CountryInfoEntity> country_infos = new ArrayList<>();
    private int country_id;
    private int lastId = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        country_id = SharedPreferenceUtils.getIntDate(getActivity(), Constants.COUNTRY_ID, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.inject(this, view);
        init();
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        boolean islogining = SharedPreferenceUtils.getBooleanDate(getActivity(), Constants.ISLOGINING, false);
        if (islogining) {
            requestServiceIsLogingInfolist();
        } else {
            requestServiceInfolist();
        }
    }

    private void requestServiceIsLogingInfolist() {
        if (country_infos != null && country_infos.size() > 0) {
            lastId = country_infos.get(country_infos.size() - 1).getArticle_id();
            RestPool.initBasic().msgHomeInfoIsLoginList(country_id, lastId, new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    country_infos.clear();
                    String body = ManagerUtil.fromBody(response);
                    MessageList messageList = new Gson().fromJson(body, MessageList.class);
                    List<MessageList.CountryInfoEntity> messageListCountry_info = messageList.getCountry_info();
                    if (messageListCountry_info != null && messageListCountry_info.size() >= 0) {
                        country_infos.addAll(messageListCountry_info);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    T.showShort(getActivity(), error.toString());
                }
            });
        }else{
            RestPool.initBasic().msgHomeInfoIsLogin(country_id, new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    country_infos.clear();
                    String body = ManagerUtil.fromBody(response);
                    MessageList messageList = new Gson().fromJson(body, MessageList.class);
                    List<MessageList.CountryInfoEntity> messageListCountry_info = messageList.getCountry_info();
                    if (messageListCountry_info != null && messageListCountry_info.size() >= 0) {
                        country_infos.addAll(messageListCountry_info);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    T.showShort(getActivity(), error.toString());
                }
            });
        }
    }

    private void requestServiceInfolist() {
        /**
         * 1 是指国家id
         */
        RestPool.init().msgHomeInfoList(country_id, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                country_infos.clear();
                String body = ManagerUtil.fromBody(response);
                MessageList messageList = new Gson().fromJson(body, MessageList.class);
                List<MessageList.CountryInfoEntity> messageListCountry_info = messageList.getCountry_info();
                if (messageListCountry_info != null && messageListCountry_info.size() >= 0) {
                    country_infos.addAll(messageListCountry_info);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                T.showShort(getActivity(), error.toString());
            }
        });
    }

    private void init() {
        adapter = new MessageAdapter(getActivity(), country_infos);
        messageListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        messageListView.setAdapter(adapter);
        messageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageList.CountryInfoEntity countryInfoEntity = (MessageList.CountryInfoEntity) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), ArticleInfoDetails.class);
                intent.putExtra(Constants.ARTICLE_ID, countryInfoEntity.getArticle_id());
                intent.putExtra(Constants.ARTICLE_LIKE_NUM, countryInfoEntity.getLikes());
                intent.putExtra(Constants.ARTICLE_COMM_NUM, countryInfoEntity.getComments());
                intent.putExtra(Constants.ISLIKE, countryInfoEntity.getLiked());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
