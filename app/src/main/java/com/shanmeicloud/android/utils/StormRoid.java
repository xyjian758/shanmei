package com.shanmeicloud.android.utils;


import android.util.Log;

import com.google.gson.Gson;
import com.shanmeicloud.android.BuildConfig;
import com.shanmeicloud.android.api.APIStores;
import com.shanmeicloud.android.canstant.Constant;
import com.shanmeicloud.android.rxnet.BaseData;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@SuppressWarnings("unckecked")
/**
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                     佛祖保佑        永无BUG
 *            佛曰:
 *                   写字楼里写字间，写字间里程序员；
 *                   程序人员写程序，又拿程序换酒钱。
 *                   酒醒只在网上坐，酒醉还来网下眠；
 *                   酒醉酒醒日复日，网上网下年复年。
 *                   但愿老死电脑间，不愿鞠躬老板前；
 *                   奔驰宝马贵者趣，公交自行程序员。
 *                   别人笑我忒疯癫，我笑自己命太贱；
 *                   不见满街漂亮妹，哪个归得程序员？
 */


/**
 * author  xyj
 * createtime 2017/4/6 10:39
 * desc 改名为StormRoid 取Storm(暴风雨)(Android后四位) 意为流式处理，Stream更契合为流 但与java8的Stream冲突 故取名为StormRoid
 *
 * 网络请求框架
 *
 * 两种使用方法：推荐封装好的方法一
 *
 *  *
 * 方法一：
 * //        StormRoid.instance()
 //                .get(map,LoginBean.class)//或者post请求
 //                .subscribe(new FilterSubscriber<LoginBean>() {
 //                    @Override
 //                    public void _onNext(LoginBean loginBean) {
 //                        LogUtil.e("成功啦《》"+loginBean.toString());
 //                    }
 //
 //                    @Override
 //                    public void _onError(String errorMsg) {
 //                        LogUtil.e("失败啦《》"+errorMsg);
 //                    }
 //                });
 *
 *
 *
 * 方法二：
 *
 * //        StormRoid.instance().baseUrl(Constant.baseUrl)
 //                .create(APIStores.class)
 //                .postRequest(map)
 //                .subscribeOn(Schedulers.io())
 //                .observeOn(AndroidSchedulers.mainThread())
 //                .subscribe(new Subscriber<ResponseBody>() {
 //                    @Override
 //                    public void onCompleted() {
 //
 //                    }
 //
 //                    @Override
 //                    public void onError(Throwable e) {
 //
 //                    }
 //
 //                    @Override
 //                    public void onNext(ResponseBody responseBody) {
 //
 //                    }
 //                });
 *

 *
 */
public class StormRoid {
    private static StormRoid sSinglton;//当前单例

    private Retrofit.Builder mBuilder;//retrofit builder

    private OkHttpClient.Builder mClientBuilder;//okhttp builder

    private APIStores apiStores;//ApiStroe 对象

    private Gson gson;//Gson 对象

    //私有化构造
    private StormRoid() {
    }

    private OkHttpClient mClient;

    public <T> T create(Class<T> clazz) {

        return initRetrofit().create(clazz);
    }


    public StormRoid baseUrl(String url) {


        mBuilder = new Retrofit.Builder()
                .baseUrl(url)
                .client(getClient());


        return this;
    }

