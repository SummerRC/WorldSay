package com.worldsay.adpter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.worldsay.Constants;
import com.worldsay.R;
import com.worldsay.api.RestPool;
import com.worldsay.pojo.IsSuccessFul;
import com.worldsay.pojo.MessageList;
import com.worldsay.util.DateUtil;
import com.worldsay.util.SharedPreferenceUtils;
import com.worldsay.util.T;
import com.worldsay.widget.ActionSheetDialog;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by LXG on 2015/8/22.
 */
public class MessageAdapter extends BaseAdapter {
    private Context ctx;
    private List<MessageList.CountryInfoEntity> country_infos;
    private boolean islike = false; //判断是否喜欢
    private final String userToken;

    public MessageAdapter(Context ctx, List<MessageList.CountryInfoEntity> country_infos) {
        this.ctx = ctx;
        this.country_infos = country_infos;
        userToken = SharedPreferenceUtils.getStringDate(ctx, Constants.USER_TOKEN, "");
    }

    public void remove(int po) {
        country_infos.remove(po);
        notifyDataSetChanged();
    }


    public void OnClickable(View v, final int position, final View obj) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.message_delet:
                        showButtomMenu(position);
                        break;
                }
            }
        });
    }

    //弹出底部菜单
    private void showButtomMenu(final int position) {
        new ActionSheetDialog(ctx)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("重复/旧闻", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                remove(position);
                                disLikeArticle(position);
                            }
                        })
                .addSheetItem("话题不感兴趣", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                remove(position);
                                disLikeArticle(position);
                            }
                        })
                .addSheetItem("内容质量差", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                remove(position);
                                disLikeArticle(position);
                            }
                        })
                .show();
    }

    private void disLikeArticle(int position) {
        RestPool.initBasic().dislikeInfo(userToken, country_infos.get(position).getArticle_id(), new Callback<IsSuccessFul>() {
            @Override
            public void success(IsSuccessFul s, Response response) {
                if (s.is_successful()) {
                    T.showShort(ctx, "操作成功");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                T.showShort(ctx, error.getMessage());
            }
        });
    }

    @Override
    public int getCount() {
        return country_infos.size();
    }

    @Override
    public Object getItem(int position) {
        return country_infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(ctx, R.layout.item_message_list, null);
            holder = new ViewHolder();
            holder.messageTitle = (TextView) convertView.findViewById(R.id.message_title_txt);
            holder.messageSource = (TextView) convertView.findViewById(R.id.txt_message_list_source);
            holder.messageTime = (TextView) convertView.findViewById(R.id.txt_message_list_time);
            holder.likeNum = (TextView) convertView.findViewById(R.id.txt_message_like_num);
            holder.commNum = (TextView) convertView.findViewById(R.id.txt_message_comm_num);
            holder.messageTitle = (TextView) convertView.findViewById(R.id.message_title_txt);
            holder.messageDelet = (ImageView) convertView.findViewById(R.id.message_delet);
            holder.likeItem = (ImageView) convertView.findViewById(R.id.message_item_like);
            holder.article_img_url_01 = (ImageView) convertView.findViewById(R.id.article_img_url_01);
            holder.article_img_url_02 = (ImageView) convertView.findViewById(R.id.article_img_url_02);
            holder.messageLike = (LinearLayout) convertView.findViewById(R.id.ll_message_like);
            holder.messageCommit = (LinearLayout) convertView.findViewById(R.id.ll_message_commont);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int intLiked = country_infos.get(position).getLiked();
        if (intLiked == 1) {
            holder.likeItem.setSelected(true);
        } else {
            holder.likeItem.setSelected(false);
        }
        holder.messageTitle.setText(country_infos.get(position).getArticle_title());
        holder.messageSource.setText(country_infos.get(position).getArticle_resource());
        //设置时间 时间戳返回的是double强转int
        int messageTime = (int) country_infos.get(position).getCreate_at();
        holder.messageTime.setText(DateUtil.getSessionTime(messageTime));
        holder.likeNum.setText(country_infos.get(position).getLikes() + "");
        holder.commNum.setText(country_infos.get(position).getComments() + "");
        OnClickable(holder.messageLike, position, holder.likeItem);
        OnClickable(holder.messageDelet, position, null);
        OnClickable(holder.messageCommit, position, null);
        String article_img_url_str = country_infos.get(position).getArticle_img_url();
        if (article_img_url_str != null) {
            if (!article_img_url_str.contains(",")) {
                Picasso.with(ctx).load(article_img_url_str).into(holder.article_img_url_01);
                holder.article_img_url_01.setVisibility(View.VISIBLE);
                holder.article_img_url_02.setVisibility(View.GONE);
            } else {
                String[] urlArrStr = article_img_url_str.split(",");
                Picasso.with(ctx).load(urlArrStr[0]).into(holder.article_img_url_01);
                Picasso.with(ctx).load(urlArrStr[1]).into(holder.article_img_url_02);
                holder.article_img_url_01.setVisibility(View.VISIBLE);
                holder.article_img_url_02.setVisibility(View.VISIBLE);
            }
        } else {
            holder.article_img_url_01.setVisibility(View.GONE);
            holder.article_img_url_02.setVisibility(View.GONE);
        }

        return convertView;
    }

    public static class ViewHolder {
        private TextView messageTitle;
        private TextView messageSource;
        private TextView messageTime;
        private TextView likeNum;
        private TextView commNum;
        private ImageView likeItem;
        private ImageView article_img_url_01;
        private ImageView article_img_url_02;
        private ImageView messageDelet;
        private LinearLayout messageLike;
        private LinearLayout messageCommit;
    }

}
