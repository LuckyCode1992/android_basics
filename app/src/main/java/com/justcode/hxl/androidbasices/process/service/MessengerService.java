package com.justcode.hxl.androidbasices.process.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ConfigurationHelper;
import android.util.Log;

public class MessengerService extends Service {
    private final String TAG = this.getClass().getSimpleName();
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg != null && msg.arg1 == ConfigHelper.MSG_ID_CLIENT) {
                if (msg.getData() == null) {
                    return;
                }
                String content = (String) msg.getData().get(ConfigHelper.MSG_CONTENT);  //接收客户端的消息
                Log.d("MessengerService", "Message from client: " + content);
                Message message = Message.obtain();
                message.arg1 = ConfigHelper.MSG_ID_SERVER;
                Bundle bundle = new Bundle();
                bundle.putString(ConfigHelper.MSG_CONTENT, "听到你的消息了，有什么快说");
                message.setData(bundle);

                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        }
    };
    Messenger messenger = new Messenger(handler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
