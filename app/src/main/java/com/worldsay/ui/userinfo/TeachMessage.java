package com.worldsay.ui.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.worldsay.Base.BaseActivity;
import com.worldsay.R;

/**
 * Created by LXG on 2015/8/27.
 */
public class TeachMessage extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View.inflate(TeachMessage.this, R.layout.activity_teach_message, contentView);
        initRes();
    }

    private void initRes() {
        setTitle("教育信息");
        setRightButton(R.drawable.icon_arrow);
        topRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("us","学校的名字");
                intent.setAction("teachMessage");
                TeachMessage.this.sendBroadcast(intent);
                finish();
            }
        });
    }
}
