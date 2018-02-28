package com.bx.jz.jy.jybx.application;

import android.app.Application;

import com.bx.jz.jy.jybx.utils.L;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * 友盟推送集成
 */

public class MyApplication extends Application{

    private static final String TAG = MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        PushAgent pushAgent = PushAgent.getInstance(this);
        pushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String s) {
                L.e(TAG,"获取device token  onSuccess " + s);
            }

            @Override
            public void onFailure(String s, String s1) {
                L.e(TAG,"获取device token  onFailure " + s + "    " + s1);
            }
        });


        PlatformConfig.setWeixin("","");
    }
}
