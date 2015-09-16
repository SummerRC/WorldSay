package com.worldsay.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.worldsay.R;
import com.worldsay.widget.UpdateTopBarTitleEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by LXG on 2015/8/30.
 */
public class MessageExploreBaseFragment extends Fragment {

    @InjectView(R.id.page)
    ViewPager page;
    private View view;
    private List<Fragment> mFragmentList = new ArrayList<>();       //数据源
    private FragmentAdapter mFragmentAdapter;   //适配器
    private Fragment squareFragment;        //消息
    private Fragment recruitingFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_message_explore_base, null);
        ButterKnife.inject(this, view);

        initView();
        return view;
    }

    private void initView() {
        String[] titls = {"资讯", "谈说"};
        squareFragment = new MessageFragment();
        recruitingFragment = new ExploreFragment();
        mFragmentList.add(squareFragment);
        mFragmentList.add(recruitingFragment);
        mFragmentAdapter = new FragmentAdapter(getChildFragmentManager(), mFragmentList);

        page.setAdapter(mFragmentAdapter);
        page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String title = null;
                if (position == 0) {
                    title = "资讯";
                } else if (position == 1) {
                    title = "探索";
                }
                EventBus.getDefault().post(new UpdateTopBarTitleEvent(title));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();
        String[] titles;

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
