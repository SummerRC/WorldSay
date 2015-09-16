package com.worldsay.api;

import com.worldsay.pojo.Comment;
import com.worldsay.pojo.IsSuccessFul;
import com.worldsay.pojo.User;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by gekson on 2015/8/26.
 */
public interface WordSayService {

    /*登录注册模块*/
    // 注册
    @POST("/api/signup")
    @FormUrlEncoded
    void userSignUp(@Field("username") String username, @Field("password") String password, @Field("email") String email, Callback<User> callback);

    // 登录
    @GET("/api/signin")
    void userSignIn(@Query("email") String email, @Query("password") String password, Callback<Response> response);

    // 重置密码
    @POST("/api/user/reset_password")
    @FormUrlEncoded
    void resetPassWord(@Field("old_password") String old_password, @Field("new_password") String new_password, Callback<IsSuccessFul> callback);

    /*资讯模块*/
    // 热门国家列表
    @GET("/api/info/hot_countries")
    void hotCountryList(Callback<Response> callback);

    // 资讯未登陆主页列表
    @GET("/api/info/info_list")
    void msgHomeInfoList(@Query("country_id") int countryId, Callback<Response> callback);

    // 资讯登陆主页列表
    @GET("/api/info/info_list_auth")
    void msgHomeInfoIsLoginList(@Query("country_id") int countryId,@Query("last_id") int lastId ,Callback<Response> callback);
    // 资讯登陆主页列表

    @GET("/api/info/info_list_auth")
    void msgHomeInfoIsLogin(@Query("country_id") int countryId,Callback<Response> callback);

    // 特定资讯
    @GET("/api/info/article")
    void articleMessage(@Query("article_id") int articleId, Callback<Response> callback);

    // 赞
    @GET("/api/info/like")
    void likeInfo(@Query("token") String token, @Query("article_id") int articleId, Callback<IsSuccessFul> callback);

    // 取消赞
    @GET("/api/info/cancel_like")
    void cancleLikeInfo(@Query("token") String token, @Query("article_id") int articleId, @Query("uid") int uid, Callback<IsSuccessFul> callback);

    // 不喜欢
    @GET("/api/info/dislike")
    void dislikeInfo(@Query("token") String token, @Query("article_id") int articleId, Callback<IsSuccessFul> callback);

    // 评论列表
    @GET("/api/info/comment_list")
    void commentInfoList(@Query("article_id") int articleId, Callback<Response> callback);

    // 发送资讯评论
    @POST("/api/info/comment")
    @FormUrlEncoded
    void sendInfoComment(@Field("token") String token, @Field("uid") int uid, @Field("article_id") int articleId, @Field("content") String content, Callback<Comment> callback);

    // 删除评论
    @GET("/api/info/delete_comment")
    void deleteInfoComment(@Query("token") String token, @Query("comment_id") int commentId, Callback<IsSuccessFul> callback);

    // 赞评论
    @GET("/api/info/like_comment")
    void likeInfoComment(@Query("token") String token, @Query("comment_id") int commentId, Callback<String> callback);

    // 取消对评论点赞
    @GET("/api/info/cancle_like_comment")
    void cancleInfoCommentLike(@Query("token") String token, @Query("comment_id") int commentId, Callback<String> callback);

    // 举报
    @POST("/api/info/report")
    @FormUrlEncoded
    void reportInfoComment(@Field("token") String token,
                           @Field("article_id") int article_id,
                           @Field("comment_id") int comment_id,
                           @Field("report_type") int report_type,
                           Callback<IsSuccessFul> callback);

    /*探索模块*/
    // 探索主页列表
    @GET("/api/opt/opt_list")
    void searchHomeList(@Query("country_id") int countryId, @Query("sort_type") int sort_type, @Query("type") int type, Callback<Response> response);

    // 特定探索信息
    @GET("/api/opt/opt_info")
    void searchDetailInfo(@Query("token") String token, @Query("opt_id") int optId, Callback<Comment> callback);

    // 探索添加申请
    @GET("/api/opt/apply")
    void searchAddApply(@Query("token") String token, @Query("opt_id") int optId, Callback<String> callback);

    // 探索收藏
    @GET("/api/opt/collect")
    void searchCollect(@Query("token") String token, @Query("opt_id") int optId, Callback<String> callback);

    // 取消收藏
    @GET("/api/opt/cancel_collect")
    void cancelCollect(@Query("token") String token, @Query("opt_id") int optId, Callback<String> callback);

    // 探索评论列表
    @GET("/api/opt/comment_list")
    void searchCommentList(@Query("token") String token, @Query("opt_id") int optId, Callback<String> callback);

    // 发送评论
    @POST("/api/opt/comment")
    void sendExploreComment(@Body RequestMap map, Callback<String> callback);

    // 删除评论
    @GET("/api/opt/delete_comment")
    void deleteExploreComment(@Query("token") String token, @Query("comment_id") int commentId, Callback<String> callback);

    // 赞评论
    @GET("/api/opt/like_comment")
    void likeExploreComment(@Query("token") String token, @Query("comment_id") int commentId, Callback<String> callback);

    // 取消对评论点赞
    @GET("/api/opt/cancel_like_comment")
    void cancelLikeExploreComment(@Query("token") String token, @Query("comment_id") int commentId, Callback<String> callback);

    // 举报评论
    @POST("/api/opt/report")
    void reportExploreComment(@Body RequestMap map, Callback<String> callback);

    /*侧拉菜单模块*/
    // 更换头像
    @Multipart
    @POST("/api/user/uploadimg")
    void changeUserAvatar(@Part("file") TypedFile avatar, Callback<Response> response);

    // 修改工作信息
    @POST("/api/user/info")
    @FormUrlEncoded
    void updateUserInfo(@Field("token") String token, @Field("uid") int uid, @Field("work") String work, Callback<IsSuccessFul> callback);

    // 修改用户信息
    @POST("/api/user/info")
    void updateUserWorkInfo(@Body RequestMap map, Callback<String> callback);

    //回复我的评论
    @GET("/api/user/reply_list")
    void replyUserComment(@Query("token") String token, @Query("uid") int uid, Callback<String> callback);

    // 我发出的评论
    @GET("/api/user/my_comment")
    void userSendedComment(@Query("token") String token, Callback<String> callback);

    // 更新探索申请菜单
    @POST("/api/opt/update_apply")
    void updateExploreMenu(@Body RequestMap map, Callback<String> callback);

    // 删除探索清单
    @GET("/api/opt/delete_apply")
    void deleteExploreList(@Query("token") String token, @Query("opt_id") int optId, Callback<String> callback);

    // 取消收藏
    @GET("/api/opt/cancel_collect")
    void canceExplorelCollect(@Query("token") String token, @Query("opt_id") int optId, Callback<String> callback);

    // 导入收藏列表
    @GET("/api/opt/collect_list")
    void importCollectList(@Query("token") String token, @Query("opt_id") int optId, Callback<String> callback);

    // 导入申请清单列表
    @GET("/api/opt/apply_list")
    void importApplyList(@Query("token") String token, @Query("opt_id") int optId, Callback<String> callback);

    // 导入个人信息
    @GET("/api/user/info")
    void importUserInfo(@Query("token") String token, Callback<Response> callback);
}
