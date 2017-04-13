package com.shanmeicloud.android.mvp.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shanmeicloud.android.R;
import com.shanmeicloud.android.base.MvpActivity;

public class LoginActivity extends MvpActivity<LoginPresenter, LoginModel> implements LoginContract.View {

    @Override
    public void initPresenter() {
        mPresenter.setViewAndModel(this, mModel);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViewAndData(Bundle savedInstanceState) {

    }


    @Override
    public void testView() {

    }
}
