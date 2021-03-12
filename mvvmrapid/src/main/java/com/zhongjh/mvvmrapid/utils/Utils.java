package com.zhongjh.mvvmrapid.utils;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * 用于提供给其他静态类调用context
 */
public class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("不能直接实例化该类，仅仅提供给其他静态类调用context");
    }

}
