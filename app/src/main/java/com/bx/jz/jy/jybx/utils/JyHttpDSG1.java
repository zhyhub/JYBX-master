package com.bx.jz.jy.jybx.utils;

import com.bx.jz.jy.jybx.Cons;
import com.joyoung.sdk.info.UserConfirmData;
import com.joyoung.sdk.utils.RequesUtil;
import com.joyoung.sdk.utils.encryptdecryptutil.MakeUUID;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by liuwei on 2017/12/13.
 */

public class JyHttpDSG1 {
    public interface JoyoungCallBack<T> {
        void Success(T t);

        void failed(String msg);
    }

    public void login(String userName, String password,
                      final JoyoungCallBack<BaseResult<UserConfirmData>> callBack) {
        try {
          String  json = new LoginBuilder()
                    .uname(userName)
                    .passwd(MakeUUID.base64Encode(password.getBytes()))
                    .build();

            String baseUrl = "/rms/v1/app/login?action=login";
            String rmsUrl = noSessionUrl(baseUrl, json);
            String param = noSessionParma(json);

            RetrofitSingleton.getINSTANCE().getRmsJoyoungApi().login(rmsUrl, param)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<BaseResult<UserConfirmData>>() {
                        @Override
                        public void call(BaseResult<UserConfirmData> result) {
                            callBack.Success(result);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            callBack.failed(throwable.getMessage());
                        }
                    });
        } catch (Exception e) {
            return;
        }
    }

    private String noSessionUrl(String baseUrl, String param) {
        //生成签名
        byte[] pdata = null;
        if (param != null && !"".equals(param)) {
            pdata = param.getBytes();
        }
        long stamp = System.currentTimeMillis();
        //&enc=0 不加密；&enc=1 AESCoder密；&enc=2 xxtea加密
        baseUrl = baseUrl + "&msgId=1&stamp=" + stamp + "&enc=0";
        String sign = RequesUtil.getSign(baseUrl, pdata, "");
        baseUrl += "&sign=" + sign;
        baseUrl = Cons.BASE_URL_RMS + baseUrl;
        return baseUrl;
    }

    private String noSessionParma(String param) {
        byte[] pdata = null;
        if (param != null && !"".equals(param)) {
            pdata = param.getBytes();
            return new String(MakeUUID.base64Encode(pdata));
        }
        return null;
    }
}
