package com.worldsay.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.worldsay.Constants;
import com.worldsay.R;
import com.worldsay.api.RestPool;
import com.worldsay.pojo.UserInfo;
import com.worldsay.ui.userinfo.DeleteLanguageMessage;
import com.worldsay.ui.userinfo.DeleteTeachMessage;
import com.worldsay.ui.userinfo.DeleteWorkMessage;
import com.worldsay.ui.userinfo.LanguageMessage;
import com.worldsay.ui.userinfo.TeachMessage;
import com.worldsay.ui.userinfo.WorkMessage;
import com.worldsay.util.ManagerUtil;
import com.worldsay.util.MimeType;
import com.worldsay.util.SharedPreferenceUtils;
import com.worldsay.util.T;
import com.worldsay.util.UIHelper;
import com.worldsay.widget.ActionSheetDialog;
import com.worldsay.widget.AlertDialog;
import com.worldsay.widget.ItemMessage;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by LXG on 2015/8/22.
 */
public class PersonalDataFragment extends Fragment implements View.OnClickListener {

    public String uploadImagePath;

    @InjectView(R.id.set_sex_img)
    ImageView setSexImg;
    @InjectView(R.id.avatar)
    ImageView avatar;
    @InjectView(R.id.nickname)
    TextView nickname;
    @InjectView(R.id.txt_user_sex)
    TextView txtUserSex;
    @InjectView(R.id.personal_et_name)
    EditText personalEtName;
    @InjectView(R.id.personal_et_email)
    EditText personalEtEmail;
    @InjectView(R.id.edit_nike_name)
    RelativeLayout editNikeName;
    @InjectView(R.id.edit_user_sex)
    RelativeLayout editUserSex;
    @InjectView(R.id.edit_user_email)
    RelativeLayout editUserEmail;
    @InjectView(R.id.edit_user_location)
    RelativeLayout editUserLocation;
    @InjectView(R.id.edit_user_birthday)
    RelativeLayout editUserBirthday;
    @InjectView(R.id.edit_add_work_message)
    RelativeLayout editAddWorkMessage;
    @InjectView(R.id.edit_add_teach_message)
    RelativeLayout editAddTeachMessage;
    @InjectView(R.id.edit_add_language_message)
    RelativeLayout editAddLanguageMessage;
    @InjectView(R.id.edit_add_visa_message)
    RelativeLayout editAddVisaMessage;
    @InjectView(R.id.txt_visa_boolean)
    TextView txtVisaBoolean;
    @InjectView(R.id.ll_edit_add_work_message)
    LinearLayout lleditAddWorkMessage;
    @InjectView(R.id.ll_edit_add_teach_message)
    LinearLayout lleditAddTeachMessage;
    @InjectView(R.id.ll_edit_add_language_message)
    LinearLayout lleditAddLanguageMessage;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                //工作经历的添加
                case 1:
                    addView(lleditAddWorkMessage, "公司", teach);
                    getActivity().unregisterReceiver(receiver);
                    break;
                //工作经历的删除
                case 2:
                    removeView(lleditAddWorkMessage, mItemMessage.getId());
                    getActivity().unregisterReceiver(receiver);
                    break;
                //教育经历的添加
                case 3:
                    addView(lleditAddTeachMessage, "學校", teach);
                    getActivity().unregisterReceiver(receiver);
                    break;

                case 4:
                    removeView(lleditAddTeachMessage, mItemMessage.getId());
                    getActivity().unregisterReceiver(receiver);
                    break;
                case 5:
                    addView(lleditAddLanguageMessage, "语言", teach);
                    getActivity().unregisterReceiver(receiver);
                    break;
                case 6:
                    removeView(lleditAddLanguageMessage, mItemMessage.getId());
                    getActivity().unregisterReceiver(receiver);
                    break;

