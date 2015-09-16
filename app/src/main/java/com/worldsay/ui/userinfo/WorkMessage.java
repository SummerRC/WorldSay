package com.worldsay.ui.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.worldsay.Base.BaseActivity;
import com.worldsay.Constants;
import com.worldsay.R;
import com.worldsay.api.RestPool;
import com.worldsay.pojo.IsSuccessFul;
import com.worldsay.util.SharedPreferenceUtils;
import com.worldsay.util.T;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by LXG on 2015/8/27.
 */
public class WorkMessage extends BaseActivity implements Serializable {
    @InjectView(R.id.edit_work_name)
    EditText editWorkName;
    @InjectView(R.id.edit_work_bumen)
    EditText editWorkBumen;
    @InjectView(R.id.edit_work_time)
    EditText editWorkTime;
    @InjectView(R.id.decelate)
    TextView decelate;
    private TextView detelate;
    private String userToken;
    private int userUid;
    private String workNameStr;
    private String workBumeStr;
    private String workTimeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       View view = View.inflate(WorkMessage.this, R.layout.activity_work_message, contentView);
        detelate = (TextView) findViewById(R.id.decelate);
        userToken = SharedPreferenceUtils.getStringDate(WorkMessage.this, Constants.USER_TOKEN, "");
        userUid = SharedPreferenceUtils.getIntDate(WorkMessage.this, Constants.USER_ID, 0);
        ButterKnife.inject(this,view);
        initRes();
    }

    private void initRes() {
        setTitle("工作信息");
        setRightButton(R.drawable.icon_arrow);
        topRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workNameStr = editWorkName.getText().toString().trim();
                workBumeStr = editWorkBumen.getText().toString().trim();
                workTimeStr = editWorkTime.getText().toString().trim();
                if (TextUtils.isEmpty(workNameStr)) {
                    T.showShort(WorkMessage.this, "公司名称不能为空");
                    return;
                }
                if (TextUtils.isEmpty(workBumeStr)) {
                    T.showShort(WorkMessage.this, "部门不能为空");
                    return;
                }
                if (TextUtils.isEmpty(workTimeStr)) {
                    T.showShort(WorkMessage.this, "在职时间不能为空");
                    return;
                }
                requestServiceWorkMessage();
            }
        });
    }

    /**
     * 修改用戶工作信息
     */
    private void requestServiceWorkMessage() {
       String work =  workNameStr +","+workBumeStr +":" +workTimeStr;
        RestPool.initBasic().updateUserInfo(userToken, userUid, work, new Callback<IsSuccessFul>() {
            @Override
            public void success(IsSuccessFul isSuccessFul, Response response) {
                if (isSuccessFul.is_successful()) {
                    T.showShort(WorkMessage.this, "上传工作信息成功");
                    Intent intent = new Intent();
                    intent.putExtra("us","公司的名字");
                    intent.setAction("WorkMessage");
                    WorkMessage.this.sendBroadcast(intent);
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                T.showShort(WorkMessage.this, error.getMessage());
            }
        });

    }
}
