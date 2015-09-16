package com.worldsay.ui.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.worldsay.Base.BaseActivity;
import com.worldsay.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LXG on 2015/8/27.
 */
public class DeleteTeachMessage extends BaseActivity {
    @InjectView(R.id.decelate)
    TextView decelate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View curView = View.inflate(DeleteTeachMessage.this, R.layout.activity_work_message, contentView);
        ButterKnife.inject(this, curView);
        initRes();
    }

    private void initRes() {
        decelate.setVisibility(View.VISIBLE);
        setTitle("教育信息");
        setRightButton(R.drawable.icon_arrow);
        decelate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("dTeachMessage");
                DeleteTeachMessage.this.sendBroadcast(intent);
                finish();
            }
        });
    }
}
