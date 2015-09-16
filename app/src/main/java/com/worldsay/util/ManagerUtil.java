package com.worldsay.util;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.mime.TypedInput;

/**
 * Created by gekson on 2015/8/28.
 */
public class ManagerUtil {
    /**
     * 从返回的Body中获取字段
     *
     * @param response
     * @return
     */
    public static String fromBody(retrofit.client.Response response) {
        TypedInput body = response.getBody();
        try {
            if (body != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(body.in()));
                StringBuilder out = new StringBuilder();
                String newLine = System.getProperty("line.separator");
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                    out.append(newLine);
                }
                // Prints the correct String representation of body.
                return out.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> fromCsv(Context context) {
        try {
            // Get input stream and Buffered Reader for our data file.
            InputStream is = context.getAssets().open("country.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            List<String> result = new ArrayList<>();
            //Read each line
            while ((line = reader.readLine()) != null) {
                //Split to separate the name from the capital
                String[] RowData = line.split(",");
                // country name
                result.add(RowData[1].replace("\"", ""));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 检查是否存在sd卡
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
}
