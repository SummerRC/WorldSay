package com.worldsay.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.worldsay.Constants;
import com.worldsay.R;
import com.worldsay.pojo.Country;
import com.worldsay.ui.MainActivity;
import com.worldsay.ui.SplashActivity;
import com.worldsay.util.SharedPreferenceUtils;
import com.worldsay.util.UIHelper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by gekson on 2015/8/19.
 */
public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private List<Country> mCountryList;
    private Context ctx;

    public CountryAdapter(Context ctx, List<Country> mCountryList) {
        this.mCountryList = mCountryList;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_country_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Country country = mCountryList.get(position);
        if (country != null) {
            String countryName = country.getCountry_name();
            String countryFlagImageUrl = country.getCountry_image_url();
            if (!TextUtils.isEmpty(countryName)) {
                holder.countryName.setText(countryName);
            }
            if (!TextUtils.isEmpty(countryFlagImageUrl)) {
                Picasso.with(ctx).load(countryFlagImageUrl).into(holder.countryFlagImage);
            }
            holder.fl_country.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferenceUtils.saveIntDate(ctx, Constants.COUNTRY_ID, country.getCountry_id());
                    Intent intent = new Intent(ctx,MainActivity.class);
                    intent.putExtra(Constants.SHOW_WHICH_FRAGMENT, "message");
                    ctx.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCountryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View view;
        @InjectView(R.id.iv_country_flag)
        ImageView countryFlagImage;
        @InjectView(R.id.tv_country_name)
        TextView countryName;
        @InjectView(R.id.fl_country)
        FrameLayout fl_country;


        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.inject(this, view);
        }

    }
}
