package com.shanmeicloud.android.api;

import com.shanmeicloud.android.canstant.Constant;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * author  xyj
 * createtime 2017/4/6 11:04
 * desc 此为统一接口
 */
@SuppressWarnings("unchecked")
public interface APIStores {


    @FormUrlEncoded
    @POST(Constant.UIP_API)
    Observable<ResponseBody> postRequest(@FieldMap Map<String, String> params);

    @GET(Constant.UIP_API)
    Observable<ResponseBody> getRequest(@QueryMap Map<String, String> params);

}
