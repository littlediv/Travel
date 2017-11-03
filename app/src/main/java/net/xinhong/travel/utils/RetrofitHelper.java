//package net.xinhong.travel.utils;
//
//import android.content.Context;
//
//import java.io.IOException;
//import java.nio.charset.Charset;
//
//import okhttp3.CacheControl;
//import okhttp3.Interceptor;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import okio.BufferedSource;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
///**
// * Created by mac on 2017/2/4.
// */
//public class RetrofitHelper {
//    private static ServerApi api;
//    private static OkHttpClient mOkHttpClient;
//    private static Context mContext;
//    private static boolean showError = true;
//
//    /**
//     * 启动后初始化
//     */
//    public static void init(Context context) {
//        mContext = context;
//        initOkHttpClient();
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlConstant.BASEURL).
//                addConverterFactory(GsonConverterFactory.create()).
//                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
//                client(mOkHttpClient).
//                build();
//        api = retrofit.create(ServerApi.class);
//    }
//
//    /**
//     * 重置baseUrl
//     */
//    public static void resetBaseUrl() {
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlConstant.BASEURL).
//                addConverterFactory(TWGsonConverterFactory.create()).
//                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
//                client(mOkHttpClient).
//                build();
//        api = retrofit.create(ServerApi.class);
//    }
//
//
//    public static ServerApi getApi() {
//        return api;
//    }
//
//    /**
//     * 统一处理原始数据
//     *
//     * @param originalResponse
//     */
//    private static Response dealResponseData(Response originalResponse) {
//        String jsonString = null;
//        try {
//            BufferedSource bufferedSource = originalResponse.body().source();
//            jsonString = bufferedSource.readString(Charset.forName("utf-8"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (!jsonString.trim().startsWith("{") && !jsonString.trim().startsWith("[")) {
//            return onSuccess(originalResponse, jsonString);
//        }
//        ResponseMessageBean msgBean = ResponseMessageBean.analyseReponse(jsonString);
//        if (msgBean == null) return onSuccess(originalResponse, msgBean.data.toString());
//        if (msgBean != null && (msgBean.errorCode == 200)) {
//            showError = true;
//            if (msgBean.data != null) {
//                return onSuccess(originalResponse, msgBean.data.toString());
//            } else {
//                return originalResponse.newBuilder().body(null).build();
//            }
//        } else {
//            onFailed(msgBean);
//            throw new RuntimeException(msgBean.moreInfo.toString());
//        }
//    }
//
//    /**
//     * 初始化okHttp
//     */
//    private static void initOkHttpClient() {
//        if (mOkHttpClient == null) {
//            synchronized (RetrofitHelper.class) {
//                Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request();
//                        if (!NetworkUtils.isNetworkConnected(mContext)) {
//                            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
//                        }
//                        /**
//                         * 统一设置请求头
//                         */
//                        Request newRequest = createRequestHeader(request.newBuilder()).build();
//                        Response originalResponse = chain.proceed(newRequest);
//                        //如果是重定向，那么就执行重定向后返回数据。
//                        if (originalResponse.isRedirect()) {
//                            Request redirectRequest = request.newBuilder().url(originalResponse.header("location")).build();
//                            originalResponse = chain.proceed(redirectRequest);
//                        }
//                        originalResponse = dealResponseData(originalResponse);
//                        return originalResponse;
//                    }
//                };
//
//                if (mOkHttpClient == null) {
//                    try {
//                        X509TrustManager xtm = new X509TrustManager() {
//                            @Override
//                            public void checkClientTrusted(X509Certificate[] chain, String authType) {
//                            }
//
//                            @Override
//                            public void checkServerTrusted(X509Certificate[] chain, String authType) {
//                            }
//
//                            @Override
//                            public X509Certificate[] getAcceptedIssuers() {
//                                X509Certificate[] x509Certificates = new X509Certificate[0];
//                                return x509Certificates;
//                            }
//                        };
//
//                        SSLContext sslContext = null;
//                        try {
//                            sslContext = SSLContext.getInstance("SSL");
//
//                            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
//
//                        } catch (NoSuchAlgorithmException e) {
//                            e.printStackTrace();
//                        } catch (KeyManagementException e) {
//                            e.printStackTrace();
//                        }
//                        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
//                            @Override
//                            public boolean verify(String hostname, SSLSession session) {
//                                return true;
//                            }
//                        };
//
//                        // 指定缓存路径,缓存大小100Mb
//                        Cache cache = new Cache(new File(mContext.getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
//                        mOkHttpClient = new OkHttpClient.Builder().
//                                addInterceptor(mRewriteCacheControlInterceptor).
//                                retryOnConnectionFailure(false).
//                                connectTimeout(30, TimeUnit.SECONDS).
//                                sslSocketFactory(sslContext.getSocketFactory()).
//                                hostnameVerifier(DO_NOT_VERIFY).
//                                cache(cache).
//                                build();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//
//
//    private static Response onSuccess(Response originalResponse, String content) {
//        return originalResponse.newBuilder().
//                body(ResponseBody.create(null, content)).
//                build();
//    }
//
//
//    /**
//     * errorCode 不为200
//     *
//     * @param msgBean
//     */
//    private static void onFailed(ResponseMessageBean msgBean) {
//        String alert = "";
//        if (msgBean == null) {
//            return;
//        }
//        if (msgBean.errorCode != 200) {
//            if (msgBean.errorCode == 401) {
//                Observable.create(new Observable.OnSubscribe<Object>() {
//                    @Override
//                    public void call(Subscriber<? super Object> subscriber) {
//                        DialogUtils.alertUserUnauthorized(mContext);
//                    }
//                }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onNext(Object o) {
//                    }
//                });
//
//                return;
//            } else if (msgBean.errorCode == 301) {
//                alert = "服务器繁忙，请稍后再试";
//            } else if (msgBean.errorCode == 302) {
//                alert = "此次访问已失效哦";
//            } else if (msgBean.errorCode == 403) {
//                alert = "接口临时关闭，请稍后再试";
//            } else if (msgBean.errorCode == 500) {
//                alert = "服务器繁忙，请稍后再试";
//            } else if (msgBean.errorCode == -1) {
//                showError = true;
//                if (msgBean.moreInfo != null) {
//                    alert = msgBean.moreInfo.toString();
//                }
//            } else {
//                return;
//            }
//            final String alertStr = alert;
//
//            Observable.create(new Observable.OnSubscribe<Object>() {
//                @Override
//                public void call(Subscriber<? super Object> subscriber) {
//                    //这里有 window bad token 错误
//                    try {
//                        if (showError && !TextUtils.isEmpty(alertStr)) {
//                            ToastUtils.showToast(mContext, alertStr);
//                            showError = false;
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
//        }
//    }
//
//    /**
//     * 统一处理请求头部数据
//     *
//     * @return
//     */
//
//    private static Request.Builder createRequestHeader(Request.Builder builder) {
//        builder.header("Content-Type",
//                "application/x-www-form-urlencoded");
//        builder.header("User-Agent", getUserAgent());
//        return builder;
//    }
//
//
//    public static String getUserAgent() {
//        UserAgentBean userAgent = new UserAgentBean();
//        userAgent.setDeviceToken(AppUtils.getDeviceId(mContext));
//        userAgent.setClient("Android");
//        userAgent.setChannel("xunbao");
//        if (User.getCurrent() != null) {
//            userAgent.setUserId(User.getCurrent().getId());
//        }
//        PackageInfo info = AppUtils.getPackageInfo(mContext);
//        if (info != null) {
//            userAgent.setBuild(info.versionCode + "");
//            userAgent.setVersion(info.versionName);
//        }
//        userAgent.setScreenSize(
//                DisplayUtils.getScreenSize(mContext));
//        userAgent.setSafeToken(SharePerferenceUtils.getString(mContext,PreferenceConstant.SAFE_TOKEN));
//        return userAgent.toString();
//    }
//
//}
