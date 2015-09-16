package com.worldsay.adpter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.squareup.picasso.Picasso;
import com.worldsay.R;
import com.worldsay.pojo.ExploreOptionList;

import java.util.List;

/**
 * Created by gekson on 2015/8/23.
 */
public class ExploreAdapter extends CommonAdapter<ExploreOptionList> {

    public ExploreAdapter(Context context, List<ExploreOptionList> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, ExploreOptionList item) {
        ImageView exploreImage = helper.getView(R.id.iv_explore);
        TextView titleText = helper.getView(R.id.tv_explore_title);
        RoundCornerProgressBar progressBar = helper.getView(R.id.tv_explore_hot);
        TextView progress = helper.getView(R.id.tv_progress);
        TextView exploreDesc = helper.getView(R.id.tv_explore_desc);
        TextView exploreType = helper.getView(R.id.tv_type);

        Picasso.with(mContext).load(item.getOpt_img_url()).into((exploreImage));
        titleText.setText(item.getOpt_title());
        progressBar.setProgress(Float.valueOf(item.getOpt_porpularity()) * 10);
        progress.setText(item.getOpt_porpularity());
        exploreDesc.setText(item.getOpt_lead());
        exploreType.setText(item.getOpt_type());
    }
}
