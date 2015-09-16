package com.worldsay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.worldsay.Constants;
import com.worldsay.R;
import com.worldsay.ui.fragments.ExploreFragment;
import com.worldsay.ui.fragments.MessageExploreBaseFragment;
import com.worldsay.ui.fragments.PersonalDataFragment;
import com.worldsay.ui.login.LoginActivity;
import com.worldsay.util.SharedPreferenceUtils;
import com.worldsay.util.T;
import com.worldsay.util.UIHelper;
import com.worldsay.widget.UpdateTopBarTitleEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    @InjectView(R.id.showMenu)
    ImageView showMenu;

    @InjectView(R.id.iv_option)
    ImageView moreOption;

//    @InjectView(R.id.ll_dot)
//    LinearLayout dotll;

    RelativeLayout menuSetting;

    TextView txtUserInfo;

    TextView menuHomeTxt;

    TextView txtProbe;

    TextView textMyCommont;

    @InjectView(R.id.tv_main_title)
    TextView mainTitleText;

    private SlidingMenu menu;
    private boolean isLogin = false;//判断登录状态
    private PersonalDataFragment personalDataFragment;
    private List<View> dotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        dotList=new ArrayList<View>();
        isLogin = SharedPreferenceUtils.getBooleanDate(MainActivity.this, Constants.ISLOGINING, false);
        initShowLeftMenu();
        initSlidingMenu();
//        initDot();
        showMessageFragment();
    }



    @Override
    protected void onResume() {
        super.onResume();
        switchShowFragment();
    }

    public void onEvent(UpdateTopBarTitleEvent event) {
        if (!TextUtils.isEmpty(event.getTitle())) {
            mainTitleText.setText(event.getTitle());
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void switchShowFragment() {
        int show_which = getIntent().getIntExtra(Constants.LOGIN_STATUS, 100);
        if (show_which == 0) {
            showPersonDataFragment();
        }
    }
    private void initSlidingMenu() {
        // configure the SlidingMenu
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);

        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        View view = View.inflate(this, R.layout.layout_menu, null);
        menuSetting = ButterKnife.findById(view, R.id.menu_setting);
        txtUserInfo = ButterKnife.findById(view, R.id.txt_user_info);
        menuHomeTxt = ButterKnife.findById(view, R.id.menu_home_txt);
        txtProbe = ButterKnife.findById(view, R.id.txt_probe);
        textMyCommont = ButterKnife.findById(view, R.id.text_my_commont);
        initRes();
        menu.setMenu(view);
    }

    private void initRes() {
        menuSetting.setOnClickListener(this);
        txtUserInfo.setOnClickListener(this);
        menuHomeTxt.setOnClickListener(this);
        txtProbe.setOnClickListener(this);
        textMyCommont.setOnClickListener(this);
    }

    private void initShowLeftMenu() {
        showMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });
    }
    private void initDot() {
        dotList.clear();
        for(int i=0;i<2;i++){
            View view = new View(this);
            if(i == 0){
                view.setBackgroundResource(R.drawable.dot_blue);
            }else{
                view.setBackgroundResource(R.drawable.dot_white);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10, 10);
            layoutParams.setMargins(0, 0, 10, 0);
            dotList.add(view);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //首页
            case R.id.menu_home_txt:
                showMessageFragment();
                break;

            //个人信息
            case R.id.txt_user_info:
                if (!isLogin) {
                    UIHelper.openActivity(MainActivity.this, LoginActivity.class);
                } else {
                    showPersonDataFragment();
                }
                break;

            //探索
            case R.id.txt_probe:
                showExploreFragment();
                break;

            //评论
            case R.id.text_my_commont:
                T.showShort(MainActivity.this, "评论");
                break;
            //设置
            case R.id.menu_setting:
                UIHelper.openActivity(MainActivity.this, MenuSetting.class);
                break;
        }
        menu.toggle();
    }

    private void showExploreFragment() {
        mainTitleText.setText("探索");
        moreOption.setImageResource(R.mipmap.show_map_icon);
        moreOption.setClickable(true);
        moreOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.openActivity(MainActivity.this, CountryActivity.class);
            }
        });
        setTabSelection(new ExploreFragment());
    }

    private void showMessageFragment() {
        mainTitleText.setText("资讯");
        moreOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.openActivity(MainActivity.this, CountryActivity.class);
            }
        });
        moreOption.setImageResource(R.mipmap.show_map_icon);
        setTabSelection(new MessageExploreBaseFragment());
    }

    private void showPersonDataFragment() {
        mainTitleText.setText("个人资料");
        moreOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        moreOption.setImageResource(R.drawable.icon_arrow);
        personalDataFragment = new PersonalDataFragment();
        setTabSelection(personalDataFragment);
    }

    public void setTabSelection(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment != null) {
            transaction.replace(R.id.fl_content, fragment).commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        personalDataFragment.onActivityResult(requestCode, resultCode, data);
    }
}
