package com.shanmeicloud.android.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * author  xyj
 * createtime 2017/4/6 9:24
 * desc 当前应用的application，完成对应用的初始化
 */
public class AppApplication extends Application {
    private static AppApplication appApplication;
    private static RefWatcher refWatcher;

    {
        //友盟配置各平台信息
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化应用所需的参数
        appApplication = this;

        initData();
    }

    private void initData() {
        initArouter();

        initLeakCanary();

        initJPush();

        initEMClient();

        initUM();

    }

    private void initUM() {
        UMShareAPI.get(this);
    }

    //初始化环信
    private void initEMClient() {

    }

    private void initJPush() {
        //初始化极光
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private void initLeakCanary() {
        //内存泄露检测工具
        refWatcher = LeakCanary.install(this);
    }

    private void initArouter() {
        //初始化 阿里的arouter 跳转框架
        ARouter.init(this);
    }

    public static RefWatcher getRefWatcher() {
        if (null == refWatcher)
            refWatcher = LeakCanary.install(appApplication);
        return refWatcher;
    }

    public static AppApplication getAppContext() {
        return appApplication;
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
