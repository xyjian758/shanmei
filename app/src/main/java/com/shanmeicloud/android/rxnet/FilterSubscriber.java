package com.shanmeicloud.android.rxnet;

import rx.Subscriber;

/**
 * @作者 xyj
 * @创建时间 2016/12/8 10:57
 * @描述 针对的数据模型是 BaseFilterBean 的
 */

public abstract class FilterSubscriber<T extends BaseData> extends Subscriber<T> {

    @Override
    public void onNext(T t) {
        if (null == t) {
            _onError("bean 为null");
            return;
        }
        if (0 == t.result) {
            _onNext(t);
        } else {
            _onError(t.result + ":" + t.detail);
        }
    }

    @Override
    public void onError(Throwable e) {
        _onError("网络失败");
    }

    @Override
    public void onCompleted() {

    }


    public abstract void _onNext(T t);

    public abstract void _onError(String errorMsg);
}
