package com.zhongjh.mvvmrapid.base;

import android.app.Application;

import androidx.annotation.NonNull;

import com.zhongjh.mvvmrapid.utils.Utils;

public class BaseApplication extends Application {

    private static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        setApplication(this);
    }

    /**
     * 当主工程没有继承BaseApplication时，可以使用setApplication方法初始化BaseApplication
     */
    public static synchronized void setApplication(@NonNull Application application) {
        sInstance = application;
        // 初始化工具类
        Utils.init(application);
    }


}
