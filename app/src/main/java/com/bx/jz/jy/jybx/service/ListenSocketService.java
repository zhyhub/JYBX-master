package com.bx.jz.jy.jybx.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * author: zhy
 * email: 760982661@qq.com
 * date: 2018/1/18 0018.
 * desc:
 */

public class ListenSocketService extends Service {
    ServerSocket mServerSocket = null;
    boolean isRunning = true;
    final static String TAG = "CooeeDemo";

    private Intent intent = new Intent("com.signway.wifimac.RECEIVER");

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mServerSocket = new ServerSocket(5000);
                    while(isRunning){
                        Socket mSocket = mServerSocket.accept();
                        new Thread(new ServiceRunnable(mSocket)).start();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    class ServiceRunnable implements Runnable {
        Socket socket = null;
        public ServiceRunnable(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String read;
            InputStream inputStream;
            BufferedReader readBuffer;

            try {
                inputStream = socket.getInputStream();
                readBuffer = new BufferedReader(new InputStreamReader(inputStream));
                while(isRunning) {
                    if( (read = readBuffer.readLine()) != null){
                        Log.i(TAG, "========= read = " + read);
                        //发送Action为com.example.communication.RECEIVER的广播
                        intent.putExtra("wifimac", read);
                        sendBroadcast(intent);
                        break;
                    }

                }
                Log.i(TAG, "read while end");
                readBuffer.close();
                inputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
