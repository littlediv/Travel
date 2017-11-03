package net.xinhong.travel.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mac on 2017/1/4.
 */
public class RetrofilUtils {

    private static Context mContext;
    public static Retrofit retrofit = null;

    public static Retrofit getInstance(Context context) {
        mContext = context;
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

//            builder.addInterceptor(addQueryParameterInterceptor());
//            builder.addInterceptor(addHeaderInterceptor());

            //设置缓存
            File cacheFile = new File(mContext.getExternalCacheDir(), "retrofitCache");

            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
            builder.cache(cache).addInterceptor(addCacheInterceptor());

            //设置超时
            builder.connectTimeout(15, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);

            //错误重连
            builder.retryOnConnectionFailure(true);

            OkHttpClient client = builder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

        }
        return retrofit;
    }

    /**
     * 设置公共参数
     * @return
     */
    private static Interceptor addQueryParameterInterceptor() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request = null;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        .addQueryParameter("key", "123456789")
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
    }


    private static Interceptor addHeaderInterceptor() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request orginalRequest = chain.request();
                Request request = orginalRequest.newBuilder()
                        .header("apptype", "tpos")
                        .header("Accept", "application/json")
                        .method(orginalRequest.method(), orginalRequest.body())
                        .build();

                return chain.proceed(request);
            }

        };
        return headerInterceptor;
    }


    /**
     * 设置缓存
     */
    private static Interceptor addCacheInterceptor() {
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!isNetworkAvailable(mContext)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();

                }
                Response response = chain.proceed(request);
                if (isNetworkAvailable(mContext)) {
                    int maxAge =  60 * 60 * 24 * 28;
                   response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                }else
                {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();

                }

                return response;
            }

        };
        return cacheInterceptor;
    }

    public static boolean isNetworkAvailable(Context ctx) {
        Context context = ctx.getApplicationContext();

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }else {
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable();
            }

        }
        return false;
    }

    public static Gson getGson(){

        Gson gson = new GsonBuilder().excludeFieldsWithModifiers().create();
        return gson;
    }


























}
