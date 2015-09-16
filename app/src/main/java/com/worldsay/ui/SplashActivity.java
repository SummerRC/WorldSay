package com.worldsay.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.worldsay.Constants;
import com.worldsay.R;
import com.worldsay.util.SharedPreferenceUtils;
import com.worldsay.util.UIHelper;


/**
 * Created by wenlong on 2015/9/3.
 */
public class SplashActivity extends Activity {
    private Handler uiHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isfirstsplash = SharedPreferenceUtils.getBooleanDate(SplashActivity.this, Constants.ISFIRSTSPLASH, false);
        if (isfirstsplash) {
            setContentView(R.layout.activity_splash);
            uiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    intent.putExtra(Constants.SHOW_WHICH_FRAGMENT,"message");
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        } else {
            UIHelper.openActivity(SplashActivity.this, WelcomeActivity.class);
            finish();
        }
    }
}
