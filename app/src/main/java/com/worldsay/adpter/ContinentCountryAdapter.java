package com.worldsay.adpter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.worldsay.R;
import com.worldsay.pojo.Country;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by gekson on 2015/8/19.
 */
public class ContinentCountryAdapter extends RecyclerView.Adapter<ContinentCountryAdapter.ViewHolder> {

    private List<Country> mCountryList;

    public ContinentCountryAdapter(List<Country> mCountryList) {
        this.mCountryList = mCountryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_area_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Country country = mCountryList.get(position);
//        if (country != null) {
//            String countryName = country.getName();
//            String countryFlagImageUrl = country.getUrl();
//            if (!TextUtils.isEmpty(countryName)) {
//                holder.countryName.setText(countryName);
//            }
//            if (!TextUtils.isEmpty(countryFlagImageUrl)) {
//            }
//            holder.countryFlagImage.setBackgroundResource(R.drawable.img_1);
//        }
    }

    @Override
    public int getItemCount() {
        return mCountryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View view;
        @InjectView(R.id.fl_country_image)
        FrameLayout countryFlagImage;
        @InjectView(R.id.tv_country_name)
        TextView countryName;


        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.inject(this, view);
        }

    }
}
