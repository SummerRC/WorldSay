package com.worldsay.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.worldsay.Base.BaseActivity;
import com.worldsay.R;
import com.worldsay.api.RestPool;
import com.worldsay.pojo.IsSuccessFul;
import com.worldsay.util.T;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by wenlong on 2015/9/4.
 */
public class ResetPasswordActivity extends BaseActivity {

    @InjectView(R.id.edit_reset_old_pwd)
    EditText editResetOldPwd;
    @InjectView(R.id.edit_reset_new_pwd)
    EditText editResetNewPwd;
    @InjectView(R.id.edit_reset_new_verify)
    EditText editResetNewVerify;
    @InjectView(R.id.button_reset_password)
    Button buttonResetPassword;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(ResetPasswordActivity.this, R.layout.activity_reset_password, contentView);
        ButterKnife.inject(this);
        setTitle("修改密码");
        initRes();
        topRightBtn.setVisibility(View.GONE);
    }


    private void initRes() {
        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestResetPassword();
            }
        });
    }

    private void requestResetPassword() {
        String resetOldPwd = editResetOldPwd.getText().toString().trim();
        String resetNewPwd = editResetNewPwd.getText().toString().trim();
        String resetNewVerify = editResetNewVerify.getText().toString().trim();
        if (TextUtils.isEmpty(resetOldPwd)) {
            T.showShort(ResetPasswordActivity.this, "原密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(resetNewPwd)) {
            T.showShort(ResetPasswordActivity.this, "新密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(resetNewVerify)) {
            T.showShort(ResetPasswordActivity.this, "输入不能为空");
            return;
        }
        if (!resetNewPwd.equals(resetNewVerify)){
            T.showShort(ResetPasswordActivity.this, "两次输入不一致");
            return;
        }
        RestPool.initBasic().resetPassWord(resetOldPwd, resetNewPwd, new Callback<IsSuccessFul>() {
            @Override
            public void success(IsSuccessFul isSuccessFul, Response response) {
                if (isSuccessFul.is_successful()){
                    T.showShort(ResetPasswordActivity.this,"修改成功");
                    finish();
                }else{
                    if (isSuccessFul.getError() == 401){
                        T.showShort(ResetPasswordActivity.this,isSuccessFul.getMessage());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                T.showShort(ResetPasswordActivity.this, error.getMessage());
            }
        });
    }
}
