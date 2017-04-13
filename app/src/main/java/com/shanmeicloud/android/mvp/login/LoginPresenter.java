package com.shanmeicloud.android.mvp.login;

import com.shanmeicloud.android.canstant.Constant;
import com.shanmeicloud.android.mvp.home.LoginBean;
import com.shanmeicloud.android.mvp.home.MainContract;
import com.shanmeicloud.android.rxnet.FilterSubscriber;
import com.shanmeicloud.android.utils.LogUtil;
import com.shanmeicloud.android.utils.StormRoid;

import java.util.HashMap;

/**
 * Created by DELL on 2017/4/6.
 * 登陆
 */

public class LoginPresenter extends LoginContract.Presenter {


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void testPresenter() {

        mView.showLoading("调到other页面");

        HashMap<String, String> map = new HashMap<>();
        map.put("token", "XXXXXX");
        map.put("method", Constant.LOGIN_LOGIN);
        map.put("account", "zhangsan");
        map.put("passwd", "123456");


        StormRoid.instance()
                .get(map, LoginBean.class)
                .subscribe(new FilterSubscriber<LoginBean>() {
                    @Override
                    public void _onNext(LoginBean loginBean) {
                        LogUtil.e("成功啦《》" + loginBean.toString());
                        mView.testView();
                    }

                    @Override
                    public void _onError(String errorMsg) {
                        LogUtil.e("失败啦《》" + errorMsg);
                        mView.testView();
                    }
                });




    }
}