    public APIStores easyApply() {

        if (apiStores == null) {
            synchronized (StormRoid.class) {
                if (apiStores == null) {
                    apiStores = new Retrofit.Builder()
                            .baseUrl(Constant.baseUrl)
                            .client(getClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build()
                            .create(APIStores.class);
                }
            }
        }

        return apiStores;
    }


    /**
     * author  xyj
     * createtime 2017/4/13 11:43
     * desc get请求
     *
     * @param clazz  javabean转换类型
     * @param params 具体参数
     */
    public <T extends BaseData> Observable<T> get(Map params, final Class<T> clazz) {
        return easyApply()
                .getRequest(params)
                .map(new Func1<ResponseBody, T>() {

                    @Override
                    public T call(ResponseBody responseBody) {
                        T t = null;
                        try {
                            String body = responseBody.string();
                            t = getGson().fromJson(body, clazz);
                        } catch (Exception e) {
                            new Exception(e.getMessage());
                        } finally {
                            if (null != responseBody) {
                                responseBody.close();
                            }
                        }

                        return t;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * author  xyj
     * createtime 2017/4/13 11:44
     * desc post 请求
     *
     * @param clazz  javabean转换类型
     * @param params 具体参数
     */
    public <T extends BaseData> Observable<T> post(Map params, final Class<T> clazz) {
        return easyApply()
                .postRequest(params)
                .map(new Func1<ResponseBody, T>() {

                    @Override
                    public T call(ResponseBody responseBody) {
                        T t = null;
                        try {
                            String body = responseBody.string();
                            t = getGson().fromJson(body, clazz);
                        } catch (Exception e) {
                            new Exception(e.getMessage());
                        } finally {
                            if (null != responseBody) {
                                responseBody.close();
                            }
                        }

                        return t;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * @return StormRoid 当前对象实例，方法一（可选url路径和apiServer）：调用baseUrl（）和create（）方法
     * 方法二（默认url路径和apiServer）：直接调用get 或post 方法
     */
    public static StormRoid instance() {

        if (sSinglton == null) {
            synchronized (StormRoid.class) {
                if (sSinglton == null) {
                    sSinglton = new StormRoid();
                }
            }
        }
        return sSinglton;
    }

    /**
     * 实例化Retrofit
     *
     * @return
     */
    private Retrofit initRetrofit() {
        return mBuilder
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    private Gson getGson() {
        if (null == gson) {
            gson = new Gson();
        }
        return gson;
    }

    /**
     * 获取Log日志拦截器
     *
     * @return
     */
    protected HttpLoggingInterceptor getHttpLogginInterceptor() {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("HttpLoggingInterceptor", "message: " + message + "\n");
            }
        });


        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return interceptor;
    }


    protected class SampleInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            Response reponse = chain.proceed(request);
            Log.e("SampleInterceptor", "本次请求数据："
                    + "\n" + "Request："
                    + "\n" + "请求头：" + request.headers()
                    + "\n" + "请求方式：" + request.method()
                    + "\n" + "请求体：" + JsonUtils.toJson(new Gson().toJson(request.body()).toString())
                    + "\n" + "请求地址：" + request.url()
                    + "\n" + "请求缓存控制：" + request.cacheControl()
                    + "\n" + "请求方式是否Https:" + request.isHttps()
                    + "\r\r"
                    + "\n" + mPoem
                    + "\r\r"
                    + "\n" + "Reponse:"
                    + "\n" + "响应头：" + "\n" + "[" + "\n" + reponse.headers() + "]"
                    + "\n" + "响应协议：" + reponse.protocol()
                    + "\n" + "响应消息：" + reponse.message()
                    + "\n" + "响应握手信息：" + reponse.handshake()
                    + "\n" + "响应缓存控制：" + reponse.cacheControl()
                    + "\n" + "响应体：" + new Gson().toJson(reponse.body())
                    + "\n" + "响应接收耗时?：" + reponse.receivedResponseAtMillis() / 1000 / 1000 / 1000 + "秒"
                    + "\n" + "响应发送耗时?：" + reponse.sentRequestAtMillis() / 1000 / 1000 / 1000 + "秒"
                    + "\n\n"
            );

            return reponse;
        }
    }


    protected OkHttpClient getClient() {

        if (mClient == null) {
            mClientBuilder = new OkHttpClient.Builder();

            if (BuildConfig.DEBUG) {
                mClientBuilder
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();
                                Response proceed = chain.proceed(request);

                                Log.e("Interceptor", "\n" + request.toString() + "\n\n" + proceed.toString());
                                return proceed;
                            }
                        })
                        .addInterceptor(getHttpLogginInterceptor());
            }

            mClient = mClientBuilder.build();
        }


        return mClient;
    }


    private String mPoem = "                        江城子 . IT汪之歌"
            + "\n"
            + "\n" + "                  十年生死两茫茫，写程序，到天亮。"
            + "\n" + "                      千行代码，Bug何处藏。"
            + "\n" + "                  纵使上线又怎样，朝令改，夕断肠。"
            + "\n" + "                  领导每天新想法，天天改，日日忙。"
            + "\n" + "                      相顾无言，惟有泪千行。"
            + "\n" + "                  每晚灯火阑珊处，夜难寐，加班狂。";


}
