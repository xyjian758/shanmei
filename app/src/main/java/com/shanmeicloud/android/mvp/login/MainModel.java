package com.shanmeicloud.android.mvp.login;

import com.shanmeicloud.android.base.CommonModel;

import java.util.List;

import rx.Observable;

/**
 * Created by DELL on 2017/4/6.
 */

public class MainModel extends CommonModel implements MainContract.Model {
    @Override
    public Observable<List<String>> testModel() {
        return null;
    }
}
