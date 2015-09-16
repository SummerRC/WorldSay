package com.worldsay.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.worldsay.Base.BaseActivity;
import com.worldsay.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LXG on 2015/8/24.
 */
public class ForgetPwd extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.edit_retrieve_email)
    EditText editRetrieveEmail;
    @InjectView(R.id.edit_retrieve_code)
    EditText editRetrieveCode;
    @InjectView(R.id.retrieve_img_vercode)
    ImageView retrieveImgVercode;
    @InjectView(R.id.retrieve_tv_exchange)
    TextView retrieveTvExchange;
    @InjectView(R.id.button_retrieve_password)
    Button retrievePassword;
    private View curView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curView = View.inflate(ForgetPwd.this, R.layout.activity_forget_pwd, contentView);
        initRes();

    }

    private void initRes() {
        ButterKnife.inject(this,curView);
        setTitle("忘记密码");
        topRightBtn.setVisibility(View.GONE);
        retrievePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_retrieve_password:
                break;
        }
    }
}
