package com.shanmeicloud.android.mvp.login;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shanmeicloud.android.R;
import com.shanmeicloud.android.base.MvpActivity;
import com.shanmeicloud.android.canstant.ArouteConstant;

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
        mPresenter.testPresenter();
    }

    @Override
    public void showLoading(String title) {
        startProgressDialog(title);
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {
        showShortToast(msg);
    }

    @Override
    public void testView() {
        stopLoading();


        ARouter.getInstance()
                .build(ArouteConstant.APP_OTHER)
                .navigation();
        finish();
    }
}