                default:
                    break;
            }
        }

        ;
    };
    private Receiver receiver;
    private ItemMessage mItemMessage;
    private String teach;

    private void addView(final LinearLayout view, String nameKey, String nameValue) {
        mItemMessage = new ItemMessage(getActivity());
        TextView mnameKey = mItemMessage.getNameKey();
        TextView mnameValue = mItemMessage.getNameValue();
        mnameKey.setText(nameKey);
        mnameValue.setText(nameValue);
        mItemMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (view.getId()){
                    case R.id.ll_edit_add_work_message:
                        registerReceiver("dWorkMessage", 2);
                        v.setTag(v.getId());
                        UIHelper.openActivity(getActivity(), DeleteWorkMessage.class);
                        break;
                    case R.id.ll_edit_add_teach_message:
                        registerReceiver("dTeachMessage", 4);
                        v.setTag(v.getId());
                        UIHelper.openActivity(getActivity(), DeleteTeachMessage.class);
                        break;
                    case R.id.ll_edit_add_language_message:
                        registerReceiver("dLanguageMessage", 6);
                        v.setTag(v.getId());
                        UIHelper.openActivity(getActivity(), DeleteLanguageMessage.class);
                        break;

                }

            }
        });
        view.addView(mItemMessage, 0);
    }

    //移除item
    private void removeView(LinearLayout view, int tag) {
        View v = view.findViewWithTag(tag);
        view.removeView(v);
    }

    private void registerReceiver(String name, int msg) {
        receiver = new Receiver(msg);
        IntentFilter filter = new IntentFilter();
        filter.addAction(name);
        getActivity().registerReceiver(receiver, filter);
    }

    public class Receiver extends BroadcastReceiver {
        int i;
        public Receiver(int Mmsg) {
            super();
            i = Mmsg;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Message msg = Message.obtain();
            msg.what = i;
            teach = intent.getStringExtra("us");
            mHandler.sendMessage(msg);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_data, container, false);
        ButterKnife.inject(this, view);
        initResourceFile();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String userToken = SharedPreferenceUtils.getStringDate(getActivity(), Constants.USER_TOKEN, "");
        requestUserInfo(userToken);
    }

    private void requestUserInfo(String userToken) {
        RestPool.initBasic().importUserInfo(userToken, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String body = ManagerUtil.fromBody(response);
                UserInfo userInfo = new Gson().fromJson(body, UserInfo.class);
                if (!TextUtils.isEmpty(userInfo.getUsername())) {
                    nickname.setText(userInfo.getUsername());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                T.showShort(getActivity(), error.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.avatar: //昵称
                showHeadImgDialog();
                break;
            case R.id.edit_nike_name: //昵称
                personalEtName.setClickable(true);
                break;
            case R.id.edit_user_sex: //性别
                setSexSelect();
                break;
            case R.id.edit_user_email: //email
                personalEtEmail.setClickable(true);
                break;
            case R.id.edit_user_location: //地方
                break;
            case R.id.edit_user_birthday: //生日
                break;
            case R.id.edit_add_work_message: //工作经历
                registerReceiver("WorkMessage", 1);
                UIHelper.openActivity(getActivity(), WorkMessage.class);
                break;
            case R.id.edit_add_teach_message: //教育信息
                registerReceiver("teachMessage", 3);
                UIHelper.openActivity(getActivity(), TeachMessage.class);
                break;
            case R.id.edit_add_language_message: //语言
                registerReceiver("LanguageMessage", 5);
                UIHelper.openActivity(getActivity(), LanguageMessage.class);
                break;
            case R.id.edit_add_visa_message: //签证
                setVisaSelect();
                break;
        }
    }


    private void showHeadImgDialog() {
        new AlertDialog(getActivity()).builder().setTitle("更换头像")
//                .setMsg("再连续登陆15天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？")
                .setPositiveButton("相册", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickPhotoByAlbum();
                    }
                }).setNegativeButton("拍照", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoByCamera();
            }
        }).show();
    }

    private void initResourceFile() {
        avatar.setOnClickListener(this);
        setSexImg.setOnClickListener(this);
        editNikeName.setOnClickListener(this);
        editUserSex.setOnClickListener(this);
        editUserEmail.setOnClickListener(this);
        editUserLocation.setOnClickListener(this);
        editUserBirthday.setOnClickListener(this);
        editAddWorkMessage.setOnClickListener(this);
        editAddTeachMessage.setOnClickListener(this);
        editAddLanguageMessage.setOnClickListener(this);
        editAddVisaMessage.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * 设置性别Per
     */
    private void setSexSelect() {
        new ActionSheetDialog(getActivity())
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setTitle("性别修改")
                .addSheetItem("男", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                txtUserSex.setText("男");
                            }
                        })
                .addSheetItem("女", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                txtUserSex.setText("女");
                            }
                        })
                .show();
    }

    /**
     * 设置签证修改
     */
    private void setVisaSelect() {
        new ActionSheetDialog(getActivity())
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setTitle("签证修改")
                .addSheetItem("有", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                txtVisaBoolean.setText("有");
                            }
                        })
                .addSheetItem("无", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                txtVisaBoolean.setText("无");
                            }
                        })
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.TAKE_PICTURE:
                // 照相
                if (resultCode == Constants.RESULT_OK) {
                    if (!TextUtils.isEmpty(uploadImagePath)) {
                        startPhotoZoom(Uri.fromFile(new File(uploadImagePath)));
                    }
                }
                break;
            case Constants.LOAD_IMAGE:
                // 图库
                if (resultCode == Constants.RESULT_OK && data != null) {
                    Uri pickPhotoUri = data.getData();
                    if (pickPhotoUri != null) {
                        startPhotoZoom(pickPhotoUri);
                    }
                }
                break;
            case Constants.IMAGE_ZOOM:
                Uri imagePhotoUri = data.getData();
                uploadImagePath = getRealPathFromURI(imagePhotoUri);
                // 上传头像
                TypedFile typedFile = new TypedFile(MimeType.IMAGE_JPEG, new File(uploadImagePath));
                final String avatarPath = uploadImagePath;
                RestPool.initBasic().changeUserAvatar(typedFile, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        T.show(getActivity(), "更新成功", Toast.LENGTH_SHORT);
                        // 显示头像
                        Picasso.with(getActivity()).load(avatarPath).into(avatar);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        T.show(getActivity(), "更新失败", Toast.LENGTH_SHORT);
                    }
                });
                break;
        }
    }

    /**************************************具体实现**********************************************/
    /**
     * 拍照获取图片
     */
    protected void takePhotoByCamera() {
        try {
            Intent openCameraIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            String SdCardPathDir = Constants.SDCARD_CACHDIR;
            if (ManagerUtil.hasSdcard()) {
                // 有sd卡，是否有tempImage文件夹
                File fileDir = new File(SdCardPathDir);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                // 是否有Img文件
                uploadImagePath = SdCardPathDir + System.currentTimeMillis() + ".JPEG";
                Uri photoUri = Uri.fromFile(new File(uploadImagePath));
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                getActivity().startActivityForResult(openCameraIntent, Constants.TAKE_PICTURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从相册中选取图片
     */
    protected void pickPhotoByAlbum() {
        try {
            Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            getActivity().startActivityForResult(pickPhotoIntent, Constants.LOAD_IMAGE);
        } catch (RuntimeException exception) {
            // 手机没有安装图库应用
            Toast.makeText(getActivity(), "手机未安装相册应用", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 图片剪裁
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, MimeType.IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 512);
        intent.putExtra("outputY", 512);
        intent.putExtra("return-data", false);
        String imagePath = Constants.SDCARD_CACHDIR + System.currentTimeMillis() + ".JPEG";
        File fileDir = new File(Constants.SDCARD_CACHDIR);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File cropFile = new File(imagePath);
        if (cropFile.exists()) {
            cropFile.delete();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropFile));
        getActivity().startActivityForResult(intent, Constants.IMAGE_ZOOM);
    }

    public String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
