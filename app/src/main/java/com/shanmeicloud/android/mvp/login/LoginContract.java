package com.shanmeicloud.android.mvp.login;

import com.shanmeicloud.android.base.BaseModel;
import com.shanmeicloud.android.base.BasePresenter;
import com.shanmeicloud.android.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by DELL on 2017/4/6.
 * 登陆
 */

public class LoginContract {
        interface Model extends BaseModel {
        Observable<List<String>> testModel();
    }

    interface View extends BaseView {
        void testView();
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void testPresenter();
    }
}
