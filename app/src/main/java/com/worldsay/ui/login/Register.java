package com.worldsay.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.worldsay.Base.BaseActivity;
import com.worldsay.R;
import com.worldsay.api.RequestMap;
import com.worldsay.api.RestPool;
import com.worldsay.pojo.User;
import com.worldsay.util.T;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by LXG on 2015/8/24.
 */
public class Register extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.edit_register_email)
    EditText editRegisterEmail;
    @InjectView(R.id.edit_register_username)
    EditText editRegisterUsername;
    @InjectView(R.id.edit_register_password)
    EditText editRegisterPassword;
    @InjectView(R.id.edit_register_password_make_sure)
    EditText editRegisterPasswordMakeSure;
    @InjectView(R.id.register_rb_agree)
    RadioButton registerRbAgree;
    @InjectView(R.id.button_register)
    Button buttonRegister;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(Register.this, R.layout.activity_register, contentView);
        initRes();

    }

    private void initRes() {
        ButterKnife.inject(this, view);
        setTitle("注册");
        topRightBtn.setVisibility(View.GONE);
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_register:
                requestServiceRegister();
                break;
        }
    }

    /**
     * 注册请求
     */
    private void requestServiceRegister() {
        String registerEmail = editRegisterEmail.getText().toString().trim();
        String registerUsername = editRegisterUsername.getText().toString().trim();
        String registerPassword = editRegisterPassword.getText().toString().trim();
        String verifyPassword = editRegisterPasswordMakeSure.getText().toString().trim();
        if (TextUtils.isEmpty(registerEmail)) {
            T.showShort(Register.this, "邮箱不能为空");
            return;
        }
        if (TextUtils.isEmpty(registerUsername)) {
            T.showShort(Register.this, "用户名不能为空");
            return;
        }

        if (TextUtils.isEmpty(registerPassword)) {
            T.showShort(Register.this, "密码不能为空");
            return;
        }
        if (!registerPassword.equals(verifyPassword)) {
            T.showShort(Register.this, "两次密码不一致");
            return;
        }

        RestPool.init().userSignUp(registerUsername, registerPassword, registerEmail, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                if (!TextUtils.isEmpty(user.getUsername())) {
                    T.showShort(Register.this, "注册成功");
                    finish();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                T.showShort(Register.this, error.toString());
            }
        });
    }
}
