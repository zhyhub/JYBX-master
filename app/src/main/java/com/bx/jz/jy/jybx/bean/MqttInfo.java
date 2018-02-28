package com.bx.jz.jy.jybx.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Joyoung on 2016/5/24.
 */
public class MqttInfo implements Parcelable {
    private int port;

    private String addr;

    private String user;

    private String pass;

    protected MqttInfo(Parcel in) {
        port = in.readInt();
        addr = in.readString();
        user = in.readString();
        pass = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(port);
        dest.writeString(addr);
        dest.writeString(user);
        dest.writeString(pass);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MqttInfo> CREATOR = new Creator<MqttInfo>() {
        @Override
        public MqttInfo createFromParcel(Parcel in) {
            return new MqttInfo(in);
        }

        @Override
        public MqttInfo[] newArray(int size) {
            return new MqttInfo[size];
        }
    };

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
