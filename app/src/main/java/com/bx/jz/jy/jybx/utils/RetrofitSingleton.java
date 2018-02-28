package com.bx.jz.jy.jybx.utils;

import android.os.Build;

import com.bx.jz.jy.jybx.Cons;
import com.bx.jz.jy.jybx.bean.IaJoyoungAPI;
import com.bx.jz.jy.jybx.bean.RmsJoyoungApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017\7\17 0017.
 */

public class RetrofitSingleton {

    private volatile static RetrofitSingleton INSTANCE = null;
    private OkHttpClient okHttpClient;
    private final int TIMEOUT_READ = 25;
    private final int TIMEOUT_CONNECTION = 25;


    private Retrofit retrofit;
    private Retrofit rmsRetrofit;


    private RetrofitSingleton(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Cons.BASE_URL_RMS)
                .client(initClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        rmsRetrofit = new Retrofit.Builder()
                .baseUrl(Cons.BASE_URL_RMS)
                .client(initClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }

    public synchronized static RetrofitSingleton getINSTANCE(){
        if(INSTANCE == null){
            synchronized (RetrofitSingleton.class){
                if(INSTANCE == null){
                    INSTANCE = new RetrofitSingleton();
                }
            }
        }
        return INSTANCE;
    }

    private OkHttpClient initClient(){
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request build = request.newBuilder()
                                .addHeader("source", "1")
                                .addHeader("model", Build.MANUFACTURER + Build.MODEL)
                                .addHeader("localsizeModel", "Android")
                                .addHeader("systemName", "\"Android\" + android.os.Build.VERSION.RELEASE")
                                .addHeader("systemVersion", String.valueOf(Build.VERSION.SDK_INT))
                                .addHeader("mobileId", "145674636")
                                .addHeader("mobileName", Build.MANUFACTURER)
                                .addHeader("appVersion", "1.0.1")
                                .build();
                        return chain.proceed(build);
                    }
                })
                .retryOnConnectionFailure(true)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION,TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    public IaJoyoungAPI getJoyoungApi(){
        return retrofit.create(IaJoyoungAPI.class);
    }

    public RmsJoyoungApi getRmsJoyoungApi(){
        return rmsRetrofit.create(RmsJoyoungApi.class);
    }

}
