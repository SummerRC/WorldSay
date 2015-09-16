package com.worldsay.ui.article;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.worldsay.Base.BaseActivity;
import com.worldsay.Constants;
import com.worldsay.R;
import com.worldsay.adpter.CommonAdapter;
import com.worldsay.adpter.ViewHolder;
import com.worldsay.api.RestPool;
import com.worldsay.pojo.Comment;
import com.worldsay.pojo.CommentList;
import com.worldsay.pojo.IsSuccessFul;
import com.worldsay.ui.login.LoginActivity;
import com.worldsay.util.DateUtil;
import com.worldsay.util.ManagerUtil;
import com.worldsay.util.SharedPreferenceUtils;
import com.worldsay.util.T;
import com.worldsay.widget.ActionSheetDialog;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by LXG on 2015/8/19.
 */
public class MessageCommont extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.commont_list)
    ListView commontList;
    @InjectView(R.id.tv_comment_num)
    TextView tvCommentNum;
    @InjectView(R.id.tv_comment_send)
    TextView tvCommentSend;
    @InjectView(R.id.edit_comment_content)
    EditText editCommentContent;

    private List<CommentList.CommentEntity> comments = new ArrayList<>();
    private View curView;
    private int article_id;
    private CommonAdapter adapter;
    private int userId;
    private String token;
    private boolean islogining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curView = View.inflate(MessageCommont.this, R.layout.activity_message_commot, contentView);
        initRes();
        requestServiceCommentList();
        initListView();
    }

    private void initListView() {
        adapter = new CommonAdapter<CommentList.CommentEntity>(MessageCommont.this, comments, R.layout.item_commont_list) {
            @Override
            public void convert(ViewHolder helper, final CommentList.CommentEntity item) {
                /**
                 * 填充item
                 */
                int commentTime = (int) item.getCreate_at();
                helper.setText(R.id.commont_content_txt, item.getComment_content());
                helper.setText(R.id.tv_comment_username, item.getComment_username());
                helper.setText(R.id.tv_comm_likes, item.getLikes() + "");
                helper.setText(R.id.tv_comment_time, DateUtil.getTime(commentTime));
                ImageView head_url = helper.getView(R.id.iv_comment_user_headurl);
                Picasso.with(MessageCommont.this).load(item.getComment_headimg_url()).into(head_url);
                //弹出底部菜单
                helper.getView(R.id.commont_setting).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(MessageCommont.this)
                                .builder()
                                .setCancelable(false)
                                .setCanceledOnTouchOutside(false);
                        actionSheetDialog.addSheetItem("回复", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                    }
                                })
                                .addSheetItem("删除", ActionSheetDialog.SheetItemColor.Blue,
                                        new ActionSheetDialog.OnSheetItemClickListener() {
                                            @Override
                                            public void onClick(int which) {
                                               int commentId = item.getComment_id();
                                                delectComment(commentId);
                                            }
                                        })
                                .addSheetItem("举报", ActionSheetDialog.SheetItemColor.Blue,
                                        new ActionSheetDialog.OnSheetItemClickListener() {
                                            @Override
                                            public void onClick(int which) {
                                                Intent intent = new Intent(MessageCommont.this, ReportComment.class);
                                                intent.putExtra(Constants.ARTICLE_ID, article_id);
                                                intent.putExtra(Constants.COMMENT_ID, item.getComment_id());
                                                startActivity(intent);
                                            }
                                        })
                                .show();
                    }
                });


            }
        };
        commontList.setAdapter(adapter);
    }

    private void delectComment(int commentId) {
        RestPool.initBasic().deleteInfoComment(token, commentId, new Callback<IsSuccessFul>() {
            @Override
            public void success(IsSuccessFul isSuccessFul, Response response) {
                if (isSuccessFul.is_successful()){
                    T.showShort(getApplicationContext(),"删除成功");
                    requestServiceCommentList();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    private void requestServiceCommentList() {
        RestPool.init().commentInfoList(article_id, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                comments.clear();
                String body = ManagerUtil.fromBody(response);
                CommentList comment = new Gson().fromJson(body, CommentList.class);
                List<CommentList.CommentEntity> commentEntityLists = comment.getComments();
                tvCommentNum.setText("全部评论(" + comment.getComments().size() + ")");
                if (commentEntityLists != null && commentEntityLists.size() >= 0) {
                    comments.addAll(commentEntityLists);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                T.showShort(MessageCommont.this, error.getMessage());
            }
        });
    }

    private void initRes() {
        ButterKnife.inject(this, curView);
        setTitle("评论");
        topRightBtn.setVisibility(View.GONE);
        article_id = getIntent().getIntExtra(Constants.ARTICLE_ID, 0);
        token = getIntent().getStringExtra(Constants.USER_TOKEN);
        userId = getIntent().getIntExtra(Constants.USER_ID, 0);
        tvCommentSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_comment_send:
                islogining = SharedPreferenceUtils.getBooleanDate(MessageCommont.this, Constants.ISLOGINING, false);
                String commentContentStr = editCommentContent.getText().toString().trim();
                if (islogining) {
                    if (TextUtils.isEmpty(commentContentStr)) {
                        T.showShort(MessageCommont.this, "评论不能为空");
                        return;
                    }
                    userSendcomment(commentContentStr);
                } else {
                    Intent intent = new Intent(MessageCommont.this, LoginActivity.class);
                    intent.putExtra(Constants.LOGIN_STATUS, 1);
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 发送评论
     */
    private void userSendcomment(String contentStr) {
        RestPool.initBasic().sendInfoComment(token, userId, article_id, contentStr, new Callback<Comment>() {
            @Override
            public void success(Comment comment, Response response) {
                if (comment.getComment_id() >= 0) {
                    T.showShort(getApplicationContext(), "评论成功");
                    editCommentContent.setText("");
                    requestServiceCommentList();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
