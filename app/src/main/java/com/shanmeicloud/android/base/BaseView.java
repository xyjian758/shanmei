package com.shanmeicloud.android.base;

/**
 * des:baseview
 * Created by xsf
 * on 2016.07.11:53
 */
/**
 * author  xyj
 * createtime 2017/4/13 17:08
 * desc baseview  三个抽象方法已经在BaseActivity中实现
 */
public interface BaseView {
    /*******内嵌加载*******/
    void showLoading(String title);
    void stopLoading();
    void showErrorTip(String msg);
}
