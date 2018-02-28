package com.bx.jz.jy.jybx.bean;

import com.bx.jz.jy.jybx.utils.BaseResult;
import com.joyoung.sdk.info.UserConfirmData;

import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by liuwei on 2017/12/13.
 */

public   interface RmsJoyoungApi {
    @POST
    Observable<BaseResult<UserConfirmData>> login(
            @Url String url, @Query("param") String param);
}
