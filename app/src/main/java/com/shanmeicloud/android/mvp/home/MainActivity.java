package com.shanmeicloud.android.mvp.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shanmeicloud.android.R;
import com.shanmeicloud.android.base.MvpActivity;
import com.shanmeicloud.android.canstant.ArouteConstant;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ArouteConstant.APP_MAIN)
public class MainActivity extends MvpActivity<MainPresenter, MainModel> implements MainContract.View {


    @BindView(R.id.tv_button)
    TextView tvButton;

    @Override
    public void initPresenter() {
        mPresenter.setViewAndModel(this, mModel);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViewAndData(Bundle savedInstanceState) {
    }

    @OnClick(R.id.tv_button)
    public void onClick() {
        mPresenter.testPresenter();//测试网络
//        testUM();//测试友盟分享调起
    }


    @Override
    public void testView() {
        stopLoading();


        ARouter.getInstance()
                .build(ArouteConstant.APP_OTHER)
                .navigation();
        finish();
    }

    private void testUM() {

        UMImage image = new UMImage(this, R.drawable.umeng_socialize_copy);
        UMWeb umWeb = new UMWeb("http://www.baidu.com");
        umWeb.setThumb(image);
        umWeb.setTitle("hello");
        umWeb.setDescription("test");
        new ShareAction(this).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener)
                .withMedia(umWeb)
                .share();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {


            showShortToast("分享成功啦");

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            showShortToast("分享失败啦");

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            showShortToast("分享取消了");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
