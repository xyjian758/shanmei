package com.shanmeicloud.android.mvp.login;

import com.shanmeicloud.android.base.CommonModel;
import com.shanmeicloud.android.mvp.home.MainContract;

import java.util.List;

import rx.Observable;

/**
 * Created by DELL on 2017/4/6.
 */

public class LoginModel extends CommonModel implements LoginContract.Model {
    @Override
    public Observable<List<String>> testModel() {
        return null;
    }
}
