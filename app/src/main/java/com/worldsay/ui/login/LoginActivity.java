package com.worldsay.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.worldsay.Constants;
import com.worldsay.R;
import com.worldsay.api.RestPool;
import com.worldsay.pojo.User;
import com.worldsay.ui.MainActivity;
import com.worldsay.ui.article.MessageCommont;
import com.worldsay.ui.fragments.PersonalDataFragment;
import com.worldsay.util.ManagerUtil;
import com.worldsay.util.SharedPreferenceUtils;
import com.worldsay.util.T;
import com.worldsay.util.UIHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by LXG on 2015/8/22.
 * login_status
 * <p/>
 * 0 : 个人资料页
 * 1 : 评论页
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    @InjectView(R.id.left_btn_back)
    ImageView leftBtnBack;
    @InjectView(R.id.txt_register_btn)
    TextView txtRegisterBtn;
    @InjectView(R.id.login_name)
    EditText loginEmail;
    @InjectView(R.id.login_pwd)
    EditText loginPwd;
    @InjectView(R.id.forget_pwd_txt)
    TextView forgetPwdTxt;
    @InjectView(R.id.login)
    Button login;
    private int login_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        initRes();

    }

    private void initRes() {
        login_status = getIntent().getIntExtra(Constants.LOGIN_STATUS, 0);
        leftBtnBack.setOnClickListener(this);
        txtRegisterBtn.setOnClickListener(this);
        forgetPwdTxt.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn_back:
                finish();
                break;
            case R.id.txt_register_btn:
                UIHelper.openActivity(LoginActivity.this, Register.class);
                break;
            case R.id.login:
                hideInputMethod();
                String email = loginEmail.getText().toString().trim();
                String password = loginPwd.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    T.show(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    T.show(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT);
                    return;
                }
                final String token = email + ":" + password;
                SharedPreferenceUtils.saveStringDate(this, Constants.BASIC_AUTHORIZATION, token);
                RestPool.initBasic().userSignIn(email, password, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        String body = ManagerUtil.fromBody(response);
                        User user = new Gson().fromJson(body, User.class);
                        if (!TextUtils.isEmpty(user.getToken())) {
                            T.show(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT);
                            SharedPreferenceUtils.saveBooleanDate(LoginActivity.this, Constants.ISLOGINING, true);
                            SharedPreferenceUtils.saveIntDate(LoginActivity.this, Constants.USER_ID, user.getUid());
                            SharedPreferenceUtils.saveStringDate(LoginActivity.this, Constants.USER_TOKEN, user.getToken());
                            if (login_status == 0) {
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.putExtra(Constants.LOGIN_STATUS, Constants.STATUS_USERINFO);
                                startActivity(i);
                                finish();
                            } else if (login_status == 1) {
                                finish();
                            }

                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        T.show(LoginActivity.this, error.getMessage() + "", Toast.LENGTH_SHORT);
                    }
                });
                break;
            case R.id.forget_pwd_txt:
                UIHelper.openActivity(LoginActivity.this, ForgetPwd.class);
                break;
        }
    }

    private void hideInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        imm.showSoftInput(loginPwd, InputMethodManager.SHOW_FORCED);
//        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
