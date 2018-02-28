package com.bx.jz.jy.jybx.utils;

import org.json.JSONObject;

/**
 * Created by liuwei on 2017/12/13.
 */

public class LoginBuilder {
    private JSONObject jsonObject = new JSONObject();


    public LoginBuilder uname(String uname) throws Exception {
        jsonObject.put("uname", uname);
        return this;
    }

    public LoginBuilder passwd(String passwd) throws Exception {
        jsonObject.put("passwd", passwd);
        return this;
    }

    public String build() throws Exception {
        return jsonObject.toString();
    }
}
