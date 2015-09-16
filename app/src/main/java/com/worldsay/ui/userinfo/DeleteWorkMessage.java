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
public class DeleteWorkMessage extends BaseActivity {
    @InjectView(R.id.decelate)
    TextView decelate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View curView = View.inflate(DeleteWorkMessage.this, R.layout.activity_work_message, contentView);
        ButterKnife.inject(this, curView);
        initRes();
    }

    private void initRes() {
        decelate.setVisibility(View.VISIBLE);
        setTitle("工作信息");
        setRightButton(R.drawable.icon_arrow);
        decelate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("dWorkMessage");
                DeleteWorkMessage.this.sendBroadcast(intent);
                finish();
            }
        });
    }
}
