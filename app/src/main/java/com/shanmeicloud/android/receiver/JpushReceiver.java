package com.shanmeicloud.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.shanmeicloud.android.utils.LogUtil;

/**
 * Created by DELL on 2017/4/13.
 * 极光推送
 */

public class JpushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.e("自定义接受JpushReceiver："+intent.getAction());
    }
}
