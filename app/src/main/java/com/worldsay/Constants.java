package com.worldsay;

import android.os.Environment;

/**
 * Created by gekson on 2015/8/28.
 */
public class Constants {
    // RequestCode
    public static final int TAKE_PICTURE = 91;
    public static final int LOAD_IMAGE = 92;
    public static final int IMAGE_ZOOM = 93;
    public static final int RESULT_OK = -1;

    public static final String BASE_URL = "http://123.59.15.61";
    public static String BASIC_AUTHORIZATION = "basic_authorization";
    public static final String SDCARD_CACHDIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tempImage/";
    public static String AUTHORIZATION_TOKEN = "";
    public static String ISFIRSTSPLASH = "isfirstsplash";
    public static String ISLOGINING = "islogining";
    public static String USER_ID = "user_id";
    public static String USER_TOKEN = "user_token";
    public static String SHOW_WHICH_FRAGMENT = "show_which_fragment";
    public static String LOGIN_STATUS = "login_status";
    //城市
    public static String COUNTRY_ID = "country_id";
    //文章id
    public static String ARTICLE_ID = "article_id";
    public static String COMMENT_ID = "comment_id";
    public static String ARTICLE_LIKE_NUM ="article_like_num";
    public static String ARTICLE_COMM_NUM ="article_COMM_num";
    public static String ISLIKE ="islike";
    public static int STATUS_USERINFO =0;
    public static int STATUS_COMMENT =1;

    //资讯详情的url
    public static String ARTICLEINFODETAILS_URL = "http://123.59.15.61/api/info/article?article_id=";
}
