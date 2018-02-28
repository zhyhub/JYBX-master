package com.bx.jz.jy.jybx;

import com.bx.jz.jy.jybx.bean.MqttInfo;

/**
 * author: zhy
 * email: 760982661@qq.com
 * date: 2018/1/23 0023.
 * desc:
 */

public class Cons {

    public static final String BASE_URL_RMS = "http://apitest.joyoung.com:8389";

    public static String sessionkey = null;
    public static String sessionId = null;
    public static String dataKey = null;
    public static MqttInfo mqttInfo=null;
    public static final String BASELOGINURL = "/rms/v1/app/login?action=login";
    //获取mqtt地址信息
    public static final String BASE_GETMQTTADDRESS = "/cms/v1/app/getPush";

    public static final String BASEMYDEVURL="/rms/v1/app/userDev?action=myDevs";

    public static  final String BASEMYDEVSTATE="/url/v1/app/getDevInfo";

    public static final String BASECONTRALURL="/cms/v1/app/controlDev";

    public static final String BASEBINDDEV="/rms/v1/app/userDev?action=bindDev";

    public static final byte[]QUERYDEVSTATE=new byte[] {

            (byte) 0xcc, (byte) 0x00, //
            (byte) 0x00, (byte) 0x01, // VERSION
            (byte) 0x00, // RAS
            (byte) 0x00, (byte) 0x07, // LENGTH
            (byte) 0x00, // CRU
            (byte) 0x00, (byte) 0x01, // type
            (byte) 0x00, (byte) 0xb2, // cmd type
            (byte) 0x00, (byte) 0x00 // cmd length
    };
}
