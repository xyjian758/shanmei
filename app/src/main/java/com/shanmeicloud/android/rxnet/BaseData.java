package com.shanmeicloud.android.rxnet;

/**
 * @作者 xyj
 * @创建时间 2017/4/13 10:47
 * @描述 返回数据模型是这个样式的  2017/4/13 具体的数据模型集成此类
 */
//示例：
//      {
//        "data": {}
//        "result": 0
//        "detail": "ok"
//        }
public class BaseData {
    public int result;//请求返回状态  0：成功
    public String detail;//请求返回状态描述  example：ok ；验证码无效...

}
