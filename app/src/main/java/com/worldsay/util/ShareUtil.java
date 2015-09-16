package com.worldsay.util;


import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;


/**
 * Created by Administrator on 2015/6/3.
 */
public class ShareUtil {
    public Context mContext;
    /**************************************************************/
    // 整个平台的Controller,负责管理整个SDK的配置、操作等处理
    public UMSocialService mController;
    public ShareUtil(Context mContext) {
        this.mContext = mContext;
    }

    /**
     *  sdk 实例化
     *  分享的内容
     * @param ShareContentStr
     * 分享的url
     * @param ShareUrlStr
     * 分享的标题
     * @param ShareTitleStr
     */
    public void addCustomPlatforms(String ShareContentStr,
                                   String ShareUrlStr,String ShareTitleStr,String ShareImgUrl){
        // 整个平台的Controller,负责管理整个SDK的配置、操作等处理
         UMSocialService mController = UMServiceFactory
                .getUMSocialService("com.umeng");
        configPlatforms(mController);
        setShareContent(mController,ShareTitleStr,ShareContentStr,ShareImgUrl,ShareUrlStr);
    }

    /****
     * 实例化
     * @param mController
     * 标题
     * @param ShareTitleStr
     * 内容
     * @param ShareContentStr
     * 图片url
     * @param ShareImgUrl
     */
    private void setShareContent(UMSocialService mController,String ShareTitleStr,
                                 String ShareContentStr,String ShareImgUrl,String ShareUrlStr) {
        /**
         * 设置分享的图片
         */
        UMImage urlImage = new UMImage(mContext,ShareImgUrl);

        //分享微信
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(ShareContentStr);
        weixinContent.setTitle(ShareTitleStr);
        weixinContent.setTargetUrl(ShareUrlStr);
        weixinContent.setShareMedia(urlImage); //设置友盟分享url
        mController.setShareMedia(weixinContent);

        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(ShareContentStr);
        circleMedia.setTitle(ShareTitleStr);
        circleMedia.setTargetUrl(ShareUrlStr);
        circleMedia.setShareImage(urlImage);
        mController.setShareMedia(circleMedia);

        //新浪分享的内容
        SinaShareContent sinaContent = new SinaShareContent();
        sinaContent.setShareContent(ShareContentStr);
        sinaContent.setTitle(ShareTitleStr);
        circleMedia.setTargetUrl(ShareUrlStr);
        circleMedia.setShareImage(urlImage);
        mController.setShareMedia(sinaContent);

    }

    /**
     * 配置分享平台参数
     */
    private void configPlatforms(UMSocialService mController) {
        // 添加新浪sso授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        // 添加QQ、QZone平台
        /**
         * 添加所有的平台</br>
         */
//         添加微信平台
        addWXPlatform();

        mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA
        );
        mController.openShare((Activity) mContext, false);

    }


    /**
     * @功能描述 : 添加微信平台分享
     * @return
     */
    public void addWXPlatform() {
        // 注意：在微信授权的时候，必须传递appSecret
        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appId = "wx7c628069dcb97857";
        String appSecret ="66e16eb2acf92974c55898fd38c959f9";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(mContext, appId,
                appSecret);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(mContext,
                appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }


}
