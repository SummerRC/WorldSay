package com.worldsay.api;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.worldsay.util.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gekson on 2015/8/26.
 */
public class RequestMap extends HashMap<String, String> {

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : this.entrySet()) {
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
            builder.append("&");
        }
        String returnStr = builder.toString();
        String substring = returnStr.substring(0, returnStr.length() - 2);
        LogUtils.e(substring);
        return substring;
    }

    @Override
    public String put(String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            return super.put(key, value);
        }
        return null;
    }
}
