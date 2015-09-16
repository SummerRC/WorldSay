package com.worldsay.ui.article;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.worldsay.Base.BaseActivity;
import com.worldsay.Constants;
import com.worldsay.R;
import com.worldsay.api.RestPool;
import com.worldsay.pojo.IsSuccessFul;
import com.worldsay.util.SharedPreferenceUtils;
import com.worldsay.util.T;
import com.worldsay.util.UIHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by LXG on 2015/8/29.
 */
public class ArticleInfoDetails extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.article_web_view)
    WebView articleWebView;
    @InjectView(R.id.like_img)
    ImageView likeImg;
    @InjectView(R.id.ll_details_like)
    LinearLayout llDetailsLike;
    @InjectView(R.id.ll_details_share)
    LinearLayout llDetailsShare;
    @InjectView(R.id.ll_details_commont)
    LinearLayout llDetailsCommont;
    @InjectView(R.id.txt_article_like_num)
    TextView txtArticleLikeNum;
    @InjectView(R.id.txt_article_comm_num)
    TextView txtArticleCommNum;

    private int article_id;
    private View curView;
    private int article_like_num;
    private int article_comm_num;
    private boolean isBooleanLike = false;
    private String userToken;
    private boolean isLogin;
    private int userUid;
    private int islike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curView = View.inflate(ArticleInfoDetails.this, R.layout.activity_article_details, contentView);
        userToken = SharedPreferenceUtils.getStringDate(ArticleInfoDetails.this, Constants.USER_TOKEN, "");
        isLogin = SharedPreferenceUtils.getBooleanDate(ArticleInfoDetails.this, Constants.ISLOGINING, false);
        userUid = SharedPreferenceUtils.getIntDate(ArticleInfoDetails.this, Constants.USER_ID, 0);
        Intent intent = getIntent();
        article_id = intent.getIntExtra(Constants.ARTICLE_ID, 0);
        article_like_num = intent.getIntExtra(Constants.ARTICLE_LIKE_NUM, 0);
        article_comm_num = intent.getIntExtra(Constants.ARTICLE_COMM_NUM, 0);
        islike = intent.getIntExtra(Constants.ISLIKE, 0);
        initRes();
    }

    private void initRes() {
        ButterKnife.inject(this, curView);
        setTitle("资讯");
        topRightBtn.setVisibility(View.GONE);
        llDetailsLike.setOnClickListener(this);
        llDetailsCommont.setOnClickListener(this);
        initWebView();
        initData();
    }

    private void initData() {
        txtArticleLikeNum.setText(article_like_num + "");
        txtArticleCommNum.setText(article_comm_num + "");
        if (islike == 1) {
            likeImg.setSelected(true);
        } else {
            likeImg.setSelected(false);
        }
    }

    private void initWebView() {
        articleWebView.setWebViewClient(new WebViewClient() { //通过webView打开链接，不调用系统浏览器

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }
        });

        WebSettings webSettings = articleWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String url = Constants.ARTICLEINFODETAILS_URL + article_id;
        if (null != url) {
            articleWebView.loadUrl(url);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_details_like:
                if (isLogin) {
                    if (isBooleanLike) {
                        likeImg.setSelected(true);
                        requestServiceLike();
                        isBooleanLike = false;
                    } else {
                        likeImg.setSelected(false);
                        isBooleanLike = true;
                        requestServiceCancleLike();
                    }
                } else {
                    T.showShort(ArticleInfoDetails.this, "您还沒有登录");
                }
                break;
            case R.id.ll_details_commont:
                Intent intent = new Intent(ArticleInfoDetails.this, MessageCommont.class);
                intent.putExtra(Constants.ARTICLE_ID, article_id);
                startActivity(intent);
                break;
        }
    }

    /**
     * 取消赞
     */
    private void requestServiceCancleLike() {
        RestPool.initBasic().cancleLikeInfo(userToken, article_id, userUid, new Callback<IsSuccessFul>() {
            @Override
            public void success(IsSuccessFul isSuccessFul, Response response) {
                if (isSuccessFul.is_successful()) {
                    if (isSuccessFul.is_successful()) {
                        T.showShort(ArticleInfoDetails.this, "取消赞成功");
                        article_like_num -- ;
                        txtArticleLikeNum.setText(article_like_num+ "");
                    } else {
                        T.showShort(ArticleInfoDetails.this, isSuccessFul.getMessage());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                T.showShort(ArticleInfoDetails.this, error.getMessage());
            }
        });
    }

    /**
     * 点赞
     */
    private void requestServiceLike() {
        RestPool.initBasic().likeInfo(userToken, article_id, new Callback<IsSuccessFul>() {
            @Override
            public void success(IsSuccessFul isSuccessFul, Response response) {
                if (isSuccessFul.is_successful()) {
                    T.showShort(ArticleInfoDetails.this, "点赞成功");
                    article_like_num ++;
                    txtArticleLikeNum.setText(article_like_num + "");
                } else {
                    T.showShort(ArticleInfoDetails.this, isSuccessFul.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                T.showShort(ArticleInfoDetails.this, error.getMessage());
            }
        });

    }
}
