package com.justcode.hxl.androidbasices.process.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import com.justcode.hxl.androidbasices.IMyAidl;
import com.justcode.hxl.androidbasices.process.Person;

import java.util.ArrayList;
import java.util.List;

public class MyAidlService extends Service {
    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<Person> mPersons = new ArrayList<>();

    /**
     * 创建生成的本地 Binder 对象，实现 AIDL 制定的方法
     */
    private IBinder mIBinder = new IMyAidl.Stub() {

        @Override
        public void addPerson(Person person) throws RemoteException {
            mPersons.add(person);
        }

        @Override
        public List<Person> getPersonList() throws RemoteException {
            return mPersons;
        }
    };

    /**
     * 客户端与服务端绑定时的回调，返回 mIBinder 后客户端就可以通过它远程调用服务端的方法，即实现了通讯
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "MyAidlService onBind");
        return mIBinder;
    }
}
