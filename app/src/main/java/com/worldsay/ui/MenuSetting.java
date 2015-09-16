package com.worldsay.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.worldsay.Base.BaseActivity;
import com.worldsay.Constants;
import com.worldsay.R;
import com.worldsay.ui.login.ForgetPwd;
import com.worldsay.ui.login.LoginActivity;
import com.worldsay.ui.login.ResetPasswordActivity;
import com.worldsay.util.SharedPreferenceUtils;
import com.worldsay.util.UIHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LXG on 2015/8/21.
 */
public class MenuSetting extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.NotificationNoDisturbCheckbox)
    CheckBox NotificationNoDisturbCheckbox;
    @InjectView(R.id.feedback)
    RelativeLayout feedback;
    @InjectView(R.id.tv_exit_login)
    TextView tvExitLogin;
    @InjectView(R.id.rl_change_login_pwd)
    RelativeLayout rlChangeLoginPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View curView = View.inflate(MenuSetting.this, R.layout.activity_menu_setting, contentView);
        ButterKnife.inject(this, curView);
        initRes();

    }

    private void initRes() {
        setTitle("设置");
        topRightBtn.setVisibility(View.GONE);
        feedback.setOnClickListener(this);
        tvExitLogin.setOnClickListener(this);
        rlChangeLoginPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback:
                UIHelper.openActivity(MenuSetting.this, FeedBack.class);
                break;

            case R.id.rl_change_login_pwd:
                UIHelper.openActivity(MenuSetting.this, ResetPasswordActivity.class);
                break;

            case R.id.tv_exit_login:
                UIHelper.openActivity(MenuSetting.this, LoginActivity.class);
                finish();
                SharedPreferenceUtils.saveBooleanDate(MenuSetting.this, Constants.ISLOGINING, false);
                break;
        }
    }
}
