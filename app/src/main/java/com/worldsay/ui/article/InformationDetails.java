package com.worldsay.ui.article;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.worldsay.Base.BaseActivity;
import com.worldsay.Constants;
import com.worldsay.R;
import com.worldsay.api.RestPool;
import com.worldsay.pojo.MessageDetails;
import com.worldsay.util.ManagerUtil;
import com.worldsay.util.T;
import com.worldsay.util.UIHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by long on 2015/8/19.
 */
public class InformationDetails extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.ll_details_like)
    LinearLayout llDetailsLike;
    @InjectView(R.id.ll_details_share)
    LinearLayout llDetailsShare;
    @InjectView(R.id.ll_details_commont)
    LinearLayout llDetailsCommont;
    @InjectView(R.id.like_img)
    ImageView likeImg;
    @InjectView(R.id.txt_message_title)
    TextView txtMessageTitle;
    @InjectView(R.id.txt_message_content)
    TextView txtMessageContent;
    private boolean islike = false;
    private int article_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(InformationDetails.this, R.layout.activity_infomation_details, contentView);
        ButterKnife.inject(this, view);
        requestServiceInfoDetails();
        initRes();
    }

    private void requestServiceInfoDetails() {
        RestPool.init().articleMessage(12, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String body = ManagerUtil.fromBody(response);
                MessageDetails messageDetails = new Gson().fromJson(body, MessageDetails.class);
                txtMessageContent.setText(messageDetails.getContent());
            }

            @Override
            public void failure(RetrofitError error) {
                T.show(InformationDetails.this, error.getMessage() + "", Toast.LENGTH_SHORT);
            }
        });
    }

    private void initRes() {
        article_id = getIntent().getIntExtra(Constants.ARTICLE_ID, 0);
        setTitle("资讯");
        topRightBtn.setVisibility(View.GONE);
        llDetailsCommont.setOnClickListener(this);
        llDetailsLike.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_details_commont:
                showToast("1");
                UIHelper.openActivity(this, MessageCommont.class);
                break;

            case R.id.ll_details_like:
                if (!islike) {
                    //喜欢
                    likeImg.setSelected(true);
                    islike = true;
                } else {
                    //不喜欢
                    likeImg.setSelected(false);
                    islike = false;
                }
                break;
        }
    }
}
