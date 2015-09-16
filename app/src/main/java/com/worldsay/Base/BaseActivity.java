package com.worldsay.Base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.worldsay.R;


public class BaseActivity extends Activity {

    protected ViewGroup contentView;
    protected TextView topTitleTxt;
    protected ImageView topLeftBtn;
    protected ImageView topRightBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        contentView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        topTitleTxt = (TextView) contentView.findViewById(R.id.base_activity_title);
        topLeftBtn = (ImageView) contentView.findViewById(R.id.left_btn);
        topRightBtn = (ImageView) contentView.findViewById(R.id.right_btn);
        topLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setContentView(contentView);
    }

    @Override
        public void setTitle(int id) {
        String strTitle = getResources().getString(id);
        setTitle(strTitle);
    }

    protected void setTitle(String title) {
        if (title == null) {
            return;
        }
        if (title.length() > 12) {
            title = title.substring(0, 11) + "...";
        }
        topTitleTxt.setText(title);
        topTitleTxt.setVisibility(View.VISIBLE);
    }

    /**
     * 设置左边按钮图标
     * @param resID
     */
    protected void setLeftButton(int resID) {
        if (resID <= 0) {
            return;
        }

        topLeftBtn.setImageResource(resID);
        topLeftBtn.setVisibility(View.VISIBLE);
    }

    /**
     * 右侧按钮
     * @param resID
     */
    protected void setRightButton(int resID) {
        if (resID <= 0) {
            return;
        }

        topRightBtn.setImageResource(resID);
        topRightBtn.setVisibility(View.VISIBLE);
    }
    /**
     * 短Toast
     *
     * @param message
     */
    protected  void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
