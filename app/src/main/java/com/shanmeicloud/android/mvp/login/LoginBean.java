package com.shanmeicloud.android.mvp.login;

import com.shanmeicloud.android.rxnet.BaseData;


/**
 * author  xyj
 * createtime 2017/4/13 13:12
 * desc 登陆
 */
public class LoginBean extends BaseData {
    public Data data;

    public class Data {
        public String account;
        public String avatar;
        public String balanceOfExpCurrency;
        public String createTime;
        public String email;
        public String id;
        public String nickName;
        public String sex;
    }

}
