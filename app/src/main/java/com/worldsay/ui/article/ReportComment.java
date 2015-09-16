package com.worldsay.ui.article;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.worldsay.Constants;
import com.worldsay.R;
import com.worldsay.api.RestPool;
import com.worldsay.pojo.IsSuccessFul;
import com.worldsay.util.SharedPreferenceUtils;
import com.worldsay.util.T;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by wenlong on 2015/9/4.
 */
public class ReportComment extends Activity implements View.OnClickListener {

    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_report)
    TextView tvReport;
    private int article_id;
    private int comment_id;
    private String user_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_comment);
        ButterKnife.inject(this);
        initRes();

    }

    private void initRes() {
        article_id = getIntent().getIntExtra(Constants.ARTICLE_ID, 0);
        comment_id = getIntent().getIntExtra(Constants.COMMENT_ID, 0);
        user_token = SharedPreferenceUtils.getStringDate(ReportComment.this, Constants.USER_TOKEN, "");
        ivBack.setOnClickListener(this);
        tvReport.setOnClickListener(this);
    }

    private void requestServiceReportComment() {
        RestPool.initBasic().reportInfoComment(user_token, article_id, comment_id, 1, new Callback<IsSuccessFul>() {
            @Override
            public void success(IsSuccessFul isSuccessFul, Response response) {
                if (isSuccessFul.is_successful()) {
                    T.showShort(ReportComment.this, "举报成功");
                    finish();
                } else {
                    T.showShort(ReportComment.this, "举报失败");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                T.showShort(ReportComment.this, error.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_report:
                requestServiceReportComment();
                break;
            case R.id.iv_back:
                finish();
                break;
        }

    }
}
