package com.worldsay.api;

import android.util.Base64;

import com.google.gson.Gson;
import com.worldsay.Constants;
import com.worldsay.MainApplication;
import com.worldsay.util.SharedPreferenceUtils;

import java.io.UnsupportedEncodingException;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by gekson on 2015/8/27.
 */
public class RestPool {

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_BASIC = 1;


    public static WordSayService init() {
        return initWithType(TYPE_NORMAL);
    }

    public static WordSayService initBasic() {
        return initWithType(TYPE_BASIC);
    }

    private static WordSayService initWithType(int type) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)
                .setRequestInterceptor(new MyRequestInterceptor(type))
                .setConverter(new GsonConverter(new Gson()))
                .setClient(new OkClient())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create(WordSayService.class);
    }

    public static class MyRequestInterceptor implements RequestInterceptor {

        private int type;

        public MyRequestInterceptor(int type) {
            this.type = type;
        }

        @Override
        public void intercept(RequestFacade request) {
            if (type == TYPE_BASIC) {
                try {
                    String token = SharedPreferenceUtils.getStringDate(MainApplication.getInstance(), Constants.BASIC_AUTHORIZATION, "");
                    request.addHeader("Authorization", "Basic " + new String(Base64.encode(token.getBytes(), Base64.DEFAULT), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
}
        }
        }
