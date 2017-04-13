package com.shanmeicloud.android.mvp.other;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shanmeicloud.android.R;
import com.shanmeicloud.android.base.MvpActivity;
import com.shanmeicloud.android.canstant.ArouteConstant;
import com.shanmeicloud.android.canstant.Constant;
import com.shanmeicloud.android.mvp.home.LoginBean;
import com.shanmeicloud.android.utils.StormRoid;
import com.shanmeicloud.android.rxnet.FilterSubscriber;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ArouteConstant.APP_OTHER)
public class OtherActivity extends MvpActivity {


    @BindView(R.id.tv_other)
    TextView tvOther;

    @Override
    public void initPresenter() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_other;
    }

    @Override
    public void initViewAndData(Bundle savedInstanceState) {

    }


    @OnClick(R.id.tv_other)
    public void onClick() {
        startProgressDialog("跳回主页");
        HashMap<String, String> map = new HashMap<>();
        map.put("token", "XXXXXX");
        map.put("method", Constant.LOGIN_LOGIN);
        map.put("account", "zhangsan");
        map.put("passwd", "123456");
        StormRoid.instance()
                .post(map, LoginBean.class)
                .subscribe(new FilterSubscriber<LoginBean>() {
                    @Override
                    public void _onNext(LoginBean loginBean) {
                        OtherActivity.this.stopProgressDialog();
                        showShortToast("请求成功");
                        ARouter.getInstance()
                                .build(ArouteConstant.APP_MAIN)
                                .navigation();
                        OtherActivity.this.finish();
                    }

                    @Override
                    public void _onError(String errorMsg) {
                        OtherActivity.this.stopProgressDialog();
                        showShortToast("网络失败：" + errorMsg);
                    }
                });
    }
}
